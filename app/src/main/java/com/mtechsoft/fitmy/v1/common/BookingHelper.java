package com.mtechsoft.fitmy.v1.common;

import android.util.Log;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BookingHelper {
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public static String TOKEN;

    public static JSONObject requestToken(String username, String password) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", username)
                    .add("password", password)
                    .build();

            Request request = new Request.Builder()
                    .url(Constants.API_EFASILITI_USER_LOGIN)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestToken", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject registerUser(String nric, String postcode, String city, String password, String email, String fullname, String address, String phone, int jenis_pengguna, int nationality, int gender, int race, int country, int jenis_kategori, int state) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("username", nric)
                    .add("email", email)
                    .add("tel_bimbit_no", phone)
                    .add("full_name", fullname)
                    .add("alamat", address)
                    .add("poskod", postcode)
                    .add("bandar", city)
                    .add("negeri_id", String.valueOf(state))
                    .add("negara_id", String.valueOf(country))
                    .add("jenis_pengguna_e_kemudahan", String.valueOf(jenis_pengguna))
                    .add("jenis_kategori_id", String.valueOf(jenis_kategori))
                    .add("password", password)
                    .add("jantina_id", String.valueOf(gender))
                    .add("bangsa_id", String.valueOf(race))
//                    .add("nationality", String.valueOf(nationality))
                    .build();

            Request request = new Request.Builder()
                    .url(Constants.API_EFASILITI_USER_REGISTER)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("requestToken", ex.getMessage());
        }

        return responseObj;
    }

    public static JSONObject submitBooking(String complex_id, String facility_id, String jenis_tmepahan_id, String negeri_id, String tarikh_mula, String tarikh_akhir, String waktu_mula, String waktu_akhir, String total_value, String item_name, String item_value, String item_unit, String bayaran_sewa, String quantity_kadar, String jumlah_orang, String bil_unit) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            RequestBody requestBody = new FormBody.Builder()

                    .add("kompleks_id", complex_id)
                    .add("negeri_id", negeri_id)
                    .add("fasiliti_id", facility_id)
                    .add("jenis_tempahan_id", jenis_tmepahan_id)
                    .add("tarikh_mula", tarikh_mula)
                    .add("tarikh_akhir", tarikh_akhir)
                    .add("waktu_mula", waktu_mula)
                    .add("waktu_akhir", waktu_akhir)
                    .add("tatal_value", "")
                    .add("item_name", "")
                    .add("item_value", "")
                    .add("item_unit", "")
                    .add("bayaran_sewa", bayaran_sewa)
                    .add("quantity_kadar", quantity_kadar)
                    .add("jumlah_orang", jumlah_orang)
                    .add("bil_unit", bil_unit)
//                    .add("nationality", String.valueOf(nationality))
                    .build();

            Request request = new Request.Builder()
                    .url("http://internal.mysysdemo.com/backend/web/index.php?r=api/tempahan-kemudahan/create")
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


    public static JSONObject checkBooking(String startDate, String endDate, String startTime, String endTime, int fasiliti_id, int jenis_kadar) {
        OkHttpClient client = new OkHttpClient();
        JSONObject responseObj = null;

        try {
            RequestBody requestBody = new FormBody.Builder()
                    .add("startDate", startDate)
                    .add("endDate", endDate)
                    .add("startTime", startTime)
                    .add("endTime", endTime)
                    .add("fasiliti_id", String.valueOf(fasiliti_id))
                    .add("jenis_kadar", String.valueOf(jenis_kadar))
                    .build();

            Request request = new Request.Builder()
                    .url(Constants.API_EFASILITI_TEMPAHAN_KEMUDAHAN_CHECK_BOOKING)
                    .addHeader("Authorization", "Bearer " + TOKEN)
                    .post(requestBody)
                    .build();
            Response response = client.newCall(request).execute();
            responseObj = new JSONObject(response.body().string());

        } catch (Exception ex) {
            Log.e("checkBooking", ex.getMessage());
        }

        return responseObj;
    }
}
