package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Response;


import java.util.List;


public class SizeAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflter;
    List<Response>  poiPojos;

    public SizeAdapter(Context applicationContext, List<Response> poiPojos) {
        this.context = applicationContext;
        this.poiPojos = poiPojos;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return poiPojos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_sizeitem, null);
        TextView names = (TextView) view.findViewById(R.id.spinner_list_item);

        Log.e("sizename",""+poiPojos.get(i).getName());
        try {
            names.setText(poiPojos.get(i).getName());

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }
}