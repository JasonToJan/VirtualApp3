/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.drawable.Icon
 */
package mirror.android.graphics.drawable;

import android.annotation.TargetApi;
import mirror.RefClass;
import mirror.RefObject;

@TargetApi(value=23)
public class Icon {
    public static final int TYPE_BITMAP = 1;
    public static final int TYPE_RESOURCE = 2;
    public static final int TYPE_DATA = 3;
    public static final int TYPE_URI = 4;
    public static Class<?> TYPE = RefClass.load(Icon.class, android.graphics.drawable.Icon.class);
    public static RefObject<Object> mObj1;
    public static RefObject<String> mString1;
    public static RefObject<Integer> mType;
}

