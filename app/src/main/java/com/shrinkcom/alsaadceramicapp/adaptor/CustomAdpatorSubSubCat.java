package com.shrinkcom.alsaadceramicapp.adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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
import com.shrinkcom.alsaadceramicapp.pojo.BrandModel.com.shrinkcom.alsaadhomeapp.Brand;
import com.shrinkcom.alsaadceramicapp.pojo.products_pojo.Response;
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
import static com.shrinkcom.alsaadceramicapp.utils.ApiService.thumbnilimage;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PRODUCTID;

public class CustomAdpatorSubSubCat extends RecyclerView.Adapter<CustomAdpatorSubSubCat.viewHolder> {

    List<Response> productslist;
    List<Brand> brandlist;
    RecyclerbuttonClick recyclerbuttonClick;
    Context context;
    String st_prodid;
    WishlistPojo getWishlistPojo;
    String st_priceD;



    public CustomAdpatorSubSubCat(List<Response> productslist, RecyclerbuttonClick recyclerbuttonClick, Context context, WishlistPojo getWishlistPojo) {
        this.productslist = productslist;
        this.recyclerbuttonClick=recyclerbuttonClick;
        this.context= context;
        this.getWishlistPojo= getWishlistPojo;

    }


    @Override
    public  CustomAdpatorSubSubCat.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subsubcat, viewGroup, false);
        return new CustomAdpatorSubSubCat.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(final CustomAdpatorSubSubCat.viewHolder viewHolder, final int position) {

        Response datasubcategory=productslist.get(position);

        if(new SessionManager(context).getLanguage().equals("en")) {
            viewHolder.iconName.setText(datasubcategory.getProdTitle());
        }else {
            viewHolder.iconName.setText(datasubcategory.getProdTitleAr());
        }

        try {
            viewHolder.ratingBar_product.setRating(Float.parseFloat(String.valueOf(datasubcategory.getAverage_rating())));

            Log.d("hhhh",String.valueOf(datasubcategory.getAverage_rating()));
        }catch (Exception e){

        }






        String strimageurll = thumbnilimage+productslist.get(position).getThumbImage();
        Log.e("IMAGELINKK",">>"+strimageurll);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(viewHolder.icon);

        viewHolder.ll_subsubcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(position, 0);
            }
        });

        // TODO: 11/3/2020 add remove wishlist data
        try {

            if (getWishlistPojo.getResponse().size()>0) {

                for (int i = 0; i < getWishlistPojo.getResponse().size(); i++) {
                    Log.e("DADADADAA",">>"+getWishlistPojo.getResponse().get(i).getWishProductId()+" "+productslist.get(position).getProdId());

                    if (getWishlistPojo.getResponse().get(i).getWishProductId().equals(productslist.get(position).getProdId())) {

                        Log.e("PRODUCTIDDDD",">>"+ i+ " pos "+position);
                        viewHolder.imv_unfavproduct.setVisibility(View.GONE);
                        viewHolder.imv_favproduct.setVisibility(View.VISIBLE);
                        break;
                    } else {
                        viewHolder.imv_unfavproduct.setVisibility(View.VISIBLE);
                        viewHolder.imv_favproduct.setVisibility(View.GONE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //  viewHolder.imv_unfav_recomd.setVisibility(View.VISIBLE);
            // viewHolder.imv_fav_recomd.setVisibility(View.GONE);
        }



        viewHolder.imv_unfavproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                st_prodid=productslist.get(position).getProdId();
                if (UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID).equals("")) {
                    Utilitynew.UserAlert(context,"Please login first to add Products to wishlist");
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    viewHolder.imv_favproduct.setVisibility(View.VISIBLE);
                    viewHolder.imv_unfavproduct.setVisibility(View.INVISIBLE);
                    UserSession.getInstance(context).writePrefs(PRODUCTID, st_prodid);
                    Log.e("kjh", ">>>" + PRODUCTID);
                    addWishlist();
                }

            }
        });
        viewHolder.imv_favproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.imv_favproduct.setVisibility(View.INVISIBLE);
                viewHolder.imv_unfavproduct.setVisibility(View.VISIBLE);
                st_prodid=productslist.get(position).getProdId();
                removedata();
            }
        });

        // TODO: 3/12/2020 price and offerp price

        if (datasubcategory.getProdPrice().equals("0"))
        {
            viewHolder.tv_subcatprice.setVisibility(View.GONE);
        }



        String startdate =  datasubcategory.getStartDate()+" "+ datasubcategory.getStartTime();
        String strenddate = datasubcategory.getEndDate()+" "+ datasubcategory.getEndTime();

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date datestart = format.parse(startdate);
            Date dateend = format.parse(strenddate);

            if (datestart.getTime()<= new Date().getTime() && dateend.getTime()>=new Date().getTime()){

                Log.e("ERRORRrr","IFFF"+datasubcategory.getSalePrice());

                st_priceD = datasubcategory.getOfferPrice();
                viewHolder.tv_subcatprice.setText(CURRENCYCODE + " " + datasubcategory.getProdPrice()+" "+"/sq.m");
                viewHolder.tv_subcatprice.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);

                viewHolder.tv_subofferedprice.setVisibility(View.VISIBLE);
                viewHolder.tv_subofferedprice.setText(CURRENCYCODE+" "+datasubcategory.getOfferPrice()+" "+"/sq.m");




                // TODO: 3/7/2020   set view here
            }else {
                // TODO: 3/7/2020   set view here
                Log.e("ERRORRrr","ELSEE"+datasubcategory.getSalePrice()+" "+"/sq.m");
                st_priceD = datasubcategory.getProdPrice();
                viewHolder.tv_subcatprice.setText(CURRENCYCODE + " " + datasubcategory.getProdPrice()+" "+"/sq.m");
                viewHolder.tv_subofferedprice.setVisibility(View.VISIBLE);
                viewHolder.tv_subofferedprice.setText(CURRENCYCODE+" "+datasubcategory.getSalePrice()+" "+"/sq.m");
                viewHolder.tv_subcatprice.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                Log.e("price",""+datasubcategory.getProdPrice());

            }
        } catch (ParseException e) {
            e.printStackTrace();
            st_priceD = datasubcategory.getProdPrice();
            viewHolder.tv_subcatprice.setText(CURRENCYCODE + " " + st_priceD+" "+"/sq.m");
            viewHolder.tv_subofferedprice.setVisibility(View.VISIBLE);
            viewHolder.tv_subofferedprice.setText(CURRENCYCODE+" "+datasubcategory.getSalePrice()+" "+"/sq.m");
            viewHolder.tv_subcatprice.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
            Log.e("price",""+datasubcategory.getProdPrice());
            Log.e("ERRORRrr",""+st_priceD);
        }





    }

    @Override
    public int getItemCount() {
        return productslist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView iconName,tv_subcatprice,tv_subofferedprice;
        LinearLayout ll_subsubcat;
        RatingBar ratingBar_product;
        ImageView imv_unfavproduct,imv_favproduct;
        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.imv_subsubcat);
            iconName = (TextView) itemView.findViewById(R.id.tv_subcatnamne);
            tv_subcatprice = (TextView) itemView.findViewById(R.id.tv_subcatprice);
            ll_subsubcat = (LinearLayout) itemView.findViewById(R.id.ll_subsubcat);
            ratingBar_product = (RatingBar) itemView.findViewById(R.id.ratingBar_product);
            imv_unfavproduct = (ImageView) itemView.findViewById(R.id.imv_unfavproduct);
            imv_favproduct = (ImageView) itemView.findViewById(R.id.imv_favproduct);
            tv_subofferedprice = (TextView) itemView.findViewById(R.id.tv_subofferedprice);

        }
    }


    public void addWishlist() {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.setProgress(pDialog.getProgress() * 100);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);
                pDialog.hide();
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
                        pDialog.hide();
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
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.e("RemoveWishlist", ">>>" + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("status") == 1) {

                        Utilitynew.UserAlert(context,context.getString(R.string.removedwish));
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
                pDialog.hide();
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
