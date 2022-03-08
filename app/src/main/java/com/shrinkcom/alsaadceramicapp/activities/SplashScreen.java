package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.shrinkcom.alsaadceramicapp.R;

import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.PermissionChecker;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Random;

public class SplashScreen extends AppCompatActivity {


    private static int SPLASH_SCREEN_TIME_OUT = 2000;

    String currentLanguage = "en", currentLang;
    Locale myLocale;
    Handler handler;
    SessionManager sessionManager;
    PermissionChecker permissionChecker = new PermissionChecker();
    String[] RequiredPermissions = {Manifest.permission.READ_PHONE_STATE};
    String  randomNumber;

    @Override
    protected void onRestart() {
        super.onRestart();


        launchApp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.shrinkcom.alsaadhomeapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        printHashKey();

        handler = new Handler();
        Random r = new Random();
        final int min = 200000;
        final int max = 800000;
        randomNumber = String.valueOf(new Random().nextInt((max - min) + 1) + min);
        Log.e("randomno",">>>"+randomNumber);


        final Mycart addressDb=new Mycart(this);
        addressDb.open();


        Cursor cursor=addressDb.getAllDataUNIWUEID();
        Log.e("COURCERCOUNT",">>"+cursor.getCount());

        if (cursor.getCount()>0){
            if (cursor.moveToFirst()){
                do {
                    String deviceid =  cursor.getString(1);

                    SharedPreferences.Editor editor = getSharedPreferences("MYAPPNAME", MODE_PRIVATE).edit();
                    editor.putString("DEVICEID", deviceid);
                    editor.apply();

                    UserSession.getInstance(SplashScreen.this).writePrefs(UserSession.PREFS_USERDEVICEID, deviceid); ;
                    Log.e("randomnum",">>>"+deviceid);
                }while (cursor.moveToNext());
            }

        }else {
            addressDb.insertDataUNIQUEID(randomNumber);

        }

        launchApp();

    }

    void launchApp(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (UserSession.getInstance(SplashScreen.this).readPrefs(UserSession.City).equals("")){
                    Intent i=new Intent(SplashScreen.this,
                            SelectYourCity.class);
                    startActivity(i);
                    finish();

                }else {
                    Intent i=new Intent(SplashScreen.this,
                            Home.class);
                    startActivity(i);
                    finish();
                }



            }
        }, SPLASH_SCREEN_TIME_OUT);

    }





/*    private void checkPermissions() {
        permissionChecker.verifyPermissions(this, RequiredPermissions, new PermissionChecker.VerifyPermissionsCallback() {
            @Override
            public void onPermissionAllGranted() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void run() {


                                if (UserSession.getInstance(SplashScreen.this).readBooleanPrefs(UserSession.PREFS_USERSESSIOIN)) {
                                    UserSession.getInstance(SplashScreen.this).writePrefs(UserSession.PREFS_USERDEVICEID,""); ;
                                } else {
                                    UserSession.getInstance(SplashScreen.this).writePrefs(UserSession.PREFS_USERDEVICEID, String.valueOf(randomNumber)); ;
                                }
                                Intent i = new Intent(SplashScreen.this,
                                        MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }, 1000);

                    }
//
                }, 1 * 1000);
            }

            @Override
            public void onPermissionDeny(String[] permissions) {
                Utilitynew.UserAlert(SplashScreen.this,getString(R.string.permite_permisssion));
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void printHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.shrinkcom.alsaadhomeapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }*/


    private void printHashKey() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.shrinkcom.alsaadhomeapp",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
