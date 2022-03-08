package com.shrinkcom.alsaadceramicapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.LoginPojo;
import com.shrinkcom.alsaadceramicapp.pojo.facebookpojo.Facebookloginpojo;
import com.shrinkcom.alsaadceramicapp.pojo.googleloginpojo.Googleloginpojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;


import org.json.JSONException;
import org.json.JSONObject;

import static com.google.firebase.auth.GoogleAuthProvider.getCredential;


public class LoginActivity extends AppCompatActivity  {

    EditText edt_emaillg, edt_pass;
    TextView tv_forgetpass, tv_createac, tv_facebook, tv_login;
    String UserEmail, Password,email;
    Button tv_google;
    private String TAG = "LoginActivity";

    // TODO: 2/15/2020 fblogin

    LoginButton loginButton;
    CallbackManager callbackManager;

    // TODO: 2/15/2020 gmail login


    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;

    private SignInButton btnSignIn;
    private Button btnSignOut, btnRevokeAccess,login_buttonf;

    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    TextView textView;
    private static final int RC_SIGN_IN = 1;
    protected GoogleSignInClient mGoogleSignInClient;
    protected FirebaseAuth mAuth;
    String emailfb,first_name,strname;
    private Fragment activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        setupGoogleSigninClient();
        initview();



        // TODO: 2/15/2020 fblogincode

       // loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.setReadPermissions("email", "public_profile");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                //loginResult.getAccessToken();
                //loginResult.getRecentlyDeniedPermissions()
                //loginResult.getRecentlyGrantedPermissions()

                getUserProfile(AccessToken.getCurrentAccessToken());


            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("errorfacebook",">>>"+exception);
                // App code
            }
        });




    }


    private void initview() {
        loginButton = findViewById(R.id.login_button);
        edt_emaillg = findViewById(R.id.edt_emaillg);
        edt_pass = findViewById(R.id.edt_pass);
        tv_forgetpass = findViewById(R.id.tv_forgetpass);
        tv_createac = findViewById(R.id.tv_createac);
        tv_google = findViewById(R.id.tv_google);
        login_buttonf = findViewById(R.id.login_buttonf);
        tv_login = findViewById(R.id.tv_login);
        login_buttonf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });

        btnSignOut = (Button) findViewById(R.id.btn_sign_out);
        btnRevokeAccess = (Button) findViewById(R.id.btn_revoke_access);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        tv_forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                LoginActivity.this.startActivity(intent);
                (LoginActivity.this).finish();
            }
        });


        tv_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSignIn();
            }
        });


        tv_createac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivty.class);
                LoginActivity.this.startActivity(intent);
            }
        });
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }


    void validation() {

        UserEmail = edt_emaillg.getText().toString().trim();
        Password = edt_pass.getText().toString().trim();

        Log.e(TAG, "validation: >>" + UserEmail);
        Log.e(TAG, "validation: >>" + Password);

        if (!emailValidator(UserEmail)) {
            edt_emaillg.setError(getString(R.string.title_Emailisrequired));
            edt_emaillg.requestFocus();

        } else {

            if (Password.isEmpty()) {
                edt_pass.setError(getString(R.string.title_passwordisrequired));
                edt_pass.requestFocus();

            } else {
                submitLogin();
            }

        }


    }


    private void submitLogin() {

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(LoginActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                        Log.e("msg========", "getTokenApi reponse check  :  " + response);
                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();



                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();

                                LoginPojo loginPojo = gson.fromJson(response, LoginPojo.class);
                                Log.v("DIKSHAAA",">>>"+loginPojo.getStatus());
                                if (loginPojo.getStatus() == 1) {

                                    String email = loginPojo.getResponse().getEmail();
                                    String id = loginPojo.getResponse().getId();
                                    String name = loginPojo.getResponse().getUserFname();
                                    String mobile = loginPojo.getResponse().getMobile();
                                    String avtar = loginPojo.getResponse().getAvatar();
                                    String city = loginPojo.getResponse().getCity();
                                    String city_name = loginPojo.getResponse().getCity_name();
                                    Log.e("Email",">>>>>>"+email);
                                    Log.e("IDDDDDD",">>>>>>"+id);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_EMAIL, email);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_USERID, id);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PROFILEFULLNAME, name);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.MOBILENO, mobile);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PROFILEIMAGE, avtar);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.City, city);
                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.city_name, city_name);
                                    UserSession.getInstance(LoginActivity.this).writeBooleanPrefs(UserSession.PREFS_USERSESSIOIN,true);

                                    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_LOGINTYPE, loginPojo.getResponse().getType());
                                    Intent intent = new Intent(LoginActivity.this, SelectYourCity.class);
                                    LoginActivity.this.startActivity(intent);
                                    (LoginActivity.this).finish();

                                } else {
                                    progressDialog.dismiss();

                                    Toast.makeText(LoginActivity.this, loginPojo.getMessage(), Toast.LENGTH_SHORT).show();


                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.v("DIKSHAAA",">>>"+e);

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                HashMap<String, String> params = new HashMap<>();
                params.put("action", "Login");
                params.put("email", UserEmail);
                params.put("password", Password);

                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    // TODO: 2/15/2020 fbloginapi


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            emailfb = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";
                            LoginManager.getInstance().logOut();

                            facebooklogin();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }


    // TODO: 2/15/2020 gmail login code


    private void setupGoogleSigninClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("854286222033-q33341nvbg2hhbvqv0hsh6aksu14g5gv.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {

                GoogleSignInAccount account = task.getResult(ApiException.class);
                signInWithGoogle(account);


            } catch (ApiException e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
    }
    public void signInWithGoogle(GoogleSignInAccount acct) {
        if (acct != null) {
            Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
            AuthCredential credential = getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("error", "signInWithCredential:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                strname =    mAuth.getCurrentUser().getDisplayName();
                                email =    mAuth.getCurrentUser().getEmail();
                                String phome =    mAuth.getCurrentUser().getPhoneNumber();
                                gmaillogin();
                                Toast.makeText(LoginActivity.this, "User Signed In", Toast.LENGTH_SHORT).show();
                            } else {

                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

//        mAuth.GoogleSignInClient.signOut(mGoogleSignInClient).setResultCallback(
//                new ResultCallback<Status>() {
//                    @Override
//                    public void onResult(Status status) {
//
//                    }
//                });
    }
    public void performSignIn() {

        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    public void facebooklogin(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSSEEEEEfbLogin",">>>"+response);
                        pDialog.hide();
                        try {

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            Facebookloginpojo facebookLoginPojo = gson.fromJson(response, Facebookloginpojo.class);

                            if (facebookLoginPojo.getStatus() == 1) {
                               // Token();
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_USERID, facebookLoginPojo.getResponse().getId());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_EMAIL, facebookLoginPojo.getResponse().getEmail());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PROFILEFULLNAME, facebookLoginPojo.getResponse().getUserFname());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_LOGINTYPE, facebookLoginPojo.getResponse().getLoginBy());

                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                LoginActivity.this.startActivity(intent);

                            } else {
                                Toast.makeText(LoginActivity.this,"LOgin Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", ""+emailfb);
                params.put("username", ""+first_name);
                params.put("action", "SocialLogin");
                params.put("login_by", "facebook");
                Log.e("paramss",">>"+params);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void gmaillogin(){

        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSSEEEEEGmailLogin",">>>"+response);
                        pDialog.hide();
                        try {
                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();

                            Googleloginpojo googleloginpojo = gson.fromJson(response, Googleloginpojo.class);

                            if (googleloginpojo.getStatus() == 1) {
                              //  Token();
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_USERID, googleloginpojo.getResponse().getId());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_EMAIL, googleloginpojo.getResponse().getEmail());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PROFILEFULLNAME, googleloginpojo.getResponse().getUserFname());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.MOBILENO, googleloginpojo.getResponse().getMobile());
                                //    UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_USERPROFILE, facebookLoginPojo.getResponse().getAvatar());
                                UserSession.getInstance(LoginActivity.this).writePrefs(UserSession.PREFS_LOGINTYPE, googleloginpojo.getResponse().getLoginBy());

                                Intent intent = new Intent(LoginActivity.this, Home.class);
                                LoginActivity.this.startActivity(intent);

                            } else {
                                Toast.makeText(LoginActivity.this,"LOgin Failed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", ""+email);
                params.put("username", ""+strname);
                params.put("action", "SocialLogin");
                params.put("login_by", "google");
                Log.e("paramss",">>"+params);
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



    @Override
    public void onBackPressed() {

        Intent intent = new Intent(LoginActivity.this, Home.class);
        LoginActivity.this.startActivity(intent);

    }

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
                if (UserSession.getInstance(LoginActivity.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("userid", "");
                    params.put("random_id", "" + UserSession.getInstance(LoginActivity.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                    params.put("type", "guest");

                }else {
                    params.put("userid", "" + UserSession.getInstance(LoginActivity.this).readPrefs(UserSession.PREFS_USERID));
                    params.put("random_id", "" + UserSession.getInstance(LoginActivity.this).readPrefs(UserSession.PREFS_USERDEVICEID));
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

}
