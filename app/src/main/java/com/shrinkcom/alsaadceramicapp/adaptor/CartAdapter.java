package com.shrinkcom.alsaadceramicapp.adaptor;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.LoginActivity;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.fragment.CartFragment;
import com.shrinkcom.alsaadceramicapp.fragment.ItemDescriptionFragment;
import com.shrinkcom.alsaadceramicapp.pojo.ProductModel;
import com.shrinkcom.alsaadceramicapp.pojo.sizestockpojo.SizeStockpojo;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.alsaadceramicapp.fragment.CartFragment.updatecartTotal;
import static com.shrinkcom.alsaadceramicapp.utils.ApiService.CURRENCYCODE;
import static com.shrinkcom.alsaadceramicapp.utils.ApiService.PRODUCT_IMAGE_URL;
import static com.shrinkcom.alsaadceramicapp.utils.ApiService.thumbnilimage;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    List<ProductModel> cartlist;
    Context mContext;
    double final_price;
    SizeStockpojo sizeStockpojo;
    String name,price,quantity,st_prodid;
    int selectedsizequantity;
     String stwishid;

    RecyclerbuttonClick recyclerbuttonClick;

    String qty ="";

    // TODO: 3/5/2020 wishapi
    WishlistPojo getWishlistPojo;
    List<com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Response> List;
    String wishid;
    public CartAdapter(List<ProductModel> cartlist, Context mContext,RecyclerbuttonClick recyclerbuttonClick) {
        this.cartlist = cartlist;
        this.mContext = mContext;
        this.recyclerbuttonClick = recyclerbuttonClick;
        //Collections.reverse(this.cartlist);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cart_adapter, viewGroup, false);

        return new CartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_addwishcartnot.setVisibility(View.VISIBLE);
        myViewHolder.tvname.setText(cartlist.get(i).getPname());
        myViewHolder.tv_descpcart.setText(cartlist.get(i).getDesp());


        int priceC = Integer.parseInt(cartlist.get(i).getPprice());
        int QTY = Integer.parseInt(cartlist.get(i).getPquantity());
        double TOTAL = priceC * QTY;
        String tt = String.valueOf(TOTAL);
        myViewHolder.tvprice.setText(CURRENCYCODE + " " + tt+" "+"/sq.m");

        myViewHolder.tvquantity.setText(cartlist.get(i).getPquantity());

        selectedsizequantity = Integer.parseInt(cartlist.get(i).getMainQty());

        Log.d("qtyyyyy",cartlist.get(i).getMainQty());

     //   myViewHolder.tv_sizemain.setText(cartlist.get(i).getPsize());
        myViewHolder.tv_sizemain.setText(cartlist.get(i).getSize_name());
        Log.e("IMAGELINKK",">>"+cartlist.get(i).getSize_name());

        String strimageurll =thumbnilimage + cartlist.get(i).getPimage();
        Log.e("IMAGELINKK",">>"+strimageurll);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.logo_2);
        requestOptions.error(R.drawable.logo_2);
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(strimageurll).into(myViewHolder.ivjimage);


  /*      String strimage = PRODUCT_IMAGE_URL + cartlist.get(i).getPimage();
        Glide.with(mContext).load(strimage).into(myViewHolder.ivjimage);*/

        st_prodid=cartlist.get(i).getPid();
        stwishid= UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_WISHID);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ItemDescriptionFragment();
                Bundle args = new Bundle();
                args.putString("productid",cartlist.get(i).getPid());
                args.putString("sizeid","");
                fragment.setArguments(args);
                FragmentTransaction transaction = ((Home)mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        myViewHolder.imgclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(R.string.tv_cartdialog1)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utilitynew.deletproductshopping(mContext, cartlist.get(i).getPid());
                                cartlist.remove(cartlist.get(i).getPid());
                                CartFragment.detCartAdaptor(mContext);
                                updatecartTotal();

                            }

                        })
                        .setNegativeButton(R.string.no, null)
                        .show();




            }
        });



        myViewHolder.tvquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub




            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 qty = s.toString();
                if (!qty.isEmpty()) {

                    if (Integer.parseInt(s.toString()) > selectedsizequantity) {
                        UserAlert(mContext, mContext.getString(R.string.reqqqq),myViewHolder);

                    }else {
                        //qty = myViewHolder.tvquantity.getText().toString().trim();
                        Log.d("TAG", "onTextChanged: "+Integer.parseInt(qty));
                        int qqq = Integer.parseInt(qty);
                        int updatesd = qqq ;
                        Utilitynew.updateproductshopping(mContext, cartlist.get(i).getPid(), "" + updatesd);
                        double price = Double.parseDouble(cartlist.get(i).getPprice());

                        double quantities = Double.parseDouble(qty);

                        double p1 = price * quantities;

                        myViewHolder.tvprice.setText(""+ApiService.CURRENCYCODE+ p1 + " "+"/sq.m");

                        updatecartTotal();
                        recyclerbuttonClick.onItemClick(i,0);

//                        Utilitynew.updateproductshopping(mContext, cartlist.get(i).getPid(), "" + Integer.parseInt(qty));
//                        setupdatevalue(myViewHolder.tvprice, myViewHolder.tvquantity, "" + Integer.parseInt(qty), i);
//                        updatecartTotal();
                        //recyclerbuttonClick.onItemClick(i,0);
                    }
                }else {

                    if (qty.isEmpty()){
                        qty = "1";
                        myViewHolder.tvquantity.setText("1");
                        Log.d("TAG", "onTextChanged: "+Integer.parseInt(qty));
                        int qqq = Integer.parseInt(qty);
                        int updatesd = qqq ;
                        Utilitynew.updateproductshopping(mContext, cartlist.get(i).getPid(), "" + updatesd);
                        double price = Double.parseDouble(cartlist.get(i).getPprice());

                        double quantities = Double.parseDouble(qty);

                        double p1 = price * quantities;

                        myViewHolder.tvprice.setText(""+ApiService.CURRENCYCODE+ p1 + " "+"/sq.m");

                        updatecartTotal();
                        recyclerbuttonClick.onItemClick(i,0);
                    }


                }
            }
        });


        // TODO: 22-04-2019 product increment r decrement hare
        myViewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 qty = myViewHolder.tvquantity.getText().toString().trim();

                if (qty.equals("1")) {
                    Utilitynew.UserAlert(mContext,mContext.getString(R.string.oneqty));
                } else {
                    Log.d("TAG", "onTextChanged: "+Integer.parseInt(qty));
                    String qtyupdate = myViewHolder.tvquantity.getText().toString().trim();

                    int qqq = Integer.parseInt(qtyupdate);
                    int updatesd = qqq - 1;
                    Utilitynew.updateproductshopping(mContext, cartlist.get(i).getPid(), "" + updatesd);
                    setupdatevalue(myViewHolder.tvprice, myViewHolder.tvquantity, "" + updatesd, i);
                    updatecartTotal();
                    //  notifyDataSetChanged();
                }


            }
        });

        myViewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 qty = myViewHolder.tvquantity.getText().toString().trim();
                Log.d("TAG", "onTextChanged: "+Integer.parseInt(qty));
                int qqq = Integer.parseInt(qty);
                int updatesd = qqq + 1;

                if (updatesd>selectedsizequantity){
                    Utilitynew.UserAlert(mContext,mContext.getString(R.string.unavailablestck));
                }else {
                    Utilitynew.updateproductshopping(mContext, cartlist.get(i).getPid(), "" + updatesd);
                    setupdatevalue(myViewHolder.tvprice, myViewHolder.tvquantity, "" + updatesd, i);
                    updatecartTotal();
                    setupdatevalue(myViewHolder.tvprice, myViewHolder.tvquantity,""+ updatesd,i);
                }

            }
        });



        myViewHolder.tv_addwishcartnot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myViewHolder.tv_addwishcartnot.setVisibility(View.GONE);
                myViewHolder.tv_addwishcart.setVisibility(View.VISIBLE);
                new AlertDialog.Builder(mContext)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage(R.string.areyusure)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID).equals("")) {
                                    Utilitynew.UserAlert(mContext, mContext.getString(R.string.firstlogin));
                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                    mContext.startActivity(intent);
                                } else {
                                    addWishlist(cartlist.get(i).getPid());
                                    Utilitynew.deletproductshopping(mContext, cartlist.get(i).getPid());
                                    cartlist.remove(cartlist.get(i).getPid());
                                    CartFragment.detCartAdaptor(mContext);
                                    updatecartTotal();
                                }
                            }

                        }
                        )
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                myViewHolder.tv_addwishcartnot.setVisibility(View.VISIBLE);
                                myViewHolder.tv_addwishcart.setVisibility(View.GONE);
                            }
                        })
                        .show();


            }
        });


        sizestocklist(cartlist.get(i).getPid());

        getWishlist();


    }


    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivjimage;
        TextView tvname, tvprice, tvquantity,imgclose,tv_descpcart,tv_sizemain,tv_addwishcartnot,tv_addwishcart;
        TextView btnplus, btnminus;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivjimage = (ImageView) itemView.findViewById(R.id.imv_cart);
            imgclose = (TextView) itemView.findViewById(R.id.tv_removecart);
            tvname = (TextView) itemView.findViewById(R.id.tv_catrycart);
            tvprice = (TextView) itemView.findViewById(R.id.tv_pricemain);
            tv_descpcart = (TextView) itemView.findViewById(R.id.tv_descpcart);
            tvquantity = (TextView) itemView.findViewById(R.id.quantity_cart);
            btnplus = (TextView) itemView.findViewById(R.id.increase_cart);
            btnminus = (TextView) itemView.findViewById(R.id.decrease_cart);
            tv_sizemain = (TextView) itemView.findViewById(R.id.tv_sizemain);
            tv_addwishcartnot = (TextView) itemView.findViewById(R.id.tv_addwishcartnot);
            tv_addwishcart = (TextView) itemView.findViewById(R.id.tv_addwishcart);
        }
    }


    void setupdatevalue(TextView tvprice, TextView tvquantity, String quantity, int i) {

        Log.e("UPDATEqty", ">>" + quantity);
        double price = Double.parseDouble(cartlist.get(i).getPprice());

        double quantities = Double.parseDouble(quantity);

        double p1 = price * quantities;

        tvprice.setText(""+ApiService.CURRENCYCODE+ p1 + " "+"/sq.m");
        tvquantity.setText("" + quantity);


    }



    public void addWishlist(final String pid) {
     /*   final ProgressDialog pDialog = new ProgressDialog(mContext);
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



                        Utilitynew.deletproductshopping(mContext, pid);
                        cartlist.remove(pid);
                        notifyDataSetChanged();
                        CartFragment.detCartAdaptor(mContext);
                        updatecartTotal();

                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                      //  pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "AddToWishlist");
                params.put("product_id", "" + pid);
                params.put("user_id", "" + UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID));

                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    public void sizestocklist(final String pid) {
       /* final ProgressDialog pDialog = new ProgressDialog(mContext);
        pDialog.setMessage("Loading...");
        pDialog.show();
*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);
             //   pDialog.hide();
                try {

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    sizeStockpojo = gson.fromJson(response, SizeStockpojo.class);

                    if (sizeStockpojo.getStatus() == 1) {


//                        selectedsizequantity = Integer.parseInt((sizeStockpojo.getResponse().get(0).getQuantity()));



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
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                     //   pDialog.hide();
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

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);
    }

    private void getWishlist() {

        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GET_WISHLIST", ">>>" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    getWishlistPojo = gson.fromJson(response, WishlistPojo.class);


                    if (getWishlistPojo.getStatus() == 1) {

                        wishid=getWishlistPojo.getResponse().get(0).getWishProductId();
                        Log.e("WISHIDWISHID",""+wishid);

                    }
                    else{

                    }

                } catch (JsonSyntaxException e) {

                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", "GetUserWishlist");
                params.put("user_id", "" + UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID));
                Log.e("paramsAdd", ">>>>" + params.toString());

                return params;

            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringreuest);
    }

    public static void UserAlert(final Context mContext, String message, final MyViewHolder myViewHolder) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        myViewHolder.tvquantity.setText("");
                        //do things
                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }

}
