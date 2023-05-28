package sendnotification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class SendNotificationDao {
	public static List<SendNotificationBean> getDueList() {
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		List<SendNotificationBean> result = new ArrayList<SendNotificationBean>();
		try {
			pdsm = con.prepareStatement(QueryUtil.DUE_AMOUNT_QUERY);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				SendNotificationBean data = new SendNotificationBean();
				data.setEmail(rs.getString("email"));
				data.setAmount(rs.getFloat("total_amount"));
				data.setSuccess(true);
				result.add(data);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}
}
