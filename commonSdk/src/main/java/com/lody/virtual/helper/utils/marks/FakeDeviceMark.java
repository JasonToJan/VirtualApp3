/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.helper.utils.marks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.SOURCE)
public @interface FakeDeviceMark {
    public String value() default "";
}

