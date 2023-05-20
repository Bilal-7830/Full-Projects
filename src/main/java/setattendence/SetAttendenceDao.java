package setattendence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class SetAttendenceDao {
	public static List<EmpNamePhoneBean> getEmpList() {
		List<EmpNamePhoneBean> result = new ArrayList<>();
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.GET_EMP_NAME_PHONE_QUERY);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				EmpNamePhoneBean empDetail = new EmpNamePhoneBean();
				empDetail.setEmpName(rs.getString("name"));
				String phone = rs.getString("phone_no");
				String empId = rs.getString("name") + "#" + phone.substring(6);
				empDetail.setEmpId(empId);
				result.add(empDetail);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}

	public static boolean isSetAttendence(String[] empIds, String[] attendences) {
		Map<String, Float> hm = new HashMap<>();
		hm.put("present", 1.0f);
		hm.put("half", 0.5f);
		hm.put("absent", 0f);
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		try {
			for (int i = 0; i < empIds.length; i++) {
				String empId = empIds[i];
				float attendence = hm.get(attendences[i]);
				int cnt = 1;
				pdsm = con.prepareStatement(QueryUtil.DB_ATTENDENCE_QUERY);
				pdsm.setString(cnt++, empId);
				pdsm.setDate(cnt++, new java.sql.Date(System.currentTimeMillis()));
				pdsm.setFloat(cnt++, attendence);
				pdsm.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			CloseResources.close(con, null, pdsm);
		}
		return true;
	}
}
