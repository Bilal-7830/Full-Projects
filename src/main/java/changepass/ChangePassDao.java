package changepass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class ChangePassDao {
	public static boolean isUpdated(String email, String oldPass, String newPass) {
		boolean ans = false;
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		int cnt = 1;
		try {
			pdsm = con.prepareStatement(QueryUtil.UPDATE_PASSWORD_QUERY);
			pdsm.setString(cnt++, newPass);
			pdsm.setString(cnt++, email);
			pdsm.setString(cnt++, oldPass);
			int col = pdsm.executeUpdate();
			ans = col == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, pdsm);
		}
		return ans;
	}
}
