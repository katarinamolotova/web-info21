package edu.school21.info21.repositories;

import edu.school21.info21.entities.P2pEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface P2pRepository extends CrudRepository<P2pEntity, Long> {}
