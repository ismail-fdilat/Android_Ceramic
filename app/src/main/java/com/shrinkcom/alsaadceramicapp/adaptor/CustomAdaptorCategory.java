package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.IMAGE_URL;

public class CustomAdaptorCategory extends RecyclerView.Adapter<CustomAdaptorCategory.viewHolder> {

    List<Response> categorylist;
    RecyclerbuttonClick recycleItemClickListener;
    Context context;
    protected List<Response> filterList;
    public CustomAdaptorCategory(List<Response> categorylist,RecyclerbuttonClick recycleItemClickListener, Context context) {
        this.categorylist = categorylist;
        this.recycleItemClickListener=recycleItemClickListener;
        this.context= context;
        this.filterList = new ArrayList<>();
        this.filterList.addAll(this.categorylist);
    }



    @Override
    public  CustomAdaptorCategory.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categorymain, viewGroup, false);
        return new CustomAdaptorCategory.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(CustomAdaptorCategory.viewHolder viewHolder,final int position) {

        Response data=categorylist.get(position);
        Log.e("CHECKLANGUAGE", ">>" + new SessionManager(context).getLanguage());
        Log.e("categorylistMainFragmentSize", ">>" + categorylist.size());
        //viewHolder.categoryname.setText(data.getCategoryName());

        if(new SessionManager(context).getLanguage().equals("en")) {
            viewHolder.iconName.setText(data.getCategoryName());
        }else {
            viewHolder.iconName.setText(data.getArCategoryName());
        }

        String strimageurll = IMAGE_URL+data.getImage();
        Log.e("IMAGELINKKMainFragment",">>"+strimageurll);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.icon);



        viewHolder.ll_cat.setOnClickListener(new View.OnClickListener() {
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
        ImageView icon;
        TextView iconName;
        LinearLayout ll_cat;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconName = (TextView) itemView.findViewById(R.id.icon_name);
            ll_cat = (LinearLayout) itemView.findViewById(R.id.ll_cat);

        }
    }
    public void updateList(List<Response> list2){
        categorylist = list2;

        Log.v("DIKSHA","LISTTTT"+categorylist.size());
        notifyDataSetChanged();
    }


}
