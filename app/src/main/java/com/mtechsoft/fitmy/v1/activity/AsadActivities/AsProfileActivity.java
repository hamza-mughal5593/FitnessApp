package com.mtechsoft.fitmy.v1.activity.AsadActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.Constants;
import com.mtechsoft.fitmy.v1.api.ServerCallback;

public class AsProfileActivity extends AppCompatActivity {

    DatePickerDialog picker;
    EditText name,etGender,user_type,category,email,phone_number,recent_address,country,zip_code,state,city,office_tel,office_fax,hieght,wieght;
    Spinner gender;
    Button signout;


    String str_name = "";
    String str_user_type= "";
    String str_category_type= "";
    String str_email= "";
    String str_phone= "";
    String str_gender= "";
//    String str_nation= "";
//    String str_citizenship= "";
    String str_office_tel_no= "";
    String str_office_fax_no= "";
    String str_address= "";
    String str_country= "";
    String str_state= "";
    String str_city= "";
    String str_zip_code= "";
    String str_hieght = "";
    String str_wieght= "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_as_profile);

        init();

        str_name = Utilities.getString(AsProfileActivity.this,"user_name");
        str_user_type = Utilities.getString(AsProfileActivity.this,"user_type");
        str_category_type = Utilities.getString(AsProfileActivity.this,"category_type");
        str_email = Utilities.getString(AsProfileActivity.this,"email");
        str_phone = Utilities.getString(AsProfileActivity.this,"phone");
        str_gender = Utilities.getString(AsProfileActivity.this,"gender");
//        str_nation = Utilities.getString(AsProfileActivity.this,"nation");
//        str_citizenship = Utilities.getString(AsProfileActivity.this,"citizenship");
        str_office_tel_no = Utilities.getString(AsProfileActivity.this,"office_tel_no");
        str_office_fax_no = Utilities.getString(AsProfileActivity.this,"office_fax_no");
        str_address = Utilities.getString(AsProfileActivity.this,"address");
        str_country = Utilities.getString(AsProfileActivity.this,"country");
        str_state = Utilities.getString(AsProfileActivity.this,"state");
        str_city = Utilities.getString(AsProfileActivity.this,"city");
        str_zip_code = Utilities.getString(AsProfileActivity.this,"zip_code");
        str_hieght = Utilities.getString(AsProfileActivity.this,"hieght");
        str_wieght = Utilities.getString(AsProfileActivity.this,"weight");


        name.setText(str_name);
        user_type.setText(String.valueOf(str_user_type));
        category.setText(str_category_type);
        email.setText(str_email);
        phone_number.setText(str_phone);
//        nationaity.setText(str_nation);
//        citizenship.setText(str_citizenship);
        office_fax.setText(str_office_fax_no);
        office_tel.setText(str_office_tel_no);
        recent_address.setText(str_address);
        country.setText(str_country);
        state.setText(str_state);
        city.setText(str_city);
        zip_code.setText(str_zip_code);
        hieght.setText(str_hieght);
        wieght.setText(str_wieght);

        RelativeLayout date = findViewById(R.id.date);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PicBirthday();
            }
        });

        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this,
                R.array.gender, R.layout.spinner_item);
        adapters.setDropDownViewResource(R.layout.spinner_item);
        gender.setAdapter(adapters);




        str_gender = gender.getSelectedItem().toString();


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int u_id = Utilities.getInt(AsProfileActivity.this,"user_id");


                str_name = name.getText().toString();
                str_user_type = "1";
                str_category_type = "1";
                str_email = email.getText().toString();
                str_phone = phone_number.getText().toString();
//                str_nation = nationaity.getText().toString();
//                str_citizenship = citizenship.getText().toString();
                str_office_tel_no = "0";
                str_office_fax_no =" 0";
                str_address = recent_address.getText().toString();
                str_country = "0";
                str_state = "0";
                str_city = "0";
                str_zip_code = "0";
                str_hieght = hieght.getText().toString();
                str_wieght= wieght.getText().toString();



                if (!str_name.isEmpty()){
                            if (!str_email.isEmpty()){
                                if (!str_phone.isEmpty()){
//                                        if (!str_citizenship.isEmpty()){
                                            if (!str_address.isEmpty()){
//                                                            if (!str_nation.isEmpty()){
                                                                if (!str_hieght.isEmpty()){
                                                                    if (!str_wieght.isEmpty()){



                                                                        update(str_user_type,str_category_type,str_name,str_email,str_phone,str_gender,str_office_tel_no,str_office_fax_no,str_address,str_country,str_state,str_city,str_zip_code,String.valueOf(u_id),str_hieght,str_wieght);



                                                                    }else {
                                                                        Toast.makeText(AsProfileActivity.this, R.string.weight_emp, Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }else {
                                                                    Toast.makeText(AsProfileActivity.this, R.string.hieght_empty, Toast.LENGTH_SHORT).show();
                                                                }
//                                                            }else {
//                                                                Toast.makeText(AsProfileActivity.this, R.string.naionality_empty, Toast.LENGTH_SHORT).show();
//                                                            }
                                            }else {
                                                Toast.makeText(AsProfileActivity.this, R.string.address_empty, Toast.LENGTH_SHORT).show();
                                            }
//                                        }else {
//                                            Toast.makeText(AsProfileActivity.this, R.string.naionality_empty, Toast.LENGTH_SHORT).show();
//                                        }
                                    }else {
                                    Toast.makeText(AsProfileActivity.this, R.string.phone_empty, Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(AsProfileActivity.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
                            }
                }else {
                    Toast.makeText(AsProfileActivity.this, R.string.name_empty, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void PicBirthday(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        // date picker dialog
        picker = new DatePickerDialog(AsProfileActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        recent_address.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, day);
        picker.show();
    }
    private void init() {

        name = findViewById(R.id.full_name);
//        etGender = findViewById(R.id.etGender);
        user_type = findViewById(R.id.user_type);
        category = findViewById(R.id.catagory_type);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        recent_address = findViewById(R.id.recent_address);
//        citizenship = findViewById(R.id.citizenship);
//        nationaity= findViewById(R.id.nationality);
        country = findViewById(R.id.country);
        zip_code= findViewById(R.id.zip_code);
        state= findViewById(R.id.state);
        city= findViewById(R.id.city);
        office_tel= findViewById(R.id.office_tel_num);
        office_fax= findViewById(R.id.office_fax_num);
        gender= findViewById(R.id.gender_sp);
        signout= findViewById(R.id.b_profile_signout);
        hieght= findViewById(R.id.hieght);
        wieght= findViewById(R.id.wieght);


    }



    public void update(final String user_type, final String category_type, final String full_name, final String email, final String phone,
                       final String gender, final String office_tel_no, final String office_fax_no, final String address
            ,final String country,final String state,final String city,final String zip_code,final String user_id,final String hieghtt,final String wieghtt){

        final IOSDialog dialog0 = new IOSDialog.Builder(AsProfileActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();
//        Toast.makeText(this, R.string.wait, Toast.LENGTH_SHORT).show();
//        Utilities.showProgressDialog(AsProfileActivity.this, String.valueOf(R.string.wait));

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("user_id", user_id);
        postParam.put("user_type", user_type);
        postParam.put("category_type", category_type);
        postParam.put("full_name", full_name);
        postParam.put("email", email);
        postParam.put("phone", phone);
        postParam.put("gender", gender);
//        postParam.put("nation", nation);
//        postParam.put("citizenship", citizenship);
        postParam.put("office_tel_no", office_tel_no);
        postParam.put("office_fax_no", office_fax_no);
        postParam.put("address", address);
        postParam.put("country", country);
        postParam.put("state", state);
        postParam.put("city", city);
        postParam.put("zip_code", zip_code);
        postParam.put("height", hieghtt);
        postParam.put("weight", wieghtt);



        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");

        ApiModelClass.GetApiResponse(Request.Method.POST, Constants.URL.BASE_URL+"update_profile", AsProfileActivity.this, postParam, headers, new ServerCallback() {

            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200){

                            dialog0.dismiss();
                            JSONObject user = jsonObject.getJSONObject("data");

                            int user_id = user.getInt("id");
                            String full_name = user.getString("full_name");
                            String user_type = user.getString("user_type");
                            String category_type = user.getString("category_type");
                            String email = user.getString("email");
                            String phone= user.getString("phone");
                            String gender= user.getString("gender");
//                            String nation= user.getString("nation");
//                            String citizenship= user.getString("citizenship");
                            String office_tel_no= user.getString("office_tel_no");
                            String office_fax_no= user.getString("office_fax_no");
                            String dob= user.getString("address");
                            String country= user.getString("country");
                            String state= user.getString("state");
                            String city= user.getString("city");
                            String zip_code= user.getString("zip_code");
                            String hieght= user.getString("height");
                            String weight= user.getString("weight");


                            Utilities.saveInt(AsProfileActivity.this,"user_id",user_id);
                            Utilities.saveString(AsProfileActivity.this,"user_name",full_name);
                            Utilities.saveString(AsProfileActivity.this,"user_type",user_type);
                            Utilities.saveString(AsProfileActivity.this,"email",email);
                            Utilities.saveString(AsProfileActivity.this,"phone",phone);
                            Utilities.saveString(AsProfileActivity.this,"category_type",category_type);
                            Utilities.saveString(AsProfileActivity.this,"gender",gender);
//                            Utilities.saveString(AsProfileActivity.this,"nation",nation);
//                            Utilities.saveString(AsProfileActivity.this,"citizenship",citizenship);
                            Utilities.saveString(AsProfileActivity.this,"office_tel_no",office_tel_no);
                            Utilities.saveString(AsProfileActivity.this,"office_fax_no",office_fax_no);
                            Utilities.saveString(AsProfileActivity.this,"address",dob);
                            Utilities.saveString(AsProfileActivity.this,"country",country);
                            Utilities.saveString(AsProfileActivity.this,"state",state);
                            Utilities.saveString(AsProfileActivity.this,"city",city);
                            Utilities.saveString(AsProfileActivity.this,"zip_code",zip_code);
                            Utilities.saveString(AsProfileActivity.this,"hieght",hieght);
                            Utilities.saveString(AsProfileActivity.this,"weight",weight);

                            Toast.makeText(AsProfileActivity.this, R.string.profile_success, Toast.LENGTH_SHORT).show();

                            finish();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else{

                    Log.i("errorrr",""+ERROR);
//                            Utilities.hideProgressDialog();
                }

                dialog0.dismiss();
                Utilities.hideProgressDialog();

            }
        });





    }
}
