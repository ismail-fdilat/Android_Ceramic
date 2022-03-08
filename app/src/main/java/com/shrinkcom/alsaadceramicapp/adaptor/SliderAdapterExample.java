package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.FullProdutImageActivity;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Image;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<Image> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context, List<Image> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    public void renewItems(List<Image> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(Image sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        final Image sliderItem = mSliderItems.get(position);


        Glide.with(viewHolder.itemView)
                .load(ApiService.PRODUCT_IMAGE_URL+sliderItem.getImage())
                .fitCenter()
                .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullProdutImageActivity.class);
                intent.putExtra("imageurl",sliderItem.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.slider_image);

            this.itemView = itemView;
        }
    }

}