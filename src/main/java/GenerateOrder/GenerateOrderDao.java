package GenerateOrder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;

import utils.AppLogger;
import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class GenerateOrderDao {
	public static List<OrderDetailBean> generateOrderList() {
		AppLogger appLogger = new AppLogger();
		Logger logger = appLogger.getLogger();
		logger.info("Generating orders ../../..-./--./-==>>>");
		List<OrderDetailBean> result = new ArrayList<>();
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		try {
			pdsm = con.prepareStatement(QueryUtil.GENERATE_ORDERS_QUERY);
			rs = pdsm.executeQuery();
			while (rs.next()) {
				OrderDetailBean medicineDetail = new OrderDetailBean();
				medicineDetail.setMedicineName(rs.getString("medicine_name"));
				System.out.print("medicine_name = " + rs.getString("medicine_name"));
				medicineDetail.setAvailableQuantity(rs.getInt("qnty"));
				System.out.print(" qnty = " + rs.getInt("qnty"));
				medicineDetail.setCategory(rs.getString("category"));
				System.out.print(" category = " + rs.getString("category"));
				medicineDetail.setOrderQuantity(rs.getInt("order_quantity"));
				System.out.println(" order quantity = "+rs.getInt("order_quantity"));
				medicineDetail.setPrice(rs.getFloat("price"));
				System.out.println("price = "+rs.getFloat("price"));
				result.add(medicineDetail);
			}
			logger.info("Order Generated successfully!");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		return result;
	}
}
