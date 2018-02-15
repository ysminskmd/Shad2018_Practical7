package com.yandex.appmetrica.applications;

import android.util.Pair;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

class UrlDownloader {

    Pair<Integer, String> downloadUrl(URL url) throws IOException {
        InputStream stream = null;
        HttpURLConnection connection = null;
        int responseCode;
        String result = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            responseCode = connection.getResponseCode();
            stream = connection.getInputStream();
            if (stream != null) {
                result = readStream(stream);
            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new Pair<>(responseCode, result);
    }

    private String readStream(InputStream stream) throws IOException {
        int maxReadSize = 500;
        Reader reader;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuilder builder = new StringBuilder();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            builder.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return builder.toString();
    }
}
