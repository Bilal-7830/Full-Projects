package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CloseResources {
	public static void close(Connection con, ResultSet rs, PreparedStatement pdsm) {
		try {
			if (con != null) {
				con.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (pdsm != null) {
				pdsm.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(Connection con, PreparedStatement pdsm) {
		try {
			if (con != null) {
				con.close();
			}
			if (pdsm != null) {
				pdsm.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
