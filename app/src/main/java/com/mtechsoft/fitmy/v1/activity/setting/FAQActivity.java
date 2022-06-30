package com.mtechsoft.fitmy.v1.activity.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mtechsoft.fitmy.R;

public class FAQActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        LinearLayout tv_af_t_01 = findViewById(R.id.tv_af_t_01);
        LinearLayout tv_af_t_02 = findViewById(R.id.tv_af_t_02);
        LinearLayout tv_af_t_03 = findViewById(R.id.tv_af_t_03);
        LinearLayout tv_af_t_04 = findViewById(R.id.tv_af_t_04);
        LinearLayout tv_af_t_05 = findViewById(R.id.tv_af_t_05);
        LinearLayout tv_af_t_06 = findViewById(R.id.tv_af_t_06);
        LinearLayout tv_af_t_07 = findViewById(R.id.tv_af_t_07);
        LinearLayout tv_af_t_08 = findViewById(R.id.tv_af_t_08);
        LinearLayout tv_af_t_09 = findViewById(R.id.tv_af_t_09);
        LinearLayout tv_af_t_10 = findViewById(R.id.tv_af_t_10);

        TextView tv_af_d_01 = findViewById(R.id.tv_af_d_01);
        TextView tv_af_d_02 = findViewById(R.id.tv_af_d_02);
        TextView tv_af_d_03 = findViewById(R.id.tv_af_d_03);
        TextView tv_af_d_04 = findViewById(R.id.tv_af_d_04);
        TextView tv_af_d_05 = findViewById(R.id.tv_af_d_05);
        TextView tv_af_d_06 = findViewById(R.id.tv_af_d_06);
        TextView tv_af_d_07 = findViewById(R.id.tv_af_d_07);
        TextView tv_af_d_08 = findViewById(R.id.tv_af_d_08);
        TextView tv_af_d_09 = findViewById(R.id.tv_af_d_09);
        TextView tv_af_d_10 = findViewById(R.id.tv_af_d_10);

        tv_af_t_01.setOnClickListener(view -> {
            if(tv_af_d_01.getVisibility() == View.GONE) {
                tv_af_d_01.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_01.setVisibility(View.GONE);
            }
        });

        tv_af_t_02.setOnClickListener(view -> {
            if(tv_af_d_02.getVisibility() == View.GONE) {
                tv_af_d_02.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_02.setVisibility(View.GONE);
            }
        });

        tv_af_t_03.setOnClickListener(view -> {
            if(tv_af_d_03.getVisibility() == View.GONE) {
                tv_af_d_03.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_03.setVisibility(View.GONE);
            }
        });

        tv_af_t_04.setOnClickListener(view -> {
            if(tv_af_d_04.getVisibility() == View.GONE) {
                tv_af_d_04.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_04.setVisibility(View.GONE);
            }
        });

        tv_af_t_05.setOnClickListener(view -> {
            if(tv_af_d_05.getVisibility() == View.GONE) {
                tv_af_d_05.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_05.setVisibility(View.GONE);
            }
        });

        tv_af_t_06.setOnClickListener(view -> {
            if(tv_af_d_06.getVisibility() == View.GONE) {
                tv_af_d_06.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_06.setVisibility(View.GONE);
            }
        });

        tv_af_t_07.setOnClickListener(view -> {
            if(tv_af_d_07.getVisibility() == View.GONE) {
                tv_af_d_07.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_07.setVisibility(View.GONE);
            }
        });

        tv_af_t_08.setOnClickListener(view -> {
            if(tv_af_d_08.getVisibility() == View.GONE) {
                tv_af_d_08.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_08.setVisibility(View.GONE);
            }
        });

        tv_af_t_09.setOnClickListener(view -> {
            if(tv_af_d_09.getVisibility() == View.GONE) {
                tv_af_d_09.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_09.setVisibility(View.GONE);
            }
        });

        tv_af_t_10.setOnClickListener(view -> {
            if(tv_af_d_10.getVisibility() == View.GONE) {
                tv_af_d_10.setVisibility(View.VISIBLE);
            } else {
                tv_af_d_10.setVisibility(View.GONE);
            }
        });

    }
}
