/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.sec.clipboard;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IClipboardService {
    public static Class<?> TYPE = RefClass.load(IClipboardService.class, StringFog.decrypt("EgsWBAoHO10QChFeCgMHAwcdFxcKcTogAxsACwAPAQEhExcYNhAG"));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10QChFeCgMHAwcdFxcKcTogAxsACwAPAQEhExcYNhAGSyEEHA0="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

