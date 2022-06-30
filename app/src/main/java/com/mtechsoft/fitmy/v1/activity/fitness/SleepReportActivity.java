package com.mtechsoft.fitmy.v1.activity.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.Utils;

public class SleepReportActivity extends AppCompatActivity {

    private String m_Text = "";

    int sunday_hours = 0;
    int monday_hours = 0;
    int tuesday_hours = 0;
    int wednesday_hours = 0;
    int thursday_hours = 0;
    int friday_hours = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_report);

        prepareList();
        prepareBarChart();
    }

    private void prepareList() {

        LinearLayout ll_aer_list = findViewById(R.id.ll_aer_list);
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_SLEEP_DATA = sharedPreferences.getString(Constants.SP_SLEEP_DATA, "[]");

        try {
            JSONArray responseArray = new JSONArray(SP_SLEEP_DATA);

            ll_aer_list.removeAllViews();

                for (int i = 0; i < responseArray.length(); i++) {
                    try {
                        JSONObject tempObj = responseArray.getJSONObject(i);

                        LayoutInflater inflater = getLayoutInflater();
                        View rowView = inflater.inflate(R.layout.listitem_sleep_report, null, true);

                        TextView tv_da_rf_li_01 = rowView.findViewById(R.id.tv_da_rf_li_01);
                        TextView tv_da_rf_li_03 = rowView.findViewById(R.id.tv_da_rf_li_03);




                        tv_da_rf_li_01.setText(Utils.getDateNonT(tempObj.getString("date_created")));
                        tv_da_rf_li_03.setText(tempObj.getString("hours"));

                        ImageView iv_aea_list_active = rowView.findViewById(R.id.iv_da_rf_li_01);
                        iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_moon));

                        runOnUiThread(() -> ll_aer_list.addView(rowView));

                    } catch (JSONException ex) {
                        // TODO
                    }
                }
        } catch (JSONException ex) {
             // TODO
        }
    }

    private void prepareBarChart() {
        prepareChart01();
        prepareAddRecord();
    }

    private ArrayList getData01(){
        ArrayList<BarEntry> entries = new ArrayList<>();

        SharedPreferences mPref = getSharedPreferences("Sunday", MODE_PRIVATE);
        final int suncount = mPref.getInt("count", 0);
        entries.add(new BarEntry(0f, suncount));

        SharedPreferences mPrefs = getSharedPreferences("Monday", MODE_PRIVATE);
        final int modaycount = mPrefs.getInt("count", 0);
        entries.add(new BarEntry(1f, modaycount));


        SharedPreferences mPrefss = getSharedPreferences("Tuesday", MODE_PRIVATE);
        final int tuesdaycount = mPrefss.getInt("count", 0);
        entries.add(new BarEntry(2f, tuesdaycount));

        SharedPreferences mPrefsss = getSharedPreferences("Wednesday", MODE_PRIVATE);
        final int wednesdaycount = mPrefsss.getInt("count", 0);
        entries.add(new BarEntry(3f, wednesdaycount));


        SharedPreferences mPrefssss = getSharedPreferences("Thursday", MODE_PRIVATE);
        final int thursdaycount = mPrefssss.getInt("count", 0);
        entries.add(new BarEntry(4f, thursdaycount));

        SharedPreferences mPrefsssss = getSharedPreferences("Friday", MODE_PRIVATE);
        final int fricount = mPrefsssss.getInt("count", 0);
        entries.add(new BarEntry(5f, fricount));

        SharedPreferences mPrefssssss = getSharedPreferences("Saturday", MODE_PRIVATE);
        final int satcount = mPrefssssss.getInt("count", 0);
        entries.add(new BarEntry(6f, satcount));
        return entries;
    }

    private void prepareChart01() {
        BarChart barChart = findViewById(R.id.barChart);
        barChart.setDescription(null);
        BarDataSet barDataSet = new BarDataSet(getData01(), "Daily Average Sleep Hours");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        final String[] months = new String[]{"S", "M", "T", "W", "T", "F", "S"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(0, 3000);
        barChart.invalidate();
    }

    private void prepareAddRecord() {
        Button button6 = findViewById(R.id.button6);
        button6.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(SleepReportActivity.this);
            builder.setTitle("Add Sleep Record (hours)");

            final EditText input = new EditText(SleepReportActivity.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            builder.setView(input);

            builder.setPositiveButton("Save", (dialog, which) -> saveSleepRecord(input.getText().toString()));
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

            builder.show();
        });
    }

    private void saveSleepRecord(String hours) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("hours", hours);
            jsonObject.put("date_created", Utils.convertDatetimeSQL(new Date()));


            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            String day = sdf.format(d);

            if (day.equals("Sunday")){


                SharedPreferences mPrefs = getSharedPreferences("Sunday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();

            }

            if (day.equals("Monday")){


                SharedPreferences mPrefs = getSharedPreferences("Monday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();

            }else if (day.equals("Tuesday")){

                SharedPreferences mPrefs = getSharedPreferences("Tuesday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();


            }else if (day.equals("Wednesday")){

                SharedPreferences mPrefs = getSharedPreferences("Wednesday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();



            }else if (day.equals("Thursday")){

                SharedPreferences mPrefs = getSharedPreferences("Thursday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();


            }else if (day.equals("Friday")){

                SharedPreferences mPrefs = getSharedPreferences("Friday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();

            }else if (day.equals("Saturday")){

                SharedPreferences mPrefs = getSharedPreferences("Saturday", MODE_PRIVATE);
                int count = mPrefs.getInt("count", 0);
                count = count + Integer.parseInt(hours);

                SharedPreferences.Editor editor = mPrefs.edit();
                editor.putInt("count", count);
                editor.apply();

                prepareBarChart();


            }else {

            }

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
            String SP_SLEEP_DATA = sharedPreferences.getString(Constants.SP_SLEEP_DATA, "[]");

            JSONArray jsonArray = new JSONArray(SP_SLEEP_DATA);
            jsonArray.put(jsonObject);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.SP_SLEEP_DATA, jsonArray.toString());
            editor.commit();

            prepareList();
        } catch (JSONException ex) {
            // TODO
        }
    }
}
