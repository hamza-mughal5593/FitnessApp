package com.mtechsoft.fitmy.v1.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.event.EventDetailsActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.EventObj;
import com.mtechsoft.fitmy.v1.common.HttpHelper;
import com.mtechsoft.fitmy.v1.common.Utils;

public class EventHistoryActivity extends AppCompatActivity {

    private JSONArray responseArray;
    private LinearLayout ll_aeh_list;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history);

        prepareNavBar2();
        prepareList();
        prepareSearchBar();
        prepareTab();

        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);

        imageView = findViewById(R.id.imageView);

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
    }

    private void prepareNavBar2() {
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
//                    startActivity(new Intent(this, AudioActivity.class));

                    Dialogue();
                    break;
                case R.id.v_nav_video:
                case R.id.iv_nav_video:
                case R.id.tv_nav_video:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);

//                    startActivity(new Intent(this, VideoActivity.class));

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


    private void prepareNavBar() {
        View.OnClickListener onClickListener = view -> {
            switch (view.getId()) {
                case R.id.v_nav_home:
                case R.id.iv_nav_home:
                case R.id.tv_nav_home:
                    startActivity(new Intent(this, FitnessActivity.class));
                    break;
                case R.id.v_nav_fitness:
                case R.id.iv_nav_fitness:
                case R.id.tv_nav_fitness:
                    startActivity(new Intent(this, BookingActivity.class));
                    break;
                case R.id.v_nav_reward:
                case R.id.iv_nav_reward:
                case R.id.tv_nav_reward:
                    startActivity(new Intent(this, RewardActivity.class));
                    break;
                case R.id.v_nav_event:
                case R.id.iv_nav_event:
                case R.id.tv_nav_event:
                    startActivity(new Intent(this, AsEventActivity.class));
                    break;
                case R.id.v_nav_booking:
                case R.id.iv_nav_booking:
                case R.id.tv_nav_booking:
                    startActivity(new Intent(this, HomeActivity.class));
                    break;

                case R.id.v_nav_history:
                case R.id.iv_nav_history:
                case R.id.tv_nav_history:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, FitnessHistory.class));
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
//                    startActivity(new Intent(this, AudioActivity.class));

                    Dialogue();
                    break;
                case R.id.v_nav_video:
                case R.id.iv_nav_video:
                case R.id.tv_nav_video:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);

//                    startActivity(new Intent(this, VideoActivity.class));

                    Dialogue();
                    break;
            }

            finish();
        };

        ImageView iv_nav_home = findViewById(R.id.iv_nav_home);
        ImageView iv_nav_fitness = findViewById(R.id.iv_nav_fitness);
        ImageView iv_nav_reward = findViewById(R.id.iv_nav_reward);
        ImageView iv_nav_event = findViewById(R.id.iv_nav_event);
        ImageView iv_nav_booking = findViewById(R.id.iv_nav_booking);

        View v_nav_history = findViewById(R.id.v_nav_history);
        v_nav_history.setOnClickListener(onClickListener);


        iv_nav_home.setOnClickListener(onClickListener);
        iv_nav_fitness.setOnClickListener(onClickListener);
        iv_nav_reward.setOnClickListener(onClickListener);
//        iv_nav_event.setOnClickListener(onClickListener);
        iv_nav_booking.setOnClickListener(onClickListener);

        TextView tv_nav_home = findViewById(R.id.tv_nav_home);
        TextView tv_nav_fitness = findViewById(R.id.tv_nav_fitness);
        TextView tv_nav_reward = findViewById(R.id.tv_nav_reward);
        TextView tv_nav_event = findViewById(R.id.tv_nav_event);
        TextView tv_nav_booking = findViewById(R.id.tv_nav_booking);
        ImageView iv_nav_gallery = findViewById(R.id.iv_nav_gallery);
        ImageView iv_nav_music = findViewById(R.id.iv_nav_music);
        ImageView iv_nav_video = findViewById(R.id.iv_nav_video);

        iv_nav_gallery.setOnClickListener(onClickListener);
        iv_nav_music.setOnClickListener(onClickListener);
        iv_nav_video.setOnClickListener(onClickListener);
        tv_nav_home.setOnClickListener(onClickListener);
        tv_nav_fitness.setOnClickListener(onClickListener);
        tv_nav_reward.setOnClickListener(onClickListener);
//        tv_nav_event.setOnClickListener(onClickListener);
        tv_nav_booking.setOnClickListener(onClickListener);

        View v_nav_home = findViewById(R.id.v_nav_home);
        View v_nav_fitness = findViewById(R.id.v_nav_fitness);
        View v_nav_reward = findViewById(R.id.v_nav_reward);
        View v_nav_event = findViewById(R.id.v_nav_event);
        View v_nav_booking = findViewById(R.id.v_nav_booking);
        v_nav_home.setOnClickListener(onClickListener);
        v_nav_fitness.setOnClickListener(onClickListener);
        v_nav_reward.setOnClickListener(onClickListener);
//        v_nav_event.setOnClickListener(onClickListener);
        v_nav_booking.setOnClickListener(onClickListener);
    }

    public void Dialogue(){

        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.comming_soon))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void prepareList() {
        ll_aeh_list = findViewById(R.id.ll_aeh_list);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
        HttpHelper.TOKEN = token;

        new Thread() {
            @Override
            public void run() {
                responseArray = HttpHelper.getInterestEvents();

                for (int i = 0; i < responseArray.length(); i++) {
                    try {
                        JSONObject tempObj = responseArray.getJSONObject(i);

                        EventObj eventObj = new EventObj();
                        eventObj.setId(tempObj.getString("id"));
                        eventObj.setName(tempObj.getString("name"));
                        eventObj.setCover_image(tempObj.getString("cover_image"));
                        eventObj.setEvent_datetime(tempObj.getString("event_datetime"));
                        eventObj.setVenue(tempObj.getString("venue"));
                        eventObj.setAbout_desc(tempObj.getString("about_desc"));
                        eventObj.setAbout_image(tempObj.getString("about_image"));
                        eventObj.setReward_desc(tempObj.getString("reward_desc"));
                        eventObj.setReward_image(tempObj.getString("reward_image"));
                        eventObj.setEo_id(tempObj.getString("eo_id"));
                        eventObj.setEo_name(tempObj.getString("eo_name"));

                        String rewardObjJsonStr = tempObj.toString();

                        LayoutInflater inflater = getLayoutInflater();
                        View rowView = inflater.inflate(R.layout.listitem_dashboard_event, null, true);

                        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
                        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
                        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
                        TextView tv_da_ef_li_04 = rowView.findViewById(R.id.tv_da_ef_li_04);
                        Button b_da_ef_li_01 = rowView.findViewById(R.id.b_da_ef_li_01);

                        tv_da_ef_li_01.setText(eventObj.getName());
                        tv_da_ef_li_02.setText(eventObj.getVenue());
                        tv_da_ef_li_04.setText(Utils.getDate(eventObj.getEvent_datetime()));
                        b_da_ef_li_01.setOnClickListener(view1 -> goDetails(rewardObjJsonStr));
                        new DownloadImageTask(iv_da_ef_li_01).execute(eventObj.getCover_image());

                        runOnUiThread(() -> ll_aeh_list.addView(rowView));

                    } catch (JSONException ex) {
                        // TODO
                    }
                }
            }
        }.start();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    private void goDetails(String rewardObjJsonStr) {
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, rewardObjJsonStr);
        startActivity(intent);
    }

    private void prepareSearchBar() {
        EditText et_aeh_search = findViewById(R.id.et_aeh_search);
        TextView tv_aeh_search = findViewById(R.id.tv_aeh_search);

        tv_aeh_search.setOnClickListener(view -> {
            doSearch(et_aeh_search.getText().toString());
        });
    }

    private void doSearch(String keyword) {
        ll_aeh_list.removeAllViews();

        for (int i = 0; i < responseArray.length(); i++) {
            try {
                JSONObject tempObj = responseArray.getJSONObject(i);

                EventObj eventObj = new EventObj();
                eventObj.setId(tempObj.getString("id"));
                eventObj.setName(tempObj.getString("name"));
                eventObj.setCover_image(tempObj.getString("cover_image"));
                eventObj.setEvent_datetime(tempObj.getString("event_datetime"));
                eventObj.setVenue(tempObj.getString("venue"));
                eventObj.setAbout_desc(tempObj.getString("about_desc"));
                eventObj.setAbout_image(tempObj.getString("about_image"));
                eventObj.setReward_desc(tempObj.getString("reward_desc"));
                eventObj.setReward_image(tempObj.getString("reward_image"));
                eventObj.setEo_id(tempObj.getString("eo_id"));
                eventObj.setEo_name(tempObj.getString("eo_name"));

                String objJsonStr = tempObj.toString();

                LayoutInflater inflater = getLayoutInflater();
                View rowView = inflater.inflate(R.layout.listitem_dashboard_event, null, true);

                ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
                TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
                TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
                TextView tv_da_ef_li_04 = rowView.findViewById(R.id.tv_da_ef_li_04);
                Button b_da_ef_li_01 = rowView.findViewById(R.id.b_da_ef_li_01);

                tv_da_ef_li_01.setText(eventObj.getName());
                tv_da_ef_li_02.setText(eventObj.getVenue());
                tv_da_ef_li_04.setText(Utils.getDate(eventObj.getEvent_datetime()));
                b_da_ef_li_01.setOnClickListener(view1 -> goDetails(objJsonStr));
                new DownloadImageTask(iv_da_ef_li_01).execute(eventObj.getCover_image());

                if (keyword.length() == 0) {
                    ll_aeh_list.addView(rowView);
                } else {
                    if (eventObj.getName().toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                        ll_aeh_list.addView(rowView);
                    }
                }


            } catch (JSONException ex) {
                // TODO
            }
        }
    }

    private void prepareTab() {
        TextView tv_aeh_all_rewards = findViewById(R.id.tv_aeh_all_rewards);
        tv_aeh_all_rewards.setOnClickListener(view -> {
            startActivity(new Intent(this, AsEventActivity.class));
            finish();
        });
    }
}
