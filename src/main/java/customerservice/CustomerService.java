package customerservice;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;

import dose.DoseBean;
import dose.DoseDao;
import lendbalance.LendBalanceDao;
import login.LoginDao;
import recenttransaction.RecentTransactionBean;
import recenttransaction.RecentTransactionDao;
import utils.MailSender;
import utils.MemCache;
import utils.RandomNumber;
import utils.ResponseMessages;

public class CustomerService {
	public String getCustomerFeature(Model model) {
		String name = MemCache.getName();
		name = name.substring(0, 1).toUpperCase() + name.substring(1);
		model.addAttribute("welcome", "Welcome " + name);
		return "CustomerFeature";
	}

	public String getLendBalance(Model model) {
		float amount = LendBalanceDao.lendBalance();
		model.addAttribute("amount", "Amount to be paid = " + amount);
		return "CustomerFeature";
	}

	public String getPaymentHistory(Model model) {
		List<RecentTransactionBean> data = RecentTransactionDao
				.getRecentCustomerTransaction(MemCache.getCustomerMail());
		model.addAttribute("data", data);
		return "RecentCusTransaction";
	}

	public String getDose(String billId, Model model) {
		List<DoseBean> data = DoseDao.getDose(billId);
		if (data == null || data.size() == 0) {
			model.addAttribute("errorMessage", "Invalid bill Id");
			return "GetDose";
		}
		model.addAttribute("data", data);
		return "DisplayDose";
	}

	public String customerLogin(String email, String password, Model model) {
		String name = LoginDao.isAuthCustmer(email, password);
		MemCache.setName(name);
		MemCache.setCustomerMail(email);
		if (name == null || name.length() <= 0) {
			model.addAttribute("errorMessage", ResponseMessages.ACCESS_DENIED);
			return "CustomerLogin";
		}
		return "redirect:/CustomerFeature";
	}

	public String registerCustomer(HashMap<String, String> customerDetail, Model model) {
		try {
			LoginDao.registerCustomer(customerDetail);
		} catch (SQLException e) {
			model.addAttribute("errorMessage", ResponseMessages.DUPLICATE_VALUE);
			e.printStackTrace();
			return "RegisterCustomer";
		}
		model.addAttribute("action", "Customer Added successfully");
		return "success";
	}

}
