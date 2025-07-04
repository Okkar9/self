

public class Hole {
	private int par;	//parの値
	private int strokes;	//出した値
	
	//コンストラクタ
	public Hole(int par,int strokes) {
		this.par = par;
		this.strokes = strokes;
	}
	
	//成績を計算するためのメソッド(strokes - par)
	public int getScore() {
		return strokes - par;
	}
	
	//strokesをもらう
	public int getStrokes() {
		return strokes;
	}
	
	//parをもらう
	public int getPar() {
		return par;
	}
}
