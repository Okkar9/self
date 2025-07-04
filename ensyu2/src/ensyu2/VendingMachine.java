package ensyu2;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
	private Map<Integer, Drink> inventory = new HashMap<>();
	MoneyHandler moneyHandler;

	public VendingMachine(int maxMoney) {
		moneyHandler = new MoneyHandler(maxMoney);
		// ドリンク初期設定
		inventory.put(1 , new Drink("Cola", 130, 10));
		inventory.put(2 , new Drink("Monster", 230, 5));
		inventory.put(3 , new Drink("Coffee", 110, 15));
		inventory.put(4 , new Drink("Lemon Tea", 120, 10));
		inventory.put(5 , new Drink("Red Bull", 230, 8));
		inventory.put(6 , new Drink("水", 100, 20));
	}

	//在庫表示
	public void showStock() {
		System.out.println("販売可能なドリンク：");
		for (var entry : inventory.entrySet()) {
			Drink d = entry.getValue();
			System.out.printf("%d. %s - \u00a5%d - 在庫数：%d\n ", 
					entry.getKey(), d.getName(), d.getPrice(), d.getStock());
		}
	}

	//
	public boolean buyDrink(int id) {
        Drink d = inventory.get(id);
        if (d == null || !d.isAvailable()) {
            System.out.println("売り切れです");
            return false;
        }

        if (moneyHandler.getBalance() >= d.getPrice()) {
            moneyHandler.deduct(d.getPrice());
            d.reduceStock();
            System.out.println("売上：" + d.getName());
            return true;
        } else {
            System.out.println("残高が足りないです");
            return false;
        }
    }

    public void showBalance() {
        System.out.println("残高： " + MoneyHandler.formatAmount(moneyHandler.getBalance()));
    }

    public void refund() {
        Map<Integer, Integer> change = moneyHandler.returnChange();
        System.out.println("返金：");
        for (Map.Entry<Integer, Integer> entry : change.entrySet()) {
            System.out.printf("\u00a5%d  × %d\n", entry.getKey(), entry.getValue());
        }
    }

    public boolean insertCoin(int coin) {
        return moneyHandler.insertCoin(coin);
    }

    public boolean insertBanknote(int note) {
        return moneyHandler.InsertBanknotes(note);
    }
    
	public int getMaxAmount() {
		return moneyHandler.getMaxAmount();
	}
}