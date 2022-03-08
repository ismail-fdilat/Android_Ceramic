package com.shrinkcom.alsaadceramicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shrinkcom.alsaadceramicapp.activities.Home;

public class TransperentActivity extends AppCompatActivity {

    TextView next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transperent);

        next = findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(TransperentActivity.this,
                        Home.class);
                //Intent is used to switch from one activity to another.

                startActivity(i);

            }
        });
    }
}
