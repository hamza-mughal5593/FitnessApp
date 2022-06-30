package com.mtechsoft.fitmy.v1.common;

import android.app.Activity;
import android.content.res.Configuration;

import java.util.Locale;

public class LocaleHelper {

    public static void setActivityLocale(Activity activity, String locale_code) {

        Locale locale = new Locale(locale_code);
        Locale.setDefault(locale);

        Configuration config = new Configuration();
        config.locale = locale;

        activity.getResources().updateConfiguration(config, activity.getResources().getDisplayMetrics());
    }
}