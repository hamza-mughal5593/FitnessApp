package com.mtechsoft.fitmy.v1.activity.landing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.mtechsoft.fitmy.BuildConfig;
import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.activity.dashboard.FitnessActivity;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.HttpHelper;
import com.mtechsoft.fitmy.v1.common.LocaleHelper;
import com.mtechsoft.fitmy.v1.service.StepsService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setLocale();

        setLogoAnimation();
        setVersionLabel();

        setFirebase();
        startStepService();

        checkUserLoggedIn();
    }

    private void setLocale() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String locale = sharedPreferences.getString(Constants.SP_LANG, Constants.LOCALE_MALAY);
        LocaleHelper.setActivityLocale(this, locale);
    }

    private void setLogoAnimation() {
        ImageView iv_am_logo_01 = findViewById(R.id.iv_am_logo_01);
        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_cw);
        iv_am_logo_01.startAnimation(aniRotate);
    }

    private void setVersionLabel() {
        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;
        TextView tv_am_version_01 = findViewById(R.id.tv_am_version_01);
        tv_am_version_01.setText(String.format("%s (%s)", versionName, String.valueOf(versionCode)));
    }

    private void checkUserLoggedIn() {
        if (checkInternet()) {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
            final boolean SP_IS_LOGIN = sharedPreferences.getBoolean(Constants.SP_IS_LOGIN, false);
            final String SP_PHONE_NUM = sharedPreferences.getString(Constants.SP_PHONE_NUM, null);
            final String SP_APP_PASSWORD = sharedPreferences.getString(Constants.SP_APP_PASSWORD, null);

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    if (SP_IS_LOGIN) {
                        new Thread() {
                            @Override
                            public void run() {
                                doRefreshToken(SP_PHONE_NUM, SP_APP_PASSWORD);
                            }
                        }.start();
                    } else {
                        goIntroduction();
                    }
                }
            }, 2000);
        }
    }

    private void doRefreshToken(String phone_num, String app_password) {
        JSONObject responseObj = HttpHelper.requestToken(phone_num, app_password);

        if (responseObj != null) {
            updateToken(responseObj);
            updateProfile(responseObj);

            goDashboard();

        } else {
            goLogin();
        }
    }

    public void updateToken(JSONObject jsonObject) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            String token = jsonObject.getString("token");
            editor.putString(Constants.SP_TOKEN, token);
            editor.commit();

            Log.d("updateToken", token);

        } catch (JSONException ex) {
            Log.e("updateToken", ex.getMessage());
        }
    }

    public void updateProfile(JSONObject jsonObject) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            JSONObject profileObj = jsonObject.getJSONObject("profile");

            editor.putString(Constants.SP_USER_PROFILE, profileObj.toString());
            editor.putInt(Constants.SP_CURRENT_POINT, profileObj.getInt("total_points"));
            editor.putString(Constants.SP_CURRENT_STEP, profileObj.getString("total_steps"));
            editor.putString(Constants.SP_WEIGHT, profileObj.getString("weight_kg"));
            editor.putString(Constants.SP_HEIGHT, profileObj.getString("height_cm"));
            editor.commit();

        } catch (JSONException ex) {
            Log.e("updateToken", ex.getMessage());
        }
    }

    private void goIntroduction() {
        startActivity(new Intent(this, LanguagePrefActivity.class));
        finish();
    }

    private void goDashboard() {
        finish();
        startActivity(new Intent(this, FitnessActivity.class));
    }

    private void goLogin() {
        finish();
        startActivity(new Intent(this, StartActivity.class));
    }

    private void startStepService() {
        Intent mStepsIntent = new Intent(getApplicationContext(), StepsService.class);
        startService(mStepsIntent);
    }

    private void setFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    Log.w("setFirebase", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();
                Log.d("setFirebase", token);
            });
    }

    private Boolean isConnected = false, isWiFi = false, isMobile = false;
    private boolean checkInternet() {
        boolean result = false;
        // https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null) {
            isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;
            isMobile = activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
            isConnected = activeNetwork.isConnectedOrConnecting();
        }

        if (isConnected) {
            if (isWiFi) {
//                if (isConnectedToThisServer("www.google.com")) {
//                    result = true;
//                }
                result = true;
            }

            if (isMobile) {
//                if (isConnectedToThisServer("www.google.com")) {
//                    result = true;
//                }
                result = true;
            }
        }

        if (!result) {
            Toast.makeText(this, "FitMY requires internet connection to work. Please connect to the internet and restart the app.", Toast.LENGTH_LONG).show();
        }

        return result;
    }

    // Function that uses ping, takes server name or ip as argument.
    public boolean isConnectedToThisServer(String host) {
        // https://stackoverflow.com/questions/3905358/how-to-ping-external-ip-from-java-android
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 " + host);
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
