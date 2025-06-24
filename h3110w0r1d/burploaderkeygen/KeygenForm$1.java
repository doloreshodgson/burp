/*
 * Decompiled with CFR 0.152.
 */
package com.h3110w0r1d.burploaderkeygen;

import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

static final class KeygenForm.1
implements X509TrustManager {
    KeygenForm.1() {
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
    }
}
