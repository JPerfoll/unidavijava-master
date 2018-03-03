package br.edu.unidavi.unidavijava.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private final String FIELD_USERNAME = "username";
    private final String FIELD_PROFILE_URL = "profile_url";
    private final String PREF_NAME = "session";

    /*public void saveEmailInSession(String emailValue, SharedPreferences sharedPreferencesParam) {
        SharedPreferences sharedPreferences = sharedPreferencesParam;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIELD_USERNAME, emailValue);
        editor.commit();
    }

    public String getEmailInSession(SharedPreferences sharedPreferencesParam) {
        SharedPreferences sharedPreferences = sharedPreferencesParam;
        return sharedPreferences.getString(FIELD_USERNAME,"");
    }*/

    public void saveEmailInSession(Context context, String emailValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIELD_USERNAME, emailValue);
        editor.commit();
    }

    public String getEmailInSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIELD_USERNAME,"");
    }

    public void saveProfileURLInSession(Context context, String profileURLValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(FIELD_PROFILE_URL, profileURLValue);
        editor.commit();
    }

    public String getURLProfileInSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(FIELD_PROFILE_URL,"");
    }
}
