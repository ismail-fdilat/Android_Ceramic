package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.pojo.notify_pojo.Notificationpojo;

import java.util.Collections;
import java.util.List;

public class MyNotificatnAdaptor extends RecyclerView.Adapter<MyNotificatnAdaptor.MyViewHolder> {

    private Context context;
    private List<Notificationpojo> notifyList;

    public MyNotificatnAdaptor(Context context, List<Notificationpojo> notifyList) {
        this.context = context;
        this.notifyList = notifyList;
        Collections.reverse(this.notifyList);
    }

    @NonNull
    @Override
    public MyNotificatnAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notication, viewGroup, false);

        return new MyNotificatnAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNotificatnAdaptor.MyViewHolder myViewHolder, int i) {
        Notificationpojo notifyModel = notifyList.get(i);
        myViewHolder.tv_msgnoti.setText(notifyList.get(i).getUserData().get(i).getMessage());
        myViewHolder.tv_datenotify.setText(notifyList.get(i).getUserData().get(i).getCreatedAt());


    }

    @Override
    public int getItemCount() {
        return notifyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_usernamenoti, tv_msgnoti, tv_datenotify;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_usernamenoti = (TextView) itemView.findViewById(R.id.tv_usernamenoti);
            tv_msgnoti = (TextView) itemView.findViewById(R.id.tv_msgnoti);
            tv_datenotify = (TextView) itemView.findViewById(R.id.tv_datenotify);


        }
    }
}

