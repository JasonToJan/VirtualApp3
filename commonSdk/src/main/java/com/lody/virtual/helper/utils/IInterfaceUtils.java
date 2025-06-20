/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IInterface
 */
package com.lody.virtual.helper.utils;

import android.os.IInterface;
import com.lody.virtual.client.core.VirtualCore;

public class IInterfaceUtils {
    public static boolean isAlive(IInterface binder) {
        if (binder == null) {
            return false;
        }
        if (VirtualCore.get().isMainProcess()) {
            return binder.asBinder().pingBinder();
        }
        return binder.asBinder().isBinderAlive();
    }
}

