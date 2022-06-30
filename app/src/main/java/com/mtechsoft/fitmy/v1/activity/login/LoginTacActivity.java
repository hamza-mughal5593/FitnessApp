package com.mtechsoft.fitmy.v1.activity.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.landing.MainActivity;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.HttpHelper;

public class LoginTacActivity extends AppCompatActivity {
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_tac);

        Intent intent = getIntent();
        String phone_num = intent.getStringExtra(Constants.BUNDLE_TEMP_PHONE_NUM);

        EditText et_lta_01 = findViewById(R.id.et_lta_01);
        EditText et_lta_02 = findViewById(R.id.et_lta_02);
        EditText et_lta_03 = findViewById(R.id.et_lta_03);
        EditText et_lta_04 = findViewById(R.id.et_lta_04);
        EditText et_lta_05 = findViewById(R.id.et_lta_05);
        EditText et_lta_06 = findViewById(R.id.et_lta_06);




        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    View next = text.focusSearch(View.FOCUS_RIGHT); // or FOCUS_FORWARD
                    if (next != null)
                        next.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        };

        et_lta_01.addTextChangedListener(textWatcher);
        et_lta_02.addTextChangedListener(textWatcher);
        et_lta_03.addTextChangedListener(textWatcher);
        et_lta_04.addTextChangedListener(textWatcher);
        et_lta_05.addTextChangedListener(textWatcher);
        et_lta_06.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView text = (TextView) getCurrentFocus();

                if (text != null && text.length() > 0) {
                    String tac_number = String.format("%s%s%s%s%s%s", et_lta_01.getText().toString(), et_lta_02.getText().toString(), et_lta_03.getText().toString(), et_lta_04.getText().toString(), et_lta_05.getText().toString(), et_lta_06.getText().toString());
                    goVerify(phone_num, tac_number);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });

//        View.OnKeyListener keyListener = (v, keyCode, event) -> {
//            if(keyCode != KeyEvent.KEYCODE_DEL) {
//                if (!((EditText) v).getText().toString().isEmpty())
//                    v.focusSearch(View.FOCUS_RIGHT).requestFocus();
//            }
//
//            return false;
//        };
//
//        et_lta_01.setOnKeyListener(keyListener);
//        et_lta_02.setOnKeyListener(keyListener);
//        et_lta_03.setOnKeyListener(keyListener);
//        et_lta_04.setOnKeyListener(keyListener);
//        et_lta_05.setOnKeyListener(keyListener);
//        et_lta_06.setOnKeyListener((view, i, keyEvent) -> {
//            String tac_number = String.format("%s%s%s%s%s%s", et_lta_01.getText().toString(), et_lta_02.getText().toString(), et_lta_03.getText().toString(), et_lta_04.getText().toString(), et_lta_05.getText().toString(), et_lta_06.getText().toString());
//            goVerify(phone_num, tac_number);
//            return false;
//        });

        prepareLoadingDialog();
    }

    public void goBack(View view) {
        finish();
    }

    public void goResend(View view) {
        // TODO
    }

    public void goVerify(String phone_num, String tac_number) {
        if (tac_number.length() == 6) {
            showLoadingDialog();

            new Thread() {
                @Override
                public void run() {
                    JSONObject responseObj = HttpHelper.requestAppPassword(phone_num, tac_number);
                    doVerify(responseObj);
                }
            }.start();
        }
    }

    private void doVerify(JSONObject responseObj) {
        dismissLoadingDialog();

        if (responseObj != null) {
            try {
                String phone_num = responseObj.getString("phone_num");
                String app_password = responseObj.getString("app_password");

                SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean(Constants.SP_IS_LOGIN, true);
                editor.putString(Constants.SP_PHONE_NUM, phone_num);
                editor.putString(Constants.SP_APP_PASSWORD, app_password);
                editor.commit();

                finishAffinity();
                startActivity(new Intent(this, MainActivity.class));
            } catch (JSONException ex) {
                // TODO
            }
        } else {
            runOnUiThread(() -> {
                Toast.makeText(this, "Invalid OTP number", Toast.LENGTH_LONG).show();
            });
        }
    }

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
        alertDialog.show();
    }

    private void dismissLoadingDialog() {
        alertDialog.dismiss();
    }
}
