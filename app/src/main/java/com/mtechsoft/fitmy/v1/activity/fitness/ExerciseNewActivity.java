package com.mtechsoft.fitmy.v1.activity.fitness;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

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

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.GPSTracker;
import com.mtechsoft.fitmy.v1.Utilities;
import com.mtechsoft.fitmy.v1.common.Constants;
import com.mtechsoft.fitmy.v1.dialogs.SelectSDKDialog;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class ExerciseNewActivity extends FragmentActivity implements OnMapReadyCallback {


    String start_lat = "",start_long = "",end_lat = "",end_long ="";

    public static boolean activity_started = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_new);

        prepareCheckup();
        prepareMap();
        prepareList();
        prepareNav();
    }

    //region prepareMap()

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private int locationRequestCode = 1000;
    private double wayLatitude = 0.0, wayLongitude = 0.0;
    private final int DEFAULT_ZOOM = 16;

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
                        setCurrentLocation(location);
                        mFusedLocationClient.removeLocationUpdates(locationCallback);
                    }
                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        } else {
            getLastLocation();
        }
    }

    private void showSelectSDKDialog() {
        DialogFragment newFragment = SelectSDKDialog.newInstance(
                R.string.search_word_meaning_dialog_title);
        newFragment.show(getSupportFragmentManager(), "dialog");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.community_sample));
            if (!success) {
                Log.e("onMapReady", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("onMapReady", "Can't find style. Error: ", e);
        }

        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.getUiSettings().setZoomGesturesEnabled(false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void getLastLocation() {
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                setCurrentLocation(location);
            }
        });
    }

    private void setCurrentLocation(Location location) {
        wayLatitude = location.getLatitude();
        wayLongitude = location.getLongitude();
        Log.d("setCurrentLocation", String.format("%s -- %s", wayLatitude, wayLongitude));

        mMap.clear();

        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_location_pin);
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 64, 64, false);
        BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

        LatLng currentLocation = new LatLng(wayLatitude, wayLongitude);
        MarkerOptions marker = new MarkerOptions().position(currentLocation);
        marker.icon(smallMarkerIcon);

        mMap.addMarker(marker);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, DEFAULT_ZOOM));
    }

    //endregion

    //region prepareList()

//    private String activityType = "outdoor_walking";
    private String activityType = "";

    private void prepareList() {
        ScrollView sv_aen_list = findViewById(R.id.sv_aen_list);
        ImageView iv_aen_list_active = findViewById(R.id.iv_aen_list_active);
        ImageView iv_aen_list_01 = findViewById(R.id.iv_aen_list_01);
        ImageView iv_aen_list_02 = findViewById(R.id.iv_aen_list_02);
        ImageView iv_aen_list_03 = findViewById(R.id.iv_aen_list_03);
        ImageView iv_aen_list_04 = findViewById(R.id.iv_aen_list_04);
        ImageView iv_aen_list_05 = findViewById(R.id.iv_aen_list_05);
        ImageView iv_aen_list_06 = findViewById(R.id.iv_aen_list_06);
        ImageView iv_aen_list_07 = findViewById(R.id.iv_aen_list_07);
        ImageView iv_aen_list_08 = findViewById(R.id.iv_aen_list_08);
        TextView tv_aen_list_01 = findViewById(R.id.tv_aen_list_01);
        TextView tv_aen_list_02 = findViewById(R.id.tv_aen_list_02);
        TextView tv_aen_list_03 = findViewById(R.id.tv_aen_list_03);
        TextView tv_aen_list_04 = findViewById(R.id.tv_aen_list_04);
        TextView tv_aen_list_05 = findViewById(R.id.tv_aen_list_05);
        TextView tv_aen_list_06 = findViewById(R.id.tv_aen_list_06);
        TextView tv_aen_list_07 = findViewById(R.id.tv_aen_list_07);
        TextView tv_aen_list_08 = findViewById(R.id.tv_aen_list_08);

        iv_aen_list_active.setOnClickListener(view -> {
//            showSelectSKAlert();
            if (sv_aen_list.getVisibility() == View.GONE) {
                sv_aen_list.setVisibility(View.VISIBLE);
            } else {
                sv_aen_list.setVisibility(View.GONE);
            }
        });

        iv_aen_list_01.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_walk));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_walking";
        });
        iv_aen_list_02.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_running));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_running";
        });
        iv_aen_list_03.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_climbing));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_stairs_climbing";
        });
        iv_aen_list_04.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_cycling));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_cycling";
        });
        iv_aen_list_05.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_hiking));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_hiking";
        });
        iv_aen_list_06.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_swimming));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_swimming";
        });
        iv_aen_list_07.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_aerobic));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_aerobic";
        });
        iv_aen_list_08.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_indoor_running));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "indoor_running";
        });

        tv_aen_list_01.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_walk));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_walking";
        });
        tv_aen_list_02.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_running));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_running";
        });
        tv_aen_list_03.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_climbing));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_stairs_climbing";
        });
        tv_aen_list_04.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_cycling));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_cycling";
        });
        tv_aen_list_05.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_hiking));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_hiking";
        });
        tv_aen_list_06.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_swimming));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_swimming";
        });
        tv_aen_list_07.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_aerobic));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "outdoor_aerobic";
        });
        tv_aen_list_08.setOnClickListener(view -> {
            iv_aen_list_active.setImageDrawable(getResources().getDrawable(R.drawable.ic_indoor_running));
            sv_aen_list.setVisibility(View.GONE);

            activityType = "indoor_running";
        });
    }

    //endregion

    private void prepareNav() {
        ImageView iv_aen_back = findViewById(R.id.iv_aen_back);
        iv_aen_back.setOnClickListener(view -> {
            finish();
        });

        Button b_aen_start = findViewById(R.id.b_aen_start);
        b_aen_start.setOnClickListener(view -> {

            activity_started = true;

            GPSTracker gps = new GPSTracker(this);

            // check if GPS enabled
            if (gps.canGetLocation()) {

                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                start_lat = String.valueOf(latitude);
                start_long = String.valueOf(longitude);

                Utilities.saveString(ExerciseNewActivity.this,"activity_type",activityType);
                Utilities.saveString(ExerciseNewActivity.this,"start_lat",start_lat);
                Utilities.saveString(ExerciseNewActivity.this,"start_long",start_long);


                // Do What you want to do
            }

            Utilities.saveString(ExerciseNewActivity.this,"activity_type",activityType);

            if (activityType.isEmpty()){

                showemptyDialouge();

            }else {

                Intent intent = new Intent(ExerciseNewActivity.this, ExerciseActiveActivity.class);
                intent.putExtra(Constants.BUNDLE_ACTIVITY_TYPE, activityType);
                intent.putExtra("Coming_from","exercise");
                startActivity(intent);

                finish();
            }
        });
    }

    private void showemptyDialouge() {


        PrettyDialog pDialog = new PrettyDialog(this);

        pDialog
                .setTitle(getResources().getString(R.string.activity_type))
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


    private final int MY_IGNORE_OPTIMIZATION_REQUEST = 1002;

    private void prepareCheckup() {
        // check internet

        // check power saving
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent();
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);

            boolean isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
            if (!isIgnoringBatteryOptimizations) {
                intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                intent.setData(Uri.parse("package:" + packageName));
                startActivityForResult(intent, MY_IGNORE_OPTIMIZATION_REQUEST);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_IGNORE_OPTIMIZATION_REQUEST) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
                boolean isIgnoringBatteryOptimizations = pm.isIgnoringBatteryOptimizations(getPackageName());
                if (isIgnoringBatteryOptimizations) {
                    // Ignoring battery optimization
                } else {
                    // Not ignoring battery optimization
                    Toast.makeText(this, "Not disabling battery optimization for FitMY will cause your device to sleep and stops FitMY activity tracking.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void showSelectSKAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Select")
                .setMessage("Plese connect a device to get accurate data")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showSelectSDKDialog();
//                        startActivity(new Intent(ExerciseNewActivity.this, SelectSDKActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
