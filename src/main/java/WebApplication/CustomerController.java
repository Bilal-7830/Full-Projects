package WebApplication;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import customerservice.CustomerService;

@Controller
public class CustomerController {
	CustomerService service = new CustomerService();

	@RequestMapping( value ="/CustomerLogin", method = RequestMethod.GET)
	public String customerLogin() {
		return "CustomerLogin";
	}

	@RequestMapping( value ="/CusRegister", method = RequestMethod.GET)
	public String cusRegister() {
		return "CustomerRegister";
	}

	@RequestMapping( value ="/CustomerFeature", method = RequestMethod.GET)
	public String customerFeature(Model model) {
		return service.getCustomerFeature(model);
	}

	@RequestMapping( value ="/LendBalance", method = RequestMethod.GET)
	public String balance(Model model) {
		return service.getLendBalance(model);
	}

	@RequestMapping( value ="/PaymentHistory", method = RequestMethod.GET)
	public String history(Model model) {
		return service.getPaymentHistory(model);
	}

	@RequestMapping( value ="/GetDose", method = RequestMethod.GET)
	public String dose() {
		return "GetDose";
	}

	@RequestMapping( value ="/DisplayDose", method = RequestMethod.GET)
	public String displayDose(String billId, Model model) {
		return service.getDose(billId, model);
	}

	@RequestMapping( value ="/CustomerAuth", method = RequestMethod.POST)
	public String customerLogin(@RequestParam("email") String email, @RequestParam("pass") String pass, Model model) {
		return service.customerLogin(email, pass, model);
	}

	@RequestMapping( value ="/RegisterCustomer", method = RequestMethod.POST)
	public String registerCustomer(@RequestParam HashMap<String, String> customerDetail, Model model) {
		return service.registerCustomer(customerDetail, model);
	}
}
