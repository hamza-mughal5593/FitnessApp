package com.mtechsoft.fitmy.v1.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.garmin.android.connectiq.ConnectIQ;
import com.garmin.android.connectiq.IQDevice;
import com.garmin.android.connectiq.exception.InvalidStateException;
import com.garmin.android.connectiq.exception.ServiceUnavailableException;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.CaloriesReportActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.NewStepsReportActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.PdfViewActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.SelectComplexActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.ExerciseReportActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.activity.fitness.SleepReportActivity;
import com.mtechsoft.fitmy.v1.adapter.IQDeviceAdapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.DateStepsModel;
import com.mtechsoft.fitmy.v1.common.StepsDBHelper;

public class FitnessActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private int steps;



    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    private ConnectIQ mConnectIQ;
    private TextView mEmptyView;
    private IQDeviceAdapter mAdapter;
    private boolean mSdkReady = false;
    public static final String APP_TAG = "SimpleHealth";

    int cal = 0;
    int stepss = 0;
    int dist = 0;

    TextView tv_af_steps_counter;
    TextView tv_af_distance_counter;
    TextView tv_af_kcal_counter;

    TextView tv_af_distance_counter_2;


    private ConnectIQ.IQDeviceEventListener mDeviceEventListener = new ConnectIQ.IQDeviceEventListener() {

        @Override
        public void onDeviceStatusChanged(IQDevice device, IQDevice.IQDeviceStatus status) {
            mAdapter.updateDeviceStatus(device, status);
        }

    };

    private ConnectIQ.ConnectIQListener mListener = new ConnectIQ.ConnectIQListener() {

        @Override
        public void onInitializeError(ConnectIQ.IQSdkErrorStatus errStatus) {
            if( null != mEmptyView )
                mEmptyView.setText(R.string.initialization_error + errStatus.name());
            mSdkReady = false;
        }

        @Override
        public void onSdkReady() {
            loadDevices();
            mSdkReady = true;
        }


        @Override
        public void onSdkShutDown() {
            mSdkReady = false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        Utilities.filePath.observe(Objects.requireNonNull(this), nameObserver);
        prepareActivityNav();
        //prepareGpsTracking();
        prepareNavBar();

        imageView = findViewById(R.id.imageView);
        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
        tv_af_steps_counter = findViewById(R.id.tv_af_steps_counter);
        tv_af_distance_counter = findViewById(R.id.tv_af_distance_counter);
        tv_af_kcal_counter = findViewById(R.id.tv_af_calorie_counter);

        tv_af_distance_counter_2 = findViewById(R.id.tv_af_distance_counter_2);




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

        loadApi();
    }

    final Observer<List<String>> nameObserver = new Observer<List<String>>() {
        @Override
        public void onChanged(List<String> newName) {
            if (newName != null && !newName.equals("")) {

                tv_af_distance_counter.setText(newName.get(0));
                tv_af_kcal_counter.setText(newName.get(1));

            }
        }
    };

    private void loadApi() {

        int user_id = Utilities.getInt(FitnessActivity.this,"user_id");

        final IOSDialog dialog0 = new IOSDialog.Builder(FitnessActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();
//        Toast.makeText(this, R.string.wait, Toast.LENGTH_SHORT).show();


        Map<String,String> postParam = new HashMap<String, String>();
        postParam.put("user_id",String.valueOf(user_id));


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");



        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL+"fitness_diary", FitnessActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {


                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            JSONObject jsonObject1 = jsonObject.getJSONObject("data");

                            int total_steps = jsonObject1.getInt("total_steps");
                            float value = Float.valueOf(jsonObject1.getString("total_distance"));
                            int total_kcals = jsonObject1.getInt("total_kcals");

                            tv_af_steps_counter.setText(Utilities.formattTwoDecimal(FitnessActivity.this,total_steps));
                            tv_af_distance_counter.setText(String.valueOf(value));
                            tv_af_distance_counter_2.setText(String.valueOf(value));
                            tv_af_kcal_counter.setText(Utilities.formattTwoDecimal(FitnessActivity.this,total_kcals));

                        }else {

                            Toast.makeText(FitnessActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();
                    Utilities.hideProgressDialog();
                }
            }
        });






    }

    public void loadDevices() {
        // Retrieve the list of known devices
        try {
            List<IQDevice> devices = mConnectIQ.getKnownDevices();

            if (devices.size()>0) {
//                mAdapter.setDevices(devices);
                Toast.makeText(this, "Device Founded!", Toast.LENGTH_SHORT).show();
                // Let's register for device status updates.  By doing so we will
                // automatically get a status update for each device so we do not
                // need to call getStatus()
                for (IQDevice device : devices) {
                    mConnectIQ.registerForDeviceEvents(device, mDeviceEventListener);
                }
            }else {
                Toast.makeText(this, "Ops.. Device Not Found", Toast.LENGTH_SHORT).show();
            }

        } catch (InvalidStateException e) {
            // This generally means you forgot to call initialize(), but since
            // we are in the callback for initialize(), this should never happen
        } catch (ServiceUnavailableException e) {
            // This will happen if for some reason your app was not able to connect
            // to the ConnectIQ service running within Garmin Connect Mobile.  This
            // could be because Garmin Connect Mobile is not installed or needs to
            // be upgraded.
            if( null != mEmptyView )
                mEmptyView.setText(R.string.service_unavailable);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();

        prepareSteps();
    }

    private void prepareActivityNav() {
        View view2 = findViewById(R.id.view2);
        View view3 = findViewById(R.id.view3);
        View view40 = findViewById(R.id.view40);
        View view50 = findViewById(R.id.view50);
        View view20 = findViewById(R.id.view20);

        View.OnClickListener clickListener = view -> {
            startActivity(new Intent(this, ExerciseReportActivity.class));
        };

        view2.setOnClickListener(view -> startActivity(new Intent(FitnessActivity.this, NewStepsReportActivity.class)));
        view3.setOnClickListener(view -> startActivity(new Intent(FitnessActivity.this, CaloriesReportActivity.class)));
        view40.setOnClickListener(clickListener);
        view50.setOnClickListener(clickListener);
        view20.setOnClickListener(view -> startActivity(new Intent(FitnessActivity.this, SleepReportActivity.class)));
    }

    private void prepareSteps() {
        StepsDBHelper mStepsDBHelper = new StepsDBHelper(this);
        List<DateStepsModel> mStepCountList = mStepsDBHelper.readStepsEntriesToday();

        for (DateStepsModel model : mStepCountList) {
            steps = model.mStepCount;
        }

        TextView tv_af_steps_counter = findViewById(R.id.tv_af_steps_counter);
        TextView tv_af_steps_counter_2 = findViewById(R.id.tv_af_steps_counter_2);

        DecimalFormat df = new DecimalFormat("0.##");

        TextView tv_af_distance_counter = findViewById(R.id.tv_af_distance_counter);
//        TextView tv_af_distance_counter_2 = findViewById(R.id.tv_af_distance_counter_2);
        TextView tv_af_kcal_counter = findViewById(R.id.tv_af_calorie_counter);
        TextView tv_af_calorie_counter_2 = findViewById(R.id.tv_af_calorie_counter_2);



        double distance = (steps * 0.762) / 1000;
        double kcal = steps / 20;

//        tv_af_distance_counter.setText(df.format(distance));
//        tv_af_kcal_counter.setText(df.format(kcal));
//        tv_af_steps_counter.setText(String.valueOf(steps));

//        tv_af_distance_counter_2.setText(df.format(distance));
        tv_af_calorie_counter_2.setText(df.format(kcal));
        tv_af_steps_counter_2.setText(String.valueOf(steps));
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
//                    startActivity(new Intent(this, AudioActivity.class));

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
}
