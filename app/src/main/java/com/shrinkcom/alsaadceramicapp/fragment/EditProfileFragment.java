package com.shrinkcom.alsaadceramicapp.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.VolleyMultipartRequest;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;

import com.shrinkcom.alsaadceramicapp.pojo.getPojoClass.GetProfilePojo;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.PermissionChecker;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MultipartBody;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;


public class EditProfileFragment extends Fragment {
    EditText edtfullname, edtmobile;
    String Fullname, MobileNo, Email, FULLNAME, MOBILENO, IMAGE, Userid;
    TextView tvUpdate, edtemail, tv_logout;
    CircleImageView profileimage;
    ImageView imv_addpficon;
    String updatetime;


    byte[] byteArray;
    private String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    Handler handler;

    MultipartBody.Part body;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_GALLERY_PHOTO = 2;
    File mPhotoFile;
    //    MultipartBody.Part body;
    Context context;
    private static final int PICK_FROM_GALLERY = 1;

    byte[] inputData;
    File profileImageFile, filePath;



    PermissionChecker permissionChecker = new PermissionChecker();
    String[] RequiredPermissions = {Manifest.permission.CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        //  checkPermissions();
        Toolbar toolbar = view.findViewById(R.id.toolbar_mypfedit);
        context = getContext();
        handler = new Handler();
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
        edtfullname = view.findViewById(R.id.edt_fulname);
        edtmobile = view.findViewById(R.id.edt_mobile);
        edtemail = view.findViewById(R.id.edt_email);
        tvUpdate = view.findViewById(R.id.tv_update);
        profileimage = view.findViewById(R.id.imv_addpf);
        tv_logout = view.findViewById(R.id.tv_logout);
        imv_addpficon = view.findViewById(R.id.imv_addpficon);

        Userid = UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID);


        Email = UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_EMAIL);
        FULLNAME = UserSession.getInstance(getActivity()).readPrefs(UserSession.PROFILEFULLNAME);
        MOBILENO = UserSession.getInstance(getActivity()).readPrefs(UserSession.MOBILENO);
        IMAGE = UserSession.getInstance(getActivity()).readPrefs(UserSession.PROFILEIMAGE);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_userpf);
        Glide.with(profileimage.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(ApiService.PROFILE_IMAGE + IMAGE)
                .into(profileimage);


        Log.e("email", ">>>>>>" + Email);

        getProfile();

        imv_addpficon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if ((ActivityCompat.checkSelfPermission(getContext()
                            , Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PICK_FROM_GALLERY);
                    } else {
                        onPictureSelector(v.getContext());

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


/*

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.icon_editpf);
        Log.v("SAKSHI", "image >>" + IMAGE);

        Glide.with(profileimage.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(ApiService.PROFILE_IMAGE + IMAGE)
                .into(profileimage);
*/


        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitynew.logoutUser(getContext());
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                edtemail.setText(Email);
                edtfullname.setText(FULLNAME);
                edtmobile.setText(MOBILENO);
            }
        });


        tvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditProfile();


            }
        });

   /*     imv_addpficon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });*/
        // getProfile();

        return view;
    }

    private void checkPermissions() {
        permissionChecker.verifyPermissions(getActivity(), RequiredPermissions, new PermissionChecker.VerifyPermissionsCallback() {
            @Override
            public void onPermissionAllGranted() {

            }


            @Override
            public void onPermissionDeny(String[] permissions) {
                Utilitynew.UserAlert(getContext(), getString(R.string.permite_permisssion));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //________________VALIDATION _______________________________________________

    private boolean validate() {
        Fullname = edtfullname.getText().toString();
        MobileNo = edtmobile.getText().toString();


        if (Fullname.isEmpty()) {
            edtfullname.setError("Please enter fullname");
            edtfullname.requestFocus();
        } else if (MobileNo.isEmpty()) {
            edtmobile.setError("Please enter Mobile No.");
            edtmobile.requestFocus();
        }


        return true;
    }

    //_______________________________IMAGE PIC CODE___________________________

    private void selectImage() {
        final CharSequence[] items = {getString(R.string.take_photo), getString(R.string.choose_from_libary),
                getString(R.string.cancel)};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.add_photo));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utilitynew.checkPermission(getActivity());

                if (items[item].equals(getString(R.string.take_photo))) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals(getString(R.string.choose_from_libary))) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals(getString(R.string.cancel))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
//        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
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

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == REQUEST_TAKE_PHOTO) {
//                callForImageupload();
////             try {
////                 mPhotoFile = mCompressor.compressToFile(mPhotoFile);
////             } catch (IOException e) {
////                 e.printStackTrace();
////             }
//                Glide.with(getContext()).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.ic_user)).into(profileimage);
//            } else if (requestCode == REQUEST_GALLERY_PHOTO) {
//                Uri selectedImage = data.getData();
//                mPhotoFile = (new File(getRealPathFromUri(selectedImage)));
//                callForImageupload();
//                Glide.with(getContext()).load(mPhotoFile).apply(new RequestOptions().centerCrop().circleCrop().placeholder(R.drawable.ic_user)).into(profileimage);
//
//            }
//        }
//    }

    File destination;




    //____________________________________API EDIT PROFILE_________________________

    private void EditProfile() {
       final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("EditprofileResponse", ">>" + response);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("status").equals("1")) {

                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");

                                String Message = jsonObject1.optString("message");

                                UserSession.getInstance(getActivity()).writePrefs(UserSession.PROFILEIMAGE, jsonObject1.getString("avatar"));
                                UserSession.getInstance(getActivity()).writePrefs(UserSession.PROFILEFULLNAME, jsonObject1.getString("user_fname"));
                                UserSession.getInstance(getActivity()).writePrefs(UserSession.MOBILENO, jsonObject1.getString("mobile"));
                                UserSession.getInstance(getActivity()).writePrefs(UserSession.PREFS_EMAIL, jsonObject1.getString("email"));

                                 getProfile();

                                Toast.makeText(getActivity(), "Profile  Updated Successfully!!", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

                            }
                        }
                        catch (JSONException e) {
                            Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                            e.printStackTrace();
                            Log.e("msg======", "RESPONSE: " + e);
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();

                params.put("action","UpdateProfile");
                params.put("email",edtemail.getText().toString().trim());
                params.put("phone",edtmobile.getText().toString().trim());
                params.put("userfname",edtfullname.getText().toString().trim());
                params.put("userlname","");

                Log.e("parameditprofile", params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    //______________________________API UPDATE PROFILE IMAGE_______________________________

/*    private void editImageProfile() {
       final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                progressDialog.dismiss();
                String resultResponse = new String(response.data);
                Log.e("ResponseImageProfile", "--->" + resultResponse);
                try {


                    JSONObject jsonObject = new JSONObject(resultResponse);

                    if (jsonObject.getString("status").equals("1")) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("response");


                        String image_url = ApiService.PROFILE_IMAGE + jsonObject1.optString("avatar");

                        Log.e("image",""+image_url);


                        Glide.with(getContext()).load(image_url).into(profileimage);
                        Log.v("DIKSHA", "PROFILE URL :" + profileimage);
                        UserSession.getInstance(getContext()).writePrefs(UserSession.PROFILEIMAGE, jsonObject1.optString("avatar"));


                    }

                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              progressDialog.dismiss();
                error.printStackTrace();
                Log.e("ERRORRRRR", "---->" + error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("action","UpdateProfileImage");
                params.put("email",Email);

                Log.e("ImageParamsProfile", params.toString());
                return params;
            }
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                params.put("image", new VolleyMultipartRequest.DataPart(updatetime + "file_a.jpg", byteArray, "avtar/jpeg"));

                Log.e("USERIMAGE", "===>" + byteArray);

                return params;
            }
        };

        VolleySingleton.getInstance(getActivity()).addToRequestQueue(multipartRequest);
    }*/

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


    private void getProfile() {

      /*  final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();
*/
        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("Responseprofile", ">>>" + response);
              //  pDialog.hide();
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                GetProfilePojo getProfilePojo = gson.fromJson(response, GetProfilePojo.class);
                MOBILENO =  getProfilePojo.getResponse().getMobile();
                Fullname =  getProfilePojo.getResponse().getUserFname();
                IMAGE =  getProfilePojo.getResponse().getAvatar();
                Email =  getProfilePojo.getResponse().getEmail();


                edtfullname.setText(Fullname);
                edtmobile.setText(MOBILENO);
                edtemail.setText(Email);

               /* RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.ic_userpf);

                Glide.with(profileimage.getContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(ApiService.PROFILE_IMAGE + IMAGE)
                        .into(profileimage);*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
               // pDialog.hide();
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

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String mFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mFile = File.createTempFile(mFileName, ".jpg", storageDir);
        Log.d("mFile", mFile.toString());
        return mFile;
    }
    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
    private void callForUpdateProfile() {
        Toast.makeText(context, "uploadimage", Toast.LENGTH_SHORT).show();
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                Log.e("Nurse_REGISTER_RESPONSE", "Nurse_REGISTER_RESPONSE" + resultResponse);
                try {
                    JSONObject jsonObject = new JSONObject(resultResponse);
                    try {
                        JSONObject jsonObjectRepo = new JSONObject(resultResponse.toString());

                        String msg =   jsonObjectRepo.getString("message");
                        int status    =  jsonObjectRepo.getInt("status");
                        if (status==1){
                            Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
                            JSONObject data = new JSONObject(resultResponse);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                            String image = jsonObject1.getString("avatar");
                            Log.d("imageup",image);
                            UserSession.getInstance(getActivity()).writePrefs(UserSession.PROFILEIMAGE, image);

//
                        }else {
                            msg =   jsonObjectRepo.getString("message");
                            Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show();
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
                params.put("action", "UpdateProfileImage");
                params.put("email", UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_EMAIL));
                Log.e("DIKSHA", "REQUEST>>" + params);
                return params;

            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                Log.d("TAG", "getByteData: "+inputData);
                if(inputData!=null){
                    //  params.put("profile_pic", ""+edit_email.getText().toString());
                    // params.put("profile_pic", new VolleyMultipartRequest.DataPart( "Aff"+"file_a.jpg" , inputData, ".png/jpeg"));
                    params.put("image", new VolleyMultipartRequest.DataPart( updatetime+"Aff"+"file_a.jpeg" , inputData, "image/jpeg"));

                }else {


                }


                Log.e("Nurse_SEND_IMAGE" , "SENDING_PARAM>>>"+params);

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

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }
    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, bytes);

        profileimage.setImageBitmap(thumbnail);
        Glide.with(context).load(thumbnail).error(R.drawable.ic_user).into(profileimage);
        inputData = getBytes(thumbnail);
        callForUpdateProfile();
    }
    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
                Glide.with(context).load(bm).error(R.drawable.ic_user).into(profileimage);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        profileimage.setImageBitmap(bm);
        Glide.with(context).load(bm).error(R.drawable.ic_user).into(profileimage);
        inputData = getBytes(bm);
        callForUpdateProfile();

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

//    private void callForImageupload() {
//            final ProgressDialog progressDialog = new ProgressDialog(getContext());
//            progressDialog.setCancelable(true);
//            progressDialog.show();
//            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            Log.d("progress", "progress");
//            Retrofit retrofit = RetrofitFactory.getRetrofit();
//            ApiUpload apiService = retrofit.create(ApiUpload.class);
//
//            if (mPhotoFile != null) {
//                profileImageFile = new File(String.valueOf(mPhotoFile));
//                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), profileImageFile);
//                // MultipartBody.Part is used to send also the actual file name
//                Log.d("imagepathh", "callForImageupload: " + profileImageFile.getName());
//                body = MultipartBody.Part.createFormData("image", profileImageFile.getName(), requestFile);
//
//            } else {
//                RequestBody attachmentEmpty = RequestBody.create(MediaType.parse("text/plain"), "");
//
//                body = MultipartBody.Part.createFormData("image", "", attachmentEmpty);
//
//            }
//
//
//            RequestBody action = RequestBody.create(MediaType.parse("text/plain"), "UpdateProfileImage");
//            RequestBody agentname = RequestBody.create(MediaType.parse("text/plain"), "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_EMAIL));
//
//            Call<ProfileuploadModel> req = apiService.postcontact(body, action, agentname);
//            req.enqueue(new Callback<ProfileuploadModel>() {
//                public void onResponse(Call<ProfileuploadModel> call, retrofit2.Response<ProfileuploadModel> response) {
//                    progressDialog.dismiss();
//
////                    Log.d("response----->", response.body().toString());
//
//                    ProfileuploadModel profileUploadModel = new ProfileuploadModel();
//                    profileUploadModel = response.body();
////                    Gson gsonObj = new Gson();
////                     profileUploadModel = gsonObj.fromJson(response.toString(), Editprofilemodel.class);
//
//                    if (profileUploadModel.getStatus().equals("1")) {
//                        Log.d("response>>", response.toString());
//                        Toast.makeText(context, "" + profileUploadModel.getMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//
//
//                    }else {
//                        progressDialog.dismiss();
//
//                        Toast.makeText(context, "" + profileUploadModel.getMessage(), Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ProfileuploadModel> call, Throwable t) {
//                    progressDialog.dismiss();
//                    Log.d("tresquest", t.getMessage());
//                    Toast.makeText(getContext(), "Request failed", Toast.LENGTH_SHORT).show();
//                    t.printStackTrace();
//                }
//
//
//            });
////             Log.e("sasas", "o<<<<<>>>>>" + files_bytes.size());
//
//        }
    }



