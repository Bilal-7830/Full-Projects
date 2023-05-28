package nearexpired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;

import utils.AppLogger;
import utils.DBConnection;
import utils.QueryUtil;

public class NearExpiredDao {
	public static List<MedicineDetailBean> nearExpiredMedicines() {
		AppLogger appLogger = new AppLogger();
		Logger logger = appLogger.getLogger();
		List<MedicineDetailBean> result = new ArrayList<>();
		Connection con = DBConnection.createConnection();
		Date kini = new Date();
		java.sql.Date expDate = new java.sql.Date(kini.getTime() + 90l * 24l * 60l * 60l * 1000l);
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.NEAR_EXPIRE_QUERY);
			pdsm.setDate(1, expDate);
			rs = pdsm.executeQuery();
			logger.info("Getting nearly expire medicines=../.../......");
			while (rs.next()) {
				MedicineDetailBean medicineDetail = new MedicineDetailBean();
				medicineDetail.setMedicineName(rs.getString("medicine_name"));

				medicineDetail.setQuantity(rs.getInt("qnty"));

				medicineDetail.setPricePerUnit(rs.getFloat("price"));

				medicineDetail.setExpiryDate(rs.getDate("exp_date"));

				medicineDetail.setCatagory(rs.getString("category"));

				result.add(medicineDetail);
			}
			if(result != null) {
				logger.info("List Generated successfullu");
			}
		} catch (SQLException e) {
			logger.error("Cant get results" + e.getMessage());
		} finally {
			try {
				con.close();
				rs.close();
				pdsm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return result;
	}

}
