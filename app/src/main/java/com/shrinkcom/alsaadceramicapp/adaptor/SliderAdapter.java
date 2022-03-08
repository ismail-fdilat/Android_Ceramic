package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Response;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.viewholder> {
    Context context;
    List<Response> responses;

    public SliderAdapter(Context context, List<Response> responses) {
        this.context = context;
        this.responses = responses;
    }

    @NonNull
    @Override
    public SliderAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, parent, false);
        return new SliderAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderAdapter.viewholder holder, int position) {

        Response response = responses.get(position);
        Picasso.with(context).load(ApiService.PRODUCT_IMAGE_URL+""+response.getImages().get(position).getImage()).into(holder.slider_image);



    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView slider_image;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            slider_image = itemView.findViewById(R.id.slider_image);
        }
    }
}
