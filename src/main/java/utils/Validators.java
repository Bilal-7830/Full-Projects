package utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Validators {

	public static final Pattern MAIL_PATT = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
	public static final Pattern CONTACT_PATT = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[6789]\\d{9}$");
	public static final Pattern PASSWORD_PATT = Pattern
			.compile("(?=.*[A-Z].*)(?=.*[!@#$&*])(?=.*[0-9].*)(?=.*[a-z].*).{8,}");
	public static final Pattern MIN_STRING_PATT = Pattern.compile("^(([a-zA-Z]+\\s)*[a-zA-Z]+){3,60}$");
	public static final Pattern DATE_PATT = Pattern.compile(
			"^(((\\d{4}\\/((0[13578]\\/|1[02]\\/)(0[1-9]|[12]\\d|3[01])|(0[13456789]\\/|1[012]\\/)(0[1-9]|[12]\\d|30)|02\\/(0[1-9]|1\\d|2[0-8])))|((([02468][048]|[13579][26])00|\\d{2}([13579][26]|0[48]|[2468][048])))\\/02\\/29)){0,10}$");

	public static boolean isValid(Pattern pattObj, String str) {
		return pattObj.matcher(str).matches();
	}

	public static boolean isValidAddress(String address) {
		boolean result = false;
		if (address.contains(",")) {

			String[] addressArr = address.replaceAll(" +", "").split(",");

			if (addressArr.length == 4 && addressArr[addressArr.length - 1].length() == 6) {

				result = true;
			}
		}
		return result;
	}

	public static boolean isEmptyNullOrBlank(Map<String, String> map) {
		try {
			System.out.println("checking valadity");
			for (Map.Entry<String, String> entry : map.entrySet()) {
				if (entry.getValue() == null || entry.getValue().isBlank() || entry.getValue().isEmpty() || entry.getValue().equals("")) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
