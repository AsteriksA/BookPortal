package com.gold.service.interfaces;

import java.util.List;

public interface BaseService <T, ID> {

    List<T> findAll();

    T findById(ID id);

    void add(T entity);

    void remove(ID id);

    void update(ID id, T entity);
}
