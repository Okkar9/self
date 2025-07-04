import java.util.ArrayList;

public class player {
	private String name;	//選手の名前
	private ArrayList<Hole> holes;	//各ホールの情報
	
	//コンストラクタ
	public player(String name) {
		this.name = name; 
		this.holes = new ArrayList<>();	//初期化
	}
	
	public void addHole(Hole hole) {
		holes.add(hole);
	}
	
	//選手の名前を返す
	public String getName(){
		return name;
	}
	
	//合計(打数)を計算する
	public int getTotalStrokes() {
		int total = 0;
		for(Hole hole : holes) {
			total += hole.getStrokes();
		}
		return total;
	}
	
	//合計パーを計算する
	public int getTotalPar() {
		int total = 0;
		for(Hole hole : holes) {
			total += hole.getPar();
		}
		return total;
	}
	
	//総合スコア
	public int getScore() {
		return getTotalStrokes() - getTotalPar();
	}

}
