package com.shrinkcom.alsaadceramicapp.utils;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.shrinkcom.alsaadceramicapp.R;

public class ProgressDialog extends DialogFragment
{
    ImageView imageView_1,imageView_2,imageView_3,imageView_4,imageView_5;
    int position=0;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_dialog, container);
        imageView_1 = (ImageView) view.findViewById(R.id.pd_iv_fruit_1);
        imageView_2 = (ImageView) view.findViewById(R.id.pd_iv_fruit_2);
        imageView_3 = (ImageView) view.findViewById(R.id.pd_iv_fruit_3);
        imageView_4 = (ImageView) view.findViewById(R.id.pd_iv_fruit_4);
        imageView_5 = (ImageView) view.findViewById(R.id.pd_iv_fruit_5);
        getDialog().setCancelable(false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeImageSlider();
    }

    private void changeImageSlider() {
        position++;
        switch (position)
        {

            default:{position=0;}
        }


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                changeImageSlider();
            }
        }, 500);
    }

}
