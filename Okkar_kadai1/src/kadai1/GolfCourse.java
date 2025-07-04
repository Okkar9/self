package kadai1;

public class GolfCourse {
	private static final int[] PARS = {4, 4, 3, 4, 5, 4, 5, 3, 4, 4, 3, 4, 5, 4, 3, 4, 5, 4};
	
	//ホールごと(1から18)にparをもらう
	public static int getParForHole(int holeNumber) {
		//1より小さい、または18より大きい場合はエラー
		if(holeNumber < 1 || holeNumber >18) {
			throw new IllegalArgumentException("ホール番号に誤りがあります：" + holeNumber);
		}
		return PARS[holeNumber - 1];
	}
}
