/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.pm.PackageInstaller$SessionInfo
 *  android.graphics.Bitmap
 *  android.net.Uri
 */
package mirror.android.content.pm;


import android.graphics.Bitmap;
import android.net.Uri;
import com.lody.virtual.StringFog;
import mirror.RefBoolean;
import mirror.RefClass;
import mirror.RefConstructor;
import mirror.RefFloat;
import mirror.RefInt;
import mirror.RefLong;
import mirror.RefObject;

public class PackageInstaller {

    public static class SessionParamsMarshmallow {
        public static Class<?> TYPE = RefClass.load(SessionParamsMarshmallow.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXRUfWDUPPBgCCBc5BxwaEgkeExdKDBYQHBsfBz8PAQQfBQ=="));
        public static RefObject<String> abiOverride;
        public static RefObject<Bitmap> appIcon;
        public static RefLong appIconLastModified;
        public static RefObject<String> appLabel;
        public static RefObject<String> appPackageName;
        public static RefObject<String[]> grantedRuntimePermissions;
        public static RefInt installFlags;
        public static RefInt installLocation;
        public static RefInt mode;
        public static RefObject<Uri> originatingUri;
        public static RefObject<Uri> referrerUri;
        public static RefLong sizeBytes;
        public static RefObject<String> volumeUuid;
    }

    public static class SessionParamsLOLLIPOP {
        public static Class<?> TYPE = RefClass.load(SessionParamsLOLLIPOP.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXRUfWDUPPBgCCBc5BxwaEgkeExdKDBYQHBsfBz8PAQQfBQ=="));
        public static RefObject<String> abiOverride;
        public static RefObject<Bitmap> appIcon;
        public static RefLong appIconLastModified;
        public static RefObject<String> appLabel;
        public static RefObject<String> appPackageName;
        public static RefInt installFlags;
        public static RefInt installLocation;
        public static RefInt mode;
        public static RefObject<Uri> originatingUri;
        public static RefObject<Uri> referrerUri;
        public static RefLong sizeBytes;
    }

    public static class SessionInfo {
        public static Class<?> TYPE = RefClass.load(SessionInfo.class, StringFog.decrypt("EgsWBAoHO10AABwEDAEaXRUfWDUPPBgCCBc5BxwaEgkeExdKDBYQHBsfByYAFQo="));
        public static RefBoolean active;
        public static RefObject<Bitmap> appIcon;
        public static RefObject<CharSequence> appLabel;
        public static RefObject<String> appPackageName;
        public static RefConstructor<PackageInstaller.SessionInfo> ctor;
        public static RefObject<String> installerPackageName;
        public static RefInt mode;
        public static RefFloat progress;
        public static RefObject<String> resolvedBaseCodePath;
        public static RefBoolean sealed;
        public static RefInt sessionId;
        public static RefLong sizeBytes;
    }
}

