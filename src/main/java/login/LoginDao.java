package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class LoginDao {
	public static boolean isAuthCustmer(String email, String pass) {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		boolean ans = false;
		try {
			pdsm = con.prepareStatement(QueryUtil.Auth_CUSTOMER_QUERY);
			pdsm.setString(1, email);
			pdsm.setString(2, pass);
			rs = pdsm.executeQuery();
			ans = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			CloseResources.close(con, rs, pdsm);
		}

		return ans;
	}

	public static void registerCustomer(HashMap<String, String> customerDetail) throws SQLException {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		int cnt = 1;
		pdsm = con.prepareStatement(QueryUtil.REGISTER_CUS_QUERY);
		pdsm.setString(cnt++, customerDetail.get("name"));
		pdsm.setString(cnt++, customerDetail.get("address"));
		pdsm.setString(cnt++, customerDetail.get("phone"));
		pdsm.setString(cnt++, customerDetail.get("email"));
		pdsm.setString(cnt++, customerDetail.get("cpass"));
		pdsm.execute();
		CloseResources.close(con, pdsm);
	}

	public static boolean isAuthEmployee(String email, String password) {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		boolean ans = false;
		try {
			pdsm = con.prepareStatement(QueryUtil.Auth_EMPLOYEE_QUERY);
			pdsm.setString(1, email);
			pdsm.setString(2, password);
			rs = pdsm.executeQuery();
			ans = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return ans;
	}
}
