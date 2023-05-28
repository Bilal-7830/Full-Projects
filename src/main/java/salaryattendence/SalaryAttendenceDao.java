package salaryattendence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DBConnection;
import utils.QueryUtil;

public class SalaryAttendenceDao {
	public static float getAttendence(String email) {
		float attendence = 0f;
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.GET_EMP_ATTENDENCE_QUERY);
			pdsm.setString(1, email);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				attendence = rs.getFloat(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return attendence;
	}
	public static float getDaySalary(String email) {
		float salry = 0f;
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.GET_EMP_SALARY_QUERY);
			pdsm.setString(1, email);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				salry = rs.getFloat(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return salry;
	}
}
