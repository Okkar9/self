package ensyu2;

public class Drink {
	private String name;
	private int price;
	private int stock;
	
	//コンストラクタ
	public Drink(String name, int price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}
	
	//ドリンク名
	public String getName() {return name;}
	//値段
	public int getPrice() {return price;}
	//残数
	public int getStock() {return stock;}
	//減数
	public void reduceStock() {if (stock > 0) stock--;}
	//売り切れかチェック
	public Boolean isAvailable() {return stock > 0;}
}
