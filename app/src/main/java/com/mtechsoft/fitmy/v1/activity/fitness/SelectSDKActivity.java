package com.mtechsoft.fitmy.v1.activity.fitness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.garmin.android.connectiq.ConnectIQ;
import com.garmin.android.connectiq.IQDevice;
import com.garmin.android.connectiq.exception.InvalidStateException;
import com.garmin.android.connectiq.exception.ServiceUnavailableException;
import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.mtechsoft.fitmy.R;
import com.mtechsoft.fitmy.v1.adapter.IQDeviceAdapter;
import com.mtechsoft.fitmy.v1.service.StepCountReporter;

public class SelectSDKActivity extends AppCompatActivity {
    private ConnectIQ mConnectIQ;
    private IQDeviceAdapter mAdapter;
    private boolean mSdkReady = false;
    private TextView mEmptyView;
    TextView tv_af_steps_counter_2;
    private HealthDataStore mStore;
    private ConnectIQ.IQDeviceEventListener mDeviceEventListener = new ConnectIQ.IQDeviceEventListener() {

        @Override
        public void onDeviceStatusChanged(IQDevice device, IQDevice.IQDeviceStatus status) {
            mAdapter.updateDeviceStatus(device, status);
        }

    };
    public static final String APP_TAG = "SimpleHealth";
    private StepCountReporter mReporter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_sdk);
        Switch samsungSwitch = findViewById(R.id.sFitBit);
        Switch germinSwitch = findViewById(R.id.sGermin);
        tv_af_steps_counter_2 = findViewById(R.id.tv_af_steps_counter_2);
        requestPermission();
        // Set a checked change listener for switch button
        samsungSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mStore = new HealthDataStore(SelectSDKActivity.this, mConnectionListener);
                    // Request the connection to the health data store
                    mStore.connectService();

                    germinSwitch.setClickable(false);



                } else {
                    // If the switch button is off
                    germinSwitch.setClickable(true);
                    // Show the switch button checked status as toast message
                }
            }
        });


        germinSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    mConnectIQ = ConnectIQ.getInstance(SelectSDKActivity.this, ConnectIQ.IQConnectType.WIRELESS);

                    // Initialize the SDK
                    mConnectIQ.initialize(SelectSDKActivity.this, true, mListener);
                    samsungSwitch.setClickable(false);



                } else {
                    // If the switch button is off
                    samsungSwitch.setClickable(true);
                    // Show the switch button checked status as toast message
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();

//        prepareBMI();
        prepareSteps();
    }

    private void prepareSteps() {
    }

    public void loadDevices() {
        // Retrieve the list of known devices
        try {
            List<IQDevice> devices = mConnectIQ.getKnownDevices();

            if (devices.size() > 0) {
//                mAdapter.setDevices(devices);
                Toast.makeText(this, "Device Founded!", Toast.LENGTH_SHORT).show();
                // Let's register for device status updates.  By doing so we will
                // automatically get a status update for each device so we do not
                // need to call getStatus()
                for (IQDevice device : devices) {
                    mConnectIQ.registerForDeviceEvents(device, mDeviceEventListener);
                }
            } else {
                Toast.makeText(this, "Ops.. Device Not Found", Toast.LENGTH_SHORT).show();
            }

        } catch (InvalidStateException e) {
            // This generally means you forgot to call initialize(), but since
            // we are in the callback for initialize(), this should never happen
        } catch (ServiceUnavailableException e) {
            // This will happen if for some reason your app was not able to connect
            // to the ConnectIQ service running within Garmin Connect Mobile.  This
            // could be because Garmin Connect Mobile is not installed or needs to
            // be upgraded.
            if (null != mEmptyView)
                mEmptyView.setText(R.string.service_unavailable);
        }
    }

    private ConnectIQ.ConnectIQListener mListener = new ConnectIQ.ConnectIQListener() {

        @Override
        public void onInitializeError(ConnectIQ.IQSdkErrorStatus errStatus) {
            if (null != mEmptyView)
                mEmptyView.setText(R.string.initialization_error + errStatus.name());
            mSdkReady = false;
        }

        @Override
        public void onSdkReady() {
            loadDevices();
            mSdkReady = true;
        }


        @Override
        public void onSdkShutDown() {
            mSdkReady = false;
        }

    };
    private final HealthDataStore.ConnectionListener mConnectionListener = new HealthDataStore.ConnectionListener() {

        @Override
        public void onConnected() {
            Log.d(APP_TAG, "Health data service is connected.");
            mReporter = new StepCountReporter(mStore);
            if (isPermissionAcquired()) {
                mReporter.start(mStepCountObserver);
            } else {
                requestPermission();
            }
        }

        @Override
        public void onConnectionFailed(HealthConnectionErrorResult error) {
            Log.d(APP_TAG, "Health data service is not available.");
            showConnectionFailureDialog(error);
        }

        @Override
        public void onDisconnected() {
            Log.d(APP_TAG, "Health data service is disconnected.");
            if (!isFinishing()) {
                mStore.connectService();
            }
        }
    };
    private void showConnectionFailureDialog(final HealthConnectionErrorResult error) {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        if (error.hasResolution()) {
            switch (error.getErrorCode()) {
                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                    alert.setMessage(R.string.msg_req_install);
                    break;
                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                    alert.setMessage(R.string.msg_req_upgrade);
                    break;
                case HealthConnectionErrorResult.PLATFORM_DISABLED:
                    alert.setMessage(R.string.msg_req_enable);
                    break;
                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                    alert.setMessage(R.string.msg_req_agree);
                    break;
                default:
                    alert.setMessage(R.string.msg_req_available);
                    break;
            }
        } else {
            alert.setMessage(R.string.msg_conn_not_available);
        }

        alert.setPositiveButton(R.string.ok, (dialog, id) -> {
            if (error.hasResolution()) {
                error.resolve(SelectSDKActivity.this);
            }
        });

        if (error.hasResolution()) {
            alert.setNegativeButton(R.string.cancel, null);
        }

        alert.show();
    }
    private boolean isPermissionAcquired() {
        HealthPermissionManager.PermissionKey permKey = new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ);
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Check whether the permissions that this application needs are acquired
            Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(Collections.singleton(permKey));
            return resultMap.get(permKey);
        } catch (Exception e) {
            Log.e(APP_TAG, "Permission request fails.", e);
        }
        return false;
    }
    private void showPermissionAlarmDialog() {
        if (isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(SelectSDKActivity.this);
        alert.setTitle(R.string.notice)
                .setMessage(R.string.msg_perm_acquired)
                .setPositiveButton(R.string.ok, null)
                .show();
    }
    private void requestPermission() {
        HealthPermissionManager.PermissionKey permKey = new HealthPermissionManager.PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, HealthPermissionManager.PermissionType.READ);
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Show user permission UI for allowing user to change options
            pmsManager.requestPermissions(Collections.singleton(permKey), SelectSDKActivity.this)
                    .setResultListener(result -> {
                        Log.d(APP_TAG, "Permission callback is received.");
                        Map<HealthPermissionManager.PermissionKey, Boolean> resultMap = result.getResultMap();

                        if (resultMap.containsValue(Boolean.FALSE)) {
                            updateStepCountView("");
                            showPermissionAlarmDialog();
                        } else {
                            // Get the current step count and display it
                            mReporter.start(mStepCountObserver);
                        }
                    });
        } catch (Exception e) {
            Log.e(APP_TAG, "Permission setting fails.", e);
        }
    }

    private StepCountReporter.StepCountObserver mStepCountObserver = count -> {
        Log.d(APP_TAG, "Step reported : " + count);
        updateStepCountView(String.valueOf(count));
    };
    private void updateStepCountView(final String count) {
//        runOnUiThread(() -> mStepCountTv.setText(count));
        tv_af_steps_counter_2.setText(count);
        Toast.makeText(this, count, Toast.LENGTH_SHORT).show();
    }
}
