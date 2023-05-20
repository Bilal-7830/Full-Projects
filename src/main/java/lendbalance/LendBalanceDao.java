package lendbalance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.CloseResources;
import utils.DBConnection;
import utils.MemCache;
import utils.QueryUtil;

public class LendBalanceDao {
	public static float lendBalance() {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		float amount = 0;
		try {
			pdsm = con.prepareStatement(QueryUtil.LEND_BALANCE_QUERY);
			pdsm.setString(1, MemCache.getCustomerMail());
			rs = pdsm.executeQuery();
            rs.next();
            amount = rs.getFloat(1);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			CloseResources.close(con, rs, pdsm);
		}
		System.out.println(amount);
		return amount;
	}
}
