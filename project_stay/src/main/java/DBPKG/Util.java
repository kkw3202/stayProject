package DBPKG;

import java.sql.Connection;
import java.sql.DriverManager;
public class Util {
	public static Connection getConnection() throws Exception{
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/pro_test?serverTimezone=UTC&useSSL=false",
				"root", "1234");
		return con;
	}
}

