package com.mtechsoft.fitmy.v1.activity.dashboard;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.Booking;
import com.mtechsoft.fitmy.v1.MySingleton;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsEventActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.AsNewsActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.BookingProcessActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.booking.BookingPaymentActivity;
import com.mtechsoft.fitmy.v1.activity.booking.BookingReceiptActivity;
import com.mtechsoft.fitmy.v1.activity.fitness.FitnessHistory;
import com.mtechsoft.fitmy.v1.adapter.AllRewardsAdapter;
import com.mtechsoft.fitmy.v1.adapter.BookingHistoryAdapter;
import com.mtechsoft.fitmy.v1.common.Constants;

public class BookingHistoryActivity extends AppCompatActivity {
    private JSONArray responseArray;
    private LinearLayout ll_abh_list;
    private ImageView imageView;
    private LinearLayout navLayout;
    private View coverView;

    ArrayList<Booking> bookings;
    RecyclerView recyclerView;
    TextView tv_abh_all_bookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        bookings = new ArrayList<>();
        recyclerView = findViewById(R.id.booking_history_rv);
        tv_abh_all_bookings = findViewById(R.id.tv_abh_all_bookings);

        tv_abh_all_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(BookingHistoryActivity.this,BookingActivity.class);
                startActivity(intent);
            }
        });


        apicall();

        prepareNavBar();


        navLayout = findViewById(R.id.navLayout);
        coverView = findViewById(R.id.coverView);
        imageView = findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navLayout.setVisibility(View.VISIBLE);
                coverView.setVisibility(View.VISIBLE);
            }
        });

        coverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navLayout.setVisibility(View.GONE);
                coverView.setVisibility(View.GONE);
            }
        });
    }

    private void apicall() {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);



        final IOSDialog dialog0 = new IOSDialog.Builder(BookingHistoryActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.POST, "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/get-booking-history", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    dialog0.dismiss();

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    JSONArray data = jsonObject.getJSONArray("data");

                    for (int i=data.length() - 1; i >= 0; i--){

                        JSONObject jsonObject1 = data.getJSONObject(i);

                        Booking booking = new Booking();

                        int id = jsonObject1.getInt("id");
                        String order_number = jsonObject1.getString("order_no");
                        String date = jsonObject1.getString("tarikh_tempahan");

                        booking.setId(id);
                        booking.setName(order_number);
                        booking.setDescriotion(date);

                        bookings.add(booking);

                    }


                    BookingHistoryAdapter bookingHistoryAdapter = new BookingHistoryAdapter(bookings, BookingHistoryActivity.this);
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(BookingHistoryActivity.this);
                    recyclerView.setLayoutManager(layoutManager2);
                    recyclerView.setAdapter(bookingHistoryAdapter);








                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Utilities.hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//
                dialog0.dismiss();
//                Utilities.hideProgressDialog();
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                if (BookingHistoryActivity.this != null)
                    Toast.makeText(BookingHistoryActivity.this, message, Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postParam = new HashMap<>();


                return postParam;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer " + SP_EFASILITI_TOKEN);

                return params;
            }
        };

        dramarequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(BookingHistoryActivity.this).addToRequestQueue(dramarequest);



    }

    private void prepareNavBar() {
        View.OnClickListener onClickListener = view -> {

            switch (view.getId()) {
                case R.id.v_nav_home:
                case R.id.iv_nav_home:
                case R.id.tv_nav_home:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, FitnessActivity.class));
                    break;
                case R.id.v_nav_fitness:
                case R.id.iv_nav_fitness:
                case R.id.tv_nav_fitness:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, BookingActivity.class));
                    break;
                case R.id.v_nav_reward:
                case R.id.iv_nav_reward:
                case R.id.tv_nav_reward:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, RewardActivity.class));
                    break;
                case R.id.v_nav_event:
                case R.id.iv_nav_event:
                case R.id.tv_nav_event:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, AsEventActivity.class));
                    break;
                case R.id.v_nav_booking:
                case R.id.iv_nav_booking:
                case R.id.tv_nav_booking:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, HomeActivity.class));
                    break;
                case R.id.v_nav_history:
                case R.id.iv_nav_history:
                case R.id.tv_nav_history:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, FitnessHistory.class));
                    break;

                case R.id.v_nav_news:
                case R.id.iv_nav_news:
                case R.id.tv_nav_news:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    startActivity(new Intent(this, AsNewsActivity.class));
                    break;
                case R.id.v_nav_gallery:
                case R.id.iv_nav_gallery:
                case R.id.tv_nav_gallery:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    Dialogue();
                    break;
                case R.id.v_nav_music:
                case R.id.iv_nav_music:
                case R.id.tv_nav_music:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    // startActivity(new Intent(this, AudioActivity.class));

                    Dialogue();
                    break;
                case R.id.v_nav_video:
                case R.id.iv_nav_video:
                case R.id.tv_nav_video:
                    navLayout.setVisibility(View.GONE);
                    coverView.setVisibility(View.GONE);
                    //startActivity(new Intent(this, VideoActivity.class));
                    Dialogue();
                    break;

            }

//            finish();
        };

        ImageView iv_nav_home = findViewById(R.id.iv_nav_home);
        ImageView iv_nav_fitness = findViewById(R.id.iv_nav_fitness);
        ImageView iv_nav_reward = findViewById(R.id.iv_nav_reward);
        ImageView iv_nav_event = findViewById(R.id.iv_nav_event);
        ImageView iv_nav_booking = findViewById(R.id.iv_nav_booking);
        ImageView iv_nav_history = findViewById(R.id.iv_nav_history);
        ImageView iv_nav_news = findViewById(R.id.iv_nav_news);
        ImageView iv_nav_gallery = findViewById(R.id.iv_nav_gallery);
        ImageView iv_nav_music = findViewById(R.id.iv_nav_music);
        ImageView iv_nav_video = findViewById(R.id.iv_nav_video);
        iv_nav_home.setOnClickListener(onClickListener);
        iv_nav_fitness.setOnClickListener(onClickListener);
        iv_nav_reward.setOnClickListener(onClickListener);
        iv_nav_event.setOnClickListener(onClickListener);
        iv_nav_booking.setOnClickListener(onClickListener);
        iv_nav_history.setOnClickListener(onClickListener);
        iv_nav_news.setOnClickListener(onClickListener);
        iv_nav_gallery.setOnClickListener(onClickListener);
        iv_nav_music.setOnClickListener(onClickListener);
        iv_nav_video.setOnClickListener(onClickListener);

        TextView tv_nav_home = findViewById(R.id.tv_nav_home);
        TextView tv_nav_fitness = findViewById(R.id.tv_nav_fitness);
        TextView tv_nav_reward = findViewById(R.id.tv_nav_reward);
        TextView tv_nav_event = findViewById(R.id.tv_nav_event);
        TextView tv_nav_booking = findViewById(R.id.tv_nav_booking);
        TextView tv_nav_history = findViewById(R.id.tv_nav_history);
        TextView tv_nav_news = findViewById(R.id.tv_nav_news);
        TextView tv_nav_gallery = findViewById(R.id.tv_nav_gallery);
        TextView tv_nav_music = findViewById(R.id.tv_nav_music);
        TextView tv_nav_video = findViewById(R.id.tv_nav_video);
        tv_nav_home.setOnClickListener(onClickListener);
        tv_nav_fitness.setOnClickListener(onClickListener);
        tv_nav_reward.setOnClickListener(onClickListener);
        tv_nav_event.setOnClickListener(onClickListener);
        tv_nav_booking.setOnClickListener(onClickListener);
        tv_nav_history.setOnClickListener(onClickListener);
        tv_nav_news.setOnClickListener(onClickListener);
        tv_nav_gallery.setOnClickListener(onClickListener);
        tv_nav_music.setOnClickListener(onClickListener);
        tv_nav_video.setOnClickListener(onClickListener);

        View v_nav_home = findViewById(R.id.v_nav_home);
        View v_nav_fitness = findViewById(R.id.v_nav_fitness);
        View v_nav_reward = findViewById(R.id.v_nav_reward);
        View v_nav_event = findViewById(R.id.v_nav_event);
        View v_nav_booking = findViewById(R.id.v_nav_booking);
        View v_nav_history = findViewById(R.id.v_nav_history);
        View v_nav_news = findViewById(R.id.v_nav_news);
        View v_nav_gallery = findViewById(R.id.v_nav_gallery);
        View v_nav_music = findViewById(R.id.v_nav_music);
        View v_nav_video = findViewById(R.id.v_nav_video);
        v_nav_home.setOnClickListener(onClickListener);
        v_nav_fitness.setOnClickListener(onClickListener);
        v_nav_reward.setOnClickListener(onClickListener);
        v_nav_event.setOnClickListener(onClickListener);
        v_nav_booking.setOnClickListener(onClickListener);
        v_nav_history.setOnClickListener(onClickListener);
        v_nav_news.setOnClickListener(onClickListener);
        v_nav_gallery.setOnClickListener(onClickListener);
        v_nav_music.setOnClickListener(onClickListener);
        v_nav_video.setOnClickListener(onClickListener);
    }


    public void Dialogue() {

        new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.comming_soon))
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }



}
