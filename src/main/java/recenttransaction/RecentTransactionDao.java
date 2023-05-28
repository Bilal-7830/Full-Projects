package recenttransaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class RecentTransactionDao {
	public static List<RecentTransactionBean> getRecentTransaction(Date date) {
		List<RecentTransactionBean> result = new ArrayList<RecentTransactionBean>(); 
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.RecentTransactionQuery);
			pdsm.setDate(1, date);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				RecentTransactionBean bean = new RecentTransactionBean();
				bean.setEmail(rs.getString("email"));
				bean.setBillId(rs.getString("bill_pid"));
				bean.setAmount(rs.getFloat("amount"));
				bean.setLendAmount(rs.getFloat("lend_amount"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}
	public static List<RecentTransactionBean> getRecentCustomerTransaction(String email) {
		List<RecentTransactionBean> result = new ArrayList<RecentTransactionBean>(); 
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.RECENT_CUSTOMER_TRANSACTION_QUERY);
			pdsm.setString(1, email);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				RecentTransactionBean bean = new RecentTransactionBean();
				bean.setEmail(rs.getString("email"));
				bean.setBillId(rs.getString("bill_pid"));
				bean.setAmount(rs.getFloat("amount"));
				bean.setLendAmount(rs.getFloat("lend_amount"));
				bean.setDate(rs.getDate("b_date"));
				result.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}
	
}
