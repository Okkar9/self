import java.sql.Connection;
import java.sql.SQLException;

public class DBtest {
	public static void main(String[] args) {
		try (Connection connection = DBconnect.getConnection()) {
			if (connection != null) {
				System.out.println("Connection succeeded!");
			}

		} catch (SQLException e) {
			System.out.println("Connection Faliled" + e.getMessage());
		}
	}

}
