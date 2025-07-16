while(True):
    user_input = input("入力->")#入力を受け取る
    c_input = user_input.replace(" ", "")#空白を削除
    

    if not all(char.isdigit() or char == ',' for char in c_input):
        #ループで数字かカンマかチェック
        print("数字を入力してください")
        continue
    
    parts = c_input.split(',')#カンマ区切り
    
    try:
        nums = [int(n) for n in parts if n != '']
        if not nums:
            raise ValueError#不適切な数字が入ってないか
    except ValueError:
        print("数字に誤りがあります。もう一度入力してください")
        continue
    
    s_nums = sorted(nums)
    result = ','.join(map(str, s_nums))

    print("出力->", result)
    break
 