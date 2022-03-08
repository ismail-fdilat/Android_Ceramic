package com.shrinkcom.alsaadceramicapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSession {

    private static UserSession _instance = null;
    private static SharedPreferences _sPrefs,_sPrefs2 = null;
    private static SharedPreferences.Editor _editor = null;

    private static final String OMAN_APP_PREFS = "PRVIDERByPriya";
    private static final String OMAN_APP_PREFS_2 = "PROVIDERByPriyaN";



    public static final String PREFS_EMAIL = "email";
    public static final String PREFS_USERID = "id";
    public static final String PREFS_WISHID = "wishid";
    public static final String PROFILEIMAGE = "avatar";
    public static final String City = "city";
    public static final String City_NAME = "cityNAME";
    public static final String PROFILEFULLNAME = "user_fname";
    public static final String MOBILENO = "mobile";
    public static final String PRODUCTID = "productid";
    public static final String AMOUNT = "amountdata";
    public static final String SHIPSTATUS = "status";
    public static final String WHATSAPPNUM = "whatsappno";
    public static final String ADDRESS = "address";
    public static final String shipping_area = "shipping_area";
    public static final String shipping_city = "shipping_city";

    public static final String PREFS_LOGINTYPE = "userlogintype";
    public static final String PREFS_USERPROFILE = "userprofile";
    public static final String PREFS_USERSESSIOIN = "usersession";
    public static final String PREFS_USERDEVICEID= "deviceid";
    public static final String city_name= "city_name";



    public static final String PREFS_PROVIDER_SERVICE_ID = "Provider_Service_id";

    public UserSession() {
    }

    public UserSession(Context context) {
        _sPrefs = context.getSharedPreferences(OMAN_APP_PREFS,
                Context.MODE_PRIVATE);

        _sPrefs2 = context.getSharedPreferences(OMAN_APP_PREFS_2,
                Context.MODE_PRIVATE);
    }

    public static UserSession getInstance(Context context) {
        if (_instance == null) {
            _instance = new UserSession(context);
        }
        return _instance;
    }

    public String readPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "");
    }

    public void writePrefs(String pref_name, String pref_val) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_val);
        _editor.commit();
    }

    public void clearPrefs() {
        _editor = _sPrefs.edit();
        _editor.clear();
        _editor.commit();
    }

    public boolean readBooleanPrefs(String pref_name) {
        return _sPrefs.getBoolean(pref_name, false);
    }

    public void writeBooleanPrefs(String pref_name, boolean pref_val) {
        _editor = _sPrefs.edit();
        _editor.putBoolean(pref_name, pref_val);
        _editor.commit();
    }

    public String readDefaultLangPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "");
    }

    public void writeDefaultLangPrefs(String pref_name) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_name);
        _editor.commit();
    }

    public String readLatLngPrefs(String pref_name) {
        return _sPrefs.getString(pref_name, "0.0");
    }

    public void writeLatLngPrefs(String pref_name, String pref_val) {
        _editor = _sPrefs.edit();
        _editor.putString(pref_name, pref_val);
        _editor.commit();
    }

    public String readBackupPrefs(String pref_name) {
        return _sPrefs2.getString(pref_name, "");
    }

    public void writeBackupPrefs(String pref_name, String pref_val) {
        SharedPreferences.Editor _editor = _sPrefs2.edit();
        _editor.putString(pref_name, pref_val);
        _editor.commit();
    }


}
