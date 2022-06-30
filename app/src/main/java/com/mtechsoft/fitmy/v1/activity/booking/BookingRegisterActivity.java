package com.mtechsoft.fitmy.v1.activity.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.MySingleton;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.onboarding.OnboardingProfileActivity;
import com.mtechsoft.fitmy.v1.common.BookingHelper;

import java.util.HashMap;
import java.util.Map;

public class BookingRegisterActivity extends AppCompatActivity {

    private TextView tv_abr_08;
    ImageView b_abr_back;


    EditText et_abr_nric;
    EditText et_abr_postcode;
    EditText et_abr_city;
    EditText et_abr_password;
    EditText et_abr_email;
    EditText et_abr_fullname;
    EditText et_abr_address;
    EditText et_abr_phone;
    Spinner s_abr_jenis_pengguna;
    Spinner s_abr_nationality;
    Spinner s_abr_gender;
    Spinner s_abr_state;
    Spinner s_abr_race;
    Spinner s_abr_country;
    Spinner s_abr_jenis_kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_register);


        initt();
        b_abr_back = findViewById(R.id.b_abr_back);
        b_abr_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        prepareLoadingDialog();
        prepareForm();

        setData();

    }

    private void setData() {

        et_abr_fullname.setText(Utilities.getString(BookingRegisterActivity.this, "user_name"));
        et_abr_email.setText(Utilities.getString(BookingRegisterActivity.this, "email"));
        et_abr_phone.setText(Utilities.getString(BookingRegisterActivity.this, "phone"));
    }

    private void initt() {

        et_abr_nric = findViewById(R.id.et_abr_nric);
        et_abr_postcode = findViewById(R.id.et_abr_postcode);
        et_abr_city = findViewById(R.id.et_abr_city);
        et_abr_password = findViewById(R.id.et_abr_password);
        et_abr_email = findViewById(R.id.et_abr_email);
        et_abr_fullname = findViewById(R.id.et_abr_fullname);
        et_abr_address = findViewById(R.id.et_abr_address);
        et_abr_phone = findViewById(R.id.et_abr_phone);
        s_abr_jenis_pengguna = findViewById(R.id.s_abr_jenis_pengguna);
        s_abr_nationality = findViewById(R.id.s_abr_nationality);
        s_abr_gender = findViewById(R.id.s_abr_gender);
        s_abr_state = findViewById(R.id.s_abr_state);
        s_abr_race = findViewById(R.id.s_abr_race);
        s_abr_country = findViewById(R.id.s_abr_country);
        s_abr_jenis_kategori = findViewById(R.id.s_abr_jenis_kategori);

    }

    private void prepareForm() {


        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                R.array.s_abr_jenis_pengguna, R.layout.spinner_item_new);
        adapters.setDropDownViewResource(R.layout.spinner_item_new);
        s_abr_jenis_pengguna.setAdapter(adapters);


        ArrayAdapter<CharSequence> adaptersss = ArrayAdapter.createFromResource(this,
                R.array.s_abr_jenis_kategori, R.layout.spinner_item_new);
        adaptersss.setDropDownViewResource(R.layout.spinner_item_new);
        s_abr_jenis_kategori.setAdapter(adaptersss);


        ArrayAdapter<CharSequence> adaptersss3 = ArrayAdapter.createFromResource(this,
                R.array.s_abr_gender, R.layout.spinner_item_new);
        adaptersss3.setDropDownViewResource(R.layout.spinner_item_new);
        s_abr_gender.setAdapter(adaptersss3);



        ArrayAdapter<CharSequence> adaptersss4 = ArrayAdapter.createFromResource(this,
                R.array.s_abr_race, R.layout.spinner_item_new);
        adaptersss4.setDropDownViewResource(R.layout.spinner_item_new);
        s_abr_race.setAdapter(adaptersss4);


  ArrayAdapter<CharSequence> adaptersss5 = ArrayAdapter.createFromResource(this,
                R.array.s_abr_nationality, R.layout.spinner_item_new);
        adaptersss5.setDropDownViewResource(R.layout.spinner_item_new);
        s_abr_nationality.setAdapter(adaptersss5);


        Button b_abr_submit = findViewById(R.id.b_abr_submit);
        b_abr_submit.setOnClickListener(view -> doSubmit());

        tv_abr_08 = findViewById(R.id.tv_abr_08);

    }

    private void doSubmit() {

        final String nric = et_abr_nric.getText().toString();
        final String postcode = et_abr_postcode.getText().toString();
        final String city = et_abr_city.getText().toString();
        final String password = et_abr_password.getText().toString();
        final String email = et_abr_email.getText().toString();
        final String fullname = et_abr_fullname.getText().toString();
        final String address = et_abr_address.getText().toString();
        final String phone = et_abr_phone.getText().toString();

        final int jenis_pengguna = s_abr_jenis_pengguna.getSelectedItemPosition() + 1;
        final int nationality = s_abr_nationality.getSelectedItemPosition() + 1;
        final int gender = s_abr_gender.getSelectedItemPosition() + 1;
        final int race = s_abr_race.getSelectedItemPosition() + 1;
        final int country = s_abr_country.getSelectedItemPosition() + 158; // 158 = MALAYSIA
        final int jenis_kategori = s_abr_jenis_kategori.getSelectedItemPosition() + 1;

        int state = 14;
        String s = s_abr_state.getSelectedItem().toString();
        if ("WP Kuala Lumpur".equals(s)) state = 14;
        else if ("WP Putrajaya".equals(s)) state = 16;
        else if ("WP Labuan".equals(s)) state = 15;
        else if ("Perlis".equals(s)) state = 9;
        else if ("Kedah".equals(s)) state = 2;
        else if ("Selangor".equals(s)) state = 10;
        else if ("Johor".equals(s)) state = 1;
        else if ("Negeri Sembilan".equals(s)) state = 5;
        else if ("Pahang".equals(s)) state = 6;
        else if ("Melaka".equals(s)) state = 4;
        else if ("Sabah".equals(s)) state = 12;
        else if ("Sarawak".equals(s)) state = 13;
        else if ("Perak".equals(s)) state = 8;
        else if ("Kelantan".equals(s)) state = 3;
        else if ("Terengganu".equals(s)) state = 11;
        else if ("Pulau Pinang".equals(s)) state = 7;
        final int stateCode = state;


        if (nric.length() > 0 && postcode.length() > 0 && city.length() > 0 && password.length() > 0 && email.length() > 0 && fullname.length() > 0 && address.length() > 0 && phone.length() > 0) {

//            if (nric.length() == 6){


                showLoadingDialog();

                final StringRequest dramarequest = new StringRequest(Request.Method.POST, "https://internal.mysysdemo.com/backend/web/index.php?r=api/user-public/create", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            dismissLoadingDialog();

                            JSONObject jsonObject = new JSONObject(String.valueOf(response));

                            boolean status = jsonObject.getBoolean("status");
                            if (status){


                                AlertDialog.Builder mbuilder=new AlertDialog.Builder(BookingRegisterActivity.this);

                                View mview = LayoutInflater.from(BookingRegisterActivity.this).inflate(R.layout.signup_dialog,null);
                                RelativeLayout ok_rel=mview.findViewById(R.id.ok_rel);
                                mbuilder.setView(mview);
                                final AlertDialog dialog2= mbuilder.create();
                                dialog2.setCanceledOnTouchOutside(false);
                                dialog2.show();

                                ok_rel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        dialog2.dismiss();
                                        finish();
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

                        dismissLoadingDialog();

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
                        if (BookingRegisterActivity.this != null)
                            Toast.makeText(BookingRegisterActivity.this, message, Toast.LENGTH_LONG).show();

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();

                        params.put("username",nric);
                        params.put("email", email);
                        params.put("tel_bimbit_no", phone);
                        params.put("full_name", fullname);
                        params.put("alamat", address);
                        params.put("poskod", postcode);
                        params.put("bandar", city);
                        params.put("negeri_id", String.valueOf(stateCode));
                        params.put("negara_id", String.valueOf(country));
                        params.put("jenis_pengguna_e_kemudahan", String.valueOf(jenis_pengguna));
                        params.put("jenis_kategori_id", String.valueOf(jenis_kategori));
                        params.put("password", password);
                        params.put("jantina_id", String.valueOf(gender));
                        params.put("bangsa_id", String.valueOf(race));

                        return params;

                    }


                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();


                        return params;
                    }
                };

                dramarequest.setRetryPolicy(new DefaultRetryPolicy(
                        25000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                MySingleton.getInstance(BookingRegisterActivity.this).addToRequestQueue(dramarequest);


//            JSONObject responseObj = BookingHelper.registerUser(nric, postcode, city, password, email, fullname, address, phone, jenis_pengguna, nationality, gender, race, country, jenis_kategori, stateCode);


//            Log.d("registerUser", responseObj.toString());


//
//            }else {
//
//                Toast.makeText(this, "NRIC should be 6 digit", Toast.LENGTH_SHORT).show();}
            } else {
            updateMessage(getResources().getString(R.string.error_message));
        }
    }

    private void updateMessage(String message) {
        runOnUiThread(() -> {
            tv_abr_08.setVisibility(View.VISIBLE);
            tv_abr_08.setText(message);
        });
    }

    private AlertDialog alertDialog;

    private void prepareLoadingDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        View deleteDialogView = factory.inflate(R.layout.dialog_loading, null);

        ImageView iv_am_logo_01 = deleteDialogView.findViewById(R.id.iv_am_logo_01);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_cw);
        iv_am_logo_01.startAnimation(aniRotate);

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(deleteDialogView);
    }

    private void showLoadingDialog() {
        runOnUiThread(() -> alertDialog.show());
    }

    private void dismissLoadingDialog() {
        runOnUiThread(() -> alertDialog.dismiss());
    }
}
