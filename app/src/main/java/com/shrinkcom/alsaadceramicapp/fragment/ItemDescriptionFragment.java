package com.shrinkcom.alsaadceramicapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Parcelable;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.RecyclerbuttonClick;
import com.shrinkcom.alsaadceramicapp.activities.CalculatorActivity;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.LoginActivity;
import com.shrinkcom.alsaadceramicapp.activities.ReviewViewMoreActivity;
import com.shrinkcom.alsaadceramicapp.activities.SelectLoginActvity;
import com.shrinkcom.alsaadceramicapp.activities.VolleyMultipartRequest;
import com.shrinkcom.alsaadceramicapp.activities.VolleySingleton;
import com.shrinkcom.alsaadceramicapp.adaptor.CartAdapter;
import com.shrinkcom.alsaadceramicapp.adaptor.FloorAdaptor;
import com.shrinkcom.alsaadceramicapp.adaptor.SliderAdapterExample;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Image;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Review;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.SingleProductPojo;
import com.shrinkcom.alsaadceramicapp.pojo.singleproduct_pojo.Size;
import com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.WishlistPojo;
import com.shrinkcom.alsaadceramicapp.storage.Mycart;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.FloorModel;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

import static com.shrinkcom.alsaadceramicapp.utils.ApiService.CURRENCYCODE;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PREFS_USERDEVICEID;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PREFS_USERID;
import static com.shrinkcom.alsaadceramicapp.utils.UserSession.PRODUCTID;


public class ItemDescriptionFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    View v;
    Toolbar toolbar;
    Button btnincrease, btndescrese;
    private static ViewPager mPager;
    private static int currentPage = 0;
   private ArrayList<Integer> XMENArray = new ArrayList<Integer>();

    CircleIndicator indicator;
    TextView tv_city,tv_thesize,tv_thecolor,tv_thickness,tv_namedescp, tv_countryof_manufacture,tv_pricedescrp,tv_productratingtxt,tv_surfaceshapetxt,tv_typeofcuttingtxt,tv_titleDdescription, tv_instock, tv_integer, tv_addcart,tv_itemavailable;
    String st_nameD;
    String st_ARnameD;
    String st_priceD;
    String st_descripD;
    String st_ARdescripD;
    String st_instockD;
    String st_prodid;
    String sizeid;
    String st_rating;
    String st_shap;
    Integer st_typethickness;
    String st_country;
    String st_thickness;
    String st_color;
    String st_size;
    String st_city;
    String product_city;
    private String qty;
    public static long countproductoncart = 0;
    ImageView imv_cart, imv_unfavwish, imv_favwish,imv_share;
    double p2;

    RecyclerView rv_size;
    String selectedsize,selectedsizeid,size_name;
    private int selectedItem = 0;

    SingleProductPojo singleProductPojo;
    Image iumagepojo;
    List<String> arraylist;
    List<Image> imageList = new ArrayList<>();
    String imagedta;
    List<com.shrinkcom.alsaadceramicapp.pojo.wishlistpojo.Response> wishlist;
    WishlistPojo getWishlistPojo;
    List<Integer> positionlist = new ArrayList<>();
    TextView tv_titledes,tv_titlerev,tv_titlerevmore,tv_nodatamain;
    ProgressDialog progressDialog;
    RecyclerView rv_review;
    TextView tvofferdprice;
    private SliderLayout sliderShow;
    int selectedsizequantity;
    TextView tv_reviewnodata,tv_delivery;
    RelativeLayout rl4,rl2;
     ScrollView scrollitemd;
    LinearLayout ll_qty;

    TextView tvSeller;
    String sellerId;
    ImageView imgcalculator;
    AppCompatButton btn_calculator;


    EditText edtWidth,edtHeight;
    EditText edtArea;
    TextView tvreRult;
    Button tvCalculate, btnReset;
    ImageView tvFloorQuantity;
    TextView noOffloorQty;
    RadioGroup radioGroup;
    TextView tvDetail;
    TextView tvTotalCartoonPrice;
    LinearLayout Arealayout,FloorLayout,layoutAddfloor;
    boolean FLAGTYPE = true;
    RecyclerView recyclerViewFloor;
    List<FloorModel> floorList;
    FloorAdaptor floorAdaptor;

    int plenth;
    int pWidth;
    int thickness;
    double packetarea;
    double saleprice;
    RatingBar ratingBar;
    TextView tv_review;
    String review="";
    SliderView imageSlider;
    Mycart mycart1;
    String review11;
    String mainQty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_item_description, container, false);
        initviews();



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getActivity().onBackPressed();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                int count = fragmentManager.getBackStackEntryCount();
                if (count > 0) {
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                } else {
                    getActivity().onBackPressed();
                }
            }
        });
        /*progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading ...");
        progressDialog.show();*/
        getWishlist();


        btnincrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = tv_integer.getText().toString().trim();
                int qqq = Integer.parseInt(qty);
                int updated = qqq + 1;
                if (updated>selectedsizequantity){
                    Utilitynew.UserAlert(getContext(),"Item unavailable in stock");
                }else {
                    setupdatevalue(tv_pricedescrp, tv_integer, String.valueOf(updated));
                }
            }
        });

        btndescrese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qty = tv_integer.getText().toString().trim();
                if (qty.equals("1")) {
                    Utilitynew.UserAlert(getContext(),getString(R.string.oneqty));
                } else {
                    String qtyupdate = tv_integer.getText().toString().trim();
                    int qq = Integer.parseInt(qtyupdate);
                    int updated_ = qq - 1;
                    setupdatevalue(tv_pricedescrp, tv_integer, String.valueOf(updated_));
                }
            }
        });


        tv_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String QTy = tv_integer.getText().toString().trim();

                if (QTy.isEmpty()) {
                    Toast.makeText(getContext(), "qty should not be null", Toast.LENGTH_SHORT).show();
                } else if (QTy.equals("0")){

                    Toast.makeText(getContext(), "qty should not be Zero", Toast.LENGTH_SHORT).show();

                }else {

                    if (selectedsizequantity < 0) {
                        Utilitynew.UserAlert(getContext(), "Item unavailable in stock");

                    } else {
                        qty = tv_integer.getText().toString().trim();
                        if(Integer.parseInt(tv_integer.getText().toString().trim())>selectedsizequantity){
                            UserAlert(getContext(), "Requested Quantity is not available in stock");

                        }else {
                            mycart1 = new Mycart(getContext());
                            mycart1.open();
                            int  avllable = mycart1.checkAvailable(st_prodid, selectedsize, UserSession.getInstance(getActivity()).readPrefs(PREFS_USERID));

                            if (UserSession.getInstance(getActivity()).readPrefs(PREFS_USERID).equals("")) {
                                Utilitynew.UserAlert(getContext(), "Please login first to add product in cart");

                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
//                                avllable = mycart1.checkAvailable(st_prodid, selectedsize, UserSession.getInstance(getActivity()).readPrefs(PREFS_USERDEVICEID));
                            } else {

                                if (avllable == 1) {
                                    tv_addcart.setText(R.string.chckout);
                                    Utilitynew.UserAlert(getContext(), "already added in cart");

                                    if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {

                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                    } else {
//                            Intent intent = new Intent(getActivity(), Home.class);
//                            intent.putExtra("position", 3);
//                            startActivity(intent);

                                    }
                                    // Utilitynew.UserAlert(getContext(),"Already Added to cart");

                                } else {
                                        if (avllable == 1) {
                                            Toast.makeText(getContext(), "already added in cart", Toast.LENGTH_SHORT).show();
                                        } else {
                                            mycart1.insertDatashopping(st_prodid, st_nameD, imagedta, singleProductPojo.getResponse().get(0).getSalePrice(), qty, st_descripD, selectedsize, size_name, UserSession.getInstance(getActivity()).readPrefs(UserSession.PREFS_USERID), sellerId, product_city, mainQty);
                                            Log.d("availability", String.valueOf(avllable));
                                        }

                                    mycart1.close();
                                    Intent intent = new Intent(getActivity(), Home.class);
                                    intent.putExtra("position", 3);
                                    startActivity(intent);

                                    selectedsizequantity = selectedsizequantity - 1;

                                    Log.e("UUDATECOUNTVALLLL", ">>" + Utilitynew.getCount(getContext()));
                                    try {
                                        countproductoncart = Utilitynew.getCount(getContext());
                                        Log.e("ADDCART", ">>>" + String.valueOf(Utilitynew.getCount(getContext())));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e("ERRRORR", ">>" + e);
                                    }

                                }
                            }
                        }





                    }
                }
            }
        });

//        sliderShow.setPresetTransformer(SliderLayout.Transformer.Default);
//        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
//        sliderShow.setCustomAnimation(new DescriptionAnimation());
//        sliderShow.addOnPageChangeListener(this);


//        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
//        imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
////        imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
//        imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//        imageSlider.setIndicatorSelectedColor(Color.WHITE);
//        imageSlider.setIndicatorUnselectedColor(Color.GRAY);
//        imageSlider.setScrollTimeInSec(4);
//        imageSlider.setAutoCycle(true);
////set scroll delay in seconds :
//        imageSlider.startAutoCycle();


        imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        imageSlider.setScrollTimeInSec(3);
        imageSlider.setAutoCycle(true);
        imageSlider.startAutoCycle();
        imageSlider.getPagerIndicator().setAutoVisibility(true);
        imageSlider.getIndicatorSelectedColor();
        imageSlider.getIndicatorUnselectedColor();
        imageSlider.setIndicatorEnabled(true);
        imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);



        // TODO: 2/21/2020 add remove wishlist data

        imv_unfavwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {
                    Utilitynew.UserAlert(getContext(),"Please login first to add Products to wishlist");
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    imv_favwish.setVisibility(View.VISIBLE);
                    imv_unfavwish.setVisibility(View.INVISIBLE);
                    UserSession.getInstance(getContext()).writePrefs(PRODUCTID, st_prodid);
                    Log.e("kjh", ">>>" + PRODUCTID);
                    addWishlist();
                }

            }
        });
        imv_favwish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imv_favwish.setVisibility(View.INVISIBLE);
                imv_unfavwish.setVisibility(View.VISIBLE);
                removedata();
            }
        });

        imv_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = "http://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });


        imgcalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcal = new Intent(getActivity(), CalculatorActivity.class);
                intentcal.putExtra("length",singleProductPojo.getResponse().get(0).getLength());
                intentcal.putExtra("width",singleProductPojo.getResponse().get(0).getWidth());
                intentcal.putExtra("thickness",singleProductPojo.getResponse().get(0).getThickness());
                intentcal.putExtra("packetarea",singleProductPojo.getResponse().get(0).getPacket_area());
                intentcal.putExtra("saleprice",singleProductPojo.getResponse().get(0).getSalePrice());
                startActivityForResult(intentcal,1000);
            }
        });


        return v;
    }


    void setupdatevalue(TextView tvprice, TextView tvquantity, String quantity) {
        Log.e("UPDATEqty", ">>" + quantity);
        double price = Double.parseDouble(st_priceD);
        double quantities = Double.parseDouble(quantity);
        p2 = quantities * price;
        Log.e("UPDATEprice", ">>" + p2);
       // tvprice.setText("AED " + p2);
        tvquantity.setText("" + quantity);
    }

    private void initviews() {
        imgcalculator = v.findViewById(R.id.id_calculator);
        imageSlider = v.findViewById(R.id.imageSlider);
//        tv_productratingtxt = v.findViewById(R.id.tv_productratingtxt);
        tv_surfaceshapetxt = v.findViewById(R.id.tv_surfaceshapetxt);
        tv_typeofcuttingtxt = v.findViewById(R.id.tv_typeofcuttingtxt);
        tv_countryof_manufacture = v.findViewById(R.id.tv_countryof_manufacture);
        tv_thickness = v.findViewById(R.id.tv_thickness);
        tv_thecolor = v.findViewById(R.id.tv_thecolor);
        tv_thesize = v.findViewById(R.id.tv_thesize);

        toolbar = v.findViewById(R.id.toolbar_description);
        btndescrese = v.findViewById(R.id.decrease);
        tv_integer = v.findViewById(R.id.integer_number);
        btnincrease = v.findViewById(R.id.increase);
        tv_addcart = v.findViewById(R.id.tv_addcart);
        rv_size = v.findViewById(R.id.rv_size);
        imv_unfavwish = v.findViewById(R.id.imv_unfavwishs);
        imv_favwish = v.findViewById(R.id.imv_favwish);
        imv_share = v.findViewById(R.id.imv_share);
        tv_reviewnodata = v.findViewById(R.id.tv_reviewnodata);
        rl4 = v.findViewById(R.id.rl4);
        rl2 = v.findViewById(R.id.rl2);
        tvofferdprice = v.findViewById(R.id.offeredprice);
        tv_titleDdescription = v.findViewById(R.id.tv_titleDdescription);
        tv_namedescp = v.findViewById(R.id.tv_namedescp);
        tv_instock = v.findViewById(R.id.tv_instock);
        tv_pricedescrp = v.findViewById(R.id.tv_pricedescrp);
        tv_titledes = v.findViewById(R.id.tv_titledes);
        tv_titlerev = v.findViewById(R.id.tv_titlerev);
        rv_review = v.findViewById(R.id.rv_review);
        tv_titlerevmore = (TextView) v.findViewById(R.id.tv_titlerevmore);
        ll_qty = v.findViewById(R.id.ll_qty);
        tv_itemavailable = v.findViewById(R.id.tv_itemavailable);
        tv_delivery = v.findViewById(R.id.tv_delivery);
        tv_nodatamain = v.findViewById(R.id.tv_nodatamain);
        scrollitemd = v.findViewById(R.id.scrollitemd);
        tvSeller = v.findViewById(R.id.tv_titleDdescriptionsailer);

        btn_calculator = v.findViewById(R.id.btn_calculator);
        ratingBar =v.findViewById(R.id.ratingBar);


        radioGroup = v.findViewById(R.id.radiogrouptypeid);

        recyclerViewFloor = v.findViewById(R.id.id_recycleview);
        edtWidth = v.findViewById(R.id.edt_width);
        edtHeight = v.findViewById(R.id.edt_height);
        edtArea = v.findViewById(R.id.edt_area);

        tvreRult = v.findViewById(R.id.tv_result);
        tvCalculate = v.findViewById(R.id.tv_calculate);
        btnReset = v.findViewById(R.id.id_reset);
        tvFloorQuantity = v.findViewById(R.id.id_add_floorquty);
        noOffloorQty = v.findViewById(R.id.id_nooffloorqty);
        Arealayout = v.findViewById(R.id.id_arealayout);
        FloorLayout = v.findViewById(R.id.id_layout_floor);
        layoutAddfloor = v.findViewById(R.id.id_addfloor);
        tvDetail = v.findViewById(R.id.id_deatil);
        tvTotalCartoonPrice = v.findViewById(R.id.tv_total_price);

        tv_city = v.findViewById(R.id.tv_city);

        tv_review = v.findViewById(R.id.tv_review);

        btn_calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentcal = new Intent(getActivity(), CalculatorActivity.class);
                intentcal.putExtra("length",singleProductPojo.getResponse().get(0).getLength());
                intentcal.putExtra("width",singleProductPojo.getResponse().get(0).getWidth());
                intentcal.putExtra("thickness",singleProductPojo.getResponse().get(0).getThickness());
                intentcal.putExtra("packetarea",singleProductPojo.getResponse().get(0).getPacket_area());
                intentcal.putExtra("saleprice",saleprice);

                startActivityForResult(intentcal,1000);
            }
        });


        tvDetail.setText("Ceramic floor tiles "+ plenth +" M"+ " \n "+ pWidth + " M"+" \n"+CURRENCYCODE+" / per carton "+saleprice+" \nper cartoons cover "+packetarea+" area");

        noOffloorQty.setText("Sum m2 = ");


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL, false);
        recyclerViewFloor.setLayoutManager(layoutManager);
        floorList = new ArrayList<>();
        floorList.add(new FloorModel());
        floorAdaptor = new FloorAdaptor(floorList,getContext());
        recyclerViewFloor.setAdapter(floorAdaptor);
        floorAdaptor.notifyDataSetChanged();


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.id_rdarea){
                    Arealayout.setVisibility(View.VISIBLE);
                    FloorLayout.setVisibility(View.GONE);
                    layoutAddfloor.setVisibility(View.GONE);
                    FLAGTYPE = false;
                    noOffloorQty.setText(" Total m2 = ");

                }else if (checkedId==R.id.id_rdflore){
                    Arealayout.setVisibility(View.GONE);
                    FloorLayout.setVisibility(View.VISIBLE);
                    layoutAddfloor.setVisibility(View.VISIBLE);
                    FLAGTYPE = true;
                    noOffloorQty.setText("Sum m2 = ");
                }
            }
        });



        tvFloorQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // sunoffloor = sunoffloor+1;
                // noOffloorQty.setText("Sum m2 = "+sunoffloor);

                floorAdaptor.addFloor();

            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtWidth.setText("");
                edtHeight.setText("");
                edtArea.setText("");

                floorList = new ArrayList<>();
                floorList.add(new FloorModel());
                floorAdaptor = new FloorAdaptor(floorList,getContext());
                recyclerViewFloor.setAdapter(floorAdaptor);
                floorAdaptor.notifyDataSetChanged();

                if (FLAGTYPE){
                    noOffloorQty.setText("Sum m2 = ");
                }else {
                    noOffloorQty.setText(" Total m2 = ");
                }

                tvreRult.setText("Total Of Cartons");

            }
        });

        tvCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FLAGTYPE){

                    calculate();
                }else {
                    calculateArea();

                }



            }
        });




    }
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), "click on slider", Toast.LENGTH_SHORT).show();


    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

/*    public class ImageAdapter extends PagerAdapter {
        Context mContext;
        List<Image> imageList = new ArrayList<>();

        ImageAdapter(Context context, List<Image> imageList) {
            this.mContext = context;
            this.imageList = imageList;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            if (imageList.size() != 0) {
                Glide.with(mContext)
                        .load(ApiService.PRODUCT_IMAGE_URL + imageList.get(position).getImage())
                        .into(imageView);

                ((ViewPager) container).addView(imageView, 0);
            }

            Log.e("imagelist", "" + imagedta);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((ImageView) object);
        }

        @Override
        public int getCount() {

            return imageList.size();

        }

    }*/


    public class SizeAdpatorD extends RecyclerView.Adapter<SizeAdpatorD.viewHolder> {


        RecyclerbuttonClick recyclerbuttonClick;
        Context context;
        List<Size> sizeList;


            public SizeAdpatorD(Context context, List<Size> sizeList) {
            this.recyclerbuttonClick = recyclerbuttonClick;
            this.context = context;
            this.sizeList = sizeList;

        }


        @Override
        public SizeAdpatorD.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_descrip_size, viewGroup, false);
            return new SizeAdpatorD.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(final SizeAdpatorD.viewHolder viewHolder, final int position) {

            if (selectedItem != position) {
                viewHolder.item_d_size.setTextColor(context.getResources().getColor(R.color.darkgrey));
            }
            if (selectedItem == position)
            {
                selectedsize =sizeList.get(position).getSizeId();
                size_name =sizeList.get(position).getName();
                viewHolder.item_d_size.setTextColor(context.getResources().getColor(R.color.colorappbar));

            }

            if(selectedsize==null)
            {
                rl2.setVisibility(View.GONE);
            }
            else {
                viewHolder.item_d_size.setText(sizeList.get(position).getName());
            }
            viewHolder.item_d_size.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int previousItem = selectedItem;
                    selectedItem = position;
                    notifyItemChanged(previousItem);
                    notifyItemChanged(position);
                    selectedsize = viewHolder.item_d_size.getText().toString().trim();
                    size_name = viewHolder.item_d_size.getText().toString().trim();
                    selectedsizeid = sizeList.get(position).getSizeId();
                    Log.e("c", ">>" + selectedsize);
                    if (sizeList.get(position).getQuantity().equals("")) {

                        Log.e("selectedsize", ">>" + selectedsize);
                        tv_instock.setText(getResources().getString(R.string.OutOfStock));

                    }else {

                        tv_instock.setText(sizeList.get(position).getQuantity()+" "+"Sq.m"+ " " + getResources().getString(R.string.InStock));
                       // selectedsizequantity = Integer.parseInt(sizeList.get(position).getQuantity());
                    }

                }
            });


        }

        @Override
        public int getItemCount() {

            return 1;
        }

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView item_d_size;
            LinearLayout ll_sizead;

            public viewHolder(View itemView) {
                super(itemView);
                item_d_size = (TextView) itemView.findViewById(R.id.item_d_size);
                ll_sizead = (LinearLayout) itemView.findViewById(R.id.ll_sizead);


            }
        }

    }


    public void addWishlist() {
    /*    final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.setMessage("Loading...");
        progressDialog.setProgress(progressDialog.getProgress() * 100);
        pDialog.show();
*/

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSSEEEEE", ">>>" + response);
             //   pDialog.hide();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("status") == 1) {

                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                    e.printStackTrace();
                }
            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                  //      pDialog.hide();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "AddToWishlist");
                params.put("product_id", "" + st_prodid);
                params.put("user_id", "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID));

                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void removedata() {
  /*      final ProgressDialog pDialog = new ProgressDialog(getContext());
        pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        pDialog.setProgress(pDialog.getProgress() * 100);
        pDialog.show();
*/
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // pDialog.hide();
                Log.e("RemoveWishlist", ">>>" + response);

                try {

                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getInt("status") == 1) {

                      Utilitynew.UserAlert(getContext(),"Removed from wishlist");
                    } else {

                    }
                } catch (JSONException e) {
                    Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
              //  pDialog.hide();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("action", "RemoveUserWishlist");
                params.put("wish_id", "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_WISHID));
                Log.e("paramsAdd", ">>>>" + params.toString());
                return params;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }


    private void SingleProductsApi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ApiService.BASE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                       // if(progressDialog.isShowing())progressDialog.dismiss();
                        Log.e("RESPONSEEEEGAL", ">>" + response);
                        try {

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            singleProductPojo = gson.fromJson(response, SingleProductPojo.class);
                            if (singleProductPojo.getStatus() == 1)
                            {
                                if (singleProductPojo.getResponse().size() > 0) {
                                 // Log.e("SAILOERNAME",">>> "+singleProductPojo.getResponse().get(0).getSeller().get(0).getSname());
                                    try {
                                        tvSeller.setText(singleProductPojo.getResponse().get(0).getSeller().get(0).getSname());
                                        sellerId = singleProductPojo.getResponse().get(0).getSeller().get(0).getSellerId();

                                       plenth = singleProductPojo.getResponse().get(0).getLength();
                                       pWidth= singleProductPojo.getResponse().get(0).getWidth();
                                        thickness= singleProductPojo.getResponse().get(0).getThickness();
                                        packetarea = singleProductPojo.getResponse().get(0).getPacket_area();
                                        saleprice = Double.parseDouble(singleProductPojo.getResponse().get(0).getSalePrice());


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

//                                    if (singleProductPojo.getResponse().get(0).getImages().size()==0)
//                                    {
//                                        DefaultSliderView sliderView = new DefaultSliderView(getActivity());
//                                        sliderView.image(R.drawable.logo_2);
//                                        sliderShow.addSlider(sliderView);
//                                    }
//                                    else {

                                        for (int i = 0; i < singleProductPojo.getResponse().get(0).getImages().size(); i++) {
                                            imagedta = singleProductPojo.getResponse().get(0).getThumbnail_image();

                                            SliderAdapterExample adapter = new SliderAdapterExample(getContext(),singleProductPojo.getResponse().get(0).getImages());
                                            imageSlider.setSliderAdapter(adapter);


//                                            DefaultSliderView sliderView = new DefaultSliderView(getActivity());
//                                            sliderView.image(ApiService.PRODUCT_IMAGE_URL + "" + singleProductPojo.getResponse().get(0).getImages().get(i).getImage());
//                                            Log.e("URLLLLL", ">>" + ApiService.PRODUCT_IMAGE_URL + "" + singleProductPojo.getResponse().get(0).getImages().get(i).getImage());
//                                            sliderShow.addSlider(sliderView);
//                                            sliderShow.addOnPageChangeListener(onpa);


//                                        }
//                                        sliderShow.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                                    }



                            /*        ImageAdapter adapterView = new ImageAdapter(getContext(), singleProductPojo.getResponse().get(0).getImages());
                                    mPager.setAdapter(adapterView);*/

                                    if (singleProductPojo.getResponse().get(0).getSize().size() == 0) {
                                        //tv_reviewnodata.setVisibility(View.VISIBLE);
                                        rv_size.setVisibility(View.GONE);
                                    } else {


                                        SizeAdpatorD adptor = new SizeAdpatorD(getContext(), singleProductPojo.getResponse().get(0).getSize());
                                        LinearLayoutManager horizontalLayoutManagaer
                                                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                        rv_size.setLayoutManager(horizontalLayoutManagaer);
                                        rv_size.setAdapter(adptor);
                                    }

                                    if (singleProductPojo.getResponse().get(0).getReview().size() == 0) {
                                        //tv_reviewnodata.setVisibility(View.VISIBLE);
                                        rl4.setVisibility(View.GONE);
                                    } else {
                                        ReviewAdpator rvadptor = new ReviewAdpator(getContext(), singleProductPojo.getResponse().get(0).getReview());
                                        LinearLayoutManager horizontalLayoutManagaer1
                                                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                        rv_review.setLayoutManager(horizontalLayoutManagaer1);
                                        rv_review.setAdapter(rvadptor);
                                    }

                                    st_nameD = singleProductPojo.getResponse().get(0).getProdTitle();
                                    st_ARnameD = singleProductPojo.getResponse().get(0).getProdTitleAr();
                                    st_descripD = singleProductPojo.getResponse().get(0).getProdDesc();
                                    st_color = singleProductPojo.getResponse().get(0).getColor();
                                    st_country = singleProductPojo.getResponse().get(0).getManufacture_country();
                                    st_city = singleProductPojo.getResponse().get(0).getSeller().get(0).getCity_name();
                                    st_thickness = String.valueOf(singleProductPojo.getResponse().get(0).getThickness());
                                    st_typethickness = singleProductPojo.getResponse().get(0).getThickness();
                                    st_size = String.valueOf(singleProductPojo.getResponse().get(0).getSize().get(0).getSize());
                                    st_shap = singleProductPojo.getResponse().get(0).getSurface_shape();
                                    st_ARdescripD = singleProductPojo.getResponse().get(0).getProdDescAr();
                                    st_rating = ""+singleProductPojo.getResponse().get(0).getAverage_rating();
                                    review11 = ""+singleProductPojo.getResponse().get(0).getUser_rating();
                                    product_city = ""+singleProductPojo.getResponse().get(0).getCity();
                                    Log.d("citiesssss",""+singleProductPojo.getResponse().get(0).getCity());

                                    tv_review.setText("Review"+" "+review11);


                                    if (singleProductPojo.getResponse().get(0).getDelivery_time().equals("")){
                                        tv_delivery.setText("");
                                    }else {

                                        if (new SessionManager(getContext()).getLanguage().equals("en")) {
                                            String deliveryy = singleProductPojo.getResponse().get(0).getDelivery_time();
                                            String concate = R.string.deliveryby+ deliveryy;
                                            tv_delivery.setText(getResources().getString(R.string.deliveryby)+" "+deliveryy);
                                        } else {
                                            String deliveryy = singleProductPojo.getResponse().get(0).getDelivery_time();
                                            String concate = R.string.deliveryby+ deliveryy;
                                            tv_delivery.setText(R.string.deliveryby+" "+deliveryy);                                        }

                                    }

                                    Log.v("DIKSHAA","RATIONG>>>"+st_rating);

                                    st_instockD = singleProductPojo.getResponse().get(0).getQuantity();

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_namedescp.setText(st_nameD);
                                    } else {
                                        tv_namedescp.setText(st_ARnameD);
                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_titleDdescription.setText(st_descripD);
                                    } else {
                                        tv_titleDdescription.setText(st_ARdescripD);
                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_countryof_manufacture.setText(st_country);
                                    } else {
                                        tv_countryof_manufacture.setText(st_country);
                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {
                                        tv_city.setText(st_city);
                                    } else {
                                        tv_city.setText(st_city);
                                    }


                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        if (st_rating.equals("null")){

                                        }else {
                                            try {
                                                ratingBar.setRating(Float.parseFloat(st_rating));
                                            } catch (NumberFormatException e) {
                                                e.printStackTrace();
                                                ratingBar.setRating(0.0f);
                                            }
                                        }

                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_surfaceshapetxt.setText(st_shap);
                                    } else {
                                        tv_surfaceshapetxt.setText(st_shap);
                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_thecolor.setText(st_color);
                                    } else {
                                        tv_thecolor.setText(st_color);
                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_thesize.setText(st_size);
                                    } else {
                                        tv_thesize.setText(st_size);
                                    }
                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_thickness.setText(st_thickness);
                                    } else {
                                        tv_thickness.setText(st_thickness);
                                    }

                                    if (new SessionManager(getContext()).getLanguage().equals("en")) {

                                        tv_typeofcuttingtxt.setText(st_thickness);
                                    } else {
                                        tv_typeofcuttingtxt.setText(st_thickness);
                                    }



                                    if (singleProductPojo.getResponse().get(0).getQuantity().equals("0")) {

                                        tv_instock.setText(getResources().getString(R.string.OutOfStock));
                                        tv_delivery.setVisibility(View.GONE);
                                        ll_qty.setVisibility(View.GONE);
                                        tv_addcart.setVisibility(View.GONE);
                                        tv_itemavailable.setVisibility(View.VISIBLE);

                                    } else {

                                        tv_instock.setText(singleProductPojo.getResponse().get(0).getQuantity() +" "+"Sq.m"+ " " + getResources().getString(R.string.InStock));

                                        mainQty= singleProductPojo.getResponse().get(0).getQuantity();

                                        selectedsizequantity = Integer.parseInt(singleProductPojo.getResponse().get(0).getQuantity());

                                    }


                                    if (singleProductPojo.getResponse().get(0).getProdPrice().equals("0"))
                                    {
                                        tv_pricedescrp.setVisibility(View.GONE);
                                    }

                                    String startdate = singleProductPojo.getResponse().get(0).getStartDate() + " " + singleProductPojo.getResponse().get(0).getStart_time();
                                    String strenddate = singleProductPojo.getResponse().get(0).getEndDate() + " " + singleProductPojo.getResponse().get(0).getEnd_time();

                                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                    try {
                                        Date datestart = format.parse(startdate);
                                        Date dateend = format.parse(strenddate);

                                        if (datestart.getTime() <= new Date().getTime() && dateend.getTime() >= new Date().getTime()) {
                                            st_priceD = singleProductPojo.getResponse().get(0).getSalePrice();
                                            tv_pricedescrp.setText(CURRENCYCODE + " " + singleProductPojo.getResponse().get(0).getProdPrice()+" "+"per sq. m");
                                            tv_pricedescrp.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);

                                            tvofferdprice.setVisibility(View.VISIBLE);
                                            tvofferdprice.setText(CURRENCYCODE + " " + singleProductPojo.getResponse().get(0).getSalePrice()+" "+"per sq. m ");


                                            Log.e("offerprice", "" + st_priceD);

                                            // TODO: 3/7/2020   set view here
                                        } else {
                                            // TODO: 3/7/2020   set view here
                                            st_priceD = singleProductPojo.getResponse().get(0).getSalePrice();
                                            tv_pricedescrp.setText(CURRENCYCODE + " " + singleProductPojo.getResponse().get(0).getProdPrice()+" "+"per sq. m");
                                            tv_pricedescrp.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                                            tvofferdprice.setVisibility(View.VISIBLE);
                                            tvofferdprice.setText(CURRENCYCODE + " " + singleProductPojo.getResponse().get(0).getSalePrice()+" "+"per sq. m ");

                                            Log.e("price", "" + singleProductPojo.getResponse().get(0).getProdPrice());

                                        }
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        st_priceD = singleProductPojo.getResponse().get(0).getProdPrice();
                                        tv_pricedescrp.setText(CURRENCYCODE + " " + st_priceD+" "+"per sq. m" );
                                        tv_pricedescrp.setPaintFlags(TextPaint.STRIKE_THRU_TEXT_FLAG);
                                        tvofferdprice.setVisibility(View.VISIBLE);
                                        tvofferdprice.setText(CURRENCYCODE + " " + singleProductPojo.getResponse().get(0).getSalePrice()+" "+"per sq. m ");

                                        Log.e("price", "" + singleProductPojo.getResponse().get(0).getProdPrice());

                                    }


                                    if (getWishlistPojo.getResponse().size() > 0) {
                                        for (int i = 0; i < getWishlistPojo.getResponse().size(); i++) {

                                            Log.e("DADADADAA", ">>" + getWishlistPojo.getResponse().get(i).getWishProductId() + " " + singleProductPojo.getResponse().get(0).getProdId());
                                            if (getWishlistPojo.getResponse().get(i).getWishProductId().equals(singleProductPojo.getResponse().get(0).getProdId())) {
                                                // TODO: 2/24/2020  show heart symbol wishlist
                                                imv_favwish.setVisibility(View.VISIBLE);
                                                imv_unfavwish.setVisibility(View.GONE);
                                                break;
                                            } else {
                                                // TODO: 2/24/2020  show defpounlt hesart symbol  not wishlist
                                                imv_favwish.setVisibility(View.GONE);
                                                imv_unfavwish.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    } else {
                                        // TODO: 2/24/2020  show defpounlt hesart symbol  not wishlist

                                        imv_favwish.setVisibility(View.GONE);
                                        imv_unfavwish.setVisibility(View.VISIBLE);
                                    }
                                    tv_titlerevmore.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent intent = new Intent(getContext(), ReviewViewMoreActivity.class);
                                            intent.putParcelableArrayListExtra("listreview", (ArrayList<? extends Parcelable>) singleProductPojo.getResponse().get(0).getReview());
                                            startActivity(intent);

                                        }
                                    });
                                }

                            }
                        } catch (JsonSyntaxException e) {
                          //  if(progressDialog.isShowing())progressDialog.dismiss();
                            tv_nodatamain.setVisibility(View.VISIBLE);
                            scrollitemd.setVisibility(View.GONE);
                            Utilitynew.UserAlert(getActivity(),(getString(R.string.tv_internet)));
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // if(progressDialog.isShowing())progressDialog.dismiss();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "SingleProduct");
                params.put("product_id", "" + st_prodid);
              /*  if(sizeid.equals("")){
                    params.put("size_id","");
                }
                else {
                    params.put("size_id","");
                }*/

                Log.e("SENDVALUE11", ">>" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


    private void getWishlist() {
        StringRequest stringreuest = new StringRequest(Request.Method.POST, ApiService.BASE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("GET_WISHLIST", ">>>" + response);
                try {
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    getWishlistPojo = gson.fromJson(response, WishlistPojo.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }


                st_prodid =  getArguments().getString("productid");
                Log.e("st_prodid", ">>" + st_prodid);
                sizeid =  getArguments().getString("sizeid");

                Log.e("st_prodidSIZEE", ">>" + ""+sizeid);
                SingleProductsApi();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                st_prodid =  getArguments().getString("productid");
                sizeid =  getArguments().getString("sizeid");

                Log.e("st_prodidSIZEE", ">>" + ""+sizeid);


            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("action", "GetUserWishlist");
                params.put("user_id", "" + UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID));
                Log.e("paramsAdd", ">>>>" + params.toString());

                return params;

            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringreuest);
    }


    public class ReviewAdpator extends RecyclerView.Adapter<ReviewAdpator.viewHolder> {


        RecyclerbuttonClick recyclerbuttonClick;
        Context context;
        List<Review> reviewList;


        public ReviewAdpator(Context context, List<Review> reviewList) {
            this.recyclerbuttonClick = recyclerbuttonClick;
            this.context = context;
            this.reviewList = reviewList;

        }


        @Override
        public ReviewAdpator.viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_reviewlist, viewGroup, false);
            return new ReviewAdpator.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ReviewAdpator.viewHolder viewHolder, final int position) {

            if(singleProductPojo.getResponse().get(position).getReview().size()>0) {
                viewHolder.tv_usernamerv.setText(reviewList.get(position).getUserFname());
                viewHolder.tv_userrv.setText(reviewList.get(position).getReview());
                viewHolder.tv_daterv.setText(reviewList.get(position).getDate());
                viewHolder.ratingrv.setRating(Float.parseFloat(reviewList.get(position).getRating()));
            }



        }

        @Override
        public int getItemCount() {

            return reviewList.size();
        }

        public class viewHolder extends RecyclerView.ViewHolder {

            TextView tv_usernamerv,tv_userrv,tv_daterv;
            RatingBar ratingrv;

            public viewHolder(View itemView) {
                super(itemView);
                tv_usernamerv = (TextView) itemView.findViewById(R.id.tv_usernamerv);
                tv_userrv = (TextView) itemView.findViewById(R.id.tv_userrv);
                tv_daterv = (TextView) itemView.findViewById(R.id.tv_daterv);

                ratingrv = (RatingBar) itemView.findViewById(R.id.ratingrv);


            }
        }

    }


    void  calculate(){

        Log.e("DATAAAAA",">>>>plenth "+plenth);
        Log.e("DATAAAAA",">>>>pWidth "+pWidth);
        Log.e("DATAAAAA",">>>>packetarea "+packetarea);
        double nooftiles = packetarea/(plenth*pWidth);
        Log.e("DATAAAAA",">>>> "+nooftiles);
        double area = plenth*pWidth*nooftiles;




        double result =  floorAdaptor.calculateArea()/area;
        noOffloorQty.setText("Sum m2 = "+(int)floorAdaptor.calculateArea());
        if (result%1==0){
            tvreRult.setText(" Total Of Cartons "+(int) result);
        }else {
            result= result+1;
            tvreRult.setText(" Total Of Cartons "+(int) result);
        }

        tvTotalCartoonPrice.setText("Total Cartons Price "+ ""+CURRENCYCODE+" "+String.format("%.2f", result*saleprice));
    }


    void  calculateArea(){
        double nooftiles = packetarea/(plenth*pWidth);
        double area =plenth*pWidth*nooftiles;

        if (edtArea.getText().toString().isEmpty()){
            edtWidth.setError("Enter Area");
        }else {
            double userares = Double.parseDouble(edtArea.getText().toString());
            noOffloorQty.setText(" Total m2 = "+(int)userares);

            double result = userares/area;

            if (result%1==0){
                tvreRult.setText(" Total Of Cartons "+(int) result);
            }else {
                result= result+1;
                tvreRult.setText(" Total Of Cartons "+(int) result);
            }
            tvTotalCartoonPrice.setText("Total Cartons Price "+ ""+CURRENCYCODE+" "+String.format("%.2f", result*saleprice));
        }

    }


    public void UserAlert(final Context mContext, String message) {

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        tv_integer.setText("");
                        //do things
                    }
                });
        androidx.appcompat.app.AlertDialog alert = builder.create();
        alert.show();
    }

}
