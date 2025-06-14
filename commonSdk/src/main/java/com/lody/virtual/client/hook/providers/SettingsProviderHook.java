/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IInterface
 *  android.provider.Settings
 *  android.provider.Settings$System
 *  android.text.TextUtils
 */
package com.lody.virtual.client.hook.providers;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IInterface;
import android.provider.Settings;
import android.text.TextUtils;
import com.lody.virtual.GmsSupport;
import com.lody.virtual.StringFog;
import com.lody.virtual.client.VClient;
import com.lody.virtual.client.env.SpecialComponentList;
import com.lody.virtual.client.hook.base.MethodBox;
import com.lody.virtual.client.hook.providers.ExternalProviderHook;
import com.lody.virtual.client.ipc.VActivityManager;
import com.lody.virtual.helper.compat.BuildCompat;
import com.lody.virtual.helper.utils.VLog;
import com.lody.virtual.remote.VDeviceConfig;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SettingsProviderHook
extends ExternalProviderHook {
    private static final String TAG = SettingsProviderHook.class.getSimpleName();
    private static final int METHOD_GET = 0;
    private static final int METHOD_PUT = 1;
    private static final int METHOD_LIST = 2;
    private static final Map<String, String> PRE_SET_VALUES = new HashMap<String, String>();
    private static final Set<String> SETTINGS_DIRECT_TO_SYSTEM = new HashSet<String>();
    private static final Set<String> sSystemTableColums = new HashSet<String>();

    public SettingsProviderHook(IInterface base) {
        super(base);
    }

    private static int getMethodType(String method) {
        if (method.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JSwuBmYzSFo=")))) {
            return 0;
        }
        if (method.startsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhUuBmYzSFo=")))) {
            return 1;
        }
        return -1;
    }

    private static boolean isSecureMethod(String method) {
        return method.endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uOWwaFis=")));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object ... args) throws Throwable {
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki05OGszJCRgVyRF")) + method.getName() + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl8HJnsFSFo=")) + Arrays.toString(args));
        return super.invoke(proxy, method, args);
    }

    @Override
    public Cursor query(MethodBox methodBox, Uri url, String[] projection, String selection, String[] selectionArgs, String sortOrder, Bundle originQueryArgs) throws InvocationTargetException {
        if (url.toString().equals(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGwFNCZmVgU1Oi4qPWUwBi9lNyA6PD42KWAwICxuJ1RF")))) {
            return null;
        }
        return super.query(methodBox, url, projection, selection, selectionArgs, sortOrder, originQueryArgs);
    }

    static int getTableIndex(String str) {
        if (str.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki4uOWwaFis=")))) {
            return 1;
        }
        if (str.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ki0YKWwFNCM=")))) {
            return 0;
        }
        if (str.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4ED2sjJCQ=")))) {
            return 2;
        }
        return str.contains(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGgjAi0="))) ? 3 : -1;
    }

    private static void initSystemTableColums() {
        try {
            Field[] declaredFields = Settings.System.class.getDeclaredFields();
            if (declaredFields != null) {
                for (Field field : declaredFields) {
                    if ((field.getModifiers() & 1) == 0 || (field.getModifiers() & 8) == 0 || (field.getModifiers() & 0x10) == 0 || field.getType() != String.class) continue;
                    sSystemTableColums.add((String)field.get(null));
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isUseVSettingsProvider(String pkg) {
        return GmsSupport.isGoogleAppOrService(pkg) || SpecialComponentList.getPreInstallPackages().contains(pkg);
    }

    public static void passSettingsProviderPermissionCheck(String packageName) {
        if (SettingsProviderHook.isUseVSettingsProvider(packageName)) {
            try {
                XposedHelpers.findAndHookMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fLGwjBitsMxpKLhciI30gLABsJB4vKi4IVg==")), Settings.class.getClassLoader(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGcwMARjDlk9")), String.class, String.class, String.class, new XC_MethodHook(){

                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        param.setResult(param.args[2]);
                    }
                });
                XposedHelpers.findAndHookMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fLGwjBitsMxpKLhciI30gLABsJB4vKi4IVg==")), Settings.class.getClassLoader(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGMjGiVgHjA7Kj5SVg==")), String.class, String.class, Boolean.TYPE, new XC_MethodHook(){

                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        param.setResult(param.args[2]);
                    }
                });
                XposedHelpers.findAndHookMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fLGwjBitsMxpKLhciI30gLABsJB4vKi4IVg==")), Settings.class.getClassLoader(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLH0VBgY=")), String.class, String.class, Integer.TYPE, new XC_MethodHook(){

                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        param.setResult(param.args[2]);
                    }
                });
                XposedHelpers.findAndHookMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fLGwjBitsMxpKLhciI30gLABsJB4vKi4IVg==")), Settings.class.getClassLoader(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGIFGiZiJ1RF")), String.class, String.class, Long.TYPE, new XC_MethodHook(){

                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        param.setResult(param.args[2]);
                    }
                });
                XposedHelpers.findAndHookMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fLGwjBitsMxpKLhciI30gLABsJB4vKi4IVg==")), Settings.class.getClassLoader(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LS4uLGAjHiV9AQpF")), String.class, String.class, Float.TYPE, new XC_MethodHook(){

                    @Override
                    protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                        param.setResult(param.args[2]);
                    }
                });
                if (BuildCompat.isS()) {
                    XposedHelpers.findAndHookMethod(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iV1ksIz1fLGwjBitsMxoQLhcqCmMKRSJlIDBTKC4mJ2czJAJsESgMOwcuJ24FSFo=")), Settings.class.getClassLoader(), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAc2E2sVHiRiASwVLRcMD2owBghsNwYeIS4uO2IaPCVsHgoRLy0YCmwzAjVsAR46Jj5SVg==")), new XC_MethodHook(){

                        @Override
                        protected void beforeHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                            param.setResult(true);
                        }
                    });
                }
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Bundle call(MethodBox methodBox, String method, String arg, Bundle extras) throws InvocationTargetException {
        if (!VClient.get().isProcessBound()) {
            return (Bundle)methodBox.call();
        }
        int tableIndex = SettingsProviderHook.getTableIndex(method);
        if (tableIndex >= 0) {
            if (BuildCompat.isR() && TextUtils.equals((CharSequence)method, (CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IiwuBmYxJA5oHx45Ki0YPGwjElo=")))) {
                Bundle bundle = new Bundle();
                bundle.putInt(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Li4ACGgjAi1sJyg/LBZfOW8zOB9sNyg/KhcMKg==")), 1);
                return bundle;
            }
            if (BuildCompat.isR() && TextUtils.equals((CharSequence)method, (CharSequence)StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OxYYA2QIGilgJFk+KQc6Vg==")))) {
                return null;
            }
            int methodType = SettingsProviderHook.getMethodType(method);
            if (methodType == 0) {
                VDeviceConfig config;
                String presetValue = PRE_SET_VALUES.get(arg);
                if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4EI2gaMCVgJwo0Ji0YOW8jGlo=")).equals(arg)) {
                    config = VClient.get().getDeviceConfig();
                    if (config.enable && config.bluetoothName != null) {
                        return this.wrapBundle(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Lj4EI2gaMCVgJwo0Ji0YOW8jGlo=")), config.bluetoothName);
                    }
                }
                VLog.d(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("ITw9DQ==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii4uLGwFAiZiJygCIz1fLGwjBitsNVkcLD5aJH0gPDdsVyAcLy0cLGUjMAppHjw0DV4hMQ==")) + methodType + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Pl85OHsKIARiASg/LBY+OW8wGit+N1RF")) + presetValue, new Object[0]);
                if (presetValue != null) {
                    return this.wrapBundle(arg, presetValue);
                }
                if (StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iHx4zKBhSVg==")).equals(arg)) {
                    config = VClient.get().getDeviceConfig();
                    if (config.enable && config.androidId != null) {
                        return this.wrapBundle(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggcPG8jGi9iHx4zKBhSVg==")), config.androidId);
                    }
                }
                if (SETTINGS_DIRECT_TO_SYSTEM.contains(arg)) {
                    return (Bundle)methodBox.call();
                }
                if (tableIndex != 0 && SettingsProviderHook.isUseVSettingsProvider(VClient.get().getCurrentPackage())) {
                    return this.wrapBundle(arg, VActivityManager.get().getSettingsProvider(tableIndex, arg));
                }
            } else {
                if (tableIndex != 0 && SettingsProviderHook.isUseVSettingsProvider(VClient.get().getCurrentPackage())) {
                    String value = extras.getString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNFo=")));
                    if (TextUtils.isEmpty((CharSequence)value) || SETTINGS_DIRECT_TO_SYSTEM.contains(arg)) {
                        return new Bundle();
                    }
                    VActivityManager.get().setSettingsProvider(tableIndex, arg, value);
                    return new Bundle();
                }
                if (SettingsProviderHook.isSecureMethod(method)) {
                    return new Bundle();
                }
            }
        }
        try {
            return (Bundle)methodBox.call();
        }
        catch (Exception e) {
            if (e.getCause() instanceof SecurityException) {
                return new Bundle();
            }
            if (e.getCause() instanceof IllegalArgumentException) {
                return new Bundle();
            }
            throw e;
        }
    }

    private Bundle wrapBundle(String name, String value) {
        Bundle bundle = new Bundle();
        if (Build.VERSION.SDK_INT >= 24) {
            bundle.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iz4+DWgVSFo=")), name);
            bundle.putString(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KT4+DmwVNFo=")), value);
        } else {
            bundle.putString(name, value);
        }
        return bundle;
    }

    @Override
    protected void processArgs(Method method, Object ... args) {
        super.processArgs(method, args);
    }

    static {
        PRE_SET_VALUES.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("KQc2M28mGgNiAQovIxZfP28FPAJlESg/LhhSVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OghSVg==")));
        PRE_SET_VALUES.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LAgcKWwFJCRgHx42Ki0YHW8jQQRqJyg/ID4+DmEVNFo=")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OghSVg==")));
        PRE_SET_VALUES.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguLmgVHiVhHl0/Kj42HWoFGgZvER4bLj02E2IKRSRpNF0uLz5SVg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OhhSVg==")));
        PRE_SET_VALUES.put(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LggqOmYzNCZ9DiwoKAc2Vg==")), StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OhhSVg==")));
        SETTINGS_DIRECT_TO_SYSTEM.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("LRguLmUVLCtsJyQqKi4+MWoFLCVlNygv")));
        SETTINGS_DIRECT_TO_SYSTEM.add(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgAOWsaMC9gJFlAIxguDWUVLCxrDgo6ID4+KGAaAjJuDjBF")));
    }
}

