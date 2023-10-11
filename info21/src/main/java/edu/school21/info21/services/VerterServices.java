package edu.school21.info21.services;

import edu.school21.info21.entities.VerterEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.VerterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VerterServices implements EduService<VerterEntity, Long> {
    private final VerterRepository repository;
    private List<VerterEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;
    private final EntityHandler<VerterEntity> entityHandler;

    @Autowired
    public VerterServices(
            final VerterRepository repository,
            final EntityHandler<VerterEntity> entityHandler
    ) {
        this.repository = repository;
        this.entityHandler = entityHandler;
    }

    @Override
    public VerterEntity created(VerterEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public VerterEntity update(VerterEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<VerterEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<VerterEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), VerterEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.VERTER_TABLE.getName();
    }

    @Override
    public VerterEntity findById(Long id) {
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
        return Arrays.stream(VerterEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
