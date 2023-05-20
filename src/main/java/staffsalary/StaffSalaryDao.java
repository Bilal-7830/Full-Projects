package staffsalary;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.CloseResources;
import utils.DBConnection;
import utils.MemCache;
import utils.QueryUtil;

public class StaffSalaryDao {
	public static List<EmpSalaryBean> getSalaryDetails() {
		List<EmpSalaryBean> result = new ArrayList<>();
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.STAFF_SALARY_QUERY);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				int cnt = 1;
				EmpSalaryBean salaryBean = new EmpSalaryBean();
				float presents = rs.getFloat(cnt++);
				System.out.print("presents = " + presents);
				salaryBean.setPresents(presents);
				String empId = rs.getString(cnt++);
				salaryBean.setEmpId(empId);
				System.out.print("  empId = " + rs.getString("emp_id"));
				int salary = rs.getInt(cnt++);
				System.out.print("  salary = " + salary);
				salaryBean.setBaseSalary(salary);
				float payableSalary = presents * salary / 26;
				System.out.println("  payable salary = " + payableSalary);
				salaryBean.setAmountToPay(payableSalary);
				MemCache.addToMemcache(empId, payableSalary);
				result.add(salaryBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}

	public static void logInDb(String emp_id, float amount, Date date) throws SQLException {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		
			pdsm = con.prepareStatement(QueryUtil.SALARY_LOGGER_QUERY);
			int cnt = 1;
			pdsm.setString(cnt++, emp_id);
			pdsm.setBoolean(cnt++, true);
			pdsm.setFloat(cnt++, amount);
			pdsm.setDate(cnt++, date);
			pdsm.execute();
			System.out.println("payrole logged successfully");

			CloseResources.close(con, pdsm);
	}

}
