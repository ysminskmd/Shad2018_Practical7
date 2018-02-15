package com.yandex.appmetrica.applications;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class PackageProviderTest {

    @Mock private PackageManager mPackageManager;
    @Mock private PackageNameExtractor mPackageNameExtractor;
    @Mock private PackageFilter mPackageFilter;

    private PackageProvider mPackageProvider;

    @Mock private List<PackageInfo> mPackages;
    @Mock private List<String> mPackageNames;
    @Mock private List<String> mFilteredPackages;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPackageProvider = new PackageProvider(mPackageManager, mPackageNameExtractor, mPackageFilter);
    }

    @Test
    public void getInstalledPackageNames() {
        when(mPackageManager.getInstalledPackages(0)).thenReturn(mPackages);
        when(mPackageNameExtractor.extractPackageNames(mPackages)).thenReturn(mPackageNames);
        when(mPackageFilter.filterPackages(mPackageNames)).thenReturn(mFilteredPackages);

        assertThat(mPackageProvider.getInstalledPackageNames(), is(equalTo(mFilteredPackages)));
    }
}