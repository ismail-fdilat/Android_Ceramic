package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.pojo.brandpojo.Response;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;


import java.util.List;

public class BrandAdpator extends BaseAdapter {
    Context context;

    LayoutInflater inflter;
    List<Response> poiPojos;

    public BrandAdpator(Context applicationContext, List<Response> poiPojos) {
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


        try {
            if(new SessionManager(context).getLanguage().equals("en")) {
                names.setText(poiPojos.get(i).getBrandTitle());
            }else {
                names.setText(poiPojos.get(i).getArBrandTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }
}
