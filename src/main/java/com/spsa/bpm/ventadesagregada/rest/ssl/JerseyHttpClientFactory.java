package com.spsa.bpm.ventadesagregada.rest.ssl;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import com.spsa.bpm.ventadesagregada.rest.ssl.NoOpHostnameVerifier;
import com.spsa.bpm.ventadesagregada.rest.ssl.NoOpTrustManager;

public class JerseyHttpClientFactory {
	public static Client getJerseyHTTPSClient() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContext sslContext = getSslContext();
        HostnameVerifier allHostsValid = new NoOpHostnameVerifier();

        return ClientBuilder.newBuilder()
                .sslContext(sslContext)
                .hostnameVerifier(allHostsValid)
                .build();
    }

    private static SSLContext getSslContext() throws NoSuchAlgorithmException,
                                                     KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLSv1.2");

        KeyManager[] keyManagers = null;
        TrustManager[] trustManager = {new NoOpTrustManager()};
        SecureRandom secureRandom = new SecureRandom();

        sslContext.init(keyManagers, trustManager, secureRandom);

        return sslContext;
    }

}
