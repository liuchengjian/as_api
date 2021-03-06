package com.liucj.as.api.utils;

import reactor.util.annotation.NonNull;
import reactor.util.annotation.Nullable;

import java.io.Closeable;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HiConfigUtil {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

    public static String getVersion() {
        return sdf.format(new Date());
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    public static boolean compareVersion(@NonNull String v1, @Nullable String v2){
        if(v2 ==null) return true;
        return v1.compareTo(v2)>0;
    }
}
