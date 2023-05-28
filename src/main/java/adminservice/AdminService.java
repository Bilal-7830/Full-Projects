package adminservice;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import GenerateOrder.GenerateOrderDao;
import GenerateOrder.OrderDetailBean;
import availablemedicines.AvailableMedicineDao;
import bill.BillDao;
import contactus.ContactUsService;
import nearexpired.MedicineDetailBean;
import nearexpired.NearExpiredDao;
import register.employee.RegisterDao;
import sendnotification.SendNotificationBean;
import sendnotification.SendNotificationService;
import sendorder.OrderMedicineBean;
import sendorder.RecivedBean;
import setattendence.EmpNamePhoneBean;
import setattendence.SetAttendenceDao;
import staffsalary.StaffSalaryService;
import utils.AppLogger;
import utils.MailSender;
import utils.MemCache;
import utils.RandomNumber;
import utils.ResponseMessages;
import utils.Validators;

public class AdminService {
	static AppLogger appLogger = new AppLogger();
	static Logger logger = appLogger.getLogger();

	public String validateOtp(int otp, Model model) {
		logger.info("Validating otp");
		if (otp != MemCache.getOtp()) {
			model.addAttribute("errorMessage", ResponseMessages.INVALID_OTP);
			logger.error("Invalid otp given = " + otp + "Required = " + MemCache.getOtp());
			return "RegisterOTP";
		}
		boolean isRegistrationSuccess = RegisterDao.registerEmp(MemCache.getRegisterEmployeeMap(), model);
		if (!isRegistrationSuccess) {
			model.addAttribute("errorMessage", ResponseMessages.DUPLICATE_VALUE);
			return "Register";
		}
		
		model.addAttribute("action", ResponseMessages.EMP_ADDED);
		return "success";
	}

	public String adminAuth(Map<String, String> map, Model model) {
		logger.info("Validating Admin====>>>");
		if (map.get("email").equals("bilal123@gmail.com") && map.get("password").equals("Bilal@321")) {
			model.addAttribute("errorMessage", "");
			logger.info("Loged in successfully");
			return "AdminFeature";
		} else {
			model.addAttribute("errorMessage", ResponseMessages.ACCESS_DENIED);
			logger.error("Admin credentials not matched");
			return "AdminLogin";
		}
	}

	public String calcuteDaySale(Model model) {
		float amount = BillDao.calculateFullDaySale(new Date(System.currentTimeMillis()));
		model.addAttribute("sale", "Sale Amount = " + amount);
		return "AdminFeature";
	}

	public String contact(String name, String email, String msg, Model model) {
		logger.info("contacting ==>>>>");
		ContactUsService.contactToAdmin(name, email, msg);
		model.addAttribute("action", "Contacted successfully");
		return "success";
	}

	public String registerEmp(Map<String, String> map, Model model) {
		MemCache.setRegisterEmployeeMap(map);
		String password = map.get("pass");
		String cpassword = map.get("cpass");
		String email = map.get("email");
		if (Validators.isEmptyNullOrBlank(map) || !password.equals(cpassword)) {
			model.addAttribute("errorMessage", ResponseMessages.VALUE_ERROR);
			logger.error(ResponseMessages.VALUE_ERROR);
			return "Register";
		}
		int otp = RandomNumber.generateRandomNumber();
		MemCache.setOtp(otp);
		MailSender.sendMail("Sehat Era OTP", ResponseMessages.OTP_EMIAL + String.valueOf(otp), email);
		return "RegisterOTP";
	}

	public String nearExpire(Model model) {
		List<MedicineDetailBean> medicines = NearExpiredDao.nearExpiredMedicines();
		model.addAttribute("data", medicines);
		return "NearExpiredTable";
	}

	public String getOrders(Model model) {
		List<OrderDetailBean> medicines = GenerateOrderDao.generateOrderList();
		model.addAttribute("data", medicines);
		return "OrdersTable";
	}

	public String getEmpAttendence(Model model) {
		List<EmpNamePhoneBean> data = SetAttendenceDao.getEmpList();
		model.addAttribute("data", data);
		return "Attendence";
	}

	public String setEmpAttendece(String[] empIDs, String[] attendences, Model model) {
		boolean success = SetAttendenceDao.isSetAttendence(empIDs, attendences);
		if (!success) {
			model.addAttribute("errorMessage", ResponseMessages.ALREADY_UPDATED_SALARY);
			return "Attendence";
		}
		model.addAttribute("action", "Attendence set successfully!");
		return "success";
	}

	public String updateSalry(List<String> empIdList, Model model) {
		try {
			StaffSalaryService.dBLogger(empIdList);
		} catch (SQLException e) {
			model.addAttribute("errorMessage", ResponseMessages.ALREADY_UPDATED_SALARY);
			return "EmployeeSalary";
		}
		model.addAttribute("action", "Salary paid successfully");
		return "success";
	}

	public String sendOrder(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<OrderDetailBean> medicines = GenerateOrderDao.generateOrderList();
		HttpEntity<List<OrderDetailBean>> request = new HttpEntity<>(medicines);
		String endPoint = "http://localhost:9000/DoOrder";
		HttpEntity<RecivedBean> response = restTemplate.exchange(endPoint, HttpMethod.POST, request, RecivedBean.class);
		RecivedBean recived = response.getBody();
//		Retrieving the list of medicine ordered 
		List<OrderMedicineBean> bill = MemCache.retrieveOrderMedicineFromMemCache();
		BillDao.updateOrderMedicineQntINDB(bill);
		model.addAttribute("data", bill);
		model.addAttribute("total", "Total amt(INR) = " + recived.getAmount());
		return "SupplierBill";
	}

	public ResponseEntity<RecivedBean> doOrderToSupplier(List<OrderDetailBean> medicines) {
		float totalAmount = 0f;
		for (OrderDetailBean medicine : medicines) {
			OrderMedicineBean objOrderBean = new OrderMedicineBean();
			float amount = medicine.getPrice() - medicine.getPrice() * 20 / 100;
			totalAmount = medicine.getOrderQuantity() * amount;
			objOrderBean.setMedicineName(medicine.getMedicineName());
			objOrderBean.setPrice(totalAmount);
			objOrderBean.setQuantity(medicine.getOrderQuantity());
			objOrderBean.setCategory(medicine.getCategory());
			MemCache.addOrderMedicineToMemCache(objOrderBean);
		}
		RecivedBean recived = new RecivedBean();
		recived.setAmount(totalAmount);
		recived.setSuccess(true);
		return ResponseEntity.ok(recived);
	}

	public String allAvilableMedicines(Model model) {
		List<MedicineDetailBean> data = AvailableMedicineDao.availableMedicines();
		model.addAttribute("data", data);
		return "AvailableMedicines";
	}

	public String sendNotification(Model model) {
		List<SendNotificationBean> data = SendNotificationService.sendedMailList();
		model.addAttribute("data", data);
		return "SendNotification";
	}
}
