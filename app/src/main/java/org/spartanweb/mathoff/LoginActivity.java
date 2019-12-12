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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String loginEndpoint = "https://alt.spartanweb.org/tolken";

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
                lt.execute(loginEndpoint, userInput.getText().toString(), passwordInput.getText().toString(), bruh);
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

    private static class LoginTask extends AsyncTask<Object, Void, String> {
        private Object definitelyNotAnActivity;

        protected String doInBackground(Object... params) {
            definitelyNotAnActivity = params[3];
            return login((String)params[0], (String)params[1], (String)params[2]);
        }

        protected void onPostExecute(String result) {
            ((LoginActivity)definitelyNotAnActivity).endpointResponse(result);
        }
    }

    private static String login(String endpoint, String username, String password) {
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put("user", username);
            parameters.put("password", password);

            StringBuilder paramJoiner = new StringBuilder();
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                paramJoiner.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                paramJoiner.append("=");
                paramJoiner.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                paramJoiner.append("&");
            }
            String fullParams = paramJoiner.toString();
            fullParams = fullParams.substring(0, fullParams.length() - 1);

            URL url = new URL(endpoint);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(fullParams.length()));
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.connect();

            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(fullParams);
            out.flush();
            out.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String sessionToken = in.readLine();
            in.close();
            return sessionToken;
        } catch (IOException e) {
            return "&";
        }
    }

}
