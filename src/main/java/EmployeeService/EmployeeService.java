package EmployeeService;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import availablemedicines.AvailableMedicineDao;
import bill.BillBean;
import bill.BillDao;
import changepass.ChangePassDao;
import lendmedicine.LendMedicineDao;
import login.LoginDao;
import nearexpired.MedicineDetailBean;
import recenttransaction.RecentTransactionBean;
import recenttransaction.RecentTransactionDao;
import salaryattendence.SalaryAttendenceDao;
import utils.MailSender;
import utils.MemCache;
import utils.RandomNumber;
import utils.ResponseMessages;

public class EmployeeService {
	public String getEmployeeFeature(Model model) {
		String name = MemCache.getName();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		model.addAttribute("welcome", "Welcome " + name);
		System.out.println("Emp name = " + name);
		return "EmployeeFeature";
	}

	public String sell(Model model) {
		List<MedicineDetailBean> data = AvailableMedicineDao.availableMedicines();
		model.addAttribute("data", data);
		return "Sell";
	}

	public String displayBillId(List<Integer> quantities, List<String> medicines, List<Float> prices,
			List<String> catagories, List<String> doses, Model model) {
		String billId = UUID.randomUUID().toString();
		MemCache.addBillListToMemcache(billId, medicines, quantities, prices, catagories, doses);
		model.addAttribute("billId", "Bill Id = " + billId + "  (please copy it for create bill)");
		model.addAttribute("goback", "Go back to Feature page and Generate bill");
		return "Sell";
	}

	public String validateOtp(int otp, Model model) {
		if (otp != MemCache.getOtp()) {
			model.addAttribute("errorMessage", "OTP not matched try again");
			return "BillOTP";
		}
		Map<String, String> map = MemCache.getBillIdMap();
		String billId = map.get("billId");
		String email = map.get("email");
		Map<String, List<BillBean>> result = MemCache.retrieveBillListToMemcache();
		List<BillBean> data = result.get(billId);
		int total = MemCache.getTotalMedicineBillAmount();
		BillDao.addBillToDB(email, billId, total, new Date(System.currentTimeMillis()));
		BillDao.addBillMedicinesToDB(data, email, billId);
		BillDao.updateBillMedicineQntINDB(data);
		if (!result.containsKey(billId)) {
			model.addAttribute("errorMessage", "Bill id not exists try again");
			return "GenerateBill";
		}
		model.addAttribute("data", data);
		model.addAttribute("total", "Amount to Pay(INR) = " + total);
		return "DisplayBill";
	}

	public String lendMedicine(Map<String, String> map, Model model) {
		String billId = map.get("billId");
		String email = map.get("email");
		float amount = Float.parseFloat(map.get("amount"));
		if (!MemCache.retrieveBillListToMemcache().containsKey(billId) || !email.equals(MemCache.getCustomerMail())) {
			model.addAttribute("errorMessage", "Bill id or email not exists try again");
			return "LendForm";
		}
		LendMedicineDao.lendMedicineInDB(billId, amount);
		model.addAttribute("action", "Medicine lended success");
		return "success";
	}

	public String getRecentTransaction(Model model) {
		List<RecentTransactionBean> data = RecentTransactionDao
				.getRecentTransaction(new Date(System.currentTimeMillis()));
		model.addAttribute("data", data);
		return "RecentTransaction";
	}

	public String authEmp(String email, String pass, Model model) {
		String name = LoginDao.isAuthEmployee(email, pass);
		MemCache.SetEmpDetails(name, email, pass);
		if (name == null || name.length() <= 0) {
			model.addAttribute("errorMessage", ResponseMessages.ACCESS_DENIED);
			return "EmployeeLogin";
		}
		return "redirect:/EmployeeFeature";
	}

	public String seePresents(Model model) {
		float presents = SalaryAttendenceDao.getAttendence(MemCache.getEmpEmail());
		MemCache.setEmpAttendece(presents);
		model.addAttribute("presents", "Your presents is : " + presents);
		return "EmployeeFeature";
	}

	public String getSalary(Model model) {
		float presents = MemCache.getEmpAttendece();
		if (presents == 0) {
			presents = SalaryAttendenceDao.getAttendence(MemCache.getEmpEmail());
		}
		float salary = SalaryAttendenceDao.getDaySalary(MemCache.getEmpEmail());
		float finalSalary = salary * presents;
		model.addAttribute("amount", "Total amount = " + finalSalary);
		return "EmployeeFeature";
	}

	public String changePass(String oldPass, String password, String cPassword, Model model) {
		if (!password.equals(cPassword)) {
			model.addAttribute("errorMessage", "Confirm password doesn't match to password");
			return "ChangePassword";
		}
		if(!oldPass.equals(MemCache.getEmpPassword())) {
			model.addAttribute("errorMessage", "Old Password not matched");
			return "ChangePassword";
		}
		boolean isUpdated = ChangePassDao.isUpdated(MemCache.getEmpEmail(), oldPass, cPassword);
		if (!isUpdated) {
			model.addAttribute("errorMessage", ResponseMessages.GENERIC);
			return "ChangePassword";
		}
		model.addAttribute("action", "Password changed successfully!");
		return "success";
	}

	public String setBillOTP(Map<String, String> map, Model model) {
		MemCache.setBillIdMap(map);
		String cusEmail = map.get("email");
		MemCache.setCustomerMail(cusEmail);
		int otp = RandomNumber.generateRandomNumber();
		MemCache.setOtp(otp);
		MailSender.sendMail("Sehat Era OTP", ResponseMessages.OTP_BILL + String.valueOf(otp), cusEmail);
		return "BillOTP";
	}

}