
public class BankAccount {
	private String accNum;
	private String pin;
	private int balance;
	private String name;

	// コンストラクタ
	public BankAccount(String accNum, String pin, int balance, String name) {
		this.accNum = accNum;
		this.pin = pin;
		this.balance = balance;
		this.name = name;
	}

	// 認証する
	public boolean authenticate(String inputPin) {
		return this.pin.equals(inputPin);
	}

	// 入金
	public void deposit(int amount, boolean showMessage) {
		if (amount > 0) {
			balance += amount;
			if (showMessage) {
				System.out.println(amount + "円を入金しました。");
			}

			DBHelper.updateAccBal(accNum, balance);
		} else {
			System.out.println("入金額に誤りがあります！");
		}
	}

	// 残高が出金より多い場合は引き出し
	public void withdraw(int amount, boolean showMessage) {
		if (amount > 0 && amount <= balance) {
			balance -= amount;
			if (showMessage) {
				System.out.println(amount + "円を引き出しました。");
			}
			DBHelper.updateAccBal(accNum, balance);
		} else if (amount > balance) {
			System.out.println("残高が不足しています");
		} else {
			System.out.println("入力に誤りがあります！");
		}
	}

	// 残高確認
	public void checkBalance() {
		System.out.printf("残高：\u00a5%d\n", balance);
	}

	// 口座番号
	public String getAccNum() {
		return accNum;
	}

	// 口座名義
	public String getName() {
		return name;
	}

	public int getBalance() {
		return balance;
	}

}
