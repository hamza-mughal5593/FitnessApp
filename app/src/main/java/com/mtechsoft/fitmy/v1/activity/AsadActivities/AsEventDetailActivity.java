package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.booking.BookingPaymentActivity;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.Constants;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.squareup.picasso.Picasso;

import com.mtechsoft.fitmy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AsEventDetailActivity extends AppCompatActivity {

    ImageView imageView,large_image,register_now;
    TextView description,terms_and_condtion,namee,event_date,event_location;

    RelativeLayout going_rel,not_going_rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_event_detail);

        imageView = findViewById(R.id.event_image);
        description = findViewById(R.id.event_description);
        terms_and_condtion =findViewById(R.id.event_term_and_condition);
        namee =findViewById(R.id.event_name);
        large_image =findViewById(R.id.event_large_image);
        register_now =findViewById(R.id.register_now);
        event_date =findViewById(R.id.event_date);
        event_location =findViewById(R.id.event_location);
        going_rel =findViewById(R.id.going_rel);
        not_going_rel =findViewById(R.id.not_going_rel);

        Intent intent = getIntent();

        String name = intent.getStringExtra("event_name");
        String image = intent.getStringExtra("event_image");
        String desc = intent.getStringExtra("event_des");
        String term = intent.getStringExtra("event_term");
        String data = intent.getStringExtra("date");
        String loc = intent.getStringExtra("location");
        String url = intent.getStringExtra("url");
        String id = intent.getStringExtra("id");

        if (url.equals("null")){

            register_now.setVisibility(View.GONE);
        }

        Picasso.get()
                .load(image)
                .into(imageView);

        Picasso.get()
                .load(image)
                .into(large_image);

        description.setText(desc);
        terms_and_condtion.setText(term);
        namee.setText(name);
        event_date.setText(data);
        event_location.setText(loc);

        register_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });

        going_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                going_rel.setBackground(ContextCompat.getDrawable(AsEventDetailActivity.this, R.drawable.going_bg));
                not_going_rel.setBackground(ContextCompat.getDrawable(AsEventDetailActivity.this, R.drawable.going_bg_unselcted));


                goiongApi(id);

            }
        });

        not_going_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                going_rel.setBackground(ContextCompat.getDrawable(AsEventDetailActivity.this, R.drawable.going_bg_unselcted));
                not_going_rel.setBackground(ContextCompat.getDrawable(AsEventDetailActivity.this, R.drawable.going_bg));

                notgoingApi(id);

            }
        });


    }

    private void notgoingApi(String id) {

        final IOSDialog dialog0 = new IOSDialog.Builder(AsEventDetailActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        String user_id = String.valueOf(Utilities.getInt(AsEventDetailActivity.this,"user_id"));


        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id",user_id);
        postParam.put("event_id",id);
        postParam.put("status","Not Going");

        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"event_status", AsEventDetailActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){


                            Toast.makeText(AsEventDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                        }else if (status == 400 ){


                            Toast.makeText(AsEventDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(AsEventDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }


                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });






    }

    private void goiongApi(String id) {



        final IOSDialog dialog0 = new IOSDialog.Builder(AsEventDetailActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        String user_id = String.valueOf(Utilities.getInt(AsEventDetailActivity.this,"user_id"));



        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id",user_id);
        postParam.put("event_id",id);
        postParam.put("status","Going");


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept","application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"event_status", AsEventDetailActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            Toast.makeText(AsEventDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }else if (status == 400 ){


                            Toast.makeText(AsEventDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(AsEventDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {

                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {

                    dialog0.dismiss();

                }
            }
        });






    }

}
