package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;


import com.shrinkcom.alsaadceramicapp.R;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {
 
    private ArrayList<Integer> images;
    private LayoutInflater inflater;
    private Context context;
 
    public MyAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }
 
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
 
    @Override
    public int getCount() {
        return images.size();
    }
 
    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        ImageView myImage = myImageLayout
                .findViewById(R.id.image);
        myImage.setImageResource(images.get(position));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }
 
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}