package com.spsa.bpm.ventadesagregada.rest.ssl;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class NoOpTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

}
