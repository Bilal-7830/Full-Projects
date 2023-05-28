package utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;

public class MailSender {
	public static void sendMail(String subject, String msg,String to) {
		AppLogger appLogger = new AppLogger();
		Logger logger = appLogger.getLogger();
		String email = "bilalsaifi94112@gmail.com";
		String password = "opexeyxcdtqyebak";

		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
		properties.put("mail.smtp.port", "587");

		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(email, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(email));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(msg);
			Transport.send(message);
			System.out.println("Mail sended successfully");

		} catch (MessagingException e) {
			logger.error("Can't send mail to "+to+"  =======>>>"+e.getMessage());
			System.out.println(e.getMessage());
		}
	}
}
