package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import bill.BillBean;
import sendorder.OrderMedicineBean;

public class MemCache {
	public static Map<String, Float> empSalaryId = new HashMap<>();
	public static List<OrderMedicineBean> orderMedicineList = new ArrayList<>();
	public static Map<String, List<BillBean>> billMap = new HashMap<>();
	public static String customerMail;
	public static String name;
	public static int otp;
	public static String employeeMail;
	public static float empAttendece = 0f;
	public static Map<String, String> registerEmployeeMap = new HashMap<String, String>();
	public static Map<String, String> billIdMap = new HashMap<>();
	public static String empPassword;

	public static void SetEmpDetails(String empName, String email, String password) {
		name = empName;
		employeeMail = email;
		empPassword = password;

	}

	public static String getEmpPassword() {
		return empPassword;
	}

	public static String getEmpEmail() {
		return employeeMail;
	}

	public static Map<String, String> getBillIdMap() {
		return billIdMap;
	}

	public static void setBillIdMap(Map<String, String> billIdMap) {
		MemCache.billIdMap = billIdMap;
	}

	public static Map<String, String> getRegisterEmployeeMap() {
		return registerEmployeeMap;
	}

	public static int getOtp() {
		return otp;
	}

	public static void setOtp(int otp) {
		MemCache.otp = otp;
	}

	public static void setRegisterEmployeeMap(Map<String, String> registerEmployeeMap) {
		MemCache.registerEmployeeMap = registerEmployeeMap;
	}

	public static float getEmpAttendece() {
		return empAttendece;
	}

	public static void setEmpAttendece(float empAttendece) {
		MemCache.empAttendece = empAttendece;
	}

	public static int totalMedicineBillAmount;

	public static void addToMemcache(String empId, Float amount) {
		empSalaryId.put(empId, amount);
	}

	public static Map<String, Float> retrieveFromMemCache() {
		return empSalaryId;
	}

	public static void addOrderMedicineToMemCache(OrderMedicineBean objMedicineBean) {
		orderMedicineList.add(objMedicineBean);
	}

	public static List<OrderMedicineBean> retrieveOrderMedicineFromMemCache() {
		return orderMedicineList;
	}

	public static void addBillListToMemcache(String billId, List<String> medicines, List<Integer> quantities,
			List<Float> prices, List<String> catagories, List<String> doses) {
		AppLogger appLogger = new AppLogger();
		Logger logger = appLogger.getLogger();
		List<BillBean> medicineDetails = new ArrayList<BillBean>();
		int total = 0;
		for (int i = 0; i < medicines.size(); i++) {
			if (quantities.get(i) != null) {
				BillBean billBean = new BillBean();
				billBean.setCategory(catagories.get(i));
				billBean.setMedicineName(medicines.get(i));
				billBean.setPrice(prices.get(i));
				billBean.setQuantity(quantities.get(i));
				billBean.setTotalPrice(prices.get(i) * quantities.get(i));
				billBean.setDose(doses.get(i));
				total += prices.get(i) * quantities.get(i);
				medicineDetails.add(billBean);
			}
		}
		setTotalMedicineBillAmount(total);
		billMap.put(billId, medicineDetails);
		logger.info("Bill Id stored in memcache = " + billId);
	}

	public static Map<String, List<BillBean>> retrieveBillListToMemcache() {
		return billMap;
	}

	public static String getName() {
		return name;
	}

	public static void setName(String customerName) {
		MemCache.name = customerName;
		System.out.println("name added to memcache");
	}

	public static int getTotalMedicineBillAmount() {
		return totalMedicineBillAmount;
	}

	public static void setTotalMedicineBillAmount(int totalMedicineBillAmount) {
		MemCache.totalMedicineBillAmount = totalMedicineBillAmount;
	}

	public static String getCustomerMail() {
		return customerMail;
	}

	public static void setCustomerMail(String customerMail) {
		MemCache.customerMail = customerMail;
	}
}
