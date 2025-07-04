package kadai1;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		ArrayList<Hole> holes = new ArrayList<>();

		// ユーザーから入力を求める
		System.out.println("各ホールの出た値を(1, 2, 3の形式で)入力してください：");
		String input = s.nextLine().trim();

		// 入力誤りの処理
		while (!isValidInput(input)) {
			System.out.println("入力に誤りがあります。もう一度入力してください：");
			input = s.nextLine().trim();
		}

		// 入力をHoleに入れて計算する
		String[] strokesInput = input.split("\\s*,\\s*");// コマで区切、空白を無視
		int holesPlayed = Math.min(strokesInput.length, 18);// 18より大きいホールが無視されます
		int totalstrokes = 0;

		for (int i = 0; i < holesPlayed; i++) {
			int strokes = Integer.parseInt(strokesInput[i].trim());
			int par = GolfCourse.getParForHole(i + 1);
			holes.add(new Hole(par, strokes));
			totalstrokes += strokes;
		}

		// 成績を計算
		int totalPar = 0;
		for (Hole hole : holes) {
			totalPar += hole.getPar();
		}
		int score = totalstrokes - totalPar;
		s.close();
		//正・負の分け、0の場合は+-0
		String scoreStr = score > 0 ? "+" + score : score < 0 ? String.valueOf(score) : "+-0";
		System.out.println(holesPlayed + "ホール終了して、" + scoreStr + "です。"); // 成績を出力

	}

	private static boolean isValidInput(String input) {
		// 入力形式を確認
		return input.matches("([0-9]+\\s*,\\s*)*[0-9]+\\s*,?");
	}
}
