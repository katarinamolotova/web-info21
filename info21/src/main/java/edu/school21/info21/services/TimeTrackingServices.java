package edu.school21.info21.services;

import edu.school21.info21.entities.TimeTrackingEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.TimeTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTrackingServices implements EduService<TimeTrackingEntity, Long> {
    private final TimeTrackingRepository repository;
    private List<TimeTrackingEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;
    private final EntityHandler<TimeTrackingEntity> entityHandler;

    @Autowired
    public TimeTrackingServices(
            final TimeTrackingRepository repository,
            final EntityHandler<TimeTrackingEntity> entityHandler
    ) {
        this.repository = repository;
        this.entityHandler = entityHandler;
    }

    @Override
    public TimeTrackingEntity created(TimeTrackingEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public TimeTrackingEntity update(TimeTrackingEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<TimeTrackingEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<TimeTrackingEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), TimeTrackingEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.TIME_TRACKING_TABLE.getName();
    }

    @Override
    public TimeTrackingEntity findById(Long id) {
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
        return Arrays.stream(TimeTrackingEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());    }
}
