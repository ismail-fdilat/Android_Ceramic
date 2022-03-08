package com.shrinkcom.alsaadceramicapp.fragment;

import android.Manifest;
import android.app.ProgressDialog;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.shrinkcom.alsaadceramicapp.utils.PermissionChecker;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ContactUsFragment extends Fragment {


    View view;
    Toolbar toolbar;

    EditText edt_namecont,edt_emailcont,edt_phonecont,edt_comentcont;
    String name,email,phone,comment;
    TextView tv_send,tv_email_cont,tv_phone_cont;
    PermissionChecker permissionChecker = new PermissionChecker();
    String[] RequiredPermissions = {Manifest.permission.CALL_PHONE};

    public ContactUsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_contact_us, container, false);
        checkPermissions();
        toolbar = view.findViewById(R.id.toolbar_contactus);
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
        initviews();
        getcontactusApi();
        return view;

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

    private void checkPermissions() {
        permissionChecker.verifyPermissions(getActivity(), RequiredPermissions, new PermissionChecker.VerifyPermissionsCallback() {
            @Override
            public void onPermissionAllGranted() {

            }


            @Override
            public void onPermissionDeny(String[] permissions) {
                Utilitynew.UserAlert(getContext(),getString(R.string.permite_permisssion));
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    private void initviews() {
        edt_namecont=view.findViewById(R.id.edt_namecont);
        edt_emailcont=view.findViewById(R.id.edt_emailcont);
        edt_phonecont=view.findViewById(R.id.edt_phonecont);
        edt_comentcont=view.findViewById(R.id.edt_comentcont);
        tv_send=view.findViewById(R.id.tv_send);
        tv_email_cont=view.findViewById(R.id.tv_email_cont);
        tv_phone_cont=view.findViewById(R.id.tv_phone_cont);

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validation();
            }
        });



    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    void validation() {
        name = edt_namecont.getText().toString().trim();
        email = edt_emailcont.getText().toString().trim();
        comment = edt_comentcont.getText().toString().trim();
        phone = edt_phonecont.getText().toString().trim();

        if (name.isEmpty()) {

            edt_namecont.setError("Username is required");
            edt_namecont.requestFocus();

        } else {
            if (email.isEmpty()) {
                edt_emailcont.setError("Email is required");
                edt_emailcont.requestFocus();
            } else {
                if (phone.isEmpty()) {
                    edt_phonecont.setError("Number is required");
                    edt_phonecont.requestFocus();

                } else {
                    if (phone.length() <9) {
                        edt_phonecont.setError("Please Enter valid number with country code");
                        edt_phonecont.requestFocus();
                    } else {
                        if (comment.isEmpty()) {
                            edt_comentcont.setError("Please enter comment!");
                            edt_comentcont.requestFocus();

                        } else {
                            if (!emailValidator(email)) {
                                edt_emailcont.setError("Please Enter Valid email");
                                edt_emailcont.requestFocus();
                            } else {
                                AddcontactusApi();
                            }

                        }


                    }


                }
            }


        }
    }



    private void AddcontactusApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                JSONObject jo=new JSONObject(response);
                                if (jo.getInt("status")==1)
                                {
                                    startActivity(new Intent(getContext(), Home.class));

                                }
                                else
                                {
                                    Toast.makeText(getContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override


            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "Contact_Us");
                params.put("name", name);
                params.put("comments", comment);
                params.put("email", email);
                params.put("contact", phone);
                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void getcontactusApi() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();

                                Log.e("msg========", "getTokenApi reponse check  :  " + response);

                                JSONObject jo=new JSONObject(response);
                                if (jo.getInt("status")==1) {
                                    JSONArray contacts = jo.getJSONArray("response");
                                    for (int i = 0; i < contacts.length(); i++) {
                                        JSONObject c = contacts.getJSONObject(i);
                                        String email = c.getString("email");
                                        String phone = c.getString("phone");

                                        tv_email_cont.setText(email);
                                        tv_phone_cont.setText(phone);


                                    }
                                }
                                else {
                                    Toast.makeText(getContext(), jo.getString("message"), Toast.LENGTH_SHORT).show();
                                  }

                            }
                        } catch (Exception e) {

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override


            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "contactdetail");
                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

}
