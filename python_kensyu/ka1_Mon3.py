while True:
    try:
        start = input("開始の数字を入力してください:")
        end = input("最後の数字を入力してください:")

        #int型に変換
        start = int(start)
        end = int(end)
        
        #開始より終了が大きい
        if end <= start:
            print("終了数字が開始する数字より大きくなっています。")
            continue
        for i in range(start,end + 1):
            if i % 3 == 0 and i % 5 == 0:
                print("FizzBuzz")
            elif i % 3 == 0:
                print("Fizz")
            elif i % 5 == 0:
                print("Buzz")
            else:
                print(i)
        break
    #intへの変換が失敗した場合数字じゃないと判断する
    except ValueError:
        print("入力に誤りがありました。数字を入力してください")
        continue

