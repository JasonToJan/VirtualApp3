/*
 * Decompiled with CFR 0.152.
 */
package com.kook.network.https;

import com.kook.network.StringFog;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpsUtils {
    public static SSLSocketFactory getSslSocketFactory(InputStream[] certificates, InputStream bksFile, String password) {
        try {
            TrustManager[] trustManagers = HttpsUtils.prepareTrustManager(certificates);
            KeyManager[] keyManagers = HttpsUtils.prepareKeyManager(bksFile, password);
            SSLContext sslContext = SSLContext.getInstance(StringFog.decrypt("ODwj"));
            sslContext.init(keyManagers, new TrustManager[]{new MyTrustManager(HttpsUtils.chooseTrustManager(trustManagers))}, new SecureRandom());
            return sslContext.getSocketFactory();
        }
        catch (NoSuchAlgorithmException e) {
            throw new AssertionError((Object)e);
        }
        catch (KeyManagementException e) {
            throw new AssertionError((Object)e);
        }
        catch (KeyStoreException e) {
            throw new AssertionError((Object)e);
        }
    }

    private static TrustManager[] prepareTrustManager(InputStream ... certificates) {
        if (certificates == null || certificates.length <= 0) {
            return null;
        }
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(StringFog.decrypt("M0FaWxQ="));
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate == null) continue;
                    certificate.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            TrustManagerFactory trustManagerFactory = null;
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            return trustManagers;
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (CertificateException e) {
            e.printStackTrace();
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) {
                return null;
            }
            KeyStore clientKeyStore = KeyStore.getInstance(StringFog.decrypt("KSQ8"));
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        }
        catch (KeyStoreException e) {
            e.printStackTrace();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        catch (CertificateException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (!(trustManager instanceof X509TrustManager)) continue;
            return (X509TrustManager)trustManager;
        }
        return null;
    }

    public static SSLSocketFactory initSSLSocketFactory() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance(StringFog.decrypt("ODwj"));
            TrustManager[] xTrustArray = new X509TrustManager[]{HttpsUtils.initTrustManager()};
            sslContext.init(null, xTrustArray, new SecureRandom());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    public static X509TrustManager initTrustManager() {
        X509TrustManager mTrustManager = new X509TrustManager(){

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        return mTrustManager;
    }

    private static class MyTrustManager
    implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore)null);
            this.defaultTrustManager = HttpsUtils.chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                this.defaultTrustManager.checkServerTrusted(chain, authType);
            }
            catch (CertificateException ce) {
                this.localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}

