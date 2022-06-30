package com.mtechsoft.fitmy.v1.activity.onboarding;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.mtechsoft.fitmy.v1.activity.landing.MainActivity;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.UrlController;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.Rerofit.RestService;
import com.mtechsoft.fitmy.v1.activity.login.LoginPhoneActivity;
import com.mtechsoft.fitmy.v1.adapter.SliderAdapterExample;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnboardingProfileActivity extends AppCompatActivity {

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
    RelativeLayout date;
    EditText birhtday;
    DatePickerDialog picker;

    //    Spinner user_type_sp, category_type_sp, gender_sp, nation_sp, citizenship_sp, state_sp, coutry_sp;
    Spinner user_type_sp, category_type_sp, gender_sp, state_sp, coutry_sp;

    RelativeLayout submit, back_rel;

    EditText user_id_et, email_et, full_name_et, password, confirm_passsword, ph_num_et, office_tel_et, office_fax_et, recent_addres_et, zip_code_et, city_et, hieght_et, wieght_et;
    String user_type = "", category_type = "", gender = "", nation = "", citizenship = "", state = "", coutry = "";

    CheckBox term_cb;

    TextView term_and_condition_link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_profile);

        SliderView sliderView = findViewById(R.id.imageSlider);
        SliderAdapterExample adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();
        sliderView.setIndicatorVisibility(false);

        initil();

        back_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        date = findViewById(R.id.date);
        birhtday = findViewById(R.id.birhtday);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PicBirthday();
            }
        });


        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                R.array.user_type, R.layout.spinner_item);
        adapters.setDropDownViewResource(R.layout.spinner_item);
        user_type_sp.setAdapter(adapters);

        user_type = user_type_sp.getSelectedItem().toString();


        ArrayAdapter<CharSequence> adapters2 = ArrayAdapter.createFromResource(this,
                R.array.category_type, R.layout.spinner_item);
        adapters2.setDropDownViewResource(R.layout.spinner_item);
        category_type_sp.setAdapter(adapters2);

        category_type = category_type_sp.getSelectedItem().toString();

        ArrayAdapter<CharSequence> adapters3 = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.spinner_item);
        adapters3.setDropDownViewResource(R.layout.spinner_item);
        gender_sp.setAdapter(adapters3);

        gender = gender_sp.getSelectedItem().toString();

//
//        ArrayAdapter<CharSequence> adapters4 = ArrayAdapter.createFromResource(this,
//                R.array.nation, R.layout.spinner_item);
//        adapters4.setDropDownViewResource(R.layout.spinner_item);
//        nation_sp.setAdapter(adapters4);
//
//        nation = nation_sp.getSelectedItem().toString();


//        ArrayAdapter<CharSequence> adapters5 = ArrayAdapter.createFromResource(this,
//                R.array.Citizenship, R.layout.spinner_item);
//        adapters5.setDropDownViewResource(R.layout.spinner_item);
//        citizenship_sp.setAdapter(adapters5);
//
//        citizenship = citizenship_sp.getSelectedItem().toString();


        ArrayAdapter<CharSequence> adapters6 = ArrayAdapter.createFromResource(this,
                R.array.stateee, R.layout.spinner_item);
        adapters6.setDropDownViewResource(R.layout.spinner_item);
        state_sp.setAdapter(adapters6);

        state = state_sp.getSelectedItem().toString();


        ArrayAdapter<CharSequence> adapters7 = ArrayAdapter.createFromResource(this,
                R.array.Mali, R.layout.spinner_item);
        adapters7.setDropDownViewResource(R.layout.spinner_item);
        coutry_sp.setAdapter(adapters7);

        coutry = coutry_sp.getSelectedItem().toString();


        term_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                term_cb.setChecked(false);

                AlertDialog.Builder mbuilder=new AlertDialog.Builder(OnboardingProfileActivity.this);

                View mview = LayoutInflater.from(OnboardingProfileActivity.this).inflate(R.layout.ok_dialog,null);
                RelativeLayout ok_rel=mview.findViewById(R.id.i_agree);
                mbuilder.setView(mview);
                final AlertDialog dialog2= mbuilder.create();
                dialog2.setCanceledOnTouchOutside(false);
                dialog2.show();

                ok_rel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog2.dismiss();
                        term_cb.setChecked(true);
                    }
                });



            }
        });

        term_and_condition_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                term_cb.setChecked(false);

                AlertDialog.Builder mbuilder=new AlertDialog.Builder(OnboardingProfileActivity.this);

                View mview = LayoutInflater.from(OnboardingProfileActivity.this).inflate(R.layout.ok_dialog,null);
                RelativeLayout ok_rel=mview.findViewById(R.id.i_agree);
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



            }
        });



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String user_idd = "1";
                String pas = password.getText().toString();
                String confirm_pass = confirm_passsword.getText().toString();
                String emaill = email_et.getText().toString();
                String name = full_name_et.getText().toString();
                String phone = ph_num_et.getText().toString();
                String office_tel = "0";
                String office_fax = "0";
                String recet_Ad = birhtday.getText().toString();
                String zipp = "0";
                String cityy = "0";
                String hieght = hieght_et.getText().toString();
                String wieght = wieght_et.getText().toString();

                if (pas.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.password_empty, Toast.LENGTH_SHORT).show();
                } else if (confirm_pass.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.confirm_password_empty, Toast.LENGTH_SHORT).show();
                }  else if (!pas.equals(confirm_pass)) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.password_not_matched, Toast.LENGTH_SHORT).show();
                } else if (emaill.isEmpty() || !isEmailValid(emaill)) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
                } else if (pas.length() < 8) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.pass_length, Toast.LENGTH_SHORT).show();
                } else if (confirm_pass.length() < 8) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.pass_length, Toast.LENGTH_SHORT).show();
                }else if (name.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.name_empty, Toast.LENGTH_SHORT).show();
                } else if (phone.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.phone_empty, Toast.LENGTH_SHORT).show();
                }
                else if (hieght.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.weight_emp, Toast.LENGTH_SHORT).show();
                }else if (recet_Ad.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.birthday_empty, Toast.LENGTH_SHORT).show();
                } else if (!term_cb.isChecked()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.empty_term, Toast.LENGTH_SHORT).show();
                }  else if (wieght.isEmpty()) {
                    Toast.makeText(OnboardingProfileActivity.this, R.string.hieght_empty, Toast.LENGTH_SHORT).show();
                } else {
                    RegistrationApi(name, emaill, phone, office_tel, office_fax, recet_Ad, cityy, zipp, pas, confirm_pass, hieght, wieght);

                }

//                if (!pas.isEmpty()) {
//                    if (!confirm_pass.isEmpty()) {
//                        if (pas.equals(confirm_pass)) {
//                            if (!emaill.isEmpty()) {
//                                if (!(pas.length() < 8)) {
//                                    if(!(confirm_pass.length() < 8)){
//                                        if(!name.isEmpty()){
//                                            if (!phone.isEmpty()){
//                                                if (!hieght.isEmpty()){
//                                                    if (!wieght.isEmpty()) {
//                                                        if (!recet_Ad.isEmpty()){
//                                                            if (term_cb.isChecked()){
//                                                                RegistrationApi(name, emaill, phone, office_tel, office_fax, recet_Ad, cityy, zipp, pas, confirm_pass, hieght, wieght);
//
//                                                            }else{
//                                                                Toast.makeText(OnboardingProfileActivity.this, R.string.empty_term, Toast.LENGTH_SHORT).show();
//
//
//                                                            }
//                                                        }else{
//                                                            Toast.makeText(OnboardingProfileActivity.this, R.string.birthday_empty, Toast.LENGTH_SHORT).show();
//
//                                                        }
//
//
//                                                    } else{
////                                                        Toast.makeText(OnboardingProfileActivity.this, R.string.weight_empty, Toast.LENGTH_SHORT).show();
//                                                        wieght_et.setError(getResources().getString(R.string.weight_emp));
//                                                    }
//
//                                                }else{
//                                                    hieght_et.setError(getResources().getString(R.string.hieght_empty));
////                                                    Toast.makeText(OnboardingProfileActivity.this, R.string.hieght_empty, Toast.LENGTH_SHORT).show();
//
//                                                }
//
//                                            }else{
//
//                                                Toast.makeText(OnboardingProfileActivity.this, R.string.phone_empty, Toast.LENGTH_SHORT).show();
//
//                                            }
//                                        }else{
//                                            Toast.makeText(OnboardingProfileActivity.this, R.string.name_empty, Toast.LENGTH_SHORT).show();
//
//
//                                        }
//
//                                    }else{
//                                        Toast.makeText(OnboardingProfileActivity.this, R.string.pass_length, Toast.LENGTH_SHORT).show();
//
//
//                                    }
//                                } else {
//
//                                    Toast.makeText(OnboardingProfileActivity.this, R.string.pass_length, Toast.LENGTH_SHORT).show();
//
//                                }
//                            } else {
//
//                                Toast.makeText(OnboardingProfileActivity.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
//
//                            }
//                        } else {
//                            Toast.makeText(OnboardingProfileActivity.this, R.string.password_not_matched, Toast.LENGTH_SHORT).show();
//
//                        }
//
//                    } else {
//
//                        Toast.makeText(OnboardingProfileActivity.this, R.string.confirm_password_empty, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                }else{
//
//                    Toast.makeText(OnboardingProfileActivity.this, R.string.password_empty, Toast.LENGTH_SHORT).show();
//
//                }
//                    if (!pas.isEmpty()){
//                        if (!confirm_pass.isEmpty()) {
//                            if (pas.equals(confirm_pass)) {
//                                if (!emaill.isEmpty()){
//                                    if (!name.isEmpty()){
//                                        if (!phone.isEmpty()){
//                                                        if (term_cb.isChecked()){
//                                                            if (!hieght.isEmpty()){
//                                                                if (!wieght.isEmpty()){
//
//                                                                    adforest_getLoginViews(name,emaill,phone,office_tel,office_fax,recet_Ad,cityy,zipp,pas,confirm_pass,hieght,wieght);
//
//                                                                }else {
//                                                                    Toast.makeText(OnboardingProfileActivity.this, "Hieght is empty", Toast.LENGTH_SHORT).show(); }
//                                                            }else {
//                                                                Toast.makeText(OnboardingProfileActivity.this, "Wieght is empty", Toast.LENGTH_SHORT).show(); }
//                                                        }else {
//                                                            Toast.makeText(OnboardingProfileActivity.this, "You have not agreed with our terms and conditions", Toast.LENGTH_SHORT).show();
//                                                        }
//                                        }else {
//                                            Toast.makeText(OnboardingProfileActivity.this, "phone is empty", Toast.LENGTH_SHORT).show();
//                                        } }else {
//
//                                        }
//                                    }else {
//                                        Toast.makeText(OnboardingProfileActivity.this, "Name is empty", Toast.LENGTH_SHORT).show();
//                                    }
//                                }else {
//                                    Toast.makeText(OnboardingProfileActivity.this, "Email is empty", Toast.LENGTH_SHORT).show();
//
//                                }
//                            } else {
//
//                                Toast.makeText(OnboardingProfileActivity.this, "Password not matched", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            Toast.makeText(OnboardingProfileActivity.this, "Confirm password is empty", Toast.LENGTH_SHORT).show();
//                        }
//                    }else {
//                        Toast.makeText(OnboardingProfileActivity.this, "Password is empty", Toast.LENGTH_SHORT).show();
            }

        });


    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void PicBirthday() {
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(OnboardingProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birhtday.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
        picker.show();
    }

    private void initil() {

        back_rel = findViewById(R.id.backrel);
        user_type_sp = findViewById(R.id._user_type_sp);
        category_type_sp = findViewById(R.id.category_type_sp);
        gender_sp = findViewById(R.id.gender_sp);
//        nation_sp = findViewById(R.id.nation_sp);
//        citizenship_sp = findViewById(R.id.citizenship_sp);
        state_sp = findViewById(R.id.state_sp);
        coutry_sp = findViewById(R.id.coutry_sp);
        submit = findViewById(R.id.submit);
        term_cb = findViewById(R.id.term_cb);

        user_id_et = findViewById(R.id.user_id);
        email_et = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirm_passsword = findViewById(R.id.confirm_password);
        full_name_et = findViewById(R.id.full_name);
        office_tel_et = findViewById(R.id.office_tel_num);
        office_fax_et = findViewById(R.id.office_fax_num);
        recent_addres_et = findViewById(R.id.recent_address);
        zip_code_et = findViewById(R.id.zip_code);
        city_et = findViewById(R.id.city);
        ph_num_et = findViewById(R.id.phone_number);
        hieght_et = findViewById(R.id.wieght);
        wieght_et = findViewById(R.id.hieght);
        term_and_condition_link = findViewById(R.id.term_conditon_link);

    }

    private void RegistrationApi(final String f_name, final String f_email, final String f_phonee, final String f_offic_tel, final String f_office_fax, final String f_adress, final String f_city, final String f_zip_code, final String f_password, final String f_confirm_password, final String hieghtt, final String wieghtt) {

        final IOSDialog dialog0 = new IOSDialog.Builder(OnboardingProfileActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();
        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_type", user_type);
        postParam.put("category_type", category_type);
        postParam.put("full_name", f_name);
        postParam.put("email", f_email);
        postParam.put("phone", f_phonee);
        postParam.put("gender", gender);
        postParam.put("nation", "");
        postParam.put("citizenship", "");
        postParam.put("office_tel_no", f_offic_tel);
        postParam.put("office_fax_no", f_office_fax);
        postParam.put("address", f_adress);
        postParam.put("country", coutry);
        postParam.put("state", state);
        postParam.put("city", f_city);
        postParam.put("zip_code", f_zip_code);
        postParam.put("password", f_password);
        postParam.put("password_confirmation", f_confirm_password);
        postParam.put("height", hieghtt);
        postParam.put("weight", wieghtt);

        HashMap<String, String> headers = new HashMap<String, String>();

        headers.put("Accept", "application/json");

        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL+"register", OnboardingProfileActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    try {
                        JSONObject object = new JSONObject(String.valueOf(result));

                        int status = object.getInt("status");
                        if (status == 200) {
                            String msg = object.getString("message");
                            dialog0.dismiss();
                            Toast.makeText(OnboardingProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(OnboardingProfileActivity.this, LoginPhoneActivity.class));

                        } else {
                            String msgg = object.getString("message");
                            dialog0.dismiss();
                            new PrettyDialog(OnboardingProfileActivity.this)
                                    .setTitle(msgg)
                                    .setIcon(R.drawable.pdlg_icon_info)
                                    .setIconTint(R.color.colorPrimary)
//                .setMessage("PrettyDialog Message")
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else {
                    dialog0.dismiss();
                    showemptyDialouge();
                }
            }
        });


    }

    private void showemptyDialouge() {


        PrettyDialog pDialog = new PrettyDialog(this);

        pDialog
                .setTitle(getResources().getString(R.string.email_error_registered))
                .setIcon(R.drawable.pdlg_icon_info)
                .setIconTint(R.color.colorOrange)
                .addButton(
                        "OK",
                        R.color.pdlg_color_white,
                        R.color.colorOrange,
                        new PrettyDialogCallback() {
                            @Override
                            public void onClick() {
                                pDialog.dismiss();
                            }
                        }
                )
                .show();
    }


    public void goBack(View view) {
        finish();
    }
}
