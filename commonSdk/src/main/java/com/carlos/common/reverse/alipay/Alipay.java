/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.carlos.common.reverse.alipay;

import android.content.Context;
import com.carlos.common.reverse.ReflectionApplication;
import com.kook.librelease.StringFog;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

public class Alipay
extends ReflectionApplication {
    public static void hook(ClassLoader classLoader) {
        if (!REFLECTION_ALIPAY) {
            return;
        }
        try {
            XposedHelpers.findAndHookMethod(XposedHelpers.findClass(com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("Li4ADXojJCRjASQ7LQMYOWozPCVoNx4dLhc2J30jLDVvATAyLAgcLXUwLDVrERooIBgMKGkgGQJuASg8IwgACHomLCl9DlkRLBg2OW4FJFo=")), classLoader), com.carlos.libcommon.StringFog.decrypt(StringFog.decrypt("LS4uLGMbMwFPVgpF")), Context.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE, String.class, new XC_MethodHook(){

                @Override
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {
                    param.setResult(null);
                }
            });
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

