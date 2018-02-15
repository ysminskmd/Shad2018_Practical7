package com.yandex.appmetrica.applications;

import android.support.v7.widget.RecyclerView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class PackageFragmentRobolectricTest {

    private final MainActivity mActivity = Robolectric.setupActivity(MainActivity.class);

    @Test
    public void loadButtonLoadsPackages() {
        mActivity.findViewById(R.id.load).performClick();
        RecyclerView list = mActivity.findViewById(R.id.list);
        assertThat(list.getAdapter().getItemCount(), is(greaterThan(0)));
    }

    @Test
    public void clearButtonClearsPackages() {
        mActivity.findViewById(R.id.load).performClick();
        mActivity.findViewById(R.id.clear).performClick();
        RecyclerView list = mActivity.findViewById(R.id.list);
        PackageRecyclerViewAdapter adapter = (PackageRecyclerViewAdapter) list.getAdapter();
        assertThat(adapter.getItemCount(), is(0));
    }

    @Test
    public void noPackagesByDefault() {
        RecyclerView list = mActivity.findViewById(R.id.list);
        PackageRecyclerViewAdapter adapter = (PackageRecyclerViewAdapter) list.getAdapter();
        assertThat(adapter.getItemCount(), is(0));
    }
}