package com.yandex.appmetrica.applications;

import android.content.pm.PackageInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class PackageNameExtractor {

    List<String> extractPackageNames(List<PackageInfo> packages) {
        List<String> result = new ArrayList<>();
        for (PackageInfo packageInfo : packages) {
            result.add(packageInfo.packageName);
            Log.d(getClass().getSimpleName(), "extractPackageName: " + packageInfo.packageName);
        }

        return result;
    }
}
