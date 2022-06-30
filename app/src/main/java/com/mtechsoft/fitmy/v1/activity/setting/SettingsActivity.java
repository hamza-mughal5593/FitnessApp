package com.mtechsoft.fitmy.v1.activity.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import com.mtechsoft.fitmy.BuildConfig;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.landing.LanguagePrefActivity;
import com.mtechsoft.fitmy.v1.activity.profile.ProfileActivity;
import com.mtechsoft.fitmy.v1.common.Constants;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        prepareProfile();
        prepareList();
        setVersionLabel();

    }


    private void prepareProfile() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(Constants.SP_USER_PROFILE, "");

        try {
            JSONObject jsonObject = new JSONObject(profileJson);
            String full_name = jsonObject.getString("full_name");
            String total_points = jsonObject.getString("total_points");
            String gender = jsonObject.getString("gender");
            String photo = jsonObject.getString("photo");

            TextView tv_ah_name = findViewById(R.id.tv_ah_name);
            ImageView iv_ah_avatar = findViewById(R.id.iv_ah_avatar);

            iv_ah_avatar.setOnClickListener(view -> {
                startActivity(new Intent(this, ProfileActivity.class));
            });

            tv_ah_name.setText(full_name);

            if (!gender.equalsIgnoreCase("M")) {
                iv_ah_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar_girl));
            }

            new DownloadImageTask(iv_ah_avatar).execute(photo);

        } catch (JSONException ex) {
            // TODO
        }
    }

    private void prepareList() {
        View v_as_personal = findViewById(R.id.v_as_personal);
        View v_as_faq = findViewById(R.id.v_as_faq);
        View v_as_signout = findViewById(R.id.v_as_signout);
        View v_as_feedback = findViewById(R.id.v_as_feedback);
        View v_as_tnc = findViewById(R.id.v_as_tnc);

        v_as_personal.setOnClickListener(view -> {
            goProfile();
        });

        v_as_faq.setOnClickListener(view -> {
            goFAQ();
        });

        v_as_signout.setOnClickListener(view -> {
            doLogout();
        });

        v_as_feedback.setOnClickListener(view -> {
            doFeedback();
        });

        v_as_tnc.setOnClickListener(view -> {
            goTNC();
        });
    }

    private void setVersionLabel() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        TextView tv_am_version_01 = findViewById(R.id.tv_am_version_01);
        tv_am_version_01.setText(String.format("%s (%s)", versionName, String.valueOf(versionCode)));
    }

    private void doLogout() {

        Utilities.clearSharedPref(SettingsActivity.this);

        startActivity(new Intent(this, LanguagePrefActivity.class));
        finish();
    }

    private void goProfile() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    private void goFAQ() {
        startActivity(new Intent(this, FAQActivity.class));
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
            if (result != null)
                bmImage.setImageBitmap(result);
        }
    }

    private void doFeedback() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://forms.gle/sZHSCvsQxVc1XqoH6"));
        startActivity(browserIntent);
    }

    private void goTNC() {
        startActivity(new Intent(this, TncActivity.class));
    }
}
