package com.mtechsoft.fitmy.v1.activity.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.common.BookingHelper;
import com.mtechsoft.fitmy.v1.common.Constants;

public class BookingLoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String SP_EFASILITI_LOCAL_VAR_USERNAME, SP_EFASILITI_LOCAL_VAR_PASSWORD;
    private EditText et_abl_nric, et_abl_pass;
    private TextView tv_abl_08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_login);

        sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        SP_EFASILITI_LOCAL_VAR_USERNAME = sharedPreferences.getString(Constants.SP_EFASILITI_LOCAL_VAR_USERNAME, null);
        SP_EFASILITI_LOCAL_VAR_PASSWORD = sharedPreferences.getString(Constants.SP_EFASILITI_LOCAL_VAR_PASSWORD, null);

        prepareLoadingDialog();
        prepareLoginForm();
        requestToken();
    }

    private void prepareLoginForm() {
        et_abl_nric = findViewById(R.id.et_abl_nric);
        et_abl_pass = findViewById(R.id.et_abl_pass);
        tv_abl_08 = findViewById(R.id.tv_abl_08);

        et_abl_nric.setText(SP_EFASILITI_LOCAL_VAR_USERNAME);

        ImageView b_abl_back = findViewById(R.id.b_abl_back);
        b_abl_back.setOnClickListener(view -> finish());

        ImageView b_abl_submit = findViewById(R.id.b_abl_submit);
        b_abl_submit.setOnClickListener(view -> doSubmit());

        TextView tv_abl_09 = findViewById(R.id.tv_abl_09);
        tv_abl_09.setOnClickListener(view -> goBookingRegister());
    }

    private void requestToken() {
        if (SP_EFASILITI_LOCAL_VAR_USERNAME != null && SP_EFASILITI_LOCAL_VAR_PASSWORD != null) {
            new Thread() {
                @Override
                public void run() {
                    showLoadingDialog();

                    JSONObject responseObj = BookingHelper.requestToken(SP_EFASILITI_LOCAL_VAR_USERNAME, SP_EFASILITI_LOCAL_VAR_PASSWORD);

                    try {
                        if (responseObj.has("access_token")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.SP_EFASILITI_TOKEN, responseObj.getString("access_token")).commit();

                            updateMessage(responseObj.getString("message"));

                            goBookingSchedule();
                        } else {

                            JSONObject errors = responseObj.getJSONObject("errors");
                            JSONArray password = errors.getJSONArray("password");
                            updateMessage(password.getString(0));
                        }
                    } catch (JSONException ex) {
                        // TODO
                    }

                    dismissLoadingDialog();
                    Log.d("refreshToken", responseObj.toString());
                }
            }.start();
        }
    }

    private void updateMessage(String message) {
        runOnUiThread(() -> tv_abl_08.setText(message));
    }

    private void doSubmit() {
        String username = et_abl_nric.getText().toString();
        String password = et_abl_pass.getText().toString();

        if (username.length() > 0 && password.length() > 0) {
            new Thread() {
                @Override
                public void run() {
                    showLoadingDialog();

                    JSONObject responseObj = BookingHelper.requestToken(username, password);

                    try {
                        if (responseObj.has("access_token")) {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(Constants.SP_EFASILITI_LOCAL_VAR_USERNAME, username);
                            editor.putString(Constants.SP_EFASILITI_LOCAL_VAR_PASSWORD, password);
                            editor.putString(Constants.SP_EFASILITI_TOKEN, responseObj.getString("access_token"));
                            editor.commit();

                            updateMessage(responseObj.getString("message"));

                            goBookingSchedule();
                        } else {
                            JSONObject errors = responseObj.getJSONObject("errors");
                            JSONArray password = errors.getJSONArray("password");
                            updateMessage(password.getString(0));
                        }
                    } catch (JSONException ex) {
                        // TODO
                    }

                    dismissLoadingDialog();
                    Log.d("doSubmit", responseObj.toString());
                }
            }.start();
        }
    }

    private void goBookingSchedule() {
        String BUNDLE_TEMP_JSON = getIntent().getStringExtra(Constants.BUNDLE_TEMP_JSON);

        Intent intent = new Intent(this, BookingScheduleActivity.class);
        intent.putExtra(Constants.BUNDLE_TEMP_JSON, BUNDLE_TEMP_JSON);
        startActivity(intent);

        finish();
    }

    private void goBookingRegister() {

        Intent intent = new Intent(this, BookingRegisterActivity.class);
        startActivity(intent);
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
