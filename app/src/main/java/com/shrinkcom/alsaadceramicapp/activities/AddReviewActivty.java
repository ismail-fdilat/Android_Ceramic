package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddReviewActivty extends AppCompatActivity {

    RatingBar rate_product;
    EditText edt_addrvmsg;
    TextView tv_submitrv;
    String st_prodid;
    String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review_activty);
        rate_product=findViewById(R.id.rate_product);
        edt_addrvmsg=findViewById(R.id.edt_addrvmsg);
        tv_submitrv=findViewById(R.id.tv_submitrv);
        st_prodid=getIntent().getStringExtra("productidrv");
        order_id=getIntent().getStringExtra("order_id");

        tv_submitrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addrating();
            }
        });

    }


    public void addrating() {
       /* final ProgressDialog pDialog = new ProgressDialog(AddReviewActivty.this);
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.show();
*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);
             //   pDialog.hide();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {

                        Toast.makeText(AddReviewActivty.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddReviewActivty.this,
                                Home.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(AddReviewActivty.this, "EROOR", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AddReviewActivty.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("er",e.getMessage());
                    Toast.makeText(AddReviewActivty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    Utilitynew.UserAlert(AddReviewActivty.this,(getString(R.string.tv_internet)));
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(AddReviewActivty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                     //   pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "review");
                params.put("product_id", "" + st_prodid);
                params.put("user_id", "" + UserSession.getInstance(AddReviewActivty.this).readPrefs(UserSession.PREFS_USERID));
                params.put("rating", "" + rate_product.getRating());
                params.put("review", ""+edt_addrvmsg.getText().toString());
                params.put("orderid", ""+order_id);

                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(AddReviewActivty.this).addToRequestQueue(stringRequest);
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

}
