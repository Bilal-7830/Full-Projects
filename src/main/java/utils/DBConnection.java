package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class DBConnection {
	public static Connection createConnection() {
		
		String url="jdbc:postgresql://pgdb.veapp.net:5440/training_2023";
		String user="training";
		String password="training@23";
		
		Connection conn = null;
		
		while(conn==null) {
			Scanner sc =new Scanner(System.in);
			try{
				Class.forName("org.postgresql.Driver");
				conn = DriverManager.getConnection(url, user, password);
			}
			catch (SQLException e) {
				System.out.println(e.getMessage());

				System.out.println("Enter any input to retry:");
				
				sc.nextLine();

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
			finally {
				if(sc!=null) sc.close();
			}
		}

		return conn;
	}
}
