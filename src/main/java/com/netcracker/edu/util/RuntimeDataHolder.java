package com.netcracker.edu.util;

import com.netcracker.edu.fxmodel.Root;

public class RuntimeDataHolder {

    public static final String PATH = "data.json";
    private static  Root holder;

    public static Root getHolder() {
        if (holder==null) holder = new Root();
        return holder;
    }

    public static void setHolder(Root root) {
        holder = root;
    }


}
