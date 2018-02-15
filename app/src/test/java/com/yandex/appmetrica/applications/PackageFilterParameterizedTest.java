package com.yandex.appmetrica.applications;

import android.content.pm.PackageInfo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class PackageFilterParameterizedTest {

    private final PackageFilter mPackageFilter = new PackageFilter();

    @Parameterized.Parameters
    public static List<List[]> data() {
        String yandexBrowser = "com.yandex.browser";
        String abacabaBrowser = "com.abacaba.browser";

        return Arrays.asList(
                new List[] {Collections.emptyList(), Collections.emptyList()},
                new List[] {Collections.singletonList(abacabaBrowser), Collections.emptyList()},
                new List[] {Collections.singletonList(yandexBrowser), Collections.singletonList(yandexBrowser)},
                new List[] {Arrays.asList(abacabaBrowser, yandexBrowser), Collections.singletonList(yandexBrowser)},
                new List[] {Arrays.asList(yandexBrowser, abacabaBrowser), Collections.singletonList(yandexBrowser)}
        );
    }

    private final List<String> mInput;
    private final List<String> mOutput;

    public PackageFilterParameterizedTest(List<String> input, List<String> output) {
        mInput = input;
        mOutput = output;
    }

    @Test
    public void filterPackages() {
        List<String> filteredPackages = mPackageFilter.filterPackages(mInput);
        assertThat(mOutput, is(equalTo(filteredPackages)));
    }
}