package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditCheckoutActvty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    EditText etusername,etaddress,etzipcode,etemail,etnumber,etwhatsapp;
    CountryCodePicker etcountry;
    Spinner etcity;
    String countryCodeone="91",countryCode,addressid,st_city;
    Button done_editadd;
    String[] cityn = { "Al Ain", "Ajman", " Abu Dhabi", "Dubai", "Sharjah",
            "Fujaira", " Ras al khaima", "Um al quwain","Khour Fakan"};

    int cityposition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_checkout_actvty);
        toolbar = findViewById(R.id.toolbar_editcheckout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCheckoutActvty.this.onBackPressed();
            }
        });
        initviews();

        String  name = getIntent().getStringExtra("name");
        String  phone = getIntent().getStringExtra("phone");
        String  email = getIntent().getStringExtra("email");
        String  address = getIntent().getStringExtra("address");
        String  city = getIntent().getStringExtra("city");
        addressid = getIntent().getStringExtra("addressid");

        for (int i = 0; i <cityn.length ; i++) {
            if (cityn[i].equals(city)){
                cityposition = i;
                break;
            }
        }



        etusername.setText(name);
        etnumber.setText(phone);
        etemail.setText(email);
        etaddress.setText(address);



        done_editadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditShipApi();
            }
        });

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),R.layout.item_city ,R.id.tv_itemcity,cityn);

// Setting Adapter to the Spinner
        etcity.setAdapter(adapter);
        etcity.setSelection(cityposition);

// Setting OnItemClickListener to the Spinner
        etcity.setOnItemSelectedListener(this);


    }
    public void onItemSelected(AdapterView parent, View view, int pos,
                               long id) {
        st_city=etcity.getSelectedItem().toString();
        Toast.makeText(getApplicationContext(),
                etcity.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG)
                .show();
    }

    // Defining the Callback methods here
    @Override
    public void onNothingSelected(AdapterView arg0) {
// TODO Auto-generated method stub

    }

    private void initviews() {
        etusername=findViewById(R.id.et_name_edit);
        etnumber=findViewById(R.id.edt_mobileno_edit);
        etaddress=findViewById(R.id.et_address_edit);
        etcity=findViewById(R.id.et_city_edit);
        etemail=findViewById(R.id.edt_email_edit);
        done_editadd=findViewById(R.id.done_editadd);
        etwhatsapp=findViewById(R.id.edt_whatsappno_edit);


    }




    private void EditShipApi() {
    /*    final ProgressDialog progressDialog = new ProgressDialog(EditCheckoutActvty.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                        /*    if (progressDialog != null) {
                                progressDialog.dismiss();*/

                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            JSONObject jo = new JSONObject(response);
                            if (jo.getInt("status") == 1) {
                                startActivity(new Intent(EditCheckoutActvty.this, NewCheckoutPage.class));
                                finish();
                            } else {
                                Toast.makeText(EditCheckoutActvty.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {
                            Utilitynew.UserAlert(EditCheckoutActvty.this, (getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   progressDialog.dismiss();

                        Toast.makeText(EditCheckoutActvty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "EditShippingAddress");
                params.put("user_id", UserSession.getInstance(EditCheckoutActvty.this).readPrefs(UserSession.PREFS_USERID));
                params.put("address_id", addressid);
                params.put("address", etaddress.getText().toString());
                params.put("username", etusername.getText().toString());
                params.put("city",st_city);
                params.put("zipcode", "");
                params.put("country", "");
                params.put("email", etemail.getText().toString());
                params.put("phone", etnumber.getText().toString());
                params.put("whatsapp_no", etwhatsapp.getText().toString());
                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(EditCheckoutActvty.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

}
