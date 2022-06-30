package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.CaloriesReport;
import com.mtechsoft.fitmy.v1.Models.StepsReport;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.adapter.CaloriesReportAdapter;
import com.mtechsoft.fitmy.v1.adapter.StepsReportAdapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.Constants;
import com.mtechsoft.fitmy.v1.api.ServerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class CaloriesReportActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<CaloriesReport> caloriesReports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_report);


        initr();

        stepsApi();

    }

    private void stepsApi() {

        final IOSDialog dialog0 = new IOSDialog.Builder(CaloriesReportActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        int user_id = Utilities.getInt(CaloriesReportActivity.this, "user_id");


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id", String.valueOf(user_id));


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL + "get_total_cals", CaloriesReportActivity.this, postParam, headers, new ServerCallback() {
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

                                CaloriesReport stepsReport = new CaloriesReport();


                                int id = jsonObject1.getInt("id");
                                String activity_type = jsonObject1.getString("activity_type");
                                String start_date = jsonObject1.getString("start_date");
                                String end_date = jsonObject1.getString("end_date");
                                String steps = jsonObject1.getString("kcal");

                                stepsReport.setId(id);
                                stepsReport.setActivity_type(activity_type);
                                stepsReport.setStart_date(start_date);
                                stepsReport.setEnd_date(end_date);
                                stepsReport.setSteps(steps);

                                caloriesReports.add(stepsReport);

                            }

                            CaloriesReportAdapter stepsReportAdapter = new CaloriesReportAdapter(caloriesReports, CaloriesReportActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(CaloriesReportActivity.this);
                            recyclerView.setLayoutManager(layoutManager2);
                            recyclerView.setAdapter(stepsReportAdapter);

                        }else {

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

        new PrettyDialog(CaloriesReportActivity.this)
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


    private void initr() {

        recyclerView = findViewById(R.id.calories_report);
        caloriesReports = new ArrayList<>();

    }
}
