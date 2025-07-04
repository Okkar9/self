import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {
	public static List<BankAccount> loadAccountsFromDatabase() {
		List<BankAccount> accounts = new ArrayList<>();
		String query = "SELECT * FROM BankAccounts";

		try (Connection c = DBconnect.getConnection();
				PreparedStatement stmt = c.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {

			// データベースからのデータを取得
			while (rs.next()) {
				String accNum = rs.getString("acc_Id");// 口座番号
				String name = rs.getString("acc_Name");// 名義
				String pin = rs.getString("acc_Pass");// 暗証番号
				int balance = rs.getInt("acc_Bal");// 残高

				accounts.add(new BankAccount(accNum, pin, balance, name));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accounts;
	}

	// 残高更新
	public static void updateAccBal(String accNum, int newBal) {
		String query = "UPDATE BankAccounts SET acc_Bal = ? WHERE acc_Id = ?";

		try (Connection c = DBconnect.getConnection(); PreparedStatement stmt = c.prepareStatement(query)) {

			stmt.setInt(1, newBal);
			stmt.setString(2, accNum);
			stmt.execute();
		}

		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
