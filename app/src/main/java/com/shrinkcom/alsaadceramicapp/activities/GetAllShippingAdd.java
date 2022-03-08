package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.AllAddressAdaptor;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.GetShipModel.com.shrinkcom.alsaadhomeapp.GetShipModel;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import java.util.HashMap;
import java.util.Map;

public class GetAllShippingAdd extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView rv_alladress;
    LinearLayout nodata_alladress;
     AllAddressAdaptor allAddressAdaptor;
     ImageView imv_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_shipping_add);
        toolbar = findViewById(R.id.toolbar_addbook);
        rv_alladress = findViewById(R.id.rv_alladress);
        nodata_alladress = findViewById(R.id.nodata_alladress);
        imv_add = findViewById(R.id.imv_add);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllShippingAdd.this.onBackPressed();

            }
        });
        getShipApi();

        imv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GetAllShippingAdd.this,
                        CheckOutActivity.class);
                startActivity(i);
            }
        });
    }



    private void getShipApi() {
        final ProgressDialog pDialog = new ProgressDialog(GetAllShippingAdd.this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("Responseprofile", ">>>" + response);
                pDialog.hide();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                GetShipModel getShipModel = gson.fromJson(response, GetShipModel.class);
                nodata_alladress.setVisibility(View.GONE);

                if (getShipModel.getStatus() == null) {
                    nodata_alladress.setVisibility(View.VISIBLE);


                    }
                else {
                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(GetAllShippingAdd.this,1);
                    rv_alladress.setLayoutManager(mLayoutManager);
                    rv_alladress.setItemAnimator(new DefaultItemAnimator());
                    //categorylist.addAll(categoryModel.getResponse());
                    allAddressAdaptor=new AllAddressAdaptor(getShipModel.getResponse(),GetAllShippingAdd.this);
                    rv_alladress.setAdapter(allAddressAdaptor);
                    allAddressAdaptor.notifyDataSetChanged();
                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nodata_alladress.setVisibility(View.VISIBLE);
                Toast.makeText(GetAllShippingAdd.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("action","GetShippingAddress");
                params.put("user_id", UserSession.getInstance(GetAllShippingAdd.this).readPrefs(UserSession.PREFS_USERID));
                Log.e("param profile", params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(GetAllShippingAdd.this).addToRequestQueue(stringreuest);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
        Log.v("Ceramic", "language "+base);
    }

}
