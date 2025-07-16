class GolfScoreCalculator:
    def __init__(self):
        self.pars = [4,4,3,4,5,4,5,3,4,4,3,4,5,4,3,4,5,4]
        self.max_holes = len(self.pars)
        
    #入力のため
    def get_valid_input(self):
        while True:
            #カンマで区切り
            raw_input = input("入力->").replace(" ", "")
            
            #数字ですかチェックする
            if not all(c.isdigit() or c==',' for c in raw_input):
                print("数字、空白、カンマ以外は入力不可です")
                continue
            
            try:
                scores = [int(s) for s in raw_input.split(',') if s!= '']
            except ValueError:
                print("入力に誤りがありました。もう一度試してください")
                continue
            
            #負の数字が受け取れない
            if any(score < 0 for score in scores):
                print("整数の数字を入力してください")
                continue
            
            return scores
    
    #スコアを計算する
    def calculate_score(self,scores):
        holes_played = min(len(scores),self.max_holes)
        total_score = 0
        
        for i in range(holes_played):
            stroke = scores[i]
            par = self.pars[i]
            total_score += (stroke - par)
            
        return holes_played, total_score
    
    #メインメソッド
    def run(self):
        scores = self.get_valid_input()
        holes_played, score_diff = self.calculate_score(scores)
        
        if score_diff > 0:
            score_str = f"+{score_diff}"
            
        elif score_diff == 0:
            score_str = "±0"
        
        else:
            score_str = score_diff
            
        print(f"{holes_played}ホール終了して、{score_str}")
        
app = GolfScoreCalculator()
app.run()
            
        
