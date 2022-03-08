package com.shrinkcom.alsaadceramicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.text.TextUtils.isEmpty;

public class ChangePasswordFragment extends Fragment {
    TextView tv_submitchpass;
    EditText edt_oldpassword,edt_chngpass,edt_Conpass;
    Toolbar toolbar;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_change_password, container, false);

          toolbar = view.findViewById(R.id.tool);
        tv_submitchpass = view.findViewById(R.id.tv_submitchpass);
        edt_oldpassword = view.findViewById(R.id.edt_oldpassword);
        edt_chngpass = view.findViewById(R.id.edt_chngpass);
        edt_Conpass = view.findViewById(R.id.edt_Conpass);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AccountFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                removeFragFromBackStack();
                transaction.commit();
            }
        });


        tv_submitchpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  oldpassword = edt_oldpassword.getText().toString().trim();
                String strnewpass = edt_chngpass.getText().toString().trim();
                String strnconfirmpass = edt_Conpass.getText().toString().trim();

                if (strnewpass.isEmpty()){
                    Toast.makeText(getContext(), R.string.title_passwordisrequired, Toast.LENGTH_SHORT).show();
                }else if(strnconfirmpass.isEmpty()){
                    Toast.makeText(getContext(), R.string.enter_your_confirm_password, Toast.LENGTH_SHORT).show();
                }else if (!strnewpass.equals(strnconfirmpass)){
                    Toast.makeText(getContext(), R.string.password_not_match, Toast.LENGTH_SHORT).show();
                }else if (oldpassword.isEmpty()){
                    Toast.makeText(getContext(), R.string.oldreq, Toast.LENGTH_SHORT).show();
                }else {
                    SetPassword();

                }



            }
        });

        return  view;



    }


    boolean validation() {
        boolean valid = true;
        String strnewpass = edt_chngpass.getText().toString().trim();
        String strnconfirmpass = edt_Conpass.getText().toString().trim();
        String  oldpassword = edt_oldpassword.getText().toString().trim();


        if (strnewpass.isEmpty()) {
            edt_chngpass.setError(getResources().getString(R.string.enternewpassword));
            valid = false;
        } else {
            edt_chngpass.setError(null);
        }

        if (oldpassword.isEmpty()) {
            edt_oldpassword.setError(getResources().getString(R.string.enteroldpassword));
            valid = false;
        } else {
            edt_oldpassword.setError(null);
        }

        if(isEmpty(strnconfirmpass))
        {
            if (!strnconfirmpass.equals(strnewpass))
            {
                edt_Conpass.setError(getResources().getString(R.string.enter_your_confirm_password));
                valid = false;
            }
            else{
                edt_Conpass.setError(getResources().getString(R.string.password_not_match));
            }
        }else {
            edt_Conpass.setError(null);
        }
        return valid;
    }

    private void SetPassword() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("RESPONSEEEEGAL", ">>" + response);
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            if (obj.getInt("status")==1){
                                Intent intent=new Intent(getContext(), Home.class);
                                startActivity(intent);
                                Toast.makeText(getContext(), R.string.password_success_update, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Utilitynew.UserAlert(getContext(),(getString(R.string.tv_internet)));

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action","changePassword");
                params.put("email", UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_EMAIL));
                params.put("new_password",edt_chngpass.getText().toString());
                params.put("old_password",""+edt_oldpassword.getText().toString());
                Log.e("SENDVALUE", ">>" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }



    public boolean removeFragFromBackStack() {
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
