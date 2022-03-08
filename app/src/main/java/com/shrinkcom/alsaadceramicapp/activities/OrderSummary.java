package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.fragment.OrderSummaryAdpater;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.ProductModel;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.ArrayList;
import java.util.List;

public class OrderSummary extends AppCompatActivity {
    TextView idsdeliveryaddress,idsemail,idsphone,idsdeliverycity,idsdeliveryzipcode,sitemtotal,idsname;
   Toolbar toolbar;
   RecyclerView idrecycleviewproductsummery;
  static Mycart mycart;
    List<ProductModel> cartlist;
    static double finaltotal = 0.0;
    static Context mContext;
    static TextView idsfinaltotal;
    Button nextpay_btn,btn_edit;
    String phone,email,address,city,zipcode,name,addressid,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        mycart = new Mycart(OrderSummary.this);
        mycart.open();
        mContext = OrderSummary.this;
        toolbar = findViewById(R.id.toolbar_ordersummary);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderSummary.this.onBackPressed();
                finish();
            }
        });
        idsdeliveryaddress=findViewById(R.id.idsdeliveryaddress);
        idsemail=findViewById(R.id.idsemail);
        idsphone=findViewById(R.id.idsphone);

        idsdeliverycity=findViewById(R.id.idsdeliverycity);
        sitemtotal=findViewById(R.id.sitemtotal);
        idsfinaltotal=findViewById(R.id.idsfinaltotal);
        nextpay_btn=findViewById(R.id.nextpay_btn);
        idsname=findViewById(R.id.idsname);
        btn_edit=findViewById(R.id.btn_edit);

           phone = getIntent().getStringExtra("phone");
           email = getIntent().getStringExtra("email");
           address = getIntent().getStringExtra("address");
           city = getIntent().getStringExtra("city");;
           name = getIntent().getStringExtra("name");
          addressid = getIntent().getStringExtra("addressid");



          if (UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID).equals("")){
              btn_edit.setVisibility(View.GONE);
          }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummary.this, EditCheckoutActvty.class);
                intent.putExtra("addressid",addressid);
                intent.putExtra("name",name);
                intent.putExtra("phone", phone);
                intent.putExtra("email",email);
                intent.putExtra("address", address);
                intent.putExtra("city", city);

                startActivity(intent);
            }
        });

       idsdeliveryaddress.setText(address);
                idsemail.setText(email);
                idsphone.setText(phone);
                idsdeliverycity.setText(city);

                idsname.setText(name);

        idrecycleviewproductsummery=findViewById(R.id.idrecycleviewproductsummery);

        cartlist = new ArrayList<>();
        cartlist = Utilitynew.getAllProduct(OrderSummary.this);
        Log.e("LISTTTT",">>>"+cartlist.size());

        // TODO: 3/14/2019  setAdaptor
        if (cartlist.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(OrderSummary.this, LinearLayoutManager.VERTICAL, true);
            idrecycleviewproductsummery.setLayoutManager(layoutManager);
            idrecycleviewproductsummery.setItemAnimator(new DefaultItemAnimator());
            OrderSummaryAdpater adaptor = new OrderSummaryAdpater(cartlist,getApplicationContext());
            idrecycleviewproductsummery.setAdapter(adaptor);
            adaptor.notifyDataSetChanged();
            sitemtotal.setText(getResources().getString(R.string.items) + " (" + cartlist.size() + ")");


        } else
        {
            Toast.makeText(OrderSummary.this, "no items found", Toast.LENGTH_SHORT).show();

        }

        updatecartTotal();

        nextpay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OrderSummary.this, PaymentActivityww.class);
                intent.putExtra("phone_os", phone);
                intent.putExtra("email_os", email);
                intent.putExtra("address_os", address);
                intent.putExtra("city_os",city);
                intent.putExtra("zipcode_os", zipcode);
                intent.putExtra("name_os", name);
                intent.putExtra("country_os", country);
                intent.putExtra("finaltotla", finaltotal);
                startActivity(intent);

            }
        });

    }

    //TODO 21-02-2020 GETSHIPAPI

    //____________________________API Get Shhiping address details__________________________________________//




    public static void updatecartTotal() {
        finaltotal = 0.0;

        mycart = new Mycart(mContext);

        List<ProductModel> listdaa = new ArrayList<>();
        listdaa = Utilitynew.getAllProduct(mContext);


        for (int i = 0; i < listdaa.size(); i++) {
            String pprice = listdaa.get(i).getPprice();
            String pquantity = listdaa.get(i).getPquantity();

            double ddprice = Double.parseDouble(pprice);
            int ddqusy = Integer.parseInt(pquantity);

            finaltotal = finaltotal + ddprice * ddqusy;


        }

        idsfinaltotal.setText(finaltotal + ""+ApiService.CURRENCYCODE);
    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

}
