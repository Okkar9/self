class Player:
    def __init__(self, name, scores, pars):
        self.name = name  #選手名
        self.scores = scores  #18個のスコア
        self.pars = pars
        self.score_diff = self.calculate_score()
        
    #スコア計算メソッド
    def calculate_score(self):
        return sum(stroke - par for stroke, par in zip(self.scores, self.pars))

class GolfScoreCalculator:
    def __init__(self):
        self.pars = [4,4,3,4,5,4,5,3,4,4,3,4,5,4,3,4,5,4]
        self.max_holes = len(self.pars)
        
    #入力のため
    def get_valid_input(self):
        while True:
            #カンマで区切り
            raw_input = input("入力->").replace(" ", "")
            parts = raw_input.split(',')
            
            #38個の入力がなかったら再度入力させる
            if len(parts) != 38:
                print("入力に誤りがあります。正しい形式(Tanaka,otaro,1,3,4,..36個の数字)")
                continue
            #選手名の定義
            name1, name2 = parts[0], parts[1]
            
            
            try:
                #int型へ変換
                scores = [int(s) for s in parts[2:]]
            except ValueError:
                print("スコアは数字で入力してください")
                continue
            
            #負の数字が受け取れない
            if any(score < 0 for score in scores):
                print("整数の数字を入力してください")
                continue
            #選手とスコアをつける
            player1_scores = scores[:18]
            player2_scores = scores[18:]
            
            return name1, player1_scores, name2, player2_scores
    
    
    #メインメソッド
    def run(self):
        name1, scores1, name2, scores2 = self.get_valid_input()
        
        player1 = Player(name1, scores1, self.pars)
        player2 = Player(name2, scores2, self.pars)
        
        #スコア表示
        print(f"{player1.name} のスコア：{'+' if player1.score_diff > 0 else ''}{player1.score_diff}")
        print(f"{player2.name} のスコア：{'+' if player2.score_diff > 0 else ''}{player2.score_diff}")

        #勝敗判断
        if player1.score_diff < player2.score_diff:
            print(f"{player1.name}の勝ちです。")
        elif player1.score_diff > player2.score_diff:
            print(f"{player2.name}の勝ちです。")
        else:
            print("引き分けです。")
app = GolfScoreCalculator()
app.run()
            
        
