package com.gold.util;

public class EntityUtils {

    public static void checkNull(Object entity) {
        if (entity == null) {
            throw new IllegalArgumentException();
        }
    }
}

