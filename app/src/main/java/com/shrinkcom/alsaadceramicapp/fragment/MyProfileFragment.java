package com.shrinkcom.alsaadceramicapp.fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;

import com.shrinkcom.alsaadceramicapp.pojo.getPojoClass.GetProfilePojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyProfileFragment extends Fragment {
    ImageView edit_profile;
    String Userid,mobileno,Fullname,Email,Image;
    TextView tvFname,tvMobile,tvEmail,tv_logout;
    CircleImageView iv_image;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
      Toolbar toolbar = view.findViewById(R.id.toolbar_mypf);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  fragment = new AccountFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                removeFragFromBackStack();
                transaction.commit();
            }
        });
        edit_profile = view.findViewById(R.id.edit_profile);

        tvFname = view.findViewById(R.id.tv_fulname);
        tvMobile = view.findViewById(R.id.tv_mobile);
        tvEmail = view.findViewById(R.id.tv_email);
        iv_image = view.findViewById(R.id.iv_image);
        tv_logout = view.findViewById(R.id.tv_logout);

        getProfile();
        Userid = UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID);
        Email = UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_EMAIL);


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  fragment = new EditProfileFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitynew.logoutUser(getContext());
            }
        });

        return view;
    }

    //____________________________API MY PROFILE___________________________
    private void getProfile() {
/*
        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("Responseprofile", ">>>" + response);
               // pDialog.hide();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                GetProfilePojo  getProfilePojo = gson.fromJson(response, GetProfilePojo.class);
                mobileno =  getProfilePojo.getResponse().getMobile();
                Fullname =  getProfilePojo.getResponse().getUserFname();
                Image =  getProfilePojo.getResponse().getAvatar();

                Log.e("mobileno",">>>>"+mobileno);
                Log.e("fname",">>>>"+Fullname);

                tvFname.setText(Fullname);
                tvMobile.setText(mobileno);
                tvEmail.setText(Email);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.ic_userpf);
                Log.v("SAKSHI", "image >>" + Image);

                Glide.with(iv_image.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(ApiService.PROFILE_IMAGE + Image)
                        .into(iv_image);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
              //  pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("action","Profile");
                params.put("userId",Userid);
                Log.e("param profile", params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringreuest);
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
}
