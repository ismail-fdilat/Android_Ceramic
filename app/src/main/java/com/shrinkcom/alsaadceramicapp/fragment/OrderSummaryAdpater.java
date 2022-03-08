package com.shrinkcom.alsaadceramicapp.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.pojo.ProductModel;

import java.util.List;

public class OrderSummaryAdpater extends RecyclerView.Adapter<OrderSummaryAdpater.MyViewHolder> {

    List<ProductModel> cartlist;
    Context mContext;
    double final_price;

    String name, price, quantity;

    public OrderSummaryAdpater(List<ProductModel> cartlist, Context mContext) {
        this.cartlist = cartlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderSummaryAdpater.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_ordersum, viewGroup, false);

        return new OrderSummaryAdpater.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderSummaryAdpater.MyViewHolder myViewHolder, final int i) {

        myViewHolder.tvnm_catry_orders.setText(cartlist.get(i).getPname());
        myViewHolder.tv_price_orders.setText(cartlist.get(i).getPprice());
        myViewHolder.tv_quantity_order.setText(cartlist.get(i).getPquantity());



    /*    String strimage = PRODUCT_IMAGE_URL + cartlist.get(i).getPimage();
        Glide.with(mContext).load(strimage).into(myViewHolder.imv_orders);*/


    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imv_orders;
        TextView tvnm_catry_orders, tv_descp_orders, tv_price_orders, tv_size_order,tv_quantity_order;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvnm_catry_orders = (TextView) itemView.findViewById(R.id.tvnm_catry_orders);
            tv_price_orders = (TextView) itemView.findViewById(R.id.tv_price_orders);
            tv_quantity_order = (TextView) itemView.findViewById(R.id.tv_quantity_order);
        }
    }

}

