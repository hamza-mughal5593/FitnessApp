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
import com.mtechsoft.fitmy.v1.Models.Newss.News;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventDetailActivity;
import com.mtechsoft.fitmy.v1.activity.news.NewsDetailsActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AsNewsAdapter extends RecyclerView.Adapter<AsNewsAdapter.MyViewHolder> {


    List<com.mtechsoft.fitmy.v1.Models.Newss.News> news;
    Context context;


    public AsNewsAdapter(List<News> news, Context context) {
        this.news = news;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.news_adapter_layout, parent, false);
        return new AsNewsAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.title.setText(news.get(position).getTitle());
        holder.descrition.setText(news.get(position).getDescription());


        Picasso.get()
                .load(news.get(position).getImage())
                .into(holder.imageView);

        holder.main_layout.setOnClickListener(view -> {



            String imagee = news.get(position).getImage();
            String titler = news.get(position).getTitle();
            String descriptionn = news.get(position).getDescription();

            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("name",titler);
            intent.putExtra("image",imagee);
            intent.putExtra("description",descriptionn);
            context.startActivity(intent);


        });
        
}

    @Override
    public int getItemCount() {
        return news.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {



        TextView title,descrition;
        ImageView imageView;
        RelativeLayout main_layout;


        public MyViewHolder(View itemView) {
            super(itemView);



            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.news_titile);
            descrition = itemView.findViewById(R.id.news_description);
            main_layout = itemView.findViewById(R.id.main_layout);

        }
    }
}
