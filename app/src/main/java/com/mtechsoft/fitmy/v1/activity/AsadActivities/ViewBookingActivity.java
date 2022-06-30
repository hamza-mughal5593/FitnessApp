package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingHistoryActivity;
import com.mtechsoft.fitmy.v1.adapter.BookingHistoryAdapter;
import com.mtechsoft.fitmy.v1.common.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewBookingActivity extends AppCompatActivity {

    TextView name,email,tel,date,order_no,reference_no,transaction_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_booking);

        initii();

        int id = getIntent().getIntExtra("id",0);
        ApiCallll(id);

    }

    private void ApiCallll(final int id) {


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);


        String url = "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/view&id="+String.valueOf(id);

        final IOSDialog dialog0 = new IOSDialog.Builder(ViewBookingActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    dialog0.dismiss();

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    JSONObject main_date = jsonObject.getJSONObject("main-data");
                    JSONObject payment_data = jsonObject.getJSONObject("payment-data");


                    String nama_pengguna = main_date.getString("nama_pengguna");
                    String email_pengguna = main_date.getString("email_pengguna");
                    String tell = main_date.getString("no_tel_pengguna");
                    String order_noo = main_date.getString("order_no");
                    String tarikh_tempahan = main_date.getString("tarikh_tempahan");

                    String reference_noo = payment_data.getString("reference_no");
                    String transaction_noo = payment_data.getString("transaction_no");


                    name.setText(nama_pengguna);
                    email.setText(email_pengguna);
                    tel.setText(tell);
                    order_no.setText(order_noo);
                    reference_no.setText(reference_noo);
                    transaction_no.setText(transaction_noo);
                    date.setText(tarikh_tempahan);



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
                if (ViewBookingActivity.this != null)
                    Toast.makeText(ViewBookingActivity.this, message, Toast.LENGTH_LONG).show();

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

        MySingleton.getInstance(ViewBookingActivity.this).addToRequestQueue(dramarequest);




    }

    private void initii() {

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        tel = findViewById(R.id.tel_no);
        date = findViewById(R.id.date);
        order_no = findViewById(R.id.order_no);
        reference_no = findViewById(R.id.reference_no);
        transaction_no = findViewById(R.id.transaction_no);


    }
}
