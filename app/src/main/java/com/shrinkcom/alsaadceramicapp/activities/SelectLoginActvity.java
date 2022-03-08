package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shrinkcom.alsaadceramicapp.R;

public class SelectLoginActvity extends AppCompatActivity {

    Toolbar toolbar;
    TextView tv_loging,tv_guestlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_login_actvity);
        toolbar = findViewById(R.id.toolbar_guests);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectLoginActvity.this, Home.class);
                startActivity(intent);
             //   SelectLoginActvity.this.onBackPressed();
            }});
        tv_loging=findViewById(R.id.tv_loging);


        tv_guestlogin=findViewById(R.id.tv_guestlogin);


        tv_loging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectLoginActvity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


        tv_guestlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SelectLoginActvity.this, NewCheckoutPage.class);
                startActivity(intent);

            }
        });





    }
}
