package com.yandex.appmetrica.applications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.concurrent.CountDownLatch;

class LocationProvider implements LocationListener {

    private final LocationManager mLocationManager;
    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private volatile Location mLocation;

    @SuppressLint("MissingPermission")
    @SuppressWarnings("ConstantConditions")
    LocationProvider(Context context, final String provider) {
        mLocationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                mLocationManager.requestLocationUpdates(provider, 0, 0, LocationProvider.this);
            }
        }).run();
    }

    Location getLocation() throws InterruptedException {
        mCountDownLatch.await();

        return mLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        mLocation = location;
        mCountDownLatch.countDown();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
