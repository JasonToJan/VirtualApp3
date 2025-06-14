/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.ComponentName
 *  android.content.IntentFilter
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.ConfigurationInfo
 *  android.content.pm.FeatureInfo
 *  android.content.pm.InstrumentationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PermissionGroupInfo
 *  android.content.pm.PermissionInfo
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ServiceInfo
 *  android.content.pm.Signature
 *  android.os.Bundle
 *  android.util.SparseArray
 */
package android.content.pm;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ConfigurationInfo;
import android.content.pm.FeatureInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.SparseArray;
import com.lody.virtual.StringFog;
import java.io.File;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;

public class PackageParser {
    public static final int PARSE_IS_SYSTEM = 1;
    public static final int PARSE_IS_SYSTEM_DIR = 16;
    public static final int PARSE_COLLECT_CERTIFICATES = 32;
    public static final int PARSE_ENFORCE_CODE = 64;

    public static ApkLite parseApkLite(File apkFile, int flags) throws PackageParserException {
        throw new RuntimeException(StringFog.decrypt("IBEHFEQ="));
    }

    @TargetApi(value=29)
    public void setCallback(Callback cb) {
        throw new RuntimeException(StringFog.decrypt("IBEHFEQ="));
    }

    private static abstract class SplitDependencyLoader<E extends Exception> {
        private SplitDependencyLoader() {
        }

        private static int[] append(int[] src, int elem) {
            if (src == null) {
                return new int[]{elem};
            }
            int[] dst = Arrays.copyOf(src, src.length + 1);
            dst[src.length] = elem;
            return dst;
        }

        public static SparseArray<int[]> createDependenciesFromPackage(PackageLite pkg) throws IllegalDependencyException {
            int splitIdx;
            SparseArray splitDependencies = new SparseArray();
            splitDependencies.put(0, (Object)new int[]{-1});
            for (splitIdx = 0; splitIdx < pkg.splitNames.length; ++splitIdx) {
                int targetIdx;
                if (!pkg.isFeatureSplits[splitIdx]) continue;
                String splitDependency = pkg.usesSplitNames[splitIdx];
                if (splitDependency != null) {
                    int depIdx = Arrays.binarySearch(pkg.splitNames, splitDependency);
                    if (depIdx < 0) {
                        throw new IllegalDependencyException(StringFog.decrypt("IBUeHxFOeA==") + pkg.splitNames[splitIdx] + StringFog.decrypt("VEUAExQbNgEGHFIDGQMHB0VV") + splitDependency + StringFog.decrypt("VElSAQ0HPBtDBgFQBAYdAAwcEUs="));
                    }
                    targetIdx = depIdx + 1;
                } else {
                    targetIdx = 0;
                }
                splitDependencies.put(splitIdx + 1, (Object)new int[]{targetIdx});
            }
            int size = pkg.splitNames.length;
            for (splitIdx = 0; splitIdx < size; ++splitIdx) {
                int targetSplitIdx;
                if (pkg.isFeatureSplits[splitIdx]) continue;
                String configForSplit = pkg.configForSplit[splitIdx];
                if (configForSplit != null) {
                    int depIdx = Arrays.binarySearch(pkg.splitNames, configForSplit);
                    if (depIdx < 0) {
                        throw new IllegalDependencyException(StringFog.decrypt("IBUeHxFOeA==") + pkg.splitNames[splitIdx] + StringFog.decrypt("VEUGFxcJOgcQTwEABQYaU0I=") + configForSplit + StringFog.decrypt("VElSAQ0HPBtDBgFQBAYdAAwcEUs="));
                    }
                    if (!pkg.isFeatureSplits[depIdx]) {
                        throw new IllegalDependencyException(StringFog.decrypt("IBUeHxFOeA==") + pkg.splitNames[splitIdx] + StringFog.decrypt("VEUWEwYCPgEGHFIZHRwLHwNSFxZOPBwNCRsXHB0PBwwdGEUdLx8KG1IWBh1OEkUcGQtDORYCGwcCDE8dAwkbAkVJ") + pkg.splitNames[depIdx] + StringFog.decrypt("VA=="));
                    }
                    targetSplitIdx = depIdx + 1;
                } else {
                    targetSplitIdx = 0;
                }
                splitDependencies.put(targetSplitIdx, (Object)SplitDependencyLoader.append((int[])splitDependencies.get(targetSplitIdx), splitIdx + 1));
            }
            BitSet bitset = new BitSet();
            int size2 = splitDependencies.size();
            for (int i = 0; i < size2; ++i) {
                int splitIdx2 = splitDependencies.keyAt(i);
                bitset.clear();
                while (splitIdx2 != -1) {
                    if (bitset.get(splitIdx2)) {
                        throw new IllegalDependencyException(StringFog.decrypt("MBwRGgBOOxYXChEEDAtOGgtSBRUCNgdDCxcADAEKFgsRHwAdcQ=="));
                    }
                    bitset.set(splitIdx2);
                    int[] deps = (int[])splitDependencies.get(splitIdx2);
                    splitIdx2 = deps != null ? deps[0] : -1;
                }
            }
            return splitDependencies;
        }

        public static class IllegalDependencyException
        extends Exception {
            private IllegalDependencyException(String message) {
                super(message);
            }
        }
    }

    public static class PackageLite {
        public String packageName;
        public int versionCode;
        public int versionCodeMajor;
        public int installLocation;
        public String[] splitNames;
        public boolean[] isFeatureSplits;
        public String[] usesSplitNames;
        public String[] configForSplit;
        public String codePath;
        public String baseCodePath;
        public String[] splitCodePaths;
        public int baseRevisionCode;
        public int[] splitRevisionCodes;
        public boolean coreApp;
        public boolean debuggable;
        public boolean multiArch;
        public boolean use32bitAbi;
        public boolean extractNativeLibs;
        public boolean isolatedSplits;
        public boolean profilableByShell;
        public boolean isSplitRequired;
        public boolean useEmbeddedDex;

        public String toString() {
            return StringFog.decrypt("IwQRHQQJOj8KGxcLGQ4NGAQVEysPMhZeSA==") + this.packageName + '\'' + StringFog.decrypt("X0UEExcdNhwNLB0UDFI=") + this.versionCode + StringFog.decrypt("X0UEExcdNhwNLB0UDCIPGQoASw==") + this.versionCodeMajor + StringFog.decrypt("X0UbGBYaPh8PIx0TCBsHHAtP") + this.installLocation + StringFog.decrypt("X0UBBgkHKz0CAhcDVA==") + Arrays.toString(this.splitNames) + StringFog.decrypt("X0UbBSMLPgcWHRcjGQMHBxZP") + Arrays.toString(this.isFeatureSplits) + StringFog.decrypt("X0UHBQAdDAMPBgY+CAILAFg=") + Arrays.toString(this.usesSplitNames) + StringFog.decrypt("X0URGQsINhQlAAAjGQMHB1g=") + Arrays.toString(this.configForSplit) + StringFog.decrypt("X0URGQELDxIXB09X") + this.codePath + '\'' + StringFog.decrypt("X0UQFxYLHBwHCiIRHQdTVA==") + this.baseCodePath + '\'' + StringFog.decrypt("X0UBBgkHKzAMCxcgCBsGAFg=") + Arrays.toString(this.splitCodePaths) + StringFog.decrypt("X0UQFxYLDRYVBgEZBgEtHAEXSw==") + this.baseRevisionCode + StringFog.decrypt("X0UBBgkHKyEGGRsDAAAAMAoWExZT") + Arrays.toString(this.splitRevisionCodes) + StringFog.decrypt("X0URGRcLHgMTUg==") + this.coreApp + StringFog.decrypt("X0UWEwcbOBQCDR4VVA==") + this.debuggable + StringFog.decrypt("X0UfAwkaNjIRDBpN") + this.multiArch + StringFog.decrypt("X0UHBQBdbREKGzMSAFI=") + this.use32bitAbi + StringFog.decrypt("X0UXDhEcPhAXIRMEABkLPwwQBVg=") + this.extractNativeLibs + StringFog.decrypt("X0UbBQoCPgcGCyEABQYaAFg=") + this.isolatedSplits + StringFog.decrypt("X0UCBAoINh8CDR4VKxY9GwAeGlg=") + this.profilableByShell + StringFog.decrypt("X0UbBTYeMxoXPRcBHAYcFgFP") + this.isSplitRequired + StringFog.decrypt("X0UHBQArMhEGCxYVDSsLC1g=") + this.useEmbeddedDex + '}';
        }
    }

    public class ProviderIntentInfo
    extends IntentInfo {
        public Provider provider;
    }

    public class ServiceIntentInfo
    extends IntentInfo {
        public Service service;
    }

    public class ActivityIntentInfo
    extends IntentInfo {
        public Activity activity;
    }

    public final class PermissionGroup
    extends Component<IntentInfo> {
        public PermissionGroupInfo info;
    }

    public final class Permission
    extends Component<IntentInfo> {
        public PermissionInfo info;
    }

    public final class Instrumentation
    extends Component<IntentInfo> {
        public InstrumentationInfo info;
    }

    public final class Provider
    extends Component<ProviderIntentInfo> {
        public ProviderInfo info;
    }

    public final class Service
    extends Component<ServiceIntentInfo> {
        public ServiceInfo info;
    }

    public class Package {
        public final ArrayList<Activity> activities = new ArrayList(0);
        public final ArrayList<Activity> receivers = new ArrayList(0);
        public final ArrayList<Provider> providers = new ArrayList(0);
        public final ArrayList<Service> services = new ArrayList(0);
        public final ArrayList<Instrumentation> instrumentation = new ArrayList(0);
        public final ArrayList<Permission> permissions = new ArrayList(0);
        public final ArrayList<PermissionGroup> permissionGroups = new ArrayList(0);
        public final ArrayList<String> requestedPermissions = new ArrayList();
        public Signature[] mSignatures;
        public SigningDetails mSigningDetails;
        public Bundle mAppMetaData;
        public Object mExtras;
        public String packageName;
        public int mPreferredOrder;
        public String mSharedUserId;
        public ArrayList<String> usesLibraries;
        public ArrayList<String> usesOptionalLibraries;
        public int mVersionCode;
        public ApplicationInfo applicationInfo;
        public String mVersionName;
        public boolean use32bitAbi;
        public ArrayList<ConfigurationInfo> configPreferences = null;
        public ArrayList<FeatureInfo> reqFeatures = null;
        public int mSharedUserLabel;
    }

    public static class ApkLite {
        public String codePath;
        public String packageName;
        public String splitName;
        public int versionCode;
        public int versionCodeMajor;
        public int installLocation;
        public Signature[] signatures;
        public boolean coreApp;
        public boolean multiArch;
        public boolean use32bitAbi;
        public boolean extractNativeLibs;
    }

    public static final class Activity
    extends Component<ActivityIntentInfo> {
        public ActivityInfo info;
    }

    public static class Component<II extends IntentInfo> {
        public Package owner;
        public ArrayList<II> intents;
        public String className;
        public Bundle metaData;

        public ComponentName getComponentName() {
            return null;
        }
    }

    public static class IntentInfo
    extends IntentFilter {
        public boolean hasDefault;
        public int labelRes;
        public CharSequence nonLocalizedLabel;
        public int icon;
        public int logo;
        public int banner;
    }

    @TargetApi(value=28)
    public static class Builder {
        private Signature[] mSignatures;

        public Builder setSignatures(Signature[] signatures) {
            this.mSignatures = signatures;
            return this;
        }

        public SigningDetails build() throws CertificateException {
            return new SigningDetails();
        }
    }

    @TargetApi(value=28)
    public static final class SigningDetails {
        public Signature[] signatures;
        public Signature[] pastSigningCertificates;
        public static final SigningDetails UNKNOWN = null;
    }

    @TargetApi(value=29)
    public static final class CallbackImpl
    implements Callback {
        public CallbackImpl(PackageManager pm) {
            throw new RuntimeException(StringFog.decrypt("IBEHFEQ="));
        }
    }

    @TargetApi(value=29)
    public static interface Callback {
    }

    public static class PackageParserException
    extends Exception {
        public final int error;

        public PackageParserException(int error, String detailMessage) {
            super(detailMessage);
            this.error = error;
        }

        public PackageParserException(int error, String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
            this.error = error;
        }
    }
}

