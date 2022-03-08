package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.ReviewMoreAdaptor;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Review;

import java.util.List;

public class ReviewViewMoreActivity extends AppCompatActivity {

    RecyclerView rv_more;

    Toolbar toolbar;


    List<Review> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_view_more);
        toolbar = findViewById(R.id.toolbar_rvmore);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ReviewViewMoreActivity.this, Home.class);
                //intent.putExtra("position",5);
                startActivity(intent);
            }
        });

        rv_more=findViewById(R.id.rv_more);

        list =this.getIntent().getExtras().getParcelableArrayList("listreview");

      ReviewMoreAdaptor rvadptor = new ReviewMoreAdaptor(ReviewViewMoreActivity.this, list);
        LinearLayoutManager horizontalLayoutManagaer1
                = new LinearLayoutManager(ReviewViewMoreActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_more.setLayoutManager(horizontalLayoutManagaer1);
        rv_more.setAdapter(rvadptor);
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
        Intent intent=new Intent(ReviewViewMoreActivity.this, Home.class);
      //  intent.putExtra("position",-1);
        startActivity(intent);
    }




}
