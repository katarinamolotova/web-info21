package edu.school21.info21.repositories;

import edu.school21.info21.entities.FriendsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends CrudRepository<FriendsEntity, Long> {}
