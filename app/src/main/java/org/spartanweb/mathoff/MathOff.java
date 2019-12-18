package org.spartanweb.mathoff;

import android.app.Application;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class MathOff extends Application {
    private static final String endpoint = "https://alt.spartanweb.org/api/mathoff";
    private String sessionToken;

    public String getToken() {
        return sessionToken;
    }

    public void setToken(String token) {
        sessionToken = token;
    }

    public static int toDp(int dp, DisplayMetrics dm) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm);
    }

    public static String httpsRequest(Map<String, String> parameters) {
        try {
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
            String response = in.readLine();
            in.close();
            if (response == null) {
                response = "";
            }
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
