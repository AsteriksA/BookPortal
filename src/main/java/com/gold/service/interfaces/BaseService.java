package com.gold.service.interfaces;

import java.util.List;

public interface BaseService <T, ID> {

    List<T> findAll();
    T findOne(ID id);
    T add(T entity);
    void delete(ID id);
}
