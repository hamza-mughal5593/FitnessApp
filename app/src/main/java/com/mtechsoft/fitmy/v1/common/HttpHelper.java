package com.mtechsoft.fitmy.v1.common;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpHelper {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static String TOKEN;

    public static JSONObject requestToken(String phone_num, String app_password) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("phone_num", phone_num);
            requestObj.put("app_password", app_password);

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_USER_AUTH_REQUEST_TOKEN)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestToken", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject registerProfile(ProfileObj profile) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("full_name", profile.getFull_name());
            requestObj.put("nick_name", profile.getNick_name());
            requestObj.put("birth_year", profile.getBirth_year());
            requestObj.put("weight_kg", profile.getWeight_kg());
            requestObj.put("height_cm", profile.getHeight_cm());
            requestObj.put("gender", profile.getGender());

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_USER_PROFILE_REGISTER)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("registerProfile", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject requestAppPassword(String phone_num, String user_password) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("phone_num", phone_num);
            requestObj.put("user_password", user_password);

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_USER_AUTH_REQUEST_APP_PASSWORD)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestToken", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject postActivity(ActivityObj activity) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("activity_type", activity.getActivity_type());
            requestObj.put("created_at", activity.getStart_datetime());
            requestObj.put("start_datetime", activity.getStart_datetime());
            requestObj.put("end_datetime", activity.getEnd_datetime());
            requestObj.put("gps_paths", activity.getPaths());
            requestObj.put("steps", activity.getSteps());
            requestObj.put("distance", activity.getDistance());
            requestObj.put("calories", activity.getCalories());

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_FITNESS_POST_ACTIVITY)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());
//            while (!response.isSuccessful()) {
//                response = client.newCall(request).execute();
//            }
            String responseObjStr = responseObj.toString();
        } catch (Exception ex) {
            Log.e("postActivity", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getRewards() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()
                    .url(Constants.API_REWARD_INVENTORY_GET_REWARDS)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getCodes() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()
                    .url(Constants.API_REWARD_REDEEM_GET_CODES)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject requestCode(String item_id) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("item_id", item_id);

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_REWARD_REDEEM_REQUEST_CODES)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestToken", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getEvents() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()
                    .url(Constants.API_EVENT_INVENTORY_GET_EVENTS)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
//            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getNews() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()
                    .url(Constants.API_SOCIAL_GET_NEWS)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
//            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getNewsCommunity() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()
                    .url(Constants.API_SOCIAL_GET_NEWS_COMMUNITY)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
//            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getInterestEvents() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()
                    .url(Constants.API_EVENT_INTEREST_GET)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
//            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject postInterest(String item_id) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("item_id", item_id);

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_EVENT_INTEREST_POST)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestToken", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject requestTAC(String phone_num) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("phone_num", phone_num);
            requestObj.put("user_password", "null");

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .url(Constants.API_USER_AUTH_REQUEST_TAC)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            String responseStr = response.body().string();
            responseObj = new JSONObject(responseStr);

            Log.d("requestTAC", responseStr);

        } catch (Exception ex) {
            Log.e("requestTAC", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONArray getActivities() {
        OkHttpClient client = new OkHttpClient();
        JSONArray responseObj = new JSONArray();

        try {
            Request request = new Request.Builder()

                    .url(Constants.API_FITNESS_GET_ACTIVITIES)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .build();
            Response response = client.newCall(request).execute();

            responseObj = new JSONArray(response.body().string());
        } catch (Exception ex) {
            Log.e("getRewards", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject verifyPhone(String phone_num) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("phone_num", phone_num);

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .url(Constants.API_USER_PROFILE_VERIFY_PHONE)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestTAC", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject updateProfile(ProfileObj profile) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            JSONObject requestObj = new JSONObject();
            requestObj.put("phone_num", profile.getPhone_num());
            requestObj.put("full_name", profile.getFull_name());
            requestObj.put("nick_name", profile.getNick_name());
            requestObj.put("email", profile.getEmail());
            requestObj.put("race", profile.getRace());
            requestObj.put("nationality", profile.getNationality());
            requestObj.put("birth_year", profile.getBirth_year());
            requestObj.put("birth_date", profile.getBirth_date());
            requestObj.put("weight_kg", profile.getWeight_kg());
            requestObj.put("height_cm", profile.getHeight_cm());
            requestObj.put("gender", profile.getGender());

            RequestBody requestBody = RequestBody.create(requestObj.toString(), JSON);
            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .url(Constants.API_USER_PROFILE_UPDATE_PROFILE)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

            Log.d("updateProfile", responseObj.toString());

        } catch (Exception ex) {
            Log.e("updateProfile", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject updatePhoto(byte[] imageData) {
        JSONObject responseObj = null;

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", "file.png", RequestBody.create(MediaType.parse("image/png"), imageData))
                    .build();

            Request request = new Request.Builder()
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .url(Constants.API_USER_PROFILE_UPDATE_PHOTO)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

            Log.d("updatePhoto", responseObj.toString());
        } catch (Exception ex) {
            Log.e("updatePhoto", ex.getMessage());
        }
        return responseObj;
    }
}
