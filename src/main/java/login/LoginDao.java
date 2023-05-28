package login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.slf4j.Logger;

import utils.AppLogger;
import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class LoginDao {
	static AppLogger appLogger = new AppLogger();
	static Logger logger = appLogger.getLogger();

	public static String isAuthCustmer(String email, String pass) {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		String name = "";
		try {
			logger.info("Authenticating customer");
			pdsm = con.prepareStatement(QueryUtil.Auth_CUSTOMER_QUERY);
			pdsm.setString(1, email);
			pdsm.setString(2, pass);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				name = rs.getString(1);
			}
			if (!name.equals("")) {
				logger.info("Authenticaiton successfully ====>>>");
			}
		} catch (SQLException e) {
			logger.error("cant authenticate customer " + e.getMessage());
			return "";
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return name;
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
		logger.info("Registering customer====>>>>>>>");
		CloseResources.close(con, pdsm);
	}

	public static String isAuthEmployee(String email, String password) {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		String name = "";
		try {
			logger.info("Cheking Employee credentials");
			pdsm = con.prepareStatement(QueryUtil.Auth_EMPLOYEE_QUERY);
			pdsm.setString(1, email);
			pdsm.setString(2, password);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				name = rs.getString("name");
			}
			if (!name.equals("")) {
				logger.info("Emp auth success====>>>>>>>>");
			}
		} catch (SQLException e) {
			logger.error("Employee auth failed===>>>>>>>>");
			e.printStackTrace();
			return "";
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return name;
	}
}
