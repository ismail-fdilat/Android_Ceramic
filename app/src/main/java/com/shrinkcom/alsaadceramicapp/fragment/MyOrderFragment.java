package com.shrinkcom.alsaadceramicapp.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.adaptor.OrderHistoryAdpator;
import com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo.OrderHistoryPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyOrderFragment extends Fragment {

    View v;
    Toolbar toolbar;
    OrderHistoryPojo orderHistoryPojo;
    LinearLayout nodata_myorder;
    RecyclerView rv_myorder;
    TextView no_order;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my_order, container, false);

        toolbar = v.findViewById(R.id.toolbar_myorder);
        nodata_myorder = v.findViewById(R.id.nodata_myorder);
        no_order = v.findViewById(R.id.no_order);
        rv_myorder = v.findViewById(R.id.rv_myorder);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        if (UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID).equals("")){
            Toast.makeText(getContext(), "Please Login first", Toast.LENGTH_SHORT).show();

        }else {
            orderHlist();
        }





        return v;
    }



    private void orderHlist() {
       /* final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();*/
        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //     progressDialog.dismiss();
                Log.e("GET_HISTORY", ">>>" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    orderHistoryPojo = gson.fromJson(response, OrderHistoryPojo.class);
                    nodata_myorder.setVisibility(View.GONE);

                    if (orderHistoryPojo.getStatus() == 1) {

                        if (orderHistoryPojo.getResponse().size() > 0) {

                            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1)
                            {
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

                            rv_myorder.setLayoutManager(mLayoutManager);
                            rv_myorder.setItemAnimator(new DefaultItemAnimator());

                            OrderHistoryAdpator adaptor = new OrderHistoryAdpator(orderHistoryPojo.getResponse(), new RecyclerbuttonClick() {
                                @Override
                                public void onItemClick(int position, int type) {
                                    Fragment fragment = new TrackOrderFragmnet();
                                    Bundle args = new Bundle();
                                    args.putString("orderid", orderHistoryPojo.getResponse().get(position).getBookedId());
                                    args.putString("delivery_status",orderHistoryPojo.getResponse().get(position).getDelivery_status());
                                    args.putString("shippingaddress", orderHistoryPojo.getResponse().get(position).getStreetadd1());
                                    args.putString("shippingarea", orderHistoryPojo.getResponse().get(position).getShipping_area());
                                    args.putString("shippingcity", orderHistoryPojo.getResponse().get(position).getShipping_city());
                                    args.putString("payment_method", orderHistoryPojo.getResponse().get(position).getPaymentMethod());
                                    args.putString("order_review", orderHistoryPojo.getResponse().get(position).getOrderedProducts().get(0).getOrderid());
                                    args.putString("shippingstatus", orderHistoryPojo.getResponse().get(position).getConfirmStatus());
                                    Log.e("shipstatus",""+orderHistoryPojo.getResponse().get(position).getShippingStatus());
                                    Log.e("delivery_statusprachi",""+orderHistoryPojo.getResponse().get(position).getDelivery_status());
                                    args.putParcelableArrayList("listorderproduct", (ArrayList<? extends Parcelable>) orderHistoryPojo.getResponse().get(position).getOrderedProducts());


                                    fragment.setArguments(args);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frame, fragment);
                                    transaction.addToBackStack(null);

                                    transaction.commit();
                                }
                            }, getContext());
                            rv_myorder.setAdapter(adaptor);
                        } else {
//                            nodata_myorder.setVisibility(View.VISIBLE);

                        }

                    }else {
                        nodata_myorder.setVisibility(View.VISIBLE);
                    }
                } catch (JsonSyntaxException e) {



                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.dismiss();
                nodata_myorder.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "OrderHistory");
                if (UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID));
                }
                Log.e("paramsAdd", ">>>>" + params.toString());

                return params;

            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringreuest);
    }


}
