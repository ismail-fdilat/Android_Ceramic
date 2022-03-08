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
import com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.RandomProduct;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.CURRENCYCODE;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PRODUCTID;

public class RecommendAdapter  extends RecyclerView.Adapter<RecommendAdapter.viewHolder> {

    Context context;
   List<RandomProduct> randomProductArrayList;
    RecyclerbuttonClick recyclerbuttonClick;
    WishlistPojo getWishProductId;
    String st_prodid,st_priceD;

    public RecommendAdapter(Context context, List<RandomProduct> randomProductArrayList, RecyclerbuttonClick recyclerbuttonClick,WishlistPojo getWishProductId) {
        this.context = context;
        this.randomProductArrayList = randomProductArrayList;
        this.recyclerbuttonClick = recyclerbuttonClick;
        this.getWishProductId = getWishProductId;
    }

    @Override
    public  RecommendAdapter.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_recommend_adapter, viewGroup, false);
        return new RecommendAdapter.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(final RecommendAdapter.viewHolder viewHolder, final int position) {


        String  strimageurll = "https://shrinkcom.com/ceramic-api/seller/"+randomProductArrayList.get(position).getThumbImage();


        Log.e("RECOMNDEDIMAGES",">>> "+strimageurll);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.imgrecommend);

/*
        Glide.with(viewHolder.imgrecommend)
                .load(ApiService.thumbnilimage+randomProductArrayList.get(position).getThumbImage())
                .into(viewHolder.imgrecommend);
*/

        if(new SessionManager(context).getLanguage().equals("en")) {
            viewHolder.tv_recomndname.setText(randomProductArrayList.get(position).getProdTitle());
        }else {
            viewHolder.tv_recomndname.setText(randomProductArrayList.get(position).getProdTitleAr());
        }
        viewHolder.tv_pricerecomnd.setText(CURRENCYCODE+" "+randomProductArrayList.get(position).getProdPrice());

        if (randomProductArrayList.get(position).getReview().size()==0)
        {
            viewHolder.ratingBar_recomd.setRating(0);
        }
        else {
            viewHolder.ratingBar_recomd.setRating(Float.parseFloat(randomProductArrayList.get(position).getAverage_rating()));
        }

        viewHolder.imgrecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(position,position);
            }
        });



        try {

            if (getWishProductId.getResponse().size()>0) {

                for (int i = 0; i < getWishProductId.getResponse().size(); i++) {
                    Log.e("DADADADAA",">>"+getWishProductId.getResponse().get(i).getWishProductId()+" "+randomProductArrayList.get(position).getProdId());

                    if (getWishProductId.getResponse().get(i).getWishProductId().equals(randomProductArrayList.get(position).getProdId())) {

                        Log.e("PRODUCTIDDDD",">>"+ i+ " pos "+position);
                        viewHolder.imv_unfav_recomd.setVisibility(View.GONE);
                        viewHolder.imv_fav_recomd.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        viewHolder.imv_unfav_recomd.setVisibility(View.VISIBLE);
                        viewHolder.imv_fav_recomd.setVisibility(View.GONE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
          //  viewHolder.imv_unfav_recomd.setVisibility(View.VISIBLE);
           // viewHolder.imv_fav_recomd.setVisibility(View.GONE);
        }


        viewHolder.imv_unfav_recomd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st_prodid=randomProductArrayList.get(position).getProdId();

                if (UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID).equals("")) {
                    Utilitynew.UserAlert(context,"Please login first to add Products to wishlist");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    viewHolder.imv_fav_recomd.setVisibility(View.VISIBLE);
                    viewHolder.imv_unfav_recomd.setVisibility(View.INVISIBLE);
                    UserSession.getInstance(context).writePrefs(PRODUCTID, st_prodid);
                    Log.e("kjh", ">>>" + PRODUCTID);
                    addWishlist();
                }

            }
        });
        viewHolder.imv_fav_recomd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.imv_fav_recomd.setVisibility(View.INVISIBLE);
                viewHolder.imv_unfav_recomd.setVisibility(View.VISIBLE);
                st_prodid=randomProductArrayList.get(position).getProdId();

                removedata();
            }
        });


        // TODO: 3/12/2020 price and offerp price
        viewHolder.tv_pricerecomnd.setText(CURRENCYCODE+" "+randomProductArrayList.get(position).getProdPrice()+" "+"/sq.m");

        if (randomProductArrayList.get(position).getProdPrice().equals("0"))
        {
            viewHolder.tv_pricerecomnd.setVisibility(View.GONE);
        }



        String startdate =  randomProductArrayList.get(position).getStartDate()+" "+ randomProductArrayList.get(position).getStartTime();
        String strenddate = randomProductArrayList.get(position).getEndDate()+" "+ randomProductArrayList.get(position).getEndTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date datestart = format.parse(startdate);
            Date dateend = format.parse(strenddate);

            if (datestart.getTime()<= new Date().getTime() && dateend.getTime()>=new Date().getTime()){
                st_priceD = randomProductArrayList.get(position).getOfferPrice();
                viewHolder.tv_pricerecomnd.setText(CURRENCYCODE + " " + randomProductArrayList.get(position).getProdPrice()+" "+"/sq.m");
                viewHolder.tv_pricerecomnd.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);

                viewHolder.tv_recofferedprice.setVisibility(View.VISIBLE);
                viewHolder.tv_recofferedprice.setText(CURRENCYCODE+" "+randomProductArrayList.get(position).getOfferPrice()+" "+"/sq.m");


                Log.e("offerprice",""+st_priceD);

                // TODO: 3/7/2020   set view here
            }else {
                // TODO: 3/7/2020   set view here

                st_priceD = randomProductArrayList.get(position).getProdPrice();
                viewHolder.tv_pricerecomnd.setText(CURRENCYCODE + " " + randomProductArrayList.get(position).getProdPrice()+" "+"/sq.m");
                viewHolder.tv_pricerecomnd.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.tv_recofferedprice.setVisibility(View.VISIBLE);
                viewHolder.tv_recofferedprice.setText(CURRENCYCODE+" "+randomProductArrayList.get(position).getSalePrice()+" "+"/sq.m");
                Log.e("price",""+randomProductArrayList.get(position).getProdPrice());

            }
        } catch (Exception e) {
            e.printStackTrace();
            st_priceD = randomProductArrayList.get(position).getProdPrice();
            viewHolder.tv_pricerecomnd.setText(CURRENCYCODE + " " + randomProductArrayList.get(position).getProdPrice()+" "+"/sq.m");
            viewHolder.tv_pricerecomnd.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.tv_recofferedprice.setVisibility(View.VISIBLE);
            viewHolder.tv_recofferedprice.setText(CURRENCYCODE+" "+randomProductArrayList.get(position).getSalePrice()+" "+"/sq.m");
            Log.e("price",""+randomProductArrayList.get(position).getProdPrice());

        }

    }

    @Override
    public int getItemCount() {
        return randomProductArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
      ImageView imgrecommend;
        TextView tv_recomndname,tv_pricerecomnd,tv_recofferedprice;
        RatingBar ratingBar_recomd;
        ImageView imv_unfav_recomd,imv_fav_recomd;

        public viewHolder(View itemView) {
            super(itemView);
            imgrecommend=itemView.findViewById(R.id.img_recommend);
            tv_recomndname=itemView.findViewById(R.id.tv_recomndname);
            tv_pricerecomnd=itemView.findViewById(R.id.tv_pricerecomnd);
            ratingBar_recomd=itemView.findViewById(R.id.ratingBar_recomd);
            imv_unfav_recomd=itemView.findViewById(R.id.imv_unfav_recomd);
            imv_fav_recomd=itemView.findViewById(R.id.imv_fav_recomd);
            tv_recofferedprice=itemView.findViewById(R.id.tv_recofferedprice);

        }
    }

    public void addWishlist() {
       /* final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");

        pDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);
               // pDialog.hide();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else
                        {

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
                       // pDialog.hide();
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
      /*  final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // pDialog.hide();
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
               // pDialog.hide();
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
