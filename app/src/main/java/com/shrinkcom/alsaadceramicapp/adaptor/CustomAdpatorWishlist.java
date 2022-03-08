package com.shrinkcom.alsaadceramicapp.adaptor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.internal.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;

import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.pojo.sizestockpojo.SizeStockpojo;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Product;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Response;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.thumbnilimage;

public class CustomAdpatorWishlist extends RecyclerView.Adapter<CustomAdpatorWishlist.viewHolder> {

    Context context;

    List<Response> arrayList;
    RecyclerbuttonClick recyclerbuttonClick;
    String wishid;
    Mycart mycart;
    String st_priceD;
    SizeStockpojo sizeStockpojo;
    int selectedsizequantity;
    List<Product> products;
    String USerCity= "";

    public CustomAdpatorWishlist(Context context, List<Response> arrayList,List<Product> products, RecyclerbuttonClick recyclerbuttonClick) {
        this.context = context;
        this.arrayList = arrayList;
        this.recyclerbuttonClick = recyclerbuttonClick;
        this.products = products;
        Collections.reverse(this.arrayList);
        Collections.reverse(this.products);

    }

    @Override
    public  CustomAdpatorWishlist.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemwishlist, viewGroup, false);
        return new CustomAdpatorWishlist.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(final CustomAdpatorWishlist.viewHolder viewHolder, final int position) {
        mycart = new Mycart(context);


        USerCity =  UserSession.getInstance(context).readPrefs(UserSession.City);


//        Product product = products.get(position);


        try {
            if(new SessionManager(context).getLanguage().equals("en")) {
                 viewHolder.iconName.setText(""+arrayList.get(position).getProducts().get(0).getProdTitle());
             }else {
                 viewHolder.iconName.setText(""+arrayList.get(position).getProducts().get(0).getProdTitleAr());
             }
            if(new SessionManager(context).getLanguage().equals("en")) {
                viewHolder.tv_descr.setText(""+arrayList.get(position).getProducts().get(0).getProdDesc());
            }
            else{
                viewHolder.tv_descr.setText(""+arrayList.get(position).getProducts().get(0).getProdDescAr());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        viewHolder.tv_sizewishlist.setText(""+arrayList.get(position).getProducts().get(0).getProdTitle());



        wishid=arrayList.get(position).getWishId();
        UserSession.getInstance(context).writePrefs(UserSession.PREFS_WISHID, wishid);

        String strimageurll2="";

        try {
            String strimageurll = arrayList.get(position).getProducts().get(0).getThumbnail_image();

            strimageurll = strimageurll.replaceAll(" ", "%20");


            strimageurll2 = thumbnilimage+strimageurll;

            Log.e("IMAGELINKKIMAGE",">>"+strimageurll);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.logo_2);
            requestOptions.error(R.drawable.logo_2);
            Glide.with(context)
                    .setDefaultRequestOptions(requestOptions)
                    .load(strimageurll2).into(viewHolder.icon);

            viewHolder.tv_price.setText("SAR "+arrayList.get(position).getProducts().get(0).getSale_price()+" "+"/sq.m");
            sizestocklist(arrayList.get(position).getProducts().get(0).getProdId());


        }catch (Exception e){

        }







        viewHolder.ll_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerbuttonClick.onItemClick(position, 0);
            }
        });




        viewHolder.tv_movecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String qty = arrayList.get(position).getProducts().get(0).getQuantity();

                int qqq = Integer.parseInt(qty);
                int updatesd = qqq + 1;

//                if (updatesd>selectedsizequantity){
//                    Utilitynew.UserAlert(context,context.getString(R.string.unavailablestck));
//                }

                if (qqq==0){
                    Utilitynew.UserAlert(context,context.getString(R.string.unavailablestck));
                    viewHolder.tv_movecart.setText("Out of stock");
                }

               else {
                mycart.open();
                int checkit = mycart.checkAvailableshopping(arrayList.get(position).getProducts().get(0).getProdId());
                if (checkit == 1) {
                    Toast.makeText(context, R.string.productalready, Toast.LENGTH_SHORT).show();
                } else {
                    String strimage = "";
                    if (arrayList.get(position).getProducts().get(0).getImages().size()>0){
                        strimage = arrayList.get(position).getProducts().get(0).getThumbnail_image();
                    }else {
                        strimage= ""  ;
                    }

                    if (USerCity.equals(arrayList.get(position).getProducts().get(0).getCity())){
                        mycart.insertDatashopping(arrayList.get(position).getProducts().get(0).getProdId(), arrayList.get(position).getProducts().get(0).getProdTitle(), strimage, arrayList.get(position).getProducts().get(0).getSale_price(), "1", arrayList.get(position).getProducts().get(0).getProdDesc(), arrayList.get(position).getProducts().get(0).getSize().get(0).getSizeId(),arrayList.get(position).getProducts().get(0).getSize().get(0).getSize(), UserSession.getInstance(context).readPrefs(UserSession.PREFS_USERID),arrayList.get(position).getProducts().get(0).getSeller().get(0).getSellerId(),arrayList.get(position).getProducts().get(0).getCity(),arrayList.get(position).getProducts().get(0).getQuantity());
                        Log.v("DIKSHAA",">sizeee>"+arrayList.get(position).getProducts().get(0).getSize().get(0).getSizeId());
                        Log.v("DIKSHAA",">sizeee>"+arrayList.get(position).getProducts().get(0).getSize().get(0).getSize());
                        Log.v("DIKSHAA",">sizeee>"+arrayList.get(position).getProducts().get(0).getSize().get(0).getName());
                        Log.d("cityi",arrayList.get(position).getProducts().get(0).getCity());



                        addcart(arrayList.get(position).getWishId());
                        arrayList.remove(position);
                        notifyDataSetChanged();

                        Toast.makeText(context, R.string.productadded, Toast.LENGTH_SHORT).show();



                        mycart.close();
                    }else {
                        Utilitynew.UserAlert(context,"Please add products of same city");
                    }

                }
            }}
        });

        viewHolder.imgdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removedata(arrayList.get(position).getWishId());
                arrayList.remove(position);

                notifyDataSetChanged();

            }
        });


        // TODO: 4/1/2020 price



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView icon,imgdelete;
        TextView tv_price,iconName,tv_descr,tv_movecart,tv_sizewishlist,tv_catrywishlist,idwishlistmessage,tv_pricewishlist,tv_subofferedpricewish;
        LinearLayout ll_wish;


        public viewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.imv_wishlist);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            imgdelete = (ImageView) itemView.findViewById(R.id.imgdelete);
            iconName = (TextView) itemView.findViewById(R.id.tv_catrywishlist);
            tv_descr = (TextView) itemView.findViewById(R.id.tv_descpwishlist);
            tv_movecart = (TextView) itemView.findViewById(R.id.tv_movecart);
            tv_sizewishlist = (TextView) itemView.findViewById(R.id.tv_sizewishlist);
            ll_wish = (LinearLayout) itemView.findViewById(R.id.ll_wish);
            idwishlistmessage = (TextView) itemView.findViewById(R.id.idwishlistmessage);



        }
    }

    private void removedata(final String wishlistid) {
    /*    final ProgressDialog pDialog = new ProgressDialog(context);
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

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
              //  pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "RemoveUserWishlist");
                params.put("wish_id", "" + wishlistid);
                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
    private void addcart(final String wishlistid) {
    /*    final ProgressDialog pDialog = new ProgressDialog(context);
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

//                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
              //  pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "RemoveUserWishlist");
                params.put("wish_id", "" + wishlistid);
                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


    public void sizestocklist(final String pid) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);

                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    sizeStockpojo = gson.fromJson(response, SizeStockpojo.class);

                    if (sizeStockpojo.getStatus() == 1) {


                        selectedsizequantity = Integer.parseInt((sizeStockpojo.getResponse().get(0).getQuantity()));



                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "Size_stock");
                params.put("product_id", "" + pid);

                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
