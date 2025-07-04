import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String input;
		String[] parts;

		// 入力を求める
		while (true) {
			System.out.println("選手名2人と36ホールのスコアをカンマ区切りで入力してください：");
			System.out.println("例: 國分,居林,4,5,3,4,...（合計38個の要素）");

			input = s.nextLine().trim();
			parts = input.split("\\s*,\\s*");//空白いくつも可能カンマで区切り

			// 形式に合っているか確認
			int nparts = 38;//入力が必要な配列の要素数
			if (parts.length == nparts) {
				boolean allNumbersValid = true;
				for (int i = 2; i < nparts; i++) {
					if (!parts[i].matches("\\d+")) {
						allNumbersValid = false;//3番目以降が数字じゃないとエラー
						break;
					}
				}
				if (allNumbersValid) {
					break; // 入力に誤りあり
				}
			}

			System.out.println("入力エラー：名前2つ＋36個のスコア（すべて整数）をカンマで区切って入力してください。");
		}

		// 選手名を取得
		String name1 = parts[0];
		String name2 = parts[1];

		player player1 = new player(name1);
		player player2 = new player(name2);

		// 各選手のホールデータを作成
		for (int i = 0; i < 18; i++) {
			// 選手１
			int strokes1 = Integer.parseInt(parts[2 + i]);
			int par = GolfCourse.getParForHole(i + 1);
			player1.addHole(new Hole(par, strokes1));

			// 選手２
			int strokes2 = Integer.parseInt(parts[20 + i]);
			player2.addHole(new Hole(par, strokes2));	//同じパー
		}
			s.close();
			printResult(player1);
			printResult(player2);
			
			int score1 = player1.getScore();
			int score2 = player2.getScore();
			
			//勝敗を決定
			if(score1 < score2) {
				System.out.println(player1.getName() + "の勝ち");
			}else if(score2 < score1){
				System.out.println(player2.getName() + "の勝ち");
			}else {
				System.out.println("引き分けです！");
			}

	}

	//スコアを出力するメゾット(+x/ -x / +-0)
	private static void printResult(player player) {
		int score = player.getScore();
		String scoreStr = score > 0 ? "+" + score : score < 0 ? String.valueOf(score) : "+-0";//正数なら+,0なら+-0として表示
		System.out.println(player.getName() + "のスコア：" + scoreStr);
		
	}
}
