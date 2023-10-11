package edu.school21.info21.services;

import edu.school21.info21.entities.CheckEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.CheckRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckServices implements EduService<CheckEntity, Long> {
    private final CheckRepository repository;

    @Override
    public CheckEntity created(CheckEntity entity) {
        return repository.save(entity);
    }

    @Override
    public CheckEntity update(CheckEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<CheckEntity> findAll() {
        return (List<CheckEntity>)repository.findAll();
    }

    @Override
    public CheckEntity findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(CheckEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
