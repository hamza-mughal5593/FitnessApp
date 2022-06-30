package com.mtechsoft.fitmy.v1.activity.fitness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.mtechsoft.fitmy.v1.Models.ActivityHistory;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.adapter.ActivityHistoryAdapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.Constants;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ExerciseReportActivity extends AppCompatActivity {

    int z = 0, img;
    String type;
    ArrayList<String> activity_typeee = new ArrayList<>();
    ArrayList<String> timeArray = new ArrayList<>();
    ArrayList<String> distanceArray = new ArrayList<>();
    ArrayList<String> stepsArray = new ArrayList<>();
    ArrayList<String> caloriesArray = new ArrayList<>();
    ArrayList<String> startLatitude = new ArrayList<>();
    ArrayList<String> startLongitude= new ArrayList<>();
    ArrayList<String> endLatitude = new ArrayList<>();
    ArrayList<String> endLongitude= new ArrayList<>();

    ArrayList<ActivityHistory> activityHistories = new ArrayList<>();
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_report);

        recyclerView = findViewById(R.id.activity_history_rv);

        Intent intent = getIntent();
        int activity_type= intent.getIntExtra("activity_name",0);
        int user_id = Utilities.getInt(ExerciseReportActivity.this,"user_id");

        Log.i("twoooo",""+activity_type);

        if(activity_type == 1)
        {
            type = "outdoor_walking";
        }else if(activity_type == 2){
            type = "outdoor_running";
        }else if(activity_type == 3){
            type = "outdoor_stairs_climbing";
        }else if(activity_type == 4){
            type = "outdoor_cycling";
        }else if(activity_type == 5){
            type = "outdoor_hiking";
        }else if(activity_type == 6){
            type = "outdoor_swimming";
        }else if (activity_type == 7){
            type = "outdoor_aerobic";
        }else if(activity_type == 8){
            type = "indoor_running";
        }

        Log.i("thiss",""+type);
        historyApi(user_id,0,type);
    }

    private void historyApi(final int user_id,final int activity_type, String typee) {

        final IOSDialog dialog0 = new IOSDialog.Builder(ExerciseReportActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        Map<String, String> postParam = new HashMap<String, String>();
//        postParam.put("user_id","2");
        postParam.put("user_id",String.valueOf(user_id));
        postParam.put("activity_type",typee);


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


//        Intent intent = getIntent();
//        int a = intent.getIntExtra("activity_name",0);
//        Log.i("activity_name", ""+a);
//        if(a == 1){
//            type = "Walking";
//        }else if(a == 2){
//            type = "Running";
//        }

        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"get_activity_history", ExerciseReportActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {
                    dialog0.dismiss();

                    Utilities.hideProgressDialog();


                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            for (int i=0; i< jsonArray.length(); i++){

                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

//                                ActivityHistory activityHistoryyy = new ActivityHistory();

//                                String timer = jsonObject1.getString("timer");
//                                String distance = jsonObject1.getString("distance");
                                String activity_name= jsonObject1.getString("activity_name");

                                Log.i("typesss",""+activity_name);

                                Log.i("mydata",""+type);
                                if(type.equals(activity_name)){

                                    int id = jsonObject1.getInt("id");
                                    String activity_namee = jsonObject1.getString("activity_name");
                                    String timer = jsonObject1.getString("timer");
                                    String distance = jsonObject1.getString("distance");
                                    String steps= jsonObject1.getString("steps");
                                    String cal = jsonObject1.getString("kcal");
                                    String start_lat = jsonObject1.getString("start_latitude");
                                    String start_longitude = jsonObject1.getString("start_longitude");
                                    String end_latitude = jsonObject1.getString("end_latitude");
                                    String end_longitude = jsonObject1.getString("end_longitude");



                                    timeArray.add(timer);
                                    distanceArray.add(distance);
                                    stepsArray.add(steps);
                                    caloriesArray.add(cal);
                                    startLatitude.add(start_lat);
                                    startLongitude.add(start_longitude);
                                    endLatitude.add(end_latitude);
                                    endLongitude.add(end_longitude);
                                    activity_typeee.add(activity_namee);


                                }else {

                                    showemptyDialouge();

                                }

                            }

                            ActivityHistoryAdapter activityHistoryAdapter = new ActivityHistoryAdapter(activity_typeee,timeArray,distanceArray,stepsArray,caloriesArray,startLatitude,startLongitude,endLatitude,endLongitude,type,ExerciseReportActivity.this);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ExerciseReportActivity.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(activityHistoryAdapter);

                        }else if (status == 400){

                            showemptyDialouge();

                        }else {

                            Toast.makeText(ExerciseReportActivity.this, ERROR, Toast.LENGTH_SHORT).show();
                        }


                      } catch (JSONException e) {
                        dialog0.dismiss();
                        Utilities.hideProgressDialog();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();
                    Utilities.hideProgressDialog();
                }
            }
        });





    }

    private void showemptyDialouge() {


        new PrettyDialog(ExerciseReportActivity.this)
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

                                finish();
                            }
                        }
                )
                .show();
    }

}
