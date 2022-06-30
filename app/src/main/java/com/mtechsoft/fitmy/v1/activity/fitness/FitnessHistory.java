package com.mtechsoft.fitmy.v1.activity.fitness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.HomeActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;

public class FitnessHistory extends AppCompatActivity {

    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_history);

        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navLayout.setVisibility(View.VISIBLE);
                coverView.setVisibility(View.VISIBLE);
            }
        });

        coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navLayout.setVisibility(View.GONE);
                coverView.setVisibility(View.GONE);
            }
        });

        prepareNavBar();
        PreHistory();
        prepareGpsTracking();
    }

    private void prepareGpsTracking() {
        Button b_af_gps_start = findViewById(R.id.b_af_gps_start);
        b_af_gps_start.setOnClickListener(view -> {

            if (ExerciseNewActivity.activity_started){

                Intent intent = new Intent(FitnessHistory.this,ExerciseActiveActivity.class);
                intent.putExtra("Coming_from","fitness_history");
                startActivity(intent);
//                startActivity(new Intent(FitnessHistory.this, ExerciseActiveActivity.class));


            }else {
                startActivity(new Intent(FitnessHistory.this, ExerciseNewActivity.class));

            }

        });
    }

    private void prepareNavBar() {
        View.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.v_nav_home:
                case R.id.iv_nav_home:
                case R.id.tv_nav_home:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, FitnessActivity.class));
                    break;
                case R.id.v_nav_fitness:
                case R.id.iv_nav_fitness:
                case R.id.tv_nav_fitness:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, BookingActivity.class));
                    break;
                case R.id.v_nav_reward:
                case R.id.iv_nav_reward:
                case R.id.tv_nav_reward:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, RewardActivity.class));
                    break;
                case R.id.v_nav_event:
                case R.id.iv_nav_event:
                case R.id.tv_nav_event:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, AsEventActivity.class));
                    break;
                case R.id.v_nav_booking:
                case R.id.iv_nav_booking:
                case R.id.tv_nav_booking:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, HomeActivity.class));
                    break;
                case R.id.v_nav_history:
                case R.id.iv_nav_history:
                case R.id.tv_nav_history:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, FitnessHistory.class));
                    break;

                case R.id.v_nav_news:
                case R.id.iv_nav_news:
                case R.id.tv_nav_news:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, AsNewsActivity.class));
                    break;
                case R.id.v_nav_gallery:
                case R.id.iv_nav_gallery:
                case R.id.tv_nav_gallery:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    Dialogue();
                    break;
                case R.id.v_nav_music:
                case R.id.iv_nav_music:
                case R.id.tv_nav_music:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                  //  startActivity(new Intent(this, AudioActivity.class));

                    Dialogue();
                    break;
                case R.id.v_nav_video:
                case R.id.iv_nav_video:
                case R.id.tv_nav_video:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                  //  startActivity(new Intent(this, VideoActivity.class));

                    Dialogue();
                    break;

            }

            //   finish();
        };

        ImageView iv_nav_home = findViewById(R.id.iv_nav_home);
        ImageView iv_nav_fitness = findViewById(R.id.iv_nav_fitness);
        ImageView iv_nav_reward = findViewById(R.id.iv_nav_reward);
        ImageView iv_nav_event = findViewById(R.id.iv_nav_event);
        ImageView iv_nav_booking = findViewById(R.id.iv_nav_booking);
        ImageView iv_nav_history = findViewById(R.id.iv_nav_history);
        ImageView iv_nav_news = findViewById(R.id.iv_nav_news);
        ImageView iv_nav_gallery = findViewById(R.id.iv_nav_gallery);
        ImageView iv_nav_music = findViewById(R.id.iv_nav_music);
        ImageView iv_nav_video = findViewById(R.id.iv_nav_video);
        iv_nav_home.setOnClickListener(onClickListener);
        iv_nav_fitness.setOnClickListener(onClickListener);
        iv_nav_reward.setOnClickListener(onClickListener);
        iv_nav_event.setOnClickListener(onClickListener);
        iv_nav_booking.setOnClickListener(onClickListener);
        iv_nav_history.setOnClickListener(onClickListener);
        iv_nav_news.setOnClickListener(onClickListener);
        iv_nav_gallery.setOnClickListener(onClickListener);
        iv_nav_music.setOnClickListener(onClickListener);
        iv_nav_video.setOnClickListener(onClickListener);

        TextView tv_nav_home = findViewById(R.id.tv_nav_home);
        TextView tv_nav_fitness = findViewById(R.id.tv_nav_fitness);
        TextView tv_nav_reward = findViewById(R.id.tv_nav_reward);
        TextView tv_nav_event = findViewById(R.id.tv_nav_event);
        TextView tv_nav_booking = findViewById(R.id.tv_nav_booking);
        TextView tv_nav_history = findViewById(R.id.tv_nav_history);
        TextView tv_nav_news = findViewById(R.id.tv_nav_news);
        TextView tv_nav_gallery = findViewById(R.id.tv_nav_gallery);
        TextView tv_nav_music = findViewById(R.id.tv_nav_music);
        TextView tv_nav_video = findViewById(R.id.tv_nav_video);
        tv_nav_home.setOnClickListener(onClickListener);
        tv_nav_fitness.setOnClickListener(onClickListener);
        tv_nav_reward.setOnClickListener(onClickListener);
        tv_nav_event.setOnClickListener(onClickListener);
        tv_nav_booking.setOnClickListener(onClickListener);
        tv_nav_history.setOnClickListener(onClickListener);
        tv_nav_news.setOnClickListener(onClickListener);
        tv_nav_gallery.setOnClickListener(onClickListener);
        tv_nav_music.setOnClickListener(onClickListener);
        tv_nav_video.setOnClickListener(onClickListener);

        View v_nav_home = findViewById(R.id.v_nav_home);
        View v_nav_fitness = findViewById(R.id.v_nav_fitness);
        View v_nav_reward = findViewById(R.id.v_nav_reward);
        View v_nav_event = findViewById(R.id.v_nav_event);
        View v_nav_booking = findViewById(R.id.v_nav_booking);
        View v_nav_history = findViewById(R.id.v_nav_history);
        View v_nav_news = findViewById(R.id.v_nav_news);
        View v_nav_gallery = findViewById(R.id.v_nav_gallery);
        View v_nav_music = findViewById(R.id.v_nav_music);
        View v_nav_video = findViewById(R.id.v_nav_video);
        v_nav_home.setOnClickListener(onClickListener);
        v_nav_fitness.setOnClickListener(onClickListener);
        v_nav_reward.setOnClickListener(onClickListener);
        v_nav_event.setOnClickListener(onClickListener);
        v_nav_booking.setOnClickListener(onClickListener);
        v_nav_history.setOnClickListener(onClickListener);
        v_nav_news.setOnClickListener(onClickListener);
        v_nav_gallery.setOnClickListener(onClickListener);
        v_nav_music.setOnClickListener(onClickListener);
        v_nav_video.setOnClickListener(onClickListener);
    }

    public void Dialogue(){

        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.comming_soon))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public void PreHistory(){

        RelativeLayout iv_af_nav_01 = findViewById(R.id.iv_af_nav_01);
        RelativeLayout iv_af_nav_02 = findViewById(R.id.iv_af_nav_02);
        RelativeLayout iv_af_nav_03 = findViewById(R.id.iv_af_nav_03);
        RelativeLayout iv_af_nav_04 = findViewById(R.id.iv_af_nav_04);
        RelativeLayout iv_af_nav_05 = findViewById(R.id.iv_af_nav_05);
        RelativeLayout iv_af_nav_06 = findViewById(R.id.iv_af_nav_06);
        RelativeLayout iv_af_nav_07 = findViewById(R.id.iv_af_nav_07);
        RelativeLayout iv_af_nav_08 = findViewById(R.id.iv_af_nav_08);
        RelativeLayout iv_af_nav_09 = findViewById(R.id.iv_af_nav_09);

        iv_af_nav_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",1);
                startActivity(intent);
            }
        });

        iv_af_nav_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",2);
                startActivity(intent);
            }
        });

        iv_af_nav_03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",3);
                startActivity(intent);
            }
        });

        iv_af_nav_04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",4);
                startActivity(intent);
            }
        });

        iv_af_nav_05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",5);
                startActivity(intent);
            }
        });
        iv_af_nav_06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",6);
                startActivity(intent);
            }
        });

        iv_af_nav_07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",7);
                startActivity(intent);
            }
        });

        iv_af_nav_08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FitnessHistory.this,ExerciseReportActivity.class);
                intent.putExtra("activity_name",8);
                startActivity(intent);
            }
        });

        iv_af_nav_09.setOnClickListener(view -> startActivity(new Intent(FitnessHistory.this, SleepReportActivity.class)));

    }
}
