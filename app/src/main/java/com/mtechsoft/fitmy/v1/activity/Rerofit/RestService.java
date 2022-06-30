package com.mtechsoft.fitmy.v1.activity.Rerofit;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

/**
 * Created by apple on 12/18/17.
 */

public interface RestService {

    @FormUrlEncoded
    @POST("register")
    Call<ResponseBody> registerii(

            @Field("user_type") String user_type,
            @Field("category_type") String category_type,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("gender") String gender,
            @Field("nation") String nation,
            @Field("citizenship") String citizenship,
            @Field("office_tel_no") String office_tel_no,
            @Field("office_fax_no") String office_fax_no,
            @Field("address") String address,
            @Field("country") String country,
            @Field("state") String state,
            @Field("city") String city,
            @Field("zip_code") String zip_code,
            @Field("password") String password,
            @Field("confirm_password") String confirm_password,
            @Field("height") String height,
            @Field("weight") String weight,
            @HeaderMap Map<String, String> headers

    );


    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> login(

            @Field("email") String email,
            @Field("password") String password,
            @HeaderMap Map<String, String> headers

    );


    @FormUrlEncoded
    @POST("activity")
    Call<ResponseBody> saveActivity(

            @Field("user_id") String user_id,
            @Field("activity_type") String activity_type,
            @Field("timer") String timer,
            @Field("distance") String distance,
            @Field("steps") String steps,
            @Field("kcal") String kcal,
            @HeaderMap Map<String, String> headers

    );



    @FormUrlEncoded
    @POST("update_profile")
    Call<ResponseBody> UpdateProfile(

            @Field("user_type") String user_type,
            @Field("category_type") String category_type,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("gender") String gender,
//            @Field("nation") String nation,
//            @Field("citizenship") String citizenship,
            @Field("office_tel_no") String office_tel_no,
            @Field("office_fax_no") String office_fax_no,
            @Field("address") String address,
            @Field("country") String country,
            @Field("state") String state,
            @Field("city") String city,
            @Field("zip_code") String zip_code,
            @Field("user_id") String user_id,
            @HeaderMap Map<String, String> headers

    );


}
