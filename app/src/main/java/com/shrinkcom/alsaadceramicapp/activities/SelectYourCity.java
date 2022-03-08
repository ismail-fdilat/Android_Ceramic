package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.adaptor.CityAdaptor;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.citypojo.Citymodel;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectYourCity extends AppCompatActivity {

    RecyclerView recyclerViewCity;
    Context mContext;
    AppCompatButton btnCountinue;
    String cityId="";
    String cityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_your_city);
        mContext = this;
        recyclerViewCity = findViewById(R.id.recycleview_city);

        GetCityAlert();
        btnCountinue = findViewById(R.id.dialog_button_countinue);

        btnCountinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cityId.equals("")){
                    Toast.makeText(mContext, getResources().getString(R.string.please_select_city), Toast.LENGTH_SHORT).show();
                    return;
                }
                UserSession.getInstance(mContext).writePrefs(UserSession.City,cityId);
                UserSession.getInstance(mContext).writePrefs(UserSession.City_NAME,cityName);

                Intent i=new Intent(mContext,
                        Home.class);
                startActivity(i);


            }
        });





    }


    private void   GetCityAlert()  {

        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "GET_COUNTRIES_RESPONSE>>" + response);
                        progressDialog.dismiss();
                        try {
                                Log.v("DIKSHA", "LOGIN_RESPONSE" + response);

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
//                                progressDialog.dismiss();

                                Citymodel pojo = gson.fromJson(response, Citymodel.class);

                                if (pojo.getStatus() == 1) {
                                    if (pojo.getResponse().size() > 0) {

                                        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
                                        recyclerViewCity.setLayoutManager(layoutManager);
                                        CityAdaptor adaptor = new CityAdaptor(mContext, pojo.getResponse(), new RecyclerbuttonClick() {
                                            @Override
                                            public void onItemClick(int position, int type) {

                                                cityId =  pojo.getResponse().get(position).getId();
                                                cityName =  pojo.getResponse().get(position).getCityName();

                                            }
                                        });
                                        recyclerViewCity.setAdapter(adaptor);
                                } else {

                                }

                            }

                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));
                            progressDialog.dismiss();
                            Log.v("DIKSHAAAA", "ERROR>>>>" + e);
                            GetCityAlert();
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
                return params;

            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

    @Override
    public void onBackPressed() {

    }
}