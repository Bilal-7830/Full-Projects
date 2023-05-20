package dose;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class DoseDao {
	public static List<DoseBean> getDose(String billId){
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		List<DoseBean> result = new ArrayList<>();
		try {
			pdsm = con.prepareStatement(QueryUtil.DOSE_QUERY);
			pdsm.setString(1,billId);
			rs = pdsm.executeQuery();
			while(rs.next()) {
				DoseBean doseBean = new DoseBean();
				doseBean.setCatagory(rs.getString("category"));
				doseBean.setMedicineName(rs.getString("medicine_name"));
				doseBean.setDose(rs.getString("dose"));
				result.add(doseBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}
}
