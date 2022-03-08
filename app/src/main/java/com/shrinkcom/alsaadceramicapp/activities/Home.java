package com.shrinkcom.alsaadceramicapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shrinkcom.alsaadceramicapp.R;

import com.shrinkcom.alsaadceramicapp.fragment.AccountFragment;
import com.shrinkcom.alsaadceramicapp.fragment.CartFragment;
import com.shrinkcom.alsaadceramicapp.fragment.CategoryFragment;
import com.shrinkcom.alsaadceramicapp.fragment.HomeFragment;
import com.shrinkcom.alsaadceramicapp.fragment.WishlistFragment;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Home extends AppCompatActivity {
    private ActionBar toolbar;
    int currentpostion;
 public static BottomNavigationView navView;
  SwipeRefreshLayout mSwipeRefreshLayout;
  String from_checkout = "";

  public  static  int currentHome = -1;
   public  static String classname ;
    public  static  int CATEGORY_POSITION=0;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //   toolbar.setTitle("Home");
                    loadFragment(new HomeFragment());
                    classname = "HomeFragment";
                    return true;
                case R.id.navigation_dashboard:
                    loadFragment(new CategoryFragment());
                    classname = "CategoryFragment";
                    return true;
                case R.id.navigation_wishlist:
                    //toolbar.setTitle("Setting");
                    loadFragment(new WishlistFragment());
                    classname = "WishlistFragment";
                    return true;

                case R.id.navigation_cart:
                    //toolbar.setTitle("Setting");
                    loadFragment(new CartFragment());
                    classname = "CartFragment";
                    return true;
                    case R.id.navigation_account:
                    //toolbar.setTitle("Setting");
                    loadFragment(new AccountFragment());
                    classname = "AccountFragment";
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        toolbar=getSupportActionBar();
        Intent intent = getIntent();
        from_checkout = ""+intent.getStringExtra("from_checkout");
        Log.d("string>>>>",from_checkout);

        String id = FirebaseInstanceId.getInstance().getToken();
        Log.e("idToken",id);



        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.shrinkcom.alsaadceramicapp",
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


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getSupportActionBar()).hide();
        }
        toolbar.setTitle(getString(R.string.title_home));
       // loadFragment(new HomeFragment());
        Token();
        try {
            currentpostion = getIntent().getIntExtra("position", 0);
            Log.e("currentpostion", "<<>>" + currentpostion);

            if (currentpostion == 3) {
                CartFragment cartFragment = new CartFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, cartFragment)
                        .commit();
                navView.setSelectedItemId(R.id.navigation_cart);
                }
              else if (currentpostion == 4) {
                AccountFragment accountFragment = new AccountFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, accountFragment)
                        .commit();
            navView.setSelectedItemId(R.id.navigation_account);
            } else if (currentpostion == 2) {
                WishlistFragment wishlistFragment = new WishlistFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, wishlistFragment)
                        .commit();
            navView.setSelectedItemId(R.id.navigation_wishlist);

            } else if (currentpostion == 1) {
                CategoryFragment categoryFragment = new CategoryFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame, categoryFragment)
                        .commit();
                navView.setSelectedItemId(R.id.navigation_wishlist);
            }
              else {
            HomeFragment homeFragment = new HomeFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame, homeFragment)
                    .commit();
                navView.setSelectedItemId(R.id.navigation_home);

        }



        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SSSSSSSS", ">>>" + e);
        }


//        if (from_checkout.equals("checkout")){
//            AccountFragment accountFragment = new AccountFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame, accountFragment)
//                    .commit();
//        }else {
//            HomeFragment homeFragment = new HomeFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction()
//                    .replace(R.id.frame, homeFragment)
//                    .commit();
//            navView.setSelectedItemId(R.id.navigation_home);
//        }
    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
    }


    // TODO: 3/12/2020 token

    private void Token() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEEEEGALTOKEN", ">>" + response);
                        try {
                            //converting response to json object
                            JSONObject jsonObject = new JSONObject(response);
                            //  Toast.makeText(NavigationActivity.this, "token", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                SharedPreferences prefs = getSharedPreferences("MYAPPNAME", MODE_PRIVATE);
                String name = prefs.getString("DEVICEID", "");


                if (UserSession.getInstance(Home.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("userid", "");
                    params.put("random_id", "" + name);
                    params.put("type", "guest");

                }else {
                    params.put("userid", "" + UserSession.getInstance(Home.this).readPrefs(UserSession.PREFS_USERID));
                    params.put("random_id", "" +name);
                    params.put("type", "login");
                }
                params.put("action","update_token");
                params.put("token", "" + FirebaseInstanceId.getInstance().getToken());
                Log.e("sendvalueTOKEN", params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onResume() {
        super.onResume();
        // .... other stuff in my onResume ....
        /*this.doubleBackToExitPressedOnce = false;
        if (CATEGORY_POSITION==1){
            navView.setSelectedItemId(R.id.navigation_dashboard);
        }

        if (UserSession.getInstance(Home.this).readBooleanPrefs(UserSession.PREFS_USERSESSIOIN)) {
         //  UserSession.getInstance(Home.this).writePrefs(UserSession.PREFS_USERDEVICEID,""); ;
        } else {
           // UserSession.getInstance(Home.this).writePrefs(UserSession.PREFS_USERDEVICEID,Utilitynew.getDeviceid(Home.this)); ;
        }
*/
    }

    @Override
    public void onBackPressed() {




        Log.e("STACKENTRYCOUNT"," >>"+ getSupportFragmentManager().getBackStackEntryCount());


        if (getSupportFragmentManager().getBackStackEntryCount()==0){

            if (currentHome==-1){
                loadFragment(new HomeFragment());
                navView.setSelectedItemId(R.id.navigation_home);

            }else {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(getString(R.string.tv_exit))
                    .setCancelable(false)
                    .setPositiveButton(getString(R.string.tv_yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                          //  super.onBackPressed();
                        finish();
                        }
                    })
                    .setNegativeButton(getString(R.string.tv_no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();


            }

        }else {
            getSupportFragmentManager().popBackStack();
        }



        /*if (doubleBackToExitPressedOnce) {

            return;
        }else {

        this.doubleBackToExitPressedOnce = true;

       // Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
        }*/
    }
}
