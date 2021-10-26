import java.util.Random;
import java.util.Scanner;

public class App {

    // 乱数生成クラス
    private static Random random;
    // 入力取得に使うクラス
    private static Scanner scanner;

    // 定数
    // 挑戦可能回数
    private static int MAX_CHALLENG = 5;
    // 乱数の下限値
    private static int MIN_VALUE = 0;
    // 乱数の上限値
    private static int MAX_VALUE = 99;
    // 入力許容回数
    private static int MAX_INPUT_LIMIT = 10;

    // 定数（文字）
    // 改行用
    private static String MESSAGE_FOR_BLANK = "";
    // 前置き
    private static String MESSAGE_FOR_PREFACE = "数を当ててみてね。";
    // 挑戦上限
    private static String MESSAGE_FORMAT_FOR_LIMIT_COUNT = "答えられるのは %d 回までだよ。";
    
    // メイン関数
    public static void main(String[] args) throws Exception {

        Init();

        // 前置きの表示
        DispPreface();

        // 各変数の用意
        // 挑戦回数
        int challengeCount = 0;
        // 正解の値
        int correctNum = CreateRandomNumber(MIN_VALUE, MAX_VALUE);

        // 回答用変数
        int answerNum = 0;

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

        Fin();
    }

    // 初期化関数
    private static void Init() {

        // 最初に処理が必要なものをここに入れる

        // 乱数
        random = new Random();
        // 読み取りクラス
        scanner = new Scanner(System.in);
    }

    // 終了関数
    private static void Fin() {

        // 最後に処理が必要なものをここに入れる
        
        // 閉じておく
        scanner.close();
    }

    // 前置きの表示
    private static void DispPreface() {

        System.out.println(MESSAGE_FOR_PREFACE);
        System.out.println(MESSAGE_FOR_BLANK);
        System.out.println(String.format(MESSAGE_FORMAT_FOR_LIMIT_COUNT, MAX_CHALLENG));
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 範囲内で無作為な値を作成する関数
    private static int CreateRandomNumber(int min, int max) {

        // 差だけでは1足りなくなる（元の値の分がカウントされない）ので＋１
        int range = max - min + 1;

        // 指定の大きさから乱数を作り、最小値を足すことで指定範囲の乱数にする
        return random.nextInt(range) + min;
    }
}
