package com.yandex.appmetrica.applications;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class LocationProviderTest {

    private static final String PROVIDER = LocationManager.NETWORK_PROVIDER;

    static {
        Looper.prepare();
    }

    private final Context mContext = InstrumentationRegistry.getTargetContext();
    private final LocationManager mLocationManager =
            (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

    private final LocationProvider mLocationProvider = new LocationProvider(mContext, PROVIDER);

    @Test
    public void getLocation() throws InterruptedException {
        double latitude = 24;
        double longitude = 42;
        mockLocation(latitude, longitude);

        Location location = mLocationProvider.getLocation();

        assertThat(location, is(notNullValue()));
        assertThat(location.getLatitude(), is(equalTo(latitude)));
        assertThat(location.getLongitude(), is(equalTo(longitude)));
    }

    @Before
    public void prepareMockLocation() {
        mLocationManager.addTestProvider(PROVIDER, false, false, false, false, true, true, true, 0, 0);
        mLocationManager.setTestProviderEnabled(PROVIDER, true);
    }

    private void mockLocation(double latitude, double longitude) {
        Location location = new Location(PROVIDER);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        location.setAccuracy(0);
        location.setTime(System.currentTimeMillis());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            location.setElapsedRealtimeNanos(SystemClock.elapsedRealtimeNanos());
        }
        mLocationManager.setTestProviderLocation(PROVIDER, location);
    }

    @After
    public void resetMockLocation() {
        mLocationManager.removeTestProvider(PROVIDER);
    }
}