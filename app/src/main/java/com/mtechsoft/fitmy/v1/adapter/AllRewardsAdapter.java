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

import com.android.volley.Request;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.All_Rewards;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.reward.RewardDetailsActivity;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;

public class AllRewardsAdapter extends RecyclerView.Adapter<AllRewardsAdapter.MyViewHolder> {

    List<All_Rewards> all_rewards;
    Context context;


    public AllRewardsAdapter(List<All_Rewards> all_rewards, Context context) {
        this.all_rewards = all_rewards;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.reward_adapter_layout, parent, false);
        return new AllRewardsAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {



        Picasso.get()
                .load(all_rewards.get(position).getImage())
                .into(holder.image);
        holder.name.setText(all_rewards.get(position).getName());
        holder.tvRewardPoint.setText(all_rewards.get(position).getRequired_fitness_points());
        holder.desrciption.setText(all_rewards.get(position).getDescription());
        int r_idd = all_rewards.get(position).getId();
        Utilities.saveInt(context,"r_idd",r_idd);
        holder.reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = all_rewards.get(position).getName();
                String description = all_rewards.get(position).getDescription();
                String term = all_rewards.get(position).getTerm_and_condition();
                String image = all_rewards.get(position).getImage();
                int id = all_rewards.get(position).getId();
                String required_fit_points = all_rewards.get(position).getRequired_fitness_points();


                Intent intent = new Intent(context,RewardDetailsActivity.class);

                intent.putExtra("name",name);
                intent.putExtra("description",description);
                intent.putExtra("term",term);
                intent.putExtra("image",image);
                intent.putExtra("r_id",id);
                intent.putExtra("required_points",required_fit_points);

                context.startActivity(intent);
            }
        });

//        holder.buy_lin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String user_id = String.valueOf(Utilities.getInt(context,"user_id"));
//                String r_idd = String.valueOf(all_rewards.get(position).getId());
//
//                butApi(user_id,r_idd);
//
//            }
//        });
}

    private void butApi(final String u_id,final String rew_id) {



        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id",u_id);
        postParam.put("reward_id",rew_id);
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL+"buy_reward", context, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    Utilities.hideProgressDialog();

                    Utilities.makeToast(context,"Congratulation!, You Purchased That Reward!");


                } else {

                    Utilities.hideProgressDialog();
                    Utilities.makeToast(context,ERROR);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return all_rewards.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name,desrciption,tvRewardPoint;
        LinearLayout reward;



        public MyViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.reward_image);
            name = itemView.findViewById(R.id.reward_name);
            reward= itemView.findViewById(R.id.reward);
            desrciption= itemView.findViewById(R.id.reward_description);
            tvRewardPoint= itemView.findViewById(R.id.tvRewardPoint);
//            buy_lin= itemView.findViewById(R.id.buy_lin);

        }
    }
}
