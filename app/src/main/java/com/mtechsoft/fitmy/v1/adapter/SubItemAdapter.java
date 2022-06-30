package com.mtechsoft.fitmy.v1.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.Newss.SubItem;
import com.mtechsoft.fitmy.v1.activity.news.NewsDetailsActivity;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemAdapter.SubItemViewHolder> {

    private List<SubItem> subItemList;
    private Context context;

    public SubItemAdapter(List<SubItem> subItemList, Context context) {
        this.subItemList = subItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SubItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_adapter_layout, parent, false);
        return new SubItemViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SubItemViewHolder holder, int position) {

        holder.title.setText(subItemList.get(position).getTitle());
        holder.descrition.setText(subItemList.get(position).getDescription());
        Picasso.get()
                .load(subItemList.get(position).getImage())
                .into(holder.imageView);

        holder.main_layout.setOnClickListener(view -> {

            String imagee = subItemList.get(position).getImage();
            String titler = subItemList.get(position).getTitle();
            String descriptionn = subItemList.get(position).getDescription();

            Intent intent = new Intent(context, NewsDetailsActivity.class);
            intent.putExtra("name",titler);
            intent.putExtra("image",imagee);
            intent.putExtra("description",descriptionn);
            context.startActivity(intent);


        });


    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    class SubItemViewHolder extends RecyclerView.ViewHolder {

        TextView title,descrition;
        ImageView imageView;
        RelativeLayout main_layout;



        SubItemViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.news_image);
            title = itemView.findViewById(R.id.news_titile);
            descrition = itemView.findViewById(R.id.news_description);
            main_layout = itemView.findViewById(R.id.main_layout);
        }
    }

}
