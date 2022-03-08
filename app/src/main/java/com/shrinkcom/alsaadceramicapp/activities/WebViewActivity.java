package com.shrinkcom.alsaadceramicapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import com.shrinkcom.alsaadceramicapp.R;

import com.shrinkcom.alsaadceramicapp.pojo.getinvoicepojo.Getinvoicemodel;
import com.shrinkcom.alsaadceramicapp.pojo.orderhistorypojo.OrderHistoryPojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.PermissionChecker;
import com.shrinkcom.alsaadceramicapp.utils.ProgressDialog;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.http.Url;

public class WebViewActivity extends AppCompatActivity {
    private static final int PICK_FROM_GALLERY = 1;
    String URL;
    ImageView icon_download;
    Toolbar toolbar_myorder;
    public static final int STORAGE_PERMISSION_REQUEST_CODE= 1;
    PermissionChecker permissionChecker = new PermissionChecker();
    android.app.ProgressDialog pDialog;
    String orderid;
    com.shrinkcom.alsaadceramicapp.pojo.getinvoicepojo.Getinvoicemodel Getinvoicemodel;
    List<com.shrinkcom.alsaadceramicapp.pojo.getinvoicepojo.Response> responseList = new ArrayList<>();
    WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
//        URL = intent.getStringExtra("URl");
        orderid = intent.getStringExtra("orderid");
//        Log.d("ttttt",URL);

        askPermissions();

        getinvoice();



        icon_download = findViewById(R.id.icon_download);
        toolbar_myorder = findViewById(R.id.toolbar_myorder);

        toolbar_myorder.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




       icon_download.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               downloadUpdate(getApplicationContext(),URL);
           }
       });




    }



    private void askPermissions() {

        try {
            if ((ActivityCompat.checkSelfPermission(this
                    , android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
            } else {
                //onPictureSelector(v.getContext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void downloadUpdate(final Context context, String apkurl) {

        progressDialog();

        try {
            final File apk_file_path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), SystemClock.currentThreadTimeMillis()+"_"+getResources().getString(R.string.app_name)+".pdf");
            if (apk_file_path.exists()) apk_file_path.delete();

            //  Log.v(TAG,"Downloading request on url :"+"https://doktoroman.com/HelloeatsRestaurent28.apk");
            Log.v("mmmsjsuhuygu", "Downloading request on url :" + apkurl);
            //  DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkurl));
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkurl));
            request.setDescription("versionUpdate");
            request.setTitle(context.getString(R.string.app_name));
            //set destination
            final Uri uri = Uri.parse("file://" + apk_file_path);
            request.setDestinationUri(uri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            final long downloadId = manager.enqueue(request);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean downloading = true;
                    while (downloading) {
                        DownloadManager.Query q = new DownloadManager.Query();
                        q.setFilterById(downloadId);
                        Cursor cursor = manager.query(q);
                        cursor.moveToFirst();

                        final int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                        if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                            downloading = false;
                            pDialog.dismiss();
                        }
                        int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                        if (bytes_total != 0) {
                            final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
                            WebViewActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //  downloadAppProgess.setProgress((int) dl_progress);
                                    Log.e("PROGRESSSSSS", ">>> " + dl_progress);
                                    int progress_bar_type = dl_progress;
                                    pDialog.setProgress(dl_progress);
                                }
                            });
                        }
                        cursor.close();

                    }
                }
            }).start();

            //set BroadcastReceiver to install app when .apk is downloaded
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    //BroadcastReceiver on Complete
                    Log.e("FILEPATHHH", ">>>apkUri " + apk_file_path);

                    if (apk_file_path.exists()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Uri apkUri = FileProvider.getUriForFile(WebViewActivity.this, getApplicationContext().getPackageName() + ".provider", apk_file_path);
                            Log.e("FILEPATHHH", ">>>apkUri " + apk_file_path);
                            // Intent intentinstall = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                            Intent intentinstall = new Intent(Intent.ACTION_VIEW);
                            intentinstall.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intentinstall.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intentinstall.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                            intentinstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intentinstall.setData(apkUri);
                            context.startActivity(intentinstall);

                            context.unregisterReceiver(this);

                        } else {
                            Uri apkUri = Uri.fromFile(apk_file_path);
                            Log.e("FILEPATHHH", ">>>apkUrielse " + apkUri);
                            Intent intentinstall = new Intent(Intent.ACTION_VIEW);
                            intentinstall.setDataAndType(apkUri, manager.getMimeTypeForDownloadedFile(downloadId));
                            intentinstall.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                            intentinstall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intentinstall);
                            context.unregisterReceiver(this);
                        }

                    } else {
                        Log.e("FILEPATHHH", ">>>apkUrielse not " + apk_file_path);

                        // dismissDialog(progress_bar_type);

                        //AppController.setAlert(context, "Something went wrong");
                        // downloadAppProgess.setVisibility(View.GONE);
                        //update_now.setVisibility(View.VISIBLE);
                    }


                }
            };

            context.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("EXCEPTOPNDATAA", ">>>" + e);
            pDialog.dismiss();


//            dismissDialog(progress_bar_type);

            // downloadAppProgess.setVisibility(View.GONE);
            // update_now.setVisibility(View.VISIBLE);
        }

    }


    void progressDialog() {

        pDialog = new android.app.ProgressDialog(WebViewActivity.this);
        pDialog.setMessage("Loading");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setCancelable(false);
        pDialog.show();

    }


    private void getinvoice() {
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
                    Getinvoicemodel = gson.fromJson(response, Getinvoicemodel.class);

                    responseList = Getinvoicemodel.getResponse();
                    Log.d("listtttt", String.valueOf(responseList.size()));


                    if (Getinvoicemodel.getStatus() == 1) {

                        if (responseList.size() > 0) {
                            URL  = responseList.get(0).getInvoice();
                            Log.d("urlllllll",URL);

                            WebView webview = findViewById(R.id.webview);
                            WebSettings webSettings = webview.getSettings();
                            webview.setWebViewClient(new myWebViewClient());
                            webSettings.setJavaScriptEnabled(true);
                            webview.loadUrl("https://docs.google.com/gview?embedded=true&url=" +  URL);
                            webview.setWebChromeClient(new WebChromeClient() {
                                public void onProgressChanged(WebView view, int progress)
                                {

                                    Log.d("urlssss",""+progress);
                                    //Make the bar disappear after URL is loaded, and changes string to Loading...
                                    //Make the bar disappear after URL is loaded

                                    // Return the app name after finish loading
                                    if(progress == 100);

                                }
                            });




                        }


                    }else {
                    }
                } catch (JsonSyntaxException e) {



                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();

                Toast.makeText(WebViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("action", "orderinvoice");
                params.put("booking_id",orderid);

                Log.e("paramsAdd", ">>>>" + params.toString());

                return params;

            }
        };

        VolleySingleton.getInstance(WebViewActivity.this).addToRequestQueue(stringreuest);
    }


    public class myWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            Log.d("urlprint",url);
            return true;
        }
    }


}