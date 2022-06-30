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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.booking.BookingDetailsActivity;
import com.mtechsoft.fitmy.v1.activity.booking.BookingLoginActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.common.CacheHelper;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.Utils;

public class BookingActivity extends AppCompatActivity {

    private JSONArray responseArray;
    private LinearLayout ll_ab_list;
    private CacheHelper cacheHelper;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;
    RelativeLayout booking_logout;
    private SharedPreferences sharedPreferences;



    ArrayList<Integer> integers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        cacheHelper = new CacheHelper(this);

        prepareNavBar();
        prepareList("WP Kuala Lumpur");
        prepareStateChoice();
        prepareSearchBar();
        prepareTab();
        clearCart();

        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
//        booking_logout = findViewById(R.id.booking_logout);

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

//        booking_logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.clear();
//                editor.apply();
//
//                startActivity(new Intent(BookingActivity.this, BookingLoginActivity.class));
//                finish();
//
//
//            }
//        });
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

    public void Dialogue(){

        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.comming_soon))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }
    private void prepareList(String selectedValue) {
        ll_ab_list = findViewById(R.id.ll_ab_list);
        ll_ab_list.removeAllViews();

        new Thread() {
            @Override
            public void run() {
                String stateIcon = "";
                String stateCode = "14"; // WP Kuala Lumpur
                switch (selectedValue) {
                    case "WP Kuala Lumpur":
                        stateCode = "14";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/wp.png";
                        break;
                    case "WP Putrajaya":
                        stateCode = "16";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/wp.png";
                        break;
                    case "WP Labuan":
                        stateCode = "15";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/wp.png";
                        break;
                    case "Perlis":
                        stateCode = "9";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/perlis.png";
                        break;
                    case "Kedah":
                        stateCode = "2";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/kedah.png";
                        break;
                    case "Selangor":
                        stateCode = "10";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/selangor.png";
                        break;
                    case "Johor":
                        stateCode = "1";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/johor.png";
                        break;
                    case "Negeri Sembilan":
                        stateCode = "5";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/sembilan.png";
                        break;
                    case "Pahang":
                        stateCode = "6";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/pahang.png";
                        break;
                    case "Melaka":
                        stateCode = "4";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/melaka.png";
                        break;
                    case "Sabah":
                        stateCode = "12";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/sabah.png";
                        break;
                    case "Sarawak":
                        stateCode = "13";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/serawak.png";
                        break;
                    case "Perak":
                        stateCode = "8";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/perak.png";
                        break;
                    case "Kelantan":
                        stateCode = "3";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/kelantan.png";
                        break;
                    case "Terengganu":
                        stateCode = "11";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/terengganu.png";
                        break;
                    case "Pulau Pinang":
                        stateCode = "7";
                        stateIcon = Constants.API_EFASILITI_BASE_URL + "/backend/web/img/states/penang.png";
                        break;
                }

                try {
                    String arrayStr = Utils.getStringFromRawResource(getResources(), R.raw.list_venue);
                    JSONObject responseObj = new JSONObject(arrayStr);
                    responseArray = responseObj.getJSONArray("data");

                    for (int i = 0; i < responseArray.length(); i++) {


                        JSONObject tempObj = responseArray.getJSONObject(i);
                        String alamat_negeri = tempObj.getString("alamat_negeri");

                        if (alamat_negeri.equalsIgnoreCase(stateCode)) {
                            String nama_venue = tempObj.getString("nama_venue");
                            String alamat = String.format("%s,%s,%s", tempObj.getString("alamat_1"), tempObj.getString("alamat_2"), tempObj.getString("alamat_3")).replace(",,", "").replace(" ,", ",").replace(",", ", ").replace("  ", " ");
                            int id = tempObj.getInt("pengurusan_kemudahan_venue_id");

                            integers.add(id);



                            String rewardObjJsonStr = tempObj.toString();


                            LayoutInflater inflater = getLayoutInflater();
                            View rowView = inflater.inflate(R.layout.listitem_booking_facilities, null, true);

                            ImageView iv_bf_li_01 = rowView.findViewById(R.id.iv_bf_li_01);
                            TextView tv_bf_li_01 = rowView.findViewById(R.id.tv_bf_li_01);
                            TextView tv_bf_li_02 = rowView.findViewById(R.id.tv_bf_li_02);

                            tv_bf_li_01.setText(nama_venue);
                            tv_bf_li_02.setText(alamat);



                            rowView.setOnClickListener(view1 -> goDetails(rewardObjJsonStr));

                            ll_ab_list.indexOfChild(rowView);


                            byte[] imgBytes = cacheHelper.getCache("state", stateCode);
                            if (imgBytes == null) {
                                String[] fileParams = {stateIcon, stateCode, "state"};
                                new DownloadImageCacheTask(iv_bf_li_01).execute(fileParams);
                            } else {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                                iv_bf_li_01.setImageBitmap(bitmap);
                            }


                            runOnUiThread(() -> ll_ab_list.addView(rowView));
                        }
                    }
                } catch (JSONException ex) {
                    // TODO
                    String e = ex.getMessage();
                }
            }
        }.start();
    }


    private void goDetails(String rewardObjJsonStr) {
        Intent intent = new Intent(this, BookingDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, rewardObjJsonStr);
        startActivity(intent);
    }

    private void prepareSearchBar() {
        EditText et_ab_search = findViewById(R.id.et_ab_search);
        TextView tv_ab_search = findViewById(R.id.tv_ab_search);

        tv_ab_search.setOnClickListener(view -> {
            doSearch(et_ab_search.getText().toString());
        });
    }

    private void prepareStateChoice() {
        Spinner s_ab_state = findViewById(R.id.s_ab_state);
        s_ab_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                String selectedValue = adapterView.getItemAtPosition(pos).toString();
                prepareList(selectedValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void doSearch(String keyword) {
        ll_ab_list.removeAllViews();

//        try {
//            String arrayStr = Utils.getStringFromRawResource(getResources(), R.raw.facilities_listing);
//            responseArray = new JSONArray(arrayStr);
//
//            for (int i = 0; i < responseArray.length(); i++) {
//
//
//                JSONObject tempObj = responseArray.getJSONObject(i);
//
//                FacilityObj facilityObj = new FacilityObj();
//                facilityObj.setId(tempObj.getString("id"));
//                facilityObj.setName(tempObj.getString("name"));
//                facilityObj.setAddress(tempObj.getString("address"));
//                facilityObj.setPhone_num(tempObj.getString("phone_num"));
//                facilityObj.setEmail(tempObj.getString("email"));
//                facilityObj.setCover_image(tempObj.getString("cover_image"));
//
//                String rewardObjJsonStr = tempObj.toString();
//
//                LayoutInflater inflater = getLayoutInflater();
//                View rowView = inflater.inflate(R.layout.listitem_dashboard_booking, null, true);
//
//                ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
//                TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
//                TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
//                Button b_da_ef_li_01 = rowView.findViewById(R.id.b_da_ef_li_01);
//
//                tv_da_ef_li_01.setText(facilityObj.getName());
//                tv_da_ef_li_02.setText(facilityObj.getAddress());
//                b_da_ef_li_01.setOnClickListener(view1 -> goDetails(rewardObjJsonStr));
//                new DownloadImageTask(iv_da_ef_li_01).execute(facilityObj.getCover_image());
//
//                if (keyword.length() == 0) {
//                    ll_ab_list.addView(rowView);
//                } else {
//                    if (facilityObj.getName().toLowerCase().indexOf(keyword.toLowerCase()) != -1) {
//                        ll_ab_list.addView(rowView);
//                    }
//                }
//            }
//        } catch (JSONException ex) {
//            // TODO
//        }
    }

    private void prepareTab() {
        TextView tv_ab_history = findViewById(R.id.tv_ab_history);
        tv_ab_history.setOnClickListener(view -> {
            goBookingHistory();
        });
    }

    private void goBookingHistory() {
        startActivity(new Intent(this, BookingHistoryActivity.class));
        finish();
    }

    private void clearCart() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.SP_EFASILITI_CART).commit();
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
