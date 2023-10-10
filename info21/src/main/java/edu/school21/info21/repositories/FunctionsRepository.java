package edu.school21.info21.repositories;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class FunctionsRepository {
    private EntityManager entityManager;

    //  тестовая история, чтобы посмотреть как работать в нативный sql
    //  чтобы вывести потом результат, надо сделать массив объектов и поочереди выводить
    public String test() {
        return ((Object[]) entityManager.createNativeQuery("SELECT * FROM info21java.public.peers")
                                           .getSingleResult())[0].toString();
    }
}
