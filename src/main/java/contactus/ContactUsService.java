package contactus;

import org.slf4j.Logger;

import utils.AppLogger;
import utils.MailSender;

public class ContactUsService {
	

	public static void contactToAdmin(String name, String email, String message) {
		AppLogger appLogger = new AppLogger();
		Logger logger = appLogger.getLogger();
		MailSender.sendMail("Contact from " + name, message, "bilalsaifi24977@gmail.com");
		logger.info("Successfully contacted to Admin");
	}
}
