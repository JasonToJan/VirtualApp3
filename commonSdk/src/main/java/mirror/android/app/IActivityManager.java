/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.pm.ProviderInfo
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.app;

import android.content.pm.ProviderInfo;
import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefMethod;
import mirror.RefObject;

public class IActivityManager {
    public static Class<?> TYPE = RefClass.load(IActivityManager.class, StringFog.decrypt("EgsWBAoHO10CHwJeIC4NBwwEHxEXEhINDhUVGw=="));
    @MethodParams(value={IBinder.class, boolean.class})
    public static RefMethod<Integer> getTaskForActivity;
    @MethodParams(value={IBinder.class, int.class})
    public static RefMethod<Void> setRequestedOrientation;
    @MethodParams(value={IBinder.class, String.class, int.class, int.class})
    public static RefMethod<Void> overridePendingTransition;
    public static RefMethod<Integer> startActivity;
    public static RefMethod<Integer> startActivities;

    public static class ContentProviderHolderMIUI {
        public static Class<?> TYPE = RefClass.load(ContentProviderHolder.class, StringFog.decrypt("EgsWBAoHO10CHwJeIC4NBwwEHxEXEhINDhUVG0stHAsGEwsaDwEMGRsUDB0mHAkWExc="));
        public static RefObject<ProviderInfo> info;
        public static RefObject<IInterface> provider;
        public static RefBoolean noReleaseNeeded;
        public static RefBoolean waitProcessStart;
    }

    public static class ContentProviderHolder {
        public static Class<?> TYPE = RefClass.load(ContentProviderHolder.class, StringFog.decrypt("EgsWBAoHO10CHwJeIC4NBwwEHxEXEhINDhUVG0stHAsGEwsaDwEMGRsUDB0mHAkWExc="));
        public static RefObject<ProviderInfo> info;
        public static RefObject<IInterface> provider;
        public static RefBoolean noReleaseNeeded;
    }
}

