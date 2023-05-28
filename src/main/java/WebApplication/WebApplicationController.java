package WebApplication;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebApplicationController {
	@RequestMapping( value = "/index", method = RequestMethod.GET)
	public String index() {
		return "index";
	}

	@RequestMapping( value = "/Register", method = RequestMethod.GET)
	public String register() {
		return "Register";
	}

	@RequestMapping( value = "/ContactUs", method = RequestMethod.GET)
	public String contactUs() {
		return "ContactUs";
	}
}