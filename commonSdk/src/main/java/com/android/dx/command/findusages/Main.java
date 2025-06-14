/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx.command.findusages;

import com.android.dex.Dex;
import com.android.dx.command.findusages.FindUsages;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public final class Main {
    public static void main(String[] args) throws IOException {
        String dexFile = args[0];
        String declaredBy = args[1];
        String memberName = args[2];
        Dex dex = new Dex(new File(dexFile));
        PrintWriter out = new PrintWriter(System.out);
        new FindUsages(dex, declaredBy, memberName, out).findUsages();
        out.flush();
    }
}

