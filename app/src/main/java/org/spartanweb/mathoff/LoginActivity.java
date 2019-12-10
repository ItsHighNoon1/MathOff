package org.spartanweb.mathoff;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String loginEndpoint = "https://alt.spartanweb.org/tolken";

    private EditText userInput;
    private EditText passwordInput;
    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        test = findViewById(R.id.username_prompt);
    }

    public void login(View view) {
        try {
            URL url = new URL(loginEndpoint);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            Map<String, String> parameters = new HashMap<String, String>();
            parameters.put("user", userInput.getText().toString());
            parameters.put("password", passwordInput.getText().toString());

            StringBuilder paramJoiner = new StringBuilder();
            for (Map.Entry<String, String> param : parameters.entrySet()) {
                paramJoiner.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                paramJoiner.append("=");
                paramJoiner.append(URLEncoder.encode(param.getValue(), "UTF-8"));
                paramJoiner.append("&");
            }
            String fullParams = paramJoiner.toString();
            fullParams = fullParams.substring(0, fullParams.length() - 1);

            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(fullParams);
            out.flush();
            out.close();

            conn.setRequestProperty("Content-Type", "application/json");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String sessionToken = in.readLine();
            test.setText(sessionToken);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            test.setText("bad url");
        } catch (IOException e) {
            e.printStackTrace();
            test.setText(e.getMessage());
        }
    }

}
