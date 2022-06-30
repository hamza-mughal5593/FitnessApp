package com.mtechsoft.fitmy.v1.activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mtechsoft.fitmy.R;

public class ProfileActivity extends AppCompatActivity {

    private String OTP = "";
    private boolean isValidated = false;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        prepareLoadingDialog();

        TextView tv_profile_phone = findViewById(R.id.tv_profile_phone);
        EditText et_profile_tac = findViewById(R.id.et_profile_tac);
        TextView textView3 = findViewById(R.id.textView3);
        TextView tv_profile_firstname = findViewById(R.id.tv_profile_firstname);
        TextView tv_profile_lastname = findViewById(R.id.tv_profile_lastname);
        TextView tv_profile_email = findViewById(R.id.tv_profile_email);
        TextView tv_profile_race = findViewById(R.id.tv_profile_race);
        TextView tv_profile_nationality = findViewById(R.id.tv_profile_nationality);
        TextView tv_profile_height = findViewById(R.id.tv_profile_height);
        TextView tv_profile_weight = findViewById(R.id.tv_profile_weight);
        TextView tv_profile_birthyear = findViewById(R.id.tv_profile_birthyear);
        TextView tv_profile_gender = findViewById(R.id.tv_profile_gender);
        TextView tv_profile_gender_change = findViewById(R.id.tv_profile_gender_change);
        TextView tv_profile_race_change = findViewById(R.id.tv_profile_race_change);
        Button b_profile_signout = findViewById(R.id.b_profile_signout);
        TextView b_pa_01 = findViewById(R.id.b_pa_01);
        Spinner s_profile_01 = findViewById(R.id.s_profile_01);

//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, Context.MODE_PRIVATE);
//        String SP_USER_PROFILE = sharedPreferences.getString(Constants.SP_USER_PROFILE, null);
//        if (SP_USER_PROFILE != null) {
//            try {
//                JSONObject jsonObject = new JSONObject(SP_USER_PROFILE);
//                String first_name = jsonObject.getString("full_name");
//                String last_name = jsonObject.getString("nick_name");
//                String email = jsonObject.getString("email");
//                String race = jsonObject.getString("race");
//                String nationality = jsonObject.getString("nationality");
//                String birth_year = jsonObject.getString("birth_year");
//                String weight_kg = jsonObject.getString("weight_kg");
//                String height_cm = jsonObject.getString("height_cm");
//                String gender = jsonObject.getString("gender");
//
//                tv_profile_firstname.setText(first_name);
//                tv_profile_lastname.setText(last_name);
//                tv_profile_email.setText(email);
//                tv_profile_race.setText(race);
//                tv_profile_nationality.setText(nationality);
//                tv_profile_height.setText(height_cm);
//                tv_profile_weight.setText(weight_kg);
//                tv_profile_birthyear.setText(birth_year);
//
//                if (gender.equalsIgnoreCase("M")) {
//                    tv_profile_gender.setText("Male");
//                } else {
//                    tv_profile_gender.setText("Female");
//                }
//
//            } catch (JSONException ex) {
//                // TODO
//            }
//        }

//        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);
//        HttpHelper.TOKEN = token;
//
//        String locale = sharedPreferences.getString(Constants.SP_LANG, Constants.LOCALE_MALAY);
//        if (locale.equalsIgnoreCase(Constants.LOCALE_MALAY)) {
//            s_profile_01.setSelection(0);
//        } else if (locale.equalsIgnoreCase(Constants.LOCALE_ENGLISH)) {
//            s_profile_01.setSelection(1);
//        }


//        String SP_PHONE_NUM = sharedPreferences.getString(Constants.SP_PHONE_NUM, null);
//        if (SP_PHONE_NUM.length() < 15) {
//            tv_profile_phone.setText(SP_PHONE_NUM);
//            textView3.setText("Verified");
//            textView3.setTextColor(getResources().getColor(R.color.colorIndigo));
//            b_pa_01.setVisibility(View.GONE);
//            isValidated = true;
//        }

//        tv_profile_gender_change.setOnClickListener(view -> {
//            String gender = tv_profile_gender.getText().toString();
//            if (gender.equalsIgnoreCase("Male")) {
//                tv_profile_gender.setText("Female");
//            } else {
//                tv_profile_gender.setText("Male");
//            }
//        });

//        tv_profile_race_change.setOnClickListener(view -> {
//            String race = tv_profile_race.getText().toString();
//
//            if (race.equalsIgnoreCase("null") || race.equalsIgnoreCase("") || race.equalsIgnoreCase("Malay")) {
//                tv_profile_race.setText("Chinese");
//            } else if (race.equalsIgnoreCase("Chinese")) {
//                tv_profile_race.setText("Indian");
//            } else if (race.equalsIgnoreCase("Indian")) {
//                tv_profile_race.setText("Other");
//            } else if (race.equalsIgnoreCase("Other")) {
//                tv_profile_race.setText("Malay");
//            }
//        });
//
//        b_profile_signout.setOnClickListener(view -> {
//            String phone_num = tv_profile_phone.getText().toString();
//
//            if (isValidated) {
//                if (SP_PHONE_NUM.equalsIgnoreCase(phone_num)) {
//                    new Thread() {
//                        @Override
//                        public void run() {
//                            doSave();
//                        }
//                    }.start();
//                } else {
//                    b_pa_01.performClick();
//                }
//            } else {
//                runOnUiThread(() -> {
//                    showError("Please verify your account to update your profile.");
//                });
//            }
//        });
//
//        b_pa_01.setOnClickListener(view -> {
//            String phone_num = tv_profile_phone.getText().toString();
//
//            if (phone_num.length() != 0) {
//                new Thread() {
//                    @Override
//                    public void run() {
//                        showLoadingDialog();
//
//                        JSONObject responseObj = HttpHelper.verifyPhone(phone_num);
//                        if (responseObj != null) {
//                            try {
//                                OTP = responseObj.getString("user_password");
//
//                                Intent intent = new Intent(ProfileActivity.this, VerifyProfileActivity.class);
//                                intent.putExtra(Constants.BUNDLE_TEMP_OTP, OTP);
//                                startActivityForResult(intent, 101);
//
//                            } catch (JSONException ex) {
//                                // TODO
//                            }
//                        } else {
//                            runOnUiThread(() -> {
//                                showError("Error, phone number already registered. If this is your phone number, kindly sign out and re-login.");
//                            });
//                        }
//
//                        dismissLoadingDialog();
//                    }
//                }.start();
//            }
//        });
    }

//    private void doSave() {
//        showLoadingDialog();
//
//        EditText tv_profile_phone = findViewById(R.id.tv_profile_phone);
//        EditText tv_profile_firstname = findViewById(R.id.tv_profile_firstname);
//        EditText tv_profile_lastname = findViewById(R.id.tv_profile_lastname);
//        EditText tv_profile_height = findViewById(R.id.tv_profile_height);
//        EditText tv_profile_weight = findViewById(R.id.tv_profile_weight);
//        EditText tv_profile_birthyear = findViewById(R.id.tv_profile_birthyear);
//        EditText tv_profile_email = findViewById(R.id.tv_profile_email);
//        TextView tv_profile_race = findViewById(R.id.tv_profile_race);
//        EditText tv_profile_nationality = findViewById(R.id.tv_profile_nationality);
//        TextView tv_profile_gender = findViewById(R.id.tv_profile_gender);
//        Spinner s_profile_01 = findViewById(R.id.s_profile_01);
//
//        String phone_num = tv_profile_phone.getText().toString();
//        String full_name = tv_profile_firstname.getText().toString();
//        String nick_name = tv_profile_lastname.getText().toString();
//        String height = tv_profile_height.getText().toString();
//        String weight = tv_profile_weight.getText().toString();
//        String birth_year = tv_profile_birthyear.getText().toString();
//        String gender = tv_profile_gender.getText().toString();
//        String email = tv_profile_email.getText().toString();
//        String race = tv_profile_race.getText().toString();
//        String nationality = tv_profile_nationality.getText().toString();
//        String lang = s_profile_01.getSelectedItem().toString();
//
//        ProfileObj profileObj = new ProfileObj();
//        profileObj.setPhone_num(phone_num);
//        profileObj.setFull_name(full_name);
//        profileObj.setNick_name(nick_name);
//        profileObj.setEmail(email);
//        profileObj.setRace(race);
//        profileObj.setNationality(nationality);
//        profileObj.setHeight_cm(Integer.parseInt(height));
//        profileObj.setWeight_kg(Integer.parseInt(weight));
//        profileObj.setBirth_year(Integer.parseInt(birth_year));
//        profileObj.setBirth_date("2000-01-01");
//        profileObj.setGender(gender.equalsIgnoreCase("Male") ? "M" : "F");
//        JSONObject jsonObject = HttpHelper.updateProfile(profileObj);
//
//        dismissLoadingDialog();
//
//        try {
//            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//
//            editor.putString(Constants.SP_PHONE_NUM, phone_num);
//            editor.putString(Constants.SP_USER_PROFILE, jsonObject.toString());
//            editor.putInt(Constants.SP_CURRENT_POINT, jsonObject.getInt("total_points"));
//            editor.putString(Constants.SP_CURRENT_STEP, jsonObject.getString("total_steps"));
//
//            editor.commit();
//
//            switch (lang) {
//                case "Bahasa Malaysia":
//                    doSavePref(Constants.LOCALE_MALAY);
//                    break;
//                case "English":
//                    doSavePref(Constants.LOCALE_ENGLISH);
//                    break;
//            }
//
//            runOnUiThread(() ->
//                    Toast.makeText(this, "Profile updated!", Toast.LENGTH_LONG).show()
//            );
//            finish();
//
//        } catch (Exception ex) {
//            Log.e("updateProfile", ex.getMessage());
//        }
//    }

//    public void goBack(View view) {
//        finish();
//    }

//    public void showError(String message) {
//        TextView tv_lpa_08 = findViewById(R.id.tv_lpa_08);
//        ImageView iv_lpa_03 = findViewById(R.id.iv_lpa_03);
//
//        tv_lpa_08.setText(message);
//        tv_lpa_08.setVisibility(View.VISIBLE);
//        iv_lpa_03.setVisibility(View.VISIBLE);
//    }

//    public void hideError() {
//        TextView tv_lpa_08 = findViewById(R.id.tv_lpa_08);
//        ImageView iv_lpa_03 = findViewById(R.id.iv_lpa_03);
//
//        tv_lpa_08.setText("");
//        tv_lpa_08.setVisibility(View.INVISIBLE);
//        iv_lpa_03.setVisibility(View.INVISIBLE);
//    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101) {
//            if (resultCode == RESULT_OK) {
//                hideError();
//
//                new Thread() {
//                    @Override
//                    public void run() {
//                        doSave();
//                    }
//                }.start();
//            }
//        }
//    }

//    private void prepareLoadingDialog() {
//        LayoutInflater factory = LayoutInflater.from(this);
//        View deleteDialogView = factory.inflate(R.layout.dialog_loading, null);
//
//        ImageView iv_am_logo_01 = deleteDialogView.findViewById(R.id.iv_am_logo_01);
//        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_cw);
//        iv_am_logo_01.startAnimation(aniRotate);
//
//        alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setView(deleteDialogView);
//    }

//    private void showLoadingDialog() {
//        runOnUiThread(() -> alertDialog.show());
//    }

//    private void dismissLoadingDialog() {
//        runOnUiThread(() -> alertDialog.dismiss());
//    }

//    private void doSavePref(String locale) {
//        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(Constants.SP_LANG, locale).commit();
//
//        LocaleHelper.setActivityLocale(this, locale);
//    }
}
