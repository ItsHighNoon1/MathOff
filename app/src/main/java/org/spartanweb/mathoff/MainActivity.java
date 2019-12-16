package org.spartanweb.mathoff;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Random rand;

    private String inputNum = "";
    private boolean inputNeg = false;

    private boolean running;
    private int score = 0;

    private Problem problem;

    private TextView problemText;
    private TextView answerText;
    private TextView timerText;
    private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand = new Random();
        problemText = findViewById(R.id.problem);
        answerText = findViewById(R.id.answer);
        timerText = findViewById(R.id.timer);
        scoreText = findViewById(R.id.score);
        start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void start() {
        timerText.setText("1:00");
        score = 0;
        running = true;
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                int secs = ((int)millisUntilFinished + 500) / 1000;
                int mins = secs / 60;
                secs %= 60;
                String timerString = String.format(Locale.KOREA, "%d:%02d", mins, secs);
                timerText.setText(timerString);
            }

            public void onFinish() {
                running = false;
                timerText.setText("0:00");
            }
        }.start();
        newProblem();
    }

    private void newProblem() {
        problem = new Problem(rand);
        problemText.setText(problem.getProblem());
    }

    private void buttonPress(int btn) {
        if(!running) {
            return;
        }
        if(btn >= 0) {
            inputNum += btn;
        } else if(btn == -1) {
            inputNeg = !inputNeg;
        } else {
            if(inputNum.length() > 0) {
                inputNum = inputNum.substring(0, inputNum.length() - 1);
            }
        }
        String finalText = inputNum;
        if(inputNeg) {
            finalText = "-" + finalText;
        }
        if(inputNum.length() > 0) {
            if(Integer.parseInt(finalText) == problem.getAnswer()) {
                inputNum = "";
                inputNeg = false;

                score += problem.getValue();
                String scoreString = "Score: " + score;
                scoreText.setText(scoreString);

                newProblem();
                finalText = "";
            }
        }
        answerText.setText(finalText);
    }

    public void buttonPress0(View view) { buttonPress(0); }
    public void buttonPress1(View view) { buttonPress(1); }
    public void buttonPress2(View view) { buttonPress(2); }
    public void buttonPress3(View view) { buttonPress(3); }
    public void buttonPress4(View view) { buttonPress(4); }
    public void buttonPress5(View view) { buttonPress(5); }
    public void buttonPress6(View view) { buttonPress(6); }
    public void buttonPress7(View view) { buttonPress(7); }
    public void buttonPress8(View view) { buttonPress(8); }
    public void buttonPress9(View view) { buttonPress(9); }
    public void buttonPressMinus(View view) { buttonPress(-1); }
    public void buttonPressBack(View view) { buttonPress(-2); }

    public void scoreSubmitted(String response) {
        this.finish();
    }

    private static String submitScore(int score, String token) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("score", Integer.toString(score));
        parameters.put("token", token);
        return MathOff.httpsRequest(parameters);
    }

    private static class ScoreSubmitTask extends AsyncTask<Object, Void, String> {
        private Object definitelyNotAnActivity;

        protected String doInBackground(Object... params) {
            definitelyNotAnActivity = params[2];
            return submitScore((Integer)params[0], (String)params[1]);
        }

        protected void onPostExecute(String result) {
            ((MainActivity)definitelyNotAnActivity).scoreSubmitted(result);
        }
    }

}
