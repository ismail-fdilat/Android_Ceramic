package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.hbb20.CountryCodePicker;
import com.shrinkcom.alsaadceramicapp.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuestShipping extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Toolbar toolbar;
    EditText etusernameguest,etaddressguest,etzipcodeguest,etemailguest,etnumberguest,etwhatsappguest;
    CountryCodePicker etcountryguest;
    String Username,Mobileno,Email,Address,City,Country,Zipcode,Userid;
    String countryCodeone="Algeria",countryCode="Algeria";
    Button next_btnguest;
   Context mContext;
   Spinner spn_cityguest;
   String st_city;
    String[] city = { "Al Ain", "Ajman", " Abu Dhabi", "Dubai", "Sharjah",
            "Fujaira", " Ras al khaima", "Um al quwain"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_shipping);
        mContext= this;
        initviews();


        next_btnguest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });


        ArrayAdapter adapter = new ArrayAdapter(
                getApplicationContext(),R.layout.item_city ,R.id.tv_itemcity,city);

// Setting Adapter to the Spinner
        spn_cityguest.setAdapter(adapter);

// Setting OnItemClickListener to the Spinner
        spn_cityguest.setOnItemSelectedListener(this);



    }
    public void onItemSelected(AdapterView parent, View view, int pos,
                               long id) {
       // int_city=spn_cityguest.getSelectedItemPosition();
        st_city=spn_cityguest.getSelectedItem().toString();

    }

    // Defining the Callback methods here
    @Override
    public void onNothingSelected(AdapterView arg0) {
// TODO Auto-generated method stub






}



    private void initviews() {
        toolbar = findViewById(R.id.toolbar_guestship);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GuestShipping.this.onBackPressed(); }});
        etusernameguest=findViewById(R.id.et_nameguest);
        etnumberguest=findViewById(R.id.edt_mobilenoguest);
        etaddressguest=findViewById(R.id.et_addressguest);
        spn_cityguest=findViewById(R.id.spn_cityguest);
        etemailguest=findViewById(R.id.edt_emailguest);
        etwhatsappguest=findViewById(R.id.edt_whatsappnoguest);

        next_btnguest=findViewById(R.id.next_btnguest);


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
        Username = etusernameguest.getText().toString().trim();
        Email = etemailguest.getText().toString().trim();
        Address = etaddressguest.getText().toString().trim();
        Mobileno = etnumberguest.getText().toString().trim();

        if (Username.isEmpty())
        {

            etusernameguest.setError("Username is required");
            etusernameguest.requestFocus();

        }

        else {
            if (!emailValidator(Email)) {
                etemailguest.setError("Email is required");
                etemailguest.requestFocus();
            }
            else {
                if (Address.isEmpty()) {
                    etaddressguest.setError("Address is required");
                    etaddressguest.requestFocus();

                }


                        else {
                                Intent intent = new Intent(mContext, OrderSummary.class);
                                intent.putExtra("phone", Mobileno);
                                intent.putExtra("whatsapp_no ", etwhatsappguest.getText().toString());
                                intent.putExtra("email", Email);
                                intent.putExtra("address", Address);
                                intent.putExtra("city", st_city);
                                intent.putExtra("zipcode","");
                                intent.putExtra("country", "");
                                intent.putExtra("name", Username);
                                startActivity(intent);
                            }


                    }






            }



    }

}
