/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.client.env;

public class DeadServerException
extends RuntimeException {
    public DeadServerException() {
    }

    public DeadServerException(String message) {
        super(message);
    }

    public DeadServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DeadServerException(Throwable cause) {
        super(cause);
    }

    public DeadServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

