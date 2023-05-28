package sendnotification;

import java.util.List;

import utils.MailSender;

public class SendNotificationService {
	public static List<SendNotificationBean> sendedMailList() {
		List<SendNotificationBean> cusList = SendNotificationDao.getDueList();
		for (SendNotificationBean cusDetail : cusList) {
			MailSender.sendMail("SehatEra Due amount", getMailMessage(cusDetail.getEmail(), cusDetail.getAmount()),
					cusDetail.getEmail());
		}
		return cusList;
	}

	public static String getMailMessage(String email, Float amount) {
		String mailText = "Hi " + email + " \nHope you will be well\n you are reciving "
				+ "this mail\n because you have some due amount of \n Sehat Era pharmacy "
				+ "please \n pay it as soon as possible \n Due Amount is (INR): " + amount;
		return mailText;
	}
}
