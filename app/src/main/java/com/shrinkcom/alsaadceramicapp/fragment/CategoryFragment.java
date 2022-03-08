package com.shrinkcom.alsaadceramicapp.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdaptorCategory;
import com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.CategoryListPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CategoryFragment extends Fragment {

    RecyclerView rv_categrymain;
    Toolbar toolbar;
    View view;
    Fragment fragment;
    CustomAdaptorCategory categoryAdapter;
  //  String searchkey;
    EditText edt_search;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    ArrayList<com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response> categorylist = new ArrayList<com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response>();
  //  ArrayList<com.shrinkcom.alsaadhomeapp.pojo.categorylist_pojo.Response> categorylist ;
    CategoryListPojo categoryModel;
    List<com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response> responseArrayList = new ArrayList<>();

    LinearLayout nodata_cat;
    String catname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_category, container, false);
        Home.currentHome=-1;
        rv_categrymain = (RecyclerView) view.findViewById(R.id.rv_categrymain);
        nodata_cat =view.findViewById(R.id.nodata_cat);
        edt_search =view.findViewById(R.id.edt_search);


        //Change 2 to your choice because here 2 is the number of Grid layout Columns in each row.
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());

        rv_categrymain.setLayoutManager(recyclerViewLayoutManager);



        edt_search.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter2(editable.toString());
                Log.v("DIKSHA", "LISTTTT!!!!!!!!!!!!!!!" + editable.toString());

            }

        });




//        edt_search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
//                if (cs.length()>0) {
//                    Log.e("FADADAA",">>>"+cs.length());
//                    filter(cs.toString());
//                }else {
//                    if (categoryModel.getResponse().size() > 0) {
//                       RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
//                        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
//                        rv_categrymain.setLayoutManager(manager);
//                        rv_categrymain.setNestedScrollingEnabled(false);
//                        rv_categrymain.setItemAnimator(new DefaultItemAnimator());
//                        //categorylist.addAll(categoryModel.getResponse());
//
//
//                        rv_categrymain.setLayoutManager(manager);
//                        categoryAdapter = new CustomAdaptorCategory(categoryModel.getResponse(), new RecyclerbuttonClick() {
//
//                            @Override
//                            public void onItemClick(int postion, int type) {
//
//                                if (categoryModel.getResponse().get(postion).getSubcatCount()==0){
//                                    fragment = new ProductFragment();
//                                    Bundle args = new Bundle();
//                                    args.putString("categoryid", categoryModel.getResponse().get(postion).getCategoryId());
//                                    fragment.setArguments(args);
//                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.frame, fragment);
//                                    transaction.addToBackStack(null);
//
//                                    transaction.commit();
//                                }else {
//                                    fragment = new SubCategoryFragment();
//                                    Bundle args = new Bundle();
//                                    args.putString("categoryid", categoryModel.getResponse().get(postion).getCategoryId());
//                                    args.putString("categoryname", categoryModel.getResponse().get(postion).getCategoryName());
//                                    args.putString("categorynameAr", categoryModel.getResponse().get(postion).getArCategoryName());
//                                    fragment.setArguments(args);
//                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.frame, fragment);
//                                    transaction.addToBackStack(null);
//                                    transaction.commit();
//                                }
//
//                            }
//                        }, getContext());
//
//                        rv_categrymain.setAdapter(categoryAdapter);
//                        categoryAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//                //  Toast.makeText(getApplicationContext(),"before text change",Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void afterTextChanged(Editable arg0) {
//                //   Toast.makeText(getApplicationContext(),"after text change",Toast.LENGTH_LONG).show();
//            }
//        });

        // TODO: 2/10/2020 category1



        categoryApi();






        return view;
    }


    private void  categoryApi() {
    /*    final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show();*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  progressDialog.dismiss();
                        Log.e("RESPONSEEEEGAL", ">>" + response);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            categoryModel = gson.fromJson(response, CategoryListPojo.class);
                                nodata_cat.setVisibility(View.GONE);

                                if (categoryModel.getStatus() == 1) {
                                    responseArrayList = categoryModel.getResponse();
                                if (categoryModel.getResponse().size()>0) {


                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext()){


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
                                   /* rv_categrymain.setLayoutManager(mLayoutManager);
                                    rv_categrymain.setNestedScrollingEnabled(false);
                                    rv_categrymain.setItemAnimator(new DefaultItemAnimator());*/
                                   //categorylist.addAll(categoryModel.getResponse());
                                    categoryAdapter=new CustomAdaptorCategory(categoryModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type) {

                                            catname=categoryModel.getResponse().get(postion).getCategoryName();
                                            if (categoryModel.getResponse().get(postion).getSubcatCount()==0){
                                                fragment = new ProductFragment();
                                                Bundle args = new Bundle();
                                                args.putString("categoryid", categoryModel.getResponse().get(postion).getCategoryId());
                                                fragment.setArguments(args);
                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.frame, fragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();
                                            }else {

                                                fragment = new SubCategoryFragment();
                                                Bundle args = new Bundle();
                                                args.putString("categoryid", categoryModel.getResponse().get(postion).getCategoryId());
                                                args.putString("categoryname", categoryModel.getResponse().get(postion).getCategoryName());
                                                args.putString("categorynameAr", categoryModel.getResponse().get(postion).getArCategoryName());
                                                fragment.setArguments(args);
                                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                transaction.replace(R.id.frame, fragment);
                                                transaction.addToBackStack(null);
                                                transaction.commit();
                                            }

                                        }
                                    },getContext());

                                    rv_categrymain.setAdapter(categoryAdapter);

                                } else {
                                    nodata_cat.setVisibility(View.VISIBLE);
                                }
                            }
                        } catch (JsonSyntaxException e) {
                            nodata_cat.setVisibility(View.VISIBLE);
                            Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action","Category");
//                params.put("city", UserSession.getInstance(getContext()).readPrefs(UserSession.City));
                Log.e("SENDVALUE", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);    }


    public static boolean containsIgnoreCase(String src, String what) {
        final int length = what.length();
        if (length == 0)
            return true; // Empty string is contained

        final char firstLo = Character.toLowerCase(what.charAt(0));
        final char firstUp = Character.toUpperCase(what.charAt(0));

        for (int i = src.length() - length; i >= 0; i--) {
            // Quick check before calling the more expensive regionMatches() method:
            final char ch = src.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (src.regionMatches(true, i, what, 0, length))
                return true;
        }

        return false;
    }

    public void filter(String priya)
    {

       final List<com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response> lessonarray = new ArrayList<>();
        nodata_cat.setVisibility(View.GONE);
      //  Log.e("categoryCourseddd", ">>>>>" + userDatumList.size());
        for (int j = 0; j <categoryModel.getResponse().size(); j++) {

            Log.e("courselist", ">>>>>" + categoryModel.getResponse().get(j).getCategoryName());
            Log.e("dfffdfff", ">>>>>" +priya);
            if (containsIgnoreCase(categoryModel.getResponse().get(j).getCategoryName(),priya))
            {
                Log.e("dfffdfff", ">>>>>ifff  "+j +" = " +priya);
                com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response userdatum = new com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response();
                userdatum.setCategoryId(categoryModel.getResponse().get(j).getCategoryId());
                userdatum.setCategoryName(categoryModel.getResponse().get(j).getCategoryName());
                userdatum.setArCategoryName(categoryModel.getResponse().get(j).getArCategoryName());
                userdatum.setImage(categoryModel.getResponse().get(j).getImage());
                userdatum.setBaseUrl(categoryModel.getResponse().get(j).getBaseUrl());
                userdatum.setSubcatCount(categoryModel.getResponse().get(j).getSubcatCount());

                lessonarray.add(userdatum);

// }
            }
        }
        Log.e("lessonarray",">>>"+lessonarray.size());
        if (lessonarray.size()>0) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            rv_categrymain.setLayoutManager(mLayoutManager);
            rv_categrymain.setItemAnimator(new DefaultItemAnimator());
            //categorylist.addAll(categoryModel.getResponse());
            categoryAdapter=new CustomAdaptorCategory(lessonarray,new RecyclerbuttonClick() {

                @Override
                public void onItemClick(int postion, int type) {

                    if (lessonarray.get(postion).getSubcatCount()==0){
                        fragment = new ProductFragment();
                        Bundle args = new Bundle();
                        args.putString("categoryid", lessonarray.get(postion).getCategoryId());
                        fragment.setArguments(args);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }else {
                        fragment = new SubCategoryFragment();
                        Bundle args = new Bundle();
                        args.putString("categoryid", lessonarray.get(postion).getCategoryId());
                        args.putString("categoryname", lessonarray.get(postion).getCategoryName());
                        args.putString("categorynameAr", lessonarray.get(postion).getArCategoryName());
                        fragment.setArguments(args);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            },getContext());

            rv_categrymain.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
          //  Toast.makeText(getActivity(), "Filter success", Toast.LENGTH_SHORT).show();

        }else {
            nodata_cat.setVisibility(View.VISIBLE);
            rv_categrymain.setVisibility(View.GONE);
          //  Toast.makeText(getActivity(),"No DAta found", Toast.LENGTH_SHORT).show();
        }

    }
    void filter2(String text) {
        List<com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response> temp = new ArrayList();
        Log.e("DADAAA", ">>>>" + responseArrayList.size());
        for (com.shrinkcom.alsaadceramicapp.pojo.categorylist_pojo.Response d : responseArrayList) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.getCategoryName().toLowerCase().contains(text.toLowerCase()) || d.getCategoryName().toUpperCase().contains(text.toUpperCase())) {
                temp.add(d);

                Log.v("DIKSHA", "LISTTTT!!  temp  >>" + temp.size());
//                Log.v("DIKSHA","LISTTTT!!"+lessonModelList);
            }
        }
        //update recyclerview
        if (temp.isEmpty()){
            nodata_cat.setVisibility(View.VISIBLE);

        }
        categoryAdapter.updateList(temp);

    }


}
