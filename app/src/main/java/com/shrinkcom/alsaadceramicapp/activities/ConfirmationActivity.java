package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;

public class ConfirmationActivity extends AppCompatActivity {

    Button btnconfirm;
    AppCompatButton tv_review;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btnconfirm = findViewById(R.id.confirm_payment);
        tv_review = findViewById(R.id.tv_review);
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmationActivity.this, Home.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });
        tv_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();


            }
        });
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(ConfirmationActivity.this, Home.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }



    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

        public void showDialog(){
            final Dialog dialog = new Dialog(ConfirmationActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialogreview);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_submit);
             final RatingBar simpleRatingBar = (RatingBar) dialog.findViewById(R.id.ratingBara);

            simpleRatingBar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String rating = "Rating is :" + simpleRatingBar.getRating();
                    Toast.makeText(ConfirmationActivity.this, rating, Toast.LENGTH_LONG).show();
                }
            });

           // initiate a rating bar
//            int numberOfStars = simpleRatingBar.getNumStars();
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }


}
