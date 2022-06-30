package com.mtechsoft.fitmy.v1.activity.reward;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.v1.activity.login.LoginPhoneActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;

public class RewardDetailsActivity extends AppCompatActivity {
    String u_id;
    int r_idd;
    TextView tv_rda_02;
    TextView tv_rda_01,rquired_fitpoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reward_detail);



        u_id = String.valueOf(Utilities.getInt(RewardDetailsActivity.this, "user_id"));
//        r_idd = Utilities.getInt(RewardDetailsActivity.this, "r_idd");
        tv_rda_01 = findViewById(R.id.tv_rda_01);
        rquired_fitpoints = findViewById(R.id.required_fitpoints);
        ImageView small_image = findViewById(R.id.iv_rda_01);
        ImageView large_image = findViewById(R.id.iv_rda_02);
        TextView name = findViewById(R.id.tv_rda_03);
        TextView term_and_condition = findViewById(R.id.tv_rda_11);
        TextView description = findViewById(R.id.tv_rda_09);
        tv_rda_02 = findViewById(R.id.tv_rda_02);


        Intent intent = getIntent();


        name.setText(intent.getStringExtra("name"));
        description.setText(intent.getStringExtra("description"));
        term_and_condition.setText(intent.getStringExtra("term"));
        r_idd = intent.getIntExtra("r_id",0);
        String req_fit_points = intent.getStringExtra("required_points");
        rquired_fitpoints.setText(req_fit_points+"ƒ");


        tv_rda_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                butApi();
            }
        });


        Picasso.get()
                .load(intent.getStringExtra("image"))
                .into(large_image);
        Picasso.get()
                .load(intent.getStringExtra("image"))
                .into(small_image);

//        Double aDouble = Double.valueOf(Utilities.getString(RewardDetailsActivity.this, "fitness_points"));
//        double roundOff = Math.round(aDouble * 100.0) / 100.0;
        tv_rda_01.setText(Utilities.getString(RewardDetailsActivity.this, "fitness_points"));


    }

    private void butApi() {

        final IOSDialog dialog0 = new IOSDialog.Builder(RewardDetailsActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        Toast.makeText(this, R.string.wait, Toast.LENGTH_SHORT).show();

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id", String.valueOf(u_id));
        postParam.put("reward_id", String.valueOf(r_idd));
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "buy_reward", RewardDetailsActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();
                    Utilities.hideProgressDialog();

                    try {

                        JSONObject jsonObject = new JSONObject(String.valueOf(result));
                        int status = jsonObject.getInt("status");
                        if (status == 200){

                           String remaining_fitness_points = jsonObject.getString("remaining_fitness_points");
//                            double roundOff = Math.round(Double.parseDouble(remaining_fitness_points) * 100.0) / 100.0;
                            tv_rda_01.setText(remaining_fitness_points);

                            Utilities.makeToast(RewardDetailsActivity.this, "Congratulation!, You Purchased That Reward!");

                            Utilities.saveString(RewardDetailsActivity.this,"fitness_points",remaining_fitness_points);


                        }



                    }catch (Exception ex){

                        ex.printStackTrace();
                    }



                } else {

                    dialog0.dismiss();
                    Utilities.hideProgressDialog();
                    Utilities.makeToast(RewardDetailsActivity.this, ERROR);
                }
            }
        });


    }
}
