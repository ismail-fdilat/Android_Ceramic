package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.citypojo.Citymodel;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.http.GET;
import retrofit2.http.Url;

public class RegisterActivty extends AppCompatActivity implements  AdapterView.OnItemSelectedListener,View.OnClickListener {

    EditText edt_username,edt_mobileno,edt_email,edt_pass,edt_cpass;
    TextView tv_signup,tv_loginsi;

    String Username,Mobileno,Email,Password,ConPassword;

    TextView registerWithSeller;

    Citymodel citymodel;
    List<com.shrinkcom.alsaadceramicapp.pojo.citypojo.Response> responses = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListid = new ArrayList<>();

    SearchableSpinner spinner_city;
    Context context;
    String city_id ="" , country_id="" , state_id="" ,country_id_temp="", state="", state_id_temp="", city_id_tep="",city_name;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activty);
        initviews();
    }

    private void initviews() {
        edt_username=findViewById(R.id.edt_username);
        edt_mobileno=findViewById(R.id.edt_mobileno);
        edt_email=findViewById(R.id.edt_email);
        edt_pass=findViewById(R.id.edt_pass);
        edt_cpass=findViewById(R.id.edt_cpass);
        spinner_city = findViewById(R.id.spinner_city);

        tv_signup=findViewById(R.id.tv_signup);
        tv_loginsi=findViewById(R.id.tv_loginsi);

        tv_signup.setOnClickListener(this);
        tv_loginsi.setOnClickListener(this);
        context = this;

        registerWithSeller = findViewById(R.id.register_with_seller);


        registerWithSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 6/29/2021  seller Register
                Intent intent = new Intent(RegisterActivty.this,SellerRegistar.class);
                startActivity(intent);
            }
        });

        GetCity();


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


        Username = edt_username.getText().toString().trim();
        Email = edt_email.getText().toString().trim();
        Password = edt_pass.getText().toString().trim();
        Mobileno = edt_mobileno.getText().toString().trim();
        ConPassword=edt_cpass.getText().toString().trim();

        if (Username.isEmpty())
        {

            edt_username.setError(getString(R.string.title_userisrequired));
            edt_username.requestFocus();

        }

        else if (city_id_tep.equals("")){
            Toast.makeText(context, "Please select city", Toast.LENGTH_SHORT).show();
            return;
        }else {
            if (!emailValidator(Email)) {
                edt_email.setError(getString(R.string.title_Emailisrequired));
                edt_email.requestFocus();
            } else {
                if (Password.isEmpty()) {
                    edt_pass.setError(getString(R.string.title_passwordisrequired));
                    edt_pass.requestFocus();

                } else {
                    if (ConPassword.isEmpty()) {
                        edt_cpass.setError(getString(R.string.title_cpassisrequired));
                        edt_cpass.requestFocus();

                    } else {

                        if (Mobileno.isEmpty()) {
                            edt_mobileno.setError(getString(R.string.title_mobileisrequired));
                            edt_mobileno.requestFocus();
                        } else {
                            if (Mobileno.length() <9) {
                                edt_mobileno.setError("Please Enter valid number with country code");
                                edt_mobileno.requestFocus();
                            } else if (!Password.equals(ConPassword)) {
                                edt_cpass.setError(getString(R.string.title_correct));
                                edt_cpass.requestFocus();

                            } else {
                                submitRegister();
                            }

                        }


                    }


                }
            }
        }


        }

    private void submitRegister() {
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivty.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {



                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                JSONObject jo=new JSONObject(response);
                                if (jo.getInt("status")==1)
                                {
                                    //Token();
                               Intent intent =  new Intent(RegisterActivty.this, LoginActivity.class);
                               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                               finish();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivty.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            Utilitynew.UserAlert(RegisterActivty.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(RegisterActivty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


                HashMap<String, String> params = new HashMap<>();
                params.put("action", "Register");
                params.put("email", Email);
                params.put("password", Password);
                params.put("phone", Mobileno);
                params.put("user_fname", Username);
                params.put("user_lname", "");
                params.put("city", city_id_tep);

                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivty.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_signup:
                validation();
                break;

            case R.id.tv_loginsi:

                startActivity(new Intent(RegisterActivty.this, LoginActivity.class));

                break;

        }

    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(RegisterActivty.this, Home.class);
        RegisterActivty.this.startActivity(intent);

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
                if (UserSession.getInstance(RegisterActivty.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("userid", "");
                    params.put("random_id", "" + UserSession.getInstance(RegisterActivty.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                    params.put("type", "guest");

                }else {
                    params.put("userid", "" + UserSession.getInstance(RegisterActivty.this).readPrefs(UserSession.PREFS_USERID));
                    params.put("random_id", "" + UserSession.getInstance(RegisterActivty.this).readPrefs(UserSession.PREFS_USERDEVICEID));
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

    private void   GetCity()  {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "GET_COUNTRIES_RESPONSE>>" + response);

                        try {

                            if (true) {


                                Log.v("DIKSHA", "LOGIN_RESPONSE" + response);

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
//                                progressDialog.dismiss();

                                Citymodel pojo = gson.fromJson(response, Citymodel.class);
                                responses = pojo.getResponse();

                                if (pojo.getStatus() == 1) {

                                    if (pojo.getResponse().size() > 0) {

                                        citymodel = gson.fromJson(response, Citymodel.class);
                                        arrayList.clear();
                                        arrayListid.clear();
                                        arrayList.add("Enter City");
                                        arrayListid.add("");
                                        // APIClientLogin();

                                        for (int i=0 ; i<responses.size();i++){
                                            arrayList.add(citymodel.getResponse().get(i).getCityName());
                                            arrayListid.add(citymodel.getResponse().get(i).getId());
                                        }

                                        setDataToAdapter(arrayList);

                                        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                city_id_tep= arrayListid.get(i);
//                                                city_name = arrayList.get(i);
//                                                city_name= citymodel.getResponse().get(i).getCityName();
//                                                Log.d("cityid",city_name);


                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });



                                    } else {

                                    }


                                    //   home_recycler_view.setLayoutManager(mLayoutManager);

                                } else {

                                }

                            } else {
                            }

                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                            Log.v("DIKSHAAAA", "ERROR>>>>" + e);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("DIKSHAAAA", "ERROR>>>>" + error.getMessage());
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action","city_list");
                params.put("city_name",city_id);


                return params;

            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void setDataToAdapter(ArrayList<String> arrayList)
    {
        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayList)

        {
            public View getView(int position , View convertView, ViewGroup parent)
            {
                View view1 = super.getView(position,convertView,parent);
                ((TextView)view1).setTextSize(14);
                ((TextView)view1).setTextColor(getColor(R.color.black));


                return view1;
            }
        };
        // Specify layout to be used when list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner_city.setAdapter(arrayAdapter);


//        for (int i = 0; i <citymodel.getResponse().size(); i++) {
//            Log.e("cityCountryselection",">> "+arrayList.get(i)+" "+citySelected);
//            if (citymodel.getResponse().get(i).getId().equals(citySelected)){
//                spinner_city.setSelection(i);
//                break;
//            }
//        }

        spinner_city.setOnItemSelectedListener(RegisterActivty.this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

