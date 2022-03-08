package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.TodaysDeal;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.CURRENCYCODE;

public class CustomerAdptorOffer extends RecyclerView.Adapter<CustomerAdptorOffer.viewHolder> {

    Context context;
   List<TodaysDeal> arrayList;
    RecyclerbuttonClick recycleItemClickListener;
    String st_priceD;

    public CustomerAdptorOffer(Context context,List<TodaysDeal> arrayList, RecyclerbuttonClick recycleItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.recycleItemClickListener = recycleItemClickListener;
    }

    @Override
    public  CustomerAdptorOffer.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offerslist, viewGroup, false);
        return new CustomerAdptorOffer.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(CustomerAdptorOffer.viewHolder viewHolder, final int position) {


        String    strimageurll = ApiService.thumbnilimage +arrayList.get(position).getThumbnailImage();


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.imv_offers);









        viewHolder.imv_offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recycleItemClickListener.onItemClick(position,position);
            }
        });



        // TODO: 3/12/2020 price and offerp price

        if (arrayList.get(position).getProdPrice().equals("0"))
        {
            viewHolder.tv_price.setVisibility(View.GONE);
        }

        // viewHolder.tv_pricenewarrival.setText(CURRENCYCODE+" "+arrayList.get(position).getProdPrice());



        String startdate =  arrayList.get(position).getStartDate()+" "+ arrayList.get(position).getStartTime();
        String strenddate = arrayList.get(position).getEndDate()+" "+ arrayList.get(position).getEndTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date datestart = format.parse(startdate);
            Date dateend = format.parse(strenddate);

            if (datestart.getTime()<= new Date().getTime() && dateend.getTime()>=new Date().getTime()){
                st_priceD = arrayList.get(position).getOfferPrice();
                viewHolder.tv_price.setText(CURRENCYCODE + " " + arrayList.get(position).getProdPrice());
                viewHolder.tv_price.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);

                viewHolder.tv_price_offer.setVisibility(View.VISIBLE);
                viewHolder.tv_price_offer.setText(CURRENCYCODE+" "+arrayList.get(position).getOfferPrice());




                // TODO: 3/7/2020   set view here
            }else {
                // TODO: 3/7/2020   set view here
                st_priceD = arrayList.get(position).getProdPrice();
                viewHolder.tv_price.setText(CURRENCYCODE + " " + arrayList.get(position).getProdPrice());
                viewHolder.tv_price.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.tv_price_offer.setVisibility(View.VISIBLE);
                viewHolder.tv_price_offer.setText(CURRENCYCODE+" "+arrayList.get(position).getSalePrice());
                Log.e("price",""+arrayList.get(position).getProdPrice());

            }
        } catch (ParseException e) {
            e.printStackTrace();
            st_priceD = arrayList.get(position).getProdPrice();
            viewHolder.tv_price.setText(CURRENCYCODE + " " + arrayList.get(position).getProdPrice());
            viewHolder.tv_price.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tv_price_offer.setVisibility(View.VISIBLE);
            viewHolder.tv_price_offer.setText(CURRENCYCODE+" "+arrayList.get(position).getSalePrice());
            Log.e("price",""+arrayList.get(position).getProdPrice());

        }





    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imv_offers;
        TextView tv_price,tv_price_offer;


        public viewHolder(View itemView) {
            super(itemView);
            imv_offers = (ImageView) itemView.findViewById(R.id.imv_offers);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_price_offer = (TextView) itemView.findViewById(R.id.tv_price_offer);

        }
    }
}
