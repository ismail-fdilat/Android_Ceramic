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
import com.shrinkcom.alsaadceramicapp.pojo.SubCategory_pojo.Response;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;

import java.util.List;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.IMAGE_URL;

public class CustomAdpatorSubCat extends RecyclerView.Adapter<CustomAdpatorSubCat.viewHolder> {



    List<Response> subcategorylist;
    RecyclerbuttonClick recyclerbuttonClick;
    Context context;



    public CustomAdpatorSubCat(List<Response> subcategorylist,RecyclerbuttonClick recyclerbuttonClick, Context context) {
        this.subcategorylist = subcategorylist;
        this.recyclerbuttonClick=recyclerbuttonClick;
        this.context= context;

    }


    @Override
    public  CustomAdpatorSubCat.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subcategory, viewGroup, false);
        return new CustomAdpatorSubCat.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(CustomAdpatorSubCat.viewHolder viewHolder,final  int position) {

        Response datasubcategory=subcategorylist.get(position);

        if(new SessionManager(context).getLanguage().equals("en")) {
            viewHolder.iconName.setText(datasubcategory.getSubCategoryName());
        }else {
            viewHolder.iconName.setText(datasubcategory.getArSubCategoryName());
        }

        String strimageurll =IMAGE_URL+datasubcategory.getSubcateImage();
        Log.e("IMAGELINKK",">>"+strimageurll);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.icon);






        viewHolder.ll_subcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(position, 0);
            }
        });

    }

    @Override
    public int getItemCount() {
        return subcategorylist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        LinearLayout ll_subcat;
        TextView iconName;

        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.imv_subcat);
            iconName = (TextView) itemView.findViewById(R.id.tv_subcat);
            ll_subcat = (LinearLayout) itemView.findViewById(R.id.ll_subcat);

        }
    }




}
