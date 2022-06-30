package com.mtechsoft.fitmy.v1.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.All_Events;
import com.mtechsoft.fitmy.v1.Models.Events_Category;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.event.EventDetailsActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.activity.media.AudioActivity;
import com.mtechsoft.fitmy.v1.activity.media.VideoActivity;
import com.mtechsoft.fitmy.v1.adapter.AllEventsAdapter;
import com.mtechsoft.fitmy.v1.adapter.Event_Categories_Adapter;
import com.mtechsoft.fitmy.v1.common.CacheHelper;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.EventObj;
import com.mtechsoft.fitmy.v1.common.Utils;

public class EventActivity extends AppCompatActivity {

    private JSONArray responseArray;
    private LinearLayout ll_ae_list;
    private CacheHelper cacheHelper;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    ArrayList<Events_Category> events_categories;
    ArrayList<All_Events> all_events;
    RecyclerView cat_recyler,all_events_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);



        cacheHelper = new CacheHelper(this);

        inti();


        prepareNavBar();
        prepareSearchBar();
        prepareTab();


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


//        events_categories .add(new Events_Category(R.drawable.ic_fma_logo,"Fitness"));
//        events_categories .add(new Events_Category(R.drawable.ic_fma_logo,"Fitness"));
//        events_categories .add(new Events_Category(R.drawable.ic_fma_logo,"Fitness"));
//        events_categories .add(new Events_Category(R.drawable.ic_fma_logo,"Fitness"));
//        events_categories .add(new Events_Category(R.drawable.ic_fma_logo,"Fitness"));


        Event_Categories_Adapter event_categories_adapter = new Event_Categories_Adapter(events_categories, EventActivity.this, new Event_Categories_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Events_Category item) {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        cat_recyler.setLayoutManager(layoutManager);
        cat_recyler.setAdapter(event_categories_adapter);




        AllEventsAdapter allEventsAdapter= new AllEventsAdapter(all_events, EventActivity.this);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        all_events_recycler.setLayoutManager(layoutManager2);
        all_events_recycler.setAdapter(allEventsAdapter);

    }

    private void inti() {

        all_events = new ArrayList<>();
        events_categories = new ArrayList<>();
        cat_recyler = findViewById(R.id.event_categories_rv);
        all_events_recycler= findViewById(R.id.events_rv);
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
                    startActivity(new Intent(this, AudioActivity.class));

//                    Dialogue();
                    break;
                case R.id.v_nav_video:
                case R.id.iv_nav_video:
                case R.id.tv_nav_video:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, VideoActivity.class));

//                    Dialogue();
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

    private void goDetails(String rewardObjJsonStr) {
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, rewardObjJsonStr);
        startActivity(intent);
    }

    private void prepareSearchBar() {
        EditText et_ae_search = findViewById(R.id.et_ae_search);
        TextView tv_ae_search = findViewById(R.id.tv_ae_search);

        tv_ae_search.setOnClickListener(view -> {

//            doSearch(et_ae_search.getText().toString());
        });
    }

    private void doSearch(String keyword) {
        ll_ae_list.removeAllViews();

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
//                new DownloadImageTask(iv_da_ef_li_01).execute(eventObj.getCover_image());

                byte[] imgBytes = cacheHelper.getCache("event", eventObj.getId());
                if (imgBytes == null) {
                    String[] fileParams = {eventObj.getCover_image(), eventObj.getId(), "event"};
                    new DownloadImageCacheTask(iv_da_ef_li_01).execute(fileParams);
                } else {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                    iv_da_ef_li_01.setImageBitmap(bitmap);
                }

                if (keyword.length() == 0) {
                    ll_ae_list.addView(rowView);
                } else {
                    if (eventObj.getName().toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
                        ll_ae_list.addView(rowView);
                    }
                }


            } catch (JSONException ex) {
                // TODO
            }
        }
    }

    private void prepareTab() {

        TextView tv_ae_history = findViewById(R.id.tv_ae_history);
        tv_ae_history.setOnClickListener(view -> {
            startActivity(new Intent(this, EventHistoryActivity.class));
            finish();
        });

    }
}
