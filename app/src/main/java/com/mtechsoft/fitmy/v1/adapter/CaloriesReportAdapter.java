package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.CaloriesReport;
import com.mtechsoft.fitmy.v1.Models.StepsReport;

import java.util.ArrayList;

public class CaloriesReportAdapter extends RecyclerView.Adapter<CaloriesReportAdapter.MyViewHolder> {


//    List<ActivityHistory> activityHistories;

    ArrayList<CaloriesReport> caloriesReports;
    Context context;


    public CaloriesReportAdapter(ArrayList<CaloriesReport> caloriesReports, Context context) {
        this.caloriesReports = caloriesReports;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.steps_report_adapter_layout, parent, false);
        return new CaloriesReportAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.steps.setText(caloriesReports.get(position).getSteps());
        holder.type.setText("Calories");


        String datee = caloriesReports.get(position).getStart_date();
        if (datee.equals("null")){

            holder.date_time.setText("N/A");
        }else {

            String ss = datee.substring(0,20);
        holder.date_time.setText(ss);
        }

        String a_type = caloriesReports.get(position).getActivity_type();



        if (a_type.equalsIgnoreCase("outdoor_walking")){
            holder.image.setImageResource(R.drawable.ic_walk);
            holder.activity_name.setText(R.string.walk);
        }else if (a_type.equalsIgnoreCase("outdoor_running")){
            holder.image.setImageResource(R.drawable.ic_running);
            holder.activity_name.setText(R.string.run);
        }else if(a_type.equalsIgnoreCase("outdoor_stairs_climbing")){
            holder.image.setImageResource(R.drawable.stairs_icon);
            holder.activity_name.setText(R.string.stairs);
        }else if(a_type.equalsIgnoreCase("outdoor_cycling")){
            holder.image.setImageResource(R.drawable.ic_cycling);
            holder.activity_name.setText(R.string.cycle);
        }else if(a_type.equalsIgnoreCase("outdoor_hiking")){
            holder.image.setImageResource(R.drawable.ic_hiking);
            holder.activity_name.setText(R.string.hike);
        }else if(a_type.equalsIgnoreCase("outdoor_swimming")){
            holder.image.setImageResource(R.drawable.ic_swimming);
            holder.activity_name.setText(R.string.swim);
        }else if(a_type.equalsIgnoreCase("outdoor_aerobic")){
            holder.image.setImageResource(R.drawable.ic_aerobic);
            holder.activity_name.setText(R.string.aerobic);
        }else if (a_type.equalsIgnoreCase("indoor_running")){
            holder.image.setImageResource(R.drawable.ic_indoor_running);
            holder.activity_name.setText(R.string.indoor_run);
        }


    }

    @Override
    public int getItemCount() {
        return caloriesReports.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView activity_name,steps,date_time;
        TextView  type;




        public MyViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.activity_image);
            activity_name = itemView.findViewById(R.id.activity_name);
            steps = itemView.findViewById(R.id.steps);
            date_time = itemView.findViewById(R.id.date_time);
            type = itemView.findViewById(R.id.typee);


        }
    }
}
