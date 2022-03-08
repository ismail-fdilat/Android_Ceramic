package com.shrinkcom.alsaadceramicapp.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.ConfirmationActivity;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.LoginActivity;
import com.shrinkcom.alsaadceramicapp.activities.NewCheckoutPage;
import com.shrinkcom.alsaadceramicapp.activities.SelectLoginActvity;
import com.shrinkcom.alsaadceramicapp.adaptor.CartAdapter;

import com.shrinkcom.alsaadceramicapp.pojo.ProductModel;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.shrinkcom.alsaadceramicapp.utils.UserSession.AMOUNT;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PREFS_USERID;


public class CartFragment extends Fragment {

    static TextView textViewtotal;
    View view;
    Toolbar toolbar;
    TextView tvaddmorewieshlist;
    static TextView tv_item1;
    static TextView tvcartempty;
    TextView tv_checkout,tv_continueshopp;
    static RecyclerView rvcart;
   static List<ProductModel> cartlist;
    static double finaltotal = 0.0;
    static Mycart mycart;
    static Context mContext;
   static CartAdapter adaptor;
    static double totalmain;
    static JSONArray jsonArray ;
    static String pprice;
    static String pquantity;
    static String pid;
    static String psizee;
    static String size_name;
    static String sellerid;
    long mLastClickTime = 0;
    static  String qty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (UserSession.getInstance(getActivity()).readPrefs(PREFS_USERID).equals("")){

            Utilitynew.UserAlert(getContext(), "Please login first to add product in cart");

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);

        }else {
            mycart = new Mycart(getActivity());
            mycart.open();
            data(getContext());
        }



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_cart, container, false);
        mContext = getActivity();
        Home.currentHome=-1;
        toolbar = view.findViewById(R.id.toolbar_cart);
        rvcart = view.findViewById(R.id.rv_cart);
        tv_item1 = view.findViewById(R.id.tv_item1);
        tvcartempty = view.findViewById(R.id.idcartmessage);
        textViewtotal = view.findViewById(R.id.tv_subtotAL);
        tv_checkout = view.findViewById(R.id.tv_checkout);
        tv_continueshopp = view.findViewById(R.id.tv_continueshopp);


        tv_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (cartlist.size()==0){

                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    mLastClickTime = SystemClock.elapsedRealtime();
                    alertforcart();
                }else {
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        return;
                    }
                    else {
//                        Log.d("tryrtryryr",qty);
                        mLastClickTime = SystemClock.elapsedRealtime();
                        showAlertDialog();
                    }
                }


            }
        });

//
//        tv_checkout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String amount = String.valueOf(finaltotal);
//                UserSession.getInstance(mContext).writePrefs(AMOUNT,amount);
//                if(cartlist.size()==0){
//                    Toast.makeText(getContext(), R.string.additem, Toast.LENGTH_SHORT).show();
//
//                }
//                else{
//
//                if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {
//
//                    Intent intent=new Intent(getActivity(), SelectLoginActvity.class);
//                    startActivity(intent);
//                    // TODO: 3/12/2020  create new activity
//
//                } else {
//                    Intent intent=new Intent(getActivity(), NewCheckoutPage.class);
//                    startActivity(intent);
//
//                }
//
//            }}
//        });
        tv_continueshopp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Home.class);
                startActivity(intent);
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  fragment = new HomeFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                removeFragFromBackStack();
                transaction.commit();
            }
        });


        // TODO: 2/19/2020 cart items

        detCartAdaptor(getActivity());


        updatecartTotal();

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


  public static void detCartAdaptor(final Context mContext){
       cartlist = new ArrayList<>();
       cartlist = Utilitynew.getAllProduct(mContext);
       Log.e("LISTTTT",">>>"+cartlist.size());

       // TODO: 3/14/2019  setAdaptor
       if (cartlist.size() > 0) {
           rvcart.setVisibility(View.VISIBLE);
           tvcartempty.setVisibility(View.GONE);
           LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true);
           rvcart.setLayoutManager(layoutManager);
           rvcart.setItemAnimator(new DefaultItemAnimator());
           adaptor = new CartAdapter(cartlist,mContext, new RecyclerbuttonClick() {
               @Override
               public void onItemClick(int position, int type) {
                   qty = ""+cartlist.get(position).getPquantity();


               }
           });
           rvcart.setAdapter(adaptor);
           adaptor.notifyDataSetChanged();

           tv_item1.setText(mContext.getResources().getString(R.string.itemscount) + " (" + cartlist.size() + ")");
       } else
       {
           tv_item1.setText(mContext.getResources().getString(R.string.subzero));
         rvcart.setVisibility(View.GONE);
           tvcartempty.setVisibility(View.VISIBLE);
       }

   }


    public static void updatecartTotal() {

        finaltotal = 0.0;
        mycart = new Mycart(mContext);

        List<ProductModel> listdaa = new ArrayList<>();
        listdaa = Utilitynew.getAllProduct(mContext);


        for (int i = 0; i < listdaa.size(); i++) {
             pprice = listdaa.get(i).getPprice();
             pquantity = listdaa.get(i).getPquantity();
             pid = listdaa.get(i).getPid();
             size_name = listdaa.get(i).getSize_name();
             psizee = listdaa.get(i).getSku();
             sellerid = listdaa.get(i).getSellerid();
             try
             {
                 double ddprice = Double.parseDouble(pprice);
                 int ddqusy = Integer.parseInt(pquantity);

                 finaltotal = finaltotal + ddprice * ddqusy;

             }catch (Exception e)
             {
                 e.printStackTrace();
             }



        }

        textViewtotal.setText(finaltotal + ""+ApiService.CURRENCYCODE);
//        jsonArray = new JSONArray();
//        JSONObject object = new JSONObject();
//        try {
//            object.put("product_id",pid);
//            object.put("qty",pquantity);
//            object.put("price",pprice);
//            object.put("totalprice",String.valueOf(finaltotal));
//            object.put("size_id",psizee);
//            object.put("size",size_name);
//            object.put("seller_id",sellerid);
//
//            jsonArray.put(object);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

    }

    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle("Please Choose ONE Of The Options Below To Checkout");
        String[] items = {"Pick Up","Ship"};
        int checkedItem = 0;
        alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                        String amount1 = String.valueOf(finaltotal);
                        UserSession.getInstance(mContext).writePrefs(AMOUNT,amount1);
                        if(cartlist.size()==0){
                            Toast.makeText(getContext(), R.string.additem, Toast.LENGTH_SHORT).show();
                        } if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        // TODO: 3/12/2020  create new activity
                    } else {
                        dialog.dismiss();
                        Intent intent = new Intent(getActivity(), NewCheckoutPage.class);
                        intent.putExtra("deliverystatus","1");
                        startActivity(intent);

                    }
                        break;
                    case 1:
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();
                        String amount = String.valueOf(finaltotal);
                        UserSession.getInstance(mContext).writePrefs(AMOUNT,amount);
                        if(cartlist.size()==0){
                            Toast.makeText(getContext(), R.string.additem, Toast.LENGTH_SHORT).show();
                        }
                        else {

                            if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                dialog.dismiss();

                                // TODO: 3/12/2020  create new activity
                            } else {
                                Intent intent = new Intent(getActivity(), NewCheckoutPage.class);
                                intent.putExtra("deliverystatus","2");
                                startActivity(intent);
                                dialog.dismiss();


                            }
                        }
                        break;

                }
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    private void ConfirmBook(final String delivery_status) {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        Log.e("msg======", "getSubmitapi: ");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.e("msg======", "getSubmitapi: "+response);
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getInt("status") == 1) {


                                Intent i = new Intent(getContext(),
                                        ConfirmationActivity.class);
                                startActivity(i);

                                String userid;
                                if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")){
                                    userid = UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERDEVICEID);
                                    Log.e("ranuser",""+UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERDEVICEID));
                                }else {
                                    userid = UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID);
                                }
                                Mycart mycartdb = new Mycart(getContext());
                                mycartdb.open();
                                mycartdb.deletealldata(userid);
                                mycartdb.close();
                            } else {
                                Toast.makeText(getContext(), ">>>>>>>>>>", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {



                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Utilitynew.UserAlert(getContext(),(getString(R.string.tv_internet)));
                    }

                }) {
            @Override
            protected Map<String, String> getParams() {

                HashMap<String, String> params = new HashMap<>();

                if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")){
                    params.put("user_id", "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERDEVICEID));
                }else {
                    params.put("user_id", "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID));
                }

                params.put("action", "BookOrder");

                params.put("transaction_id", "" );
                params.put("last_name", "");
                params.put("phone", "" +"");
                params.put("email", "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_EMAIL));
                params.put("state", "" );
                params.put("city", "" + "");
                params.put("address", "" + "");
                params.put("postal_code", "");
                params.put("payment_method", "COD");
                params.put("delivery_status", ""+delivery_status);
                params.put("product_data", "" + jsonArray);
                params.put("first_name", "" +  UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_EMAIL));
                params.put("country", "");
                params.put("total", ""+ String.valueOf(finaltotal));
                params.put("whatsapp_no","");


                Log.e("paramsAddPayment", ">>>>" + params.toString());

                return params;
            }

        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }
    private void data(Context activity){

        String userid;
        if (UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID).equals("")){
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERDEVICEID);
        }else {
            userid = UserSession.getInstance(activity).readPrefs(UserSession.PREFS_USERID);
        }

        Mycart mycart = new Mycart(getContext());
        mycart.open();
        Cursor cursor = mycart.getAllDatashopping(userid);
        jsonArray = new JSONArray();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String pid = cursor.getString(1);
                String pname = cursor.getString(2);
                String pimage = cursor.getString(3);
                String pprice = cursor.getString(4);
                String pquantity = cursor.getString(5);
                String pdescription = cursor.getString(6);

                String psizee=cursor.getString(8);
                String size_name=cursor.getString(9);
                String sellerid=cursor.getString(11);
                String city_id=cursor.getString(cursor.getColumnIndex(Mycart.city));

                Log.e("DIZE_NAMEE",">>"+psizee);
                Log.e("DIZE_NAMEE",">>"+size_name);


                Log.e("PRODIDDD",">>"+pid);
                JSONObject object = new JSONObject();
                try {
                    object.put("product_id",pid);
                    object.put("qty",pquantity);
                    object.put("price",pprice);
                    object.put("totalprice",pprice);
                    object.put("size_id",psizee);
                    object.put("size",size_name);
                    object.put("seller_id",sellerid);

                    jsonArray.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        Log.e("JSONARRAAAA",">>"+jsonArray);
    }
    public void alertforcart(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setMessage("Please add item in card for checkout");
        builder1.setCancelable(true);


        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getContext(),Home.class);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}



