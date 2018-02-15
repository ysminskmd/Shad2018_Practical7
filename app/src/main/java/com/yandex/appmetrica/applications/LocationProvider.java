package com.yandex.appmetrica.applications;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

class LocationProvider implements LocationListener {

    private final LocationManager mLocationManager;
    private final CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private volatile Location mLocation;

    @SuppressLint("MissingPermission")
    @SuppressWarnings("ConstantConditions")
    LocationProvider(Context context, final String provider) {
        mLocationManager = (LocationManager)
                context.getSystemService(Context.LOCATION_SERVICE);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                mLocationManager.requestLocationUpdates(provider, 0, 0, LocationProvider.this);
            }
        });
    }

    Location getLocation() throws InterruptedException {
        mCountDownLatch.await(5, TimeUnit.SECONDS);

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
