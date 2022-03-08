package com.shrinkcom.alsaadceramicapp.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.activities.CheckOutActivity;
import com.shrinkcom.alsaadceramicapp.activities.Home;
import com.shrinkcom.alsaadceramicapp.activities.LanguageSelect;
import com.shrinkcom.alsaadceramicapp.activities.LoginActivity;
import com.shrinkcom.alsaadceramicapp.activities.RegisterActivty;
import com.shrinkcom.alsaadceramicapp.utils.ApiService;
import com.shrinkcom.alsaadceramicapp.utils.PermissionChecker;
import com.shrinkcom.alsaadceramicapp.utils.UserSession;
import com.shrinkcom.alsaadceramicapp.utils.Utilitynew;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment {


    Toolbar toolbar;
    TextView tv_loginpf,tv_signuppf;
    View view;
    CircleImageView imv_accpic;
    String IMAGE;
    LinearLayout ll1_pf,ll2_order,ll1_language,ll6_contact,ll4_track,ll3_addbook,ll7_rateus,ll5_call,ll2_notify,ll5_whatscall;

    PermissionChecker permissionChecker = new PermissionChecker();
    String[] RequiredPermissions = {Manifest.permission.CALL_PHONE};
    Handler handler;
    long mLastClickTime = 0;
    TextView tv_logout,tv_changepassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_account, container, false);
        Home.currentHome=-1;
        handler = new Handler();
        toolbar = view.findViewById(R.id.toolbar_acc);
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

        tv_signuppf=view.findViewById(R.id.tv_signuppf);
        tv_logout=view.findViewById(R.id.tv_logout);
        tv_changepassword = view.findViewById(R.id.tv_changepassword);
        tv_loginpf=view.findViewById(R.id.tv_loginpf);
        ll2_order=view.findViewById(R.id.ll2_order);
        ll1_language=view.findViewById(R.id.ll1_language);
        ll1_pf=view.findViewById(R.id.ll1_pf);
        ll6_contact=view.findViewById(R.id.ll6_contact);
        ll7_rateus=view.findViewById(R.id.ll7_rateus);
        ll5_call=view.findViewById(R.id.ll5_call);
        ll2_notify=view.findViewById(R.id.ll2_notify);
        imv_accpic=view.findViewById(R.id.imv_accpic);
        ll5_whatscall=view.findViewById(R.id.ll5_whatscall);

        ll3_addbook=view.findViewById(R.id.ll3_addbook);

        if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {
            tv_loginpf.setVisibility(View.VISIBLE);
            tv_signuppf.setVisibility(View.VISIBLE);
        } else {
            tv_loginpf.setVisibility(View.GONE);
            tv_signuppf.setVisibility(View.GONE);
            tv_logout.setVisibility(View.VISIBLE);

        }
        tv_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {
                            UserAlert(getContext());
                        } else {
                            Fragment fragment = new ChangePasswordFragment();
                            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.frame, fragment);
                            transaction.addToBackStack(null);
                            transaction.commit();

                        }


            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utilitynew.logoutUser(getContext());
            }
        });

        IMAGE = UserSession.getInstance(getActivity()).readPrefs(UserSession.PROFILEIMAGE);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.ic_userpf);
        Log.v("SAKSHI", "image >>" + IMAGE);

        Glide.with(imv_accpic.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(ApiService.PROFILE_IMAGE + IMAGE)
                .into(imv_accpic);



        tv_loginpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        tv_signuppf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),
                        RegisterActivty.class);
                startActivity(i);
            }
        });
        ll2_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Fragment   fragment = new MyOrderFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        ll1_pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserSession.getInstance(getContext()).readPrefs(UserSession.PREFS_USERID).equals("")) {
                    UserAlert(getContext());
                } else {
                    Fragment   fragment = new EditProfileFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                }

            }
        });

        ll6_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Fragment   fragment = new ContactUsFragment();
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();




            }
        });
        final String number="+966559996918";

        ll5_whatscall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://api.whatsapp.com/send?phone="+number;

                Log.v("DIKSHAA","WHATSAPP  >> "+"https://api.whatsapp.com/send?phone="+number);

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ll2_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment   fragment = new NotificationFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();




            }
        });


        ll1_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),
                        LanguageSelect.class);
                startActivity(i);
            }
        });

        ll3_addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(),
                        CheckOutActivity.class);
                startActivity(i);
            }
        });

        ll7_rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps?hl=en")));
            }
        });

        ll5_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                checkPermissions();

            }
        });
        return view;
    }
    private void checkPermissions() {
        permissionChecker.verifyPermissions(getActivity(), RequiredPermissions, new PermissionChecker.VerifyPermissionsCallback() {
            @Override
            public void onPermissionAllGranted() {
                                new AlertDialog.Builder(getActivity())
                                        .setMessage("+966559996918")
                                        .setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent dial = new Intent();
                                                dial.setAction("android.intent.action.DIAL");
                                                try {
                                                    dial.setData(Uri.parse("tel:+966559996918"));
                                                    startActivity(dial);
                                                } catch (Exception e) {
                                                    Log.e("Calling", "" + e.getMessage());
                                                }
                                            }

                                        })
                                        .setNegativeButton("Cancel", null)
                                        .show();


            }

            @Override
            public void onPermissionDeny(String[] permissions) {
                Utilitynew.UserAlert(getActivity(),getString(R.string.permite_permisssion));
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionChecker.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public static void UserAlert(final Context mContext) {

        new android.app.AlertDialog.Builder(mContext)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Please login First to view Profile")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(mContext, LoginActivity.class);
                        mContext.startActivity(i);

                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

}
