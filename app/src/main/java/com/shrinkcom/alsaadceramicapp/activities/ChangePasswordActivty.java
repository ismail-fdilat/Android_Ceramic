package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivty extends AppCompatActivity {

    TextView tv_submitchpass;
    EditText edt_chngpass,edt_Conpass;
    String email;
    ImageView imv_chpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_activty);
        initviews();
        email=getIntent().getStringExtra("email");

    }

    private void initviews() {
        tv_submitchpass = findViewById(R.id.tv_submitchpass);
        edt_chngpass = findViewById(R.id.edt_chngpass);
        edt_Conpass=findViewById(R.id.edt_Conpass);


        tv_submitchpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strnewpass = edt_chngpass.getText().toString().trim();
                String strnconfirmpass = edt_Conpass.getText().toString().trim();

                if (strnewpass.isEmpty()){
                    Toast.makeText(ChangePasswordActivty.this, R.string.title_passwordisrequired, Toast.LENGTH_SHORT).show();
                }else if(strnconfirmpass.isEmpty()){
                    Toast.makeText(ChangePasswordActivty.this, R.string.enter_your_confirm_password, Toast.LENGTH_SHORT).show();
                }else if (!strnewpass.equals(strnconfirmpass)){
                    Toast.makeText(ChangePasswordActivty.this, R.string.password_not_match, Toast.LENGTH_SHORT).show();
                }else {
                    SetPassword();

                }


            }
        });

    }


    private void SetPassword() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEEEEGAL", ">>" + response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("status")==1){
                                Intent intent=new Intent(ChangePasswordActivty.this, LoginActivity.class);
                                startActivity(intent);
                                Toast.makeText(ChangePasswordActivty.this, R.string.password_success_update, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Utilitynew.UserAlert(ChangePasswordActivty.this,(getString(R.string.tv_internet)));

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
                params.put("action","SetPassword");
                params.put("email",""+email);
                params.put("password",""+edt_chngpass.getText().toString());
                Log.e("SENDVALUE", ">>" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordActivty.this);
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
