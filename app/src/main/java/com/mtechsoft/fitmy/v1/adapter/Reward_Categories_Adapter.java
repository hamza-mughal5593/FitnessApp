package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
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
import com.mtechsoft.fitmy.v1.Models.Reward_Category;

public class Reward_Categories_Adapter extends RecyclerView.Adapter<Reward_Categories_Adapter.MyViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Reward_Category item);
    }

    List<Reward_Category> rewardCategories;
    Context context;
    Reward_Categories_Adapter.OnItemClickListener listener;

    public Reward_Categories_Adapter(List<Reward_Category> rewardCategories, Context context, OnItemClickListener listener) {
        this.rewardCategories = rewardCategories;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.reward_categories_adapter_layout, parent, false);
        return new Reward_Categories_Adapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final Reward_Category item = rewardCategories.get(position);


        if (position == 0){

            holder.image.setImageResource(R.drawable.all_out);

        }else {

            Picasso.get()
                    .load(rewardCategories.get(position).getE_cat_image())
                    .into(holder.image);

        }

        holder.name.setText(rewardCategories.get(position).getE_cat_name());

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onItemClick(item);

            }

        });


    }

    @Override
    public int getItemCount() {
        return rewardCategories.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name;
        LinearLayout main_layout;


        public MyViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.reward_cat_image);
            name = itemView.findViewById(R.id.reward_cat_name);
            main_layout= itemView.findViewById(R.id.main_layout);

        }
    }
}
