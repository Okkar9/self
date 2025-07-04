package ensyu2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MoneyHandler {
	private static final Set<Integer> ValidCoins = new HashSet<>(Arrays.asList(10,50,100,500));
	private static final int Valid_BankNote = 1000;
	private static final List<Integer> denominations = Arrays.asList(1000,500,100,50,10);
	
	final int maxAmount;
	private int balance = 0;
	
	//コンストラクタ
	public MoneyHandler(int maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	//硬貨を入力
	public boolean insertCoin(int coin) {
		if (ValidCoins.contains(coin) && balance + coin <= maxAmount) {
			balance += coin;
			return true;
		}
		return false;
	}
	
	//紙幣を入力
	public boolean InsertBanknotes(int note) {
		if (note == Valid_BankNote && balance + note <= maxAmount) {
			balance += note;
			return true;
		}
		return false;
	}
	
	//残高
	public int getBalance() {return balance;}
	
	//残高から引く
	public void deduct(int amount) {balance -= amount;}
	
	//返金
	public Map<Integer,Integer> returnChange(){
		Map<Integer, Integer> change = new LinkedHashMap<>();
		int amount = balance;
		for(int denom : denominations) {
			int count = amount/denom;
			if(count>0) change.put(denom, count);
			amount %= denom;
		}
		balance = 0;
		return change;
	}
	
	public static String formatAmount(int amount) {
		return "\u00a5" + amount;
	}
	
	public int getMaxAmount() {
		return maxAmount;
	}
	
}