## 数当てゲーム

ゲーム側が設定した下限値～上限値の間で無作為に値を決定し、
その値を、規定回以内の回答でユーザーが当てる

### 最初の表示

ゲームの趣旨と回答の回数を表示する

・回答が5回まで可能な場合

``` console
数字を当ててみてね。

答えられるのは5回までだよ。
```

### 入力機能

何回目の挑戦かを表示し、ユーザーの回答を受け付ける

・初回の挑戦
``` console
1回目：
```

### 入力チェック

#### 数字以外（数字と認識できない文字）が入力された場合

エラーでその旨を表示し、再入力を求める

・英文字
``` console
1回目：a
注意：数字を入れてね

1回目：
```

#### 範囲外の数値が入力された場合

エラーでその旨を表示し、再入力を求める

・0～99が範囲の場合に、200と入力
``` console
1回目：200
注意：0～99の範囲の数値を入れてね

1回目：
```

#### セーフティ

念のため規定数エラーを起こした場合は下限値を入れたものとして処理を進める

・5回を規定数とした場合（0～99は範囲）
``` console
1回目：a
注意：数字を入れてね

（中略）

1回目：200
注意：0～99の範囲の数値を入れてね
情報：5回入力に失敗したため、0を入力したものとして進めます

1回目：0
```

### 正否判定

ユーザーが入力した値が、ゲームの設定した値と一致しているか確認する

### 一致している場合

正解であることを表示する
挑戦回数も表示する

・3回目の挑戦で正解の34を入力
``` console
3回目：34
すごい！！3回で当てられちゃった！
```

### 不一致で次の挑戦が可能な場合

#### ユーザーの入力値のほうが大きい場合

その旨を表示し、次の入力を求める

・正解が34の場合に45と入力
``` console
2回目：54
もっと小さい数字だよ

3回目：
```

#### ユーザーの入力値のほうが小さい場合

その旨を表示し、次の入力を求める

・正解が34の場合に23と入力
``` console
2回目：23
もっと大きい数字だよ

3回目：
```

### 不一致で次の挑戦がない場合

次の入力を求める代わりに正解を表示する

・挑戦上限が5回、正解が34の場合
``` console
5回目：23
もっと大きい数字だよ

残念！！ 正解は 34 でした！
```


## 実行例

### 失敗例

・挑戦上限5回、正解が56の場合
``` console
数字を当ててみてね。

答えられるのは5回までだよ。

1回目：0
もっと大きい数字だよ

2回目：98
もっと小さい数字だよ

3回目：23
もっと大きい数字だよ

4回目：87
もっと小さい数字だよ

5回目：76
もっと小さい数字だよ

残念！！ 正解は 56 でした！
```

### 正解例

・正解が56の場合
``` console
数字を当ててみてね。

答えられるのは5回までだよ。

1回目：12
もっと大きい数字だよ

2回目：76
もっと小さい数字だよ

3回目：45
もっと大きい数字だよ

4回目：56
すごい！！4回で当てられちゃった！
```