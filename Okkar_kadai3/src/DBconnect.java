import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	// db接続
	private static final String URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Tokyo&useUnicode=true&characterEncoding=utf8";
	private static final String USER = "root";
	private static final String PASS = "";

	public static Connection getConnection() throws SQLException {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(URL, USER, PASS);

		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found: " + e.getMessage());
			e.printStackTrace();
			throw new SQLException("Driver Not Found", e);
		}
	}
}
