package com.shrinkcom.alsaadceramicapp.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.AddReviewActivty;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.OrderFullHistoryActivity;
import com.shrinkcom.alsaadceramicapp.fragment.ItemDescriptionFragment;
import com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo.OrderedProduct;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.thumbnilimage;

public class TrackorderAdpator extends RecyclerView.Adapter<TrackorderAdpator.viewHolder> {


    RecyclerbuttonClick recyclerbuttonClick;
    Context context;
    List<OrderedProduct> List;
    Integer confirmstatus;
    String shipstatus,orderid,order_review,payment_method,shippingarea,shipingcity,shippingaddress,delivery_status;

    public TrackorderAdpator(Context context, List<OrderedProduct> List,String shipstatus,String orderid,String order_review,String payment_method,String shippingarea,String shipingcity,String shippingaddress,String delivery_status) {
        this.context = context;
        this.List = List;
        this.shipstatus = shipstatus;
        this.orderid = orderid;
        this.order_review = order_review;
        this.payment_method = payment_method;
        this.shippingarea = shippingarea;
        this.shipingcity = shipingcity;
        this.shippingaddress = shippingaddress;
        this.delivery_status = delivery_status;

    }


    @Override
    public TrackorderAdpator.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trackorder, viewGroup, false);
        return new TrackorderAdpator.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrackorderAdpator.viewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {

        try {
            viewHolder.mainlayout.setVisibility(View.VISIBLE);
            Log.e("shipstatus",""+shipstatus);
            Log.e("delivery_status",""+delivery_status);
            if(new SessionManager(context).getLanguage().equals("en")) {
                viewHolder.tv_productnameorder.setText(List.get(position).getProducts().get(0).getProdTitle());
            }else {
                viewHolder.tv_productnameorder.setText(List.get(position).getProducts().get(0).getProdTitleAr());
            }
            if(new SessionManager(context).getLanguage().equals("en")) {
                viewHolder.tv_descriptionorder.setText(List.get(position).getProducts().get(0).getProdDesc());
            }else {
                viewHolder.tv_descriptionorder.setText(List.get(position).getProducts().get(0).getProdDescAr());
            }

            viewHolder.tv_viewfull.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderFullHistoryActivity.class);
                    intent.putExtra("pid",List.get(position).getProducts().get(0).getProdId());
                    intent.putExtra("orderid",orderid);
                    intent.putExtra("qty",List.get(position).getQty());
                    intent.putExtra("description",List.get(position).getProducts().get(0).getProdDesc());
                    intent.putExtra("pname",List.get(position).getProducts().get(0).getProdTitle());
                    intent.putExtra("image",List.get(position).getProducts().get(0).getThumbnail_image());
                    intent.putExtra("price",List.get(position).getProducts().get(0).getSale_price());
                    intent.putExtra("trackstatus",List.get(position).getStatus());
                    intent.putExtra("order_review",order_review);
                    intent.putExtra("payment_method",payment_method);
                    intent.putExtra("shippingarea",shippingarea);
                    intent.putExtra("shipingcity",shipingcity);
                    intent.putExtra("shippingaddress",shippingaddress);
                    intent.putExtra("delivery_status",delivery_status);

                    intent.putExtra("sellercity",List.get(position).getCity_name());
                    intent.putExtra("sellerlocation",List.get(position).getLocation());
                    intent.putExtra("sellershopname",List.get(position).getShop_name());
                    Log.d("statussss",List.get(position).getProducts().get(0).getStatus());
                    Log.d("delivertaddress124",""+delivery_status);

                    context.startActivity(intent);
                }
            });
            viewHolder.imv_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new ItemDescriptionFragment();
                    Bundle args = new Bundle();
                    args.putString("productid",List.get(position).getProducts().get(0).getProdId());
                    args.putString("sizeid","");
                    fragment.setArguments(args);
                    FragmentTransaction transaction = ((Home)context).getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });


//            if (shipstatus.equals("3"))
//            {
//                viewHolder.tv_titlerevproduct.setVisibility(View.VISIBLE);
//
//            }
            viewHolder.tv_priceorder.setText("SAR "+List.get(position).getProducts().get(0).getSale_price());
            viewHolder.tv_qtyorder.setText("Quantity "+List.get(position).getQty());

            String strimageurll = thumbnilimage+List.get(position).getProducts().get(0).getThumbnail_image();
            Log.e("IMAGELINKK",">>"+strimageurll);


            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.logo_2);
            requestOptions.error(R.drawable.logo_2);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(strimageurll).into(viewHolder.imv_order);


        } catch (Exception e) {
            e.printStackTrace();
            viewHolder.mainlayout.setVisibility(View.GONE);
        }

        viewHolder.tv_titlerevproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,
                        AddReviewActivty.class);
                i.putExtra("productidrv",List.get(position).getProducts().get(0).getProdId());
                i.putExtra("order_id",orderid);
                context.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {

        return List.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tv_productnameorder,tv_descriptionorder,tv_priceorder,tv_qtyorder,tv_titlerevproduct,tv_viewfull;
        LinearLayout mainlayout;
        CircleImageView imv_order;

        public viewHolder(View itemView) {
            super(itemView);
            tv_productnameorder = (TextView) itemView.findViewById(R.id.tv_productnameorder);
            tv_descriptionorder = (TextView) itemView.findViewById(R.id.tv_descriptionorder);
            tv_priceorder = (TextView) itemView.findViewById(R.id.tv_priceorder);
            tv_qtyorder = (TextView) itemView.findViewById(R.id.tv_qtyorder);
            tv_titlerevproduct = (TextView) itemView.findViewById(R.id.tv_titlerevproduct);
            imv_order = (CircleImageView) itemView.findViewById(R.id.imv_order);
            mainlayout = (LinearLayout) itemView.findViewById(R.id.idmainlayout);
            tv_viewfull = itemView.findViewById(R.id.tv_viewfull);



        }
    }

}
