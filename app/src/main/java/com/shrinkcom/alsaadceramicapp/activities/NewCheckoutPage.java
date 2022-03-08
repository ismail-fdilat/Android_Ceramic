package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.hbb20.CountryCodePicker;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.AllAddressAdaptor;
import com.shrinkcom.alsaadceramicapp.fragment.OrderSummaryAdpater;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.ProductModel;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewCheckoutPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    EditText etusername,etaddress,etemail,etnumber,etwhatsapp,et_shippingcity,et_shippingarea;
    Spinner spn_city;
    CountryCodePicker etcountry;
    String Username,Mobileno,Email,Address,Country,Zipcode,Userid,st_city,whatsapp,totalamount,Whatsapp,shippingarea,shipping_city;
    int int_city;
    Button confirm_payment;
    String deliverystatus ="";
    RadioGroup radio_group;
    RadioButton rb_paymentgateway,rb_cashondelivery;


    String[] city = { "Al Ain", "Ajman", " Abu Dhabi", "Dubai", "Sharjah",
            "Fujaira", " Ras al khaima", "Um al quwain","Khour Fakan"};

    // TODO: 3/18/2020 getshipp
    RecyclerView rv_alladress;
    LinearLayout nodata_alladress;
    AllAddressAdaptor allAddressAdaptor;

    static Mycart mycart;
    List<ProductModel> cartlist;
    static Context mContext;
    RecyclerView idrecycleviewproductsummery;
    TextView sitemtotal,tv_uaevat,tv_flatshipr;
    static double finaltotal = 0.0;
    static TextView idsfinaltotal;
    JSONArray jsonArray ;
    static int ship,uae_vat;
    static double totalmain;
    static double percent;



    public  static String classname ;
    boolean doubleBackToExitPressedOnce = false;

    public static boolean FLAGHOME = false;
    TextView seller_city,seller_location,sellershopname,pickupaddress;
    androidx.cardview.widget.CardView dddd;
    String  sellerid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_checkout_page);

        mycart = new Mycart(NewCheckoutPage.this);
        mycart.open();
        mContext = NewCheckoutPage.this;
        data(this);
        toolbar = findViewById(R.id.toolbar_newcheckout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewCheckoutPage.this, Home.class);
                startActivity(intent);
                }

        });
        initviews();
        confirm_payment=findViewById(R.id.confirm_payment);
        rb_paymentgateway = findViewById(R.id.rb_paymentgateway);
        rb_cashondelivery = findViewById(R.id.rb_cashondelivery);

        seller_city = findViewById(R.id.seller_city);
        seller_location = findViewById(R.id.sellerpostalcode);
        sellershopname = findViewById(R.id.sellershopname);

        dddd = findViewById(R.id.dddd);
        pickupaddress = findViewById(R.id.pickupaddress);
        confirm_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

         radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb=(RadioButton)findViewById(checkedId);
                Toast.makeText(NewCheckoutPage.this, rb.getText(), Toast.LENGTH_SHORT).show();

                if (rb.getText().equals("Online Payment")){

                    Intent intent = new Intent(getApplicationContext(), PaymentActivityww.class);
                    intent.putExtra("deliverystatus",deliverystatus);
                    startActivity(intent);

                }else {


                }
                // checkedId is the RadioButton selected
            }
        });



        // TODO: 2/27/2020  get country code by country name

        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),R.layout.item_city ,R.id.tv_itemcity,city);

// Setting Adapter to the Spinner
        spn_city.setAdapter(adapter);

// Setting OnItemClickListener to the Spinner
        spn_city.setOnItemSelectedListener(this);


        Intent intent = getIntent();
        deliverystatus =""+intent.getStringExtra("deliverystatus");


        if (deliverystatus.equals("1")){
            dddd.setVisibility(View.VISIBLE);
            pickupaddress.setVisibility(View.VISIBLE);
            getselleraddress();
        }else {
            dddd.setVisibility(View.GONE);
            pickupaddress.setVisibility(View.GONE);


        }

        if (UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID).equals("")){
         getShipAddApi();
        }
        else {
            Email = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_EMAIL);
            Username = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PROFILEFULLNAME);
            Mobileno = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.MOBILENO);
            Whatsapp = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.WHATSAPPNUM);
            Address = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.ADDRESS);
            shipping_city = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.shipping_city);
            shippingarea = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.shipping_area);

            etusername.setText(Username);
            etemail.setText(Email);
            etnumber.setText(Mobileno);
            etwhatsapp.setText(Whatsapp);
            etaddress.setText(Address);
            et_shippingarea.setText(shippingarea);
//            et_shippingcity.setText(shipping_city);
        }




        // TODO: 3/19/2020 cartitems


        cartlist = new ArrayList<>();
        cartlist = Utilitynew.getAllProduct(NewCheckoutPage.this);
        Log.e("LISTTTT",">>>"+cartlist.size());

        // TODO: 3/14/2019  setAdaptor
        if (cartlist.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(NewCheckoutPage.this, LinearLayoutManager.VERTICAL, true);
            idrecycleviewproductsummery.setLayoutManager(layoutManager);
            idrecycleviewproductsummery.setItemAnimator(new DefaultItemAnimator());
            OrderSummaryAdpater adaptor = new OrderSummaryAdpater(cartlist,getApplicationContext());
            idrecycleviewproductsummery.setAdapter(adaptor);
            adaptor.notifyDataSetChanged();


        } else
        {
            Toast.makeText(NewCheckoutPage.this, "no items found", Toast.LENGTH_SHORT).show();

        }
        getshippApi();
        updatecartTotal();


        totalamount = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.AMOUNT);

    }

    // TODO: 3/19/2020 add addresswork

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
        idrecycleviewproductsummery=findViewById(R.id.idrecycleviewproductsummery);
        sitemtotal=findViewById(R.id.sitemtotal);
        idsfinaltotal=findViewById(R.id.idsfinaltotal);
        tv_flatshipr=findViewById(R.id.tv_flatshipr);
        tv_uaevat=findViewById(R.id.tv_uaevat);

        et_shippingcity = findViewById(R.id.et_shippingcity);
        et_shippingarea = findViewById(R.id.et_shippingarea);

        String city = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.city_name);
        et_shippingcity.setText(city);
        Log.d("cityee",city);
        Log.d("cityee",et_shippingcity.getText().toString());

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
        shipping_city = et_shippingcity.getText().toString().trim();
        shippingarea = et_shippingarea.getText().toString().trim();

        if (Username.isEmpty())
        {

            etusername.setError(getString(R.string.title_userisrequired));
            etusername.requestFocus();

        }

        else {
            if (!emailValidator(Email)) {
                etemail.setError(getString(R.string.title_Emailisrequired));
                etemail.requestFocus();
            }
            else {
                if (Mobileno.isEmpty()) {
                    etnumber.setError(getString(R.string.title_mobileisrequired));
                    etnumber.requestFocus();
                } else if (Mobileno.length()<9){
                    etnumber.setError("Please Enter Valid Number with country code");
                    etnumber.requestFocus();
                }else if (whatsapp.isEmpty()){
                    etwhatsapp.setError(getString(R.string.title_mobileisrequired));
                    etwhatsapp.requestFocus();
                }else if (whatsapp.length()<9){
                    etwhatsapp.setError("Please Enter Valid Number with country code");
                    etwhatsapp.requestFocus();
                }else {
                    if (Address.isEmpty()) {
                        etaddress.setError(getString(R.string.title_adressisrequired));
                        etaddress.requestFocus();

                    } else {

                        if (shipping_city.isEmpty()) {
                            et_shippingcity.setError(getString(R.string.title_shippingcityr));
                            et_shippingcity.requestFocus();
                        } else {
                            if (shippingarea.isEmpty()) {
                                et_shippingarea.setError(getString(R.string.titile_shippingarea));
                                et_shippingarea.requestFocus();
                            } else {
                                ConfirmBook();
                            }


                        }


                    }


                }

            }


        }


    }





    public static void updatecartTotal() {
        finaltotal = 0.0;

        mycart = new Mycart(mContext);

        List<ProductModel> listdaa = new ArrayList<>();
        listdaa = Utilitynew.getAllProduct(mContext);


        for (int i = 0; i < listdaa.size(); i++) {
            String pprice = ""+listdaa.get(i).getPprice();
            String pquantity = listdaa.get(i).getPquantity();
            Log.v("DIKSHAAAAA",">>"+pprice);

            double ddprice = Double.parseDouble(pprice);
            int ddqusy = Integer.parseInt(pquantity);

            finaltotal = finaltotal + ddprice * ddqusy;



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
        super.onBackPressed();
        Intent intent=new Intent(NewCheckoutPage.this, Home.class);
        intent.putExtra("position",3);
        startActivity(intent);
    }


    // TODO: 3/19/2020 payment work





    private void ConfirmBook() {
        final ProgressDialog progressDialog = new ProgressDialog(NewCheckoutPage.this);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("msg======", "getSubmitapi: "+response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getInt("status") == 1) {


                                Intent i = new Intent(NewCheckoutPage.this,
                                        ConfirmationActivity.class);
                                startActivity(i);

                                String userid;
                                if (UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                                    userid = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERDEVICEID);
                                    Log.e("ranuser",""+UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                                }else {
                                    userid = UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID);
                                }
                                Mycart mycartdb = new Mycart(NewCheckoutPage.this);
                                mycartdb.open();
                                mycartdb.deletealldata(userid);
                                mycartdb.close();
                            } else {
                                Toast.makeText(NewCheckoutPage.this, ">>>>>>>>>>", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {



                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Utilitynew.UserAlert(NewCheckoutPage.this,(getString(R.string.tv_internet)));
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();

                if (UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID));
                }

                params.put("action", "BookOrder");

                params.put("transaction_id", "" );
                params.put("last_name", "");
                params.put("phone", "" +etnumber.getText().toString());
                params.put("email", "" + etemail.getText().toString());
                params.put("state", "" );
                params.put("city", "" + st_city);
                params.put("address", "" + etaddress.getText().toString());
                params.put("postal_code", "");
                params.put("payment_method", "COD");
                params.put("delivery_status", ""+deliverystatus);
                params.put("product_data", "" + jsonArray);
                params.put("first_name", "" + etusername.getText().toString());
                params.put("country", "");
                params.put("total", ""+totalmain);
                params.put("whatsapp_no",whatsapp);
                params.put("shipping_area",shippingarea);
                params.put("shipping_city",shipping_city);
                params.put("tax_price",""+percent);
                params.put("payment_status",""+"0");
                params.put("token", ""+FirebaseInstanceId.getInstance().getToken());
                Log.d("token>>",FirebaseInstanceId.getInstance().getToken());

                Log.e("paramsAddPayment", ">>>>" + params.toString());

                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(NewCheckoutPage.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void data(Context activity){

        String userid;
        if (UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID).equals("")){
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERDEVICEID);
        }else {
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID);
        }

        Mycart mycart = new Mycart(NewCheckoutPage.this);
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


    private void getshippApi() {
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
                                    Log.e("percent",">>>>>"+percent);
                                    Log.e("totalmaintotalmain",">>>>>"+totalmain);


                                    idsfinaltotal.setText(totalmain + ""+ApiService.CURRENCYCODE);


                                }
                            }
                            else {
                                Toast.makeText(NewCheckoutPage.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();

                        Toast.makeText(NewCheckoutPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
        final RequestQueue requestQueue = Volley.newRequestQueue(NewCheckoutPage.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    private void getselleraddress() {
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

                                JSONObject jsonObject2 = jo.getJSONObject("response");
                                String sellercity = jsonObject2.getString("city_name");
                                String location = jsonObject2.getString("location");
                                String  shop_name = jsonObject2.getString("shop_name");

                                seller_city.setText(sellercity);
                                seller_location.setText(location);
                                sellershopname.setText(shop_name);

                            }
                            else {
                                Toast.makeText(NewCheckoutPage.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (Exception e) {

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //   progressDialog.dismiss();

                        Toast.makeText(NewCheckoutPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override


            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "seller_detail");
                params.put("seller_id", sellerid);
                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(NewCheckoutPage.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



    private void getShipAddApi() {

       /* final ProgressDialog pDialog = new ProgressDialog(NewCheckoutPage.this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                           /* if (pDialog != null) {
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
                                        shippingarea = c.getString("shipping_area");
                                        shipping_city = c.getString("shipping_city");


                                        etusername.setText(Username);
                                        etemail.setText(Email);
                                        etnumber.setText(Mobileno);
                                        etwhatsapp.setText(whatsapp);
                                        etaddress.setText(Address);
//                                        et_shippingcity.setText(shipping_city);
                                        et_shippingarea.setText(shippingarea);


                                    }
                                    }

                                else {
                                    Toast.makeText(NewCheckoutPage.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                           // }
                        } catch (Exception e) {


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NewCheckoutPage.this, error.getMessage(), Toast.LENGTH_SHORT).show();
               // pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("action","GetShippingAddress");
                if (UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(NewCheckoutPage.this).readPrefs(UserSession.PREFS_USERID));
                }
                Log.e("param profile", params.toString());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(NewCheckoutPage.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



}
