package com.example.assignment1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.text.DecimalFormat;

public class Locator extends AppCompatActivity implements LocationListener {
    private static final String DistanceValue = "DISTANCE_VAL";
    private static final String LatitudeValue = "LATITUDE_VAL";
    private static final String LongitudeValue = "LONGITUDE_VAL";
    private LocationManager locationManager;
    private LocationRequest locationRequest;
    private double previousLatitude = 0.0d;
    private double previousLongitude = 0.0d;
    TextView currentLatitudeValue;
    TextView currentLongitudeValue;
    TextView distanceValue;
    double distance;
    private static final DecimalFormat decimalFormatter = new DecimalFormat("#.#####");
    private static final DecimalFormat distanceDecimalFormatter = new DecimalFormat("#.##");

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locator);

        currentLatitudeValue = (TextView) findViewById(R.id.latitudeValue);
        currentLongitudeValue = (TextView) findViewById(R.id.longitudeValue);
        distanceValue = (TextView) findViewById(R.id.distanceValue);

        if (savedInstanceState != null) {
            currentLatitudeValue.setText(savedInstanceState.getString(LatitudeValue));
            currentLongitudeValue.setText(savedInstanceState.getString(LongitudeValue));
            distance = Float.parseFloat(savedInstanceState.getString(DistanceValue));
            distanceValue.setText(distanceDecimalFormatter.format(distance)+" meters");
        }

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_DENIED) {
            requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION);

        }

        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            getLatitudeLongitude();
        }
        Button resetBtn = (Button) findViewById(R.id.reset);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                distance = 0;
                distanceValue.setText(distance + " miles");
            }
        });

    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted.
                    Toast toast = Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT);
                    toast.show();
                    getLatitudeLongitude();
                } else {
                    //Permission is denied.
                    Toast toast = Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(Locator.this, MainActivity.class);
                    startActivity(intent);
                }
            });


    private void getLatitudeLongitude() {
        if (!isGPSEnabled()) {
            turnOnGPS();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 20, this);

        LocationServices.getFusedLocationProviderClient(Locator.this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);

                        LocationServices.getFusedLocationProviderClient(Locator.this)
                                .removeLocationUpdates(this);

                        if (locationResult.getLocations().size() > 0) {

                            int index = locationResult.getLocations().size() - 1;
                            double latitude = locationResult.getLocations().get(index).getLatitude();
                            double longitude = locationResult.getLocations().get(index).getLongitude();
                        }
                    }
                }, Looper.getMainLooper());
    }

    private boolean isGPSEnabled() {
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }

    private void turnOnGPS() {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(Locator.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(Locator.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {
        if (previousLatitude != location.getLatitude() && previousLongitude != location.getLongitude()) {
            Location currentLocation = new Location("Point A");
            currentLocation.setLatitude(location.getLatitude());
            currentLocation.setLongitude(location.getLongitude());

            Location previousLocation = new Location("Point B");
            previousLocation.setLatitude(previousLatitude);
            previousLocation.setLongitude(previousLongitude);

            if (previousLocation.getLatitude() != 0.0 && previousLocation.getLongitude() != 0.0) {
                distance += currentLocation.distanceTo(previousLocation);
            }

            currentLatitudeValue.setText(decimalFormatter.format(location.getLatitude()));
            currentLongitudeValue.setText(decimalFormatter.format(location.getLongitude()));

            previousLatitude = location.getLatitude();
            previousLongitude = location.getLongitude();

            distanceValue.setText(distanceDecimalFormatter.format(distance) + " meters");
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Locator.this);
        builder.setTitle("Quit");
        builder.setMessage("Are you sure you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (currentLatitudeValue != null && currentLatitudeValue.getText() != null)
            outState.putString(LatitudeValue, currentLatitudeValue.getText().toString());

        if (currentLongitudeValue != null && currentLongitudeValue.getText() != null)
            outState.putString(LongitudeValue, currentLongitudeValue.getText().toString());

        outState.putString(DistanceValue, distanceDecimalFormatter.format(distance));
    }
}