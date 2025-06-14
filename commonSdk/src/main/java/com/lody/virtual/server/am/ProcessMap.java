/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.server.am;

import com.lody.virtual.helper.collection.ArrayMap;
import com.lody.virtual.helper.collection.SparseArray;

class ProcessMap<E> {
    private final ArrayMap<String, SparseArray<E>> mMap = new ArrayMap();

    ProcessMap() {
    }

    public E get(String name, int uid) {
        SparseArray uids = (SparseArray)this.mMap.get(name);
        if (uids == null) {
            return null;
        }
        return uids.get(uid);
    }

    public E put(String name, int uid, E value) {
        SparseArray<E> uids = (SparseArray<E>)this.mMap.get(name);
        if (uids == null) {
            uids = new SparseArray<E>(2);
            this.mMap.put(name, uids);
        }
        uids.put(uid, value);
        return value;
    }

    public E remove(String name, int uid) {
        SparseArray uids = (SparseArray)this.mMap.get(name);
        if (uids != null) {
            Object old = uids.removeReturnOld(uid);
            if (uids.size() == 0) {
                this.mMap.remove(name);
            }
            return old;
        }
        return null;
    }

    public ArrayMap<String, SparseArray<E>> getMap() {
        return this.mMap;
    }
}

