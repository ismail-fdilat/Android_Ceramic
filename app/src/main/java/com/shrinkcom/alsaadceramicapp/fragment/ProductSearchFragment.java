package com.shrinkcom.alsaadceramicapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdpatorSubSubCat;
import com.shrinkcom.alsaadceramicapp.pojo.products_pojo.Productlistpojo;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.HashMap;
import java.util.Map;


public class ProductSearchFragment extends Fragment {

    View v;
    RecyclerView rv_productsearched;
    EditText edt_searchproducct;
    LinearLayout nodata_psearch;
    Productlistpojo productsModel;
    CustomAdpatorSubSubCat productsAdapter;
    WishlistPojo getWishlistPojo;
    String wishid,st_pname;
    Toolbar toolbar;
    ImageView iv_search;
    EditText edt_search_home;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_product_search, container, false);
        toolbar = v.findViewById(R.id.toolbar_psearch);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getActivity().onBackPressed();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if (count > 0) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Fragment fragment = new HomeFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {

                    getActivity().onBackPressed();
                }
            }
        });
        getWishlist();

        rv_productsearched = (RecyclerView) v.findViewById(R.id.rv_productsearched);
        nodata_psearch =v.findViewById(R.id.nodata_psearch);
        iv_search = v.findViewById(R.id.iv_search);
        edt_search_home = v.findViewById(R.id.edt_search_home);
       // edt_searchproducct =v.findViewById(R.id.edt_searchproducct);
        st_pname = getArguments().getString("pname");
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_search_home.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.GONE);


            }
        });


        edt_search_home.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int actionId,
                                          KeyEvent arg2) {
                // hide the keyboard and search the web when the enter key
                // button is pressed
                if (actionId == EditorInfo.IME_ACTION_GO
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_NEXT
                        || actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_SEARCH
                        || (arg2.getAction() == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edt_search_home.getWindowToken(), 0);
                    st_pname=edt_search_home.getText().toString().trim();
                    ProductsearchApi();
                    return true;
                }
                return false;
            }
        });

        ProductsearchApi();



        return v;
    }

    private void  ProductsearchApi() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                        //     progressDialog.dismiss();
                        Log.e("RESPONSEEEEGALPRODUCT", ">>" + response);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            productsModel = gson.fromJson(response, Productlistpojo.class);
                            nodata_psearch.setVisibility(View.GONE);
                            rv_productsearched.setVisibility(View.VISIBLE);

                            if (productsModel.getStatus() == 1) {
                                if (productsModel.getResponse().size() > 0) {
                                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),2){
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
                                    rv_productsearched.setLayoutManager(mLayoutManager);
                                    rv_productsearched.setItemAnimator(new DefaultItemAnimator());
                                    productsAdapter=new CustomAdpatorSubSubCat(productsModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type)
                                        {
                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid",productsModel.getResponse().get(postion).getProdId());
                                            args.putString("sizeid","");
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();


                                        }
                                    },getContext(),getWishlistPojo);

                                    rv_productsearched.setAdapter(productsAdapter);

                                } else {
                                    nodata_psearch.setVisibility(View.VISIBLE);
                                }
                            } else {
                                nodata_psearch.setVisibility(View.VISIBLE);
                            }
                        } catch (JsonSyntaxException e) {
                            nodata_psearch.setVisibility(View.VISIBLE);
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
                Map<String, String> params = new HashMap<>();
                params.put("action","productsearch");
                params.put("product_name",""+st_pname);
                params.put("city",""+UserSession.getInstance(getActivity()).readPrefs(UserSession.City));
                if(new SessionManager(getActivity()).getLanguage().equals("en")) {
                    params.put("type","english");
                }else {
                    params.put("type","arabic");
                }

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

                        wishid=getWishlistPojo.getResponse().get(0).getWishProductId();
                        Log.e("WISHIDWISHID",""+wishid);

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
