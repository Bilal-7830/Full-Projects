package recenttransaction;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	public static void main(String[] args) {
		// email ID of Recipient.
		String recipient = "alidanish7818@gmail.com";

		// email ID of Sender.
		String sender = "bilalsaifi24977@gmail.com";
		String password = "Bsaifi180@"; // Replace with your email password

		// SMTP server details
		String host = "smtp.gmail.com";
		int port = 587;

		// Getting system properties
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", host);
		properties.setProperty("mail.smtp.port", String.valueOf(port));
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");

		// creating session object to get properties
		Session session = Session.getDefaultInstance(properties);

		try {
			// MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From Field: adding sender's email to from field.
			message.setFrom(new InternetAddress(sender));

			// Set To Field: adding recipient's email to to field.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

			// Set Subject: subject of the email
			message.setSubject("Bhaiya re allah se dro");

			// set body of the email.
			message.setText("Amzad bhai light jala do");

			// Send email.
			Transport.send(message, sender, password);
			System.out.println("Mail successfully sent");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

}
