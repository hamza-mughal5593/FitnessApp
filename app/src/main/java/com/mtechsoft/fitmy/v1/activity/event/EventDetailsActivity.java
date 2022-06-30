package com.mtechsoft.fitmy.v1.activity.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.EventObj;
import com.mtechsoft.fitmy.v1.common.HttpHelper;
import com.mtechsoft.fitmy.v1.common.Utils;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        Intent intent = getIntent();
        String BUNDLE_TEMP_JSON = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON);

        try {
            JSONObject tempObj = new JSONObject(BUNDLE_TEMP_JSON);

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

            TextView tv_eda_03 = findViewById(R.id.tv_eda_03);
            TextView tv_eda_01 = findViewById(R.id.tv_eda_01);
            TextView tv_eda_05 = findViewById(R.id.tv_eda_05);
            TextView tv_eda_07 = findViewById(R.id.tv_eda_07);
            TextView tv_eda_09 = findViewById(R.id.tv_eda_09);
            TextView tv_eda_11 = findViewById(R.id.tv_eda_11);
            TextView tv_eda_13 = findViewById(R.id.tv_eda_13);

            ImageView iv_eda_01 = findViewById(R.id.iv_eda_01);
            ImageView iv_eda_02 = findViewById(R.id.iv_eda_02);
            ImageView iv_eda_03 = findViewById(R.id.iv_eda_03);


            new DownloadImageTask(iv_eda_01).execute(eventObj.getAbout_image());
            new DownloadImageTask(iv_eda_02).execute(eventObj.getReward_image());
            new DownloadImageTask(iv_eda_03).execute(eventObj.getCover_image());

            tv_eda_01.setText(eventObj.getName());
            tv_eda_03.setText(eventObj.getEo_name());
            tv_eda_05.setText(Utils.getDate(eventObj.getEvent_datetime()));
            tv_eda_07.setText(Utils.getTime(eventObj.getEvent_datetime()));
            tv_eda_09.setText(eventObj.getVenue());
            tv_eda_11.setText(eventObj.getAbout_desc());
            tv_eda_13.setText(eventObj.getReward_desc().replaceAll("null", ""));

            Button b_aed_register = findViewById(R.id.b_aed_register);
            b_aed_register.setOnClickListener(view -> {
                doRegister(eventObj.getId());
            });

//            v_rda_01.setOnClickListener(view -> goRedeem(BUNDLE_TEMP_JSON));
        } catch (JSONException ex) {
            // TODO
        }
    }

    public void goBack(View v) {
        finish();
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

    private void doRegister(String item_id) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);

        HttpHelper.TOKEN = token;

        new Thread() {
            @Override
            public void run() {
                JSONObject responseObj = HttpHelper.postInterest(item_id);

                if (responseObj != null) {
                    runOnUiThread(() -> {
                        Toast.makeText(EventDetailsActivity.this, R.string.registered, Toast.LENGTH_LONG).show();
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(EventDetailsActivity.this, "Already registered", Toast.LENGTH_LONG).show();
                    });
                }
            }
        }.start();
    }
}
