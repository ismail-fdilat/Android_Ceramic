package com.shrinkcom.alsaadceramicapp.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.shrinkcom.alsaadceramicapp.R;
import com.shrinkcom.alsaadceramicapp.helper.LocaleHelper;
import com.shrinkcom.alsaadceramicapp.utils.SessionManager;

import java.util.Locale;

public class LanguageSelect extends AppCompatActivity {

    TextView tv_Lenglish,tv_Larabic,tv_back;
    AlertDialog alertDialog;
    SessionManager sessionManager;
    String currentLanguage = "en", currentLang;
    Locale myLocale;
    final  CharSequence[] values= {"English(India)", "Arabic" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        sessionManager = new SessionManager(this);
    //    currentLanguage = getIntent().getStringExtra(currentLang);
        tv_Lenglish = findViewById(R.id.tv_Lenglish);
        tv_Larabic = findViewById(R.id.tv_Larabic);

        tv_back = findViewById(R.id.tv_back);
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_Lenglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateViews("en");
            }
        });

        tv_Larabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateViews("ar");
            }
        });
    }

    private void updateViews(String languageCode) {
        sessionManager.setLanguage(languageCode);
        Context context = LocaleHelper.setLocale(LanguageSelect.this, languageCode);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration conf = resources.getConfiguration();
        conf.locale = myLocale;
        resources.updateConfiguration(conf, dm);
        startActivity(new Intent(LanguageSelect.this, Home.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));


    }

    //language change
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));

        Log.v("Ceramic", "language "+base);
    }

}
