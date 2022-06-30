package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.All_Events;
import com.mtechsoft.fitmy.v1.Models.Events_Category;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.EventHistoryActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.HomeActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.ExerciseReportActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.adapter.AllEventsAdapter;
import com.mtechsoft.fitmy.v1.adapter.Event_Categories_Adapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.Constants;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.CacheHelper;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class AsEventActivity extends AppCompatActivity {

    private JSONArray responseArray;
    private LinearLayout ll_ae_list;
    private CacheHelper cacheHelper;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    ArrayList<Events_Category> events_categories;
    ArrayList<All_Events> all_events;
    RecyclerView cat_recyler, all_events_recycler;
    AutoCompleteTextView searc_et;

    RelativeLayout search;

    ArrayList<String> language = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_event);

        cacheHelper = new CacheHelper(this);

        inti();


        prepareNavBar();
        prepareTab();


        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
        navLayout.setVisibility(View.GONE);
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


        events_categories = new ArrayList<>();

        Events_Category events_categorytt = new Events_Category();
        events_categorytt.setE_cat_name("All");

        events_categories.add(0, events_categorytt);


        getEventsCategoriesApi();
        List_of_All_events();


        //Creating the instance of ArrayAdapter containing list of language names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_spinner_item,language);
        //Getting the instance of AutoCompleteTextView
        AutoCompleteTextView actv =  (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        actv.setTextColor(Color.BLACK);

        search.setOnClickListener(view -> {


            String query = searc_et.getText().toString();

            if (query.isEmpty()) {

                Toast.makeText(this, "Enter some text", Toast.LENGTH_SHORT).show();
            } else {

                SearchAPi(query);
            }

        });


    }

    private void SearchAPi(final String word) {


        final IOSDialog dialog0 = new IOSDialog.Builder(AsEventActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("event_name", word);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL + "search_events", AsEventActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    all_events = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {

                            dialog0.dismiss();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                All_Events all_events1 = new All_Events();

                                int id = jsonObject1.getInt("id");
                                String image = jsonObject1.getString("filename");
                                String event_name = jsonObject1.getString("event_name");
                                String description = jsonObject1.getString("description");
                                String term_and_conditions = jsonObject1.getString("term_and_conditions");
                                String location = jsonObject1.getString("location");
                                String event_date = jsonObject1.getString("event_date");
                                String registration_url = jsonObject1.getString("registration_url");

                                all_events1.setId(String.valueOf(id));
                                all_events1.setImage(image);
                                all_events1.setName(event_name);
                                all_events1.setDescription(description);
                                all_events1.setTerms_and_condition(term_and_conditions);
                                all_events1.setDate(event_date);
                                all_events1.setLocation(location);
                                all_events1.setUrl(registration_url);

                                all_events.add(all_events1);

                            }

                            AllEventsAdapter allEventsAdapter = new AllEventsAdapter(all_events, AsEventActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(AsEventActivity.this);
                            all_events_recycler.setLayoutManager(layoutManager2);
                            all_events_recycler.setAdapter(allEventsAdapter);
                        } else {


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

                                List_of_All_events();
                                searc_et.setText("");

                                pDialog.dismiss();
                            }
                        }
                )
                .show();
    }


    private void getEventsCategoriesApi() {


        final IOSDialog dialog0 = new IOSDialog.Builder(AsEventActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();

        HashMap<String, String> headers = new HashMap<String, String>();


        ApiModelClass.GetApiResponse(Request.Method.GET, Constants.URL.BASE_URL + "get_event_categories", AsEventActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                Events_Category events_categoryy = new Events_Category();

                                int id = jsonObject1.getInt("id");
                                String name = jsonObject1.getString("name");
                                String filename = jsonObject1.getString("filename");

                                events_categoryy.setCat_id(id);
                                events_categoryy.setE_cat_name(name);
                                events_categoryy.setE_cat_image(filename);

                                events_categories.add(i + 1, events_categoryy);
                            }

                            All_events(String.valueOf(events_categories.get(0).getCat_id()));

                            Event_Categories_Adapter event_categories_adapter = new Event_Categories_Adapter(events_categories, AsEventActivity.this, new Event_Categories_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Events_Category item) {

                                    if (item.getE_cat_name().equals("All")) {

                                        List_of_All_events();
                                        searc_et.setText("");

                                    } else {


                                        String cat_idd = String.valueOf(item.getCat_id());

                                        All_events(cat_idd);
                                    }


                                }
                            });


                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AsEventActivity.this, RecyclerView.HORIZONTAL, false);
                            cat_recyler.setLayoutManager(layoutManager);
                            cat_recyler.setAdapter(event_categories_adapter);

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

    private void List_of_All_events() {

//        Utilities.showProgressDialog(AsEventActivity.this,"Wait...");


        final IOSDialog dialog0 = new IOSDialog.Builder(AsEventActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        Map<String, String> postParam = new HashMap<String, String>();

        HashMap<String, String> headers = new HashMap<String, String>();


        ApiModelClass.GetApiResponse(Request.Method.GET, Constants.URL.BASE_URL + "get_all_events", AsEventActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    all_events = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {

                            dialog0.dismiss();
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = jsonArray.length() - 1; i >=0 ; i--) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                All_Events all_events1 = new All_Events();

                                int id = jsonObject1.getInt("id");
                                String image = jsonObject1.getString("filename");
                                String event_name = jsonObject1.getString("event_name");
                                String description = jsonObject1.getString("description");
                                String term_and_conditions = jsonObject1.getString("term_and_conditions");
                                String location = jsonObject1.getString("location");
                                String event_date = jsonObject1.getString("event_date");
                                String registration_url = jsonObject1.getString("registration_url");


                                all_events1.setId(String.valueOf(id));
                                all_events1.setImage(image);
                                all_events1.setName(event_name);
                                all_events1.setDescription(description);
                                all_events1.setTerms_and_condition(term_and_conditions);
                                all_events1.setDate(event_date);
                                all_events1.setLocation(location);
                                all_events1.setUrl(registration_url);

                                all_events.add(all_events1);

                                language.add(event_name);

                            }

                            AllEventsAdapter allEventsAdapter = new AllEventsAdapter(all_events, AsEventActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(AsEventActivity.this);
                            all_events_recycler.setLayoutManager(layoutManager2);
                            all_events_recycler.setAdapter(allEventsAdapter);
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


    private void All_events(final String catId) {

//        Utilities.showProgressDialog(AsEventActivity.this,"Wait...");

        final IOSDialog dialog0 = new IOSDialog.Builder(AsEventActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("event_category_id", catId);

        HashMap<String, String> headers = new HashMap<String, String>();


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL + "get_events", AsEventActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    all_events = new ArrayList<>();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {


                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                All_Events all_events1 = new All_Events();

                                String image = jsonObject1.getString("filename");
                                String event_name = jsonObject1.getString("event_name");
                                String description = jsonObject1.getString("description");
                                String term_and_conditions = jsonObject1.getString("term_and_conditions");
                                String location = jsonObject1.getString("location");
                                String event_date = jsonObject1.getString("event_date");
                                String registration_url = jsonObject1.getString("registration_url");

                                all_events1.setImage(image);
                                all_events1.setName(event_name);
                                all_events1.setDescription(description);
                                all_events1.setTerms_and_condition(term_and_conditions);
                                all_events1.setDate(event_date);
                                all_events1.setLocation(location);
                                all_events1.setUrl(registration_url);

                                all_events.add(all_events1);

                            }

                            AllEventsAdapter allEventsAdapter = new AllEventsAdapter(all_events, AsEventActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(AsEventActivity.this);
                            all_events_recycler.setLayoutManager(layoutManager2);
                            all_events_recycler.setAdapter(allEventsAdapter);
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


    private void inti() {

        all_events = new ArrayList<>();
        events_categories = new ArrayList<>();
        cat_recyler = findViewById(R.id.event_categories_rvvvv);
        all_events_recycler = findViewById(R.id.events_rvvvv);
        searc_et = findViewById(R.id.autoCompleteTextView);
        search = findViewById(R.id.search_rel);
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


    private void prepareTab() {
        TextView tv_ae_history = findViewById(R.id.tv_ae_history);
        tv_ae_history.setOnClickListener(view -> {
            startActivity(new Intent(this, EventHistoryActivity.class));
            finish();
        });
    }

}
