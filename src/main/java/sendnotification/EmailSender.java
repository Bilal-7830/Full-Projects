package sendnotification;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {

	public static void main(String[] args) {

		// SMTP server configuration
		String smtpHost = "localhost";
		String smtpPort = "576";
		String senderEmail = "bilalsaifi94112@gmail.com";
		String senderPassword = "opexeyxcdtqyebak";

		// Recipient's email address
		String recipientEmail = "bilalsaifi24977@gmail.com";

		// Email properties
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", smtpHost);
		properties.put("mail.smtp.port", smtpPort);

		// Create a session with SMTP authentication
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			// Create a default MimeMessage object
			MimeMessage message = new MimeMessage(session);

			// Set From: header field
			message.setFrom(new InternetAddress(senderEmail));

			// Set To: header field
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));

			// Set Subject: header field
			message.setSubject("Hello from JavaMail");

			// Set the actual message
			message.setText("This is a test email from JavaMail.");

			// Send the message
			Transport.send(message);

			System.out.println("Email sent successfully.");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
