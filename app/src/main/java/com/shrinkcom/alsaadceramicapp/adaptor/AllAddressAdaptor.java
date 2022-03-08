package com.shrinkcom.alsaadceramicapp.adaptor;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.shrinkcom.alsaadceramicapp.R;

import com.shrinkcom.alsaadceramicapp.activities.EditCheckoutActvty;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.pojo.GetShipModel.com.shrinkcom.alsaadhomeapp.Response;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllAddressAdaptor extends RecyclerView.Adapter<AllAddressAdaptor.viewHolder> {

    List<Response> addresslist;
    RadioButton selected=null;
    private int selectedPosition = -1;
    Context context;
    public AllAddressAdaptor(List<Response> addresslist, Context context) {
        this.addresslist = addresslist;
        this.context= context;
    }


    @Override
    public  AllAddressAdaptor.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_alladdress, viewGroup, false);
        return new AllAddressAdaptor.viewHolder(view);
    }
    @Override
    public  void onBindViewHolder(final AllAddressAdaptor.viewHolder viewHolder, final int position) {

        final Response data=addresslist.get(position);
        Log.e("CHECKLANGUAGE", ">>" + new SessionManager(context).getLanguage());
        //viewHolder.categoryname.setText(data.getCategoryName());

        viewHolder.deliveryaddress_list.setText(data.getAddress());
            viewHolder.idsdeliverycity_list.setText(data.getCity());
            viewHolder.idsname_list.setText(data.getUsername());
            viewHolder.idsphone_list.setText(data.getPhone());


        viewHolder.tv_edit_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditCheckoutActvty.class);
                intent.putExtra("addressid", data.getAddressId());
                intent.putExtra("name", data.getUsername());
                intent.putExtra("phone", data.getPhone());
                intent.putExtra("email", data.getEmail());
                intent.putExtra("address", data.getAddress());
                intent.putExtra("city", data.getCity());
                intent.putExtra("addressid", data.getAddressId());

                context.startActivity(intent);
            }
        });

        viewHolder.tv_remove_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removedataship(data.getAddressId());
                addresslist.remove(position);
                notifyDataSetChanged();
            }
        });



/*

      viewHolder.cb_selectadd.setChecked(position == selectedPosition);
        viewHolder.cb_selectadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mycart mycart1 = new Mycart(context);
                mycart1.open();
                if (position == selectedPosition) {
                    viewHolder.cb_selectadd.setChecked(false);

                    selectedPosition = -1;
                } else {
                    int avllable=0;
                    if (UserSession.getInstance(context).readPrefs(PREFS_USERID).equals("")){
                        avllable = mycart1.checkAvailableuser( UserSession.getInstance(context).readPrefs(PREFS_USERDEVICEID));
                    }else {
                        avllable = mycart1.checkAvailableuser( UserSession.getInstance(context).readPrefs(PREFS_USERID));
                    }
                    if (avllable==1) {
                        selectedPosition = position;
                        notifyDataSetChanged();
                        Intent intent = new Intent(context, OrderSummary.class);
                        intent.putExtra("addressid", data.getAddressId());
                        intent.putExtra("phone", data.getPhone());
                        intent.putExtra("email", data.getEmail());
                        intent.putExtra("address", data.getAddress());
                        intent.putExtra("city", data.getCity());
                        intent.putExtra("zipcode", data.getZipcode());
                        intent.putExtra("country", data.getCountry());
                        intent.putExtra("name", data.getUsername());
                        context.startActivity(intent);
                    }
                    mycart1.close();
                }
            }


        });

*/

  

    }

    @Override
    public int getItemCount() {
        return addresslist.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView deliveryaddress_list,idsdeliverycity_list,idsdeliveryzipcode_list,idsname_list,idsphone_list;
        LinearLayout ll_cat;
        TextView tv_remove_list,tv_edit_list;
        CheckBox cb_selectadd;
        RadioGroup rg_selectadd;

        public viewHolder(View itemView) {
            super(itemView);

            deliveryaddress_list=itemView.findViewById(R.id.deliveryaddress_list);
            idsdeliverycity_list=itemView.findViewById(R.id.idsdeliverycity_list);
            idsname_list=itemView.findViewById(R.id.idsname_list);
            idsphone_list=itemView.findViewById(R.id.idsphone_list);
            tv_remove_list=itemView.findViewById(R.id.tv_remove_list);
            tv_edit_list=itemView.findViewById(R.id.tv_edit_list);
            cb_selectadd=itemView.findViewById(R.id.cb_selectadd);
        //    rg_selectadd=itemView.findViewById(R.id.rg_selectadd);


        }
    }

    private void removedataship(final String addressid) {
        final ProgressDialog pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                Log.e("RemoveWishlist", ">>>" + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("status") == 1) {

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "RemoveShippingAddress");
                params.put("address_id", "" + addressid);
                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }


}
