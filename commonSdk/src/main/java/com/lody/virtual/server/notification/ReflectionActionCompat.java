/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.widget.RemoteViews
 */
package com.lody.virtual.server.notification;

import android.widget.RemoteViews;
import com.lody.virtual.StringFog;

class ReflectionActionCompat {
    private static Class ReflectionActionClass;
    private static final String ReflectionAction;
    static final int TAG = 2;
    static final int BOOLEAN = 1;
    static final int BYTE = 2;
    static final int SHORT = 3;
    static final int INT = 4;
    static final int LONG = 5;
    static final int FLOAT = 6;
    static final int DOUBLE = 7;
    static final int CHAR = 8;
    static final int STRING = 9;
    static final int CHAR_SEQUENCE = 10;
    static final int URI = 11;
    static final int BITMAP = 12;
    static final int BUNDLE = 13;
    static final int INTENT = 14;
    static final int COLOR_STATE_LIST = 15;
    static final int ICON = 16;

    ReflectionActionCompat() {
    }

    static boolean isInstance(Object object) {
        return ReflectionActionClass != null && ReflectionActionClass.isInstance(object);
    }

    static {
        ReflectionAction = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uPmoFNClmHgY1KjsiP2UzLCVlN1RF"));
        try {
            ReflectionActionClass = Class.forName(RemoteViews.class.getName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PRhSVg==")) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij4uPmoFNClmHgY1KjsiP2UzLCVlN1RF")));
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
    }
}

