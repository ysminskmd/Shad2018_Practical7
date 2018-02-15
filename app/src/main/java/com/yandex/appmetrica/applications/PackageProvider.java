package com.yandex.appmetrica.applications;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.VisibleForTesting;

import java.util.List;

class PackageProvider {

    private final PackageManager mPackageManager;
    private final PackageNameExtractor mPackageNameExtractor;
    private final PackageFilter mPackageFilter;

    PackageProvider(Context context) {
        this(context.getPackageManager(), new PackageNameExtractor(), new PackageFilter());
    }

    @VisibleForTesting
    PackageProvider(PackageManager packageManager, PackageNameExtractor packageNameExtractor, PackageFilter packageFilter) {
        mPackageManager = packageManager;
        mPackageNameExtractor = packageNameExtractor;
        mPackageFilter = packageFilter;
    }

    List<String> getInstalledPackageNames() {
        final List<PackageInfo> packages = mPackageManager.getInstalledPackages(0);
        final List<String> packageNames = mPackageNameExtractor.extractPackageNames(packages);
        return mPackageFilter.filterPackages(packageNames);
    }
}
