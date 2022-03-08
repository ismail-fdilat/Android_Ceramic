package com.shrinkcom.alsaadceramicapp.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.adaptor.MyNotificatnAdaptor;
import com.shrinkcom.alsaadceramicapp.pojo.notify_pojo.Notificationpojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {

    View view;
    RecyclerView recyclerVnotify;
    Context context;
    private MyNotificatnAdaptor adapter;
   private List<Notificationpojo> notifyModels;
    private LinearLayout nodata_notify;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_notification, container, false);
        toolbar = view.findViewById(R.id.toolbar_notify);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        recyclerVnotify=view.findViewById(R.id.rv_notify);
        nodata_notify=view.findViewById(R.id.nodata_notify);
        context = getActivity();
        notifyModels = new ArrayList<>();

        if (UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID).equals("")){
            Toast.makeText(getContext(), "Please Login first", Toast.LENGTH_SHORT).show();

        }else {
            Allnotifylist();
        }

        return view;
    }

    private void Allnotifylist() {

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

                        //  Toast.makeText(RegistrationActivity.this, "" + response, Toast.LENGTH_LONG).show();
                        try {
                        /*    if (progressDialog != null) {
                                progressDialog.dismiss();*/

                            Log.e("msg========", "getTokenApi reponse check  :  " + response);

                            GsonBuilder gsonBuilder = new GsonBuilder();
                            Gson gson = gsonBuilder.create();


                            Notificationpojo notifyPojo = gson.fromJson(response, Notificationpojo.class);


                            if (notifyPojo.getResult() == 1) {
                                if (notifyPojo.getUserData().size() > 0) {
                                    nodata_notify.setVisibility(View.GONE);

                                    for (int i = 0; i < notifyPojo.getUserData().size(); i++) {

                                        notifyModels.add(notifyPojo);
                                    }

                                    adapter = new MyNotificatnAdaptor(context, notifyModels);
                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity()){
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
                                    recyclerVnotify.setLayoutManager(mLayoutManager);
                                    recyclerVnotify.setItemAnimator(new DefaultItemAnimator());
                                    recyclerVnotify.setAdapter(adapter);
                                    // Toast.makeText(getActivity(), transactionPojo.getMessage(), Toast.LENGTH_SHORT).show();


                                } else {
                                    nodata_notify.setVisibility(View.VISIBLE);
                                }
                            } else {
                                nodata_notify.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception e) {
                            Utilitynew.UserAlert(getActivity(), (getString(R.string.tv_internet)));

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
              params.put("action", "get_notification");
                params.put("userid", ""+ UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID));

                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
}
