package com.shrinkcom.alsaadceramicapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;


import java.util.Locale;

public class MyApplication extends Application {
    private Locale locale = null;
    private static final String TAG = "MyApplication";
    private static MyApplication mInstance;
    private RequestQueue mRequestQueue;
    public static volatile Handler applicationHandler = null;



    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (locale != null) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Configuration config = getBaseContext().getResources().getConfiguration();
        String lang = new SessionManager(getBaseContext()).getLanguage();

        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }
        super.onCreate();
        mInstance = this;


        applicationHandler = new Handler(getInstance().getMainLooper());

        //NativeLoader.initNativeLibs(App.getInstance());




        // init Simplify SDK with public api key stored in metadata
        try {
            Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;

            // init simplify api key
            //  String apiKey = "sbpb_ZWI1YTE4MGItYzIzOS00ZDRhLTg2NTEtNDczZTBjYjgzYzUz";
            //bundle.getString("com.simplify.android.sdk.apiKey", null);
         /*   if (apiKey != null) {

            }*/

            // init android pay public key
            String androidPayPublicKey = bundle.getString("com.simplify.android.sdk.androidPayPublicKey", null);
            if (androidPayPublicKey != null) {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
       // MultiDex.install(this);
    }

    public RequestQueue getReqQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToReqQueue(Request<T> req, String tag) {

        getReqQueue().add(req);
    }

    public <T> void addToReqQueue(Request<T> req) {

        getReqQueue().add(req);
    }



    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }



}