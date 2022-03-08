package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.FloorAdaptor;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.FloorModel;

import java.util.ArrayList;
import java.util.List;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.CURRENCYCODE;

public class CalculatorActivity extends AppCompatActivity {

    EditText  edtWidth,edtHeight;
    EditText edtArea;
    TextView tvreRult;
    Button tvCalculate, btnReset;
    ImageView tvFloorQuantity;
    TextView noOffloorQty;
    RadioGroup radioGroup;
    TextView tvDetail;
    TextView tvTotalCartoonPrice;
    LinearLayout Arealayout,FloorLayout,layoutAddfloor;
    boolean FLAGTYPE = true;
    RecyclerView recyclerViewFloor;
    List<FloorModel> floorList;
    FloorAdaptor floorAdaptor;

    int plenth;
    int pWidth;
    int thickness;
    double packetarea;
    double saleprice;
    TextView tv_perr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar toolbar = findViewById(R.id.toolbar_newcheckout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        thickness = getIntent().getIntExtra("thickness",0);
        pWidth = getIntent().getIntExtra("width",0);
        plenth = getIntent().getIntExtra("length",0);
        packetarea = getIntent().getDoubleExtra("packetarea",0);
        saleprice = Double.valueOf(getIntent().getDoubleExtra("saleprice",0));
        Log.d("saleprice", String.valueOf(saleprice));




        radioGroup = findViewById(R.id.radiogrouptypeid);

        recyclerViewFloor = findViewById(R.id.id_recycleview);
        edtWidth = findViewById(R.id.edt_width);
        edtHeight = findViewById(R.id.edt_height);
        edtArea = findViewById(R.id.edt_area);

        tvreRult = findViewById(R.id.tv_result);
        tvCalculate = findViewById(R.id.tv_calculate);
        btnReset = findViewById(R.id.id_reset);
        tvFloorQuantity = findViewById(R.id.id_add_floorquty);
        noOffloorQty = findViewById(R.id.id_nooffloorqty);
        Arealayout = findViewById(R.id.id_arealayout);
        FloorLayout = findViewById(R.id.id_layout_floor);
        layoutAddfloor = findViewById(R.id.id_addfloor);
        tvDetail = findViewById(R.id.id_deatil);
        tvTotalCartoonPrice = findViewById(R.id.tv_total_price);
        tv_perr = findViewById(R.id.tv_perr);


        tvDetail.setText(getResources().getString(R.string.cera)+" "+ plenth +" M"+ " * "+ pWidth + " M");

        tv_perr.setText(getResources().getString(R.string.per_cartoon)+" "+"-"+" "+CURRENCYCODE+saleprice);

        noOffloorQty.setText("Sum m2 = ");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL, false);
        recyclerViewFloor.setLayoutManager(layoutManager);
        floorList = new ArrayList<>();
        floorList.add(new FloorModel());
        floorAdaptor = new FloorAdaptor(floorList,this);
        recyclerViewFloor.setAdapter(floorAdaptor);
        floorAdaptor.notifyDataSetChanged();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.id_rdarea){
                    Arealayout.setVisibility(View.VISIBLE);
                    FloorLayout.setVisibility(View.GONE);
                    layoutAddfloor.setVisibility(View.GONE);
                    FLAGTYPE = false;
                    noOffloorQty.setText(" Total m2 = ");

                }else if (checkedId==R.id.id_rdflore){
                    Arealayout.setVisibility(View.GONE);
                    FloorLayout.setVisibility(View.VISIBLE);
                    layoutAddfloor.setVisibility(View.VISIBLE);
                    FLAGTYPE = true;
                    noOffloorQty.setText("Sum m2 = ");
                }
            }
        });



        tvFloorQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sunoffloor = sunoffloor+1;
                // noOffloorQty.setText("Sum m2 = "+sunoffloor);
                floorAdaptor.addFloor();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtWidth.setText("");
                edtHeight.setText("");
                edtArea.setText("");

                floorList = new ArrayList<>();
                floorList.add(new FloorModel());
                floorAdaptor = new FloorAdaptor(floorList,CalculatorActivity.this);
                recyclerViewFloor.setAdapter(floorAdaptor);
                floorAdaptor.notifyDataSetChanged();

                if (FLAGTYPE){
                    noOffloorQty.setText("Sum m2 = ");
                }else {
                    noOffloorQty.setText(" Total m2 = ");
                }

                tvreRult.setText(getResources().getString(R.string.totalcartoons));
                tvTotalCartoonPrice.setText(getResources().getString(R.string.totalprice));

            }
        });

        tvCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FLAGTYPE){
                    calculate();
                }else {
                    calculateArea();

                }



            }
        });

    }

    void  calculate(){

        Log.e("DATAAAAA",">>>>plenth "+plenth);
        Log.e("DATAAAAA",">>>>pWidth "+pWidth);
        Log.e("DATAAAAA",">>>>packetarea "+packetarea);
        double nooftiles = packetarea/(plenth*pWidth);
        Log.e("DATAAAAA",">>>> "+nooftiles);
        double area = plenth*pWidth*nooftiles;

        double result =  floorAdaptor.calculateArea()/area;
        noOffloorQty.setText("Sum m2 = "+(int)floorAdaptor.calculateArea()+" "+"sq.m");
        if (result%1==0){
            tvreRult.setText(getResources().getString(R.string.totalcartoons)+"   - "+(int)floorAdaptor.calculateArea()+" "+"sq.m");
            //tvreRult.setText("Number of cartons : "+ (int)result);
        }else {
            result= result+1;
            tvreRult.setText(getResources().getString(R.string.totalcartoons)+"   - "+(int)floorAdaptor.calculateArea()+" "+"sq.m");
            // tvreRult.setText("Number of cartons : "+(int) result);
        }
        Log.e("RESULTSgfsh",">>>"+result);
          double value = Double.parseDouble(String.format("%.2f",(int)floorAdaptor.calculateArea()*saleprice));
        tvTotalCartoonPrice.setText(getResources().getString(R.string.totalprice)+"  - "+CURRENCYCODE+" "+value);
       // tvTotalCartoonPrice.setText(getResources().getString(R.string.totalprice)+": "+CURRENCYCODE+" "+String.format("%.2f",(int)floorAdaptor.calculateArea()*saleprice+" "+"sq.m"));
    }

    void  calculateArea(){
        double nooftiles = packetarea/(plenth*pWidth);
        double area =plenth*pWidth*nooftiles;

        if (edtArea.getText().toString().isEmpty()){
            edtWidth.setError("Enter Area");
        }else {
            double userares = Double.parseDouble(edtArea.getText().toString());
            noOffloorQty.setText(" Total m2 = "+(int)userares+" "+"sq.m");

            double result = userares/area;

            if (result%1==0){
                tvreRult.setText(getResources().getString(R.string.totalcartoons)+"   - "+(int)userares+" "+"sq.m");
            }else {
                result= result+1;
                tvreRult.setText( getResources().getString(R.string.totalcartoons)+"   - "+(int)userares+" "+"sq.m");
            }

            double value1 = Double.parseDouble(String.format("%.2f",(int)userares*saleprice));
            tvTotalCartoonPrice.setText(getResources().getString(R.string.totalprice)+ "   - "+CURRENCYCODE+" "+String.format("%.2f",(int)userares*saleprice));
        }

    }

    @Override
    public void onBackPressed() {
        finish();
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }
}