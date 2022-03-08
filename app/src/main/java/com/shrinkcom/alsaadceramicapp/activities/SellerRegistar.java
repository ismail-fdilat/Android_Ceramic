package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.pojo.citypojo.Citymodel;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Seller;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;


public class SellerRegistar extends AppCompatActivity  {

    TextView tvSellerRegister;
    EditText edtName,edtEmail,edtMobile,edtShopName, edtLocation,edtpassword,edtConfiremPassword,edt_bank,edt_account_number,edt_ifsc;

    SearchableSpinner spinner_city;
    Spinner  spinner_available;
    Citymodel citymodel;
    List<com.shrinkcom.alsaadceramicapp.pojo.citypojo.Response> responses = new ArrayList<>();
    private ArrayList<String> arrayList = new ArrayList<>();
    private ArrayList<String> arrayListid = new ArrayList<>();
    String city_id ="" , country_id="" , state_id="" ,country_id_temp="", state="", state_id_temp="", city_id_tep="",city_name;
    Context context;

    String[] listAges = {"Select Availabilty","Yes","No"};
    String availability,position1;
    TextView tv_seller_date,tv_dayadd;
    TextView chhosefile;
    private static final int PICK_FROM_GALLERY = 1;

    ImageView profileimage;
    byte[] inputData;
    String updatetime ="";

    String strname ;
    String stremail ;
    String strphone ;
    String strshopName ;
    String strLocation;
    String strpassword ;
    String strconpassword;
    String IFSC,bankname,accountnumber,date,adddate;
    String  random="";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registar);


        edtName = findViewById(R.id.edt_username);
        edtMobile = findViewById(R.id.edt_mobileno);
        edtEmail = findViewById(R.id.edt_email);
        edtShopName = findViewById(R.id.edt_shop_name);
        edtLocation = findViewById(R.id.edt_location);
        edtpassword = findViewById(R.id.edt_pass);
        edtConfiremPassword = findViewById(R.id.edt_cpass);
        tvSellerRegister = findViewById(R.id.tv_signup);
        spinner_city = findViewById(R.id.spinner_city);
        spinner_available = findViewById(R.id.spinner_available);
        tv_seller_date = findViewById(R.id.tv_seller_date);
        tv_dayadd = findViewById(R.id.tv_dayadd);
        chhosefile = findViewById(R.id.chhosefile);
        profileimage = findViewById(R.id.profile);
        edt_bank = findViewById(R.id.edt_bank);
        edt_account_number = findViewById(R.id.edt_account_number);
        edt_ifsc = findViewById(R.id.edt_ifsc);

        context  = this;

        GetCity();
//        RandomString data = new RandomString();

         random = getAlphaNumericString(10);
       Log.d("randomnumber",random);




        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listAges);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner_available.getBackground().setColorFilter(ContextCompat.getColor(this, R.color.grey), PorterDuff.Mode.SRC_ATOP);
        spinner_available.setAdapter(dataAdapter);
        spinner_available.setSelection(0);
        spinner_available.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                availability = parent.getItemAtPosition(position).toString();
                position1 = String.valueOf(parent.getItemAtPosition(position));


                if(position > 0){
                    // get spinner value
                    Toast.makeText(parent.getContext(), "Availabilty" + availability, Toast.LENGTH_SHORT).show();
                }else{
                    // show toast select gender
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        chhosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if ((ActivityCompat.checkSelfPermission(context
                            , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(SellerRegistar.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
                    } else {
                        onPictureSelector(v.getContext());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });





        tv_seller_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar myCalendar = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd MMMM yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        tv_seller_date.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(context, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
            });


        tv_dayadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar myCalendar1 = Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar1.set(Calendar.YEAR, year);
                        myCalendar1.set(Calendar.MONTH, monthOfYear);
                        myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "dd MMMM yyyy"; // your format
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        tv_dayadd.setText(sdf.format(myCalendar1.getTime()));
                    }

                };
                new DatePickerDialog(context, date, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();

            }
        });





        tvSellerRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!validation()){
                    return;
                }

                callForUpdateProfile();

//                String strname = edtName.getText().toString();
//                String stremail = edtEmail.getText().toString();
//                String strphone = edtMobile.getText().toString();
//                String strshopName = edtShopName.getText().toString();
//                String strLocation = edtLocation.getText().toString();
//                String strpassword = edtpassword.getText().toString();
//
//                Map<String,String> params = new HashMap<>();
//                params.put("action","selleregis");
//                params.put("sname",strname);
//                params.put("semail",stremail);
//                params.put("phone",strphone);
//                params.put("shop_name",strshopName);
//                params.put("city",strLocation);
//                params.put("pass",strpassword);
//
//
//                postData(params);
            }
        });

    }

    boolean validation(){
        boolean valid = true;
        strname = edtName.getText().toString();
        stremail = edtEmail.getText().toString();
         strphone = edtMobile.getText().toString();
         strshopName = edtShopName.getText().toString();
         strLocation = edtLocation.getText().toString();
         strpassword = edtpassword.getText().toString();
         strconpassword = edtConfiremPassword.getText().toString();
         bankname = edt_bank.getText().toString();
         accountnumber =edt_account_number.getText().toString();
         IFSC = edt_ifsc.getText().toString();
         date = tv_seller_date.getText().toString();
         adddate = tv_dayadd.getText().toString();

        if (strname.isEmpty()){
            edtName.setError("Enter Name");
            valid = false;
        }else {
            edtName.setError(null);
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(stremail).matches()){
            edtEmail.setError("Enter Email");
            valid = false;
        }else {
            edtEmail.setError(null);
        }
        if (strphone.length()<10){
            edtMobile.setError("Enter Phone Number");
            valid = false;
        }else {
            edtMobile.setError(null);
        }

        if (strshopName.isEmpty()){
            edtShopName.setError("Enter Shop Name");
            valid = false;
        }else {
            edtShopName.setError(null);
        }

        if (bankname.isEmpty()){
            edt_bank.setError("Enter bank Name");
            valid = false;
        }else {
            edt_bank.setError(null);
        }

        if (IFSC.isEmpty()){
            edt_ifsc.setError("Enter IFSC Code");
            valid = false;
        }else {
            edt_ifsc.setError(null);
        }

        if (accountnumber.isEmpty()){
            edt_account_number.setError("Enter Account number");
            valid = false;
        }else {
            edt_account_number.setError(null);
        }


        if (date.equals("")){
            tv_seller_date.setError("Enter seller date");
            valid = false;
        }else {
            tv_seller_date.setError(null);
        }

       

        if (city_id_tep.equals("")){
            Toast.makeText(context, "Please select city", Toast.LENGTH_SHORT).show();
            valid = false;
        }else {
        }

        if (availability.equals("Select Availabilty")){
            Toast.makeText(context, "Please select availability", Toast.LENGTH_SHORT).show();
            valid = false;
        }else {
        }


        if (strLocation.isEmpty()){
            edtLocation.setError("Enter Location");
            valid = false;
        }else {
            edtLocation.setError(null);
        }

        if (strpassword.isEmpty()){
            edtpassword.setError("Enter Password");
            valid = false;
        }else {
            edtpassword.setError(null);
        }

        if (!strpassword.equals(strconpassword)){
            edtConfiremPassword.setError("Password Not match");
            valid = false;
        }else {
            edtConfiremPassword.setError(null);
        }

        return valid;
    }


    void postData(final Map<String,String> params){
        final ProgressDialog progressDialog = new ProgressDialog(SellerRegistar.this);
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
                                    //Token();
                                    Intent intent =  new Intent(SellerRegistar.this, LoginActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(SellerRegistar.this, jo.getString("message"), Toast.LENGTH_SHORT).show();
                                }

                            }
                        } catch (Exception e) {
                            Utilitynew.UserAlert(SellerRegistar.this,(getString(R.string.tv_internet)));

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(SellerRegistar.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {


               // HashMap<String, String> params = new HashMap<>();

                Log.e("msg===========", "submitSubmission:param " + params);
                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(SellerRegistar.this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    private void   GetCity()  {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("DIKSHA", "GET_COUNTRIES_RESPONSE>>" + response);

                        try {

                            if (true) {


                                Log.v("DIKSHA", "LOGIN_RESPONSE" + response);

                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
//                                progressDialog.dismiss();

                                Citymodel pojo = gson.fromJson(response, Citymodel.class);
                                responses = pojo.getResponse();

                                if (pojo.getStatus() == 1) {

                                    if (pojo.getResponse().size() > 0) {

                                        citymodel = gson.fromJson(response, Citymodel.class);
                                        arrayList.clear();
                                        arrayListid.clear();
                                        arrayList.add("Enter City");
                                        arrayListid.add("");
                                        // APIClientLogin();

                                        for (int i=0 ; i<responses.size();i++){
                                            arrayList.add(citymodel.getResponse().get(i).getCityName());
                                            arrayListid.add(citymodel.getResponse().get(i).getId());
                                        }

                                        setDataToAdapter(arrayList);

                                        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                city_id_tep= arrayListid.get(i);
//                                                city_name = arrayList.get(i);
//                                                city_name= citymodel.getResponse().get(i).getCityName();
//                                                Log.d("cityid",city_name);


                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });



                                    } else {

                                    }


                                    //   home_recycler_view.setLayoutManager(mLayoutManager);

                                } else {

                                }

                            } else {
                            }

                        } catch (Exception e) {
                            //  Utilitynew.UserAlert(Register.this,(getString(R.string.tv_internet)));

                            Log.v("DIKSHAAAA", "ERROR>>>>" + e);

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("DIKSHAAAA", "ERROR>>>>" + error.getMessage());
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();
                params.put("action","city_list");
                params.put("city_name",city_id);


                return params;

            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    private void setDataToAdapter(ArrayList<String> arrayList)
    {
        // Creating ArrayAdapter using the string array and default spinner layout
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayList)

        {
            public View getView(int position , View convertView, ViewGroup parent)
            {
                View view1 = super.getView(position,convertView,parent);
                ((TextView)view1).setTextSize(14);
                ((TextView)view1).setTextColor(getColor(R.color.black));


                return view1;
            }
        };
        // Specify layout to be used when list of choices appears
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Applying the adapter to our spinner
        spinner_city.setAdapter(arrayAdapter);


//        for (int i = 0; i <citymodel.getResponse().size(); i++) {
//            Log.e("cityCountryselection",">> "+arrayList.get(i)+" "+citySelected);
//            if (citymodel.getResponse().get(i).getId().equals(citySelected)){
//                spinner_city.setSelection(i);
//                break;
//            }
//        }


    }

    private void onPictureSelector(Context context) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.custom_dialog_layout);

        Button layout_camera =  dialog.findViewById(R.id.btn_camera);
        layout_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectCamera();
                dialog.dismiss();
            }
        });

        Button layout_gallery =  dialog.findViewById(R.id.btn_gallery);
        layout_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectGallery();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void onSelectCamera() {

        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 200);

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//                Log.d("photoFile", photoFile.toString());
//            } catch (IOException ex) {
//                ex.printStackTrace();
//                // Error occurred while creating the File
//            }
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(getContext(),
//                        "com.shrinkcom.alsaadhomeapp" + ".provider",
//                        photoFile);
//                mPhotoFile = photoFile;
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//            }
//        }

    }

    private void onSelectGallery() {
//        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(intent,  500);//

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            onCaptureImageResult(data);
/*
            Uri selectedImage = data.getData();

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            selectedBitmap = photo;
            Log.d("TAG", "onActivityResultselectedImage: "+photo+"selectedImage"+selectedImage);
            imageProfile.setImageBitmap(photo);
            isPictureSelected = true;
            Glide.with(context).load(selectedBitmap).error(R.drawable.user_man).into(imageProfile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            inputData  = byteArrayOutputStream .toByteArray();

            userPicture = Base64.encodeToString(inputData, Base64.DEFAULT);
            selectedImage = getImageUri(context,photo);

            Uri uri = selectedImage;
            String uriString = selectedImage.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                       // upload_1.setText(displayName);

                        displayNameA = displayName;






                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }

            InputStream iStream = null;

            try {

                iStream = getActivity().getContentResolver().openInputStream(uri);
                inputData = getBytes(iStream);
                finalInputData = inputData;

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Anks", Toast.LENGTH_SHORT).show();
            }
*/


        }
        if (requestCode == 500 && resultCode == RESULT_OK) {
            onSelectFromGalleryResult(data);
/*
            Uri selectedImage = data.getData();
            Log.d("TAG", "onActivityResultselectedImage: "+selectedImage);
            imageProfile.setImageURI(selectedImage);

            try {
                selectedBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Glide.with(context).load(selectedBitmap).error(R.drawable.user_man).into(imageProfile);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            selectedBitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
            inputData = byteArrayOutputStream .toByteArray();

            userPicture = Base64.encodeToString(inputData, Base64.DEFAULT);

            isPictureSelected = true;


            Uri uri = selectedImage;
            String uriString = selectedImage.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                        Log.d("nameeeee>>>>  ",displayName);

                        // upload_1.setText(displayName);

                        displayNameA = displayName;



                    }
                } finally {
                    Toast.makeText(context, "FInally", Toast.LENGTH_SHORT).show();
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                displayName = myFile.getName();
                Log.d("nameeeee>>>>  ",displayName);
            }

            InputStream iStream = null;

            try {

                iStream = getActivity().getContentResolver().openInputStream(uri);
                inputData = getBytes(iStream);
                finalInputData = inputData;

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
*/


        }


    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

        profileimage.setImageBitmap(thumbnail);
        Glide.with(context).load(thumbnail).error(R.drawable.ic_user).into(profileimage);
        inputData = getBytes(thumbnail);

    }
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                Glide.with(context).load(bm).error(R.drawable.ic_user).into(profileimage);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        profileimage.setImageBitmap(bm);
        Glide.with(context).load(bm).error(R.drawable.ic_user).into(profileimage);
        inputData = getBytes(bm);

    }
    public static byte[]  getBytes(Bitmap bitmap){

        byte[] byteArray = new byte[0];
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
            byteArray = stream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "ERORRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR: ");

        }

        // bitmap.recycle();
        return byteArray;
    }

    private void callForUpdateProfile() {
//        Toast.makeText(context, "uploadimage", Toast.LENGTH_SHORT).show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.e("Nurse_REGISTER_RESPONSE", "Nurse_REGISTER_RESPONSE" + resultResponse);
                try {
                    JSONObject jsonObject = new JSONObject(resultResponse);
                    try {
                        JSONObject jsonObjectRepo = new JSONObject(resultResponse.toString());

                        String msg = jsonObjectRepo.getString("message");
                        int status = jsonObjectRepo.getInt("status");
                        if (status == 1) {
                            Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                            JSONObject data = new JSONObject(resultResponse);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                            String image = jsonObject1.getString("simg");
                            Log.d("imageup", image);
                            UserSession.getInstance(SellerRegistar.this).writePrefs(UserSession.PROFILEIMAGE, image);

//
                        } else {
                            msg = jsonObjectRepo.getString("message");
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        }

                        // Retrieve user information and take user to next activity
                        // after successful login
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v("DIKSHAAA", "ERROR>>" + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                Log.e("upload image", "---->" + error);

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("action", "selleregis");
                params.put("email", stremail);
                params.put("sname", strname);
                params.put("semail", stremail);
                params.put("phone", strphone);
                params.put("shop_name", strshopName);
                params.put("city", city_id_tep);
                params.put("pass", strpassword);
                params.put("seller_date", date);
                params.put("seller_uid", random);
//                params.put("day_add","");
                params.put("bank_name", bankname);
                params.put("location", strLocation);
                params.put("account_number", accountnumber);
                params.put("availability ", availability);
                params.put("ifsc_swift", IFSC);
                Log.e("DIKSHAsendvalues", "REQUEST>>" + params);
                return params;

            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();


                Log.d("TAG", "getByteData: " + inputData);
                if (inputData != null) {
                    //  params.put("profile_pic", ""+edit_email.getText().toString());
                    // params.put("profile_pic", new VolleyMultipartRequest.DataPart( "Aff"+"file_a.jpg" , inputData, ".png/jpeg"));
                    params.put("seller_img", new VolleyMultipartRequest.DataPart(updatetime + "Aff" + "file_a.jpeg", inputData, "image/jpeg"));

                } else {


                }


                Log.e("Nurse_SEND_IMAGE", "SENDING_PARAM>>>" + params);

                return params;
            }
        };

        // Log.v("DIKSHA " , ">>>"+updatetime);

        multipartRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        multipartRequest.setShouldCache(false);
        requestQueue.add(multipartRequest);


      /*  final StringRequest getRequest = new StringRequest(Request.Method.POST, UPDATEPROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        gv_geometric.setVisibility(View.GONE);
                        Log.e("RESPONSEEEELogin", ">>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            try {
                                JSONObject jsonObjectRepo = new JSONObject(response.toString());

                                String msg =   jsonObjectRepo.getString("message");
                                gv_geometric.setVisibility(View.GONE);
                                Boolean status    =  jsonObjectRepo.getBoolean("status");
                                if (status){
                                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                                    JSONObject data = new JSONObject();
                                    data =   jsonObjectRepo.getJSONObject("data");
                                    String firstname =   data.getString("firstname");
                                    String lastname =   data.getString("lastname");
                                    String email =   data.getString("email");
                                    String mobile =   data.getString("mobile");
                                    String municipalid =   data.getString("municipalid");
                                    String userid =   data.getString("id");
                                    String password =   data.getString("password");
                                    String postal_code =   data.getString("postal_code");


                                    sessionManager.saveString(SessionManager.FIRSTNAME, String.valueOf(firstname));
                                    sessionManager.saveString(SessionManager.LASTNAME, String.valueOf(lastname));
                                    sessionManager.saveString(SessionManager.USERID, String.valueOf(userid));
                                    sessionManager.saveString(SessionManager.EMAIL, String.valueOf(email));
                                    sessionManager.saveString(SessionManager.MOBILE, String.valueOf(mobile));
                                    sessionManager.saveString(SessionManager.MUNICIPALID, String.valueOf(municipalid));
                                    sessionManager.saveString(SessionManager.POSTALCODE, String.valueOf(postal_code));

                                    Intent intent = new Intent(context, DashBoardActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }else {
                                    msg =   jsonObjectRepo.getString("message");
                                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();
                                }


                                // Retrieve user information and take user to next activity
                                // after successful login
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v("DIKSHAAA", "ERROR>>" + e);
                        }
                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        gv_geometric.setVisibility(View.GONE);
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("TAG", "onErrorResponse: " + error.getMessage());
                        // Toast.makeText(getApplicationContext(),(getString(R.string.checkconnection)), Toast.LENGTH_LONG).show();

                    }

                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();


                params.put("id", ""+sessionManager.getStoreString(SessionManager.USERID));
                params.put("firstname", ""+edit_fName.getText().toString());
                params.put("lastname", ""+edit_LastName.getText().toString());
                params.put("email", ""+edit_phone.getText().toString());
                params.put("mobile", ""+edit_email.getText().toString());
                params.put("profile_pic", ""+edit_email.getText().toString());

                Log.e("SENDVALUELogin", ">>" + params);
                return params;


            }
        };

        RequestQueue queue = Volley.newRequestQueue((context));
        queue.add(getRequest);*/

    }

        public static String getAlphaNumericString(int n) {

            // length is bounded by 256 Character
            byte[] array = new byte[256];
            new Random().nextBytes(array);

            String randomString
                    = new String(array, Charset.forName("UTF-8"));

            // Create a StringBuffer to store the result
            StringBuffer r = new StringBuffer();

            // remove all spacial char
            String  AlphaNumericString
                    = randomString
                    .replaceAll("[^A-Za-z0-9]", "");

            // Append first 20 alphanumeric characters
            // from the generated random String into the result
            for (int k = 0; k < AlphaNumericString.length(); k++) {

                if (Character.isLetter(AlphaNumericString.charAt(k))
                        && (n > 0)
                        || Character.isDigit(AlphaNumericString.charAt(k))
                        && (n > 0)) {

                    r.append(AlphaNumericString.charAt(k));
                    n--;
                }
            }

            // return the resultant string
            return r.toString();
        }
    }




