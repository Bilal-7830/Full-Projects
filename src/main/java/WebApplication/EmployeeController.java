package WebApplication;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import EmployeeService.EmployeeService;

@Controller
public class EmployeeController {
	EmployeeService service = new EmployeeService();

	@RequestMapping(value = "/EmployeeLogin", method = RequestMethod.GET)
	public String employeeLogin() {
		return "EmployeeLogin";
	}

	@RequestMapping(value = "/EmployeeFeature", method = RequestMethod.GET)
	public String employeeFeature(Model model) {
		System.out.println("EMp features");
		return service.getEmployeeFeature(model);
	}

	@RequestMapping(value = "/Sell", method = RequestMethod.GET)
	public String sell(Model model) {
		return service.sell(model);
	}

	@RequestMapping(value = "/BillId", method = RequestMethod.GET)
	public String displayBillId(@RequestParam("quantities") List<Integer> quantities,
			@RequestParam("medicines") List<String> medicines, @RequestParam("prices") List<Float> prices,
			@RequestParam("catagories") List<String> catagories, @RequestParam("doses") List<String> doses,
			Model model) {
		return service.displayBillId(quantities, medicines, prices, catagories, doses, model);
	}

	@RequestMapping(value = "/GenerateBill", method = RequestMethod.GET)
	public String generateBill() {
		return "GenerateBill";
	}

	@RequestMapping(value = "/BillOTP", method = RequestMethod.POST)
	public String billOTP(@RequestParam Map<String, String> map, Model model) {
		return service.setBillOTP(map, model);
	}

	@RequestMapping(value = "/DisplayBill", method = RequestMethod.POST)
	public String displayBill(@RequestParam("otp") int otp, Model model) {
		return service.validateOtp(otp, model);
	}

	@RequestMapping(value = "/LendMedcineForm", method = RequestMethod.GET)
	public String lendForm() {
		return "LendForm";
	}

	@RequestMapping(value = "/LendMedicine", method = RequestMethod.POST)
	public String lendMedicine(@RequestParam Map<String, String> map, Model model) {
		return service.lendMedicine(map, model);
	}

	@RequestMapping(value = "/RecentTransaction", method = RequestMethod.GET)
	public String recentTransaction(Model model) {
		return service.getRecentTransaction(model);
	}

	@RequestMapping(value = "/EmployeeAuth", method = RequestMethod.POST)
	public String empAuth(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		return service.authEmp(email, password, model);
	}

	@RequestMapping(value = "/SeePresent", method = RequestMethod.GET)
	public String seePresent(Model model) {
		return service.seePresents(model);
	}

	@RequestMapping(value = "/CalculateSalary", method = RequestMethod.GET)
	public String seeSalary(Model model) {
		return service.getSalary(model);
	}

	@RequestMapping(value = "/ChangePassword", method = RequestMethod.GET)
	public String changePass() {
		return "ChangePassword";
	}

	@RequestMapping(value = "/ChangePasswordAuth", method = RequestMethod.POST)
	public String changePasswordAuth(@RequestParam("oldPassword") String oldPass,
			@RequestParam("password") String password, @RequestParam("cPassword") String cPassword, Model model) {
		return service.changePass(oldPass, password, cPassword, model);
	}
}
