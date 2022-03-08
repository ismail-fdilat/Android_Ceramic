package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.Category;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;

import java.util.List;

public class CustomAdapterCatHome extends RecyclerView.Adapter<CustomAdapterCatHome.viewHolder> {


    List<com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.Category> categorylist;
    RecyclerbuttonClick recycleItemClickListener;
    Context context;

    public CustomAdapterCatHome(List<Category> categorylist, RecyclerbuttonClick recycleItemClickListener, Context context) {
        this.categorylist = categorylist;
        this.recycleItemClickListener=recycleItemClickListener;
        this.context= context;
    }



    @Override
    public  CustomAdapterCatHome.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemlist_dashboardcat, viewGroup, false);
        return new viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(CustomAdapterCatHome.viewHolder viewHolder,final int position) {

        Log.e("CHECKLANGUAGE", ">>" + new SessionManager(context).getLanguage());
        Log.e("categorylistcheck", ">>" +categorylist.size());
        //viewHolder.categoryname.setText(data.getCategoryName());

        if(new SessionManager(context).getLanguage().equals("en")) {
            viewHolder.iconName.setText(categorylist.get(position).getCategoryName());
        }else {
            viewHolder.iconName.setText(categorylist.get(position).getArCategoryName());
        }
        // viewHolder.categoryname.setText(data.getCategoryName());

        String strimageurll =""+categorylist.get(position).getBaseUrl()+categorylist.get(position).getImage();
        Log.e("IMAGELINKKCategory",">>"+strimageurll);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loginbanner);
        requestOptions.error(R.drawable.loginbanner);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.icon);


        viewHolder.rl_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recycleItemClickListener.onItemClick(position,0);
            }
        });

    }

    @Override
    public int getItemCount() {
        Log.e("Categorysizee",">>> "+categorylist.size());
        return categorylist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconName;
        RelativeLayout rl_home;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            iconName = (TextView) itemView.findViewById(R.id.icon_name);
            rl_home = (RelativeLayout) itemView.findViewById(R.id.rl_home);

        }
    }
}
