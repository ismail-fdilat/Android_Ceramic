package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.FeaturedBrand;

import java.util.List;

public class CustomAdaptorBrand  extends RecyclerView.Adapter<CustomAdaptorBrand.viewHolder> {

    Context context;
    List<FeaturedBrand> arrayList;
    RecyclerbuttonClick recycleItemClickListener;


    public CustomAdaptorBrand(Context context, List<FeaturedBrand> arrayList, RecyclerbuttonClick recycleItemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.recycleItemClickListener=recycleItemClickListener;

    }



    @Override
    public  CustomAdaptorBrand.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_brandlist, viewGroup, false);
        return new CustomAdaptorBrand.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(CustomAdaptorBrand.viewHolder viewHolder, final int position) {

       // viewHolder.imv_brand.setImageResource(Integer.parseInt(arrayList.get(position).getBrandImage()));

        String strimageurll = arrayList.get(position).getBaseUrl()+arrayList.get(position).getBrandImage();
        Log.e("IMAGELINKK",">>"+strimageurll);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.imv_brand);



viewHolder.imv_brand.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        recycleItemClickListener.onItemClick(position,position);
    }
});

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imv_brand;


        public viewHolder(View itemView) {
            super(itemView);
            imv_brand = (ImageView) itemView.findViewById(R.id.imv_brand);

        }
    }
}
