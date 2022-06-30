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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.activity.news.NewsDetailsActivity;
import com.mtechsoft.fitmy.v1.common.CacheHelper;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.HttpHelper;

public class NewsActivity extends AppCompatActivity {

    private CacheHelper cacheHelper;

    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        cacheHelper = new CacheHelper(this);

        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);

        navLayout.bringToFront();
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

        prepareNews();
        nearbyProgs();
        prepareCommunity();
        prepareNavBar();
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
                   // startActivity(new Intent(this, AudioActivity.class));

                    Dialogue();
                    break;
                case R.id.v_nav_video:
                case R.id.iv_nav_video:
                case R.id.tv_nav_video:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                   // startActivity(new Intent(this, VideoActivity.class));

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

    private void prepareNews() {
        LinearLayout ll_ah_news = findViewById(R.id.ll_ah_news);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
        HttpHelper.TOKEN = token;

        new Thread() {
            @Override
            public void run() {
                JSONArray responseArray = HttpHelper.getNews();

                try {
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject tempObj = responseArray.getJSONObject(i);
                        String tempObjStr = tempObj.toString();

                        LayoutInflater inflater = getLayoutInflater();
                        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);

                        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
                        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
                        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);

                        tv_da_ef_li_01.setText(tempObj.getString("title"));
//                new DownloadImageTask(iv_da_ef_li_01).execute(tempObj.getString("cover_image"));

                        byte[] imgBytes = cacheHelper.getCache("news", tempObj.getString("id"));
                        if (imgBytes == null) {
                            String[] fileParams = {tempObj.getString("cover_image"), tempObj.getString("id"), "news"};
                            new DownloadImageCacheTask(iv_da_ef_li_01).execute(fileParams);
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                            iv_da_ef_li_01.setImageBitmap(bitmap);
                        }

                        String body = tempObj.getString("body");
                        tv_da_ef_li_02.setText(body);

                        rowView.setOnClickListener(view -> {
                            goNewsDetails(tempObjStr);
                        });

                        runOnUiThread(() -> ll_ah_news.addView(rowView));
                    }
                } catch (JSONException ex) {
                    // TODO
                    String exx = ex.getMessage();
                }
            }
        }.start();

        LayoutInflater inflater1 = getLayoutInflater();
        View rowView1 = inflater1.inflate(R.layout.listitem_dashboard_home, null, true);

        TextView tv_da_ef_li_01_1 = rowView1.findViewById(R.id.tv_da_ef_li_01);
        TextView tv_da_ef_li_02_1 = rowView1.findViewById(R.id.tv_da_ef_li_02);
        ImageView iv_da_ef_li_01_1 = rowView1.findViewById(R.id.iv_da_ef_li_01);
        ll_ah_news.addView(rowView1);

        tv_da_ef_li_01_1.setText(R.string.title3);
        tv_da_ef_li_02_1.setText(R.string.desp3);

        rowView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                intent.putExtra("t3",3);
                startActivity(intent);
            }
        });

    }

    private void goNewsDetails(String jsonStr) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, jsonStr);
        startActivity(intent);
    }

    private void nearbyProgs(){
        LinearLayout ll_ah_events = findViewById(R.id.ll_ah_events);
        LayoutInflater inflater = getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);
        ll_ah_events.addView(rowView);
        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
        tv_da_ef_li_01.setText(R.string.title1);
        tv_da_ef_li_02.setText(R.string.desp1);
        iv_da_ef_li_01.setImageResource(R.drawable.program_terdekat_1);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                intent.putExtra("t1",1);
                startActivity(intent);
            }
        });

        LayoutInflater inflater1 = getLayoutInflater();
        View rowView1 = inflater1.inflate(R.layout.listitem_dashboard_home, null, true);
        ll_ah_events.addView(rowView1);
        TextView tv_da_ef_li_01_1 = rowView1.findViewById(R.id.tv_da_ef_li_01);
        TextView tv_da_ef_li_02_2 = rowView1.findViewById(R.id.tv_da_ef_li_02);
        ImageView iv_da_ef_li_01_1 = rowView1.findViewById(R.id.iv_da_ef_li_01);
        tv_da_ef_li_01_1.setText(R.string.title2);
        tv_da_ef_li_02_2.setText(R.string.desp2);
        iv_da_ef_li_01_1.setImageResource(R.drawable.program_terdekat_2);

        rowView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                intent.putExtra("t2",2);
                startActivity(intent);
            }
        });
    }

    private void prepareCommunity() {
        LinearLayout ll_ah_news = findViewById(R.id.ll_ah_community);

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
        HttpHelper.TOKEN = token;

        new Thread() {
            @Override
            public void run() {
                JSONArray responseArray = HttpHelper.getNewsCommunity();

                try {
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject tempObj = responseArray.getJSONObject(i);
                        String tempObjStr = tempObj.toString();

                        LayoutInflater inflater = getLayoutInflater();
                        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);

                        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
                        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
                        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);

                        tv_da_ef_li_01.setText(tempObj.getString("title"));
//                new DownloadImageTask(iv_da_ef_li_01).execute(tempObj.getString("cover_image"));

                        byte[] imgBytes = cacheHelper.getCache("community", tempObj.getString("id"));
                        if (imgBytes == null) {
                            String[] fileParams = {tempObj.getString("cover_image"), tempObj.getString("id"), "community"};
                            new DownloadImageCacheTask(iv_da_ef_li_01).execute(fileParams);
                        } else {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                            iv_da_ef_li_01.setImageBitmap(bitmap);
                        }

                        String body = tempObj.getString("body");
                        tv_da_ef_li_02.setText(body);

                        rowView.setOnClickListener(view -> {
                            goCommunityDetails(tempObjStr);
                        });

                        runOnUiThread(() -> ll_ah_news.addView(rowView));
                    }
                } catch (JSONException ex) {
                    // TODO
                    String exx = ex.getMessage();
                }
            }
        }.start();


        LayoutInflater inflater1 = getLayoutInflater();
        View rowView1 = inflater1.inflate(R.layout.listitem_dashboard_home, null, true);

        TextView tv_da_ef_li_01_1 = rowView1.findViewById(R.id.tv_da_ef_li_01);
        TextView tv_da_ef_li_02_1 = rowView1.findViewById(R.id.tv_da_ef_li_02);
        ImageView iv_da_ef_li_01_1 = rowView1.findViewById(R.id.iv_da_ef_li_01);
        ll_ah_news.addView(rowView1);

        tv_da_ef_li_01_1.setText(R.string.title4);
        tv_da_ef_li_02_1.setText(R.string.desp4);

        rowView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                intent.putExtra("t4",4);
                startActivity(intent);
            }
        });
    }

    private void goCommunityDetails(String jsonStr) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, jsonStr);
        startActivity(intent);
    }

    private class DownloadImageCacheTask extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;
        public DownloadImageCacheTask(ImageView imageView) {
            this.imageView = imageView;
        }
        protected Bitmap doInBackground(String... urls) {
            String fileUrl = urls[0];
            String fileId = urls[1];
            String fileSuffix = urls[2];

            try {
                InputStream is = new java.net.URL(fileUrl).openStream();

                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = is.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }

                buffer.flush();
                byte[] byteArray = buffer.toByteArray();

                String filePath = cacheHelper.putCache(fileSuffix, fileId, byteArray);
                Log.d("DownloadImageCacheTask", filePath);

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                return bitmap;

            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
        protected void onPostExecute(Bitmap result) {
            if (result != null) imageView.setImageBitmap(result);
        }
    }

}
