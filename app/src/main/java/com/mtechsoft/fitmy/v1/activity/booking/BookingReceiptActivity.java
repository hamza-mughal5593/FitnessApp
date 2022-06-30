package com.mtechsoft.fitmy.v1.activity.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;
import com.mtechsoft.fitmy.v1.common.Constants;

public class BookingReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_receipt);

        Intent intent = getIntent();
        String BUNDLE_TEMP_JSON = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON);

        try {
            JSONObject jsonObject = new JSONObject(BUNDLE_TEMP_JSON);
            String name = jsonObject.getString("name");
            String cover_image = jsonObject.getString("cover_image");

            TextView tv_abs_name = findViewById(R.id.tv_abs_name);
            ImageView iv_abs_cover = findViewById(R.id.iv_abs_cover);
            Button b_abs_book = findViewById(R.id.b_abs_book);

            tv_abs_name.setText(name);
            b_abs_book.setOnClickListener(view -> {
                goFinish(BUNDLE_TEMP_JSON);
            });
            new DownloadImageTask(iv_abs_cover).execute(cover_image);

        } catch (JSONException ex) {
            // TODO
        }
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

    private void goFinish(String objJsonStr) {
        finishAffinity();
        startActivity(new Intent(this, BookingActivity.class));
    }
}
