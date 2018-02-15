package com.yandex.appmetrica.applications;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PackageFilterTest {

    private static final String YANDEX_BROWSER = "com.yandex.browser";
    private static final String ABACABA_BROWSER = "com.abacaba.browser";

    private final PackageFilter mPackageFilter = new PackageFilter();

    @Test
    public void filterPackages() {
        List<String> filteredPackages = mPackageFilter.filterPackages(Arrays.asList(YANDEX_BROWSER, ABACABA_BROWSER));
        assertThat(Collections.singletonList(YANDEX_BROWSER), is(equalTo(filteredPackages)));
    }
}