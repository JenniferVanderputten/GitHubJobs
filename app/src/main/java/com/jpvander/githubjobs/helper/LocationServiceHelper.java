package com.jpvander.githubjobs.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationServiceHelper {

    private static final int LOCATION_ACCESS_REQUEST = 111;
    private static final String LOG_LABEL = "Jobs";
    private static final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};

    private boolean shouldShowRationale;
    private final int accessPerm;
    private final Geocoder geocoder;

    public LocationServiceHelper(Activity activity) {
        accessPerm = ContextCompat.checkSelfPermission(activity, permissions[0]);
        shouldShowRationale = false;
        geocoder = new Geocoder(activity, Locale.getDefault());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            shouldShowRationale = activity.shouldShowRequestPermissionRationale(permissions[0]);
        }
    }

    public boolean permissionWasRequested() {
        return shouldShowRationale;
    }

    public boolean hasPermission() {
        return (accessPerm == PackageManager.PERMISSION_GRANTED);
    }

    public boolean permissionGranted(int requestCode, int[] results) {
        boolean granted = false;

        switch (requestCode) {
            case LOCATION_ACCESS_REQUEST: {
                if (0 < results.length && results[0] == PackageManager.PERMISSION_GRANTED) {
                    granted = true;
                }
                break;
            }
        }

        return granted;
    }

    public Location getLocation(Activity activity) {
        LocationManager locationManager = (LocationManager) activity
                .getSystemService(Context.LOCATION_SERVICE);
        Location location = null;

        // Collapsing these causes a compile error; Lint is incorrect here.
        //noinspection TryWithIdenticalCatches
        try {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (null == location) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
        catch (SecurityException exception) {
            Log.e(LOG_LABEL, exception.getMessage(), exception);
        }
        catch (Exception exception) {
            Log.e(LOG_LABEL, exception.getMessage(), exception);
        }

        return location;
    }

    public String getLocality(Location location) {
        String locality = "";

        try {
            if (null != location) {
                List<Address> addresses = geocoder.getFromLocation(
                        location.getLatitude(), location.getLongitude(), 1);

                if (null != addresses && 0 < addresses.size()) {
                    Address address = addresses.get(0);
                    locality = address.getLocality();
                }
            }
        }
        catch (IOException exception) {
            Log.e(LOG_LABEL, exception.getMessage(), exception);
        }

        return locality;
    }

    public void requestPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRationale) {
            activity.requestPermissions(permissions, LOCATION_ACCESS_REQUEST);
        }
    }
}
