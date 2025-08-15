import tkinter as tk
import math
import re

class Calculator:
    def __init__(self):
        self.current_input = ""    #現在の入力
        
    def add_input(self, value):
        self.current_input += str(value)    #入力
        
    def allclear_input(self):
        self.current_input = ""    #AC
        
    def clear_input(self):
        self.current_input = self.current_input[:-1]    #C
        
    def calculate(self):
        expression = self.current_input

        #前処理：記号や特殊文字をPython式に変換
        expression = expression.replace('π', str(math.pi))
        expression = re.sub(r'√(\d+)', r'math.sqrt(\1)', expression)
        expression = expression.replace('^', '**')

        try:
            #階乗（例：5! や 3+4! など）を処理
            while '!' in expression:
                match = re.search(r'(\d+)!', expression)
                if not match:
                    break
                number = int(match.group(1))
                factorial_result = str(math.factorial(number))
                expression = expression.replace(f"{number}!", factorial_result, 1)

            # 最終的な式を評価（計算）する
            result = eval(expression)
            self.current_input = str(result)
            return result
        except Exception:
            self.current_input = ""
            return "エラー"
        
    def get_input(self):
        return self.current_input
    
class calculatorGUI:
    #画面の設定
    def __init__(self, root, Calculator):
        self.Calculator = Calculator
        self.root = root
        self.root.title = "電卓"
        
        self.result_var = tk.StringVar()
        self.entry = tk.Entry(root, textvariable=self.result_var, font=("Arial",20), bd=10, relief="sunken", justify=   "right")
        self.entry.grid(row=0, column=0, columnspan=4)
        
        #ボタンの設定
        self.buttons = [
            ('7', 1, 0), ('8', 1, 1), ('9', 1, 2), ('/', 1, 3),
            ('4', 2, 0), ('5', 2, 1), ('6', 2, 2), ('*', 2, 3),
            ('1', 3, 0), ('2', 3, 1), ('3', 3, 2), ('-', 3, 3),
            ('0', 4, 0), ('C', 4, 1), ('=', 4, 2), ('+', 4, 3),
            ('π', 5, 0), ('^', 5, 1), ('√', 5, 2), ('!', 5, 3),
            ('AC', 6, 0, 4)
        ]
        
        for (text, row, col, *span) in self.buttons:
            if span:
                btn = tk.Button(root, text=text, font=("Arial", 20), width=5, height=2, command=lambda t=text: self.on_button_click(t))
                btn.grid(row=row, column=col, columnspan=span[0])
                
            else:
                btn = tk.Button(root, text=text, font=("Arial", 20), width=5, height=2, command=lambda t=text: self.on_button_click(t))
                btn.grid(row=row, column=col)
                
            #キーボード入力とバインドする
            root.bind('<Return>', lambda event: self.calculate())
            root.bind('<Key>', self.handle_keypress)
    
    #ボタンの操作処理
    def on_button_click(self,text):
        if text == "AC":
            self.Calculator.allclear_input()
        elif text == "C":
            self.Calculator.clear_input()
        elif text == "=":
            self.calculate()
        elif text == "π":
            self.Calculator.add_input(math.pi)
        elif text == "√":
            self.Calculator.add_input("√")
        elif text == "^":
            self.Calculator.add_input("^")
        elif text == "!":
            self.Calculator.add_input("!")
        else:
            self.Calculator.add_input(text)
            
        self.update_display()
        
    def calculate(self):
        result = self.Calculator.calculate()
        self.result_var.set(result)
    
    #キーボード入力の処理
    def handle_keypress(self, event):
        key = event.char
        if key.isdigit() or key in "+-*/.()^":
            self.Calculator.add_input(key)
        elif key.lower() == "c":
            self.Calculator.allclear_input()
        elif key == "Backspace":
            self.Calculator.clear_input()
        elif key == "Enter":
            self.calculate()
            
        self.update_display()
        
    def update_display(self):
        self.result_var.set(self.Calculator.get_input())
        
        

root = tk.Tk()
calculator = Calculator()
app = calculatorGUI(root, calculator)
root.mainloop()
