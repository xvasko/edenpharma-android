package sk.sytam.eden_pharma.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import sk.sytam.eden_pharma.App;

public class SharedPref {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static volatile SharedPref instance;

    public static SharedPref getInstance() {
        if (instance == null) {
            synchronized (SharedPref.class) {
                if (instance == null) {
                    instance = new SharedPref();
                }
            }
        }
        return instance;
    }

    private SharedPref() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getAppContext());
        editor = preferences.edit();

    }

    public String getUsername() {
        return preferences.getString("@string/session_username", null);
    }

    public void setUsername(String username) {
        editor.putString("@string/session_username", username);
        editor.commit();
    }

    public String getToken() {
        return preferences.getString("@string/session_token", null);
    }

    public void setToken(String token) {
        editor.putString("@string/session_token", token);
        editor.commit();
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

}
