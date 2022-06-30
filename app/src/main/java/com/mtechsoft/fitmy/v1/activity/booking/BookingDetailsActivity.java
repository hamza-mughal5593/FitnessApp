package com.mtechsoft.fitmy.v1.activity.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.common.CacheHelper;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.Utils;

public class BookingDetailsActivity extends AppCompatActivity {

    private CacheHelper cacheHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);

        cacheHelper = new CacheHelper(this);

        Intent intent = getIntent();
        String BUNDLE_TEMP_JSON = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON);
        String listKemudahanVenueStr = Utils.getStringFromRawResource(getResources(), R.raw.list_kemudahan_venue);


        try {
            JSONObject jsonObject = new JSONObject(BUNDLE_TEMP_JSON);
            String pengurusan_kemudahan_venue_id = jsonObject.getString("pengurusan_kemudahan_venue_id");

            Utilities.saveString(BookingDetailsActivity.this,"complex_id",pengurusan_kemudahan_venue_id);

            String nama_venue = jsonObject.getString("nama_venue");
            String alamat = String.format("%s,%s,%s", jsonObject.getString("alamat_1"), jsonObject.getString("alamat_2"), jsonObject.getString("alamat_3")).replace(",,", "").replace(" ,", ",").replace(",", ", ").replace("  ", " ");

            TextView tv_abd_name = findViewById(R.id.tv_abd_name);
            TextView tv_abd_address = findViewById(R.id.tv_abd_address);
            tv_abd_name.setText(nama_venue);
            tv_abd_address.setText(alamat);


//            ImageView iv_abd_cover = findViewById(R.id.iv_abd_cover);
//            Button b_adb_book = findViewById(R.id.b_adb_book);
//
//            b_adb_book.setOnClickListener(view -> {
//                goSchedule(BUNDLE_TEMP_JSON);
//            });
////            new DownloadImageTask(iv_abd_cover).execute(cover_image);

            LinearLayout ll_ab_list = findViewById(R.id.ll_ab_list);

            if (pengurusan_kemudahan_venue_id != null) {
                JSONObject listKemudahanVenueObj = new JSONObject(listKemudahanVenueStr);
                JSONArray listKemudahanVenueArray = listKemudahanVenueObj.getJSONArray("data");

                for (int i = 0; i < listKemudahanVenueArray.length(); i++) {
                    JSONObject tempObj = listKemudahanVenueArray.getJSONObject(i);
                    String pengurusan_kemudahan_venue_id_ref = tempObj.getString("pengurusan_kemudahan_venue_id");

                    if (pengurusan_kemudahan_venue_id_ref != null) {
                        if (pengurusan_kemudahan_venue_id.equalsIgnoreCase(pengurusan_kemudahan_venue_id_ref)) {

                            JSONObject refSukanRekreasi = tempObj.getJSONObject("refSukanRekreasi");
                            String refSukanRekreasiDesc = refSukanRekreasi.getString("desc");

                            JSONObject refJenisKemudahan = tempObj.getJSONObject("refJenisKemudahan");
                            String refJenisKemudahanDesc = refJenisKemudahan.getString("desc");

                            String pengurusan_kemudahan_sedia_ada_id = tempObj.getString("pengurusan_kemudahan_sedia_ada_id");
                            String idPhoto = String.format("%s_%s", pengurusan_kemudahan_venue_id_ref, pengurusan_kemudahan_sedia_ada_id);

                            String gambar_1 = tempObj.getString("gambar_1");
                            String gambar_2 = tempObj.getString("gambar_2");
                            String gambar_3 = tempObj.getString("gambar_3");
                            String gambar_4 = tempObj.getString("gambar_4");
                            String gambar_5 = tempObj.getString("gambar_5");

                            String objJsonStr = tempObj.toString();

                            LayoutInflater inflater = getLayoutInflater();
                            View rowView = inflater.inflate(R.layout.listitem_booking_facilities, null, true);

                            ImageView iv_bf_li_01 = rowView.findViewById(R.id.iv_bf_li_01);
                            TextView tv_bf_li_01 = rowView.findViewById(R.id.tv_bf_li_01);
                            TextView tv_bf_li_02 = rowView.findViewById(R.id.tv_bf_li_02);

                            rowView.setOnClickListener(view -> {
                                goSchedule(objJsonStr);
                            });

                            tv_bf_li_01.setText(refJenisKemudahanDesc);
                            tv_bf_li_02.setText(refSukanRekreasiDesc);

                            String gambar = "https://internal.mysysdemo.com/backend/web/";
                            if (gambar_1.length() > 0) {
                                gambar += gambar_1;
                            } else if (gambar_2.length() > 0) {
                                gambar += gambar_2;
                            } else if (gambar_3.length() > 0) {
                                gambar += gambar_3;
                            } else if (gambar_4.length() > 0) {
                                gambar += gambar_4;
                            } else if (gambar_5.length() > 0) {
                                gambar += gambar_5;
                            }

                            Log.d("gambar", gambar);

                            byte[] imgBytes = cacheHelper.getCache("booking", idPhoto);
                            if (imgBytes == null) {
                                String[] fileParams = {gambar, idPhoto, "booking"};
                                new DownloadImageCacheTask(iv_bf_li_01).execute(fileParams);
                            } else {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                                iv_bf_li_01.setImageBitmap(bitmap);
                            }

                            runOnUiThread(() -> ll_ab_list.addView(rowView));
                        }
                    }
                }
            }

        } catch (JSONException ex) {
            // TODO
            String e = ex.getMessage();
        }
    }

    public void goSchedule(String objJsonStr) {
        Intent intent = new Intent(this, BookingLoginActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, objJsonStr);
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
