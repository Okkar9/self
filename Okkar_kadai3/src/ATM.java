import java.util.HashMap;
import java.util.Map;

//口座番号やユーザーログインの処理
public class ATM {
	private Map<String, BankAccount> accounts = new HashMap<>();

	// 口座登録
	public void addAccount(BankAccount account) {
		accounts.put(account.getAccNum(), account);
	}

	// 口座番号とパスワードでログイン
	public BankAccount login(String accNum, String pin) {
		BankAccount acc = accounts.get(accNum);
		if (acc != null && acc.authenticate(pin))
			return acc;
		return null;
	}

	//送金のため
	public BankAccount getAccount(String accNum) {
		return accounts.get(accNum);
	}
}
