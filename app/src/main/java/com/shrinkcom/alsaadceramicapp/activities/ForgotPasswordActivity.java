package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edt_emailforgotpass;
    TextView tv_nextforgotpass;
    String stremail;
    ImageView imv_backfgpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initview();
    }

    private void initview() {
        edt_emailforgotpass=findViewById(R.id.edt_emailforgotpass);
        tv_nextforgotpass=findViewById(R.id.tv_nextforgotpass);
        imv_backfgpass=findViewById(R.id.imv_backfgpass);

        imv_backfgpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        tv_nextforgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validation()) {
                    return;
                }
                stremail = edt_emailforgotpass.getText().toString().trim();
                sendEmailLink();

            }
        });


    }


    boolean validation() {
        boolean valid = true;
      stremail = edt_emailforgotpass.getText().toString().trim();

        if (TextUtils.isEmpty(stremail) && !Patterns.EMAIL_ADDRESS.matcher(stremail).matches()) {
            edt_emailforgotpass.setError("Enter Valid Email");
            edt_emailforgotpass.requestFocus();
            valid = false;
        } else {
            edt_emailforgotpass.setError(null);
        }
        return valid;
    }

    private void sendEmailLink() {

        final ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
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
                                JSONObject obj = new JSONObject(response);
                                int resss = obj.getInt("status");
                                if (resss == 1) {
                                    progressDialog.dismiss();
                                    showDialogMenu1();

                                } else {

                                    progressDialog.dismiss();
                                    showDialogMenu2();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Utilitynew.UserAlert(ForgotPasswordActivity.this,(getString(R.string.tv_internet)));

                        }

                    }
                },

                new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(ForgotPasswordActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action", "VerifyEmail");
                params.put("email", stremail);


                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(ForgotPasswordActivity.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    private void showDialogMenu1 () {

        AlertDialog.Builder builder = new AlertDialog.Builder(
                ForgotPasswordActivity.this);
     builder.setTitle(getString(R.string.title_otpst));
        builder.setPositiveButton(getString(R.string.title_otpst2),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent intent = new Intent(ForgotPasswordActivity.this, OTPActvity.class);

                        intent.putExtra("emailaddress", stremail);
                        finish();
                        startActivity(intent);
                    }
                });
        builder.show();


    }
    private void showDialogMenu2 () {

        final AlertDialog.Builder builder = new AlertDialog.Builder(
                ForgotPasswordActivity.this);
        builder.setTitle(getString(R.string.title_otpst1));

        builder.setPositiveButton(getString(R.string.title_otpst2),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {

                    }
                });
        builder.show();

    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

}
