package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.StepsReport;
import com.mtechsoft.fitmy.v1.Utilities;
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

public class NewStepsReportActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<StepsReport> stepsReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_steps_report);

        initr();
        stepsApi();

    }

    private void stepsApi() {

        final IOSDialog dialog0 = new IOSDialog.Builder(NewStepsReportActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        int user_id = Utilities.getInt(NewStepsReportActivity.this, "user_id");


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id", String.valueOf(user_id));


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL + "get_total_steps", NewStepsReportActivity.this, postParam, headers, new ServerCallback() {
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

                                StepsReport stepsReport = new StepsReport();


                                int id = jsonObject1.getInt("id");
                                String activity_type = jsonObject1.getString("activity_type");
                                String start_date = jsonObject1.getString("start_date");
                                String end_date = jsonObject1.getString("end_date");
                                String steps = jsonObject1.getString("steps");

                                stepsReport.setId(id);
                                stepsReport.setActivity_type(activity_type);
                                stepsReport.setStart_date(start_date);
                                stepsReport.setEnd_date(end_date);
                                stepsReport.setSteps(steps);

                                stepsReports.add(stepsReport);

                            }

                            StepsReportAdapter stepsReportAdapter = new StepsReportAdapter(stepsReports, NewStepsReportActivity.this);
                            RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(NewStepsReportActivity.this);
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


        new PrettyDialog(NewStepsReportActivity.this)
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

        recyclerView = findViewById(R.id.steps_report);
        stepsReports = new ArrayList<>();

    }
}
