package WebApplication;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import availablemedicines.AvailableMedicineDao;
import bill.BillBean;
import bill.BillDao;
import dose.DoseBean;
import dose.DoseDao;
import lendbalance.LendBalanceDao;
import lendmedicine.LendMedicineDao;
import login.LoginDao;
import nearexpired.MedicineDetailBean;
import nearexpired.NearExpiredDao;
import recenttransaction.RecentTransactionBean;
import recenttransaction.RecentTransactionDao;
import register.employee.RegisterDao;
import sendorder.OrderMedicineBean;
import sendorder.RecivedBean;
import setattendence.EmpNamePhoneBean;
import setattendence.SetAttendenceDao;
import staffsalary.EmpSalaryBean;
import staffsalary.StaffSalaryDao;
import staffsalary.StaffSalaryService;
import utils.MemCache;
import utils.ResponseMessages;
import utils.Validators;

@Controller
public class WebApplicationController {
	@RequestMapping("/index")
	public String index() {
		System.out.println("index method called");
		return "index";
	}

	@RequestMapping("/Register")
	public String register() {
		return "Register";
	}

	@RequestMapping("/CusRegister")
	public String cusRegister() {
		return "CustomerRegister";
	}

	@RequestMapping("/AdminLogin")
	public String adminLogin() {
		return "AdminLogin";
	}

	@RequestMapping("/EmployeeLogin")
	public String employeeLogin() {
		return "EmployeeLogin";
	}

	@RequestMapping("/CustomerLogin")
	public String customerLogin() {
		return "CustomerLogin";
	}

	@RequestMapping("/ContactUs")
	public String contactUs() {
		System.out.println("contact us method called");
		return "ContactUs";
	}

	@RequestMapping("/AdminFeature")
	public String adminFeature() {
		System.out.println("Admin feature method called");
		return "AdminFeature";
	}

	@RequestMapping("/EmployeeFeature")
	public String employeeFeature() {
		return "EmployeeFeature";
	}

	@RequestMapping("/CustomerFeature")
	public String customerFeature(Model model) {
		String name = MemCache.getCustomerName();
		model.addAttribute("welcome", "Welcome " + name + "Here are some features that you can explore!");
		return "CustomerFeature";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String processLoginForm(@RequestParam Map<String, String> map, Model model) {
		System.out.println("Login****");
		if (map.get("email").equals("bilal123@gmail.com") && map.get("password").equals("Bilal@321")) {
			model.addAttribute("errorMessage", "");
			return "AdminFeature";
		} else {
			model.addAttribute("errorMessage", ResponseMessages.ACCESS_DENIED);
			return "AdminLogin";
		}
	}

	@RequestMapping("/RegisterEmployee")
	public String registerEmployee(@RequestParam Map<String, String> map, Model model) {
		try {
			String password = map.get("pass");
			String cpassword = map.get("cpass");
			if (Validators.isEmptyNullOrBlank(map) || !password.equals(cpassword)) {
				model.addAttribute("errorMessage", ResponseMessages.VALUE_ERROR);
				return "Register";
			}
			boolean isRegistrationSuccess = RegisterDao.registerEmp(map, model);
			if (!isRegistrationSuccess) {
				model.addAttribute("errorMessage", ResponseMessages.DUPLICATE_VALUE);
				return "Register";
			}
			model.addAttribute("errorMessage", ResponseMessages.EMP_ADDED);
			return "success";
		} catch (Exception e) {
			model.addAttribute("errorMessage", ResponseMessages.GENERIC);
			return "Register";
		}
	}

	@RequestMapping("/success")
	public String success() {
		return "success";
	}

	@RequestMapping("/Table")
	public String nearExmp(Model model) {
		System.out.println("NearExp******===>");
		List<MedicineDetailBean> medicines = NearExpiredDao.nearExpiredMedicines();
		model.addAttribute("data", medicines);
		return "NearExpiredTable";
	}

	@RequestMapping("/Orders")
	public String generateOrderList(Model model) {
		System.out.println("generating Orders=======>>>>");
		List<OrderDetailBean> medicines = GenerateOrderDao.generateOrderList();
		model.addAttribute("data", medicines);
		return "OrdersTable";
	}

	@RequestMapping("/GetAttendence")
	public String getAttendece(Model model) {
		List<EmpNamePhoneBean> data = SetAttendenceDao.getEmpList();
		model.addAttribute("data", data);
		return "Attendence";
	}

	@RequestMapping("/SetAttendence")
	public String setAttendence(@RequestParam("empId") String[] empIDs,
			@RequestParam("attendance") String[] attendences, Model model) {
		boolean success = SetAttendenceDao.isSetAttendence(empIDs, attendences);
		if (!success) {
			model.addAttribute("errorMessage", ResponseMessages.ALREADY_UPDATED_SALARY);
			return "Attendence";
		}
		return "success";
	}

	@RequestMapping("StaffSalary")
	public String staffSalary(Model model) {
		List<EmpSalaryBean> result = StaffSalaryDao.getSalaryDetails();
		model.addAttribute("data", result);
		return "EmployeeSalary";
	}

	@RequestMapping("DoneSalary")
	public String doneSalary(@RequestParam(name = "checkbox", required = false) List<String> empIdList, Model model) {
		try {
			StaffSalaryService.dBLogger(empIdList);
		} catch (SQLException e) {
			model.addAttribute("errorMessage", ResponseMessages.ALREADY_UPDATED_SALARY);
			return "EmployeeSalary";
		}
		return "success";
	}

	@RequestMapping("/SendOrder")
	public String sendOrder(Model model) {
		RestTemplate restTemplate = new RestTemplate();
		List<OrderDetailBean> medicines = GenerateOrderDao.generateOrderList();
		HttpEntity<List<OrderDetailBean>> request = new HttpEntity<>(medicines);
		String endPoint = "http://localhost:9000/DoOrder";
		HttpEntity<RecivedBean> response = restTemplate.exchange(endPoint, HttpMethod.POST, request, RecivedBean.class);
		RecivedBean recived = response.getBody();
//		retriving the list of medicine ordered 
		List<OrderMedicineBean> bill = MemCache.retrieveOrderMedicineFromMemCache();
		BillDao.updateOrderMedicineQntINDB(bill);
		model.addAttribute("data", bill);
		model.addAttribute("total", "Total amt(INR) = " + recived.getAmount());
		return "SupplierBill";
	}

	@RequestMapping("/DoOrder")
	public ResponseEntity<RecivedBean> recieveOrder(@RequestBody List<OrderDetailBean> medicines) {
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

	@RequestMapping("/AvailableMedicine")
	public String availableMed(Model model) {
		List<MedicineDetailBean> data = AvailableMedicineDao.availableMedicines();
		model.addAttribute("data", data);
		return "AvailableMedicines";
	}

	@RequestMapping("/Sell")
	public String sell(Model model) {
		List<MedicineDetailBean> data = AvailableMedicineDao.availableMedicines();
		model.addAttribute("data", data);
		String bill_id = UUID.randomUUID().toString();
		return "Sell";
	}

	@RequestMapping("/BillId")
	public String billId(@RequestParam("quantities") List<Integer> quantities,
			@RequestParam("medicines") List<String> medicines, @RequestParam("prices") List<Float> prices,
			@RequestParam("catagories") List<String> catagories, @RequestParam("doses") List<String> doses,
			Model model) {

		String billId = UUID.randomUUID().toString();
		MemCache.addBillListToMemcache(billId, medicines, quantities, prices, catagories, doses);
		model.addAttribute("billId", "Bill Id = " + billId + "  (please copy it for create bill)");
		return "Sell";
	}

	@RequestMapping("/GenerateBill")
	public String generateBill() {
		return "GenerateBill";
	}

	@RequestMapping("/DisplayBill")
	public String displayBill(@RequestParam Map<String, String> map, Model model) {
		String billId = map.get("billId");
		String email = map.get("email");
		Map<String, List<BillBean>> result = MemCache.retrieveBillListToMemcache();
		List<BillBean> data = result.get(billId);
		int total = MemCache.getTotalMedicineBillAmount();
		BillDao.addBillToDB(email, billId, total, new Date(System.currentTimeMillis()));
		BillDao.addBillMedicinesToDB(data, email, billId);
		BillDao.updateBillMedicineQntINDB(data);
		if (!result.containsKey(billId)) {
			model.addAttribute("errorMessage", "Bill id not exists try again");
			return "GenerateBill";
		}
		model.addAttribute("data", data);
		model.addAttribute("total", "Amount to Pay(INR) = " + total);
		return "DisplayBill";
	}

	@RequestMapping("/FullDaySale")
	public String fullDaySale(Model model) {
		float amount = BillDao.calculateFullDaySale(new Date(System.currentTimeMillis()));
		model.addAttribute("sale", "Sale Amount = " + amount);
		return "AdminFeature";
	}

	@RequestMapping("/LendMedcineForm")
	public String lendForm() {
		return "LendForm";
	}

	@RequestMapping("/LendMedicine")
	public String lendMedicine(@RequestParam Map<String, String> map, Model model) {
		String billId = map.get("billId");
		String email = map.get("email");
		float amount = Float.parseFloat(map.get("amount"));
		if (!MemCache.retrieveBillListToMemcache().containsKey(billId) || !email.equals(MemCache.getCustomerMail())) {
			model.addAttribute("errorMessage", "Bill id or email not exists try again");
			return "LendForm";
		}
		LendMedicineDao.lendMedicineInDB(billId, amount);
		return "success";
	}

	@RequestMapping("/RecentTransaction")
	public String recentTransaction(Model model) {
		List<RecentTransactionBean> data = RecentTransactionDao
				.getRecentTransaction(new Date(System.currentTimeMillis()));
		model.addAttribute("data", data);
		return "RecentTransaction";
	}

	@RequestMapping("/LendBalance")
	public String balance(Model model) {
		float amount = LendBalanceDao.lendBalance();
		model.addAttribute("amount", "Amount to be pain = " + amount);
		return "CustomerFeature";
	}

	@RequestMapping("/PaymentHistory")
	public String history(Model model) {
		System.out.println("PaymentHistory");
		List<RecentTransactionBean> data = RecentTransactionDao
				.getRecentCustomerTransaction(MemCache.getCustomerMail());
		model.addAttribute("data", data);
		return "RecentTransaction";
	}

	@RequestMapping("/GetDose")
	public String dose() {
		return "GetDose";
	}

	@RequestMapping("DisplayDose")
	public String displayDose(String billId, Model model) {
		List<DoseBean> data = DoseDao.getDose(billId);
		if (data == null || data.size() == 0) {
			model.addAttribute("errorMessage", "Invalid bill Id");
			return "GetDose";
		}
		model.addAttribute("data", data);
		return "DisplayDose";
	}

	@RequestMapping("/CustomerAuth")
	public String customerLogin(@RequestParam("name") String name,@RequestParam("email") String email, @RequestParam("pass") String pass, Model model) {
		boolean isAuth = LoginDao.isAuthCustmer(email, pass);
		if (!isAuth) {
			model.addAttribute("errorMessage", ResponseMessages.ACCESS_DENIED);
			return "CustomerLogin";
		}
		MemCache.setCustomerName(name);
		return "CustomerFeature";
	}

	@RequestMapping("/RegisterCustomer")
	public String registerCustomer(@RequestParam HashMap<String, String> customerDetail, Model model) {
		try {
			LoginDao.registerCustomer(customerDetail);
		} catch (SQLException e) {
			model.addAttribute("errorMessage", ResponseMessages.DUPLICATE_VALUE);
			e.printStackTrace();
			return "RegisterCustomer";
		}
		return "success";
	}
	@RequestMapping("/EmployeeAuth")
	public String empAuth(@RequestParam("email") String email,@RequestParam("password") String password,Model model) {
		System.out.println("employee auth");
		boolean isAuth = LoginDao.isAuthEmployee(email,password);
		if(!isAuth){
			model.addAttribute("errorMessage",ResponseMessages.ACCESS_DENIED);
			return "EmployeeLogin";
		}
		return "EmployeeFeature";
		
	}
}