package com.mtechsoft.fitmy.v1.common;

import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.preference.PreferenceManager;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.acos;

public class Utils {

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {

        // Convert degrees to radians
        lat1 = lat1 * Math.PI / 180.0;
        lon1 = lon1 * Math.PI / 180.0;

        lat2 = lat2 * Math.PI / 180.0;
        lon2 = lon2 * Math.PI / 180.0;

        // radius of earth in metres
        double r = 6378100;

        // P
        double rho1 = r * cos(lat1);
        double z1 = r * sin(lat1);
        double x1 = rho1 * cos(lon1);
        double y1 = rho1 * sin(lon1);

        // Q
        double rho2 = r * cos(lat2);
        double z2 = r * sin(lat2);
        double x2 = rho2 * cos(lon2);
        double y2 = rho2 * sin(lon2);

        // Dot product
        double dot = (x1 * x2 + y1 * y2 + z1 * z2);
        double cos_theta = dot / (r * r);

        double theta = acos(cos_theta);

        // Distance in Metres
        return r * theta;
    }

    public static String convertDatetimeSQL(Date input) {
        String format = "yyyy-MM-dd HH:mm:ss.000";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(input);
    }

    public static String getDate(String timestamp) {
        String formatFrom = "yyyy-MM-dd'T'HH:mm:ss";
        String formatTo = "dd MMM yyyy";
        String output = "";

        try {
            SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat(formatFrom);
            Date outputDate = simpleDateFormatFrom.parse(timestamp);

            SimpleDateFormat simpleDateFormatTo = new SimpleDateFormat(formatTo);
            output = simpleDateFormatTo.format(outputDate);
        } catch (ParseException ex) {
            // TODO
            String exStr = ex.getMessage();
        }

        return output;
    }

    public static String getDateNonT(String timestamp) {
        String formatFrom = "yyyy-MM-dd HH:mm:ss";
        String formatTo = "dd MMM yyyy";
        String output = "";

        try {
            SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat(formatFrom);
            Date outputDate = simpleDateFormatFrom.parse(timestamp);

            SimpleDateFormat simpleDateFormatTo = new SimpleDateFormat(formatTo);
            output = simpleDateFormatTo.format(outputDate);
        } catch (ParseException ex) {
            // TODO
            String exStr = ex.getMessage();
        }

        return output;
    }

    public static String getTime(String timestamp) {
        String formatFrom = "yyyy-MM-dd'T'H:mm:ss";
        String formatTo = "hh:mm a";
        String output = "";

        try {
            SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat(formatFrom);
            Date outputDate = simpleDateFormatFrom.parse(timestamp);

            SimpleDateFormat simpleDateFormatTo = new SimpleDateFormat(formatTo);
            output = simpleDateFormatTo.format(outputDate);
        } catch (ParseException ex) {
            // TODO
        }

        return output;
    }

    public static Date toDate(String timestamp) {
        String formatFrom = "yyyy-MM-dd'T'H:mm:ss";
        Date output = null;

        try {
            SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat(formatFrom);
            output = simpleDateFormatFrom.parse(timestamp);
        } catch (ParseException ex) {
            // TODO
        }

        return output;
    }

    public static Date toDateNonT(String timestamp) {
        String formatFrom = "yyyy-MM-dd H:mm:ss";
        Date output = null;

        try {
            SimpleDateFormat simpleDateFormatFrom = new SimpleDateFormat(formatFrom);
            output = simpleDateFormatFrom.parse(timestamp);
        } catch (ParseException ex) {
            // TODO
        }

        return output;
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static String getStringFromRawResource(Resources resources, int resId) {
        InputStream rawResource = resources.openRawResource(resId);
        String content = streamToString(rawResource);
        try {rawResource.close();} catch (IOException e) {}
        return content;
    }

    private static String streamToString(InputStream in) {
        String l;
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder s = new StringBuilder();
        try {
            while ((l = r.readLine()) != null) {
                s.append(l + "\n");
            }
        } catch (IOException e) {}
        return s.toString();
    }

    static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_locaction_updates";

    /**
     * Returns true if requesting location updates, otherwise returns false.
     *
     * @param context The {@link Context}.
     */
    public static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    /**
     * Stores the location updates state in SharedPreferences.
     * @param requestingLocationUpdates The location updates state.
     */
    public static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    /**
     * Returns the {@code location} object as a human readable string.
     * @param location  The {@link Location}.
     */
    public static String getLocationText(Location location) {
        return location == null ? "Unknown location" :
                "(" + location.getLatitude() + ", " + location.getLongitude() + ")";
    }

    public static String getLocationTitle(Context context) {
        return String.format("gps updates: %s", DateFormat.getDateTimeInstance().format(new Date()));
    }

    public static Date toDateOnly(String dateStr) {
        Date date = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            date = format.parse (dateStr);
        } catch (Exception ex) {
            // TODO
        }

        return date;
    }

    public static Date toTimeOnly(String timeStr) {
        Date date = null;

        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            date = format.parse (timeStr);
        } catch (Exception ex) {
            // TODO
        }

        return date;
    }
}
