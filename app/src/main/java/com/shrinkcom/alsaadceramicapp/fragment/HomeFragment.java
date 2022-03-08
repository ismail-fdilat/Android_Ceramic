package com.shrinkcom.alsaadceramicapp.fragment;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.LoginActivity;
import com.shrinkcom.alsaadceramicapp.activities.MyScrollView;
import com.shrinkcom.alsaadceramicapp.activities.NewCheckoutPage;
import com.shrinkcom.alsaadceramicapp.activities.RegisterActivty;
import com.shrinkcom.alsaadceramicapp.activities.SelectYourCity;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdapterCatHome;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdaptorBrand;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomerAdptorOffer;
import com.shrinkcom.alsaadceramicapp.adaptor.NewArrivalAdapter;
import com.shrinkcom.alsaadceramicapp.adaptor.RecommendAdapter;
import com.shrinkcom.alsaadceramicapp.pojo.HomeModel.com.shrinkcom.alsaadhomeapp.HomeModel;
import com.shrinkcom.alsaadceramicapp.pojo.HomeSlidermodel.com.shrinkcom.alsaadhomeapp.HomeSlidermodel;
import com.shrinkcom.alsaadceramicapp.pojo.citypojo.Citymodel;
import com.shrinkcom.alsaadceramicapp.pojo.products_pojo.Productlistpojo;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment implements AdapterView.OnItemSelectedListener,BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    private static ViewPager mPager;
    public  int currenthome = 0;


    CircleIndicator indicator;
    ImageView imv_offersdashboard;
    RecyclerView rv_categry, rv_categryoffers, rv_brands, rv_newarrival, rv_recommend;
    View view;
    Fragment fragment;
    CustomAdapterCatHome categoryAdapter;
    HomeModel homeModel = new HomeModel();
    private SliderLayout sliderShow;

    WishlistPojo getWishlistPojo;
    String wishid,st_pname,subcatid,categoryid;
    int Sliderposition = 0;
    ImageView imv_notify;
    TextView tv_timerhr,tv_timersec,tv_timermin,tv_nodate,tv_search_home;
    Context mContext;
    LinearLayout lltodays,ll_timer,llnewarival,llrecomend;
    private CountDownTimer countDownTimer;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MyScrollView scrollView,hsc2,hsc3,hsc1;
    HomeSlidermodel homeSlidermodel;
    EditText edt_search_home;
    Productlistpojo productsModel;

    LinearLayout instagram_;
    Citymodel citymodel;
    List<com.shrinkcom.alsaadceramicapp.pojo.citypojo.Response> responses = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListid = new ArrayList<>();
    boolean isSpinnerClosed = true;


    SearchableSpinner spinner2;
    Context context;
    String citySelected,citycheck;
    String city_id ="" , country_id="" , state_id="" ,country_id_temp="", state="", state_id_temp="", city_id_tep="";

    static Mycart mycart;
    static JSONArray jsonArray ;
    RecyclerView rv_new_arrivals,rv_recommend_product;

    TextView tv_today_deals,tv_brands,tv_new_arrivals,tv_recommend_product,no_data;
    Button btn_ok;


    TextView tvCityName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();

        mycart = new Mycart(getActivity());
        mycart.open();
        data(getContext());

        Home.currentHome=0;
        scrollView = (MyScrollView) view.findViewById(R.id.scrollView);
        hsc2 = (MyScrollView) view.findViewById(R.id.hsc2);
        hsc3 = (MyScrollView) view.findViewById(R.id.hsc3);
        hsc1 = (MyScrollView) view.findViewById(R.id.hsc1);

        instagram_ =  view.findViewById(R.id.instagram_);

        // scrollView.setScrolling(false); // to disable scrolling
        scrollView.setScrolling(true); // to enable scrolling.
        hsc2.setScrolling(true); // to enable scrolling.
        hsc3.setScrolling(true); // to enable scrolling.
        hsc1.setScrolling(true); // to enable scrolling.
        initview();
        //init();
        HomeSliderApi();

        sliderShow.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderShow.setCustomAnimation(new DescriptionAnimation());
        sliderShow.setDuration(3000);
        sliderShow.addOnPageChangeListener(this);

        instagram_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://instagram.com/_u/instagram");


                Intent i= new Intent(Intent.ACTION_VIEW,uri);

                i.setPackage("com.instagram.android");

                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {

                 startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/")));
                }


            }
        });


        imv_offersdashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new BrandDealFragment();
                Bundle args = new Bundle();
                try {
                    args.putString("brandid", homeModel.getResponse().getBanner().get(0).getDeal_id());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                args.putString("type", "SALE");
                fragment.setArguments(args);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        imv_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment = new NotificationFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

            }
        });

        return view;
    }



    private void initview() {

        citySelected = UserSession.getInstance(mContext).readPrefs(UserSession.City);
        citycheck = UserSession.getInstance(mContext).readPrefs(UserSession.City);
        sliderShow = view.findViewById(R.id.slider);
        tv_today_deals = view.findViewById(R.id.tv_today_deals);
        tv_brands = view.findViewById(R.id.tv_brands);
        tv_new_arrivals = view.findViewById(R.id.tv_new_arrivals);
        tv_recommend_product = view.findViewById(R.id.tv_recommend_product);
        no_data = view.findViewById(R.id.no_data);
        rv_new_arrivals = view.findViewById(R.id.rv_new_arrivals);
        rv_recommend_product= view.findViewById(R.id.rv_recommend_product);
      /*  mPager=view.findViewById(R.id.pager);
        indicator=view.findViewById(R.id.indicator);*/
        imv_offersdashboard = view.findViewById(R.id.imv_offersdashboard);
        rv_categry = (RecyclerView) view.findViewById(R.id.rv_categry);
        rv_categryoffers = (RecyclerView) view.findViewById(R.id.rv_categryoffers);
        rv_brands = (RecyclerView) view.findViewById(R.id.rv_brands);
        rv_newarrival = (RecyclerView) view.findViewById(R.id.rv_new_arrivals);
        rv_recommend = (RecyclerView) view.findViewById(R.id.rv_recommend_product);
        imv_notify = (ImageView) view.findViewById(R.id.imv_notify);
        tv_timerhr = (TextView) view.findViewById(R.id.tv_timerhr);
        tv_timersec = (TextView) view.findViewById(R.id.tv_timersec);
        tv_timermin = (TextView) view.findViewById(R.id.tv_timermin);
        lltodays = (LinearLayout) view.findViewById(R.id.lltodays);
        llnewarival = (LinearLayout) view.findViewById(R.id.llnewarival);
        llrecomend = (LinearLayout) view.findViewById(R.id.llrecomend);
        ll_timer = (LinearLayout) view.findViewById(R.id.ll_timer);
        tv_nodate = (TextView) view.findViewById(R.id.tv_nodate);
        edt_search_home = (EditText) view.findViewById(R.id.edt_search_home);
        tvCityName = view.findViewById(R.id.id_cityname);
        tvCityName.setText( UserSession.getInstance(mContext).readPrefs(UserSession.City_NAME));

        tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertcard(getActivity());
            }
        });
        getWishlist();





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

                    fragment = new ProductSearchFragment();
                    Bundle args = new Bundle();
                    args.putString("pname", st_pname);
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

//                    ProductsearchApi();
                    return true;
                }
                return false;
            }
        });



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

                            if (productsModel.getStatus() == 1) {
                                if (productsModel.getResponse().size() > 0) {
                                    fragment = new ProductSearchFragment();
                                    Bundle args = new Bundle();
                                    args.putString("pname", st_pname);
                                    fragment.setArguments(args);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame, fragment);
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                } else {
                                    Utilitynew.UserAlert(getActivity(),(getString(R.string.no_data_found)));
                                }
                            } else {
                                Utilitynew.UserAlert(getActivity(),(getString(R.string.no_data_found)));
                            }
                        } catch (JsonSyntaxException e) {
                            // nodata_products.setVisibility(View.VISIBLE);
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





    private void HomeSliderApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEEEEGALLL", ">>" + response);
                        try {


                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            homeSlidermodel = gson.fromJson(response, HomeSlidermodel.class);
                            if (homeSlidermodel.getStatus() == 1) {
                                for (int i = 0; i < homeSlidermodel.getResponse().size(); i++) {
                                    DefaultSliderView sliderView = new DefaultSliderView(getActivity());
                                    sliderView.image(ApiService.SLIDERURL + homeSlidermodel.getResponse().get(i).getDealImage());
                                    sliderShow.addSlider(sliderView);
                                    sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                                    sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                        @Override
                                        public void onSliderClick(BaseSliderView slider) {
                                            fragment = new BrandDealFragment();
                                            Bundle args = new Bundle();
                                            args.putString("brandid", homeModel.getResponse().getSale().get(0).getDealId());
                                            args.putString("type", "SALE");
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();


                                        }
                                    });

                                }
                            }

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext,"please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "Sale");
                Log.e("SENDVALUEsale", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }




    //Todo Home API 7-3-2020

    private void HomeApi() {
        final android.app.ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //   progressDialog.dismiss();
                        Log.e("RESPONSEEEEGALHOME", ">>" + response);
                        progressDialog.dismiss();
                        sliderShow.setVisibility(View.VISIBLE);
                        instagram_.setVisibility(View.VISIBLE);
                        rv_categry.setVisibility(View.VISIBLE);
                        imv_offersdashboard.setVisibility(View.VISIBLE);
                        tv_today_deals.setVisibility(View.VISIBLE);
                        ll_timer.setVisibility(View.GONE);
                        tv_brands.setVisibility(View.VISIBLE);
                        tv_new_arrivals.setVisibility(View.VISIBLE);
                        tv_recommend_product.setVisibility(View.VISIBLE);
                        no_data.setVisibility(View.GONE);
                        rv_new_arrivals.setVisibility(View.VISIBLE);
                        rv_brands.setVisibility(View.VISIBLE);
                        rv_recommend_product.setVisibility(View.VISIBLE);
                        try {
                            int val= 0;
                            try {
                            JSONObject jsonObject = new JSONObject(response);
                                val = jsonObject.getInt("status");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }


                            if (val == 1) {
                                GsonBuilder builder = new GsonBuilder();
                                try {
                                    progressDialog.dismiss();


                                    Gson gson = builder.create();
                                    homeModel = gson.fromJson(response, HomeModel.class);

                                    if (homeModel.getResponse().getNewArrival().size()==0) {
                                        rv_new_arrivals.setVisibility(View.GONE);
                                    }else {
                                        rv_new_arrivals.setVisibility(View.VISIBLE);

                                    }
                                    if (homeModel.getResponse().getRandomProduct().size()==0){
                                        rv_recommend_product.setVisibility(View.VISIBLE);
                                    }else {
                                        rv_recommend_product.setVisibility(View.VISIBLE);

                                    }


                                    Log.d("homeeeeee",""+homeModel.getResponse());
                                } catch (JsonSyntaxException e) {
                                    e.printStackTrace();
                                    Log.d("hhhh",e.getMessage());
                                    progressDialog.dismiss();

                                }
//                                for (int i = 0; i < homeModel.getResponse().getSale().size(); i++) {
//
//                                    // TODO SLIDER SHOW DATA HOME API 7-3-2020
//
//                                    /*   DefaultSliderView sliderView = new DefaultSliderView(getActivity());
//                                        sliderView.image(homeModel.getResponse().getSale().get(i).getBaseUrl() + homeModel.getResponse().getSale().get(i).getDealImage());
//                                        sliderShow.addSlider(sliderView);
//                                        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//                                        sliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                                            @Override
//                                            public void onSliderClick(BaseSliderView slider) {
//                                                fragment = new BrandDealFragment();
//                                                Bundle args = new Bundle();
//                                                args.putString("brandid", homeModel.getResponse().getSale().get(0).getDealId());
//                                                args.putString("type", "SALE");
//                                                fragment.setArguments(args);
//                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                                transaction.replace(R.id.frame, fragment);
//                                                transaction.addToBackStack(null);
//                                                transaction.commit();
//
//
//                                            }
//                                        });
//*/
//                                }


                                // TODO CATEGORY SHOW DATA HOME API 7-3-2020

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false){
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
                                rv_categry.setLayoutManager(mLayoutManager);
                                rv_categry.setNestedScrollingEnabled(false);
                                rv_categry.setItemAnimator(new DefaultItemAnimator());
                                Log.d("TAG", "onResponse: "+homeModel.getResponse().getCategory());
                                categoryAdapter = new CustomAdapterCatHome(homeModel.getResponse().getCategory(), new RecyclerbuttonClick() {
                                    @Override
                                    public void onItemClick(int postion, int type) {
                                        if (homeModel.getResponse().getCategory().get(postion).getSubcategoryCount() == 0) {
                                            fragment = new ProductFragment();
                                            Bundle args = new Bundle();
                                            args.putString("categoryid", homeModel.getResponse().getCategory().get(postion).getCategoryId());
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();

                                        } else {
                                            Log.e("ACACCACAA", ">> CALL sub");
                                            Home.navView.setSelectedItemId(R.id.navigation_dashboard);
                                            fragment = new SubCategoryFragment();
                                            Bundle args = new Bundle();
                                            args.putString("categoryid", homeModel.getResponse().getCategory().get(postion).getCategoryId());
                                            args.putString("categoryname", homeModel.getResponse().getCategory().get(postion).getCategoryName());
                                            args.putString("categorynameAr", homeModel.getResponse().getCategory().get(postion).getArCategoryName());
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        }

                                    }
                                }, getContext());
                                rv_categry.setAdapter(categoryAdapter);


                                // TODO SINGLE BANNER SHOW DATA HOME API 7-3-2020

                                if (homeModel.getResponse().getBanner().size() == 0) {
                                    imv_offersdashboard.setVisibility(View.GONE);
                                } else {
                                    Glide.with(imv_offersdashboard)
                                            .load(homeModel.getResponse().getBanner().get(0).getBase_url() + homeModel.getResponse().getBanner().get(0).getImage())
                                            .into(imv_offersdashboard);
                                }


                                // TODO TODAYDEAL SHOW DATA HOME API 7-3-2020
                                if(homeModel.getResponse().getTodaysDeal().size()==0){

                                    ll_timer.setVisibility(View.GONE);
                                    lltodays.setVisibility(View.GONE);
                                }
                                else {


                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false) {
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
                                    rv_categryoffers.setLayoutManager(layoutManager1);
                                    rv_categryoffers.setNestedScrollingEnabled(false);
                                    rv_categryoffers.setItemAnimator(new DefaultItemAnimator());
                                    CustomerAdptorOffer adapter1 = new CustomerAdptorOffer(getContext(), homeModel.getResponse().getTodaysDeal(), new RecyclerbuttonClick() {
                                        @Override
                                        public void onItemClick(int position, int type) {
                                            Log.e("DEALSIZEEE", ">>" + position);
                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid", homeModel.getResponse().getTodaysDeal().get(position).getProdId());
                                            args.putString("sizeid", "");
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();

                                        }
                                    });
                                    rv_categryoffers.setAdapter(adapter1);
                                }
                                // TODO BRAND SHOW DATA HOME API 7-3-2020

                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true){
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
                                rv_brands.setLayoutManager(layoutManager2);
                                rv_brands.setNestedScrollingEnabled(false);
                                rv_brands.setItemAnimator(new DefaultItemAnimator());
                                CustomAdaptorBrand adapter2 = new CustomAdaptorBrand(getContext(), homeModel.getResponse().getFeaturedBrands(), new RecyclerbuttonClick() {
                                    @Override
                                    public void onItemClick(int position, int type) {
                                        fragment = new BrandDealFragment();
                                        Bundle args = new Bundle();
                                        args.putString("brandid", homeModel.getResponse().getFeaturedBrands().get(position).getBrandId());
                                        args.putString("type", "BRAND");
                                        fragment.setArguments(args);
                                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        transaction.replace(R.id.frame, fragment);
                                        transaction.addToBackStack(null);
                                        transaction.commit();
                                    }
                                });
                                rv_brands.setAdapter(adapter2);

                                // TODO NEW ARRIVAL SHOW DATA HOME API 7-3-2020

                                if(homeModel.getResponse().getNewArrival().size()==0){

                                    llnewarival.setVisibility(View.GONE);
                                }
                                else {

                                    LinearLayoutManager layoutManagernewarrival = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, true) {
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

                                    rv_newarrival.setLayoutManager(layoutManagernewarrival);
                                    rv_newarrival.setNestedScrollingEnabled(false);
                                    rv_newarrival.setItemAnimator(new DefaultItemAnimator());
                                    NewArrivalAdapter newArrivalAdapter = new NewArrivalAdapter(getContext(), homeModel.getResponse().getNewArrival(),
                                            new RecyclerbuttonClick() {
                                                @Override
                                                public void onItemClick(int position, int type) {
                                                    Fragment fragment = new ItemDescriptionFragment();
                                                    Bundle args = new Bundle();
                                                    args.putString("productid", homeModel.getResponse().getNewArrival().get(position).getProdId());
                                                    args.putString("sizeid", "");
                                                    fragment.setArguments(args);
                                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                    transaction.replace(R.id.frame, fragment);
                                                    transaction.addToBackStack(null);
                                                    transaction.commit();

                                                }
                                            }, getWishlistPojo);
                                    rv_newarrival.setAdapter(newArrivalAdapter);

                                   /* int lastViewedPosition = ((LinearLayoutManager) rv_newarrival.getLayoutManager()).findLastVisibleItemPosition();
                                    rv_newarrival.getLayoutManager().scrollToPosition(lastViewedPosition + homeModel.getResponse().getNewArrival().size() - 1);*/
                                }

                                // TODO RECOMMEND PRODUCT SHOW DATA HOME API 7-3-2020
                                if(homeModel.getResponse().getRandomProduct().size()==0){

                                    llrecomend.setVisibility(View.GONE);
                                }
                                else {
                                    GridLayoutManager layoutManagerremonned = new GridLayoutManager(getContext(), 2) {
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

                                    rv_recommend.setLayoutManager(layoutManagerremonned);
                                    rv_recommend.setNestedScrollingEnabled(false);
                                    rv_recommend.setItemAnimator(new DefaultItemAnimator());
                                    Log.d("TAG", "onResponse:Ankush ");
                                    llrecomend.setVisibility(View.VISIBLE);
                                    rv_recommend.setVisibility(View.VISIBLE);
                                    RecommendAdapter recommendAdapter = new RecommendAdapter(getContext(), homeModel.getResponse().getRandomProduct(), new RecyclerbuttonClick() {
                                        @Override
                                        public void onItemClick(int position, int type) {

                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid", homeModel.getResponse().getRandomProduct().get(position).getProdId());
                                            args.putString("sizeid", "");
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();
                                        }
                                    }, getWishlistPojo);

                                    rv_recommend.setAdapter(recommendAdapter);
                                }
                            }else {
                                progressDialog.dismiss();
                                sliderShow.setVisibility(View.GONE);
                                instagram_.setVisibility(View.GONE);
                                rv_categry.setVisibility(View.GONE);
                                imv_offersdashboard.setVisibility(View.GONE);
                                tv_today_deals.setVisibility(View.GONE);
                                ll_timer.setVisibility(View.GONE);
                                tv_brands.setVisibility(View.GONE);
                                tv_new_arrivals.setVisibility(View.GONE);
                                tv_recommend_product.setVisibility(View.GONE);
                                no_data.setVisibility(View.VISIBLE);
                                rv_new_arrivals.setVisibility(View.GONE);
                                rv_brands.setVisibility(View.GONE);
                                rv_recommend_product.setVisibility(View.VISIBLE);



                            }


                        } catch (JsonSyntaxException e) {
                            Utilitynew.UserAlert(mContext,"Please Check Your Internet Connection");
                            progressDialog.dismiss();

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  progressDialog.dismiss();
                        Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "Home");
                params.put("city", citySelected);
                Log.e("SENDVALUEHOME", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {

        Log.e("SLIDERITEM",">>>onSliderClick "+Sliderposition);
        fragment = new BrandDealFragment();
        Bundle args = new Bundle();
        args.putString("brandid", homeModel.getResponse().getFeaturedBrands().get(Sliderposition).getBrandId());
        args.putString("type","SALE");
        fragment.setArguments(args);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {
        Sliderposition = position;
        Log.e("SLIDERITEM",">>>onPageSelected "+Sliderposition);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.e("SLIDERITEM",">>>onPageScrollStateChanged "+state);
    }



    private void getWishlist() {

        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GET_WISHLIST", ">>>" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    getWishlistPojo = gson.fromJson(response, WishlistPojo.class);
                    HomeApi();
                    if (getWishlistPojo.getStatus() == 1) {
                        wishid=getWishlistPojo.getResponse().get(0).getWishProductId();
                        Log.e("WISHIDWISHID",""+wishid);
                    }
                    else{
                    }

                } catch (JsonSyntaxException e) {
                    Log.e("DDDDDDDDDDDD",""+e);
                    e.printStackTrace();
                }
            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


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



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public  void alertcard(final Context mContext) {

        new android.app.AlertDialog.Builder(mContext)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(getResources().getString(R.string.changemessage))
                .setPositiveButton(getResources().getString(R.string.tv_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       // city_id_tep= s;
                       // citySelected = s;
                        //getWishlist();
                      //  UserSession.getInstance(mContext).writePrefs(UserSession.City, citySelected);

                        String userid;
                        if (UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID).equals("")){
                            userid = UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERDEVICEID);
                            Log.e("ranuser",""+UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERDEVICEID));
                        }else {
                            userid = UserSession.getInstance(mContext).readPrefs(UserSession.PREFS_USERID);
                        }
                        Mycart mycartdb = new Mycart(mContext);
                        mycartdb.open();
                        mycartdb.deletealldata(userid);
                        mycartdb.close();
                        dialog.dismiss();

                        Intent intent = new Intent(mContext, SelectYourCity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().finish();
                        startActivity(intent);

                    }

                })
                .setNegativeButton(getResources().getString(R.string.tv_no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        citySelected = citycheck;
                        dialog.dismiss();




                      /*  Intent broadcastIntent = new Intent();
                        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
                        mContext.sendBroadcast(broadcastIntent);*/
                    }

                })
                .show();
    }



    private void data(Context activity){

        String userid;
        if (UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID).equals("")){
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERDEVICEID);
        }else {
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID);
        }

        Mycart mycart = new Mycart(getContext());
        mycart.open();
        Cursor cursor = mycart.getAllDatashopping(userid);
        jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String pid = cursor.getString(1);
                String pname = cursor.getString(2);
                String pimage = cursor.getString(3);
                String pprice = cursor.getString(4);
                String pquantity = cursor.getString(5);
                String pdescription = cursor.getString(6);

                String psizee=cursor.getString(8);
                String size_name=cursor.getString(9);
                String sellerid=cursor.getString(11);

                Log.e("DIZE_NAMEE",">>"+psizee);
                Log.e("DIZE_NAMEE",">>"+size_name);


                Log.e("PRODIDDD",">>"+pid);
                JSONObject object = new JSONObject();
                try {
                    object.put("product_id",pid);
                    object.put("qty",pquantity);
                    object.put("price",pprice);
                    object.put("totalprice",pprice);
                    object.put("size_id",psizee);
                    object.put("size",size_name);
                    object.put("seller_id",sellerid);

                    jsonArray.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        Log.e("JSONARRAAAA",">>"+jsonArray);
    }
}
