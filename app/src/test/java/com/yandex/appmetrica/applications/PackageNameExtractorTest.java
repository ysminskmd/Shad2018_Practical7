package com.yandex.appmetrica.applications;

import android.content.pm.PackageInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class PackageNameExtractorTest {

    private static final String YANDEX_BROWSER = "com.yandex.browser";
    private static final String GOOGLE_CHROME = "com.google.chrome";

    private final PackageNameExtractor mPackageNameExtractor = new PackageNameExtractor();

    private final PackageInfo mYandexBrowser = new PackageInfo();
    private final PackageInfo mGoogleChrome = new PackageInfo();
    {
        mYandexBrowser.packageName = YANDEX_BROWSER;
        mGoogleChrome.packageName = GOOGLE_CHROME;
    }

    @Test
    public void filterPackages() {
        List<String> packageNames = mPackageNameExtractor.extractPackageNames(Arrays.asList(mYandexBrowser, mGoogleChrome));
        assertThat(Arrays.asList(YANDEX_BROWSER, GOOGLE_CHROME), is(equalTo(packageNames)));
    }
}