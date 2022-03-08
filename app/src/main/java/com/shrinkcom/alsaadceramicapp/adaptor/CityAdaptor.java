package com.shrinkcom.alsaadceramicapp.adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.pojo.citypojo.Response;

import java.util.List;

public class CityAdaptor extends RecyclerView.Adapter<CityAdaptor.MyViewHolder> {
    Context mContext;
    List<Response> cityList;
    RecyclerbuttonClick onClick;
    int selectedRow= -1;

    public CityAdaptor(Context mContext, List<Response> cityList, RecyclerbuttonClick onClick) {
        this.mContext = mContext;
        this.cityList = cityList;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_list_layout, parent, false);

        return new  MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvCityName.setText(cityList.get(position).getCityName());

        if (selectedRow==position){
            holder.layoutParent.setBackgroundColor(mContext.getResources().getColor(R.color.grey));
        }else {
            holder.layoutParent.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        holder.layoutParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedRow = position;
                notifyDataSetChanged();
                onClick.onItemClick(position,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutParent;
        TextView tvCityName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutParent = itemView.findViewById(R.id.id_parent_layout);
            tvCityName = itemView.findViewById(R.id.id_cityid);
        }
    }
}
