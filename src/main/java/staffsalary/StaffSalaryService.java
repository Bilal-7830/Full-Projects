package staffsalary;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import utils.MemCache;

public class StaffSalaryService {
	public static void dBLogger(List<String>empIds) throws SQLException {
		Map<String,Float>idsAmount = MemCache.retrieveFromMemCache();
		for(String empId: empIds) {
			float amount = idsAmount.get(empId);
			System.out.println("empId = "+empId+" amount = "+amount);
			StaffSalaryDao.logInDb(empId, amount, new Date(System.currentTimeMillis()));
		}
	}
}
