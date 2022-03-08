package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Review;

import java.util.List;

public class ReviewMoreAdaptor extends RecyclerView.Adapter<ReviewMoreAdaptor.viewHolder> {


    RecyclerbuttonClick recyclerbuttonClick;
    Context context;
    List<Review> reviewList;


    public ReviewMoreAdaptor(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;

    }


    @Override
    public ReviewMoreAdaptor.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reviewlist, viewGroup, false);
        return new ReviewMoreAdaptor.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ReviewMoreAdaptor.viewHolder viewHolder, final int position) {


            viewHolder.tv_usernamerv.setText(reviewList.get(position).getUserFname());
            viewHolder.tv_userrv.setText(reviewList.get(position).getReview());
            viewHolder.tv_daterv.setText(reviewList.get(position).getDate());
            viewHolder.ratingrv.setRating(Float.parseFloat(reviewList.get(position).getRating()));




    }

    @Override
    public int getItemCount() {

        return reviewList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tv_usernamerv,tv_userrv,tv_daterv;
        RatingBar ratingrv;

        public viewHolder(View itemView) {
            super(itemView);
            tv_usernamerv = (TextView) itemView.findViewById(R.id.tv_usernamerv);
            tv_userrv = (TextView) itemView.findViewById(R.id.tv_userrv);
            tv_daterv = (TextView) itemView.findViewById(R.id.tv_daterv);
            ratingrv = (RatingBar) itemView.findViewById(R.id.ratingrv);


        }
    }

}