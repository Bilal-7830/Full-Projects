package bill;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;

import sendorder.OrderMedicineBean;
import utils.AppLogger;
import utils.CloseResources;
import utils.DBConnection;
import utils.QueryUtil;

public class BillDao {
	static Connection con = null;
	static AppLogger appLogger = new AppLogger();
	static Logger logger = appLogger.getLogger();

	public static void addBillToDB(String email, String billId, int total, Date date) {
		con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		int cnt = 1;
		try {
			logger.info("Adding bill to DB");
			pdsm = con.prepareStatement(QueryUtil.ADD_BILL_TO_DB_QUERY);
			pdsm.setString(cnt++, email);
			pdsm.setString(cnt++, billId);
			pdsm.setFloat(cnt++, total);
			pdsm.setDate(cnt++, date);
			pdsm.executeUpdate();
			logger.info("Added bill in DB Successfully");
		} catch (SQLException e) {
			logger.info("Failed to add bill in db" + e.getMessage());
		}
	}

	public static void addBillMedicinesToDB(List<BillBean> data, String email, String billId) {
		PreparedStatement pdsm = null;
		System.out.println("db dose updated");
		try {
			logger.info("Adding medicines of bill in DB");
			for (BillBean billBean : data) {
				int cnt = 1;
				pdsm = con.prepareStatement(QueryUtil.ADD_BILL_MEDICINE_TO_DB_QUERY);
				pdsm.setString(cnt++, billBean.getCategory());
				pdsm.setString(cnt++, billBean.getMedicineName());
				pdsm.setInt(cnt++, billBean.getQuantity());
				pdsm.setString(cnt++, email);
				pdsm.setString(cnt++, billId);
				pdsm.setFloat(cnt++, billBean.getPrice());
				pdsm.setFloat(cnt++, billBean.getTotalPrice());
				pdsm.setString(cnt++, billBean.getDose());
				pdsm.executeUpdate();
			}
			logger.info("Bill medicines added in DB successfully");
		} catch (SQLException e) {
			logger.error("Failed to adding medicines in db " + e.getMessage());
		} finally {
			CloseResources.close(con, pdsm);
		}
	}

	public static float calculateFullDaySale(Date date) {
		con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		ResultSet rs = null;
		float sale = 0;
		int cnt = 1;
		try {
			logger.info("calculating day sale");
			pdsm = con.prepareStatement(QueryUtil.FULL_DAY_SALE_QUERY);
			pdsm.setDate(cnt, date);
			rs = pdsm.executeQuery();
			rs.next();
			sale = rs.getFloat(cnt);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseResources.close(con, rs, pdsm);
		}
		logger.info("sale callculated successfully sale amount is : " + sale);
		return sale;
	}

	public static void updateBillMedicineQntINDB(List<BillBean> data) {
		System.out.println("updateBillMedicineQntINDB called");
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		try {
			for (BillBean bill : data) {
				int cnt = 1;
				String query = QueryUtil.GET_UPDATE_MEDICINE_QUANTITY_QUERY(-bill.getQuantity());
				pdsm = con.prepareStatement(query);
				pdsm.setString(cnt++, bill.getMedicineName());
				pdsm.setString(cnt++, bill.getCategory());
				pdsm.execute();
			}
			logger.info("updated -----updateBillMedicineQntINDB");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			CloseResources.close(con, pdsm);
		}
	}

	public static void updateOrderMedicineQntINDB(List<OrderMedicineBean> data) {
		System.out.println("updateOrderMedicineQntINDB");
		Connection con = DBConnection.createConnection();
		PreparedStatement pdsm = null;
		try {
			for (OrderMedicineBean bill : data) {
				int cnt = 1;
				String query = QueryUtil.GET_UPDATE_MEDICINE_QUANTITY_QUERY(bill.getQuantity());
				pdsm = con.prepareStatement(query);
				pdsm.setString(cnt++, bill.getMedicineName());
				pdsm.setString(cnt++, bill.getCategory());
				pdsm.execute();
			}
			logger.info("updated -----updateOrderMedicineQntINDB");
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} finally {
			CloseResources.close(con, pdsm);
		}
	}
}
