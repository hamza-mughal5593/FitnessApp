package com.mtechsoft.fitmy.v1.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.mtechsoft.fitmy.BuildConfig;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsProfileActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.ExerciseActiveActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.activity.landing.LanguagePrefActivity;
import com.mtechsoft.fitmy.v1.activity.setting.FAQActivity;
import com.mtechsoft.fitmy.v1.activity.setting.TncActivity;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.CacheHelper;
import com.mtechsoft.fitmy.v1.common.Constants;

public class HomeActivity extends AppCompatActivity {

    private CacheHelper cacheHelper;
    ImageView imageView, iv_ah_avatar;
    private LinearLayout navLayout;
    private View coverView;
    String name, total_points = "", fitness_points = "";
    TextView tv_ah_name, tv_ah_point_counter;
String fitpoints;
    RelativeLayout image_rel;

    Bitmap bitmap;
    InputStream imageStream;
    Bitmap selected_img_bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_ah_name = findViewById(R.id.tvUserName);
        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
        iv_ah_avatar = findViewById(R.id.iv_ah_avatar);
        image_rel = findViewById(R.id.cv_ah_avatar);
        tv_ah_point_counter = findViewById(R.id.tv_ah_point_counter);
        name = Utilities.getString(HomeActivity.this, "user_name");
        fitpoints = Utilities.getString(HomeActivity.this, "fitness_points");

//        Double aDouble = Double.valueOf(Utilities.getString(HomeActivity.this, "fitness_points"));
//        double roundOff = Math.round(aDouble * 100.0) / 100.0;

        tv_ah_point_counter.setText(fitpoints);


        tv_ah_name.setText(name);
        cacheHelper = new CacheHelper(this);

        loadCachedActivity();
        prepareNavBar();
        prepareProfile1();
        prepareList();
        setVersionLabel();


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


        String user_image = Utilities.getString(HomeActivity.this,"profile_image");

        Picasso.get()
                .load(user_image)
                .into(iv_ah_avatar);

        image_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(HomeActivity.this, "hoja uplaod anni deya", Toast.LENGTH_SHORT).show();
//                new ImagePicker.Builder(HomeActivity.this)
//                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
//                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
//                        .directory(ImagePicker.Directory.DEFAULT)
//                        .extension(ImagePicker.Extension.PNG)
//                        .scale(600, 600)
//                        .allowMultipleImages(false)
//                        .enableDebuggingMode(true)
//                        .build();

                doProfilePhoto();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

//        prepareProfile();
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

    public void Dialogue() {

        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.comming_soon))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void prepareProfile() {


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(Constants.SP_USER_PROFILE, "");

        try {
            JSONObject jsonObject = new JSONObject(profileJson);
            String user_id = jsonObject.getString("id");
            String full_name = jsonObject.getString("full_name");
            total_points = jsonObject.getString("total_points");
            String gender = jsonObject.getString("gender");
            String photo = jsonObject.getString("photo");

            //ImageView iv_ah_setting = findViewById(R.id.iv_ah_setting);

            iv_ah_avatar.setOnClickListener(view -> {


//                doProfilePhoto();
            });

//            iv_ah_setting.setOnClickListener(view -> {
//                goSetting();
//            });

//            tv_ah_name.setText(name);
//            tv_ah_point_counter.setText(total_points);

            if (!gender.equalsIgnoreCase("M")) {
                iv_ah_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar_girl));
            }

//            new DownloadImageTask(iv_ah_avatar).execute(photo);

            byte[] imgBytes = cacheHelper.getCache("profile", user_id);
            if (imgBytes == null) {
                String[] fileParams = {photo, user_id, "profile"};
                new DownloadImageCacheTask(iv_ah_avatar).execute(fileParams);
            } else {
                Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                iv_ah_avatar.setImageBitmap(bitmap);
            }

        } catch (JSONException ex) {
            // TODO
        }
    }


    private void doProfilePhoto() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropMenuCropButtonTitle("Done")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setRequestedSize(400, 400)
                .start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri imageUri = result.getUri();

                iv_ah_avatar.setImageURI(imageUri);

                    uploadImage(imageUri);


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadImage(Uri ima ) {

        final IOSDialog dialog0 = new IOSDialog.Builder(HomeActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        try {

            selected_img_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),ima);

//            selected_img_bitmap = MediaStore.Images.Media.getBitmap(HomeActivity.this.getContentResolver(), ima);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap immagex = selected_img_bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        String input = imageEncoded;
        input = input.replace("\n", "");
        input = input.trim();
        input = "data:image/png;base64,"+input;


        int user_id = Utilities.getInt(HomeActivity.this, "user_id");

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("image",input);
        postParam.put("user_id", String.valueOf(user_id));


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "update_profile_image", HomeActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            String message = jsonObject.getString("message");
                            String data = jsonObject.getString("data");

                            Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();

                            Utilities.saveString(HomeActivity.this,"profile_image",data);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {

                    Log.i("error", "" + ERROR);
                    dialog0.dismiss();

                    Utilities.hideProgressDialog();
                }
            }
        });





    }

//    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
//        ImageView bmImage;
//
//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String urldisplay = urls[0];
//            Bitmap mIcon11 = null;
//            try {
//                InputStream in = new java.net.URL(urldisplay).openStream();
//                mIcon11 = BitmapFactory.decodeStream(in);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            return mIcon11;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//            if (result != null)
//                bmImage.setImageBitmap(result);
//        }
//    }

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
//
//    private void nearbyProgs(){
//        LinearLayout ll_ah_events = findViewById(R.id.ll_ah_events);
//        LayoutInflater inflater = getLayoutInflater();
//        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);
//        ll_ah_events.addView(rowView);
//        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
//        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
//        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
//        tv_da_ef_li_01.setText(R.string.title1);
//        tv_da_ef_li_02.setText(R.string.desp1);
//        iv_da_ef_li_01.setImageResource(R.drawable.program_terdekat_1);
//
//        rowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomeActivity.this, NewsDetailsActivity.class);
//                intent.putExtra("t1",1);
//                startActivity(intent);
//            }
//        });
//
//        LayoutInflater inflater1 = getLayoutInflater();
//        View rowView1 = inflater1.inflate(R.layout.listitem_dashboard_home, null, true);
//        ll_ah_events.addView(rowView1);
//        TextView tv_da_ef_li_01_1 = rowView1.findViewById(R.id.tv_da_ef_li_01);
//        TextView tv_da_ef_li_02_2 = rowView1.findViewById(R.id.tv_da_ef_li_02);
//        ImageView iv_da_ef_li_01_1 = rowView1.findViewById(R.id.iv_da_ef_li_01);
//        tv_da_ef_li_01_1.setText(R.string.title2);
//        tv_da_ef_li_02_2.setText(R.string.desp2);
//        iv_da_ef_li_01_1.setImageResource(R.drawable.program_terdekat_2);
//
//        rowView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomeActivity.this, NewsDetailsActivity.class);
//                intent.putExtra("t2",2);
//                startActivity(intent);
//            }
//        });
//    }
//    private void prepareNews() {
//        LinearLayout ll_ah_news = findViewById(R.id.ll_ah_news);
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
//        HttpHelper.TOKEN = token;
//
//        new Thread() {
//            @Override
//            public void run() {
//                JSONArray responseArray = HttpHelper.getNews();
//
//                try {
//                    for (int i = 0; i < responseArray.length(); i++) {
//                        JSONObject tempObj = responseArray.getJSONObject(i);
//                        String tempObjStr = tempObj.toString();
//
//                        LayoutInflater inflater = getLayoutInflater();
//                        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);
//
//                        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
//                        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
//                        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
//
//                        tv_da_ef_li_01.setText(tempObj.getString("title"));
////                new DownloadImageTask(iv_da_ef_li_01).execute(tempObj.getString("cover_image"));
//
//                        byte[] imgBytes = cacheHelper.getCache("news", tempObj.getString("id"));
//                        if (imgBytes == null) {
//                            String[] fileParams = {tempObj.getString("cover_image"), tempObj.getString("id"), "news"};
//                            new DownloadImageCacheTask(iv_da_ef_li_01).execute(fileParams);
//                        } else {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
//                            iv_da_ef_li_01.setImageBitmap(bitmap);
//                        }
//
//                        String body = tempObj.getString("body");
//                        tv_da_ef_li_02.setText(body);
//
//                        rowView.setOnClickListener(view -> {
//                            goNewsDetails(tempObjStr);
//                        });
//
//                        runOnUiThread(() -> ll_ah_news.addView(rowView));
//                    }
//                } catch (JSONException ex) {
//                    // TODO
//                    String exx = ex.getMessage();
//                }
//            }
//        }.start();
//
//        LayoutInflater inflater1 = getLayoutInflater();
//        View rowView1 = inflater1.inflate(R.layout.listitem_dashboard_home, null, true);
//
//        TextView tv_da_ef_li_01_1 = rowView1.findViewById(R.id.tv_da_ef_li_01);
//        TextView tv_da_ef_li_02_1 = rowView1.findViewById(R.id.tv_da_ef_li_02);
//        ImageView iv_da_ef_li_01_1 = rowView1.findViewById(R.id.iv_da_ef_li_01);
//        ll_ah_news.addView(rowView1);
//
//        tv_da_ef_li_01_1.setText(R.string.title3);
//        tv_da_ef_li_02_1.setText(R.string.desp3);
//
//        rowView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomeActivity.this,NewsDetailsActivity.class);
//                intent.putExtra("t3",3);
//                startActivity(intent);
//            }
//        });
//
//    }
//
//    private void goNewsDetails(String jsonStr) {
//        Intent intent = new Intent(this, NewsDetailsActivity.class);
//        intent.putExtra(Constants.BUNDLE_TEMP_JSON, jsonStr);
//        startActivity(intent);
//    }
//
//    private void prepareCommunity() {
//        LinearLayout ll_ah_news = findViewById(R.id.ll_ah_community);
//
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
//        HttpHelper.TOKEN = token;
//
//        new Thread() {
//            @Override
//            public void run() {
//                JSONArray responseArray = HttpHelper.getNewsCommunity();
//
//                try {
//                    for (int i = 0; i < responseArray.length(); i++) {
//                        JSONObject tempObj = responseArray.getJSONObject(i);
//                        String tempObjStr = tempObj.toString();
//
//                        LayoutInflater inflater = getLayoutInflater();
//                        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);
//
//                        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
//                        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
//                        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
//
//                        tv_da_ef_li_01.setText(tempObj.getString("title"));
////                new DownloadImageTask(iv_da_ef_li_01).execute(tempObj.getString("cover_image"));
//
//                        byte[] imgBytes = cacheHelper.getCache("community", tempObj.getString("id"));
//                        if (imgBytes == null) {
//                            String[] fileParams = {tempObj.getString("cover_image"), tempObj.getString("id"), "community"};
//                            new DownloadImageCacheTask(iv_da_ef_li_01).execute(fileParams);
//                        } else {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
//                            iv_da_ef_li_01.setImageBitmap(bitmap);
//                        }
//
//                        String body = tempObj.getString("body");
//                        tv_da_ef_li_02.setText(body);
//
//                        rowView.setOnClickListener(view -> {
//                            goCommunityDetails(tempObjStr);
//                        });
//
//                        runOnUiThread(() -> ll_ah_news.addView(rowView));
//                    }
//                } catch (JSONException ex) {
//                    // TODO
//                    String exx = ex.getMessage();
//                }
//            }
//        }.start();
//
//
//        LayoutInflater inflater1 = getLayoutInflater();
//        View rowView1 = inflater1.inflate(R.layout.listitem_dashboard_home, null, true);
//
//        TextView tv_da_ef_li_01_1 = rowView1.findViewById(R.id.tv_da_ef_li_01);
//        TextView tv_da_ef_li_02_1 = rowView1.findViewById(R.id.tv_da_ef_li_02);
//        ImageView iv_da_ef_li_01_1 = rowView1.findViewById(R.id.iv_da_ef_li_01);
//        ll_ah_news.addView(rowView1);
//
//        tv_da_ef_li_01_1.setText(R.string.title4);
//        tv_da_ef_li_02_1.setText(R.string.desp4);
//
//        rowView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(HomeActivity.this, NewsDetailsActivity.class);
//                intent.putExtra("t4",4);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void goCommunityDetails(String jsonStr) {
//        Intent intent = new Intent(this, NewsDetailsActivity.class);
//        intent.putExtra(Constants.BUNDLE_TEMP_JSON, jsonStr);
//        startActivity(intent);
//    }
//
//    private void prepareEvents() {
//        LinearLayout ll_ah_events = findViewById(R.id.ll_ah_events);
//
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
//        HttpHelper.TOKEN = token;
//
//        new Thread() {
//            @Override
//            public void run() {
//                JSONArray responseArray = HttpHelper.getEvents();
//
//                for (int i = 0; i < responseArray.length(); i++) {
//                    try {
//                        JSONObject tempObj = responseArray.getJSONObject(i);
//
//                        EventObj eventObj = new EventObj();
//                        eventObj.setId(tempObj.getString("id"));
//                        eventObj.setName(tempObj.getString("name"));
//                        eventObj.setCover_image(tempObj.getString("cover_image"));
//                        eventObj.setVenue(tempObj.getString("venue"));
//
//                        String rewardObjJsonStr = tempObj.toString();
//
//                        LayoutInflater inflater = getLayoutInflater();
//                        View rowView = inflater.inflate(R.layout.listitem_dashboard_home, null, true);
//
//                        TextView tv_da_ef_li_01 = rowView.findViewById(R.id.tv_da_ef_li_01);
//                        TextView tv_da_ef_li_02 = rowView.findViewById(R.id.tv_da_ef_li_02);
//                        ImageView iv_da_ef_li_01 = rowView.findViewById(R.id.iv_da_ef_li_01);
//
//                        tv_da_ef_li_01.setText(eventObj.getName());
//                        tv_da_ef_li_02.setText(eventObj.getVenue());
////                        new DownloadImageTask(iv_da_ef_li_01).execute(eventObj.getCover_image());
//
//                        byte[] imgBytes = cacheHelper.getCache("event", eventObj.getId());
//                        if (imgBytes == null) {
//                            String[] fileParams = {eventObj.getCover_image(), eventObj.getId(), "event"};
//                            new DownloadImageCacheTask(iv_da_ef_li_01).execute(fileParams);
//                        } else {
//                            Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
//                            iv_da_ef_li_01.setImageBitmap(bitmap);
//                        }
//
//                        rowView.setOnClickListener(view1 -> goEventDetails(rewardObjJsonStr));
//
//                        runOnUiThread(() -> ll_ah_events.addView(rowView));
//
//                    } catch (JSONException ex) {
//                        // TODO
//                    }
//                }
//            }
//        }.start();
//    }
//
//    private void goEventDetails(String rewardObjJsonStr) {
//        Intent intent = new Intent(this, EventDetailsActivity.class);
//        intent.putExtra(Constants.BUNDLE_TEMP_JSON, rewardObjJsonStr);
//        startActivity(intent);
//    }

    private void loadCachedActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        boolean isActivityActive = sharedPreferences.getBoolean(Constants.SP_ACTIVITY_ACTIVE, false);
        if (isActivityActive) {
            startActivity(new Intent(this, ExerciseActiveActivity.class));
        }
    }


    private void prepareProfile1() {


        String user_image = Utilities.getString(HomeActivity.this,"profile_image");
        Picasso.get()
                .load(user_image)
                .into(iv_ah_avatar);


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String profileJson = sharedPreferences.getString(Constants.SP_USER_PROFILE, "");

        try {
            JSONObject jsonObject = new JSONObject(profileJson);
            String full_name = jsonObject.getString("full_name");
            String total_points = jsonObject.getString("total_points");
            String gender = jsonObject.getString("gender");
            String photo = jsonObject.getString("photo");

            TextView tv_ah_name = findViewById(R.id.tv_ah_name);
            iv_ah_avatar = findViewById(R.id.iv_ah_avatar);

            iv_ah_avatar.setOnClickListener(view -> {
                startActivity(new Intent(this, AsProfileActivity.class));
            });

            tv_ah_name.setText(name);

            if (!gender.equalsIgnoreCase("M")) {
                iv_ah_avatar.setImageDrawable(getResources().getDrawable(R.drawable.ic_avatar_girl));
            }

            new DownloadImageTask1(iv_ah_avatar).execute(photo);

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
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.commit();

        Utilities.clearSharedPref(HomeActivity.this);

//        SharedPreferences mPref = getSharedPreferences("Sunday", MODE_PRIVATE);
//        SharedPreferences.Editor editor = mPref.edit();
//        editor.clear();
//        editor.apply();
//
//        SharedPreferences mPrefs = getSharedPreferences("Monday", MODE_PRIVATE);
//        SharedPreferences.Editor editorr = mPrefs.edit();
//        editorr.clear();
//        editorr.apply();
//
//        SharedPreferences mPrefss = getSharedPreferences("Tuesday", MODE_PRIVATE);
//        SharedPreferences.Editor editorrr = mPrefss.edit();
//        editorrr.clear();
//        editorrr.apply();
//
//        SharedPreferences mPrefsss = getSharedPreferences("Wednesday", MODE_PRIVATE);
//        SharedPreferences.Editor editorrrrr = mPrefsss.edit();
//        editorrrrr.clear();
//        editorrrrr.apply();
//
//        SharedPreferences mPrefssss = getSharedPreferences("Thursday", MODE_PRIVATE);
//        SharedPreferences.Editor editorrrrrr = mPrefssss.edit();
//        editorrrrrr.clear();
//        editorrrrrr.apply();
//
//        SharedPreferences mPrefsssss = getSharedPreferences("Friday", MODE_PRIVATE);
//        SharedPreferences.Editor editorrrrrrr = mPrefsssss.edit();
//        editorrrrrrr.clear();
//        editorrrrrrr.apply();
//
//        SharedPreferences mPrefssssss = getSharedPreferences("Saturday", MODE_PRIVATE);
//        SharedPreferences.Editor editorrrrrrrr = mPrefssssss.edit();
//        editorrrrrrrr.clear();
//        editorrrrrrrr.apply();

        startActivity(new Intent(this, LanguagePrefActivity.class));
        finish();

//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    private void goProfile() {
        startActivity(new Intent(this, AsProfileActivity.class));
    }

    private void goFAQ() {
        startActivity(new Intent(this, FAQActivity.class));
    }

    private class DownloadImageTask1 extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask1(ImageView bmImage) {
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
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/forms/d/e/1FAIpQLSfLXFPL600uUzwZmABlaDZLrDntb22uj1RdR_vGhRKmXuwv3Q/viewform?vc=0&c=0&w=1"));
        startActivity(browserIntent);
    }

    private void goTNC() {
        startActivity(new Intent(this, TncActivity.class));
    }
}
