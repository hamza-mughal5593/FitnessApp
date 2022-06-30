package com.mtechsoft.fitmy.v1.activity.landing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.smarteist.autoimageslider.SliderView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.adapter.SliderAdapterExample;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.LocaleHelper;

public class LanguagePrefActivity extends AppCompatActivity {


    Button b_lpa2_01;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_pref);

        b_lpa2_01 = findViewById(R.id.b_lpa2_01);


        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();
        sliderView.setIndicatorVisibility(false);


        prepareNav();

        verifyStoragePermissions(this);
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );


        }else {


        }
    }

    private void prepareNav() {
        Spinner s_lpa2_01 = findViewById(R.id.s_lpa2_01);

        s_lpa2_01.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String lang = s_lpa2_01.getSelectedItem().toString();
                switch (lang) {
                    case "Bahasa Malaysia":

                        b_lpa2_01.setText("TERUSKAN");
                        break;
                    case "English":
                        b_lpa2_01.setText("CONTINUE");
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        Button b_lpa2_01 = findViewById(R.id.b_lpa2_01);
        b_lpa2_01.setOnClickListener(v -> {
            String lang = s_lpa2_01.getSelectedItem().toString();
            switch (lang) {
                case "Bahasa Malaysia":
                    doSavePref(Constants.LOCALE_MALAY);
                    break;
                case "English":
                    doSavePref(Constants.LOCALE_ENGLISH);
                    break;
            }
        });

    }



    private void doSavePref(String locale) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SP_LANG, locale).commit();

        LocaleHelper.setActivityLocale(this, locale);

        String login_statuss = Utilities.getString(LanguagePrefActivity.this,"Login_status");
        if (login_statuss.equals("true")){

            startActivity(new Intent(this, FitnessActivity.class));

        }else {

            startActivity(new Intent(this, IntroductionActivity.class));

        }

    }


    private void doSavePref2(String locale) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.SP_LANG, locale).commit();

        LocaleHelper.setActivityLocale(this, locale);

//        String login_statuss = Utilities.getString(LanguagePrefActivity.this,"Login_status");
//        if (login_statuss.equals("true")){
//
//            startActivity(new Intent(this, FitnessActivity.class));
//
//        }else {
//
//            startActivity(new Intent(this, IntroductionActivity.class));
//
//        }

    }
}
