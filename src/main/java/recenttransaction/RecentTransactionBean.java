package recenttransaction;

import java.sql.Date;

public class RecentTransactionBean {
	private String email;
	private String billId;
	private float amount;
	private float lendAmount;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBillId() {
		return billId;
	}

	public void setBillId(String billId) {
		this.billId = billId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getLendAmount() {
		return lendAmount;
	}

	public void setLendAmount(float lendAmount) {
		this.lendAmount = lendAmount;
	}

}
