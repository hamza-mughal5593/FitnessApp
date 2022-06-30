package com.mtechsoft.fitmy.v1.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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
import com.mtechsoft.fitmy.v1.Models.All_Rewards;
import com.mtechsoft.fitmy.v1.Models.Booking;
import com.mtechsoft.fitmy.v1.MySingleton;
import com.mtechsoft.fitmy.v1.Utilities;
//import com.mtechsoft.fitmy.v1.activity.AsadActivities.PdfViewActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.PdfViewActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.ViewBookingActivity;
import com.mtechsoft.fitmy.v1.activity.booking.BookingPaymentActivity;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.mtechsoft.fitmy.v1.activity.reward.RewardDetailsActivity;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class BookingHistoryAdapter extends RecyclerView.Adapter<BookingHistoryAdapter.MyViewHolder> {

    List<Booking> bookings;
    Context context;


    TextView name,email,tel,date,order_no,reference_no,transaction_no,statusss;

    public BookingHistoryAdapter(List<Booking> bookings, Context context) {
        this.bookings = bookings;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.listitem_booking_facilities, parent, false);
        return new BookingHistoryAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.name.setText(bookings.get(position).getName());
        holder.desrciption.setText(bookings.get(position).getDescriotion());

        holder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int idd = bookings.get(position).getId();


                BookingDetailApi(idd);

            }
        });


    }

    private void BookingDetailApi(final int id) {


        SharedPreferences sharedPreferences =context.getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);


        String url = "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/view&id="+String.valueOf(id);

        final IOSDialog dialog0 = new IOSDialog.Builder(context)
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
                    JSONArray tempahan_data = jsonObject.getJSONArray("tempahan-data");



                    String nama_pengguna = main_date.getString("nama_pengguna");
                    String email_pengguna = main_date.getString("email_pengguna");
                    String tell = main_date.getString("no_tel_pengguna");
                    String order_noo = main_date.getString("order_no");
                    String tarikh_tempahan = main_date.getString("tarikh_tempahan");

                    String reference_noo = payment_data.getString("reference_no");
                    String transaction_noo = payment_data.getString("transaction_no");


                    JSONObject jsonObject1 = tempahan_data.getJSONObject(0);
                    String status = jsonObject1.getString("status");


                    AlertDialog.Builder mbuilder=new AlertDialog.Builder(context);

                    View mview = LayoutInflater.from(context).inflate(R.layout.view_booking_dialog,null);

                    RelativeLayout ok_rel=mview.findViewById(R.id.ok_rel);
                    RelativeLayout view_recipt=mview.findViewById(R.id.view_reciept_rel);

                    name = mview.findViewById(R.id.name);
                    email = mview.findViewById(R.id.email);
                    tel =  mview.findViewById(R.id.tel_no);
                    date = mview.findViewById(R.id.date);
                    order_no = mview.findViewById(R.id.order_no);
                    reference_no = mview.findViewById(R.id.reference_no);
                    transaction_no = mview.findViewById(R.id.transaction_no);
                    statusss = mview.findViewById(R.id.status);



                    name.setText(nama_pengguna);
                    email.setText(email_pengguna);
                    tel.setText(tell);
                    order_no.setText(order_noo);
                    reference_no.setText(reference_noo);
                    transaction_no.setText(transaction_noo);
                    date.setText(tarikh_tempahan);

                    if (status.equals("1")){

                        statusss.setText("Paid");

                    }else if (status.equals("2")){

                        statusss.setText("Not Yet Paid");
                    }else {

                        statusss.setText("Payment Unsuccessfull");

                    }


                    mbuilder.setView(mview);
                    final AlertDialog dialog2= mbuilder.create();
                    dialog2.setCanceledOnTouchOutside(false);
                    dialog2.show();

                    ok_rel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog2.dismiss();
                        }
                    });


                    view_recipt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog2.dismiss();

                            if (status.equals("2") || status.equals("3")){

                                Toast.makeText(context, "Not Paid yet", Toast.LENGTH_SHORT).show();
                            }else {

                                Api14(id);
                            }


                        }
                    });






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
                if (context != null)
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();

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

        MySingleton.getInstance(context).addToRequestQueue(dramarequest);




    }


    private void Api14(int id) {


        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);

        final IOSDialog dialog0 = new IOSDialog.Builder(context)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.GET, "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/cetak-resit&id="+id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                dialog0.dismiss();
                if (response.equals("File not available")){

                    Toast.makeText(context, "Reciept Not Available", Toast.LENGTH_SHORT).show();
                }else {


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String link = jsonObject.getString("link");
                        Intent intent = new Intent(context, PdfViewActivity.class);
                        intent.putExtra("form_link",link);
                        context.startActivity(intent);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


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
                if (context != null)
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();

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

        MySingleton.getInstance(context).addToRequestQueue(dramarequest);


    }


    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        ImageView image;
        TextView name, desrciption;
        RelativeLayout main_layout;


        public MyViewHolder(View itemView) {
            super(itemView);


            image = itemView.findViewById(R.id.iv_bf_li_01);
            name = itemView.findViewById(R.id.tv_bf_li_01);
            desrciption = itemView.findViewById(R.id.tv_bf_li_02);
            main_layout = itemView.findViewById(R.id.main_layout);

        }
    }
}
