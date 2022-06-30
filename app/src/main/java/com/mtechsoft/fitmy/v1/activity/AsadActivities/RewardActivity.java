package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.All_Rewards;
import com.mtechsoft.fitmy.v1.Models.Reward_Category;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.HomeActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.RewardHistoryActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.activity.reward.RewardDetailsActivity;
import com.mtechsoft.fitmy.v1.adapter.AllRewardsAdapter;
import com.mtechsoft.fitmy.v1.adapter.Reward_Categories_Adapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.CacheHelper;

public class RewardActivity extends AppCompatActivity {

    private CacheHelper cacheHelper;
    private LinearLayout ll_ae_list, ll_ar_list;
    private View v_ar_food, v_ar_sports_facilities, v_ar_sports_equipments, v_ar_home;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    ArrayList<Reward_Category> reward_categories;
    ArrayList<All_Rewards> all_rewards;
    RecyclerView reward_recyler, all_reward_recycler;
    TextView history, tv_ar_point_counter;
    String fitness_point="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        fitness_point = Utilities.getString(RewardActivity.this, "fitness_points");



        cacheHelper = new CacheHelper(this);
        try {

            tv_ar_point_counter = findViewById(R.id.tv_ar_point_counter);
            Double aDouble = Double.valueOf(fitness_point);
            double roundOff = Math.round(aDouble * 100.0) / 100.0;
            tv_ar_point_counter.setText(fitness_point);

        } catch (Exception ex) {
            ex.printStackTrace();

    Toast.makeText(RewardActivity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
        inti();

        prepareNavBar();
//        prepareList();
//        prepareTab();
//        prepareFilter();reward_categories_rv
//

        TextView textView2 = findViewById(R.id.textView2);
        TextView textView3 = findViewById(R.id.textView3);
        textView2.bringToFront();
        textView3.bringToFront();

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


        getrewardCategoriesApi();

        getAllRewards();
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RewardActivity.this, RewardHistoryActivity.class));
            }
        });
//        HandleClicks();

        reward_categories = new ArrayList<>();

        Reward_Category reward_categoryy = new Reward_Category();
        reward_categoryy.setE_cat_name("All");
        reward_categories.add(0, reward_categoryy);

    }

    private void getrewardCategoriesApi() {

        final IOSDialog dialog0 = new IOSDialog.Builder(RewardActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();
        int user_is = Utilities.getInt(RewardActivity.this, "user_id");

        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("user_id",String.valueOf(user_is));


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "get_reward_categories", RewardActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();
//                    Utilities.hideProgressDialog();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {


                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                Reward_Category reward_categoryy = new Reward_Category();


                                int r_id = jsonObject1.getInt("id");
                                String name = jsonObject1.getString("name");
                                String filename = jsonObject1.getString("filename");


                                reward_categoryy.setId(r_id);
                                reward_categoryy.setE_cat_name(name);
                                reward_categoryy.setE_cat_image(filename);

                                reward_categories.add(reward_categoryy);

                            }

                            Reward_Categories_Adapter reward_categories_adapter = new Reward_Categories_Adapter(reward_categories, RewardActivity.this, new Reward_Categories_Adapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Reward_Category item) {

                                    if (item.getE_cat_name().equals("All")) {

                                        getAllRewards();

                                    } else {

                                        getrewardList(String.valueOf(item.getId()));

                                    }


                                }
                            });
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RewardActivity.this, RecyclerView.HORIZONTAL, false);
                            reward_recyler.setLayoutManager(layoutManager);
                            reward_recyler.setAdapter(reward_categories_adapter);

                            getrewardList(String.valueOf(reward_categories.get(0).getId()));

                        } else if (status == 400) {

                            dialog0.dismiss();

                            AlertDialog.Builder builder1 = new AlertDialog.Builder(RewardActivity.this);
                            builder1.setMessage("No Reward Assigned to you");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();
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

    private void getrewardList(final String rew_idd) {




        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("reward_id",rew_idd);
        postParam.put("reward_category_id", rew_idd);

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "get_reward", RewardActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {


                    all_rewards = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                All_Rewards all_rewardss = new All_Rewards();

                                int id = jsonObject1.getInt("id");
                                String name = jsonObject1.getString("reward_name");
                                String filename = jsonObject1.getString("filename");
                                String reward_description = jsonObject1.getString("reward_description");
                                String term_and_conditions = jsonObject1.getString("term_and_conditions");
                                String required_fitness_points = jsonObject1.getString("required_fitness_points");

                                all_rewardss.setId(id);
                                all_rewardss.setImage(filename);
                                all_rewardss.setName(name);
                                all_rewardss.setDescription(reward_description);
                                all_rewardss.setTerm_and_condition(term_and_conditions);
                                all_rewardss.setRequired_fitness_points(required_fitness_points);

                                all_rewards.add(all_rewardss);

                            }

                            AllRewardsAdapter allRewardsAdapter = new AllRewardsAdapter(all_rewards, RewardActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(RewardActivity.this);
                            all_reward_recycler.setLayoutManager(layoutManager2);
                            all_reward_recycler.setAdapter(allRewardsAdapter);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }
        });

    }

    private void getAllRewards() {


        Map<String, String> postParam = new HashMap<String, String>();

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.GET, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "get_all_rewards", RewardActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {


                    all_rewards = new ArrayList<>();
                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i = jsonArray.length() - 1; i >= 0 ; i--) {

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                All_Rewards all_rewardss = new All_Rewards();

                                int id = jsonObject1.getInt("id");
                                String name = jsonObject1.getString("reward_name");
                                String filename = jsonObject1.getString("filename");
                                String reward_description = jsonObject1.getString("reward_description");
                                String term_and_conditions = jsonObject1.getString("term_and_conditions");
                                String required_fitness_points = jsonObject1.getString("required_fitness_points");

                                all_rewardss.setId(id);
                                all_rewardss.setImage(filename);
                                all_rewardss.setName(name);
                                all_rewardss.setDescription(reward_description);
                                all_rewardss.setTerm_and_condition(term_and_conditions);
                                all_rewardss.setRequired_fitness_points(required_fitness_points);

                                all_rewards.add(all_rewardss);

                            }

                            AllRewardsAdapter allRewardsAdapter = new AllRewardsAdapter(all_rewards, RewardActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(RewardActivity.this);
                            all_reward_recycler.setLayoutManager(layoutManager2);
                            all_reward_recycler.setAdapter(allRewardsAdapter);

                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                }
            }
        });

    }


    private void inti() {

        all_rewards = new ArrayList<>();
        reward_categories = new ArrayList<>();
        reward_recyler = findViewById(R.id.reward_categories_rv);
        all_reward_recycler = findViewById(R.id.reward_rv);
        history = findViewById(R.id.history);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Double aDouble = Double.valueOf(Utilities.getString(RewardActivity.this, "fitness_points"));
        double roundOff = Math.round(aDouble * 100.0) / 100.0;
        tv_ar_point_counter.setText(Utilities.getString(RewardActivity.this, "fitness_points"));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
                    //startActivity(new Intent(this, VideoActivity.class));

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

}
