package com.yandex.appmetrica.applications;

import android.content.pm.PackageInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ParameterizedRobolectricTestRunner.class)
public class PackageNameExtractorParameterizedTest {

    private static final String YANDEX_BROWSER = "com.yandex.browser";
    private static final String GOOGLE_CHROME = "com.google.chrome";

    private final PackageNameExtractor mPackageNameExtractor = new PackageNameExtractor();

    @ParameterizedRobolectricTestRunner.Parameters
    public static List<Object[]> data() {
        PackageInfo yandexBrowser = new PackageInfo();
        PackageInfo googleChrome = new PackageInfo();
        yandexBrowser.packageName = YANDEX_BROWSER;
        googleChrome.packageName = GOOGLE_CHROME;

        return Arrays.asList(
                new Object[] {Collections.emptyList(), Collections.emptyList()},
                new Object[] {Collections.singletonList(yandexBrowser), Collections.singletonList(YANDEX_BROWSER)},
                new Object[] {Collections.singletonList(googleChrome), Collections.singletonList(GOOGLE_CHROME)},
                new Object[] {Arrays.asList(yandexBrowser, googleChrome), Arrays.asList(YANDEX_BROWSER, GOOGLE_CHROME)},
                new Object[] {Arrays.asList(googleChrome, yandexBrowser), Arrays.asList(GOOGLE_CHROME, YANDEX_BROWSER)}
        );
    }

    private final List<PackageInfo> mInput;
    private final List<String> mOutput;

    public PackageNameExtractorParameterizedTest(List<PackageInfo> input, List<String> output) {
        mInput = input;
        mOutput = output;
    }

    @Test
    public void filterPackages() {
        List<String> packageNames = mPackageNameExtractor.extractPackageNames(mInput);
        assertThat(mOutput, is(equalTo(packageNames)));
    }
}