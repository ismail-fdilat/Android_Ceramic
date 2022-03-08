package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;

public class FullProdutImageActivity extends AppCompatActivity {
    ImageView iv_fullimage;
    String imageurl ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_produt_image);

        iv_fullimage = findViewById(R.id.iv_fullimage);
        Intent intent = getIntent();
        imageurl = intent.getStringExtra("imageurl");

        if (imageurl.isEmpty()){
            Glide.with(getApplicationContext())
                    .load(R.drawable.ceramiclogo)
                    .fitCenter()
                    .into(iv_fullimage);
        }else {
            Glide.with(getApplicationContext())
                    .load(ApiService.PRODUCT_IMAGE_URL + imageurl)
                    .fitCenter()
                    .into(iv_fullimage);
        }

    }
}