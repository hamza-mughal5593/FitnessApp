package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.All_Events;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventDetailActivity;

public class AllEventsAdapter extends RecyclerView.Adapter<AllEventsAdapter.MyViewHolder> {


    List<All_Events> all_events;
    Context context;


    public AllEventsAdapter(List<All_Events> all_events, Context context) {
        this.all_events = all_events;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.events_adapter_layout, parent, false);
        return new AllEventsAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        final All_Events item = all_events.get(position);

        holder.name.setText(all_events.get(position).getName());
        holder.desrciption.setText(all_events.get(position).getDescription());
        Picasso.get()
                .load(all_events.get(position).getImage())
                .into(holder.image);

        holder.event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String image = all_events.get(position).getImage();
                String name = all_events.get(position).getName();
                String desc = all_events.get(position).getDescription();
                String term = all_events.get(position).getTerms_and_condition();
                String date = all_events.get(position).getDate();
                String location = all_events.get(position).getLocation();
                String url = all_events.get(position).getUrl();
                String ev_id = all_events.get(position).getId();


                Intent intent = new Intent(context, AsEventDetailActivity.class);
                intent.putExtra("event_name",name);
                intent.putExtra("event_image",image);
                intent.putExtra("event_des",desc);
                intent.putExtra("event_term",term);
                intent.putExtra("date",date);
                intent.putExtra("location",location);
                intent.putExtra("url",url);
                intent.putExtra("id",ev_id);
                context.startActivity(intent);
            }
        });
}

    @Override
    public int getItemCount() {
        return all_events.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name,desrciption;
        LinearLayout event;


        public MyViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.event_image);
            name = itemView.findViewById(R.id.event_name);
            event = itemView.findViewById(R.id.main_layout);
            desrciption = itemView.findViewById(R.id.event_description);

        }
    }
}
