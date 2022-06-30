package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.CommunityNews;
import com.mtechsoft.fitmy.v1.Models.NearByEventsAndPrograms;
import com.mtechsoft.fitmy.v1.Models.NewsAndPromotion;
import com.mtechsoft.fitmy.v1.Models.Newss.Item;
import com.mtechsoft.fitmy.v1.Models.Newss.News;
import com.mtechsoft.fitmy.v1.Models.Newss.SubItem;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.HomeActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.ExerciseReportActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.activity.media.AudioActivity;
import com.mtechsoft.fitmy.v1.activity.media.VideoActivity;
import com.mtechsoft.fitmy.v1.adapter.AsNewsAdapter;
import com.mtechsoft.fitmy.v1.adapter.ItemAdapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.Constants;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.CacheHelper;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AsNewsActivity extends AppCompatActivity {

    private CacheHelper cacheHelper;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;


    ArrayList<String> year = new ArrayList<String>();
    List<String> month = new ArrayList<String>();
    ArrayList<String> category = new ArrayList<String>();

    RecyclerView recyclerView1,recyclerView2,recyclerView3;

    ArrayList<NewsAndPromotion> newsAndPromotions;
    ArrayList<NearByEventsAndPrograms> nearByEventsAndPrograms;
    ArrayList<CommunityNews> communityNews;


    TextView news_and_promo,nearbyevents,communityy;

    ArrayList<SubItem> subItems;
    ArrayList<Item> items;
    RecyclerView rvItem;

    Spinner sp_year;
    Spinner sp_month;
    Spinner sp_category;


    ArrayList<News> news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_news);


        init();

        cacheHelper = new CacheHelper(this);

         sp_year = (Spinner) findViewById(R.id.year);
         sp_month = (Spinner) findViewById(R.id.month);
         sp_category = (Spinner) findViewById(R.id.category);
         news = new ArrayList<>();

        String y = getResources().getString(R.string.year);
        year.add(y);


        String m = getResources().getString(R.string.month);
        month.add(m);
        month.add(getResources().getString(R.string.January));
        month.add(getResources().getString(R.string.February));
        month.add(getResources().getString(R.string.March));
        month.add(getResources().getString(R.string.April));
        month.add(getResources().getString(R.string.May));
        month.add(getResources().getString(R.string.Jun));
        month.add(getResources().getString(R.string.July));
        month.add(getResources().getString(R.string.August));
        month.add(getResources().getString(R.string.September));
        month.add(getResources().getString(R.string.October));
        month.add(getResources().getString(R.string.November));
        month.add(getResources().getString(R.string.December));

        String c = getResources().getString(R.string.category);
        category.add(c);
        category.add("All");


        ArrayAdapter<String> yearadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_year.setAdapter(yearadapter);


        ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, month);
        monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_month.setAdapter(monthadapter);


        sp_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String month = sp_month.getSelectedItem().toString();

                if (month.equals("Select Month")){


                }else {

                    searchbymonth(month);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        sp_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String yearr = sp_year.getSelectedItem().toString();

                if (yearr.equals("Select Year")){


                }else {

                    searchbyyear(yearr);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sp_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String cat = sp_category.getSelectedItem().toString();

                if (cat.equals("Select Category")){


                }else if(cat.equals("All")){

                    newsApi();

                }else {

                    searchbycategory(cat);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
        rvItem = findViewById(R.id.news_rv);

        items = new ArrayList<>();
        subItems = new ArrayList<>();

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

        prepareNavBar();

        getyears();

        newsApi();

    }

    private void searchbycategory(String cat) {

        final IOSDialog dialog0 = new IOSDialog.Builder(AsNewsActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("category",cat);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"get_news_by_category", AsNewsActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {


                    news = new ArrayList<>();
                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i< jsonArray.length(); i++){


                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                News newss = new News();

                                int id = jsonObject1.getInt("id");
                                String title = jsonObject1.getString("title");
                                String description = jsonObject1.getString("description");
                                String image = jsonObject1.getString("filename");
                                String new_category_name = jsonObject1.getString("news_category_name");

                                newss.setDescription(description);
                                newss.setId(id);
                                newss.setImage(image);
                                newss.setTitle(title);

                                news.add(newss);

                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(AsNewsActivity.this);
                            AsNewsAdapter itemAdapter = new AsNewsAdapter(news,AsNewsActivity.this);
                            rvItem.setAdapter(itemAdapter);
                            rvItem.setLayoutManager(layoutManager);


//                            ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(AsNewsActivity.this, android.R.layout.simple_spinner_item, category);
//                            monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            sp_category.setAdapter(monthadapter);


                        }else{


                            showemptyDialouge();

                        }

                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });

    }

    private void searchbyyear(String yearr) {

        final IOSDialog dialog0 = new IOSDialog.Builder(AsNewsActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("year",yearr);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"get_news_by_year", AsNewsActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {


                    news = new ArrayList<>();
                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i< jsonArray.length(); i++){


                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                News newss = new News();

                                int id = jsonObject1.getInt("id");
                                String title = jsonObject1.getString("title");
                                String description = jsonObject1.getString("description");
                                String image = jsonObject1.getString("filename");
                                String new_category_name = jsonObject1.getString("news_category_name");

                                newss.setDescription(description);
                                newss.setId(id);
                                newss.setImage(image);
                                newss.setTitle(title);

                                news.add(newss);

                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(AsNewsActivity.this);
                            AsNewsAdapter itemAdapter = new AsNewsAdapter(news,AsNewsActivity.this);
                            rvItem.setAdapter(itemAdapter);
                            rvItem.setLayoutManager(layoutManager);


                        }else{


                            showemptyDialouge();

                        }

                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });








    }


    private void searchbymonth(String month) {

        final IOSDialog dialog0 = new IOSDialog.Builder(AsNewsActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("month",month);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"get_news_by_month", AsNewsActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {


                   news = new ArrayList<>();
                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i< jsonArray.length(); i++){


                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                News newss = new News();

                                int id = jsonObject1.getInt("id");
                                String title = jsonObject1.getString("title");
                                String description = jsonObject1.getString("description");
                                String image = jsonObject1.getString("filename");
                                String new_category_name = jsonObject1.getString("news_category_name");

                                newss.setDescription(description);
                                newss.setId(id);
                                newss.setImage(image);
                                newss.setTitle(title);


                                news.add(newss);

                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(AsNewsActivity.this);
                            AsNewsAdapter itemAdapter = new AsNewsAdapter(news,AsNewsActivity.this);
                            rvItem.setAdapter(itemAdapter);
                            rvItem.setLayoutManager(layoutManager);


//                            ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(AsNewsActivity.this, android.R.layout.simple_spinner_item, category);
//                            monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            sp_category.setAdapter(monthadapter);


                        }else{


                            showemptyDialouge();

                        }

                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });





    }

    private void getyears() {

        final IOSDialog dialog0 = new IOSDialog.Builder(AsNewsActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.GET, Constants.URL.BASE_URL+"get_years_list", AsNewsActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i< jsonArray.length(); i++){


                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                String yearname = jsonObject1.getString("year");

                                year.add(yearname);



                            }

                            ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(AsNewsActivity.this, android.R.layout.simple_spinner_item, year);
                            monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_year.setAdapter(monthadapter);




                        }else{


                            showemptyDialouge();

                        }

                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });





    }

    private void newsApi() {

        final IOSDialog dialog0 = new IOSDialog.Builder(AsNewsActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.GET, Constants.URL.BASE_URL+"get_all_news", AsNewsActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();
                    category = new ArrayList<>();
                    news = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){


                            category.add(getString(R.string.category));
                            category.add("All");

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i< jsonArray.length(); i++){


                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                News newss = new News();

                                int id = jsonObject1.getInt("id");
                                String title = jsonObject1.getString("title");
                                String description = jsonObject1.getString("description");
                                String image = jsonObject1.getString("filename");
                                String new_category_name = jsonObject1.getString("news_category_name");

                                newss.setDescription(description);
                                newss.setId(id);
                                newss.setImage(image);
                                newss.setTitle(title);

                                category.add(new_category_name);

                                news.add(newss);

                            }

                            LinearLayoutManager layoutManager = new LinearLayoutManager(AsNewsActivity.this);
                            AsNewsAdapter itemAdapter = new AsNewsAdapter(news,AsNewsActivity.this);
                            rvItem.setAdapter(itemAdapter);
                            rvItem.setLayoutManager(layoutManager);


                            ArrayAdapter<String> monthadapter = new ArrayAdapter<String>(AsNewsActivity.this, android.R.layout.simple_spinner_item, category);
                            monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_category.setAdapter(monthadapter);






                        }else{


                            showemptyDialouge();

                        }

                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });



    }

    private void showemptyDialouge() {

        PrettyDialog pDialog = new PrettyDialog(this);


        pDialog
                .setTitle(getResources().getString(R.string.no_data))
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorOrange)
                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.colorOrange,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {

                                pDialog.dismiss();

                            }
                        }
                )
                .show();
    }
    private void init() {

        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
        news_and_promo = findViewById(R.id.textView15);
        nearbyevents = findViewById(R.id.textView16);
        communityy = findViewById(R.id.textView17);

        newsAndPromotions = new ArrayList<>();
        nearByEventsAndPrograms = new ArrayList<>();
        communityNews = new ArrayList<>();

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

}
