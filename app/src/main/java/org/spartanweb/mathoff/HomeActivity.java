package org.spartanweb.mathoff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView debugText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        debugText = findViewById(R.id.debug);
    }

    @Override
    protected void onResume() {
        super.onResume();
        debugText.setText(((MathOff)getApplication()).getToken());
    }

    public void login(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    public void play(View view) {
        Intent playIntent = new Intent(this, MainActivity.class);
        startActivity(playIntent);
    }

    public void leaderboard(View view) {
        Intent playIntent = new Intent(this, LeaderboardActivity.class);
        startActivity(playIntent);
    }

}
