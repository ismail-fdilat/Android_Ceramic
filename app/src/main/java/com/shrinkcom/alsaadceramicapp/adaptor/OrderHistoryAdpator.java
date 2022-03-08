package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.WebViewActivity;
import com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo.Response;

import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class OrderHistoryAdpator extends RecyclerView.Adapter<OrderHistoryAdpator.viewHolder> {

    List<Response> categorylist;
    RecyclerbuttonClick recycleItemClickListener;
    Context context;
    public OrderHistoryAdpator(List<Response> categorylist,RecyclerbuttonClick recycleItemClickListener,Context context) {
        this.categorylist = categorylist;
        this.recycleItemClickListener=recycleItemClickListener;
        this.context=context;
        Collections.reverse(this.categorylist);

    }



    @Override
    public  OrderHistoryAdpator.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderhistory, viewGroup, false);
        return new OrderHistoryAdpator.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(OrderHistoryAdpator.viewHolder viewHolder,final int position) {
        final Response data=categorylist.get(position);

      viewHolder.tv_dateorders.setText(data.getDate());
      viewHolder.tv_orderid.setText("#"+data.getBookedId());
      viewHolder.tv_price.setText("SAR "+data.getTotal());

      try{
          Log.d("delivery_status>>>",data.getDelivery_status());

      }catch (Exception e){

      }



      viewHolder.tv_invoice.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context, WebViewActivity.class);
              intent.putExtra("orderid",data.getBookedId());
              intent.putExtra("URl",data.getInvoice());
              context.startActivity(intent);

          }
      });


        viewHolder.tv_trackorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleItemClickListener.onItemClick(position,0);

            }
        });



    }

    @Override
    public int getItemCount() {
        return categorylist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        CircleImageView imv_order;
        TextView tv_orderid,tv_dateorders,tv_trackorder,tv_price,tv_invoice;
        LinearLayout ll_orderh;

        public viewHolder(View itemView) {
            super(itemView);

            tv_orderid = (TextView) itemView.findViewById(R.id.tv_orderid);
            tv_dateorders = (TextView) itemView.findViewById(R.id.tv_dateorders);
            tv_trackorder = (TextView) itemView.findViewById(R.id.tv_trackorder);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_invoice = itemView.findViewById(R.id.tv_invoice);

        }
    }
}
