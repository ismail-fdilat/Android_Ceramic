package com.shrinkcom.alsaadceramicapp.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.SelectYourCity;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.pojo.ProductModel;

import com.shrinkcom.alsaadceramicapp.storage.Mycart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class Utilitynew {
  public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public static boolean checkPermission(final Context context) {
    int currentAPIVersion = Build.VERSION.SDK_INT;
    if (currentAPIVersion >= Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
          AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
          alertBuilder.setCancelable(true);
          alertBuilder.setTitle("Permission necessary");
          alertBuilder.setMessage("External storage permission is necessary");
          alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {
              ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
          });
          AlertDialog alert = alertBuilder.create();
          alert.show();

        } else {
          ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }


  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static void updateResources(Activity context, String language) {
    try {
      SessionManager sessionManager = new SessionManager(context);
      Resources res = context.getResources();
      DisplayMetrics dm = res.getDisplayMetrics();
      android.content.res.Configuration conf = res.getConfiguration();

      if (!TextUtils.isEmpty(sessionManager.getLanguage())) {
        if (sessionManager.getLanguage().equalsIgnoreCase("english")) {
          conf.setLocale(new Locale("en"));

        } else {
          conf.setLocale(new Locale("ar"));

        }
      } else {
        String defaultDeviceLanguage = Locale.getDefault().getDisplayLanguage();
        sessionManager.setLanguage("ar");
        conf.setLocale(new Locale("ar"));
      }
      res.updateConfiguration(conf, dm);
      NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
      notificationManager.cancel(100);

    } catch (Exception e) {

      Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
    }
  }

  //--------------------------------------------------------------------------------------------------------//

  public static int getCount(Context activity) {

    String userid;
    if (UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID).equals("")){
      userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERDEVICEID);
    }else {
      userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID);
    }

    int value = 0;
    Mycart mycartdb = new Mycart(activity);
    mycartdb.open();
    Cursor c = mycartdb.getAllDatashopping(userid);
    value = c.getCount();
    c.close();
    return value;
  }

  public static void deleteAllDatashopping(Activity activity) {
    Mycart mycartdb = new Mycart(activity);
    mycartdb.open();
    mycartdb.close();
  }

  public static void deletproductshopping(Context activity, String productic) {
    Mycart mycartdb = new Mycart(activity);
    mycartdb.open();
    mycartdb.deleteproduct(productic);
    mycartdb.close();
  }

  public static void updateproductshopping(Context activity, String productic, String quantity) {
    Log.e("UPDATEDD", ">>" + productic + " : " + quantity);
    Mycart mycartdb = new Mycart(activity);
    mycartdb.open();
    mycartdb.updateQuantity(productic, quantity);
    mycartdb.close();
  }

  public static List<ProductModel> getAllProduct(Context activity) {

    String userid;
    if (UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID).equals("")){
      userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERDEVICEID);
    }else {
      userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID);
    }

    Mycart mycartdb = new Mycart(activity);
    mycartdb.open();
    Cursor cursor = mycartdb.getAllDatashopping(userid);
    List<ProductModel> plist = new ArrayList<>();
    if (cursor.moveToFirst()) {
      do {


        String pid = cursor.getString(1);
        String pname = cursor.getString(2);
        String pimage = cursor.getString(3);
        String pprice = cursor.getString(4);
        String pquantity = cursor.getString(5);
        String pdescription = cursor.getString(6);
        String psizee = cursor.getString(8);
        String psizee_name = cursor.getString(9);
        String mainqtry =cursor.getString(cursor.getColumnIndex(Mycart.mainQty));


   Log.v("DIKSHAA","pid>>>>"+pid);
   Log.v("DIKSHAA","pname>>>>"+pname);
   Log.v("DIKSHAA","pimage>>>>"+pimage);
   Log.v("DIKSHAA","price>>>>"+pprice);
   Log.v("DIKSHAA","pquantity>>>>"+pquantity);
   Log.v("DIKSHAA","pdescription>>>>"+pdescription);
   Log.v("DIKSHAA","psizee>>>>"+psizee);
   Log.v("DIKSHAA","psizee_name>>>>"+psizee_name);


        ProductModel pmodel = new ProductModel();
        pmodel.setPid(pid);
        pmodel.setPname(pname);
        pmodel.setPimage(pimage);
        pmodel.setPprice(pprice);
        pmodel.setPquantity(pquantity);
        pmodel.setDesp(pdescription);
        pmodel.setPsize(psizee);
        pmodel.setSize_name(psizee_name);
        pmodel.setMainQty(mainqtry);




        plist.add(pmodel);
      } while (cursor.moveToNext());
    }
    return plist;
  }

  public static int getTotalProduct(Context mContext) {

    String userid;
    if (UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID).equals("")){
      userid = UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERDEVICEID);
    }else {
      userid = UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID);
    }

    Mycart mycartdb = new Mycart(mContext);
    // mycartdb.open();
    Cursor cursor = mycartdb.getAllDatashopping(userid);
    return cursor.getCount();

  }

  public static int checkAvailability(Context activity, String productic) {
    Mycart mycartdb = new Mycart(activity);
    mycartdb.open();
    int availty = mycartdb.checkAvailableshopping(productic);
    mycartdb.close();
    return availty;
  }

  public static void logoutUser(final Context mContext) {

    new android.app.AlertDialog.Builder(mContext)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Are you sure you want to logout this App ?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                Token(mContext);


                      /*  Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        mContext.sendBroadcast(broadcastIntent);*/
              }

            })
            .setNegativeButton("No", null)
            .show();
  }


  // TODO: 2/27/2020  get Couintrycode
  public static String getCountryCode(String countryName) {
    String[] isoCountryCodes = Locale.getISOCountries();
    for (String code : isoCountryCodes) {
      Locale locale = new Locale("Algeria", code);
      if (countryName.equalsIgnoreCase(locale.getDisplayCountry())) {
        return code;
      }
    }
    return "";
  }

  public static void UserAlert(final Context mContext, String message) {

    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
    builder.setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                //do things
              }
            });
    AlertDialog alert = builder.create();
    alert.show();
  }

  public static String getDeviceid(Context mContext) {
    String imei = null;
    /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      TelephonyManager tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
      if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
           imei = tm.getImei();
          Log.e("IMEINO", ">>IFFG" + imei);
          //   UserSession.getInstance(SplashScreen.this).writePrefs(UserSession.PREFS_USERID,imei);
        }
      } else {
        if (Build.VERSION.SDK_INT==Build.VERSION_CODES.Q){
          imei  = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }else {
          imei = tm.getDeviceId();
        }
        Log.e("IMEINO", ">>" + imei);
        // UserSession.getInstance(SplashScreen.this).writePrefs(UserSession.PREFS_USERID,dvidimei);


      }
    }*/

    imei  = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    Log.e("IMEINO", ">>" + imei);
    return imei;
  }

  public static void Token(final Context context) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
            new com.android.volley.Response.Listener<String>() {
              @Override
              public void onResponse(String response) {
                Log.e("RESPONSEEEEGALTOKEN", ">>" + response);

                UserSession.getInstance(context).clearPrefs();
                Intent intent = new Intent(context, SelectYourCity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ((Activity) context).finish();
                context.startActivity(intent);

              }
            },
            new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }) {


      @Override
      protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();

        SharedPreferences prefs = context.getSharedPreferences("MYAPPNAME", MODE_PRIVATE);
        String name = prefs.getString("DEVICEID", "");

        if (UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID).equals("")){
          params.put("userid", "");
          params.put("random_id", "" +name);
          params.put("type", "logout");

        }else {
          params.put("userid", "" + UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID));
          params.put("random_id", "" +name);
          params.put("type", "logout");
        }
        params.put("action","update_token");
        params.put("token", "" + FirebaseInstanceId.getInstance().getToken());
        Log.e("sendvalueTOKEN", params.toString());
        return params;
      }
    };

    VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

  }





  public  static void getlatLongByCity(Context mContext, String cityName){

        if(Geocoder.isPresent()){
            try {
                Geocoder gc = new Geocoder(mContext);
                List<Address> addresses= gc.getFromLocationName(cityName, 5); // get the found Address Objects

                List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                for(Address a : addresses){
                    if(a.hasLatitude() && a.hasLongitude()){
                        ll.add(new LatLng(a.getLatitude(), a.getLongitude()));

                        Log.e("LATLONGUSIGCITY",">>>>> "+ ll);                       ll.add(new LatLng(a.getLatitude(), a.getLongitude()));

                    }
                }
            } catch (IOException e) {
                // handle the exception
                Log.e("LATLONGUSIGCITY",">>>>>e  "+e);
            }
        }else {
            Log.e("LATLONGUSIGCITY",">>>>> No GeoCoder found");
        }

    }

}
