package edu.school21.info21.services;

import java.util.List;

public interface EduService<T> {

    T created(T entity);

    T update(T entity);

    List<T> findAll();

    List<List<String>> findAllAsString();

    String getTableName();

    T findById(String id);

    void delete(String id);

    List<String> getHeaderForTable();

    T getEmptyEntity();

}
