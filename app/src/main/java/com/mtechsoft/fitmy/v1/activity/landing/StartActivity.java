package com.mtechsoft.fitmy.v1.activity.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smarteist.autoimageslider.SliderView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.login.LoginPhoneActivity;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.mtechsoft.fitmy.v1.adapter.SliderAdapterExample;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();
        sliderView.setIndicatorVisibility(false);
    }

    public void goOnboarding(View view) {
        startActivity(new Intent(this, OnboardingProfileActivity.class));
    }

    public void goLogin(View view) {
        startActivity(new Intent(this, LoginPhoneActivity.class));
    }
}
