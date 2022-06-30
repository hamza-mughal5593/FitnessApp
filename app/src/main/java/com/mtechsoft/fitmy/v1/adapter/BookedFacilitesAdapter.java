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
import com.mtechsoft.fitmy.v1.Models.BookedFacility;
import com.mtechsoft.fitmy.v1.Models.Complex;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.SelectFacilityActivity;

import java.util.List;

public class BookedFacilitesAdapter extends RecyclerView.Adapter<BookedFacilitesAdapter.MyViewHolder> {


    List<BookedFacility> bookedFacilities;
    Context context;


    public BookedFacilitesAdapter(List<BookedFacility> bookedFacilities, Context context) {
        this.bookedFacilities = bookedFacilities;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.booked_facilities_adapter_layout, parent, false);
        return new BookedFacilitesAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(bookedFacilities.get(position).getName());
        holder.date.setText(bookedFacilities.get(position).getDate());
        holder.type.setText(bookedFacilities.get(position).getType());
        holder.unit.setText(bookedFacilities.get(position).getUnit());
        holder.price.setText(bookedFacilities.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return bookedFacilities.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TextView date;
        TextView type;
        TextView unit;
        TextView price;


        public MyViewHolder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.facility_name);
            date = itemView.findViewById(R.id.facility_date);
            type = itemView.findViewById(R.id.facility_type);
            unit = itemView.findViewById(R.id.facility_units);
            price = itemView.findViewById(R.id.facility_price);


        }
    }
}
