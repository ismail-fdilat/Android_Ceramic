package com.shrinkcom.alsaadceramicapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivityww extends AppCompatActivity {

    private static final String EXTRA_RESULT_CONFIRMATION ="1" ;
    private static final String EXTRA_PAYMENT ="2" ;
    private static final int RESULT_EXTRAS_INVALID = 5 ;
    Button confirm_payment;

    String totalamount,finaltotal;
    JSONArray jsonArray ;
    static Mycart mycart;
    static Context mContext;
    String payID;
    String deliverystatus;


    public static final String clientKey = "AUajRZoDmzoSWNJLMX2bfJ_9Z0GhZ-0IFIaFpREj_uR90hQqv4zChF_eVlqEJTB5QO0_r2fEWlWesDeo";
    public static final String secerat = " EBSluMHXElfR9VEbUbXB6GP1TOsdmYV0p9uUFqqZw5wiE-FDj3r_dC2WOFhwkQItHIiHIxLOUSdVc7QJ";
    public static final int PAYPAL_REQUEST_CODE = 123;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(clientKey)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Example Merchant")
            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));





    String phone,email,address,city,zipcode,name,country;
    TextView paymentTV;
    String sellerid ="";
    String Username,Mobileno,Email,Address,Country,Zipcode,Userid,st_city,whatsapp,Whatsapp,shippingarea,shipping_city;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentTV  = findViewById(R.id.paymentTV);
        mycart = new Mycart(PaymentActivityww.this);
        mycart.open();
        confirm_payment = (Button)findViewById(R.id.confirm_payment);
        data(this);
        totalamount = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.AMOUNT);


        getPayment();

        Email = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_EMAIL);
        Username = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PROFILEFULLNAME);
        Mobileno = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.MOBILENO);
        Whatsapp = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.WHATSAPPNUM);
        Address = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.ADDRESS);
        shipping_city = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.shipping_city);
        shippingarea = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.shipping_area);
        city = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.City);

        Intent intent = getIntent();
        deliverystatus = intent.getStringExtra("deliverystatus");





//          phone = getIntent().getStringExtra("phone_os");
//         email = getIntent().getStringExtra("email_os");
//         address = getIntent().getStringExtra("address_os");
//         city = getIntent().getStringExtra("city_os");
//         zipcode = getIntent().getStringExtra("zipcode_os");
//         name = getIntent().getStringExtra("name_os");
//        country = getIntent().getStringExtra("country_os");
//        finaltotal = getIntent().getStringExtra("finaltotla");


        confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConfirmBook();
            }
        });

    }




    private void data(Context activity){

        String userid;
        if (UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID).equals("")){
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERDEVICEID);
        }else {
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID);
        }

        Mycart mycart = new Mycart(PaymentActivityww.this);
        mycart.open();
        Cursor cursor = mycart.getAllDatashopping(userid);
       jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String pid = cursor.getString(1);
                String pname = cursor.getString(2);
                String pimage = cursor.getString(3);
                String pprice = cursor.getString(4);
                String pquantity = cursor.getString(5);
                String pdescription = cursor.getString(6);

                String psizee=cursor.getString(8);
                String size_name=cursor.getString(9);
                sellerid=cursor.getString(cursor.getColumnIndex(Mycart.sellerId));
                String city=cursor.getString(cursor.getColumnIndex(Mycart.city));
                String mainQ=cursor.getString(cursor.getColumnIndex(Mycart.mainQty));
                Log.d("qqqqq",mainQ);


                Log.e("DIZE_NAMEE",">>"+psizee);
                Log.e("DIZE_NAMEE",">>"+size_name);


                Log.e("PRODIDDD",">>"+pid);
                JSONObject object = new JSONObject();
                try {
                    object.put("product_id",pid);
                    object.put("qty",pquantity);
                    object.put("price",pprice);
                    object.put("totalprice",Integer.parseInt(pprice)*Integer.parseInt(pquantity));
                    object.put("size_id",psizee);
                    object.put("size",size_name);
                    object.put("seller_id",sellerid);
                    object.put("city_id",city);

                    jsonArray.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        Log.e("JSONARRAAAA",">>"+jsonArray);
    }


    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }


    private void ConfirmBook() {
        final ProgressDialog progressDialog = new ProgressDialog(PaymentActivityww.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        Log.e("msg======", "getSubmitapipayment: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getInt("status") == 1) {

                                Intent i = new Intent(PaymentActivityww.this,
                                        ConfirmationActivity.class);
                                startActivity(i);

                                String userid;
                                if (UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                                    userid = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_USERDEVICEID);
                                }else {
                                    userid = UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_USERID);
                                }
                                Mycart mycartdb = new Mycart(PaymentActivityww.this);
                                mycartdb.open();
                                mycartdb.deletealldata(userid);
                                mycartdb.close();
                            } else {
                                Toast.makeText(PaymentActivityww.this, ">>>>>>>>>>", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            Utilitynew.UserAlert(PaymentActivityww.this,(getString(R.string.tv_internet)));


                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(PaymentActivityww.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();

                if (UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(PaymentActivityww.this).readPrefs(UserSession.PREFS_USERID));
                }

                params.put("action", "BookOrder");

                params.put("transaction_id", payID);
                params.put("last_name", "");
                params.put("phone", "" + Mobileno);
                params.put("email", "" + Email);
                params.put("country", "");
                params.put("state", "" );
                params.put("city", "" + city);
                params.put("address", "" + Address);
                params.put("postal_code", "" + Zipcode);
                params.put("payment_method", "Online");
                params.put("product_data", "" + jsonArray);
                params.put("first_name", "" + Username);
                params.put("delivery_status", ""+deliverystatus);
                params.put("payment_status", ""+"1");


                params.put("token", ""+ FirebaseInstanceId.getInstance().getToken());
//                params.put("country", "" + country);
                params.put("total", "" + totalamount);


                Log.e("paramsAddPayment", ">>>>" + params.toString());

                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(PaymentActivityww.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void getPayment() {

        // Creating a paypal payment on below line.
        PayPalPayment payment = new PayPalPayment(new BigDecimal(""+totalamount), "USD", "Grand Total",
                PayPalPayment.PAYMENT_INTENT_SALE);

        // Creating Paypal Payment activity intent
        Intent intent = new Intent(this, PaymentActivity.class);

        //putting the paypal configuration to the intent
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

        // Putting paypal payment to the intent
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Starting the intent activity for result
        // the request code will be used on the method onActivityResult
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // If the result is from paypal
        if (requestCode == PAYPAL_REQUEST_CODE) {

            // If the result is OK i.e. user has not canceled the payment
            if (resultCode == Activity.RESULT_OK) {

                // Getting the payment confirmation
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                Log.d("dataprint",data.toString());

                // if confirmation is not null
                if (confirm != null) {
                    try {
                        // Getting the payment details
                        String paymentDetails = confirm.toJSONObject().toString(4);
                        // on below line we are extracting json response and displaying it in a text view.
                        JSONObject payObj = new JSONObject(paymentDetails);
                         payID = payObj.getJSONObject("response").getString("id");
                        String state = payObj.getJSONObject("response").getString("state");

                        Log.i("paymentDetail22", confirm.toJSONObject().toString(4));
                        Log.i("paymentDetailpayment", confirm.getPayment().toJSONObject().toString(4));

                        paymentTV.setText("Payment " + state + "\n with payment id is " + payID);
                        Log.d("paymentDetails",paymentDetails);
                        Log.d("payID",payID);
                        ConfirmBook();
                    } catch (JSONException e) {
                        // handling json exception on below line
                        Log.e("Error", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // on below line we are checking the payment status.
                Log.i("paymentExample", "The user canceled.");
                Toast.makeText(getApplicationContext(), "The user canceled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivityww.RESULT_EXTRAS_INVALID) {
                // on below line when the invalid paypal config is submitted.
                Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
                Toast.makeText(getApplicationContext(), "An invalid Payment or PayPalConfiguration was submitted", Toast.LENGTH_SHORT).show();

            }
        }
    }
}



