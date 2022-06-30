package com.mtechsoft.fitmy.v1.activity.onboarding;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kevalpatel2106.rulerpicker.RulerValuePicker;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.landing.MainActivity;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.HttpHelper;
import com.mtechsoft.fitmy.v1.common.ProfileObj;

public class OnboardingPhysicalActivity extends AppCompatActivity {

    private final int START_YEAR = 1930;
    private final int END_YEAR = Calendar.getInstance().get(Calendar.YEAR);

    private final int START_HEIGHT = 100;
    private final int END_HEIGHT = 250;

    private final int START_WEIGHT = 10;
    private final int END_WEIGHT = 200;

    private boolean isMale = true;
    int year = START_YEAR;
    int height = START_HEIGHT;
    int weight = START_WEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_physical);

        final ImageButton b_opa_phy_01 = findViewById(R.id.b_opa_phy_01);
        final ImageButton b_opa_phy_02 = findViewById(R.id.b_opa_phy_02);

        b_opa_phy_01.setOnClickListener(view -> {
            b_opa_phy_01.setBackgroundResource(R.drawable.bg_rounded_orange);
            b_opa_phy_02.setBackgroundResource(R.drawable.bg_rounded_dark_grey);

            isMale = true;
        });

        b_opa_phy_02.setOnClickListener(view -> {
            b_opa_phy_01.setBackgroundResource(R.drawable.bg_rounded_dark_grey);
            b_opa_phy_02.setBackgroundResource(R.drawable.bg_rounded_orange);

            isMale = false;
        });

        final TextView tv_opa_phy_04 = findViewById(R.id.tv_opa_phy_04);
        tv_opa_phy_04.setText(String.valueOf(START_YEAR));

        final RulerValuePicker v_opa_phy_01 = findViewById(R.id.v_opa_phy_01);
        v_opa_phy_01.setMinMaxValue(START_YEAR, END_YEAR);
        v_opa_phy_01.selectValue(((END_YEAR - START_YEAR) / 2) + START_YEAR);
        v_opa_phy_01.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                tv_opa_phy_04.setText(String.valueOf(selectedValue));
                year = selectedValue;
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                tv_opa_phy_04.setText(String.valueOf(selectedValue));
            }
        });

        final TextView tv_opa_phy_06 = findViewById(R.id.tv_opa_phy_06);
        tv_opa_phy_06.setText(String.valueOf(START_WEIGHT) + " kg");

        final RulerValuePicker v_opa_phy_02 = findViewById(R.id.v_opa_phy_02);
        v_opa_phy_02.setMinMaxValue(START_WEIGHT, END_WEIGHT);
        v_opa_phy_02.selectValue(END_WEIGHT / 2);
        v_opa_phy_02.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                tv_opa_phy_06.setText(String.valueOf(selectedValue) + " kg");
                weight = selectedValue;
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                tv_opa_phy_06.setText(String.valueOf(selectedValue) + " kg");
            }
        });

        final TextView tv_opa_phy_08 = findViewById(R.id.tv_opa_phy_08);
        tv_opa_phy_08.setText(String.valueOf(START_HEIGHT) + " cm");

        final RulerValuePicker v_opa_phy_03 = findViewById(R.id.v_opa_phy_03);
        v_opa_phy_03.setMinMaxValue(START_HEIGHT, END_HEIGHT);
        v_opa_phy_03.selectValue(END_HEIGHT / 2);
        v_opa_phy_03.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(final int selectedValue) {
                tv_opa_phy_08.setText(String.valueOf(selectedValue) + " cm");
                height = selectedValue;
            }

            @Override
            public void onIntermediateValueChange(final int selectedValue) {
                tv_opa_phy_08.setText(String.valueOf(selectedValue) + " cm");
            }
        });
    }

    public void goNext(View view) {
        String gender = "F";
        if(isMale) {
            gender = "M";
        }

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String firstName = sharedPreferences.getString(Constants.SP_TEMP_FIRST_NAME, null);
        String lastName = sharedPreferences.getString(Constants.SP_TEMP_LAST_NAME, null);
        String state = sharedPreferences.getString(Constants.SP_TEMP_STATE, null);

        ProfileObj profileObj = new ProfileObj();
        profileObj.setBirth_year(year);
        profileObj.setHeight_cm(height);
        profileObj.setWeight_kg(weight);
        profileObj.setFull_name(firstName);
        profileObj.setNick_name(lastName);
        profileObj.setGender(gender);

        new Thread() {
            @Override
            public void run() {
                try {
                    JSONObject response_1 = HttpHelper.registerProfile(profileObj);
                    if (response_1 != null) {
                        String phone_num = response_1.getString("phone_num");
                        String user_password = response_1.getString("user_password");

                        JSONObject response_2 = HttpHelper.requestAppPassword(phone_num, user_password);
                        if (response_2 != null) {
                            String app_password = response_2.getString("app_password");
                            JSONObject response_3 = HttpHelper.requestToken(phone_num, app_password);

                            if (response_3 != null) {
                                String profile_json = response_3.getJSONObject("profile").toString();
                                String token = response_3.getString("token");

                                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(Constants.SP_PHONE_NUM, phone_num);
                                editor.putString(Constants.SP_APP_PASSWORD, app_password);
                                editor.putString(Constants.SP_USER_PROFILE, profile_json);
                                editor.putString(Constants.SP_TOKEN, token);
                                editor.putBoolean(Constants.SP_IS_LOGIN, true);
                                editor.commit();

                                Log.d("updateToken", phone_num);
                                Log.d("updateToken", app_password);
                                Log.d("updateToken", profile_json);
                                Log.d("updateToken", token);

                                goDashboard();
                            }
                        }
                    }
                } catch (JSONException ex) {
                    Log.e("goNext", ex.getMessage());
                    Toast.makeText(OnboardingPhysicalActivity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }

    private void goDashboard() {
        finishAffinity();
        startActivity(new Intent(OnboardingPhysicalActivity.this, MainActivity.class));
    }

    public void goBack(View view) {
        finish();
    }
}
