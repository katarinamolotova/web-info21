package edu.school21.info21.repositories;

import edu.school21.info21.entities.PeerEntity;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PeerRepository {

    private SessionFactory sessionFactory;

    public PeerEntity loadByNickname(final String nickname) {
        return getCurrentSession().get(PeerEntity.class, nickname);
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
