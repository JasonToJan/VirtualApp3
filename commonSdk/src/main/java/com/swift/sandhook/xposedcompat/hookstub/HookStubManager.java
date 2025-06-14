/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.swift.sandhook.xposedcompat.hookstub;

import android.util.Log;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookMethodResolver;
import com.swift.sandhook.utils.ParamWrapper;
import com.swift.sandhook.wrapper.StubMethodsFactory;
import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.hookstub.CallOriginCallBack;
import com.swift.sandhook.xposedcompat.hookstub.HookMethodEntity;
import com.swift.sandhook.xposedcompat.hookstub.MethodHookerStubs32;
import com.swift.sandhook.xposedcompat.hookstub.MethodHookerStubs64;
import com.swift.sandhook.xposedcompat.utils.DexLog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

public class HookStubManager {
    public static volatile boolean is64Bit;
    public static final int MAX_64_ARGS = 7;
    public static int MAX_STUB_ARGS;
    public static int[] stubSizes;
    public static boolean hasStubBackup;
    public static AtomicInteger[] curUseStubIndexes;
    public static int ALL_STUB;
    public static Member[] originMethods;
    public static HookMethodEntity[] hookMethodEntities;
    public static XposedBridge.AdditionalHookInfo[] additionalHookInfos;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static HookMethodEntity getHookMethodEntity(Member origin, XposedBridge.AdditionalHookInfo additionalHookInfo) {
        int needStubArgCount;
        Class<?>[] parType;
        Class<Object> retType;
        if (!HookStubManager.support()) {
            return null;
        }
        boolean isStatic = Modifier.isStatic(origin.getModifiers());
        if (origin instanceof Method) {
            Method method = (Method)origin;
            retType = method.getReturnType();
            parType = method.getParameterTypes();
        } else if (origin instanceof Constructor) {
            Constructor constructor = (Constructor)origin;
            retType = Void.TYPE;
            parType = constructor.getParameterTypes();
        } else {
            return null;
        }
        if (!ParamWrapper.support(retType)) {
            return null;
        }
        int n = needStubArgCount = isStatic ? 0 : 1;
        if (parType != null) {
            if ((needStubArgCount += parType.length) > MAX_STUB_ARGS) {
                return null;
            }
            if (is64Bit && needStubArgCount > 7) {
                return null;
            }
            for (Class<?> par : parType) {
                if (ParamWrapper.support(par)) continue;
                return null;
            }
        } else {
            parType = new Class[]{};
        }
        Class<?>[] classArray = HookStubManager.class;
        synchronized (HookStubManager.class) {
            StubMethodsInfo stubMethodInfo = HookStubManager.getStubMethodPair(is64Bit, needStubArgCount);
            if (stubMethodInfo == null) {
                // ** MonitorExit[var6_7] (shouldn't be in output)
                return null;
            }
            HookMethodEntity entity = new HookMethodEntity(origin, stubMethodInfo.hook, stubMethodInfo.backup);
            entity.retType = retType;
            entity.parType = parType;
            if (hasStubBackup && !HookStubManager.tryCompileAndResolveCallOriginMethod(entity.backup, stubMethodInfo.args, stubMethodInfo.index)) {
                DexLog.w("internal stub <" + entity.hook.getName() + "> call origin compile failure, skip use internal stub");
                // ** MonitorExit[var6_7] (shouldn't be in output)
                return null;
            }
            int id2 = HookStubManager.getMethodId(stubMethodInfo.args, stubMethodInfo.index);
            HookStubManager.originMethods[id2] = origin;
            HookStubManager.hookMethodEntities[id2] = entity;
            HookStubManager.additionalHookInfos[id2] = additionalHookInfo;
            // ** MonitorExit[var6_7] (shouldn't be in output)
            return entity;
        }
    }

    public static int getMethodId(int args, int index) {
        int id2 = index;
        for (int i = 0; i < args; ++i) {
            id2 += stubSizes[i];
        }
        return id2;
    }

    public static String getHookMethodName(int index) {
        return "stub_hook_" + index;
    }

    public static String getBackupMethodName(int index) {
        return "stub_backup_" + index;
    }

    public static String getCallOriginClassName(int args, int index) {
        return "call_origin_" + args + "_" + index;
    }

    private static synchronized StubMethodsInfo getStubMethodPair(boolean is64Bit, int stubArgs) {
        if ((stubArgs = HookStubManager.getMatchStubArgsCount(stubArgs)) < 0) {
            return null;
        }
        int curUseStubIndex = curUseStubIndexes[stubArgs].getAndIncrement();
        Class[] pars = HookStubManager.getFindMethodParTypes(is64Bit, stubArgs);
        try {
            Method backup;
            if (is64Bit) {
                Method backup2;
                Method hook = MethodHookerStubs64.class.getDeclaredMethod(HookStubManager.getHookMethodName(curUseStubIndex), pars);
                Method method = backup2 = hasStubBackup ? MethodHookerStubs64.class.getDeclaredMethod(HookStubManager.getBackupMethodName(curUseStubIndex), pars) : StubMethodsFactory.getStubMethod();
                if (hook == null || backup2 == null) {
                    return null;
                }
                return new StubMethodsInfo(stubArgs, curUseStubIndex, hook, backup2);
            }
            Method hook = MethodHookerStubs32.class.getDeclaredMethod(HookStubManager.getHookMethodName(curUseStubIndex), pars);
            Method method = backup = hasStubBackup ? MethodHookerStubs32.class.getDeclaredMethod(HookStubManager.getBackupMethodName(curUseStubIndex), pars) : StubMethodsFactory.getStubMethod();
            if (hook == null || backup == null) {
                return null;
            }
            return new StubMethodsInfo(stubArgs, curUseStubIndex, hook, backup);
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    public static Method getCallOriginMethod(int args, int index) {
        Class stubClass = is64Bit ? MethodHookerStubs64.class : MethodHookerStubs32.class;
        String className = stubClass.getName();
        className = className + "$";
        className = className + HookStubManager.getCallOriginClassName(args, index);
        try {
            Class<?> callOriginClass = Class.forName(className, true, stubClass.getClassLoader());
            return callOriginClass.getDeclaredMethod("call", long[].class);
        }
        catch (Throwable e) {
            Log.e((String)"HookStubManager", (String)"load call origin class error!", (Throwable)e);
            return null;
        }
    }

    public static boolean tryCompileAndResolveCallOriginMethod(Method backupMethod, int args, int index) {
        Method method = HookStubManager.getCallOriginMethod(args, index);
        if (method != null) {
            SandHookMethodResolver.resolveMethod(method, backupMethod);
            return SandHook.compileMethod(method);
        }
        return false;
    }

    public static int getMatchStubArgsCount(int stubArgs) {
        for (int i = stubArgs; i <= MAX_STUB_ARGS; ++i) {
            if (curUseStubIndexes[i].get() >= stubSizes[i]) continue;
            return i;
        }
        return -1;
    }

    public static Class[] getFindMethodParTypes(boolean is64Bit, int stubArgs) {
        if (stubArgs == 0) {
            return null;
        }
        Class[] args = new Class[stubArgs];
        if (is64Bit) {
            for (int i = 0; i < stubArgs; ++i) {
                args[i] = Long.TYPE;
            }
        } else {
            for (int i = 0; i < stubArgs; ++i) {
                args[i] = Integer.TYPE;
            }
        }
        return args;
    }

    public static long hookBridge(int id2, CallOriginCallBack callOrigin, long ... stubArgs) throws Throwable {
        Member originMethod = originMethods[id2];
        HookMethodEntity entity = hookMethodEntities[id2];
        Object thiz = null;
        Object[] args = null;
        if (HookStubManager.hasArgs(stubArgs)) {
            thiz = entity.getThis(stubArgs[0]);
            args = entity.getArgs(stubArgs);
        }
        if (XposedBridge.disableHooks) {
            if (hasStubBackup) {
                return callOrigin.call(stubArgs);
            }
            return HookStubManager.callOrigin(entity, originMethod, thiz, args);
        }
        DexLog.printMethodHookIn(originMethod);
        Object[] snapshot = HookStubManager.additionalHookInfos[id2].callbacks.getSnapshot();
        if (snapshot == null || snapshot.length == 0) {
            if (hasStubBackup) {
                return callOrigin.call(stubArgs);
            }
            return HookStubManager.callOrigin(entity, originMethod, thiz, args);
        }
        XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();
        param.method = originMethod;
        param.thisObject = thiz;
        param.args = args;
        int beforeIdx = 0;
        do {
            try {
                ((XC_MethodHook)snapshot[beforeIdx]).callBeforeHookedMethod(param);
            }
            catch (Throwable t) {
                param.setResult(null);
                param.returnEarly = false;
                continue;
            }
            if (!param.returnEarly) continue;
            ++beforeIdx;
            break;
        } while (++beforeIdx < snapshot.length);
        if (!param.returnEarly) {
            try {
                if (hasStubBackup) {
                    long[] newArgs = entity.getArgsAddress(stubArgs, param.args);
                    param.setResult(entity.getResult(callOrigin.call(newArgs)));
                } else {
                    param.setResult(SandHook.callOriginMethod(originMethod, entity.backup, thiz, param.args));
                }
            }
            catch (Throwable e) {
                XposedBridge.log(e);
                param.setThrowable(e);
            }
        }
        int afterIdx = beforeIdx - 1;
        do {
            Object lastResult = param.getResult();
            Throwable lastThrowable = param.getThrowable();
            try {
                ((XC_MethodHook)snapshot[afterIdx]).callAfterHookedMethod(param);
            }
            catch (Throwable t) {
                XposedBridge.log(t);
                if (lastThrowable == null) {
                    param.setResult(lastResult);
                    continue;
                }
                param.setThrowable(lastThrowable);
            }
        } while (--afterIdx >= 0);
        if (!param.hasThrowable()) {
            return entity.getResultAddress(param.getResult());
        }
        throw param.getThrowable();
    }

    public static Object hookBridge(Member origin, Method backup, XposedBridge.AdditionalHookInfo additionalHookInfo, Object thiz, Object ... args) throws Throwable {
        if (XposedBridge.disableHooks) {
            return SandHook.callOriginMethod(origin, backup, thiz, args);
        }
        DexLog.printMethodHookIn(origin);
        Object[] snapshot = additionalHookInfo.callbacks.getSnapshot();
        if (snapshot == null || snapshot.length == 0) {
            return SandHook.callOriginMethod(origin, backup, thiz, args);
        }
        XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();
        param.method = origin;
        param.thisObject = thiz;
        param.args = args;
        int beforeIdx = 0;
        do {
            try {
                ((XC_MethodHook)snapshot[beforeIdx]).callBeforeHookedMethod(param);
            }
            catch (Throwable t) {
                param.setResult(null);
                param.returnEarly = false;
                continue;
            }
            if (!param.returnEarly) continue;
            ++beforeIdx;
            break;
        } while (++beforeIdx < snapshot.length);
        if (!param.returnEarly) {
            try {
                param.setResult(SandHook.callOriginMethod(origin, backup, thiz, param.args));
            }
            catch (Throwable e) {
                XposedBridge.log(e);
                param.setThrowable(e);
            }
        }
        int afterIdx = beforeIdx - 1;
        do {
            Object lastResult = param.getResult();
            Throwable lastThrowable = param.getThrowable();
            try {
                ((XC_MethodHook)snapshot[afterIdx]).callAfterHookedMethod(param);
            }
            catch (Throwable t) {
                XposedBridge.log(t);
                if (lastThrowable == null) {
                    param.setResult(lastResult);
                    continue;
                }
                param.setThrowable(lastThrowable);
            }
        } while (--afterIdx >= 0);
        if (!param.hasThrowable()) {
            return param.getResult();
        }
        throw param.getThrowable();
    }

    public static final long callOrigin(HookMethodEntity entity, Member origin, Object thiz, Object[] args) throws Throwable {
        Object res = SandHook.callOriginMethod(origin, entity.backup, thiz, args);
        return entity.getResultAddress(res);
    }

    private static boolean hasArgs(long ... args) {
        return args != null && args.length > 0;
    }

    public static boolean support() {
        return MAX_STUB_ARGS > 0 && SandHook.canGetObject() && SandHook.canGetObjectAddress();
    }

    static {
        MAX_STUB_ARGS = 0;
        ALL_STUB = 0;
        is64Bit = SandHook.is64Bit();
        Class stubClass = is64Bit ? MethodHookerStubs64.class : MethodHookerStubs32.class;
        stubSizes = (int[])XposedHelpers.getStaticObjectField(stubClass, "stubSizes");
        Boolean hasBackup = (Boolean)XposedHelpers.getStaticObjectField(stubClass, "hasStubBackup");
        boolean bl = hasStubBackup = hasBackup != null && hasBackup != false && !XposedCompat.useNewCallBackup;
        if (stubSizes != null && stubSizes.length > 0) {
            MAX_STUB_ARGS = stubSizes.length - 1;
            curUseStubIndexes = new AtomicInteger[MAX_STUB_ARGS + 1];
            for (int i = 0; i < MAX_STUB_ARGS + 1; ++i) {
                HookStubManager.curUseStubIndexes[i] = new AtomicInteger(0);
                ALL_STUB += stubSizes[i];
            }
            originMethods = new Member[ALL_STUB];
            hookMethodEntities = new HookMethodEntity[ALL_STUB];
            additionalHookInfos = new XposedBridge.AdditionalHookInfo[ALL_STUB];
        }
    }

    static class StubMethodsInfo {
        int args = 0;
        int index = 0;
        Method hook;
        Method backup;

        public StubMethodsInfo(int args, int index, Method hook, Method backup) {
            this.args = args;
            this.index = index;
            this.hook = hook;
            this.backup = backup;
        }
    }
}

