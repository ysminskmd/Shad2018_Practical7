package com.yandex.appmetrica.applications;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.LinearLayout;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class PackageFragmentUiAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;

    private static final String LOAD_BUTTON_TEXT = "LOAD PACKAGES";
    private static final String CLEAR_BUTTON_TEXT = "CLEAR PACKAGES";

    private final String mPackageName = InstrumentationRegistry.getTargetContext().getPackageName();

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        String launcherPackage = mDevice.getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        Context context = InstrumentationRegistry.getContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(mPackageName);
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(mPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void loadButtonLoadsCurrentPackage() throws Exception {
        mDevice.findObject(new UiSelector().text(LOAD_BUTTON_TEXT).className(Button.class.getName())).click();
        UiScrollable list = new UiScrollable(new UiSelector().className(RecyclerView.class.getName()));
        UiObject chrome = list.getChildByText(new UiSelector().className(LinearLayout.class.getName()), mPackageName);
        assertThat(chrome.exists(), is(true));
    }

    @Ignore
    @Test
    public void clearButtonClearsPackages() throws Exception {
        mDevice.findObject(new UiSelector().text(LOAD_BUTTON_TEXT).className(Button.class.getName())).click();
        mDevice.findObject(new UiSelector().text(CLEAR_BUTTON_TEXT).className(Button.class.getName())).click();
        UiScrollable list = new UiScrollable(new UiSelector().className(RecyclerView.class.getName()));
        assertThat(list.getChildCount(), is(0));
    }

    @Test
    public void noPackagesByDefault() throws Exception {
        UiScrollable list = new UiScrollable(new UiSelector().className(RecyclerView.class.getName()));
        assertThat(list.getChildCount(), is(0));
    }
}
