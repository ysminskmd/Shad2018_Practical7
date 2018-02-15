package com.yandex.appmetrica.applications;

import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

public class UrlDownloaderTest {

    private final UrlDownloader mUrlDownloader = new UrlDownloader();
    private final MockWebServer mServer = new MockWebServer();

    @Mock private HttpURLConnection mUrlConnection;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void downloadUrl() throws Exception {
        int code = HttpURLConnection.HTTP_SEE_OTHER;
        String body = "expected body";
        mServer.enqueue(new MockResponse().setResponseCode(code).setBody(body));
        mServer.start();

        Pair<Integer, String> result = mUrlDownloader.downloadUrl(mServer.url("/some/path").url());

        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
            @Override
            public URLStreamHandler createURLStreamHandler(String protocol) {
                return new URLStreamHandler() {
                    @Override
                    protected URLConnection openConnection(URL u) throws IOException {
                        throw new UnsupportedOperationException("implement me");
                    }
                };
            }
        });

        assertThat(result.first, is(equalTo(code)));
        assertThat(result.second, is(equalTo(body)));

        RecordedRequest request = mServer.takeRequest();
        assertThat(request.getMethod(), is(equalTo("GET")));
        assertThat(request.getPath(), is(equalTo("/some/path")));

        mServer.shutdown();
    }

    @Test
    public void useUrlStreamHandlerFactory() throws IOException {
        URL.setURLStreamHandlerFactory(new URLStreamHandlerFactory() {
            @Override
            public URLStreamHandler createURLStreamHandler(String protocol) {
                return new URLStreamHandler() {
                    @Override
                    protected URLConnection openConnection(URL u) throws IOException {
                        return mUrlConnection;
                    }
                };
            }
        });

        mUrlDownloader.downloadUrl(new URL("http://www.yandex.ru"));

        verify(mUrlConnection).setReadTimeout(3000);
        verify(mUrlConnection).setConnectTimeout(3000);
        verify(mUrlConnection).setRequestMethod("GET");
        verify(mUrlConnection).setDoInput(true);
        verify(mUrlConnection).connect();
    }
}