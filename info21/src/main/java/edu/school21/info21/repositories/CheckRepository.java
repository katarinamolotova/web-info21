package edu.school21.info21.repositories;

import edu.school21.info21.repositories.entities.CheckEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckRepository extends CrudRepository<CheckEntity, Long> {}
