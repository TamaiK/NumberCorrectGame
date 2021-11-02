import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class App {

    // 
    private static Random random;
    // 
    private static Scanner scanner;

    // 定数
    // 
    private static final int MAX_CHALLENGE = 10;
    // 
    private static final int MIN_VALUE = 0;
    // 
    private static final int MAX_VALUE = 999;
    // 
    private static final int MAX_INPUT_LIMIT = 5;
    // 
    private static final int HINT_REPLACE_BOUND = 100;

    // 定数（文字）
    // 
    private static final String MESSAGE_FOR_BLANK = "";
    // 
    private static final String MESSAGE_FOR_PREFACE = "数を当ててみてね。";
    // 
    private static final String MESSAGE_FORMAT_FOR_LIMIT_COUNT = "答えられるのは %d 回までだよ。";

    // 
    private static final String MESSAGE_FORMAT_FOR_CHALLENGE = "%d回目：";
    // 
    private static final String MESSAGE_FOR_REQUIRE_INPUT_NUMBER = "注意：数字を入れてね";
    // 
    private static final String MESSAGE_FORMAT_FOR_REQUIRE_IN_RANGE_NUMBER = "注意：%d～%dの範囲の数値を入れてね";
    // 
    private static final String MESSAGE_FORMAT_FOR_INPUT_LIMIT_OVER = "情報：%d回入力に失敗しため、%dを入力したものとして進めます";
    
    // 
    private static final String MESSAGE_FORMAT_FOR_SUCCESS = "すごい！！%d回で当てられちゃった！";
    // 
    private static final String MESSAGE_FOR_HINT_SMALLER = "ちょっと小さい数字だよ";
    // 
    private static final String MESSAGE_FOR_HINT_GREATER = "ちょっと大きい数字だよ";
    // 
    private static final String MESSAGE_FOR_HINT_VERY_SMALLER = "もっと小さい数字だよ";
    // 
    private static final String MESSAGE_FOR_HINT_VERY_GREATER = "もっと大きい数字だよ";
    // 
    private static final String MESSAGE_FORMAT_FOR_FAILURE = "残念！！ 正解は %d でした！";
    
    static{
        // 
        Init();
    }

    // 
    public static void main(String[] args) throws Exception {

        // 
        DispPreface();

        // 
        int correctNum = CreateRandomNumber(MIN_VALUE, MAX_VALUE);

        // 
        ChallengeLoop(correctNum);

        //
        Fin();
    }

    // 
    private static void Init() {

        // 

        // 
        random = new Random();
        // 
        scanner = new Scanner(System.in);
    }

    // 
    private static void Fin() {

        // 
        
        // 
        scanner.close();
    }

    // 
    private static void DispPreface() {

        System.out.println(MESSAGE_FOR_PREFACE);
        System.out.println(MESSAGE_FOR_BLANK);
        System.out.println(String.format(MESSAGE_FORMAT_FOR_LIMIT_COUNT, MAX_CHALLENGE));
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 
    private static int CreateRandomNumber(int min, int max) {

        // 
        int range = max - min + 1;

        // 
        return random.nextInt(range) + min;
    }

    // 
    private static void ChallengeLoop(int correctNum) {

        // 
        int challengeCount = 0;

        // 
        boolean result = false;

        // 
        while(IsNextChallenge(challengeCount)) {

            // 
            challengeCount++;

            // 
            int answer = GetAnswer(challengeCount, MAX_INPUT_LIMIT);

            result = CheckAnswer(answer, correctNum);

            if(result){

                // 
                DispSuccess(challengeCount);

                // 
                break;
            }
            else{

                // 

                // 
                DispHint(answer, correctNum);
            }
        }

        if(!result){
            // 
            DispFailure(correctNum);
        }
    }

    // 
    private static boolean IsNextChallenge(int challengeCount) {
        return challengeCount < MAX_CHALLENGE;
    }

    // 
    // 
    private static int GetAnswer(int challengeCount, int limitCount) {

        // 
        if(limitCount <= 0){

            // 
            DistInputLimitOver();
            
            // 
            DispChallenge(challengeCount);
            // 
            DispInputMin(MIN_VALUE);

            // 
            return MIN_VALUE;
        }

        // 
        limitCount--;

        // 
        DispChallenge(challengeCount);

        // 
        int inputNum;
        try{
            inputNum = scanner.nextInt();
        }
        catch (InputMismatchException e){
            
            // 
            DispRequireInputNumber();

            // 
            scanner.next();

            // 
            inputNum = GetAnswer(challengeCount, limitCount);
        }

        if(!IsInRange(inputNum, MIN_VALUE, MAX_VALUE)){

            // 
            DispRequireInRangeNumber(MIN_VALUE, MAX_VALUE);

            // 
            inputNum = GetAnswer(challengeCount, limitCount);
        }

        return inputNum;
    }

    // 
    private static boolean CheckAnswer(int answer, int correctNum) {
        return answer == correctNum;
    }

    // 
    private static void DispFailure(int correctNum) {
        
        System.out.println(String.format(MESSAGE_FORMAT_FOR_FAILURE, correctNum));
    }

    // 
    private static void DistInputLimitOver() {
        
        System.out.println(String.format(MESSAGE_FORMAT_FOR_INPUT_LIMIT_OVER, MAX_INPUT_LIMIT, MIN_VALUE));
    }

    // 
    private static void DispChallenge(int challengeCount) {
        
        System.out.print(String.format(MESSAGE_FORMAT_FOR_CHALLENGE, challengeCount));
    }

    // 
    private static void DispRequireInputNumber() {

        System.out.println(MESSAGE_FOR_REQUIRE_INPUT_NUMBER);
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 
    private static boolean IsInRange(int inputNum, int minValue, int maxValue) {

        return inputNum >= minValue && inputNum <= maxValue;
    }

    // 
    private static void DispRequireInRangeNumber(int minValue, int maxValue) {

        System.out.println(String.format(MESSAGE_FORMAT_FOR_REQUIRE_IN_RANGE_NUMBER, minValue, maxValue));
        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 
    private static void DispSuccess(int challengeCount) {
        System.out.println(String.format(MESSAGE_FORMAT_FOR_SUCCESS, challengeCount));
    }

    // 
    private static void DispHint(int answer, int correctNum) {

        //
        if(answer > correctNum){

            //

            //
            if(answer - correctNum > HINT_REPLACE_BOUND){
                // 
                System.out.println(MESSAGE_FOR_HINT_VERY_SMALLER);

            }
            else{
                // 
                System.out.println(MESSAGE_FOR_HINT_SMALLER);
            }
        }
        else{

            // 
            //

            //
            if(correctNum - answer > HINT_REPLACE_BOUND){
                // 
                System.out.println(MESSAGE_FOR_HINT_VERY_GREATER);

            }
            else{
                // 
                System.out.println(MESSAGE_FOR_HINT_GREATER);
            }
        }

        System.out.println(MESSAGE_FOR_BLANK);
    }

    // 
    private static void DispInputMin(int minValue) {

        System.out.println(minValue);
    }
}
