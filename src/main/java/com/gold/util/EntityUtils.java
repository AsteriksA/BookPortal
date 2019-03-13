package com.gold.util;

import javax.persistence.EntityNotFoundException;

public final class EntityUtils {

    public static void isNull(Object entity) {
        if (entity == null) {
            throw new EntityNotFoundException();
        }
    }
}

