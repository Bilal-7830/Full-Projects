package register.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.ui.Model;

import utils.AppLogger;
import utils.DBConnection;
import utils.MailSender;
import utils.QueryUtil;

public class RegisterDao {
	public static boolean registerEmp(Map<String, String> map, Model model) {
		AppLogger appLogger = new AppLogger();
		Logger logger = appLogger.getLogger();
		boolean result = false;
		Connection con = DBConnection.createConnection();
		String name = map.get("name");
		String address = map.get("address");
		String phone = map.get("phone");
		String email = map.get("email");
		String salary = map.get("salary");
		String password = map.get("pass");
		String cpassword = map.get("cpass");
		String empId = name + "#" + phone.substring(6);
		int cnt = 1;
		logger.info("Regestering employee ....>>>>>>>>");
		try {
			PreparedStatement pdsm = con.prepareStatement(QueryUtil.REGISTER_EMP_QUERY);
			pdsm.setString(cnt++, name);
			pdsm.setString(cnt++, email);
			pdsm.setString(cnt++, phone);
			pdsm.setString(cnt++, address);
			pdsm.setString(cnt++, cpassword);
			pdsm.setInt(cnt++, Integer.parseInt(salary));
			pdsm.setString(cnt++, empId);
			result = pdsm.executeUpdate() == 0 ? false : true;
			if (result) {
				MailSender.sendMail("Congratulation!!!", mailText(empId, password, salary), email);
				logger.info("Employee Registered successfully!!");
			}
			return result;
		} catch (SQLException e) {
			logger.error("can't register employee ......./// " + e.getMessage());
			model.addAttribute("errorMessage", e.getMessage());
			return false;
		}
	}

	public static String mailText(String empId, String password, String salary) {
		String text = "We are pleased to inform you \nthat from now you are our full time employee\n you salary will be "
				+ salary + "(INR) per month." + "\nHere are your credeantials \n Employee Id : " + empId
				+ "\n password : " + password + "\nthanks and regards \n Team Sehar Era";
		return text;

	}
}