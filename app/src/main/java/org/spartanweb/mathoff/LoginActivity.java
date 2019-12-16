package org.spartanweb.mathoff;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText userInput;
    private EditText passwordInput;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        error = findViewById(R.id.error);

        final LoginActivity bruh = this;
        Button loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LoginTask lt = new LoginTask();
                lt.execute(userInput.getText().toString(), passwordInput.getText().toString(), bruh);
            }
        });
    }

    public void endpointResponse(String token) {
        if(token == null) {
            error.setText(R.string.wrong_login);
            return;
        } else if(token.length() == 0) {
            error.setText(R.string.wrong_login);
            return;
        } else if(token.startsWith("&")) {
            error.setText(R.string.timed_out);
            return;
        }
        ((MathOff)getApplication()).setToken(token);
        this.finish();
    }

    public void register(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://spartanweb.org/login"));
        startActivity(browserIntent);
    }

    private static String login(String username, String password) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("user", username);
        parameters.put("password", password);

        return MathOff.httpsRequest(parameters);
    }

    private static class LoginTask extends AsyncTask<Object, Void, String> {
        private Object definitelyNotAnActivity;

        protected String doInBackground(Object... params) {
            definitelyNotAnActivity = params[2];
            return login((String)params[0], (String)params[1]);
        }

        protected void onPostExecute(String result) {
            ((LoginActivity)definitelyNotAnActivity).endpointResponse(result);
        }
    }

}
