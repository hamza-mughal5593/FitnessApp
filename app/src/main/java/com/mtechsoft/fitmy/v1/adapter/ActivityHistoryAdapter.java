package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessDetailsActivity;

public class ActivityHistoryAdapter extends RecyclerView.Adapter<ActivityHistoryAdapter.MyViewHolder> {


//    List<ActivityHistory> activityHistories;
    ArrayList<String> activity_typeee = new ArrayList<>();
    ArrayList<String> timeArray = new ArrayList<>();
    ArrayList<String> distanceArray = new ArrayList<>();
    ArrayList<String> stepsArray = new ArrayList<>();
    ArrayList<String> caloriesArray = new ArrayList<>();
    ArrayList<String> startLatitude = new ArrayList<>();
    ArrayList<String> startLongitude= new ArrayList<>();
    ArrayList<String> endLatitude = new ArrayList<>();
    ArrayList<String> endLongitude= new ArrayList<>();
    String a_type;
    Context context;
    String typ = "";


    String time, avg_speed,kilo_meters,calories,km_h,steps,startlat,startlong,endlat,endlong;

    public ActivityHistoryAdapter(ArrayList<String> activity_typeee, ArrayList<String> timeArray, ArrayList<String> distanceArray, ArrayList<String> stepsArray, ArrayList<String> caloriesArray, ArrayList<String> startLatitude, ArrayList<String> startLongitude, ArrayList<String> endLatitude, ArrayList<String> endLongitude, String a_type, Context context) {
        this.activity_typeee = activity_typeee;
        this.timeArray = timeArray;
        this.distanceArray = distanceArray;
        this.stepsArray = stepsArray;
        this.caloriesArray = caloriesArray;
        this.startLatitude = startLatitude;
        this.startLongitude = startLongitude;
        this.endLatitude = endLatitude;
        this.endLongitude = endLongitude;
        this.a_type = a_type;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.listitem_fitness_report, parent, false);
        return new ActivityHistoryAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

//        String activity_type = activityHistories.get(position).getActivity_type();

        if (a_type == "outdoor_walking"){
            holder.image.setImageResource(R.drawable.ic_walk);
            holder.name.setText(R.string.walk);
        }else if (a_type.equalsIgnoreCase("outdoor_running")){
            holder.image.setImageResource(R.drawable.ic_running);
            holder.name.setText(R.string.run);
        }else if(a_type.equalsIgnoreCase("outdoor_stairs_climbing")){
            holder.image.setImageResource(R.drawable.stairs_icon);
            holder.name.setText(R.string.stairs);
        }else if(a_type.equalsIgnoreCase("outdoor_cycling")){
            holder.image.setImageResource(R.drawable.ic_cycling);
            holder.name.setText(R.string.cycle);
        }else if(a_type.equalsIgnoreCase("outdoor_hiking")){
            holder.image.setImageResource(R.drawable.ic_hiking);
            holder.name.setText(R.string.hike);
        }else if(a_type.equalsIgnoreCase("outdoor_swimming")){
            holder.image.setImageResource(R.drawable.ic_swimming);
            holder.name.setText(R.string.swim);
        }else if(a_type.equalsIgnoreCase("outdoor_aerobic")){
            holder.image.setImageResource(R.drawable.ic_aerobic);
            holder.name.setText(R.string.aerobic);
        }else if (a_type.equalsIgnoreCase("indoor_running")){
            holder.image.setImageResource(R.drawable.ic_indoor_running);
            holder.name.setText(R.string.indoor_run);
        }

        Double aDouble = Double.valueOf(distanceArray.get(position));
        double roundOff = Math.round(aDouble * 100.0) / 100.0;

            holder.distance.setText(String.valueOf(roundOff));
//            holder.distance.setText(Utilities.formattTwoDecimal(context,dis));
            holder.timer.setText(timeArray.get(position));

        holder.view7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                time = timeArray.get(position);
                kilo_meters = distanceArray.get(position);
                steps = stepsArray.get(position);
                calories = caloriesArray.get(position);
                startlat = startLatitude.get(position);
                startlong= startLongitude.get(position);
                endlat = endLatitude.get(position);
                endlong = endLongitude.get(position);
                typ = activity_typeee.get(position);




                Intent intent = new Intent(context, FitnessDetailsActivity.class);
                intent.putExtra("check",1);
                intent.putExtra("time",time);
                intent.putExtra("distance",kilo_meters);
                intent.putExtra("steps",steps);
                intent.putExtra("calories",calories);
                intent.putExtra("start_lat",startlat);
                intent.putExtra("start_long",startlong);
                intent.putExtra("end_lat",endlat);
                intent.putExtra("end_long",endlong);
                intent.putExtra("activity_type",typ);

                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return timeArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name,timer,distance;
        View view7;


        public MyViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.iv_da_rf_li_01);
            name = itemView.findViewById(R.id.tv_da_rf_li_01);
            timer = itemView.findViewById(R.id.tv_da_rf_li_02);
            distance = itemView.findViewById(R.id.tv_da_rf_li_03);
            view7 = itemView.findViewById(R.id.view7);

        }
    }
}
