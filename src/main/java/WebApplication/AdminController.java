package WebApplication;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import GenerateOrder.GenerateOrderDao;
import GenerateOrder.OrderDetailBean;
import adminservice.AdminService;
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
import staffsalary.EmpSalaryBean;
import staffsalary.StaffSalaryDao;
import staffsalary.StaffSalaryService;
import utils.MailSender;
import utils.MemCache;
import utils.RandomNumber;
import utils.ResponseMessages;
import utils.Validators;

@Controller
public class AdminController {
	static AdminService service = new AdminService();

	@RequestMapping(value = "/AdminLogin", method = RequestMethod.GET)
	public String adminLogin() {
		return "AdminLogin";
	}

	@RequestMapping(value = "/AdminFeature",method = RequestMethod.GET)
	public String adminFeature() {
		return "AdminFeature";
	}

	@RequestMapping(value = "/ValidateOTP", method = RequestMethod.POST)
	public String validate(@RequestParam("otp") int otp, Model model) {
		return service.validateOtp(otp, model);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String processLoginForm(@RequestParam Map<String, String> map, Model model) {
		return service.adminAuth(map, model);
	}

	@RequestMapping(value = "/FullDaySale", method = RequestMethod.GET)
	public String fullDaySale(Model model) {
		return service.calcuteDaySale(model);
	}

	@RequestMapping(value = "/ContactedData", method = RequestMethod.POST)
	public String contactData(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("msg") String msg, Model model) {
		return service.contact(name, email, msg, model);
	}

	@RequestMapping(value = "/RegisterEmployee", method = RequestMethod.POST)
	public String registerEmployee(@RequestParam Map<String, String> map, Model model) {
		return service.registerEmp(map, model);
	}

	@RequestMapping(value = "/NearExpireTable", method = RequestMethod.GET)
	public String nearExmp(Model model) {
		return service.nearExpire(model);
	}

	@RequestMapping(value = "/Orders", method = RequestMethod.GET)
	public String generateOrderList(Model model) {
		return service.getOrders(model);
	}

	@RequestMapping(value = "/GetAttendence",method = RequestMethod.GET)
	public String getAttendece(Model model) {
		return service.getEmpAttendence(model);
	}

	@RequestMapping(value = "/SetAttendence", method = RequestMethod.POST)
	public String setAttendence(@RequestParam("empId") String[] empIDs,
			@RequestParam("attendance") String[] attendences, Model model) {
		return service.setEmpAttendece(empIDs, attendences, model);
	}

	@RequestMapping(value = "/StaffSalary",method = RequestMethod.GET)
	public String staffSalary(Model model) {
		List<EmpSalaryBean> result = StaffSalaryDao.getSalaryDetails();
		model.addAttribute("data", result);
		return "EmployeeSalary";
	}

	@RequestMapping(value = "/DoneSalary", method = RequestMethod.POST)
	public String doneSalary(@RequestParam(name = "checkbox", required = false) List<String> empIdList, Model model) {
		return service.updateSalry(empIdList, model);
	}

	@RequestMapping(value = "/SendOrder", method = RequestMethod.GET)
	public String sendOrder(Model model) {
		return service.sendOrder(model);
	}

	@RequestMapping(value = "/DoOrder", method = RequestMethod.POST)
	public ResponseEntity<RecivedBean> recieveOrder(@RequestBody List<OrderDetailBean> medicines) {
		return service.doOrderToSupplier(medicines);
	}

	@RequestMapping(value = "/AvailableMedicine", method = RequestMethod.GET)
	public String availableMed(Model model) {
		return service.allAvilableMedicines(model);
	}

	@RequestMapping(value = "SendDueNotification", method = RequestMethod.GET)
	public String sendNofication(Model model) {
		return service.sendNotification(model);
	}

}
