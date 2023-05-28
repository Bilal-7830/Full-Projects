package utils;

public class QueryUtil {
	public static final String REGISTER_EMP_QUERY = "insert into m_employee_details values (?,?,?,?,?,?,?)";
	public static final String NEAR_EXPIRE_QUERY = "select medicine_name ,qnty ,price ,exp_date ,category from me_medicine_store where exp_date <= ? order by exp_date asc";
	public static final String GENERATE_ORDERS_QUERY = "select category, medicine_name, qnty,order_quantity,price from me_medicine_store where min_quantity > qnty";
	public static final String GET_EMP_NAME_PHONE_QUERY = "select name, phone_no from m_employee_details";
	public static final String DB_ATTENDENCE_QUERY = "insert into emp_attendence values(?,?,?)";
	public static final String STAFF_SALARY_QUERY = "SELECT SUM(a.attendence), a.emp_id, SUM(e.salary) AS total_salary FROM emp_attendence AS a LEFT JOIN m_employee_details AS e ON a.emp_id = e.emp_id GROUP BY a.emp_id";
	public static final String SALARY_LOGGER_QUERY = "insert into m_salary_loger values(?,?,?,?)";
	public static final String AVAILABLE_MEDICINES_QUERY = "select medicine_name ,qnty ,price ,exp_date ,category from me_medicine_store order by medicine_name";
	public static final String ADD_BILL_TO_DB_QUERY = "insert into m_bill_details values(?,?,?,?)";
	public static final String ADD_BILL_MEDICINE_TO_DB_QUERY = "insert into m_medicine_bill values(?,?,?,?,?,?,?,?)";
	public static final String FULL_DAY_SALE_QUERY = "select sum(amount) from m_bill_details where b_date = ?";
	public static final String LEND_MEDICINE_QUERY = "update m_bill_details set lend_amount = ? where bill_pid = ?";
	public static final String RecentTransactionQuery = "select email,bill_pid,amount,b_date,lend_amount from m_bill_details where b_date = ?";
	public static final String LEND_BALANCE_QUERY = "select sum(lend_amount) from m_bill_details where email = ?";
	public static final String RECENT_CUSTOMER_TRANSACTION_QUERY = "select email,bill_pid,amount,lend_amount, b_date from m_bill_details where email = ?";
	public static final String DOSE_QUERY = "Select category,medicine_name,dose from m_medicine_bill where bill_id = ?";

	public static final String GET_UPDATE_MEDICINE_QUANTITY_QUERY(int qnt) {
		return "update me_medicine_store set qnty = qnty + " + qnt + "where medicine_name = ? and category = ?";
	}

	public static final String Auth_CUSTOMER_QUERY = "select name from m_customer_details where email = ? and passward = ?";
	public static final String REGISTER_CUS_QUERY = "insert into m_customer_details values(?,?,?,?,?)";
	public static final String Auth_EMPLOYEE_QUERY = "select name from m_employee_details where email = ? and password = ?";
	public static final String DUE_AMOUNT_QUERY = "select email,sum(lend_amount) as total_amount  from m_bill_details group by email having sum(lend_amount) > 0";
	public static final String GET_EMP_ATTENDENCE_QUERY = "select sum(attendence) from emp_attendence group by emp_id having emp_id = (select emp_id from m_employee_details where email = ?)";
	public static final String GET_EMP_SALARY_QUERY = "select salary/26 from m_employee_details where email = ?";
	public static final String UPDATE_PASSWORD_QUERY = "update m_employee_details set password = ? where email = ? and password = ?";
}