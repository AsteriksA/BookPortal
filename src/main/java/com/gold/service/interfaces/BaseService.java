package com.gold.service.interfaces;

import java.util.List;

public interface BaseService <T, ID> {

    List<T> findAll();

    T findById(ID id);

    void addEntity(T entity);

    void removeEntity(ID id);

    void updateEntity(ID id, T entity);
}
