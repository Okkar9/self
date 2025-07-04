import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException {
		ATM atm = new ATM();
		Scanner sc = new Scanner(System.in);

		// dbから情報を取得
		List<BankAccount> dbAccounts = DBHelper.loadAccountsFromDatabase();
		for (BankAccount acc : dbAccounts) {
			atm.addAccount(acc);
		}

		// 口座番号入力
		System.out.print("口座番号を入力してください：");
		String accNum = sc.nextLine();

		// 暗証番号入力
		String pin;
		while (true) {
			System.out.print("暗証番号を入力してください（4桁）：");
			pin = sc.nextLine();
			if (pin.matches("\\d{4}"))
				break; // 4桁の数字が入力されたらループを抜ける
			System.out.println("入力に誤りがあります。\n4桁数字を入力してください。");
		}

		BankAccount user = atm.login(accNum, pin); // ログインする

		if (user != null) {
			System.out.println("ログイン成功!");
			System.out.println("---------------------------------");
			int choice;
			do {
				showMenu(user.getName());
				System.out.print("取引を選択：");

				// 無効なメニューが選択されないように
				choice = getValidMenuChoice(sc);

				switch (choice) {
				case 1:
					// 預金
					performTransaction(user, sc, "預金");
					System.out.println("---------------------------------");
					break;

				case 2:
					// 引き出し
					performTransaction(user, sc, "引き出し");
					System.out.println("---------------------------------");
					break;

				case 3:
					// 残高照会
					user.checkBalance();
					System.out.println("---------------------------------");
					break;

				case 4:
					// 送金
					performTransfer(atm, user, sc);
					System.out.println("---------------------------------");
					break;

				case 5:
					// 終了
					System.out.println("ご利用ありがとうございました。\nまたのご利用をお待ちしております。^^");
					System.out.println("---------------------------------");
					break;
				}

				System.out.println();
			} while (choice != 5);

		} else {
			System.out.println("ログイン失敗！(口座番号または暗証番号に誤りがあります)");
		}

		sc.close();
	}

	// メニューメソッド
	static void showMenu(String Username) {
		System.out.println("おはようございます 、" + Username + "さん");
		System.out.println("ーーーーメイテック銀行ーーーー");
		System.out.println("１．預金\n２．お引き出し\n３．残高照会\n４．送金\n５．終了");
	}

	// 入金・出金メソッド（共通）
	static void performTransaction(BankAccount user, Scanner sc, String Type) {
		int amt = (int) getValidAmount(sc, Type); // Validate amount input
		if (Type.equals("預金")) {
			user.deposit(amt,true);
		} else {
			user.withdraw(amt,true);
		}
		user.checkBalance();
	}

	// 送金メソッド
	static void performTransfer(ATM atm, BankAccount user, Scanner sc) {
		BankAccount targetAcc = null;
		
		while (true) {
			System.out.print("振込先の口座番号を入力してください\n(キャンセルの場合は0を入力してください)：");
			String targetaccNum = sc.nextLine();

			if (targetaccNum.equals("0")) {
				System.out.println("振込をキャンセルしました。");
				return;
			}

			// 自分の口座への振込も不可
			if (targetaccNum.equals(user.getAccNum())) {
				System.out.println("自分の口座への振込はできません。");
				continue;
			}
			
			// 口座が存在するか確認
			targetAcc = atm.getAccount(targetaccNum);
			if (targetAcc == null) {
				System.out.println("振込先の口座番号は存在しません。");
				continue;
			}
			break;
			
		}
		// 金額を入力
		int amount = (int) getValidAmount(sc, "振込");

		// 残高が不足してないかチェック
		if (user.getBalance() < amount) {
			System.out.println("残高が不足です。振込できません。");
			return;
		}

		// ユーザーから引き出し
		user.withdraw(amount,false);
		// 送金先へ預金
		targetAcc.deposit(amount,false);

		System.out.println("振込が完了しました。");
		System.out.println("振込先：" + targetAcc.getName());
		System.out.println("振込金額：" + amount);
		user.checkBalance();

		// 口座情報更新
		DBHelper.updateAccBal(user.getAccNum(), user.getBalance());
		DBHelper.updateAccBal(targetAcc.getAccNum(), targetAcc.getBalance());

	}

	// メニュー選択の入力バリデーション
	static int getValidMenuChoice(Scanner sc) {
		int choice = -1;
		// 有効なメニューを選択させる
		while (choice < 1 || choice > 5) {
			try {
				choice = Integer.parseInt(sc.nextLine().trim());
				if (choice < 1 || choice > 5) {
					System.out.println("無効な選択です。1～4の数字を入力してください。");
				}
			} catch (NumberFormatException e) {
				System.out.println("数字を入力してください。");
			}
		}
		return choice;
	}

	// 入金額・出金額の入力バリデーション
	static double getValidAmount(Scanner sc, String transactionType) {
		int amount = -1;
		// 正数の数字のみ入力
		while (amount <= 0) {
			System.out.print(transactionType + "金額を入力してください：");
			try {
				amount = Integer.parseInt(sc.nextLine().trim());
				if (amount <= 0) {
					System.out.println("正しい金額を入力してください（正の数）。");
				}
			} catch (NumberFormatException e) {
				System.out.println("数字を入力してください。");
			}
		}
		return amount;
	}

}
