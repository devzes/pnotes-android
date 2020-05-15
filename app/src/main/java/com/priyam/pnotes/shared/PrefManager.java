package com.priyam.pnotes.shared;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    // For storing shared preferences
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    // Context from where it is called
    Context _context;

    //Private mode
    private int PRIVATE_MODE=0;

    // Setting the preferences name
    private static final String PREF_NAME = "android-priyam-pnotes";

    // Start keys from here
    private static String isLogin = "isLogin";
    private static String email = "email";

    // Constructor
    public PrefManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);  // Shared Preferences
        editor = pref.edit(); //Editor
    }

    public boolean getIsLogin(){
        return pref.getBoolean(isLogin, false);
    }

    public void setIsLogin(boolean value){
        editor.putBoolean(isLogin, value);
        editor.commit();
    }

    public String getEmail() {
        return pref.getString(email, "");
    }

    public void setEmail(String value){
        editor.putString(email, value);
        editor.commit();
    }

}
