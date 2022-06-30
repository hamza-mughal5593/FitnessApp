package com.mtechsoft.fitmy.v1.common;

public class Constants {
    public static final String SP_PACKAGE_NAME = "com.fitmy.app";
    public static final String SP_IS_LOGIN = "SP_IS_LOGIN";
    public static final String SP_PHONE_NUM = "SP_PHONE_NUM";
    public static final String SP_APP_PASSWORD = "SP_APP_PASSWORD";
    public static final String SP_TOKEN = "SP_TOKEN";
    public static final String SP_USER_PROFILE = "SP_USER_PROFILE";
    public static final String SP_SLEEP_DATA = "SP_SLEEP_DATA";
    public static final String SP_LANG = "SP_LANG";

    public static final String SP_STEPS = "SP_STEPS";
    public static final String SP_DISTANCE = "SP_DISTANCE";
    public static final String SP_CALORIES = "SP_CALORIES";
    public static final String SP_WEIGHT = "SP_WEIGHT";
    public static final String SP_HEIGHT = "SP_HEIGHT";
    public static final String SP_ACTIVITY_ACTIVE = "SP_ACTIVITY_ACTIVE";
    public static final String SP_ACTIVITY_TYPE = "SP_ACTIVITY_TYPE";
    public static final String SP_ACTIVITY_STARTDATE = "SP_ACTIVITY_STARTDATE";
    public static final String SP_ACTIVITY_STARTTIME = "SP_ACTIVITY_STARTTIME";
    public static final String SP_ACTIVITY_LOCATIONS = "SP_ACTIVITY_LOCATIONS";
    public static final String SP_ACTIVITY_STEPS = "SP_ACTIVITY_STEPS";
    public static final String SP_ACTIVITY_LASTRESUME = "SP_ACTIVITY_LASTRESUME";
    public static final String SP_ACTIVITY_PAUSED_SECONDS = "SP_ACTIVITY_PAUSED_SECONDS";
    public static final String SP_ACTIVITY_RUNNING_SECONDS = "SP_ACTIVITY_RUNNING_SECONDS";

    public static final String SP_CURRENT_POINT = "SP_CURRENT_POINT";
    public static final String SP_CURRENT_STEP = "SP_CURRENT_STEP";

    public static final String BUNDLE_TEMP_JSON = "BUNDLE_TEMP_JSON";
    public static final String BUNDLE_TEMP_JSON_2 = "BUNDLE_TEMP_JSON_2";
    public static final String BUNDLE_TEMP_PHONE_NUM = "BUNDLE_TEMP_PHONE_NUM";
    public static final String BUNDLE_TEMP_OTP = "BUNDLE_TEMP_OTP";
    public static final String BUNDLE_ACTIVITY_TYPE = "BUNDLE_ACTIVITY_TYPE";

    public static final String SP_TEMP_FIRST_NAME = "SP_TEMP_FIRST_NAME";
    public static final String SP_TEMP_LAST_NAME = "SP_TEMP_LAST_NAME";
    public static final String SP_TEMP_STATE = "SP_TEMP_STATE";
    public static final String SP_TEMP_COORDINATE = "SP_TEMP_COORDINATE";

    public static final String LOCALE_ENGLISH = "en";
    public static final String LOCALE_MALAY = "ms";

    public static final String SP_EFASILITI_TOKEN = "SP_EFASILITI_TOKEN";
    public static final String SP_EFASILITI_CART = "SP_EFASILITI_CART";
    public static final String SP_EFASILITI_LOCAL_VAR_USERNAME = "SP_EFASILITI_LOCAL_VAR_USERNAME";
    public static final String SP_EFASILITI_LOCAL_VAR_PASSWORD = "SP_EFASILITI_LOCAL_VAR_PASSWORD";

    public static final String API_EFASILITI_BASE_URL = "https://internal.mysysdemo.com";
    public static final String API_EFASILITI_USER_LOGIN = API_EFASILITI_BASE_URL + "/backend/web/index.php?r=api/user-public/login";
    public static final String API_EFASILITI_USER_REGISTER = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/user-public/create";
    public static final String API_EFASILITI_USER_PASS_RESET = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/user-public/request-password-reset";

    public static final String API_EFASILITI_TEMPAHAN_KEMUDAHAN_CALENDAR = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/tempahan-kemudahan/calendar";
    public static final String API_EFASILITI_TEMPAHAN_KEMUDAHAN_CHECK_BOOKING = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/tempahan-kemudahan/check-booking";
    public static final String API_EFASILITI_TEMPAHAN_KEMUDAHAN_VIEW = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/tempahan-kemudahan/view";
    public static final String API_EFASILITI_TEMPAHAN_KEMUDAHAN_CREATE = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/tempahan-kemudahan/create";
    public static final String API_EFASILITI_TEMPAHAN_KEMUDAHAN_VERIFY = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/tempahan-kemudahan/pengesahan";
    public static final String API_EFASILITI_TEMPAHAN_KEMUDAHAN_HISTORY_BOOKING = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/tempahan-kemudahan/get-booking-history";

    public static final String API_EFASILITI_PENGURUSAN_SEDIA_ADA_LIST_KEMUDAHAN_VENUE = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/pengurusan-sedia-ada/list-kemudahan-venue";
    public static final String API_EFASILITI_PENGURUSAN_SEDIA_ADA_LIST_VENUE = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/pengurusan-sedia-ada/list-venue";
    public static final String API_EFASILITI_PENGURUSAN_SEDIA_ADA_LIST_FASILITI_BY_KOMPLEKS = API_EFASILITI_BASE_URL +"/backend/web/index.php?r=api/pengurusan-sedia-ada/list-fasiliti-by-kompleks";

    public static final String API_USER_AUTH_REQUEST_TOKEN = "https://api.fitmyapp.asia/api/user/auth/request_token";
    public static final String API_USER_AUTH_REQUEST_APP_PASSWORD = "https://api.fitmyapp.asia/api/user/auth/request_app_password";
    public static final String API_USER_AUTH_REQUEST_TAC = "https://api.fitmyapp.asia/api/user/auth/request_tac";

    public static final String API_USER_PROFILE_REGISTER = "https://api.fitmyapp.asia/api/user/profile/register_profile";
    public static final String API_USER_PROFILE_VERIFY_PHONE = "https://api.fitmyapp.asia/api/user/profile/verify_phone";
    public static final String API_USER_PROFILE_UPDATE_PROFILE = "https://api.fitmyapp.asia/api/user/profile/update_profile";
    public static final String API_USER_PROFILE_UPDATE_PHOTO = "https://api.fitmyapp.asia/api/user/profile/update_photo";

    public static final String API_FITNESS_POST_ACTIVITY = "https://api.fitmyapp.asia/api/fitness/activity/post_activity";
    public static final String API_FITNESS_GET_ACTIVITIES = "https://api.fitmyapp.asia/api/fitness/activity/get_activities";

    public static final String API_REWARD_INVENTORY_GET_REWARDS = "https://api.fitmyapp.asia/api/reward/inventory/get_rewards";
    public static final String API_REWARD_REDEEM_GET_CODES = "https://api.fitmyapp.asia/api/reward/redeem/get_codes";
    public static final String API_REWARD_REDEEM_REQUEST_CODES = "https://api.fitmyapp.asia/api/reward/redeem/request_code";
    public static final String API_REWARD_REDEEM_VALIDATE_CODE = "https://api.fitmyapp.asia/api/reward/redeem/validate_code";

    public static final String API_EVENT_INVENTORY_GET_EVENTS = "https://api.fitmyapp.asia/api/event/inventory/get_events";

    public static final String API_EVENT_INTEREST_POST = "https://api.fitmyapp.asia/api/event/interest/post_interest";
    public static final String API_EVENT_INTEREST_GET = "https://api.fitmyapp.asia/api/event/interest/get_interest";

    public static final String API_SOCIAL_GET_NEWS = "https://api.fitmyapp.asia/api/social/news/get_news";
    public static final String API_SOCIAL_GET_NEWS_COMMUNITY = "https://api.fitmyapp.asia/api/social/news/get_news_community";
}
