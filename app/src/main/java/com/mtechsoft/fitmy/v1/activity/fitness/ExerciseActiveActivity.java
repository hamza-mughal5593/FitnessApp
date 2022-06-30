package com.mtechsoft.fitmy.v1.activity.fitness;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.gmail.samehadar.iosdialog.IOSDialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.GPSTracker;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.api.ApiModelClass;
import com.mtechsoft.fitmy.v1.api.ServerCallback;
import com.mtechsoft.fitmy.v1.common.ActivityObj;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.common.HttpHelper;
import com.mtechsoft.fitmy.v1.common.LocationTs;
import com.mtechsoft.fitmy.v1.common.LocationUpdatesUtils;
import com.mtechsoft.fitmy.v1.common.Utils;
import com.mtechsoft.fitmy.v1.service.LocationUpdatesService;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ExerciseActiveActivity extends FragmentActivity implements OnMapReadyCallback, SensorEventListener, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = ExerciseActiveActivity.class.getSimpleName();

    private double distance = 0.0d, steps = 0.0d, calories = 0.0d;
    private String activityType;
    private Date startDatetime, endDatetime;
    private NotificationCompat.Builder builder;
    private String userWeight = "";
    private String timeee;
    private int check = 0;
    private boolean isRunning = true;
    private boolean isPaused = false;

    private String cachedLocations;

    String start_lat = "", start_long = "", end_lat = "", end_long = "";

    ArrayList<String> list = new ArrayList<>();

    String comig_from = "";

    Circle circle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_active);

        activityType = getIntent().getStringExtra(Constants.BUNDLE_ACTIVITY_TYPE);
        String comig_from = getIntent().getStringExtra("Coming_from");

        if (comig_from.equals("fitness_history")){

//            doStart();

        }else {

            doStart();

        }



        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {



                return;
            }
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {


            }

            @Override
            public void onProviderDisabled(String provider) {
            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        bindService(new Intent(this, LocationUpdatesService.class), mServiceConnection,
                Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!isSensorRegistered && isSensorPresent) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_FASTEST);
            isSensorRegistered = true;
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(LocationUpdatesService.ACTION_BROADCAST));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }
        super.onStop();
    }

    private void doStart() {
        myReceiver = new MyReceiver();

        if (LocationUpdatesUtils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }

        prepareParameters();
        prepareList();
        prepareMap();
        prepareNav();
        prepareDashboard();
        prepareStepCounter();
        prepareNotification();

    }

    //region prepareMap()

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private final int DEFAULT_ZOOM = 16;
    private int gpsCounter = 0;

    private Location lastLocation;
    private Date lastLocationTimestamp;
    private List<LocationTs> locationList = new ArrayList<>();

    private Polyline polyline;
    private PolylineOptions polylineOptions;

    private void prepareMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        polylineOptions = new PolylineOptions().width(3).color(Color.BLUE).geodesic(true);

        if (!activityType.equalsIgnoreCase("indoor_running")
                && !activityType.equalsIgnoreCase("outdoor_stairs_climbing")
                && !activityType.equalsIgnoreCase("outdoor_aerobic")) {


            locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setSmallestDisplacement(1);
            locationRequest.setInterval(1000);
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null) {
                        return;
                    }
                    for (Location location : locationResult.getLocations()) {
                        if (location != null) {
                            setCurrentLocation(location, false);
                        }
                    }
                }
            };
//            mFusedLocationClient.requestLocationUpdates(locationRequest,
//                    locationCallback,
//                    Looper.getMainLooper());
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        } else {
//            getLastLocation();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
//             Customise the styling of the base map using a JSON object defined
//             in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.community_sample));

            if (!success) {
                Log.e("", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("", "Can't find style. Error: ", e);
        }

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);
        mMap.setOnMarkerClickListener(marker -> true);

        startLocationTracking();
    }

    private void startLocationTracking() {
        if (!activityType.equalsIgnoreCase("indoor_running")
                && !activityType.equalsIgnoreCase("outdoor_stairs_climbing")
                && !activityType.equalsIgnoreCase("outdoor_aerobic")) {
            loadCachedLocations();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        if (!checkPermissions()) {
                            requestPermissions();
                        } else {
                            mService.requestLocationUpdates();
                        }
                    });
                }
            }, 2000);

        }
    }

    private void loadCachedLocations() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        boolean isActivityActive = sharedPreferences.getBoolean(Constants.SP_ACTIVITY_ACTIVE, false);
        if (isActivityActive && !isPaused) {
            gpsCounter = gpsCounter == 0 ? 1 : gpsCounter;
            String[] locations = cachedLocations.split(";");
            for (String locationStr : locations) {
                String[] coordStr = locationStr.split(",");

                try {
                    if (coordStr.length == 2) {

                        Location locationTemp = new Location("");
                        locationTemp.setLatitude(Double.parseDouble(coordStr[0]));
                        locationTemp.setLongitude(Double.parseDouble(coordStr[1]));
                        setCurrentLocation(locationTemp, true);
                    }
                } catch (Exception ex) {
                    // TODO
                }
            }

            isPaused = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getLastLocation();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

//    private void getLastLocation() {
//        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
//            if (location != null) {
//                setCurrentLocation(location, false);
//            }
//        });
//    }

    private void setCurrentLocation(Location location, boolean isCache) {
        gpsCounter++;

//        updateDebug(location);

        wayLatitude = location.getLatitude();
        wayLongitude = location.getLongitude();
        Log.d("setCurrentLocation", String.format("%s -- %s", wayLatitude, wayLongitude));

        LatLng currentLocation = new LatLng(wayLatitude, wayLongitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM));

//        mMap.addCircle(new CircleOptions()
//                .center(new LatLng(wayLatitude, wayLatitude))
//                .radius(10000)
//                .strokeColor(Color.RED)
//                .fillColor(Color.BLUE));

        if (gpsCounter > 3) {
            if (lastLocation != null) {
//                double lat1 = lastLocation.getLatitude();
//                double lon1 = lastLocation.getLongitude();
//                double lat2 = location.getLatitude();
//                double lon2 = location.getLongitude();
//
//                double tempDistanceKM = Utils.calculateDistance(lat1, lon1, lat2, lon2) / 1000;
//                if (tempDistanceKM > 0.0d) distance += tempDistanceKM;

                float tempDistanceKM = lastLocation.distanceTo(location) / 1000;
                long diffInMs = new Date().getTime() - lastLocationTimestamp.getTime();
                long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

                double speed = (tempDistanceKM / diffInSec) * 3600;
                Log.d("speed", String.valueOf(speed));
                if (speed <= 140.0d || isCache) {
                    polylineOptions.add(currentLocation);
                    polyline = mMap.addPolyline(polylineOptions);

                    if (lastLocation != null)
                        updateDashboard(location, new Date());

                    lastLocation = location;
                    lastLocationTimestamp = new Date();

                    LocationTs tempLocation = new LocationTs();
                    tempLocation.setLocation(lastLocation);
                    tempLocation.setTimestamp(lastLocationTimestamp);
                    locationList.add(tempLocation);
                } else {
                    lastLocation = location;
                    lastLocationTimestamp = new Date();
                }
            } else {

                polylineOptions.add(currentLocation);
                polyline = mMap.addPolyline(polylineOptions);

                if (lastLocation != null)
                    updateDashboard(location, new Date());

                lastLocation = location;
                lastLocationTimestamp = new Date();

                LocationTs tempLocation = new LocationTs();
                tempLocation.setLocation(lastLocation);
                tempLocation.setTimestamp(lastLocationTimestamp);
                locationList.add(tempLocation);

                mMap.addMarker(new MarkerOptions().position(currentLocation).title("Start"));
            }
        }
    }

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private MyReceiver myReceiver;
    private LocationUpdatesService mService = null;
    private boolean mBound = false;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            LocationUpdatesService.LocalBinder binder = (LocationUpdatesService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    /**
     * Returns the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(ExerciseActiveActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    String locationStrLast = "";

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        String locationStr = sharedPreferences.getString(Constants.SP_TEMP_COORDINATE, "");
        Log.d("onSharedPrefChange", locationStr);

        if (!locationStr.equalsIgnoreCase("") && !locationStr.equalsIgnoreCase(locationStrLast)) {
            Log.d("validSharedPrefChange", locationStr);
            locationStrLast = locationStr;

            try {
                String[] locationArray = locationStr.split(",");
                Location location = new Location("");
                location.setLatitude(Double.parseDouble(locationArray[0]));
                location.setLongitude(Double.parseDouble(locationArray[1]));

                setCurrentLocation(location, false);
            } catch (Exception ex) {
                // TODO
                String exx = ex.getMessage();
            }
        }
    }

    /**
     * Receiver for broadcasts sent by {@link LocationUpdatesService}.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Location location = intent.getParcelableExtra(LocationUpdatesService.EXTRA_LOCATION);
        }
    }

    //endregion

    //region prepareList()

    private void prepareList() {
        ImageView iv_aea_list_active = findViewById(R.id.iv_aea_list_active);

        switch (activityType) {
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

    //endregion

    private void prepareNav() {
        ImageView iv_aea_menu = findViewById(R.id.iv_aea_menu);
        ConstraintLayout cl_aea_menu = findViewById(R.id.cl_aea_menu);
        ConstraintLayout cl_dashboard = findViewById(R.id.cl_dashboard);
        ImageView iv_aea_menu_01 = findViewById(R.id.iv_aea_menu_01);
        ImageView iv_aea_menu_02 = findViewById(R.id.iv_aea_menu_02);

        iv_aea_menu.setOnClickListener(view -> {
            if (cl_aea_menu.getVisibility() == View.GONE) {
                cl_aea_menu.setVisibility(View.VISIBLE);
            } else {
                cl_aea_menu.setVisibility(View.GONE);
            }
        });

        iv_aea_menu_01.setOnClickListener(view -> {
            View v_aea_01 = findViewById(R.id.v_aea_01);
            if (v_aea_01.getVisibility() == View.INVISIBLE) {
                v_aea_01.setVisibility(View.VISIBLE);
                cl_dashboard.setVisibility(View.VISIBLE);
            } else {
                v_aea_01.setVisibility(View.INVISIBLE);
                cl_dashboard.setVisibility(View.INVISIBLE);
            }
            cl_aea_menu.setVisibility(View.GONE);
        });

        iv_aea_menu_02.setOnClickListener(view -> {
            //TODO
        });

        Button b_aea_stop = findViewById(R.id.b_aea_stop);
        Button b_aea_resume = findViewById(R.id.b_aea_resume);
        Button b_aea_pause = findViewById(R.id.b_aea_pause);
        Button b_aea_close = findViewById(R.id.b_aea_close);

        b_aea_stop.setOnClickListener(view -> {

            Utilities.saveString(ExerciseActiveActivity.this,"calorries",String.valueOf(calories));
            Utilities.saveString(ExerciseActiveActivity.this,"kilometers",String.valueOf(distance));

            endDatetime = new Date();
            goPause();

            b_aea_pause.setVisibility(View.GONE);
            b_aea_resume.setVisibility(View.GONE);
            b_aea_stop.setVisibility(View.INVISIBLE);
            b_aea_close.setVisibility(View.VISIBLE);
            iv_aea_menu.setVisibility(View.INVISIBLE);

            Log.d("btn", "b_aea_stop");
        });

        b_aea_pause.setOnClickListener(view -> {

            goPause();
            check = 1;
            b_aea_pause.setVisibility(View.GONE);
            b_aea_resume.setVisibility(View.VISIBLE);
            b_aea_stop.setVisibility(View.VISIBLE);

            isPaused = true;

            handler.postDelayed(runnablePaused, 1000);

            Log.d("btn", "b_aea_pause");
        });

        b_aea_resume.setOnClickListener(view -> {
            check = 0;
//            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putLong(Constants.SP_ACTIVITY_STARTTIME, StartTime);
//            editor.commit();

            handler.removeCallbacks(runnablePaused);

            prepareDashboard();
            prepareStepCounter();
            prepareNotificationOnResume();


//            doStart();
            b_aea_pause.setVisibility(View.VISIBLE);
            b_aea_resume.setVisibility(View.GONE);
            b_aea_stop.setVisibility(View.INVISIBLE);

            Log.d("btn", "b_aea_resume");
        });

        b_aea_close.setOnClickListener(view -> {

            ExerciseNewActivity.activity_started = false;

            GPSTracker gps = new GPSTracker(this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                end_lat = String.valueOf(latitude);
                end_long = String.valueOf(longitude);


                // Do What you want to do
            }

            SaveActivity();

            Log.d("btn", "b_aea_close");
        });

    }

    public void SaveActivity() {



        String start_lat = Utilities.getString(ExerciseActiveActivity.this,"start_lat");
        String start_long= Utilities.getString(ExerciseActiveActivity.this,"start_long");
        String activity_type= Utilities.getString(ExerciseActiveActivity.this,"activity_type");





        timeee = tv_aea_timer_counter.getText().toString();

        int user_id = Utilities.getInt(ExerciseActiveActivity.this, "user_id");

        final IOSDialog dialog0 = new IOSDialog.Builder(ExerciseActiveActivity.this)
                .setTitleColorRes(R.color.gray)
                .build();
        dialog0.show();

        Map<String, String> postParam = new HashMap<String, String>();

        postParam.put("user_id", String.valueOf(user_id));
        postParam.put("activity_type", activity_type);
        postParam.put("timer", timeee);
        postParam.put("distance", String.valueOf(distance));
        postParam.put("steps", String.valueOf(steps));
        postParam.put("kcal", String.valueOf(calories));
        postParam.put("start_date",String.valueOf(startDatetime));
        postParam.put("end_date", String.valueOf(endDatetime));
        postParam.put("start_latitude",start_lat);
        postParam.put("start_longitude",start_long);
        postParam.put("end_latitude",end_lat);
        postParam.put("end_longitude",end_long);


        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");


        ApiModelClass.GetApiResponse(Request.Method.POST, com.mtechsoft.fitmy.v1.api.Constants.URL.BASE_URL+"activity", ExerciseActiveActivity.this, postParam, headers, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result, String ERROR) {

                if (ERROR.isEmpty()) {

                    dialog0.dismiss();
                    String reso = String.valueOf(result);
                    Intent intent = new Intent(ExerciseActiveActivity.this, FitnessDetailsActivity.class);
                    intent.putExtra("response", reso);
                    intent.putExtra("activity_type",activityType);
                    startActivity(intent);
                    Utilities.hideProgressDialog();
                    doClearCache();
                    finish();

                } else {

                    dialog0.dismiss();
                    Log.i("error", "" + ERROR);
                    Utilities.hideProgressDialog();
                }
            }
        });


    }


    //region prepareDashboard()

    private long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    private int Hours, Seconds, Minutes, TotalSeconds;
    private int pausedSeconds;
    private double currentDistanceKM = 0;
    private Handler handler;

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

        handler = new Handler();
        handler.postDelayed(runnable, 0);
    }

    public Runnable runnable = new Runnable() {
        public void run() {

            DecimalFormat df = new DecimalFormat("#00");

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = (TimeBuff + MillisecondTime);
            Seconds = (int) (UpdateTime / 1000);
            TotalSeconds = Seconds - pausedSeconds;

            Minutes = TotalSeconds / 60;
            Hours = Minutes / 60;

            Minutes = Minutes % 60;
            Seconds = TotalSeconds % 60;

            tv_aea_timer_counter.setText(String.format("%s:%s:%s", df.format(Hours), df.format(Minutes), df.format(Seconds)));

            handler.postDelayed(this, 500);
        }
    };

    public Runnable runnablePaused = new Runnable() {

        public void run() {

            pausedSeconds++;

            SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(Constants.SP_ACTIVITY_PAUSED_SECONDS, pausedSeconds).commit();

            handler.postDelayed(this, 1000);
        }
    };

    private void updateDashboard(Location currentLocation, Date currentLocationTimestamp) {
        Log.d("updateDashboard", "YES");
        Log.d("distanceBefore", String.valueOf(distance));

        DecimalFormat df = new DecimalFormat("0.##");

        if (!activityType.equalsIgnoreCase("indoor_running")
                && !activityType.equalsIgnoreCase("outdoor_stairs_climbing")
                && !activityType.equalsIgnoreCase("outdoor_aerobic")) {
//            double lat1 = lastLocation.getLatitude();
//            double lon1 = lastLocation.getLongitude();
//            double lat2 = currentLocation.getLatitude();
//            double lon2 = currentLocation.getLongitude();

//            double tempDistanceKM = Utils.calculateDistance(lat1, lon1, lat2, lon2) / 1000;
            float tempDistanceKM = lastLocation.distanceTo(currentLocation) / 1000;
            if (tempDistanceKM > 0.0f) distance += tempDistanceKM;
            tv_aea_distance_counter.setText(df.format(distance));
            Log.d("tempDistanceKM", String.valueOf(tempDistanceKM));
            Log.d("distanceAfter", String.valueOf(distance));

            long diffInMs = currentLocationTimestamp.getTime() - lastLocationTimestamp.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

            double speed = (distance / TotalSeconds) * 3600;
            if (tempDistanceKM > 0.0d) tv_aea_speed_counter.setText(df.format(speed));

            double tempSteps = (tempDistanceKM * 1000) * 1.312;
            if (tempDistanceKM > 0.0d) steps += tempSteps;
            tv_aea_steps_counter.setText(String.valueOf((int) steps));

//            calories += (tempSteps / 20);
            calories = calculateCalories();
            tv_aea_calories_counter.setText(df.format((calories)));

            double pace = (distance * 1000) / TotalSeconds; //((distance/1000) / TotalSeconds) * 3600;
            double mMinutes = pace / 60;
            double mSeconds = pace % 60;
            tv_aea_pace_counter.setText(String.format("%o\"%o\'", (int) mMinutes, (int) mSeconds));

            double diff = distance - currentDistanceKM;


            if (diff > 0.1d) {
                currentDistanceKM = distance;

                updateNotification(String.format("%s km / %s calories burned", df.format(distance), df.format(calories)));
//                list.add(String.valueOf(distance));
////                list.add(String.valueOf(calories));
////                Utilities.filePath.postValue(list);
            }

            handler.removeCallbacks(runnableSpeed);

            if (!isRunning) {
                handler.postDelayed(runnableSpeed, 2000);
            }
        }
    }

    public Runnable runnableSpeed = new Runnable() {
        public void run() {
            tv_aea_speed_counter.setText("0.00");
        }
    };

    private double calculateCalories() {

        double dbl_weight = Double.parseDouble(userWeight);
        double MET = getMET();
        return ((MET * 4.0d) * dbl_weight * (Double.valueOf(TotalSeconds) / 3600.0d));
    }

    private double getMET() {
        switch (activityType) {
            case "outdoor_walking":
                return 3.5d;
            case "outdoor_running":
                return 11.0d;
            case "outdoor_stairs_climbing":
                return 8.0d;
            case "outdoor_cycling":
                return 6.0d;
            case "outdoor_hiking":
                return 8.0d;
            case "outdoor_swimming":
                return 6.0d;
            case "outdoor_aerobic":
                return 7.0d;
            case "indoor_running":
                return 10.5d;
            default:
                return 0.0d;
        }
    }

    //endregion

    //region prepareStepCounter()

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private boolean isSensorRegistered = false;
    private boolean isSensorPresent = false;
    private boolean isFirstStep = true;
    private float stepBalancer = 0;

    private void prepareStepCounter() {
        if (activityType.equalsIgnoreCase("indoor_running")
                || activityType.equalsIgnoreCase("outdoor_stairs_climbing")
                || activityType.equalsIgnoreCase("outdoor_aerobic")) {
            mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
            if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
                mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                isSensorPresent = true;
            } else {
                isSensorPresent = false;
            }
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d("onSensorChanged", String.valueOf(event.values[0]));

        if (isFirstStep) {
            stepBalancer = event.values[0];
            isFirstStep = false;
        }

        DecimalFormat df = new DecimalFormat("0.##");
        Date currentLocationTimestamp = new Date();

        steps = event.values[0] - stepBalancer;
//        calories = (steps / 20);
        calories = calculateCalories();

        double pace = (distance * 1000) / TotalSeconds; //((distance/1000) / TotalSeconds) * 3600;
        double mMinutes = pace / 60;
        double mSeconds = pace % 60;
        tv_aea_pace_counter.setText(String.format("%o\"%o\'", (int) mMinutes, (int) mSeconds));

        tv_aea_steps_counter.setText(String.valueOf((int) steps));


        tv_aea_calories_counter.setText(df.format((calories)));


        double tempDistanceKM = (steps * 0.762) / 1000;
        distance += tempDistanceKM;
        tv_aea_distance_counter.setText(df.format(distance));

        list.add(String.valueOf(calories));
        list.add(String.valueOf(steps));
        list.add(String.valueOf(distance));
        Utilities.filePath.setValue(list);



        double diff = distance - currentDistanceKM;
        if (diff > 0.5d) {
            currentDistanceKM = distance;

            updateNotification(String.format("%s km / %s calories burned", df.format(distance), df.format(calories)));
        }

        if (lastLocationTimestamp != null) {
            long diffInMs = currentLocationTimestamp.getTime() - lastLocationTimestamp.getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

            double speed = (distance / TotalSeconds) * 3600;
            tv_aea_speed_counter.setText(df.format(speed));

            handler.removeCallbacks(runnableSpeed);

            if (!isRunning) {
                handler.postDelayed(runnableSpeed, 2000);
            }
        }

        lastLocationTimestamp = new Date();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // TODO
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }
    }

    //endregion

    private void goPause() {
        isRunning = false;
        mService.removeLocationUpdates();
        handler.removeCallbacks(runnable);
        removeNotification();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);

        if (isSensorPresent) {
            mSensorManager.unregisterListener(this);
        }
        if (mFusedLocationClient != null && locationCallback != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void goSubmit() {
        String pathTimeline = "";
        for (LocationTs position : locationList) {
            double lat = position.getLocation().getLatitude();
            double lng = position.getLocation().getLongitude();
            Date timestamp = position.getTimestamp();

            DecimalFormat df = new DecimalFormat("0.########");

            pathTimeline += String.format("%s,%s,%s;", df.format(lat), df.format(lng), Utils.convertDatetimeSQL(timestamp));
        }

        ActivityObj activityObj = new ActivityObj();
        activityObj.setActivity_type(activityType);
        activityObj.setStart_datetime(Utils.convertDatetimeSQL(startDatetime));
        activityObj.setEnd_datetime(Utils.convertDatetimeSQL(endDatetime));
        activityObj.setPaths(pathTimeline);
        activityObj.setSteps(String.valueOf((int) steps));
        activityObj.setDistance(String.valueOf(distance));
        activityObj.setCalories(String.valueOf(calories));

        new Thread() {
            @Override
            public void run() {
                doRefreshToken();
                doSubmit(activityObj);
                doRefreshToken();
                doClearCache();
            }
        }.start();
    }

    public void doSubmit(ActivityObj activityObj) {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        String token = sharedPreferences.getString(Constants.SP_TOKEN, null);

        HttpHelper.TOKEN = token;
        JSONObject response = HttpHelper.postActivity(activityObj);

        if (response != null) {
            Log.d("doSubmit", response.toString());

            runOnUiThread(() -> {
                // TODO?
            });

            Intent intent = new Intent(this, FitnessDetailsActivity.class);
            intent.putExtra(Constants.BUNDLE_TEMP_JSON, response.toString());
            startActivity(intent);

            finish();
        }
    }

    private void discardActivity() {
        isRunning = false;
        mService.removeLocationUpdates();
        handler.removeCallbacks(runnable);
        removeNotification();
        doClearCache();

        finish();
    }

    private String CHANNEL_ID = "asia.fitmyapp.android.v1.activity.fitness.ExerciseActiveActivity";

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
            channel.setDescription(CHANNEL_ID);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void prepareNotification() {
        createNotificationChannel();

        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_fma_bg_transparent)
                .setContentTitle("Activity Active")
                .setContentText("0.00 km / 0.00 calories burned")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, builder.build());
    }

    private void prepareNotificationOnResume() {
        createNotificationChannel();

        String caloreis = Utilities.getString(ExerciseActiveActivity.this,"calorries");
        String dist = Utilities.getString(ExerciseActiveActivity.this,"kilometers");

        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_fma_bg_transparent)
                .setContentTitle("Activity Active")
                .setContentText(dist+" km"+ "/" +caloreis+" calories burned")
                .setOngoing(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(100, builder.build());
    }

    private void updateNotification(String contentText) {
        if (builder != null) {
            builder.setContentText(contentText);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(100, builder.build());
        }
    }

    private void removeNotification() {
        if (builder != null) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.cancel(100);
        }
    }

    private void doRefreshToken() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        final String SP_PHONE_NUM = sharedPreferences.getString(Constants.SP_PHONE_NUM, null);
        final String SP_APP_PASSWORD = sharedPreferences.getString(Constants.SP_APP_PASSWORD, null);

        JSONObject responseObj = HttpHelper.requestToken(SP_PHONE_NUM, SP_APP_PASSWORD);

        if (responseObj != null) {
            updateToken(responseObj);
            updateProfile(responseObj);
        }
    }

    private void updateToken(JSONObject jsonObject) {
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

    private void updateProfile(JSONObject jsonObject) {
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

    private void prepareParameters() {

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.SP_TEMP_COORDINATE).commit();

        userWeight = Utilities.getString(ExerciseActiveActivity.this, "wieght");
//        userWeight = Double.parseDouble(sharedPreferences.getString(Constants.SP_WEIGHT, "0.00"));

        boolean isActivityActive = sharedPreferences.getBoolean(Constants.SP_ACTIVITY_ACTIVE, false);
        if (!isActivityActive) {
            doClearCache();

//        if (true) {
            activityType = getIntent().getStringExtra(Constants.BUNDLE_ACTIVITY_TYPE);
//            start_lat = getIntent().getStringExtra("start_lat");
//            start_long = getIntent().getStringExtra("start_long");

            startDatetime = new Date();
            cachedLocations = "";
            StartTime = SystemClock.uptimeMillis();

            editor.putBoolean(Constants.SP_ACTIVITY_ACTIVE, true);

            editor.putString(Constants.SP_ACTIVITY_TYPE, activityType);
            editor.putString(Constants.SP_ACTIVITY_STARTDATE, Utils.convertDatetimeSQL(startDatetime));
            editor.putString(Constants.SP_ACTIVITY_LOCATIONS, cachedLocations);
            editor.putLong(Constants.SP_ACTIVITY_STARTTIME, StartTime);
            editor.putLong(Constants.SP_ACTIVITY_LASTRESUME, StartTime);
            editor.putFloat(Constants.SP_ACTIVITY_STEPS, stepBalancer);
            editor.commit();
        } else {
            activityType = sharedPreferences.getString(Constants.SP_ACTIVITY_TYPE, "");
            startDatetime = Utils.toDateNonT(sharedPreferences.getString(Constants.SP_ACTIVITY_STARTDATE, ""));
            cachedLocations = sharedPreferences.getString(Constants.SP_ACTIVITY_LOCATIONS, "");
            StartTime = sharedPreferences.getLong(Constants.SP_ACTIVITY_STARTTIME, SystemClock.uptimeMillis());
            long LastResume = sharedPreferences.getLong(Constants.SP_ACTIVITY_LASTRESUME, StartTime);
            stepBalancer = sharedPreferences.getFloat(Constants.SP_ACTIVITY_STEPS, 0f);
            pausedSeconds = sharedPreferences.getInt(Constants.SP_ACTIVITY_PAUSED_SECONDS, 0);

            long ms = SystemClock.uptimeMillis() - LastResume;
            long sec = (int) (ms / 1000);
            long min = sec / 60;
            long hour = min / 60;
            if (hour > 2) {

                discardActivity();

            }

            editor.putLong(Constants.SP_ACTIVITY_LASTRESUME, SystemClock.uptimeMillis());
            editor.commit();

            Log.d("cachedLocations", cachedLocations);
        }
    }

    private void doClearCache() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.SP_PACKAGE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(Constants.SP_ACTIVITY_ACTIVE);
        editor.remove(Constants.SP_ACTIVITY_TYPE);
        editor.remove(Constants.SP_ACTIVITY_STARTDATE);
        editor.remove(Constants.SP_ACTIVITY_LOCATIONS);
        editor.remove(Constants.SP_ACTIVITY_STEPS);
        editor.remove(Constants.SP_ACTIVITY_PAUSED_SECONDS);
        editor.commit();
    }

    @Override
    public void onBackPressed() {

        showemptyDialouge();
    }


    private void showemptyDialouge() {

        PrettyDialog pDialog = new PrettyDialog(this);

        pDialog
                .setTitle(getResources().getString(R.string.complete_actitvity))
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



}
