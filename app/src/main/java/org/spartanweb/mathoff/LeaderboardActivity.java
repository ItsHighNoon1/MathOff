package org.spartanweb.mathoff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout scoreboard;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        scoreboard = findViewById(R.id.leaderboard);
        progressBar = findViewById(R.id.progress);

        FetchLeaderboardTask flt = new FetchLeaderboardTask();
        flt.execute(this);
    }

    public void boardFetched(String data) {
        progressBar.setVisibility(View.GONE);

        String[] rows = data.split("\"");
        for(int i = 0; i < rows.length; i++) {
            String[] pieces = rows[i].split("'");

            TextView position = new TextView(this);
            TextView name = new TextView(this);
            TextView score = new TextView(this);
            position.setId(ViewCompat.generateViewId());
            name.setId(ViewCompat.generateViewId());
            score.setId(ViewCompat.generateViewId());

            position.setText(Integer.toString(i + 1));
            position.setTextSize(30.0f);
            name.setText(pieces[1]);
            name.setTextSize(20.0f);
            score.setText(pieces[0]);
            score.setTextSize(20.0f);

            ConstraintLayout viewRow = new ConstraintLayout(this);
            viewRow.addView(position);
            viewRow.addView(name);
            viewRow.addView(score);

            ConstraintSet cs = new ConstraintSet();
            cs.clone(viewRow);
            cs.connect(name.getId(), ConstraintSet.LEFT, position.getId(), ConstraintSet.RIGHT, MathOff.toDp(30, this.getResources().getDisplayMetrics()));
            cs.connect(score.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT);
            cs.centerVertically(position.getId(), ConstraintSet.PARENT_ID);
            cs.centerVertically(name.getId(), ConstraintSet.PARENT_ID);
            cs.centerVertically(score.getId(), ConstraintSet.PARENT_ID);
            cs.applyTo(viewRow);

            scoreboard.addView(viewRow);
        }
    }

    public static String fetchLeaderboard() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("leaderboard", "true");

        return MathOff.httpsRequest(parameters);
    }

    private static class FetchLeaderboardTask extends AsyncTask<Object, Void, String> {
        private Object definitelyNotAnActivity;

        protected String doInBackground(Object... params) {
            definitelyNotAnActivity = params[0];
            return fetchLeaderboard();
        }

        protected void onPostExecute(String result) {
            ((LeaderboardActivity)definitelyNotAnActivity).boardFetched(result);
        }
    }

}
