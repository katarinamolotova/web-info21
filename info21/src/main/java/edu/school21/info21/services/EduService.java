package edu.school21.info21.services;

import java.util.List;

public interface EduService<T> {

    T created(final T entity);

    T update(final T entity);

    List<T> findAll();

    List<List<String>> findAllAsString();

    String getTableName();

    T findById(final String id);

    void delete(final String id);

    List<String> getHeaderForTable();

    T getEmptyEntity();

    boolean existsById(final String id);

}
