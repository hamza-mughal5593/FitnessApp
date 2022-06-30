package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.Facility;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsBookingActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.MyViewHolder> {


    List<Facility> facilities;
    Context context;


    public FacilityAdapter(List<Facility> facilities, Context context) {
        this.facilities = facilities;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.facility_adapter_layout, parent, false);
        return new FacilityAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(facilities.get(position).getName());
        holder.nickName.setText(facilities.get(position).getNickName());
        holder.day_per_hour.setText(facilities.get(position).getDay_per_hour());
        holder.night_per_hour.setText(facilities.get(position).getNight_per_hour());
        holder.day_rate.setText(facilities.get(position).getDay_rate());
        holder.image.setImageResource(facilities.get(position).getImage());

        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, AsBookingActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        CircleImageView image;
        TextView name;
        TextView nickName;
        TextView day_per_hour;
        TextView night_per_hour;
        TextView day_rate;
        RelativeLayout next;


        public MyViewHolder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.facility_name);
            nickName = itemView.findViewById(R.id.facility_nick_name);
            day_per_hour = itemView.findViewById(R.id.day_per_hour_txt);
            night_per_hour = itemView.findViewById(R.id.night_per_hour_txt);
            day_rate = itemView.findViewById(R.id.facility_day_rate_txt);
            next = itemView.findViewById(R.id.next);
            image = itemView.findViewById(R.id.facility_image);


        }
    }
}
