package org.spartanweb.mathoff;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class LeaderboardActivity extends AppCompatActivity {

    private LinearLayout scoreboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        scoreboard = findViewById(R.id.leaderboard);

        FetchLeaderboardTask flt = new FetchLeaderboardTask();
        flt.execute(this);
    }

    public void boardFetched(String data) {
        System.err.println(data);
        String[] rows = data.split("\"");
        for(int i = 0; i < rows.length; i++) {
            TextView tv = new TextView(this);
            tv.setText(rows[i]);
            scoreboard.addView(tv);
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
