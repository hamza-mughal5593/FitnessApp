package com.mtechsoft.fitmy.v1.activity.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.dashboard.BookingActivity;
import com.mtechsoft.fitmy.v1.common.BookingHelper;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.Utils;

public class BookingScheduleActivity extends AppCompatActivity {

    private String BUNDLE_TEMP_JSON;
    private String refPengurusanVenueStr;

    private TextView et_abs_date_start, et_abs_date_end;
    private TextView et_abs_time_start, et_abs_time_end,start_time_title,end_time_title;
    private Spinner s_abs_jenis_kadar, s_abs_bil_unit_tempahan;
    private TextView tv_abs_08, tv_abs_total;

    private int pengurusan_kemudahan_sedia_ada_id;
    private double kadar_sewaan_sejam_siang, kadar_sewaan_sehari_siang;
    private String nama_venue, refSukanRekreasiDesc, refJenisKemudahanDesc;
    private double totalAmount;
    private int dayDifference;
    private String dayDifferenceLabel;

    EditText no_of_people;

    RelativeLayout logout_boking;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_schedule);

        no_of_people = findViewById(R.id.et_abs_jumlah_pengguna);
        no_of_people = findViewById(R.id.et_abs_jumlah_pengguna);
        start_time_title = findViewById(R.id.tv_abs_2);
        end_time_title = findViewById(R.id.tv_abs_3);
        logout_boking = findViewById(R.id.rel);

        prepareLoadingDialog();

        Intent intent = getIntent();
        BUNDLE_TEMP_JSON = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON);

        prepareHeader(BUNDLE_TEMP_JSON);
        prepareCalendar();
        prepareForm();

        logout_boking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                startActivity(new Intent(BookingScheduleActivity.this, BookingActivity.class));
                finish();
            }
        });
    }

    private void prepareHeader(String BUNDLE_TEMP_JSON) {
        try {
            JSONObject jsonObject = new JSONObject(BUNDLE_TEMP_JSON);
            pengurusan_kemudahan_sedia_ada_id = jsonObject.getInt("pengurusan_kemudahan_sedia_ada_id");

            Utilities.saveInt(BookingScheduleActivity.this,"faciility_id",pengurusan_kemudahan_sedia_ada_id);


            kadar_sewaan_sejam_siang = jsonObject.getDouble("kadar_sewaan_sejam_siang");
            kadar_sewaan_sehari_siang = jsonObject.getDouble("kadar_sewaan_sehari_siang");

            JSONObject refPengurusanVenue = jsonObject.getJSONObject("refPengurusanVenue");
            refPengurusanVenueStr = refPengurusanVenue.toString();
            nama_venue = refPengurusanVenue.getString("nama_venue");

            JSONObject refSukanRekreasi = jsonObject.getJSONObject("refSukanRekreasi");
            refSukanRekreasiDesc = refSukanRekreasi.getString("desc");

            JSONObject refJenisKemudahan = jsonObject.getJSONObject("refJenisKemudahan");
            refJenisKemudahanDesc = refJenisKemudahan.getString("desc");

            TextView tv_abs_name = findViewById(R.id.tv_abs_name);
            tv_abs_name.setText(nama_venue);

            TextView tv_abs_desc = findViewById(R.id.tv_abs_desc);
            tv_abs_desc.setText(refJenisKemudahanDesc + "\n" + refSukanRekreasiDesc);

        } catch (JSONException ex) {

        }
    }

    private void prepareCalendar() {

        WebView wv_abs_calendar = findViewById(R.id.wv_abs_calendar);
        wv_abs_calendar.getSettings().setJavaScriptEnabled(true);
        wv_abs_calendar.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                wv_abs_calendar.loadUrl(url);
                setStartDate(url);
                return true;
            }
        });
        wv_abs_calendar.loadUrl(Constants.API_EFASILITI_TEMPAHAN_KEMUDAHAN_CALENDAR);
    }

    private void prepareForm() {
        TextWatcher textWatcher = new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                calculateTotal();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        };


        et_abs_date_start = findViewById(R.id.et_abs_date_start);
        et_abs_date_start.setOnClickListener(view -> showStartDate());
        et_abs_date_start.addTextChangedListener(textWatcher);

        et_abs_date_end = findViewById(R.id.et_abs_date_end);
        et_abs_date_end.setOnClickListener(view -> showEndDate());
        et_abs_date_end.addTextChangedListener(textWatcher);

        et_abs_time_start = findViewById(R.id.et_abs_time_start);
        et_abs_time_start.setOnClickListener(view -> showStartTime());
        et_abs_time_start.addTextChangedListener(textWatcher);

        et_abs_time_end = findViewById(R.id.et_abs_time_end);
        et_abs_time_end.setOnClickListener(view -> showEndTime());
        et_abs_time_end.addTextChangedListener(textWatcher);

        s_abs_jenis_kadar = findViewById(R.id.s_abs_jenis_kadar);
        s_abs_jenis_kadar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String text = s_abs_jenis_kadar.getSelectedItem().toString();

                if (text.equals("Per Jam")){

                    et_abs_time_start.setVisibility(View.VISIBLE);
                    et_abs_time_end.setVisibility(View.VISIBLE);
                    start_time_title.setVisibility(View.VISIBLE);
                    end_time_title.setVisibility(View.VISIBLE);

                }else {

                    et_abs_time_start.setVisibility(View.VISIBLE);
                    et_abs_time_end.setVisibility(View.VISIBLE);
                    start_time_title.setVisibility(View.VISIBLE);
                    end_time_title.setVisibility(View.VISIBLE);

                }
                calculateTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        s_abs_bil_unit_tempahan = findViewById(R.id.s_abs_bil_unit_tempahan);
        s_abs_bil_unit_tempahan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String bill_unit = (String) adapterView.getItemAtPosition(i);

                Utilities.saveString(BookingScheduleActivity.this,"bill_unit",bill_unit);

                calculateTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        tv_abs_08 = findViewById(R.id.tv_abs_08);
        tv_abs_total = findViewById(R.id.tv_abs_total);

        TextView tv_abs_submit = findViewById(R.id.tv_abs_submit);
        tv_abs_submit.setOnClickListener(view -> goSubmit());
    }

    private void setStartDate(String url) {
        try {
            String params[] = url.split("=");
            String dateParam = params[params.length - 1];
            et_abs_date_start.setText(dateParam);
            et_abs_date_end.setText(dateParam);
        } catch (Exception ex) {
            // TODO
        }
    }

    private void showStartDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    DecimalFormat df = new DecimalFormat("#00");

                    int months = monthOfYear+1;

                    String final_date = year + "-" + df.format(months) + "-" + df.format(dayOfMonth);
                    String end_date = et_abs_date_end.getText().toString();

                    if (!end_date.isEmpty()){

                        if (final_date.compareTo(end_date) < 0){

                            et_abs_date_start.setText(final_date);


                        }else {
                            Toast.makeText(this, "Choose the correct date", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        et_abs_date_start.setText(final_date);
                    }


                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showEndDate() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    DecimalFormat df = new DecimalFormat("#00");

                    int months = monthOfYear+1;

                    String end_date = year + "-" + df.format(months) +  "-" + df.format(dayOfMonth);
                    String start_date = et_abs_date_start.getText().toString();

                    if (!start_date.isEmpty()){

                        if (start_date.compareTo(end_date) < 0 || start_date.equals(end_date)){

                            et_abs_date_end.setText(end_date);

                        }else {

                            Toast.makeText(this, "Choose the correct date", Toast.LENGTH_SHORT).show();


                        }

                    }else {

                        Toast.makeText(this, "Choose start date first", Toast.LENGTH_SHORT).show();
                    }

                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void showStartTime() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {

                    DecimalFormat df = new DecimalFormat("#00");
                    et_abs_time_start.setText(df.format(hourOfDay) + ":" + df.format(minute) + ":00");
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void showEndTime() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {

                    DecimalFormat df = new DecimalFormat("#00");
                    et_abs_time_end.setText(df.format(hourOfDay) + ":" + df.format(minute) + ":00");
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    private void goSubmit() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String SP_EFASILITI_TOKEN = sharedPreferences.getString(Constants.SP_EFASILITI_TOKEN, null);

        String startDate = et_abs_date_start.getText().toString();
        String endDate = et_abs_date_end.getText().toString();
        String startTime = et_abs_time_start.getText().toString();
        String endTime = et_abs_time_end.getText().toString();
        int fasiliti_id = pengurusan_kemudahan_sedia_ada_id;
        int jenis_kadar = s_abs_jenis_kadar.getSelectedItemPosition() + 1;
        String no_people = no_of_people.getText().toString();
        int reservation_unit = s_abs_bil_unit_tempahan.getSelectedItemPosition();


        Utilities.saveString(BookingScheduleActivity.this,"start_date",startDate);
        Utilities.saveString(BookingScheduleActivity.this,"end_date",endDate);
        Utilities.saveString(BookingScheduleActivity.this,"start_time",startTime);
        Utilities.saveString(BookingScheduleActivity.this,"end_time",endTime);
        Utilities.saveInt(BookingScheduleActivity.this,"rate_type",jenis_kadar);
        Utilities.saveString(BookingScheduleActivity.this,"no_of_people",no_people);


        if (startDate.length() > 0 && endDate.length() > 0 && no_of_people.length() >0 && reservation_unit != 0 ){

                    new Thread() {
                        @Override
                        public void run() {
                            showLoadingDialog();

                            BookingHelper.TOKEN = SP_EFASILITI_TOKEN;
                            JSONObject responseObj = BookingHelper.checkBooking(startDate, endDate, startTime, endTime, fasiliti_id, jenis_kadar);

                            try {
                                if (responseObj.getBoolean("status")) {
                                    // TODO OK
                                    goPayment();
                                } else {
                                    // TODO XOK
                                    updateMessage("Slot hari/masa yang anda pilih telah penuh");
                                }
                            } catch (JSONException ex) {
                                // TODO
                            }

                            dismissLoadingDialog();
                            Log.d("goSubmit", responseObj.toString());
                        }
                    }.start();



        }else {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();

        }


    }

    private void calculateTotal() {
        try {
            String startDate = et_abs_date_start.getText().toString();
            String endDate = et_abs_date_end.getText().toString();
            String startTime = et_abs_time_start.getText().toString();
            String endTime = et_abs_time_end.getText().toString();
            int jenis_kadar = s_abs_jenis_kadar.getSelectedItemPosition() + 1;

            if (jenis_kadar == 1) { // HARI
                Date dateStart = Utils.toDateOnly(startDate);
                Date dateEnd = Utils.toDateOnly(endDate);

                long difference = Math.abs(dateStart.getTime() - dateEnd.getTime());
                long differenceDates = difference / (24 * 60 * 60 * 1000);

                dayDifference = Integer.parseInt(Long.toString(differenceDates)) + 1;
                totalAmount = dayDifference * kadar_sewaan_sehari_siang;
                dayDifferenceLabel = "Hari";

                DecimalFormat df = new DecimalFormat("0.00");
                tv_abs_total.setText( getResources().getString(R.string.amount) +" "+df.format(totalAmount));
            } else { // JAM
                Date start = Utils.toTimeOnly(startTime);
                Date end = Utils.toTimeOnly(endTime);

                long difference = Math.abs(start.getTime() - end.getTime());
                long differenceHour = difference / (60 * 60 * 1000);

                dayDifference = Integer.parseInt(Long.toString(differenceHour));
                totalAmount = dayDifference * kadar_sewaan_sejam_siang;
                dayDifferenceLabel = "Jam";

                DecimalFormat df = new DecimalFormat("0.00");
                tv_abs_total.setText(getResources().getString(R.string.amount) +" "+df.format(totalAmount));
            }
        } catch (Exception ex) {
            // TODO
        }
    }

    private void goPayment() {
        // nama_venue, refSukanRekreasiDesc, refJenisKemudahanDesc;

        String startDate = et_abs_date_start.getText().toString();
        String endDate = et_abs_date_end.getText().toString();
        String startTime = et_abs_time_start.getText().toString();
        String endTime = et_abs_time_end.getText().toString();
        int fasiliti_id = pengurusan_kemudahan_sedia_ada_id;
        int jenis_kadar = s_abs_jenis_kadar.getSelectedItemPosition() + 1;
        String rate = s_abs_jenis_kadar.getSelectedItem().toString();

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nama_venue", nama_venue);
            jsonObject.put("refSukanRekreasiDesc", refSukanRekreasiDesc);
            jsonObject.put("refJenisKemudahanDesc", refJenisKemudahanDesc);
            jsonObject.put("startDate", startDate);
            jsonObject.put("endDate", endDate);
            jsonObject.put("startTime", startTime);
            jsonObject.put("endTime", endTime);
            jsonObject.put("fasiliti_id", fasiliti_id);
            jsonObject.put("jenis_kadar", jenis_kadar);
            jsonObject.put("totalAmount", totalAmount);
            jsonObject.put("unit", dayDifference);
            jsonObject.put("unitLabel", dayDifferenceLabel);
            jsonObject.put("rate", rate);
            jsonObject.put("refPengurusanVenue", refPengurusanVenueStr);

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
            String SP_EFASILITI_CART = sharedPreferences.getString(Constants.SP_EFASILITI_CART, "[]");

            JSONArray jsonArray = new JSONArray(SP_EFASILITI_CART);
            jsonArray.put(jsonObject);

            SP_EFASILITI_CART = jsonArray.toString();

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.SP_EFASILITI_CART, SP_EFASILITI_CART).commit();

            startActivity(new Intent(this, BookingPaymentActivity.class));
            finish();
        } catch (JSONException ex) {
            // TODO
        }
    }

    private void updateMessage(String message) {
        runOnUiThread(() -> {
            tv_abs_08.setVisibility(View.VISIBLE);
            tv_abs_08.setText(message);
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
