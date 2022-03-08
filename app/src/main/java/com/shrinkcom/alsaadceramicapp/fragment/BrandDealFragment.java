package com.shrinkcom.alsaadceramicapp.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.NewCheckoutPage;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdpatorSubSubCat;
import com.shrinkcom.alsaadceramicapp.pojo.OffeModel.com.shrinkcom.alsaadhomeapp.OffeModel;

import com.shrinkcom.alsaadceramicapp.pojo.products_pojo.Productlistpojo;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.HashMap;
import java.util.Map;


public class BrandDealFragment extends Fragment {
    RecyclerView rv_brand_deal;
    Toolbar toolbar;
    View view;
    String brandid,offerid;
    String TYPE;
    Productlistpojo brandModel;
    CustomAdpatorSubSubCat productsAdapter;
    OffeModel offeModel;
    LinearLayout nodata_branddeal;
    WishlistPojo getWishlistPojo;

    public BrandDealFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_brand_deal, container, false);
        toolbar = view.findViewById(R.id.toolbar_products);
        brandid = getArguments().getString("brandid");
        TYPE = getArguments().getString("type");

        offerid = getArguments().getString("offerid");

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (TYPE.equals("BRAND")){

            Map<String, String> params = new HashMap<>();
            params.put("action","ProductsOnBrand");
            params.put("brandid",""+brandid);
            params.put("city_id",""+ UserSession.getInstance(getContext()).readPrefs(UserSession.City));
            Log.e("SENDVALUEPRODUCT", ">>" + params);
            BrandApi(params);
        }else if (TYPE.equals("TODAYDEAL")){
            Map<String, String> params = new HashMap<>();
            params.put("action","ProductsOnoffer");
            params.put("dealid",""+brandid);
            params.put("city_id",""+ UserSession.getInstance(getContext()).readPrefs(UserSession.City));
            Log.e("SENDVALUEPRODUCT", ">>" + params);
            BrandApi(params);
        }else if (TYPE.equals("SALE")){
            Map<String, String> params = new HashMap<>();
            params.put("action","ProductsOnoffer");
            params.put("dealid",""+brandid);
            params.put("city_id",""+ UserSession.getInstance(getContext()).readPrefs(UserSession.City));
            Log.e("SENDVALUEPRODUCT", ">>" + params);
            BrandApi(params);
        }

        nodata_branddeal = (LinearLayout) view.findViewById(R.id.nodata_branddeal);
        rv_brand_deal = (RecyclerView) view.findViewById(R.id.rv_brand_deal);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
        rv_brand_deal.setLayoutManager(mLayoutManager);
        rv_brand_deal.setItemAnimator(new DefaultItemAnimator());

        return view;
    }


    //Todo Brand Api 7-3-2020

    private void  BrandApi(final Map<String, String> params) {
    /*    final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                     //   progressDialog.dismiss();
                        Log.e("RESPONSEEEEGALPRODUCT", ">>" + response);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            nodata_branddeal.setVisibility(View.GONE);
                            brandModel = gson.fromJson(response, Productlistpojo.class);
                            if (brandModel.getStatus() == 1) {
                                if (brandModel.getResponse().size() > 0) {
                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
                                    rv_brand_deal.setLayoutManager(mLayoutManager);
                                    rv_brand_deal.setItemAnimator(new DefaultItemAnimator());
                                    productsAdapter=new CustomAdpatorSubSubCat(brandModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type)
                                        {
                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid",brandModel.getResponse().get(postion).getProdId());
                                            args.putString("sizeid","");
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();

                                        }
                                    },getContext(),getWishlistPojo);

                                    rv_brand_deal.setAdapter(productsAdapter);

                                } else {
                                    nodata_branddeal.setVisibility(View.VISIBLE);

                                }
                            } else {
                                nodata_branddeal.setVisibility(View.VISIBLE);
                            }
                        } catch (JsonSyntaxException e) {
                            nodata_branddeal.setVisibility(View.VISIBLE);
                            Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   progressDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);      }



    //Todo Offer Api 7-3-2020

    private void  OfferApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("RESPONSEEEEGALPRODUCT", ">>" + response);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            offeModel = gson.fromJson(response, OffeModel.class);
                            if (offeModel.getStatus() == 1) {
                                if (offeModel.getResponse().size() > 0) {
                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2);
                                    rv_brand_deal.setLayoutManager(mLayoutManager);
                                    rv_brand_deal.setItemAnimator(new DefaultItemAnimator());
                                 /*   productsAdapter=new CustomAdpatorSubSubCat(offeModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type)
                                        {
                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid",brandModel.getResponse().get(postion).getProdId());
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();

                                        }
                                    },getContext());*/

                                    rv_brand_deal.setAdapter(productsAdapter);

                                } else {
                                }
                            } else {
                            }
                        } catch (JsonSyntaxException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action","ProductsOnoffer");
                params.put("dealid",""+offerid);
                Log.e("SENDVALUEPRODUCT", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);      }




    private void getWishlist() {

        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GET_WISHLIST", ">>>" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    getWishlistPojo = gson.fromJson(response, WishlistPojo.class);


                    if (getWishlistPojo.getStatus() == 1) {

                    }
                    else{

                    }

                } catch (JsonSyntaxException e) {

                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", "GetUserWishlist");
                params.put("user_id", "" + UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID));
                Log.e("paramsAdd", ">>>>" + params.toString());

                return params;

            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringreuest);
    }

}
