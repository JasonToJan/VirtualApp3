/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.os.IBinder
 *  android.os.IInterface
 */
package mirror.android.bluetooth;

import android.os.IBinder;
import android.os.IInterface;
import com.lody.virtual.StringFog;
import mirror.MethodParams;
import mirror.RefClass;
import mirror.RefStaticMethod;

public class IBluetoothManager {
    public static Class<?> TYPE = RefClass.load(IBluetoothManager.class, StringFog.decrypt("EgsWBAoHO10BAwcVHQABBw1cPycCKhYXAB0EASIPHQQVExc="));

    public static class Stub {
        public static Class<?> TYPE = RefClass.load(Stub.class, StringFog.decrypt("EgsWBAoHO10BAwcVHQABBw1cPycCKhYXAB0EASIPHQQVExdKDAcWDQ=="));
        @MethodParams(value={IBinder.class})
        public static RefStaticMethod<IInterface> asInterface;
    }
}

