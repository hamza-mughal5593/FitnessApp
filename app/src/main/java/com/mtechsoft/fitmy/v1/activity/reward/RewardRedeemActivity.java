package com.mtechsoft.fitmy.v1.activity.reward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.RewardObj;

public class RewardRedeemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_redeem);

        Intent intent = getIntent();
        String BUNDLE_TEMP_JSON = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON);
        String BUNDLE_TEMP_JSON_2 = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON_2);

        ImageView iv_rra_01 = findViewById(R.id.iv_rra_01);
        ImageView iv_rra_02 = findViewById(R.id.iv_rra_02);
        ImageView iv_rra_03 = findViewById(R.id.iv_rra_03);

        TextView tv_rra_01 = findViewById(R.id.tv_rra_01);
        TextView tv_rra_02 = findViewById(R.id.tv_rra_02);

        try {
            JSONObject tempObj = new JSONObject(BUNDLE_TEMP_JSON);
            RewardObj rewardObj = new RewardObj();
            rewardObj.setMy_name(tempObj.getString("my_name"));
            rewardObj.setMy_description(tempObj.getString("my_description"));
            rewardObj.setEn_name(tempObj.getString("en_name"));
            rewardObj.setEn_description(tempObj.getString("en_description"));
            rewardObj.setStart_datetime(tempObj.getString("start_datetime"));
            rewardObj.setEnd_datetime(tempObj.getString("end_datetime"));
            rewardObj.setRv_name(tempObj.getString("rv_name"));
            rewardObj.setRv_logo(tempObj.getString("rv_logo"));
            rewardObj.setRv_phone_num(tempObj.getString("rv_phone_num"));
            rewardObj.setRv_address(tempObj.getString("rv_address"));
            rewardObj.setRc_id(tempObj.getString("rc_id"));
            rewardObj.setRc_en_name(tempObj.getString("rc_en_name"));
            rewardObj.setRc_my_name(tempObj.getString("rc_my_name"));
            rewardObj.setPoints("0");


            new DownloadImageTask(iv_rra_01).execute(rewardObj.getRv_logo());
            new DownloadImageTask(iv_rra_02).execute(rewardObj.getRv_logo());

            tv_rra_01.setText(rewardObj.getRv_name());
            tv_rra_02.setText(rewardObj.getMy_name());

            JSONObject redeemObj = new JSONObject(BUNDLE_TEMP_JSON_2);
            String codeId = redeemObj.getString("id");

            String url = String.format("%s?redeem_id=%s", Constants.API_REWARD_REDEEM_VALIDATE_CODE, codeId);
            QRGEncoder qrgEncoder = new QRGEncoder(url, null, QRGContents.Type.TEXT, 200);
            try {
                iv_rra_03.setImageBitmap(qrgEncoder.encodeAsBitmap());
            } catch (WriterException e) {
                Log.v("RRA", e.toString());
            }
        } catch (JSONException ex) {
            // TODO
        }
    }

    public void goBack(View view) {
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
}