package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bill.BillBean;
import sendorder.OrderMedicineBean;

public class MemCache {
	public static Map<String, Float> empSalaryId = new HashMap<>();
	public static List<OrderMedicineBean> orderMedicineList = new ArrayList<>();
	public static Map<String, List<BillBean>> billMap = new HashMap<>();
	public static String customerMail;
	public static String customerName;

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
	}

	public static Map<String, List<BillBean>> retrieveBillListToMemcache() {
		return billMap;
	}

	public static String getCustomerName() {
		return customerName;
	}

	public static void setCustomerName(String customerName) {
		MemCache.customerName = customerName;
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
