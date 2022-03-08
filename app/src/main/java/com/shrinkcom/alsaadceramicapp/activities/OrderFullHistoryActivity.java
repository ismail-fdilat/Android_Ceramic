package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.PRODUCT_IMAGE_URL;
import static com.shrinkcom.alsaadceramicapp.utils.ApiService.thumbnilimage;

public class OrderFullHistoryActivity extends AppCompatActivity {

    LinearLayout reviewlay;
    TextView order_id,tv_name,tv_description,tv_qty,tv_price;
    ImageView image;

    RatingBar rate_product;
    EditText edt_addrvmsg;
    TextView tv_submitrv;
    String st_prodid,order_review;

    String order_ids,qty,description,pimage,price,name,trackstatus,payment_method,delivery_status;

    ImageView imv_1checked,imv_1unchecked,imv_2checked,imv_2unchecked,imv_3checked,imv_3unchecked;
    TextView tv_status1,tv_status2,tv_status3,tv_sarea,scity,tv_saddress;
    View v1,v2;
    Toolbar toolbar_newcheckout;


    static double finaltotal = 0.0;
    static TextView idsfinaltotal;
    JSONArray jsonArray ;
    static int ship,uae_vat;
    static double totalmain;
    static double percent;
    TextView sitemtotal,tv_uaevat,tv_flatshipr,tv_pay,tv_deliverystatus;

    String shippingarea,shipingcity,shippingaddress,sellercity,sellerlocation,sellershopname;
    TextView seller_city,seller_location,sellershopname11;
    LinearLayout seller_lay,user_address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_full_history);

        toolbar_newcheckout = findViewById(R.id.toolbar_newcheckout);

        toolbar_newcheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sitemtotal=findViewById(R.id.sitemtotal);
        idsfinaltotal=findViewById(R.id.idsfinaltotal);
        tv_flatshipr=findViewById(R.id.tv_flatshipr);
        tv_uaevat=findViewById(R.id.tv_uaevat);

        tv_pay = findViewById(R.id.tv_pay);
        tv_sarea = findViewById(R.id.tv_sarea);
        scity = findViewById(R.id.scity);
        tv_saddress = findViewById(R.id.tv_saddress);
        tv_deliverystatus = findViewById(R.id.tv_deliverystatus);

        seller_city = findViewById(R.id.seller_city);
        seller_location = findViewById(R.id.sellerpostalcode);
        sellershopname11 = findViewById(R.id.sellershopname);
        seller_lay = findViewById(R.id.seller_lay);
        user_address = findViewById(R.id.user_address);








        reviewlay = findViewById(R.id.reviewlay);
        order_id = findViewById(R.id.order_id);
        tv_name = findViewById(R.id.tv_name);
        tv_description = findViewById(R.id.tv_description);

        tv_qty = findViewById(R.id.tv_qty);
        tv_price = findViewById(R.id.tv_price);
        image = findViewById(R.id.image);

        rate_product=findViewById(R.id.rate_product);
        edt_addrvmsg=findViewById(R.id.edt_addrvmsg);
        tv_submitrv=findViewById(R.id.tv_submitrv);



        imv_1checked = findViewById(R.id.imv_1checked);
        imv_2checked = findViewById(R.id.imv_2checked);
        imv_3checked = findViewById(R.id.imv_3checked);
        imv_1unchecked = findViewById(R.id.imv_1unchecked);
        imv_2unchecked = findViewById(R.id.imv_2uncheck);
        imv_3unchecked = findViewById(R.id.imv_3uncheck);
        tv_status1 = findViewById(R.id.tv_status1);
        tv_status2 = findViewById(R.id.tv_status2);
        tv_status3 = findViewById(R.id.tv_status3);
        v1 = findViewById(R.id.v1);
        v2 = findViewById(R.id.v2);






        Intent intent = getIntent();
        qty = ""+intent.getStringExtra("qty");
        order_ids = ""+intent.getStringExtra("orderid");
        st_prodid = ""+intent.getStringExtra("pid");
        description = ""+intent.getStringExtra("description");
        name = ""+intent.getStringExtra("pname");
        pimage = ""+intent.getStringExtra("image");
        price = ""+intent.getStringExtra("price");
        trackstatus = ""+intent.getStringExtra("trackstatus");
        order_review = ""+intent.getStringExtra("order_review");
        payment_method = ""+intent.getStringExtra("payment_method");
        shippingaddress = ""+intent.getStringExtra("shippingaddress");
        shipingcity = ""+intent.getStringExtra("shipingcity");
        shippingarea = ""+intent.getStringExtra("shippingarea");
        delivery_status = ""+intent.getStringExtra("delivery_status");

        sellercity = ""+intent.getStringExtra("sellercity");
        sellerlocation = ""+intent.getStringExtra("sellerlocation");
        sellershopname = ""+intent.getStringExtra("sellershopname");


        seller_city.setText(sellercity);
        seller_location.setText(sellerlocation);
        sellershopname11.setText(sellershopname);

        Log.d("delivery_status",delivery_status);

        tv_sarea.setText(shippingarea);
        scity.setText(shipingcity);
        tv_saddress.setText(shippingaddress);



        tv_qty.setText("Quantity"+" "+qty);
        tv_name.setText(name);
        tv_description.setText(description);
        tv_price.setText(ApiService.CURRENCYCODE+" "+price);

        order_id.setText("#"+order_ids);

        tv_pay.setText(payment_method);

        if (delivery_status.equals("1")){
            tv_deliverystatus.setText("Pickup");
            seller_lay.setVisibility(View.VISIBLE);
            user_address.setVisibility(View.GONE);
        }else {
            tv_deliverystatus.setText("Ship");
            seller_lay.setVisibility(View.GONE);
            user_address.setVisibility(View.VISIBLE);

        }

        Log.d("tracttttt",trackstatus);


        String strimageurll = thumbnilimage+pimage;
        Log.e("IMAGELINKK111111",">>"+strimageurll);

        RequestOptions requestOptions = new RequestOptions();


        Glide.with(getApplicationContext())
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(image);


        double ddprice = Double.parseDouble(price);
        int ddqusy = Integer.parseInt(qty);

        finaltotal =  ddprice * ddqusy;




        if(trackstatus.equals("0"))
        {

            imv_1checked.setVisibility(View.VISIBLE);
            imv_1unchecked.setVisibility(View.GONE);
            tv_status1.setText("pending");

        }
        else  if(trackstatus.equals("1"))
        {
            v1.setBackgroundColor(getResources().getColor(R.color.colorappbar));
            imv_1checked.setVisibility(View.VISIBLE);
            imv_2checked.setVisibility(View.VISIBLE);
            imv_1unchecked.setVisibility(View.GONE);
            imv_2unchecked.setVisibility(View.GONE);
            tv_status2.setText("shipping");

        }
        else  if(trackstatus.equals("2"))
        {
            v2.setBackgroundColor(getResources().getColor(R.color.colorappbar));
            imv_1checked.setVisibility(View.VISIBLE);
            imv_2checked.setVisibility(View.VISIBLE);
            imv_3checked.setVisibility(View.VISIBLE);
            imv_1unchecked.setVisibility(View.GONE);
            imv_2unchecked.setVisibility(View.GONE);
            imv_3unchecked.setVisibility(View.GONE);
            tv_status3.setText("complete");

        }

        if (trackstatus.equals("2")){
            reviewlay.setVisibility(View.VISIBLE);
        }

        tv_submitrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addrating();
            }
        });

        getshippApi();
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

                        Toast.makeText(OrderFullHistoryActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(OrderFullHistoryActivity.this,
                                Home.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(OrderFullHistoryActivity.this, "EROOR", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AddReviewActivty.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("er",e.getMessage());
                    Toast.makeText(OrderFullHistoryActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("user_id", "" + UserSession.getInstance(OrderFullHistoryActivity.this).readPrefs(UserSession.PREFS_USERID));
                params.put("rating", "" + rate_product.getRating());
                params.put("review", ""+edt_addrvmsg.getText().toString());
                params.put("orderid", ""+order_review);

                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(OrderFullHistoryActivity.this).addToRequestQueue(stringRequest);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }
    private void getshippApi() {
  /*      final ProgressDialog progressDialog = new ProgressDialog(NewCheckoutPage.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           /* if (progressDialog != null) {
                                progressDialog.dismiss();*/

                            Log.e("msg========", "getTokenApi reponse check  : " + response);

                            JSONObject jo=new JSONObject(response);
                            if (jo.getInt("status")==1) {
                                JSONArray contacts = jo.getJSONArray("response");
                                for (int i = 0; i < contacts.length(); i++) {
                                    JSONObject c = contacts.getJSONObject(i);

                                    ship = c.getInt("shipping_rate");
                                    uae_vat = c.getInt("tax");

                                    sitemtotal.setText( finaltotal+ ""+ApiService.CURRENCYCODE );

                                    tv_flatshipr.setText(String.valueOf(ship+ ""+ApiService.CURRENCYCODE));
                                    percent=finaltotal*uae_vat/100;
                                    tv_uaevat.setText(String.valueOf(percent+ ""+ApiService.CURRENCYCODE)) ;




                                    totalmain= finaltotal+percent;
                                    Log.e("poonam",">>>>>"+finaltotal);
                                    Log.e("poonamship",">>>>>"+ship);
                                    Log.e("poonamuae",">>>>>"+uae_vat);


                                    idsfinaltotal.setText(totalmain + ""+ApiService.CURRENCYCODE);


                                }
                            }
                            else {
                                Toast.makeText(OrderFullHistoryActivity.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();

                        Toast.makeText(OrderFullHistoryActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override


            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "producttaxrate");
                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(OrderFullHistoryActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


}