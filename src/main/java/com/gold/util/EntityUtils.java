package com.gold.util;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

public final class EntityUtils {

    public static void notNull(Object entity) {
        if (Objects.isNull(entity)) {
            String ENTITY_NOT_FOUND_MESSAGE = "Sorry, but user isn't exist";
            throw new EntityNotFoundException(ENTITY_NOT_FOUND_MESSAGE);
        }
    }
}

