package com.shrinkcom.alsaadceramicapp.adaptor;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.LoginActivity;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.NewArrival;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.CURRENCYCODE;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PRODUCTID;

public class NewArrivalAdapter extends RecyclerView.Adapter<NewArrivalAdapter.viewHolder> {

    Context context;
    List<NewArrival> newArrivalArrayList;
    RecyclerbuttonClick recyclerbuttonClick;
    WishlistPojo getWishlistPojo;
    String st_priceD,st_prodid;
    public NewArrivalAdapter(Context context, List<NewArrival> newArrivalArrayList,RecyclerbuttonClick recyclerbuttonClick,WishlistPojo getWishlistPojo) {
        this.context = context;
        this.newArrivalArrayList = newArrivalArrayList;
        this.recyclerbuttonClick = recyclerbuttonClick;
        this.getWishlistPojo = getWishlistPojo;
    }

    @Override
    public  NewArrivalAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_new_arrival_adapter, viewGroup, false);
        return new NewArrivalAdapter.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(final NewArrivalAdapter.viewHolder viewHolder, final int position) {

       Log.e("thumbimage",">>"+ApiService.thumbnilimage+newArrivalArrayList.get(position).getThumbImage());




        String    strimageurll = ApiService.thumbnilimage+newArrivalArrayList.get(position).getThumbImage();


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.imv_newarrival);



     /*   Glide.with( viewHolder.imv_newarrival)
                .load(ApiService.thumbnilimage+newArrivalArrayList.get(position).getThumbImage())
                .into( viewHolder.imv_newarrival);*/
        if(new SessionManager(context).getLanguage().equals("en")) {
            viewHolder.tv_arrivalname.setText(newArrivalArrayList.get(position).getProdTitle());
        }else {
            viewHolder.tv_arrivalname.setText(newArrivalArrayList.get(position).getProdTitleAr());
        }
        viewHolder.tv_pricenewarrival.setText(CURRENCYCODE+" "+newArrivalArrayList.get(position).getProdPrice()+" "+"/sq.m");


        viewHolder.imv_newarrival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(position,position);
            }
        });
        if (newArrivalArrayList.get(position).getReview().size()==0)
        {
            viewHolder.ratingBar_newar.setRating(0);
        }
        else {
            viewHolder.ratingBar_newar.setRating(Float.parseFloat(newArrivalArrayList.get(position).getReview().get(0).getRating()));
        }

        // TODO: 11/3/2020 add remove wishlist data

        try {

            if (getWishlistPojo.getResponse().size()>0) {

                for (int i = 0; i < getWishlistPojo.getResponse().size(); i++) {
                    Log.e("DADADADAA",">>"+getWishlistPojo.getResponse().get(i).getWishProductId()+" "+newArrivalArrayList.get(position).getProdId());

                    if (getWishlistPojo.getResponse().get(i).getWishProductId().equals(newArrivalArrayList.get(position).getProdId())) {

                        Log.e("PRODUCTIDDDD",">>"+ i+ " pos "+position);
                        viewHolder.imv_unfav_newar.setVisibility(View.GONE);
                        viewHolder.imv_fav_newar.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        viewHolder.imv_unfav_newar.setVisibility(View.VISIBLE);
                        viewHolder.imv_fav_newar.setVisibility(View.GONE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //  viewHolder.imv_unfav_recomd.setVisibility(View.VISIBLE);
            // viewHolder.imv_fav_recomd.setVisibility(View.GONE);
        }



        viewHolder.imv_unfav_newar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st_prodid=newArrivalArrayList.get(position).getProdId();
                if (UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID).equals("")) {
                    Utilitynew.UserAlert(context,"Please login first to add Products to wishlist");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    viewHolder.imv_fav_newar.setVisibility(View.VISIBLE);
                    viewHolder.imv_unfav_newar.setVisibility(View.INVISIBLE);
                    UserSession.getInstance(context).writePrefs(PRODUCTID, st_prodid);
                    Log.e("kjh", ">>>" + PRODUCTID);
                    addWishlist();
                }

            }
        });
        viewHolder.imv_fav_newar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.imv_fav_newar.setVisibility(View.INVISIBLE);
                viewHolder.imv_unfav_newar.setVisibility(View.VISIBLE);
                st_prodid=newArrivalArrayList.get(position).getProdId();
                removedata();
            }
        });


        if (newArrivalArrayList.get(position).getProdPrice().equals("0"))
        {
            viewHolder.tv_pricenewarrival.setVisibility(View.GONE);
        }


        // TODO: 3/12/2020 price and offerp price
       // viewHolder.tv_pricenewarrival.setText(CURRENCYCODE+" "+newArrivalArrayList.get(position).getProdPrice());



        String startdate =  newArrivalArrayList.get(position).getStartDate()+" "+ newArrivalArrayList.get(position).getStartTime();
        String strenddate = newArrivalArrayList.get(position).getEndDate()+" "+ newArrivalArrayList.get(position).getEndTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date datestart = format.parse(startdate);
            Date dateend = format.parse(strenddate);

            if (datestart.getTime()<= new Date().getTime() && dateend.getTime()>=new Date().getTime()){
                st_priceD = newArrivalArrayList.get(position).getOfferPrice();
                viewHolder.tv_pricenewarrival.setText(CURRENCYCODE + " " + newArrivalArrayList.get(position).getProdPrice()+" "+"/sq.m");
                viewHolder.tv_pricenewarrival.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);

                viewHolder.tv_newarofferedprice.setVisibility(View.VISIBLE);
                viewHolder.tv_newarofferedprice.setText(CURRENCYCODE+" "+newArrivalArrayList.get(position).getOfferPrice()+" "+"/sq.m");




                // TODO: 3/7/2020   set view here
            }else {
                // TODO: 3/7/2020   set view here
                st_priceD = newArrivalArrayList.get(position).getProdPrice();
                viewHolder.tv_pricenewarrival.setText(CURRENCYCODE + " " + newArrivalArrayList.get(position).getProdPrice()+" "+"/sq.m");
                viewHolder.tv_pricenewarrival.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.tv_newarofferedprice.setVisibility(View.VISIBLE);
                viewHolder.tv_newarofferedprice.setText(CURRENCYCODE+" "+newArrivalArrayList.get(position).getSalePrice()+" "+"/sq.m");
                Log.e("price",""+newArrivalArrayList.get(position).getProdPrice());

            }
        } catch (ParseException e) {
            e.printStackTrace();
            st_priceD = newArrivalArrayList.get(position).getProdPrice();
            viewHolder.tv_pricenewarrival.setText(CURRENCYCODE + " " + newArrivalArrayList.get(position).getProdPrice()+" "+"/sq.m");
            viewHolder.tv_pricenewarrival.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tv_newarofferedprice.setVisibility(View.VISIBLE);
            viewHolder.tv_newarofferedprice.setText(CURRENCYCODE+" "+newArrivalArrayList.get(position).getSalePrice()+" "+"/sq.m");
            Log.e("price",""+newArrivalArrayList.get(position).getProdPrice());

        }



    }

    @Override
    public int getItemCount() {
        return newArrivalArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView imv_newarrival,imv_brand;
        TextView tv_arrivalname,tv_pricenewarrival,tv_newarofferedprice;
        RatingBar ratingBar_newar;
        ImageView imv_unfav_newar,imv_fav_newar;

        public viewHolder(View itemView) {

            super(itemView);
            imv_newarrival = (ImageView) itemView.findViewById(R.id.imv_new_arrival);
            imv_brand = (ImageView) itemView.findViewById(R.id.imv_brand);
            tv_arrivalname = itemView.findViewById(R.id.tv_arrivalname);
            tv_pricenewarrival = itemView.findViewById(R.id.tv_pricenewarrival);
            tv_newarofferedprice = itemView.findViewById(R.id.tv_newarofferedprice);
            ratingBar_newar = itemView.findViewById(R.id.ratingBar_newar);
            imv_unfav_newar = itemView.findViewById(R.id.imv_unfav_newar);
            imv_fav_newar = itemView.findViewById(R.id.imv_fav_newar);


        }
    }



    public void addWishlist() {
   /*     final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);
              //  pDialog.hide();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    //    pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "AddToWishlist");
                params.put("product_id", "" + st_prodid);
                params.put("user_id", "" + UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID));

                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }

    private void removedata() {
    /*    final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  pDialog.hide();
                Log.e("RemoveWishlist", ">>>" + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("status") == 1) {

                        Utilitynew.UserAlert(context,"Removed from wishlist");
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                //pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "RemoveUserWishlist");
                params.put("wish_id", "" + UserSession.getInstance(context).readPrefs(UserSession.PREFS_WISHID));
                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }




}
