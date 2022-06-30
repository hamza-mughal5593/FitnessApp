package com.mtechsoft.fitmy.v1.activity.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.common.Constants;

public class VerifyProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_profile);

        Intent intent = getIntent();
        String otp = intent.getStringExtra(Constants.BUNDLE_TEMP_OTP);

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
                    goVerify(otp, tac_number);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        });
    }

    public void goBack(View view) {
        finish();
    }

    public void goResend(View view) {
        // TODO
    }

    public void goVerify(String otp, String tac_number) {
        if (tac_number.equalsIgnoreCase(otp)) {
            Intent data = new Intent();
            data.setData(Uri.parse(otp));
            setResult(RESULT_OK, data);
            finish();
        } else {
            Toast.makeText(this, "Invalid OTP code", Toast.LENGTH_LONG).show();
        }
    }
}
