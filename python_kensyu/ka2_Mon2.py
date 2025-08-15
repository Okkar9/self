import tkinter as tk
from tkinter import messagebox
import datetime
import time
import threading
import winsound

class Clock:
    def __init__(self, root):
        self.root = root
        self.root.title("時計")
        self.root.geometry("420x200")
        
        #アラームはhh:mm形式
        self.alarm_time = tk.StringVar()
        
        #時間表示
        self.time_label = tk.Label(self.root, text="", font=("Helvetica", 20))
        self.time_label.pack(pady=20)
        
        #アラームインプット欄
        alarm_frame = tk.Frame(self.root)
        alarm_frame.pack(pady=10)
        
        tk.Label(alarm_frame, text="アラームを設定(HH:MM)：").pack(side="left")
        self.alarm_entry = tk.Entry(alarm_frame, textvariable=self.alarm_time, width=10)
        self.alarm_entry.pack(side="left")
        
        #時間更新とアラームチェック
        self.update_time()
        self.start_alarm_thread()
        
    def update_time(self):
        now = datetime.datetime.now()
        #フォマード＝yyyy年mm月dd日  hh時mm分ss秒
        formatted_time = f"{now.year}年{now.month}月{now.day}日　{now.hour}時{now.minute}分{now.second}秒"
        self.time_label.config(text=formatted_time)
        self.root.after(1000, self.update_time) #1秒ごとに更新
        
    def start_alarm_thread(self):
        alarm_thread = threading.Thread(target=self.check_alarm)
        alarm_thread.daemon = True     #終了時スレッドが止まるように
        alarm_thread.start()
        
    def check_alarm(self):
        while True:
            alarm_time = self.alarm_time.get()
            if alarm_time:
                try:
                    now = datetime.datetime.now().strftime("%H:%M")
                    if now == alarm_time:
                        self.show_alarm()
                        time.sleep(60)    #同じアラームが何度もならないように
                except Exception as e:
                    print("アラームチェック中にエラー", e)
            time.sleep(1)
            
    def show_alarm(self):
        winsound.Beep(1000,1000)
        messagebox.showinfo("アラーム","時間になりました!")
        
root = tk.Tk()
app = Clock(root)
root.mainloop()