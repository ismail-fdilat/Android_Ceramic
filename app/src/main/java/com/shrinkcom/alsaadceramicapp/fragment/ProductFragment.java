package com.shrinkcom.alsaadceramicapp.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.shrinkcom.alsaadceramicapp.adaptor.BrandAdpator;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdpatorSubSubCat;
import com.shrinkcom.alsaadceramicapp.adaptor.SizeAdapter;
import com.shrinkcom.alsaadceramicapp.pojo.brandpojo.BrandlistPojo;


import com.shrinkcom.alsaadceramicapp.pojo.products_pojo.Productlistpojo;
import com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Sizelistpojo;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;


public class ProductFragment extends Fragment {


    RecyclerView rv_subsubcategry;
    Toolbar toolbar;
    View view;
    String categoryid,subcategoryid,categoryid1,categoryname,categorynamear;
    LinearLayout nodata_products,ll_sub;

    CustomAdpatorSubSubCat productsAdapter;
    private List productslist = new ArrayList<>();
    Productlistpojo productsModel;

    List<String> arraylist;
    List<String> arraylistsize;
    List<String> arraylistsize_stock;
    Spinner spn_size,spn_brand,spn_prize;

    private String SIZEid,Brandid;
    int categoryPosSize = -1;
    int categoryPosBrand = -1;
    int categoryPosPrice = -1;

    String[] price = {"Price","ShowAll","0-200", "200-400", "400-600","600-800","800-1000","1000-Above"};
    String valueprice,priceselectbefore,priceselectafter;
    TextView imgfilter;
    TextView item_price;
    WishlistPojo getWishlistPojo;
    String wishid,st_pname;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_product, container, false);
     //   st_pname = getArguments().getString("pname");
        categoryid = getArguments().getString("categoryid");
        subcategoryid = getArguments().getString("subcategoryid");
        categoryname = getArguments().getString("categoryname");
        categorynamear = getArguments().getString("categorynameAr");
        toolbar = view.findViewById(R.id.toolbar_products);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getActivity().onBackPressed();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if (count > 0) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    Fragment fragment = new SubCategoryFragment();
                    Bundle args = new Bundle();
                    args.putString("categoryid", categoryid);
                    args.putString("categoryname", categoryname);
                    args.putString("categorynameAr", categorynamear);
                    fragment.setArguments(args);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                } else {

                    getActivity().onBackPressed();
                }
            }
        });
        //toolbar.inflateMenu(R.menu.price_menu);

        ll_sub =view.findViewById(R.id.ll_sub);
        nodata_products =view.findViewById(R.id.nodata_products);
        rv_subsubcategry = (RecyclerView) view.findViewById(R.id.rv_subsubcategry);
        rv_subsubcategry.setNestedScrollingEnabled(false);
        spn_size = (Spinner) view.findViewById(R.id.spn_size);
        spn_brand = (Spinner) view.findViewById(R.id.spn_brand);
        spn_prize = (Spinner) view.findViewById(R.id.spn_price);


        spn_prize.setVerticalScrollBarEnabled(true);



        //-------------------------Spinner Price Adapter Set-------------------------------------------//


        final ArrayAdapter<String> heavyadapter = new ArrayAdapter<String>(
                this.getActivity(), R.layout.spinner_itemprice, R.id.item_price,price){
            @Override
            public boolean isEnabled(int position){
                return true;

            }

        };
        heavyadapter.setDropDownViewResource(R.layout.spinner_itemprice);
        spn_prize.setAdapter(heavyadapter);


        spn_prize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoryPosPrice = i;
                valueprice = price[i];
                if (valueprice.equals("Price")){

                    return;
                }
                if (valueprice.equals("ShowAll")){
                    ProductsApi();
                    return;
                }
                    StringTokenizer heavysplit = new StringTokenizer(valueprice, "-");
                    priceselectbefore = heavysplit.nextToken();
                    priceselectafter = heavysplit.nextToken();
                    FilterProduct();

        }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // TODO: 2/10/2020 category1
        getWishlist();


        ProductsApi();
        SIZEAPI();
        BrandAPI();

        return view;
    }



    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflate menu
        inflater.inflate(R.menu.price_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //handle menu item clicks
        int id = item.getItemId();

        if (id == R.id.hightolow) {
            //do your function here
            Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.lowtohigh) {
            //do your function here
            Toast.makeText(getActivity(), "Sort", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
*/
    private void  ProductsApi() {
    /*    final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show();*/
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
                            nodata_products.setVisibility(View.GONE);
                            rv_subsubcategry.setVisibility(View.VISIBLE);

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
                                    rv_subsubcategry.setLayoutManager(mLayoutManager);
                                    rv_subsubcategry.setItemAnimator(new DefaultItemAnimator());
                                    productsAdapter=new CustomAdpatorSubSubCat(productsModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type)
                                        {
                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid",productsModel.getResponse().get(postion).getProdId());
                                            args.putString("sizeid",SIZEid);
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();


                                        }
                                    },getContext(),getWishlistPojo);

                                    rv_subsubcategry.setAdapter(productsAdapter);

                                } else {
                                    nodata_products.setVisibility(View.VISIBLE);
                                }
                            } else {
                                nodata_products.setVisibility(View.VISIBLE);
                            }
                        } catch (JsonSyntaxException e) {
                            nodata_products.setVisibility(View.VISIBLE);
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
                params.put("action","Products");
                params.put("category_id",""+ categoryid);
                params.put("subcategory_id",""+ subcategoryid);
                params.put("city",""+ UserSession.getInstance(getContext()).readPrefs(UserSession.City));
                Log.e("SENDVALUEPRODUCT", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);      }


    private void SIZEAPI() {
 /*      final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show();*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("msg======", "getSubmitapi: "+response);
                       // progressDialog.dismiss();
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();

                        final Sizelistpojo sizeTypePojo =gson.fromJson(response,Sizelistpojo.class);


                        final List<com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Response>  sizelist = new ArrayList<>();
                        com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Response responsemodel = new com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Response();
                        responsemodel.setName("Size");
                        responsemodel.setArName("بحجم");
                        responsemodel.setSizeId("0");
                        sizelist.add(responsemodel);


                        com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Response responsemodel1 = new com.shrinkcom.alsaadceramicapp.pojo.sizepojo.Response();
                        responsemodel1.setName("Show All");
                        responsemodel1.setArName("عرض الكل");
                        responsemodel1.setSizeId("1");
                        sizelist.add(responsemodel1);


                        if (sizeTypePojo.getStatus()==1) {
                            sizelist.addAll(sizeTypePojo.getResponse());
                            SizeAdapter customAdapter = new SizeAdapter(getContext(), sizelist);

                            spn_size.setAdapter(customAdapter);
                            customAdapter.notifyDataSetChanged();

                            spn_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    categoryPosSize = position;
                                    if (position==1){
                                     ProductsApi();
                                        return;
                                    }


                                  /*  try {
                                   SIZEid = sizeTypePojo.getResponse().get(position).getSizeId();
                                        Log.e("SIZEid",">>>"+SIZEid);
                                        Log.e("SIZEid","position>>>"+position);
                                        Log.e("SIZEid",">>>"+sizeTypePojo.getResponse().get(0).getSizeId());
                                        Log.e("SIZEid",">>>"+sizeTypePojo.getResponse().get(1).getSizeId());
                                        Log.e("SIZEid",">>>"+sizeTypePojo.getResponse().get(2).getSizeId());
                                        Log.e("SIZEid",">>>"+sizeTypePojo.getResponse().get(3).getSizeId());
                                    }
                                    catch (Exception e) {
                                        e.printStackTrace();
                                    }*/

                                    SIZEid =  sizelist.get(position).getSizeId();
                                  //  SIZEid = sizeTypePojo.getResponse().get(position).getSizeId();

                                    FilterProduct();

                                    Log.e("SPINEERSIZE",">>>"+categoryPosSize);
                                    Log.e("SPINEERSIZE","sizelist.get(position).getSizeId()>>>"+sizelist.get(position).getSizeId());
                                    //  MaidproviderPref.getInstance(getActivity()).writePrefs(MaidproviderPref.PREFS_SERVICE_ID, serviceTypePojo.getUserData().get(position).getServiceId());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "size");
                params.put("category_id",""+ categoryid);
                params.put("subcategory_id",""+ subcategoryid);

                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void BrandAPI() {

    /*    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show();*/
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("msg======", "getSubmitapi: "+response);
                     //   progressDialog.dismiss();
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();


                        final BrandlistPojo brandTypePojo =gson.fromJson(response,BrandlistPojo.class);
                        final List<com.shrinkcom.alsaadceramicapp.pojo.brandpojo.Response>  brandlist = new ArrayList<>();
                        com.shrinkcom.alsaadceramicapp.pojo.brandpojo.Response responsemodel = new com.shrinkcom.alsaadceramicapp.pojo.brandpojo.Response();
                        responsemodel.setBrandTitle("Brand");
                        responsemodel.setArBrandTitle("العلامة التجارية");
                        responsemodel.setBrandId("0");
                        responsemodel.setBrandImage("image");
                        responsemodel.setCategoryId("0");
                        responsemodel.setSubCategoryId("0");
                        responsemodel.setSubSubCategoryId("0");
                        brandlist.add(responsemodel);

                        com.shrinkcom.alsaadceramicapp.pojo.brandpojo.Response responsemodel1 = new com.shrinkcom.alsaadceramicapp.pojo.brandpojo.Response();
                        responsemodel1.setBrandTitle("Show All");
                        responsemodel1.setArBrandTitle("عرض الكل");
                        responsemodel1.setBrandId("0");
                        responsemodel1.setBrandImage("image");
                        responsemodel1.setCategoryId("0");
                        responsemodel1.setSubCategoryId("0");
                        responsemodel1.setSubSubCategoryId("0");
                        brandlist.add(responsemodel1);



                        if (brandTypePojo.getStatus()==1) {
                            brandlist.addAll(brandTypePojo.getResponse());
                            BrandAdpator customAdapter = new BrandAdpator(getActivity(), brandlist);
                            spn_brand.setAdapter(customAdapter);


                            customAdapter.notifyDataSetChanged();

                            spn_brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    categoryPosBrand = position;
                                    Log.e("brandSIZE",">>>"+categoryPosBrand);
                                    if (position==1){
                                        ProductsApi();
                                        return;
                                    }

                                    try {
                                       //// Brandid = brandTypePojo.getResponse().get(position).getBrandId();
                                        Brandid = brandlist.get(position).getBrandId();
                                        Log.e("brandSIZE","Brandid>>>"+Brandid);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    FilterProduct();
                                    //  MaidproviderPref.getInstance(getActivity()).writePrefs(MaidproviderPref.PREFS_SERVICE_ID, serviceTypePojo.getUserData().get(position).getServiceId());

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    //    progressDialog.dismiss();
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "brand");
                params.put("category_id",""+ categoryid);
                params.put("subcategory_id",""+ subcategoryid);

                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    //TODO 21-02-2020 API FILTER PRODUCT (PRICE,SIZE,BRAND)

    private void FilterProduct() {
/*        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show()*/;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  progressDialog.dismiss();
                        Log.e("RESPONSESEARCHFILTER", ">>" + response);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            productsModel = gson.fromJson(response, Productlistpojo.class);
                            nodata_products.setVisibility(View.GONE);
                            rv_subsubcategry.setVisibility(View.VISIBLE);

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
                                    rv_subsubcategry.setLayoutManager(mLayoutManager);
                                    rv_subsubcategry.setItemAnimator(new DefaultItemAnimator());
                                    productsAdapter=new CustomAdpatorSubSubCat(productsModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type)
                                        {
                                            Fragment fragment = new ItemDescriptionFragment();
                                            Bundle args = new Bundle();
                                            args.putString("productid",productsModel.getResponse().get(postion).getProdId());
                                            args.putString("sizeid",SIZEid);
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();

                                        }
                                    },getContext(),getWishlistPojo);

                                    rv_subsubcategry.setAdapter(productsAdapter);

                                } else {

                                }
                            } else {
                                nodata_products.setVisibility(View.VISIBLE);
                                rv_subsubcategry.setVisibility(View.GONE);
                            }
                        } catch (JsonSyntaxException e) {

                            nodata_products.setVisibility(View.VISIBLE);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // progressDialog.dismiss();
                        Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action","FilterProducts");
                params.put("category_id",""+ categoryid);
                params.put("subcategory_id",""+ subcategoryid);
                params.put("type","");
                if(categoryPosSize==0){
                    params.put("size","");
                }
                else {
                    params.put("size","");
                }
                if(categoryPosBrand==0){
                    params.put("brand_id","");
                }
                else {
                    params.put("brand_id","");
                }
                if(categoryPosPrice==0){
                    params.put("price_min","");
                }
                else {
                    params.put("price_min",""+ priceselectbefore);
                }
                if(categoryPosPrice==0){
                    params.put("price_max","");
                }
                else {
                    params.put("price_max",""+priceselectafter );
                }
                Log.e("SENDVALUSEARCHFILTER", ">>" + params);
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
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
