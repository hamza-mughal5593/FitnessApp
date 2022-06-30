package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.All_Events;
import com.mtechsoft.fitmy.v1.Models.Complex;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsBookingActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventDetailActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.SelectFacilityActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ComplexAdapter extends RecyclerView.Adapter<ComplexAdapter.MyViewHolder> {


    List<Complex> complexes;
    Context context;


    public ComplexAdapter(List<Complex> complexes, Context context) {
        this.complexes = complexes;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.complex_adapter_layout, parent, false);
        return new ComplexAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(complexes.get(position).getName());
        holder.description.setText(complexes.get(position).getDescription());
        holder.email.setText(complexes.get(position).getEmail());
        holder.tel_no.setText(complexes.get(position).getTel_no());
        holder.fax_no.setText(complexes.get(position).getFax_no());
        holder.fasility_list.setText(complexes.get(position).getFasility_list());

        holder.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, SelectFacilityActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return complexes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TextView description;
        TextView tel_no;
        TextView fax_no;
        TextView email;
        TextView fasility_list;
        RelativeLayout next;


        public MyViewHolder(View itemView) {
            super(itemView);


            name = itemView.findViewById(R.id.complex_name);
            description = itemView.findViewById(R.id.complex_description);
            tel_no = itemView.findViewById(R.id.complex_tel);
            fax_no = itemView.findViewById(R.id.complex_fax);
            email = itemView.findViewById(R.id.compplex_email);
            fasility_list = itemView.findViewById(R.id.complex_facility_list);
            next = itemView.findViewById(R.id.next);


        }
    }
}
