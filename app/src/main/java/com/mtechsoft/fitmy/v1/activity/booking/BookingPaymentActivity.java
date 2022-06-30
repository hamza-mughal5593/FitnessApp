package com.mtechsoft.fitmy.v1.activity.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.MySingleton;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.BookingProcessActivity;
//import com.mtechsoft.fitmy.v1.activity.AsadActivities.PdfViewActivity;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.PdfViewActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingHistoryActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.activity.dashboard.HomeActivity;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.BookingHelper;
import com.mtechsoft.fitmy.v1.common.Constants;

import static com.mtechsoft.fitmy.v1.common.Constants.SP_EFASILITI_TOKEN;

public class BookingPaymentActivity extends AppCompatActivity {

    private String SP_EFASILITI_CART;
    private String refPengurusanVenue;
    TextView tv_payment_total, discount;
    TextView tv_sub_total;
    TextView tv_discount;

    double sub_total = 0.0d;
    double discount_total = 0.0d;
    double payment_total = 0.0d;

    TextView pay;
    String paymnet_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_payment);

        init();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        SP_EFASILITI_CART = sharedPreferences.getString(Constants.SP_EFASILITI_CART, "[]");
        if (SP_EFASILITI_CART.length() == 2) finish();

        prepareHeader();
        prepareList();
        prepareAddMore();


        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mbuilder = new AlertDialog.Builder(BookingPaymentActivity.this);

                View mview = LayoutInflater.from(BookingPaymentActivity.this).inflate(R.layout.payment_method, null);
                RadioButton fpx = mview.findViewById(R.id.fpx);
                RadioButton later = mview.findViewById(R.id.later);
                RelativeLayout proceed = mview.findViewById(R.id.proceed);

                mbuilder.setView(mview);
                final AlertDialog dialog2 = mbuilder.create();
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.show();

                fpx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fpx.setChecked(true);
                        later.setChecked(false);
                        paymnet_type = "1";

                    }
                });

                later.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        fpx.setChecked(false);
                        later.setChecked(true);
                        paymnet_type = "2";

                    }
                });

                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        dialog2.dismiss();
                        SubmitBooking_Api8();

                    }
                });

            }
        });


        discount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int fitpoints = Integer.parseInt(Utilities.getString(BookingPaymentActivity.this, "fitness_points"));

                if (fitpoints != 0) {


                    AlertDialog.Builder mbuilder = new AlertDialog.Builder(BookingPaymentActivity.this);

                    View mview = LayoutInflater.from(BookingPaymentActivity.this).inflate(R.layout.discount_dialog, null);

                    RelativeLayout ok = mview.findViewById(R.id.ok_rel);
                    RelativeLayout undo = mview.findViewById(R.id.undo_rel);
                    EditText points = mview.findViewById(R.id.using_points);
                    TextView total_fitpoints = mview.findViewById(R.id.total_fit_points);

                    mbuilder.setView(mview);
                    final AlertDialog dialog2 = mbuilder.create();
                    dialog2.setCanceledOnTouchOutside(false);
                    dialog2.show();

                    total_fitpoints.setText(String.valueOf(fitpoints));


                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (!points.getText().toString().isEmpty()) {

                                int points_to_use = Integer.parseInt(points.getText().toString());

                                if (points_to_use > fitpoints) {

                                    Toast.makeText(BookingPaymentActivity.this, "You have not enough fitpoints", Toast.LENGTH_SHORT).show();
                                    dialog2.dismiss();
                                } else {


                                    String total_bill = tv_payment_total.getText().toString();
                                    String user_id = String.valueOf(Utilities.getInt(BookingPaymentActivity.this, "user_id"));

                                    discountAPi(user_id, total_bill, String.valueOf(points_to_use));

                                    dialog2.dismiss();
                                }

                            } else {

                                Toast.makeText(BookingPaymentActivity.this, "Enter some value", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    undo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            String discount = tv_discount.getText().toString();

                            if (!discount.equals("0.00")) {


                                String total_bill = tv_payment_total.getText().toString();
                                String user_id = String.valueOf(Utilities.getInt(BookingPaymentActivity.this, "user_id"));
                                String discountttt = tv_discount.getText().toString();


                                undooApi(user_id, total_bill, discountttt);

                                dialog2.dismiss();


                            } else {

                                Toast.makeText(BookingPaymentActivity.this, "Can't Undo", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                } else {
                    Toast.makeText(BookingPaymentActivity.this, "You have no points to use", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void undooApi(String user_id, String total_bill, String discountttt) {


        final IOSDialog dialog0 = new IOSDialog.Builder(BookingPaymentActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "reverse_discount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    dialog0.dismiss();

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    int status = jsonObject.getInt("status");
                    if (status == 200) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");


                        String baqi_bill = jsonObject1.getString("reversed_amount");
                        String total_points = jsonObject1.getString("total_fitness_points");

                        Utilities.saveString(BookingPaymentActivity.this, "fitness_points", total_points);

                        tv_payment_total.setText(baqi_bill);
                        tv_discount.setText("0.00");

                    }


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
                if (BookingPaymentActivity.this != null)
                    Toast.makeText(BookingPaymentActivity.this, message, Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postParam = new HashMap<>();

                postParam.put("user_id", user_id);
                postParam.put("discounted_amount", total_bill);
                postParam.put("deducted_amount", discountttt);

                return postParam;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");

                return params;
            }
        };

        dramarequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(BookingPaymentActivity.this).addToRequestQueue(dramarequest);


    }

    private void discountAPi(final String user_idd, final String total_bill, final String points_to_use) {

        final IOSDialog dialog0 = new IOSDialog.Builder(BookingPaymentActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "get_discount", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    dialog0.dismiss();

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    int status = jsonObject.getInt("status");
                    if (status == 200) {

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");


                        String baqi_bill = jsonObject1.getString("discounted_amount");
                        String baqi_points = jsonObject1.getString("remaining_points");
                        String discount = jsonObject1.getString("deducted_amount");

                        Utilities.saveString(BookingPaymentActivity.this, "fitness_points", baqi_points);

                        tv_payment_total.setText(baqi_bill);
                        tv_discount.setText(discount);

                    }


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
                if (BookingPaymentActivity.this != null)
                    Toast.makeText(BookingPaymentActivity.this, message, Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postParam = new HashMap<>();

                postParam.put("user_id", user_idd);
                postParam.put("using_points", points_to_use);
                postParam.put("total_amount", total_bill);

                return postParam;

            }


            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");

                return params;
            }
        };

        dramarequest.setRetryPolicy(new DefaultRetryPolicy(
                25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleton.getInstance(BookingPaymentActivity.this).addToRequestQueue(dramarequest);


    }

    private void SubmitBooking_Api8() {


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);

        String complex_id = Utilities.getString(BookingPaymentActivity.this, "complex_id");
        String facility_id = String.valueOf(Utilities.getInt(BookingPaymentActivity.this, "faciility_id"));
        String jenis_tmepahan_id = paymnet_type;
        String negeri_id = "1";
        String tarikh_mula = Utilities.getString(BookingPaymentActivity.this, "start_date");
        String tarikh_akhir = Utilities.getString(BookingPaymentActivity.this, "end_date");
        String waktu_mula = Utilities.getString(BookingPaymentActivity.this, "start_time");
        String waktu_akhir = Utilities.getString(BookingPaymentActivity.this, "end_time");
        String total_value = "";
        String item_name = "";
        String item_value = "";
        String item_unit = "";
        String bayaran_sewa = "100";
        String quantity_kadar = "2";
        String jumlah_orang = Utilities.getString(BookingPaymentActivity.this, "no_of_people");
        String bil_unit = Utilities.getString(BookingPaymentActivity.this, "bill_unit");


        final IOSDialog dialog0 = new IOSDialog.Builder(BookingPaymentActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        JSONObject acds = new JSONObject();


//        Utilities.showProgressDialog(getContext(),"please wait...");

        final StringRequest dramarequest = new StringRequest(Request.Method.POST, "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/create", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    dialog0.dismiss();

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    int iddd = jsonObject.getInt("id");

                    APi9(String.valueOf(iddd));
                    startActivity(new Intent(BookingPaymentActivity.this, BookingHistoryActivity.class));


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
                if (BookingPaymentActivity.this != null)
                    Toast.makeText(BookingPaymentActivity.this, message, Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postParam = new HashMap<>();

                postParam.put("kompleks_id", complex_id);
                postParam.put("negeri_id", negeri_id);
                postParam.put("fasiliti_id", facility_id);
                postParam.put("jenis_tempahan_id", jenis_tmepahan_id);
                postParam.put("tarikh_mula", tarikh_mula);
                postParam.put("tarikh_akhir", tarikh_akhir);
                postParam.put("waktu_mula", waktu_mula);
                postParam.put("waktu_akhir", waktu_akhir);
                postParam.put("tatal_value", "");
                postParam.put("item_name", "");
                postParam.put("item_value", "");
                postParam.put("item_unit", "");
                postParam.put("bayaran_sewa", bayaran_sewa);
                postParam.put("quantity_kadar", quantity_kadar);
                postParam.put("jumlah_orang", jumlah_orang);
                postParam.put("bil_unit", bil_unit);

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

        MySingleton.getInstance(BookingPaymentActivity.this).addToRequestQueue(dramarequest);
    }

    private void APi9(final String idd) {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);

        String email = Utilities.getString(BookingPaymentActivity.this, "email");
        String phone = Utilities.getString(BookingPaymentActivity.this, "phone");
        String jumlah_bayarannnn = tv_payment_total.getText().toString();


        final IOSDialog dialog0 = new IOSDialog.Builder(BookingPaymentActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.POST, "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/pengesahan", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    dialog0.dismiss();

                    JSONObject jsonObject = new JSONObject(String.valueOf(response));

                    int iddddd = jsonObject.getInt("id");


                    String urll = "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/fpx&id=" + String.valueOf(iddddd);

                    if (paymnet_type.equals("1")) {

                        Intent intent = new Intent(BookingPaymentActivity.this, BookingProcessActivity.class);
                        intent.putExtra("fpx_url", urll);
                        startActivity(intent);

//                        Intent viewIntent =
//                                new Intent("android.intent.action.VIEW",
//                                        Uri.parse("http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/fpx&id="+iddddd));
//                        startActivity(viewIntent);

                    } else {

                        AlertDialog.Builder mbuilder = new AlertDialog.Builder(BookingPaymentActivity.this);

                        View mview = LayoutInflater.from(BookingPaymentActivity.this).inflate(R.layout.later_dialog, null);

                        RelativeLayout ok_rel = mview.findViewById(R.id.ok);

                        mbuilder.setView(mview);
                        final AlertDialog dialog2 = mbuilder.create();
                        dialog2.setCanceledOnTouchOutside(false);
                        dialog2.show();

                        ok_rel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                dialog2.dismiss();

                                Api13(iddddd);

                            }
                        });


                    }


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
                if (BookingPaymentActivity.this != null)
                    Toast.makeText(BookingPaymentActivity.this, message, Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> postParam = new HashMap<>();

                postParam.put("id", idd);
                postParam.put("jenis_bayaran_id", paymnet_type);
                postParam.put("jumlah_bayaran", jumlah_bayarannnn);
                postParam.put("no_tel_pengguna", phone);
                postParam.put("email_pengguna", email);

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

        MySingleton.getInstance(BookingPaymentActivity.this).addToRequestQueue(dramarequest);


    }

    private void Api13(final int id) {


        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);

        final IOSDialog dialog0 = new IOSDialog.Builder(BookingPaymentActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();


        final StringRequest dramarequest = new StringRequest(Request.Method.GET, "http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/cetak-tempahan&id=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("File not available")) {

                    Toast.makeText(BookingPaymentActivity.this, "Booking Form Not Available", Toast.LENGTH_SHORT).show();
                } else {


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String link = jsonObject.getString("link");
                        Intent intent = new Intent(BookingPaymentActivity.this, PdfViewActivity.class);
                        intent.putExtra("form_link", link);
                        startActivity(intent);


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
                if (BookingPaymentActivity.this != null)
                    Toast.makeText(BookingPaymentActivity.this, message, Toast.LENGTH_LONG).show();

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

        MySingleton.getInstance(BookingPaymentActivity.this).addToRequestQueue(dramarequest);

    }


    private void init() {

        pay = findViewById(R.id.tv_abp_submit);
        discount = findViewById(R.id.tv_abp_total);

    }

    private void prepareAddMore() {
        TextView tv_abp_add = findViewById(R.id.tv_abp_add);
        tv_abp_add.setOnClickListener(view -> goBookingDetail());
    }

    private void goBookingDetail() {
        Intent intent = new Intent(this, BookingDetailsActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, refPengurusanVenue);
        startActivity(intent);
    }

    private void prepareHeader() {
        try {
            JSONArray jsonArray = new JSONArray(SP_EFASILITI_CART);
            if (jsonArray.length() > 0) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String nama_venue = jsonObject.getString("nama_venue");
                refPengurusanVenue = jsonObject.getString("refPengurusanVenue");

                TextView tv_abp_name = findViewById(R.id.tv_abp_name);
                tv_abp_name.setText(nama_venue);
            }
        } catch (JSONException ex) {
            // TODO
        }
    }

    private void prepareList() {
        LinearLayout ll_invoice = findViewById(R.id.ll_invoice);
        tv_sub_total = findViewById(R.id.tv_sub_total);
        tv_discount = findViewById(R.id.tv_discount);
        tv_payment_total = findViewById(R.id.tv_payment_total);


        DecimalFormat df = new DecimalFormat("0.00");

        try {
            JSONArray jsonArray = new JSONArray(SP_EFASILITI_CART);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                LayoutInflater inflater = getLayoutInflater();
                View rowView = inflater.inflate(R.layout.listitem_booking_invoice, null, true);

                TextView tv_desc = rowView.findViewById(R.id.tv_desc);
                TextView tv_unit = rowView.findViewById(R.id.tv_unit);
                TextView tv_total = rowView.findViewById(R.id.tv_total);
                TextView tv_date = rowView.findViewById(R.id.tv_date);
                TextView tv_rate = rowView.findViewById(R.id.tv_rate);

                String refSukanRekreasiDesc = jsonObject.getString("refSukanRekreasiDesc");
                String refJenisKemudahanDesc = jsonObject.getString("refJenisKemudahanDesc");
                String totalAmount = jsonObject.getString("totalAmount");
                String startDate = jsonObject.getString("startDate");
                String endDate = jsonObject.getString("endDate");
                String unit = jsonObject.getString("unit");
                String unitLabel = jsonObject.getString("unitLabel");
                String rate = jsonObject.getString("rate");

                tv_desc.setText(refJenisKemudahanDesc + " - " + refSukanRekreasiDesc);
                tv_unit.setText(unit);
                tv_total.setText(df.format(Double.parseDouble(totalAmount)));
                tv_date.setText(startDate + " --- " + endDate);
                tv_rate.setText(rate + " --- " + unit + " " + unitLabel);

                ll_invoice.addView(rowView);

                sub_total += Double.parseDouble(totalAmount);
                payment_total += Double.parseDouble(totalAmount);
            }

            tv_sub_total.setText(df.format(sub_total));
            tv_discount.setText(df.format(discount_total));
            tv_payment_total.setText(df.format(payment_total));
        } catch (JSONException ex) {
            // TODO
            String e = ex.getMessage();
        }
    }


}
