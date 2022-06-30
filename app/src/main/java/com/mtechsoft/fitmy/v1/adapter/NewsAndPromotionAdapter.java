package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.NewsAndPromotion;
import com.mtechsoft.fitmy.v1.activity.news.NewsDetailsActivity;

public class NewsAndPromotionAdapter extends RecyclerView.Adapter<NewsAndPromotionAdapter.MyViewHolder> {

    ArrayList<NewsAndPromotion> newsAndPromotions;
    Context context;

    public NewsAndPromotionAdapter(ArrayList<NewsAndPromotion> newsAndPromotions, Context context) {
        this.newsAndPromotions = newsAndPromotions;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.news_adapter_layout, parent, false);
        return new NewsAndPromotionAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


//        holder.image.setImageResource(newsAndPromotions.get(position).getNews_image());
        Picasso.get()
                .load(newsAndPromotions.get(position).getNews_image())
                .into(holder.image);
        holder.name.setText(newsAndPromotions.get(position).getNews_titile());
        holder.desrciption.setText(newsAndPromotions.get(position).getNews_description());

        String imagee = newsAndPromotions.get(position).getNews_image();
        String descriptionn = newsAndPromotions.get(position).getNews_description();
        String name= newsAndPromotions.get(position).getNews_titile();


        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NewsDetailsActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("image",imagee);
                intent.putExtra("description",descriptionn);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsAndPromotions.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name,desrciption;
        RelativeLayout main_layout;




        public MyViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.news_image);
            name = itemView.findViewById(R.id.news_titile);
            desrciption = itemView.findViewById(R.id.news_description);
            main_layout = itemView.findViewById(R.id.main_layout);


        }
    }
}
