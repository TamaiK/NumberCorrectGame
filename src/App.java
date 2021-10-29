import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class App {

    // 乱数生成クラス
    private static Random random;
    // 入力取得に使うクラス
    private static Scanner scanner;

    // 定数
    // 挑戦可能回数
    private static final int MAX_CHALLENGE = 5;
    // 乱数の下限値
    private static final int MIN_VALUE = 0;
    // 乱数の上限値
    private static final int MAX_VALUE = 99;
    // 入力許容回数
    private static final int MAX_INPUT_LIMIT = 5;

    // 定数（文字）
    // 改行用
    private static final String MESSAGE_FOR_BLANK = "";
    // 前置き
    private static final String MESSAGE_FOR_PREFACE = "数を当ててみてね。";
    // 挑戦上限
    private static final String MESSAGE_FORMAT_FOR_LIMIT_COUNT = "答えられるのは %d 回までだよ。";

    // 挑戦時
    private static final String MESSAGE_FORMAT_FOR_CHALLENGE = "%d回目：";
    // 数字を入力するように求める
    private static final String MESSAGE_FOR_REQUIRE_INPUT_NUMBER = "注意：数字を入れてね";
    // 範囲内の数字を入れることを求める
    private static final String MESSAGE_FORMAT_FOR_REQUIRE_IN_RANGE_NUMBER = "注意：%d～%dの範囲の数値を入れてね";
    // 試行上限到達時の表示
    private static final String MESSAGE_FORMAT_FOR_INPUT_LIMIT_OVER = "情報：%d回入力に失敗しため、%dを入力したものとして進めます";
    
    // 正解時
    private static final String MESSAGE_FORMAT_FOR_SUCCESS = "すごい！！%d回で当てられちゃった！";
    // 正解の方が小さい
    private static final String MESSAGE_FOR_HINT_SMALLER = "もっと小さい数字だよ";
    // 正解の方が大きい
    private static final String MESSAGE_FOR_HINT_GREATER = "もっと大きい数字だよ";
    // 失敗時
    private static final String MESSAGE_FORMAT_FOR_FAILURE = "残念！！ 正解は %d でした！";
    
    // メイン関数
    public static void main(String[] args) throws Exception {

        // 初期化
        Init();

        // 前置きの表示
        DispPreface();

        // 正解の値
        int correctNum = CreateRandomNumber(MIN_VALUE, MAX_VALUE);

        // 挑戦用のループ
        ChallengeLoop(correctNum);

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
        System.out.println(String.format(MESSAGE_FORMAT_FOR_LIMIT_COUNT, MAX_CHALLENGE));
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 範囲内で無作為な値を作成する関数
    private static int CreateRandomNumber(int min, int max) {

        // 差だけでは1足りなくなる（元の値の分がカウントされない）ので＋１
        int range = max - min + 1;

        // 指定の大きさから乱数を作り、最小値を足すことで指定範囲の乱数にする
        return random.nextInt(range) + min;
    }

    // 挑戦ループ用の関数
    private static void ChallengeLoop(int correctNum) {

        // 挑戦回数
        int challengeCount = 0;

        // 成否
        boolean result = false;

        // 挑戦回数が上限に行くまで続ける
        while(IsNextChallenge(challengeCount)) {

            // 挑戦回数を加算
            challengeCount++;

            // 入力受付
            int answer = GetAnswer(challengeCount, MAX_INPUT_LIMIT);

            result = CheckAnswer(answer, correctNum);

            if(result){

                // 正解
                DispSuccess(challengeCount);

                // 正解したので抜ける
                break;
            }
            else{

                // 不正解

                // ヒントを表示する
                DispHint(answer, correctNum);
            }
        }

        if(!result){
            // 正解できずにここに来た＝失敗なので回答を表示
            DispFailure(correctNum);
        }
    }

    // 次の挑戦が可能か
    private static boolean IsNextChallenge(int challengeCount) {
        return challengeCount < MAX_CHALLENGE;
    }

    // 入力受付
    // 試行上限は多くしないと思うので回帰処理で行う
    private static int GetAnswer(int challengeCount, int limitCount) {

        // 試行回数が残っているか
        if(limitCount <= 0){

            // 許容上限まで行った
            DistInputLimitOver();
            
            // 何回目の挑戦かを表示
            DispChallenge(challengeCount);
            // 下限値を入れたものとして扱うのでその表示
            DispInputMin(MIN_VALUE);

            // 乱数の下限値を返す
            return MIN_VALUE;
        }

        // 試行するので残り回数を減らす
        limitCount--;

        // 何回目の挑戦かを表示
        DispChallenge(challengeCount);

        // 入力値の取得
        int inputNum;
        try{
            inputNum = scanner.nextInt();
        }
        catch (InputMismatchException e){
            
            // 数字以外を入れた
            DispRequireInputNumber();

            // 次の入力をさせるために読み込む
            scanner.next();

            // 再挑戦
            inputNum = GetAnswer(challengeCount, limitCount);
        }

        if(!IsInRange(inputNum, MIN_VALUE, MAX_VALUE)){

            // 範囲外の値が入力された
            DispRequireInRangeNumber(MIN_VALUE, MAX_VALUE);

            // 再挑戦
            inputNum = GetAnswer(challengeCount, limitCount);
        }

        return inputNum;
    }

    // 成否の確認
    private static boolean CheckAnswer(int answer, int correctNum) {
        return answer == correctNum;
    }

    // 失敗時の表示
    private static void DispFailure(int correctNum) {
        
        System.out.println(String.format(MESSAGE_FORMAT_FOR_FAILURE, correctNum));
    }

    // 入力試行回数を超過した場合の表示
    private static void DistInputLimitOver() {
        
        System.out.println(String.format(MESSAGE_FORMAT_FOR_INPUT_LIMIT_OVER, MAX_INPUT_LIMIT, MIN_VALUE));
    }

    // 何回目の挑戦か表示
    private static void DispChallenge(int challengeCount) {
        
        System.out.print(String.format(MESSAGE_FORMAT_FOR_CHALLENGE, challengeCount));
    }

    // 数値入力を要求する表示
    private static void DispRequireInputNumber() {

        System.out.println(MESSAGE_FOR_REQUIRE_INPUT_NUMBER);
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 入力した数値が乱数の範囲内か
    private static boolean IsInRange(int inputNum, int minValue, int maxValue) {

        return inputNum >= minValue && inputNum <= maxValue;
    }

    // 範囲内の数値の入力を要求する表示
    private static void DispRequireInRangeNumber(int minValue, int maxValue) {

        System.out.println(String.format(MESSAGE_FORMAT_FOR_REQUIRE_IN_RANGE_NUMBER, minValue, maxValue));
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 正解時の表示
    private static void DispSuccess(int challengeCount) {
        System.out.println(String.format(MESSAGE_FORMAT_FOR_SUCCESS, challengeCount));
    }

    // ヒントの表示
    private static void DispHint(int answer, int correctNum) {

        if(answer > correctNum){

            // 正解の方が小さい
            System.out.println(MESSAGE_FOR_HINT_SMALLER);
        }
        else{

            // 正解の方が大きい
            // (この処理を呼ぶ前に正解は除外しているはずなので)
            System.out.println(MESSAGE_FOR_HINT_GREATER);
        }

        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 下限値を入力したものとして扱う際の入力表示
    private static void DispInputMin(int minValue) {

        System.out.println(minValue);
    }
}
