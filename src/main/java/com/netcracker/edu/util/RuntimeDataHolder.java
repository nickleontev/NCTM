package com.netcracker.edu.util;

import com.netcracker.edu.dao.impl.runtime.Root;

public class RuntimeDataHolder {

    private static final Root holder = new Root();


    public static Root getHolder() {
        return holder;
    }
}
