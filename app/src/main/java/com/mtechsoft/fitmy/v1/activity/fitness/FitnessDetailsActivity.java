
package com.mtechsoft.fitmy.v1.activity.fitness;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.DirectionsJSONParser;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.activity.login.LoginPhoneActivity;

public class FitnessDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    //    private String JSON_OBJ;
    private String JSON_OBJ_NEW;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;
    private String time, avg_speed,kilo_meters,km_h,steps,clories;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    int check;

    String timerr,startlat,startlong,endlat,endlong;
    private double distance = 0.0d, stepss = 0.0d, calories = 0.0d;
    private String activityType;
    private Date startDatetime, endDatetime;


    LatLng start_location,end_location;
    String activity_type = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_details);
        prepareDashboard();
        prepareNav();
        Intent intent = getIntent();
        check = intent.getIntExtra("check",0);
//        activityType = intent.getStringExtra("activity_type");
//        timerr= intent.getStringExtra("timer");
//        dis = intent.getStringExtra("distance");
//        activityType = intent.getStringExtra("steps");
//        activityType = intent.getStringExtra("kcal");
//        activityType = intent.getStringExtra("start_date");
//        activityType = intent.getStringExtra("end_date");
//        JSON_OBJ = intent.getStringExtra(Constants.BUNDLE_TEMP_JSON);

        if(check == 1){

            time = intent.getStringExtra("time");
            kilo_meters = intent.getStringExtra("distance");
            steps = intent.getStringExtra("steps");
            clories = intent.getStringExtra("calories");
            startlat = intent.getStringExtra("start_lat");
            startlong = intent.getStringExtra("start_long");
            endlat = intent.getStringExtra("end_lat");
            endlong = intent.getStringExtra("end_long");
            activity_type = intent.getStringExtra("activity_type");

            if (startlat.equals("null")){


            }else {

                start_location= new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlong));
                end_location= new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlong));

                if (start_location.equals(end_location)){

                    Toast.makeText(this, R.string.same_location, Toast.LENGTH_SHORT).show();
                }else{

                    if (activity_type.equals("outdoor_swimming") || activity_type.equals("outdoor_aerobic") || activity_type.equals("outdoor_stairs_climbing")){


                        Toast.makeText(this, "No Location Tracking", Toast.LENGTH_SHORT).show();

                    }else {

                        String url = getDirectionsUrl(start_location,end_location);
                        DownloadTask downloadTask = new DownloadTask();
                        downloadTask.execute(url);

                    }
                }

            }


            tv_aea_timer_counter.setText(time);

            Double aDouble = Double.valueOf(kilo_meters);
            double roundOff = Math.round(aDouble * 100.0) / 100.0;
            tv_aea_distance_counter.setText(String.valueOf(roundOff));

            Double aDoublee = Double.valueOf(steps);
            double roundOffff = Math.round(aDoublee* 100) / 100;

            int x = (int) roundOffff;
            tv_aea_steps_counter.setText(String.valueOf(x));


            Double aDoubleeee = Double.valueOf(clories);
            double roundOfffffff = Math.round(aDoubleeee* 100.0) / 100.0;
            tv_aea_calories_counter.setText(String.valueOf(roundOfffffff));

            String[] separated = time.split(":");
            String hours = separated[0]; // this will contain "Fruit"
            String minutesss = separated[1];
            String secondsss = separated[2];


            Float f= Float.parseFloat(kilo_meters);
            int dist = Math.round(f);

            double pace = (dist* 1000) / Integer.parseInt(secondsss);

            DecimalFormat df = new DecimalFormat("0.##");

            tv_aea_pace_counter.setText(String.valueOf(df.format(pace)));


            prepareList();



        }else {

        JSON_OBJ_NEW = intent.getStringExtra("response");


        try {
            JSONObject jsonObject = new JSONObject(JSON_OBJ_NEW);
            JSONObject data = jsonObject.getJSONObject("data");
            String distance = data.getString("distance");
            String steps = data.getString("steps");
            String calories = data.getString("kcal");
            String timer = data.getString("timer");
//            startlat = data.getString("start_latitude");
//            startlong = data.getString("start_longitude");
            endlat = data.getString("end_latitude");
            endlong = data.getString("end_longitude");
            activity_type = data.getString("activity_type");

            String fitpoints = data.getString("total_fitness_points");
            Utilities.saveString(FitnessDetailsActivity.this,"fitness_points",fitpoints);







            startlat = Utilities.getString(FitnessDetailsActivity.this,"start_lat");
            startlong= Utilities.getString(FitnessDetailsActivity.this,"start_long");
            activity_type = Utilities.getString(FitnessDetailsActivity.this,"activity_type");



            start_location = new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlong));


            end_location = new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlong));


            Utilities.saveString(FitnessDetailsActivity.this,"distance",distance);
            Utilities.saveString(FitnessDetailsActivity.this,"steps",steps);
            Utilities.saveString(FitnessDetailsActivity.this,"kcal",calories);


            String[] separated = timer.split(":");
            String hours = separated[0]; // this will contain "Fruit"
            String minutesss = separated[1];
            String secondsss = separated[2];


            Float f= Float.parseFloat(distance);
            int dist = Math.round(f);

            double pace = (dist* 1000) / Integer.parseInt(secondsss);

            DecimalFormat df = new DecimalFormat("0.##");

            tv_aea_pace_counter.setText(String.valueOf(df.format(pace)));



            Double aDouble = Double.valueOf(distance);
            double roundOff = Math.round(aDouble * 100.0) / 100.0;

            tv_aea_distance_counter.setText(String.valueOf(roundOff));

            Double aDoubleee = Double.valueOf(steps);
            double roundOffff = Math.round(aDoubleee * 100) / 100;
            int x = (int) roundOffff;
            tv_aea_steps_counter.setText(String.valueOf(x));



            Double aDoubleeeeee = Double.valueOf(calories);
            double roundOfffffff = Math.round(aDoubleeeeee * 100.0) / 100.0;

            tv_aea_calories_counter.setText(String.valueOf(roundOfffffff));

//            df = new DecimalFormat("#00");
//            tv_aea_timer_counter.setText(String.format("%s:%s:%s", df.format(hours), df.format(minutes), df.format(seconds)));
            tv_aea_timer_counter.setText(timer);


            if (start_location.equals(end_location)){

                Toast.makeText(this, R.string.same_location, Toast.LENGTH_SHORT).show();
            }else{

                if (activity_type.equals("outdoor_swimming") || activity_type.equals("outdoor_aerobic") || activity_type.equals("outdoor_stairs_climbing")){

                }else {

                    String url = getDirectionsUrl(start_location,end_location);
                    DownloadTask downloadTask = new DownloadTask();
                    downloadTask.execute(url);

                }


            }


        } catch (JSONException ex) {
            // TODO
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
            prepareList();
        }


        prepareMap();


    }


    //region prepareMap()

    private GoogleMap mMap;

    private Polyline polyline;
    private PolylineOptions polylineOptions;

    private void prepareMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {

                        if (start_location !=null && end_location != null ){

                            if (start_location.equals(end_location)){

                                Toast.makeText(FitnessDetailsActivity.this, R.string.same_location, Toast.LENGTH_SHORT).show();
                            }else{

                                if (activity_type.equals("outdoor_stairs_climbing") || activity_type.equals("outdoor_swimming") || activity_type.equals("outdoor_aerobic")){

                                    Toast.makeText(FitnessDetailsActivity.this, "No Location Tracking", Toast.LENGTH_SHORT).show();

                                }else {

                                    String url = getDirectionsUrl(start_location,end_location);
                                    DownloadTask downloadTask = new DownloadTask();
                                    // Start downloading json data from Google Directions API
                                    downloadTask.execute(url);
                                }
                            }

                        }else {

                            Toast.makeText(FitnessDetailsActivity.this, R.string.no_location_data, Toast.LENGTH_SHORT).show();
                        }



                        setCurrentLocation(location);
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());

        polylineOptions = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        try {
            boolean success = googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.community_sample));
            if (!success) {
                Log.e("onMapReady", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("onMapReady", "Can't find style. Error: ", e);
        }





//        try {
//            JSONObject jsonObject = new JSONObject(JSON_OBJ_NEW);
//            String paths = jsonObject.getString("paths");
//            if (!paths.equals("")) {
//                String[] pathArray = paths.split(";");
//
//                for (int i = 0; i < pathArray.length; i++) {
//                    String path = pathArray[i];
//                    String[] pathDetails = path.split(",");
//
//                    double lat = Double.parseDouble(pathDetails[0]);
//                    double lng = Double.parseDouble(pathDetails[1]);
//
//                    LatLng currentLocation = new LatLng(lat, lng);
//                    polylineOptions.add(currentLocation);
//
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16));
//
//                    if (i == 0) mMap.addMarker(new MarkerOptions().position(currentLocation).title("Start"));
//                    if (i == pathArray.length - 1) mMap.addMarker(new MarkerOptions().position(currentLocation).title("Finish"));
//                }
//            }
//
//            polyline = mMap.addPolyline(polylineOptions);
//        } catch (JSONException ex) {
//            // TODO
//        }
    }

    //endregion

    //region prepareDashboard()

    private TextView tv_aea_timer_counter;
    private TextView tv_aea_speed_counter;
    private TextView tv_aea_distance_counter;
    private TextView tv_aea_steps_counter;
    private TextView tv_aea_calories_counter;
    private TextView tv_aea_pace_counter;

    private void prepareDashboard() {
        tv_aea_timer_counter = findViewById(R.id.tv_aea_timer_counter);
        tv_aea_distance_counter = findViewById(R.id.tv_aea_distance_counter);
        tv_aea_speed_counter = findViewById(R.id.tv_aea_speed_counter);
        tv_aea_steps_counter = findViewById(R.id.tv_aea_steps_counter);
        tv_aea_calories_counter = findViewById(R.id.tv_aea_calories_counter);
        tv_aea_pace_counter = findViewById(R.id.tv_aea_pace_counter);

//        try {
//            JSONObject jsonObject = new JSONObject(JSON_OBJ);
//            double distance = jsonObject.getDouble("distance");
//            double steps = jsonObject.getDouble("steps");
//            double calories = jsonObject.getDouble("calories");
//
//            long diff = Utils.toDate(jsonObject.getString("end_datetime")).getTime() - Utils.toDate(jsonObject.getString("start_datetime")).getTime();
////            long diff = Utils.toDateNonT("2019-12-14 18:48:55.000").getTime() - Utils.toDateNonT("2019-12-14 18:45:02.000").getTime();
//            long seconds = diff / 1000;
//            long minutes = seconds / 60;
//            long hours = minutes / 60;
//
//            minutes = minutes % 60;
//            seconds = seconds % 60;
//
//            double pace = (distance * 1000) / seconds; //((distance/1000) / TotalSeconds) * 3600;
//            double mMinutes = pace / 60;
//            double mSeconds = pace % 60;
//            tv_aea_pace_counter.setText(String.format("%o\"%o\'", (int)mMinutes, (int)mSeconds));
//
//            DecimalFormat df = new DecimalFormat("0.##");
//            tv_aea_distance_counter.setText(df.format(distance));
//            tv_aea_steps_counter.setText(df.format(steps));
//            tv_aea_calories_counter.setText(df.format(calories));
//
//            df = new DecimalFormat("#00");
//            tv_aea_timer_counter.setText(String.format("%s:%s:%s", df.format(hours), df.format(minutes), df.format(seconds)));

//        }
//        catch (JSONException ex) {
//            // TODO
//        }
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    //endregion

    private void setCurrentLocation(Location location) {
        wayLatitude = location.getLatitude();
        wayLongitude = location.getLongitude();
        Log.d("setCurrentLocation", String.format("%s -- %s", wayLatitude, wayLongitude));

        mMap.clear();


        if (activity_type.equals("outdoor_stairs_climbing") || activity_type.equals("outdoor_swimming") || activity_type.equals("outdoor_aerobic")){

        }else {

            mMap.addMarker(new MarkerOptions()
                    .title("Destination")
                    .position(new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlong)))
                    .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.green)));
        }



        mMap.addMarker(new MarkerOptions()
                .title("Start")
                .position(new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlong)))
                .icon(bitmapDescriptorFromVector(getApplicationContext(),R.drawable.red)));

//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_pin);
////        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 64, 64, false);
////        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_pin);
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 64, 64, false);
//        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

//
//        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.redmarker);
//        Bitmap smallMarker2 = Bitmap.createScaledBitmap(b2, 64, 64, false);
//        BitmapDescriptor smallMarkerIcon2 = BitmapDescriptorFactory.fromBitmap(smallMarker2);


//        LatLng currentLocation = new LatLng(wayLatitude, wayLongitude);
        LatLng endLocation = new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlong));
////        MarkerOptions marker = new MarkerOptions().position(endLocation);
////        marker.icon(smallMarkerIcon);
//
//        LatLng startlocation = new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlong));
//        MarkerOptions marker2 = new MarkerOptions().position(startlocation);
//        marker2.icon(smallMarkerIcon2);
//
//
//        MarkerOptions markerr = new MarkerOptions().position(new LatLng(Double.parseDouble(endlat), Double.parseDouble(endlong)));
//        MarkerOptions markerr2 = new MarkerOptions().position(new LatLng(Double.parseDouble(startlat), Double.parseDouble(startlong)));
//
//// adding marker
////        mMap.addMarker(markerr);
//        mMap.addMarker(markerr2);
//        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(endLocation, 16));
    }

    private void prepareNav() {
        ImageView b_aea_close = findViewById(R.id.b_aea_close);

        b_aea_close.setOnClickListener(view -> {
            finish();
        });
    }

    //region prepareList()

    private void prepareList() {
        ImageView iv_aea_list_active = findViewById(R.id.iv_aea_list_active);

            switch (activity_type) {
                case "outdoor_walking":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_walk));
                    break;
                case "outdoor_running":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_running));
                    break;
                case "outdoor_stairs_climbing":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_climbing));
                    break;
                case "outdoor_cycling":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_cycling));
                    break;
                case "outdoor_hiking":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_hiking));
                    break;
                case "outdoor_swimming":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_swimming));
                    break;
                case "outdoor_aerobic":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_aerobic));
                    break;
                case "indoor_running":
                    iv_aea_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_indoor_running));
                    break;
            }


    }


    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        String key = "key=" + getResources().getString(R.string.map_key);


        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode + "&" + key;

//        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("result", "" + result);
            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList();
                lineOptions = new PolylineOptions();

                List<HashMap<String, String>> path = result.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap point = path.get(j);

                    double lat = Double.parseDouble(String.valueOf(point.get("lat")));
                    double lng = Double.parseDouble(String.valueOf(point.get("lng")));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                lineOptions.addAll(points);
                lineOptions.width(10);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);

            }

// Drawing polyline in the Google Map for the i-th route
            if(lineOptions!=null)
            {
                mMap.addPolyline(lineOptions);
            }
        }
    }


    //endregion
}
