package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hbb20.CountryCodePicker;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.AllAddressAdaptor;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckOutActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText etusername,etaddress,etemail,etnumber,etwhatsapp,et_shippingcity,et_shippingarea;
    Spinner spn_city;
    CountryCodePicker etcountry;
    String Username,Mobileno,Email,Address,Country,Zipcode,Userid,st_city,whatsapp,shipping_area,shipping_city;
    int int_city;
    Button btnnext;

     Toolbar toolbar;

    String[] city = { "Al Ain", "Ajman", " Abu Dhabi", "Dubai", "Sharjah",
            "Fujaira", " Ras al khaima", "Um al quwain","Khour Fakan"};

    // TODO: 3/18/2020 getshipp
    RecyclerView rv_alladress;
    LinearLayout nodata_alladress;
    AllAddressAdaptor allAddressAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        toolbar = findViewById(R.id.toolbar_checkout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Home.class);
//                intent.putExtra("from_checkout","checkout");
                intent.putExtra("position",4);
                startActivity(intent);
//                androidx.fragment.app.FragmentManager fragmentManager = CheckOutActivity.this.getFragmentManager();
//                androidx.fragment.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                AccountFragment fragment = new AccountFragment();
//                fragmentTransaction.add(R.id.frame, fragment); //ERROR ON THIS LINE
//                fragmentTransaction.commit();



            }
        });
        initviews();
        btnnext=findViewById(R.id.next_btn);
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });


        getShipApi();
        // TODO: 2/27/2020  get country code by country name

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),R.layout.item_city ,R.id.tv_itemcity,city);

// Setting Adapter to the Spinner
        spn_city.setAdapter(adapter);

// Setting OnItemClickListener to the Spinner
        spn_city.setOnItemSelectedListener(this);


        etusername.setText(Username);
        etnumber.setText(Mobileno);
        etemail.setText(Email);


    }
    public void onItemSelected(AdapterView parent, View view, int pos,
                               long id) {
        int_city=spn_city.getSelectedItemPosition();
        st_city=spn_city.getSelectedItem().toString();

    }

    // Defining the Callback methods here
    @Override
    public void onNothingSelected(AdapterView arg0) {
// TODO Auto-generated method stub

    }



    private void initviews() {
        etusername=findViewById(R.id.et_name);
        etnumber=findViewById(R.id.edt_mobileno);
        etaddress=findViewById(R.id.et_address);
        spn_city=findViewById(R.id.spn_city);
        etemail=findViewById(R.id.edt_email);
        rv_alladress = findViewById(R.id.rv_alladress);
        nodata_alladress = findViewById(R.id.nodata_alladress);
        etwhatsapp = findViewById(R.id.edt_whatsappno);
        et_shippingcity = findViewById(R.id.et_shippingcity);
        et_shippingarea = findViewById(R.id.et_shippingarea);


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
        Username = etusername.getText().toString().trim();
        Email = etemail.getText().toString().trim();
        whatsapp = etwhatsapp.getText().toString().trim();
        Address = etaddress.getText().toString().trim();
        Mobileno = etnumber.getText().toString().trim();
        shipping_area = et_shippingarea.getText().toString();
        shipping_city = et_shippingcity.getText().toString();
        if (Username.isEmpty()) {

            etusername.setError(getString(R.string.title_userisrequired));
            etusername.requestFocus();

        } else {
            if (!emailValidator(Email)) {
                etemail.setError(getString(R.string.title_Emailisrequired));
                etemail.requestFocus();
            } else {
                if (Mobileno.isEmpty()) {
                    etnumber.setError(getString(R.string.title_mobileisrequired));
                    etnumber.requestFocus();

                } else {
                    if (Mobileno.length() <9) {
                        etnumber.setError("Please Enter valid number with country code");
                        etnumber.requestFocus();
                    } else {
                        if (whatsapp.isEmpty()) {
                            etwhatsapp.setError("Number is required");
                            etwhatsapp.requestFocus();

                        } else {
                            if (whatsapp.length()<9) {
                                etwhatsapp.setError("Please Enter valid number with country code");
                                etwhatsapp.requestFocus();
                            } else {
                                if (Address.isEmpty()) {
                                    etaddress.setError(getString(R.string.title_adressisrequired));
                                    etaddress.requestFocus();

                                } else {

                                    if (shipping_city.isEmpty()) {
                                        et_shippingcity.setError(getString(R.string.title_shippingcityr));
                                        et_shippingcity.requestFocus();
                                    } else {
                                        if (shipping_area.isEmpty()) {
                                            et_shippingarea.setError(getString(R.string.titile_shippingarea));
                                            et_shippingarea.requestFocus();
                                        } else {
                                            AddShipApi();
                                        }


                                    }


                                }
                            }
                        }
                    }
                }
            }
        }
    }



                    


                

    //TODO 21-02-2020 ADDSHIPAPI

    //____________________________API Add Shhiping address details__________________________________________//



    private void AddShipApi() {
        final ProgressDialog progressDialog = new ProgressDialog(CheckOutActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
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


                                    Toast.makeText(CheckOutActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                      getShipApi();
                                   /* startActivity(new Intent(CheckOutActivity.this, CheckOutActivity.class));
                                    finish();*/
                                }
                                else
                                {
                                    Toast.makeText(CheckOutActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            Utilitynew.UserAlert(CheckOutActivity.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(CheckOutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "ShippingAddress");
                if (UserSession.getInstance(CheckOutActivity.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(CheckOutActivity.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(CheckOutActivity.this).readPrefs(UserSession.PREFS_USERID));
                }
                params.put("address", Address);
                params.put("username", Username);
                params.put("city", st_city);
                params.put("zipcode", "");
                params.put("country", "");
                params.put("email", Email);
                params.put("phone", Mobileno);
                params.put("whatsapp_no",whatsapp);

                params.put("shipping_area",shipping_area);
                params.put("shipping_city",shipping_city);
                Log.e("addship", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void getShipApi() {
   /*     final ProgressDialog pDialog = new ProgressDialog(CheckOutActivity.this);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                       /*     if (pDialog != null)
                            {
                                pDialog.dismiss();*/

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                JSONObject jo=new JSONObject(response);
                                if (jo.getInt("status")==1) {
                                    JSONArray contacts = jo.getJSONArray("response");
                                    for (int i = 0; i < contacts.length(); i++) {
                                        JSONObject c = contacts.getJSONObject(i);

                                        Mobileno = c.getString("phone");
                                        Email = c.getString("email");
                                        Username = c.getString("username");
                                        whatsapp = c.getString("whatsapp_no");
                                        Address = c.getString("address");
                                        shipping_area = c.getString("shipping_area");
                                        shipping_city = c.getString("shipping_city");

                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.PREFS_EMAIL, c.getString("email"));
                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.PROFILEFULLNAME, c.getString("username"));
                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.MOBILENO, c.getString("phone"));
                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.WHATSAPPNUM, c.getString("whatsapp_no"));
                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.ADDRESS, c.getString("address"));
                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.shipping_area, c.getString("shipping_area"));
                                        UserSession.getInstance(CheckOutActivity.this).writePrefs(UserSession.shipping_city, c.getString("shipping_city"));

                                        etusername.setText(Username);
                                        etnumber.setText(Mobileno);
                                        etemail.setText(Email);
                                        etwhatsapp.setText(whatsapp);
                                        etaddress.setText(Address);
                                        et_shippingarea.setText(shipping_area);
                                        et_shippingcity.setText(shipping_city);


                                    }
                                }
                                else {
                                    Toast.makeText(CheckOutActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }


                        } catch (Exception e) {


                    }
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOutActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
             //   pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("action","GetShippingAddress");
                if (UserSession.getInstance(CheckOutActivity.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(CheckOutActivity.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(CheckOutActivity.this).readPrefs(UserSession.PREFS_USERID));
                }
                Log.e("param profile", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(CheckOutActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(CheckOutActivity.this, Home.class);
        intent.putExtra("position",4);
        startActivity(intent);
    }

    public void replaceFragment(android.app.Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
