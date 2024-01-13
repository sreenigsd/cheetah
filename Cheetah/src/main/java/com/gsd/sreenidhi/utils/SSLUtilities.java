package com.gsd.sreenidhi.utils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.security.SecureRandom;

/**
 * This class provides various static methods that relax X509 certificate and
 * hostname verification while using SSL over the HTTP protocol.
 */
public final class SSLUtilities {

    private static HostnameVerifier hostnameVerifier;
    private static TrustManager[] trustManagers;

    static {
        // Initialize hostname verifier and trust managers
        hostnameVerifier = new FakeHostnameVerifier();
        trustManagers = new TrustManager[]{new FakeX509TrustManager()};
    }

    /**
     * Trust all hostnames.
     */
    public static void trustAllHostnames() {
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }

    /**
     * Trust all HTTPS certificates.
     */
    public static void trustAllHttpsCertificates() {
        try {
            SSLContext context = SSLContext.getInstance("SSL");
            context.init(null, trustManagers, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize SSL context", e);
        }
    }

    /**
     * Fake hostname verifier that trusts any host name.
     */
    private static class FakeHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
            return true;
        }
    }

    /**
     * Trust manager that does not validate certificate chains.
     */
    private static class FakeX509TrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            // Trust without verification
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            // Trust without verification
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
}
