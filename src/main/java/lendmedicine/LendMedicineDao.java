package lendmedicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class LendMedicineDao {
	public static void lendMedicineInDB(String billId,float amount) {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		int cnt = 1;
		try {
			pdsm = con.prepareStatement(QueryUtil.LEND_MEDICINE_QUERY);
			pdsm.setFloat(cnt++,amount);
			pdsm.setString(cnt++, billId);
			pdsm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			CloseResources.close(con, pdsm);
		}
	}
}
