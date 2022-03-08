package com.shrinkcom.alsaadceramicapp.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shrinkcom.alsaadceramicapp.adaptor.CustomAdpatorSubCat;
import com.shrinkcom.alsaadceramicapp.pojo.SubCategory_pojo.SubCategoryListPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubCategoryFragment extends Fragment {

  View view;


    RecyclerView rv_subcategry;
    Toolbar toolbar;
    Fragment fragment;
    String categoryid,categoryname,categorynamear;
    TextView tvcategry_name;
    CustomAdpatorSubCat subcategoryAdapter;
    private List subcategorylist = new ArrayList<>();
    SubCategoryListPojo subcategoryModel;
    LinearLayout nodata_cat;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sub_category, container, false);


        toolbar = view.findViewById(R.id.toolbar_subcategry);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  getActivity().onBackPressed();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if (count > 0) {
                    fragmentManager.popBackStack();
                  //  fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    Intent intent=new Intent(getActivity(), .class);
//                    startActivity(intent);
                } else {
                    getActivity().onBackPressed();
                }



            }
        });  rv_subcategry = (RecyclerView) view.findViewById(R.id.rv_subcategry);
        nodata_cat =view.findViewById(R.id.nodata_subcat);
        tvcategry_name =view.findViewById(R.id.tvcategry_name);
        categoryid = getArguments().getString("categoryid");
        categoryname = getArguments().getString("categoryname");
        categorynamear = getArguments().getString("categorynameAr");


        if(new SessionManager(getContext()).getLanguage().equals("en")) {
            tvcategry_name.setText(categoryname);
        }else {
            tvcategry_name.setText(categorynamear);

        }

        // TODO: 2/10/2020 category1
        subcategory();

        return view;
    }

    private void  subcategory() {
     /*   final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        progressDialog.show();*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {

                    @Override

                    public void onResponse(String response) {
                    //    progressDialog.dismiss();
                        Log.e("RESPONSEEEEGAL", ">>" + response);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            subcategoryModel = gson.fromJson(response, SubCategoryListPojo.class);
                            nodata_cat.setVisibility(View.GONE);

                            if (subcategoryModel.getStatus() == 1) {
                                if (subcategoryModel.getResponse().size() > 0) {
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
                                    rv_subcategry.setLayoutManager(mLayoutManager);
                                    rv_subcategry.setNestedScrollingEnabled(false);
                                    rv_subcategry.setItemAnimator(new DefaultItemAnimator());
                                    subcategoryAdapter=new CustomAdpatorSubCat(subcategoryModel.getResponse(),new RecyclerbuttonClick() {

                                        @Override
                                        public void onItemClick(int postion, int type)
                                        {

                                            fragment = new ProductFragment();
                                            Bundle args = new Bundle();
                                            args.putString("categoryid", subcategoryModel.getResponse().get(postion).getCategoryId());
                                            args.putString("subcategoryid", subcategoryModel.getResponse().get(postion).getSubCategoryId());
                                            args.putString("categoryname", categoryname);
                                            args.putString("categorynameAr", categorynamear);
                                            fragment.setArguments(args);
                                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                            transaction.replace(R.id.frame, fragment);
                                            transaction.addToBackStack(null);
                                            transaction.commit();

                                        }
                                    },getContext());

                                    rv_subcategry.setAdapter(subcategoryAdapter);

                                } else {
                                    nodata_cat.setVisibility(View.VISIBLE);
                                }
                            } else {
                                nodata_cat.setVisibility(View.VISIBLE);
                            }
                        } catch (JsonSyntaxException e) {
                            nodata_cat.setVisibility(View.VISIBLE);

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                  //      progressDialog.dismiss();
                        Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action","SubCategory");
                params.put("category_id",""+ categoryid);
                Log.e("SENDVALUE", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);      }


}
