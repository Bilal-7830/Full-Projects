package register.employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.ui.Model;

import utils.DBConnection;
import utils.QueryUtil;

public class RegisterDao {
	public static boolean registerEmp(Map<String, String> map, Model model) {
		Connection con = DBConnection.createConnection();
		String name = map.get("name");
		String address = map.get("address");
		String phone = map.get("phone");
		String email = map.get("email");
		String salary = map.get("salary");
		String password = map.get("pass");
		String cpassword = map.get("cpass");
		String empId = name+"#"+phone.substring(6);
		int cnt = 1;
		try {
			PreparedStatement pdsm = con.prepareStatement(QueryUtil.REGISTER_EMP_QUERY);
			pdsm.setString(cnt++, name);
			pdsm.setString(cnt++, email);
			pdsm.setString(cnt++, phone);
			pdsm.setString(cnt++, address);
			pdsm.setString(cnt++, cpassword);
			pdsm.setInt(cnt++, Integer.parseInt(salary));
			pdsm.setString(cnt++, empId);
			System.out.println("Insertion into db-->>>");
			return pdsm.executeUpdate() == 0 ? false : true;
		} catch (SQLException e) {
			model.addAttribute("errorMessage", e.getMessage());
			System.out.println(e.getMessage());
			return false;
		}
	}
}