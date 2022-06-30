package com.mtechsoft.fitmy.v1.activity.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.common.DateStepsModel;
import com.mtechsoft.fitmy.v1.common.StepsDBHelper;

public class StepsReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_report);

        prepareBarChart();
    }

    private void prepareBarChart() {
        prepareChart01();
        prepareChart02();
        prepareChart03();

        Button button5 = findViewById(R.id.button5);
        Button button4 = findViewById(R.id.button4);
        Button button6 = findViewById(R.id.button6);

        button5.setOnClickListener(view -> {
            show01();
            button5.setBackgroundResource(R.drawable.bg_rounded_big_orange);
            button4.setBackgroundResource(R.drawable.bg_rounded_big_white_outline_orange);
            button6.setBackgroundResource(R.drawable.bg_rounded_big_white_outline_orange);
        });

        button4.setOnClickListener(view -> {
            show02();
            button5.setBackgroundResource(R.drawable.bg_rounded_big_white_outline_orange);
            button4.setBackgroundResource(R.drawable.bg_rounded_big_orange);
            button6.setBackgroundResource(R.drawable.bg_rounded_big_white_outline_orange);
        });

        button6.setOnClickListener(view -> {
            show03();
            button5.setBackgroundResource(R.drawable.bg_rounded_big_white_outline_orange);
            button4.setBackgroundResource(R.drawable.bg_rounded_big_white_outline_orange);
            button6.setBackgroundResource(R.drawable.bg_rounded_big_orange);
        });
    }

    private ArrayList getData01() {
        StepsDBHelper mStepsDBHelper = new StepsDBHelper(this);
        List<DateStepsModel> mStepCountList = mStepsDBHelper.readStepsEntriesWeek();

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            int steps = 0;

            if (mStepCountList.size() > i) steps = mStepCountList.get(i).mStepCount;

            entries.add(new BarEntry(i, steps));
        }

        return entries;
    }

    private ArrayList getData02() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 0));
        entries.add(new BarEntry(1f, 0));
        entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(3f, 0));
        return entries;
    }

    private ArrayList getData03() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 0));
        entries.add(new BarEntry(1f, 0));
        entries.add(new BarEntry(2f, 0));
        entries.add(new BarEntry(3f, 0));
        entries.add(new BarEntry(4f, 0));
        entries.add(new BarEntry(5f, 0));
        entries.add(new BarEntry(6f, 0));
        entries.add(new BarEntry(7f, 0));
        entries.add(new BarEntry(8f, 0));
        entries.add(new BarEntry(9f, 0));
        entries.add(new BarEntry(10f, 0));
        entries.add(new BarEntry(11f, 0));
        return entries;
    }

    private void prepareChart01() {
        BarChart barChart = findViewById(R.id.barChart);
        barChart.setDescription(null);
        BarDataSet barDataSet = new BarDataSet(getData01(), "Daily Average Steps");
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

    private void prepareChart02() {
        BarChart barChart = findViewById(R.id.barChart2);
        barChart.setDescription(null);
        BarDataSet barDataSet = new BarDataSet(getData02(), "Weekly Average Steps");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        final String[] months = new String[]{"W1", "W2", "W3", "W4"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(0, 3000);
        barChart.invalidate();
    }

    private void prepareChart03() {
        BarChart barChart = findViewById(R.id.barChart3);
        barChart.setDescription(null);
        BarDataSet barDataSet = new BarDataSet(getData03(), "Monthly Average Steps");
        barDataSet.setBarBorderWidth(0.9f);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData barData = new BarData(barDataSet);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter(months);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);
        barChart.setData(barData);
        barChart.setFitBars(true);
        barChart.animateXY(0, 3000);
        barChart.invalidate();
    }

    private void show01() {
        BarChart barChart = findViewById(R.id.barChart);
        BarChart barChart2 = findViewById(R.id.barChart2);
        BarChart barChart3 = findViewById(R.id.barChart3);
        barChart.setVisibility(View.VISIBLE);
        barChart2.setVisibility(View.INVISIBLE);
        barChart3.setVisibility(View.INVISIBLE);
    }

    private void show02() {
        BarChart barChart = findViewById(R.id.barChart);
        BarChart barChart2 = findViewById(R.id.barChart2);
        BarChart barChart3 = findViewById(R.id.barChart3);
        barChart.setVisibility(View.INVISIBLE);
        barChart2.setVisibility(View.VISIBLE);
        barChart3.setVisibility(View.INVISIBLE);
    }

    private void show03() {
        BarChart barChart = findViewById(R.id.barChart);
        BarChart barChart2 = findViewById(R.id.barChart2);
        BarChart barChart3 = findViewById(R.id.barChart3);
        barChart.setVisibility(View.INVISIBLE);
        barChart2.setVisibility(View.INVISIBLE);
        barChart3.setVisibility(View.VISIBLE);
    }
}