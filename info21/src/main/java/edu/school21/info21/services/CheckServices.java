package edu.school21.info21.services;

import edu.school21.info21.entities.CheckEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckServices implements EduService<CheckEntity, Long> {
    private final CheckRepository repository;
    private List<CheckEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;

    @Autowired
    public CheckServices(final CheckRepository repository) {
        this.repository = repository;
    }

    @Override
    public CheckEntity created(CheckEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public CheckEntity update(CheckEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<CheckEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<CheckEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public CheckEntity findById(Long id) {
        if(isChanged || dataCash.isEmpty()) {
            return repository.findById(id)
                             .orElseThrow(NotFoundEntity::new);
        } else {
            return dataCash.stream()
                           .filter( i -> i.getId() == id)
                           .findFirst()
                           .orElseThrow(NotFoundEntity::new);
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
        this.isChanged = true;
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(CheckEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
