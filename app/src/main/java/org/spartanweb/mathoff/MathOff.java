package org.spartanweb.mathoff;

import android.app.Application;

public class MathOff extends Application {
    private String sessionToken;

    public String getToken() {
        return sessionToken;
    }

    public void setToken(String token) {
        sessionToken = token;
    }
}
