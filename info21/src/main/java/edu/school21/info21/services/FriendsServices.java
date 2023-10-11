package edu.school21.info21.services;

import edu.school21.info21.entities.FriendsEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.FriendsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendsServices implements EduService<FriendsEntity, Long> {
    private final FriendsRepository repository;

    @Override
    public FriendsEntity created(FriendsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public FriendsEntity update(FriendsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<FriendsEntity> findAll() {
        return (List<FriendsEntity>)repository.findAll();
    }

    @Override
    public FriendsEntity findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(FriendsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());

    }
}
