package edu.school21.info21.services;

import java.util.List;

public interface EduService<T, V> {

    T created(T entity);

    T update(T entity);

    List<T> findAll ();

    T findById(V id);

    void delete(V id);

    List<String> getHeaderForTable();

}
