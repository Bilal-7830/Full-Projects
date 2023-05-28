package sendnotification;

public class SendNotificationBean {
	private String email;
	private float amount;
	private boolean success;
	public String getEmail() {
		return email;
	}
	public void setEmail(String emial) {
		this.email = emial;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
