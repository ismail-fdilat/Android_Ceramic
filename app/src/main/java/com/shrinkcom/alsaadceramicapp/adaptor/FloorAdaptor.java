package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.utils.FloorModel;

import java.util.List;

public class FloorAdaptor extends RecyclerView.Adapter<FloorAdaptor.MyViewHolder> {
    @NonNull
    List<FloorModel> floorlist;
    Context mContext;

    public FloorAdaptor(@NonNull List<FloorModel> floorlist, Context mContext) {
        this.floorlist = floorlist;
        this.mContext = mContext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_floor_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull  FloorAdaptor.MyViewHolder holder, final int position) {

        if (position==0){
            holder.imgRemove.setVisibility(View.INVISIBLE);
        }

        holder.edtWidth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                FloorModel floorModel = new FloorModel();

                try {
                    floorModel.setWidth(Double.parseDouble(s.toString()));
                    floorModel.setHeight(floorlist.get(position).getHeight());
                    floorlist.set(position,floorModel);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    floorModel.setWidth(0.0);
                    floorModel.setHeight(floorlist.get(position).getHeight());
                    floorlist.set(position,floorModel);
                }


            }
        });


        holder.edtheight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                FloorModel floorModel = new FloorModel();

                try {
                    floorModel.setHeight(Double.parseDouble(s.toString()));
                    floorModel.setWidth(floorlist.get(position).getWidth());
                    floorlist.set(position,floorModel);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    floorModel.setHeight(0.0);
                    floorModel.setWidth(floorlist.get(position).getWidth());
                    floorlist.set(position,floorModel);
                }


            }
        });



        holder.imgRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return floorlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgRemove;
        EditText edtWidth;
        EditText edtheight;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            imgRemove = itemView.findViewById(R.id.id_removeposition);
            edtWidth = itemView.findViewById(R.id.edt_width);
            edtheight = itemView.findViewById(R.id.edt_height);
        }
    }

    public void removeAt(int position) {
        floorlist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, floorlist.size());
    }

    public  void  addFloor(){
        floorlist.add(new FloorModel());
        notifyDataSetChanged();
    }

    public double calculateArea(){
        double result = 0.0;
        for (int i = 0; i < floorlist.size(); i++) {

            double width = floorlist.get(i).getWidth();
            double height  = floorlist.get(i).getHeight();
            result = result+width*height;

        }

        return  result;
    }
}
