import java.util.Random;
import java.util.Scanner;

public class App {

    private static Random random;
    // 挑戦可能回数
    private static int MAX_CHALLENG = 5;
    // 乱数の最大値
    private static int MAX_VALUE = 99;
    // 入力許容回数
    private static int MAX_INPUT_LIMIT = 10;
    public static void main(String[] args) throws Exception {
        // 初期化
        random = new Random();
        // 読み取りクラス
        Scanner scanner = new Scanner(System.in);

        // 各変数の用意
        // 挑戦回数
        int challengeCount = 0;
        // 正解の値
        // 引数の値-1までの範囲で乱数を作るので+1する
        int correctNum = random.nextInt(MAX_VALUE+1);

        // 回答用変数
        int answerNum = 0;

        System.out.println("数を当ててみてね。");
        System.out.println("");
        System.out.println(String.format("答えられるのは %d 回までだよ。", MAX_CHALLENG));
        System.out.println("");

        // 挑戦回数以内か
        // 実質セーフティ
        while(challengeCount < MAX_CHALLENG) {

            // 挑戦回数を加算
            challengeCount++;

            // 入力に成功したか
            boolean isInput = false;
            // 入力挑戦回数
            int inputCount = 0;
            while(inputCount < MAX_INPUT_LIMIT){
                // 何回目の挑戦か表示
                System.out.print(String.format("%d回目：", challengeCount));
                
                try{
                    answerNum = scanner.nextInt();

                    // 入力に成功
                    isInput = true;

                    // 抜ける
                    break;
                }
                catch (Exception e){
                    System.out.println("数字を入れてね");

                    // 今回の入力は無視する
                    scanner.next();
                }
                inputCount++;
            }

            if(!isInput){
                // 規定回数まで数値を入れられなかった
                System.out.println("一度の入力回数の閾値を超えたので0とみなします");
                answerNum = 0;
            }

            // 回答処理
            if(answerNum == correctNum){
                // 正解
                if(challengeCount == MAX_CHALLENG){
                    // 最終回で回数つけるのは変な気がするので
                    System.out.println("すごい！！当てられちゃった！");
                }
                else{
                    // 回数つきで
                    System.out.println(String.format("すごい！！%d回で当てられちゃった！", challengeCount));
                }

                // 当てたら抜ける
                break;
            }
            else{
                // 不正解
                if(answerNum < correctNum){
                    // 正解未満
                    System.out.println("もっと大きい数字だよ");
                }
                else{
                    // 正解以上
                    // 正解ではないので正解より大きい
                    System.out.println("もっと小さい数字だよ");
                }
            }
            System.out.println("");

            // 挑戦回数以上か
            if(challengeCount >= MAX_CHALLENG){
                // ここに来る＝失敗なので回答を表示
                System.out.println(String.format("残念！！ 正解は %d でした！", correctNum));
                System.out.println("");

                // 一応抜けておく
                break;
            }
        }

        // 最後に閉じる
        scanner.close();
    }
}
