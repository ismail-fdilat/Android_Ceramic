package com.shrinkcom.alsaadceramicapp.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdpatorWishlist;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Product;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WishlistFragment extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView rv_wishlist;
    WishlistPojo getWishlistPojo;
    List<com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Response> List;
    String wishid;
    LinearLayout nodata_products;
    List<Product> products = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_wishlist, container, false);

         Home.currentHome=-1;
        toolbar = view.findViewById(R.id.toolbar_wishlist);
        nodata_products =view.findViewById(R.id.nodata_wishlist);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  fragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                removeFragFromBackStack();
                transaction.commit();
            }
        });
        rv_wishlist = (RecyclerView) view.findViewById(R.id.rv_wishlist);

        // TODO: 2/10/2020 category1
        getWishlist();

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean removeFragFromBackStack() {
        try {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            List<Fragment> fragsList = manager.getFragments();
            if (fragsList.size() == 0) {
                return true;
            }
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getWishlist() {
    /*    final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show();*/
        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              //  progressDialog.dismiss();
                Log.e("GET_WISHLIST", ">>>" + response);
                try {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                getWishlistPojo = gson.fromJson(response, WishlistPojo.class);
                nodata_products.setVisibility(View.GONE);

                if (getWishlistPojo.getStatus() == 1) {

                    if (getWishlistPojo.getResponse().size() > 0) {

                        List = new ArrayList<>();
                        products.addAll(getWishlistPojo.getResponse().get(0).getProducts());

                        Log.v("DIKSHAA","PRODUCT_LIST_SIZE"+products.size());
                        Log.v("DIKSHAA","PRODUCT_LIST_SIZE"+getWishlistPojo.getResponse().get(0).getProducts().size());


                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false){


                            @Override
                            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

                                    private static final float SPEED = 500f;

                                    @Override
                                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                        return SPEED / displayMetrics.densityDpi;
                                    }

                                };
                                smoothScroller.setTargetPosition(position);
                                startSmoothScroll(smoothScroller);
                            }

                        };

                        rv_wishlist.setLayoutManager(layoutManager);
                        rv_wishlist.setNestedScrollingEnabled(false);
                        rv_wishlist.setHasFixedSize(true);
                        Log.e("DATAAAAA",">>>> "+getWishlistPojo.getResponse().size());
                        CustomAdpatorWishlist adaptor = new CustomAdpatorWishlist(getContext(), getWishlistPojo.getResponse(), products,new RecyclerbuttonClick() {
                            @Override
                            public void onItemClick(int position, int type) {

                                Fragment fragment = new ItemDescriptionFragment();
                                Bundle args = new Bundle();
                                args.putString("productid",getWishlistPojo.getResponse().get(position).getWishProductId());
                                args.putString("sizeid","");
                                fragment.setArguments(args);
                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame, fragment);
                                transaction.addToBackStack(null);
                                transaction.commit();

                            /* wishid=getWishlistPojo.getResponse().get(position).getWishId();
                                UserSession.getInstance(getContext()).writePrefs(UserSession.PREFS_WISHID, wishid);*/
                            }
                        });
                        rv_wishlist.setAdapter(adaptor);
                        adaptor.notifyDataSetChanged();
                    }}
                    else {

                    nodata_products.setVisibility(View.VISIBLE);
                }

                } catch (JsonSyntaxException e) {

                    nodata_products.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //progressDialog.dismiss();
               // Utilitynew.UserAlert(getContext(),(getResources().getString(R.string.tv_internet)));;
                nodata_products.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", "GetUserWishlist");
                params.put("user_id", "" +UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID));
                Log.e("paramsAdd", ">>>>" + params.toString());

                return params;

            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringreuest);
    }

}
