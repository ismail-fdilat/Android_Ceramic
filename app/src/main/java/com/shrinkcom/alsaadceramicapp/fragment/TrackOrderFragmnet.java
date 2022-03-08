package com.shrinkcom.alsaadceramicapp.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.TrackorderAdpator;
import com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo.OrderedProduct;

import java.util.List;


public class TrackOrderFragmnet extends Fragment {


    View view;
    Toolbar toolbar;
    String orderid,image,name,namear,description,descriptionar,order_review,payment_method;
    TextView tv_orderid,tv_productnameorder,tv_descriptionorder;

    String shippingstatus,confirmstatus,shippingaddress,shippingarea,shippingcity,delivery_status;
    List<OrderedProduct> list;
    RecyclerView rv_trackorder;
    ImageView imv_1checked,imv_1unchecked,imv_2checked,imv_2unchecked,imv_3checked,imv_3unchecked;
    TextView tv_status1,tv_status2,tv_status3;
    View v1,v2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_track_order_fragmnet, container, false);


        initview();
        orderid = getArguments().getString("orderid");
     shippingstatus = getArguments().getString("shippingstatus");
        order_review = getArguments().getString("order_review");
        payment_method = getArguments().getString("payment_method");
        shippingaddress = getArguments().getString("shippingaddress");
        shippingarea = getArguments().getString("shippingarea");
        shippingcity = getArguments().getString("shippingcity");
        delivery_status = getArguments().getString("delivery_status");
        try{
            Log.d("deliverytrackkk",""+delivery_status.toString());

        }catch (Exception e){

        }
     //   shippingstatus = Integer.valueOf("1");
       // UserSession.getInstance(getContext()).writePrefs(UserSession.SHIPSTATUS, String.valueOf(shippingstatus));



//        if(shippingstatus.equals("1"))
//        {
//
//            imv_1checked.setVisibility(View.VISIBLE);
//            imv_1unchecked.setVisibility(View.GONE);
//           tv_status1.setText("Ordered");
//
//        }
//      else  if(shippingstatus.equals("2"))
//        {
//           v1.setBackgroundColor(getResources().getColor(R.color.colorappbar));
//            imv_1checked.setVisibility(View.VISIBLE);
//            imv_2checked.setVisibility(View.VISIBLE);
//            imv_1unchecked.setVisibility(View.GONE);
//            imv_2unchecked.setVisibility(View.GONE);
//            tv_status2.setText("Shipped");
//
//        }
//      else  if(shippingstatus.equals("3"))
//        {
//          v2.setBackgroundColor(getResources().getColor(R.color.colorappbar));
//            imv_1checked.setVisibility(View.VISIBLE);
//            imv_2checked.setVisibility(View.VISIBLE);
//            imv_3checked.setVisibility(View.VISIBLE);
//            imv_1unchecked.setVisibility(View.GONE);
//            imv_2unchecked.setVisibility(View.GONE);
//            imv_3unchecked.setVisibility(View.GONE);
//            tv_status3.setText("Delivered");
//
//        }



        tv_orderid.setText("#"+orderid);




        list = getArguments().getParcelableArrayList("listorderproduct");


            TrackorderAdpator rvadptor = new TrackorderAdpator(getContext(), list, shippingstatus,orderid,order_review,payment_method,shippingarea,shippingcity,shippingaddress,delivery_status);
            LinearLayoutManager horizontalLayoutManagaer1
                    = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rv_trackorder.setLayoutManager(horizontalLayoutManagaer1);
            rv_trackorder.setAdapter(rvadptor);

        return view;
    }

    private void initview() {
        toolbar = view.findViewById(R.id.toolbar_track);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        tv_orderid = view.findViewById(R.id.tv_orderid);
        rv_trackorder = view.findViewById(R.id.rv_trackorder);
        imv_1checked = view.findViewById(R.id.imv_1checked);
        imv_2checked = view.findViewById(R.id.imv_2checked);
        imv_3checked = view.findViewById(R.id.imv_3checked);
        imv_1unchecked = view.findViewById(R.id.imv_1unchecked);
        imv_2unchecked = view.findViewById(R.id.imv_2uncheck);
        imv_3unchecked = view.findViewById(R.id.imv_3uncheck);
        tv_status1 = view.findViewById(R.id.tv_status1);
        tv_status2 = view.findViewById(R.id.tv_status2);
        tv_status3 = view.findViewById(R.id.tv_status3);
        v1 = view.findViewById(R.id.v1);
        v2 = view.findViewById(R.id.v2);



    }


}
