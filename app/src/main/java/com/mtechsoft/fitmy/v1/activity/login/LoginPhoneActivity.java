package com.mtechsoft.fitmy.v1.activity.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.Models.Reward_Category;
import com.mtechsoft.fitmy.v1.UrlController;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.AsadActivities.RewardActivity;
import com.mtechsoft.fitmy.v1.activity.Rerofit.RestService;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.adapter.Reward_Categories_Adapter;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.Constants;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPhoneActivity extends AppCompatActivity {
    private AlertDialog alertDialog;
    AlertDialog dialog2;
    EditText email,password;
    Button login;
    TextView forgot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_phone);

        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        login = findViewById(R.id.login_btn);
        forgot = findViewById(R.id.forgot_pass);


        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showaforgotalert();

            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailll = email.getText().toString();
                String passworddd = password.getText().toString();

                if (!emailll.isEmpty() && isEmailValid(emailll)){
                    if (!passworddd.isEmpty()){

                        String em = email.getText().toString();
                        String passs = password.getText().toString();

                        adforest_getLoginViews(em,passs);

                    }else {

                        Toast.makeText(LoginPhoneActivity.this, R.string.password_empty, Toast.LENGTH_SHORT).show();
                    }

                }else {

                    Toast.makeText(LoginPhoneActivity.this, R.string.email_empty, Toast.LENGTH_SHORT).show();

                }

            }
        });



        prepareLoadingDialog();
    }

    private void showaforgotalert() {

        AlertDialog.Builder mbuilder = new AlertDialog.Builder(LoginPhoneActivity.this);

        View mview = LayoutInflater.from(LoginPhoneActivity.this).inflate(R.layout.forgot_dialog,null);

        RelativeLayout ok_rel = mview.findViewById(R.id.ok_rel);
        RelativeLayout cance_rel = mview.findViewById(R.id.cancel_rel);
        EditText email = mview.findViewById(R.id.email);


        mbuilder.setView(mview);
        dialog2 = mbuilder.create();
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();


        cance_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog2.dismiss();

            }
        });

        ok_rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emaill = email.getText().toString();
                if (emaill.isEmpty()){

                    Toast.makeText(LoginPhoneActivity.this, R.string.email_empty, Toast.LENGTH_SHORT).show();
                }else {

                    APiCall(emaill);
                }

            }
        });


    }

    private void APiCall(String emaill) {

        final IOSDialog dialog0 = new IOSDialog.Builder(LoginPhoneActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        Map<String, String> postParam = new HashMap<String, String>();
        postParam.put("email",emaill);


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL + "forgot_password", LoginPhoneActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();
//                    Utilities.hideProgressDialog();

                    try {
                        JSONObject jsonObject = new JSONObject(String.valueOf(result));

                        int status = jsonObject.getInt("status");

                        if (status == 200) {

                            Toast.makeText(LoginPhoneActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            dialog0.dismiss();

                            dialog2.dismiss();

                        } else if (status == 400) {

                            dialog0.dismiss();

                            dialog2.dismiss();

                            Toast.makeText(LoginPhoneActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                        }

                    } catch (JSONException e) {
                        dialog0.dismiss();
                        e.printStackTrace();
                    }

                } else {
                    dialog0.dismiss();
                }
            }
        });





    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void adforest_getLoginViews(final String emaill, final String passsword) {


        int user_id = 12;
        String full_name = "full_name";
        String user_type = "user_type";
        String category_type = "category_type";
        String email = "email";
        String phone= "phone";
        String gender= "gender";
        String nation= "nation";
        String citizenship= "citizenship";
        String office_tel_no= "office_tel_no";
        String office_fax_no= "office_fax_no";
        String address= "address";
        String country= "country";
        String state= "state";
        String city= "city";
        String zip_code= "zip_code";
        String fitness_point= "fitness_points";
        String hieght= "height";
        String wieght= "weight";
        String image= "image";

        Utilities.saveString(LoginPhoneActivity.this,"Login_status","true");

        Utilities.saveInt(LoginPhoneActivity.this,"user_id",user_id);
        Utilities.saveString(LoginPhoneActivity.this,"user_name",full_name);


        Utilities.saveString(LoginPhoneActivity.this,"user_type",user_type);
        Utilities.saveString(LoginPhoneActivity.this,"category_type",category_type);
        Utilities.saveString(LoginPhoneActivity.this,"email",email);
        Utilities.saveString(LoginPhoneActivity.this,"phone",phone);
        Utilities.saveString(LoginPhoneActivity.this,"gender",gender);
        Utilities.saveString(LoginPhoneActivity.this,"nation",nation);
        Utilities.saveString(LoginPhoneActivity.this,"citizenship",citizenship);
        Utilities.saveString(LoginPhoneActivity.this,"office_tel_no",office_tel_no);
        Utilities.saveString(LoginPhoneActivity.this,"office_fax_no",office_fax_no);
        Utilities.saveString(LoginPhoneActivity.this,"address",address);
        Utilities.saveString(LoginPhoneActivity.this,"country",country);
        Utilities.saveString(LoginPhoneActivity.this,"state",state);
        Utilities.saveString(LoginPhoneActivity.this,"city",city);
        Utilities.saveString(LoginPhoneActivity.this,"zip_code",zip_code);
        Utilities.saveString(LoginPhoneActivity.this,"fitness_points",fitness_point);
        Utilities.saveString(LoginPhoneActivity.this,"hieght",hieght);
        Utilities.saveString(LoginPhoneActivity.this,"wieght",wieght);
        Utilities.saveString(LoginPhoneActivity.this,"profile_image",image);

        Intent intent = new Intent(LoginPhoneActivity.this,FitnessActivity.class);
        startActivity(intent);
        finish();




//        final IOSDialog dialog0 = new IOSDialog.Builder(LoginPhoneActivity.this)
//                .setTitleColorRes(R.color.gray)
//                .build();
//        dialog0.show();
//
//        RestService restService = UrlController.createService(RestService.class);
//
//        Call<ResponseBody> myCall = restService.login( emaill, passsword,
//                UrlController.AddHeaders(LoginPhoneActivity.this));
//        myCall.enqueue(new Callback<ResponseBody>() {
//            @RequiresApi(api = Build.VERSION_CODES.O)
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> responseObj) {
//                try {
//
//                    if (responseObj.isSuccessful()) {
//                        dialog0.dismiss();
//                        Utilities.hideProgressDialog();
//                        Log.d("info Login responce", "" + responseObj.toString());
//
//                        JSONObject response = new JSONObject(responseObj.body().string());
//
//
//                        if (response.has("message")){
//
//                            String mess = response.getString("message");
//                            showemptyDialouge(mess);
//
//                        }else {
//
//                            String token = response.getString("access_token");
//
//                            Utilities.saveString(LoginPhoneActivity.this,"access_token",token);
//
//                            JSONObject user = response.getJSONObject("user");
//
//                            int user_id = user.getInt("id");
//                            String full_name = user.getString("full_name");
//                            String user_type = user.getString("user_type");
//                            String category_type = user.getString("category_type");
//                            String email = user.getString("email");
//                            String phone= user.getString("phone");
//                            String gender= user.getString("gender");
//                            String nation= user.getString("nation");
//                            String citizenship= user.getString("citizenship");
//                            String office_tel_no= user.getString("office_tel_no");
//                            String office_fax_no= user.getString("office_fax_no");
//                            String address= user.getString("address");
//                            String country= user.getString("country");
//                            String state= user.getString("state");
//                            String city= user.getString("city");
//                            String zip_code= user.getString("zip_code");
//                            String fitness_point= user.getString("fitness_points");
//                            String hieght= user.getString("height");
//                            String wieght= user.getString("weight");
//                            String image= user.getString("image");
//
//                            Utilities.saveString(LoginPhoneActivity.this,"Login_status","true");
//
//                            Utilities.saveInt(LoginPhoneActivity.this,"user_id",user_id);
//                            Utilities.saveString(LoginPhoneActivity.this,"user_name",full_name);
//
//
//                            Utilities.saveString(LoginPhoneActivity.this,"user_type",user_type);
//                            Utilities.saveString(LoginPhoneActivity.this,"category_type",category_type);
//                            Utilities.saveString(LoginPhoneActivity.this,"email",email);
//                            Utilities.saveString(LoginPhoneActivity.this,"phone",phone);
//                            Utilities.saveString(LoginPhoneActivity.this,"gender",gender);
//                            Utilities.saveString(LoginPhoneActivity.this,"nation",nation);
//                            Utilities.saveString(LoginPhoneActivity.this,"citizenship",citizenship);
//                            Utilities.saveString(LoginPhoneActivity.this,"office_tel_no",office_tel_no);
//                            Utilities.saveString(LoginPhoneActivity.this,"office_fax_no",office_fax_no);
//                            Utilities.saveString(LoginPhoneActivity.this,"address",address);
//                            Utilities.saveString(LoginPhoneActivity.this,"country",country);
//                            Utilities.saveString(LoginPhoneActivity.this,"state",state);
//                            Utilities.saveString(LoginPhoneActivity.this,"city",city);
//                            Utilities.saveString(LoginPhoneActivity.this,"zip_code",zip_code);
//                            Utilities.saveString(LoginPhoneActivity.this,"fitness_points",fitness_point);
//                            Utilities.saveString(LoginPhoneActivity.this,"hieght",hieght);
//                            Utilities.saveString(LoginPhoneActivity.this,"wieght",wieght);
//                            Utilities.saveString(LoginPhoneActivity.this,"profile_image",image);
//
//                            Intent intent = new Intent(LoginPhoneActivity.this,FitnessActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//
//                    }else {
//
//                        Toast.makeText(LoginPhoneActivity.this,"Something went wrong", Toast.LENGTH_SHORT).show();
//                        dialog0.dismiss();
//                    }
//                } catch (JSONException e) {
//
//                    dialog0.dismiss();
//                    Utilities.hideProgressDialog();
//
//                    e.printStackTrace();
//                } catch (IOException e) {
//
//                    dialog0.dismiss();
//                    Utilities.hideProgressDialog();
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Log.d("info Login error", String.valueOf(t));
//                Log.d("info Login error", String.valueOf(t.getMessage() + t.getCause() + t.fillInStackTrace()));
//            }
//        });
    }

    public void goBack(View view) {
        finish();
    }

    private void showemptyDialouge(String mess) {


        PrettyDialog pDialog = new PrettyDialog(this);

        pDialog
                .setTitle(mess)
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

    private void prepareLoadingDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        View deleteDialogView = factory.inflate(R.layout.dialog_loading, null);

        ImageView iv_am_logo_01 = deleteDialogView.findViewById(R.id.iv_am_logo_01);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_cw);
        iv_am_logo_01.startAnimation(aniRotate);

        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setView(deleteDialogView);
    }

}
