package edu.school21.info21.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Repository
public class FunctionsRepository {
    private EntityManager entityManager;

    //  перенести формирование запроса в сервис?
    public List doNativeQuery(final String query) {
        return entityManager.createNativeQuery(query)
                            .getResultList();
    }

    //  получение имен столбцов?
    public List transferredPointsFromPeers() {
        final Query query = entityManager.createNativeQuery("SELECT * FROM transferred_points_form()");
        return query.getResultList();
    }

    public List successfullyCompletedProjects() {
        return entityManager.createNativeQuery("SELECT * FROM successfully_completed_projects()")
                            .getResultList();
    }

    public List peersAreNotLeavingSchoolOnDate(final LocalDate date) {
        final String query = String.format("SELECT * FROM peers_did_not_come_out('%s')", date);
        return entityManager.createNativeQuery(query).getResultList();
    }

    public List changingPeersPoints() {
        return entityManager.createNativeQuery("SELECT * FROM changing_peer_points()")
                            .getResultList();
    }


}
