package com.yandex.appmetrica.applications;

import java.util.ArrayList;
import java.util.List;

class PackageFilter {

    List<String> filterPackages(List<String> packageNames) {
        List<String> result = new ArrayList<>();
        for (String packageName : packageNames) {
            if (!packageName.contains("abacaba")) {
                result.add(packageName);
            }
        }

        return result;
    }
}
