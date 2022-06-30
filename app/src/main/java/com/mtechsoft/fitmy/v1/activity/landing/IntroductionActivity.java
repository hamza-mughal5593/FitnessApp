package com.mtechsoft.fitmy.v1.activity.landing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.login.LoginPhoneActivity;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.mtechsoft.fitmy.v1.adapter.IntroductionViewPagerAdapter;
import com.mtechsoft.fitmy.v1.common.OnSwipeTouchListener;
import me.relex.circleindicator.CircleIndicator;

public class IntroductionActivity extends AppCompatActivity {
    private final int NUM_PAGES = 5;
    private ViewPager viewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        IntroductionViewPagerAdapter mAdapter = new IntroductionViewPagerAdapter(getSupportFragmentManager());

        viewpager = findViewById(R.id.viewpager);
        CircleIndicator indicator = findViewById(R.id.indicator);
        viewpager.setAdapter(mAdapter);
        indicator.setViewPager(viewpager);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            public void onPageSelected(int position) {
                refreshPageNavigation(position);
            }
        });

        viewpager.setOnTouchListener(new OnSwipeTouchListener(IntroductionActivity.this) {
            public void onSwipeTop() {
                //Toast.makeText(getApplicationContext(), "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
                int curPage = viewpager.getCurrentItem();

                if (curPage != 0) {
                    viewpager.setCurrentItem(curPage-1);
                }
               // Toast.makeText(getApplicationContext(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                int curPage = viewpager.getCurrentItem();

                if (curPage == (NUM_PAGES-1)) {
                   // startActivity(new Intent(IntroductionActivity.this, StartActivity.class));
                } else {
                    viewpager.setCurrentItem(curPage+1);
                }
               // Toast.makeText(getApplicationContext(), "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
              //  Toast.makeText(getApplicationContext(), "bottom", Toast.LENGTH_SHORT).show();
            }

        });


        TextView tv_ia_02 = findViewById(R.id.tv_ia_02);
        tv_ia_02.setOnClickListener(view -> {
            int curPage = viewpager.getCurrentItem();

            if (curPage != 0) {
                viewpager.setCurrentItem(curPage-1);
            }
        });

        TextView tv_ia_01 = findViewById(R.id.tv_ia_01);
        tv_ia_01.setOnClickListener(view -> {
            int curPage = viewpager.getCurrentItem();

            if (curPage == (NUM_PAGES-1)) {
               // startActivity(new Intent(IntroductionActivity.this, StartActivity.class));
            } else {
                viewpager.setCurrentItem(curPage+1);
            }
        });
    }

    private void refreshPageNavigation(int curPage) {
        TextView tv_ia_02 = findViewById(R.id.tv_ia_02);
        TextView tv_ia_01 = findViewById(R.id.tv_ia_01);

        tv_ia_02.setVisibility(View.INVISIBLE);
        tv_ia_01.setVisibility(View.INVISIBLE);

        switch (curPage) {
            case 0:
                tv_ia_01.setVisibility(View.VISIBLE);
                break;
            default:
                tv_ia_02.setVisibility(View.VISIBLE);
                tv_ia_01.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        int curPage = viewpager.getCurrentItem();

        if (curPage == 0) {
            super.onBackPressed();
        } else {
            viewpager.setCurrentItem(curPage-1);
        }
    }

    public void goOnboarding(View view) {
        startActivity(new Intent(this, OnboardingProfileActivity.class));
    }

    public void goLogin(View view) {
        startActivity(new Intent(this, LoginPhoneActivity.class));
    }
}
