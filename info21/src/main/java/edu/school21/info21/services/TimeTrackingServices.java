package edu.school21.info21.services;

import edu.school21.info21.entities.TimeTrackingEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.CashHandler;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.handlers.ServicesHandler;
import edu.school21.info21.repositories.TimeTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TimeTrackingServices implements EduService<TimeTrackingEntity, Long> {
    private final TimeTrackingRepository repository;
    private List<TimeTrackingEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<TimeTrackingEntity> entityHandler;

    @Autowired
    public TimeTrackingServices(
            final TimeTrackingRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<TimeTrackingEntity> entityHandler,
            final ServicesHandler servicesHandler
    ) {
        servicesHandler.registry("time-tracking", this);
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public TimeTrackingEntity created(TimeTrackingEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public TimeTrackingEntity update(TimeTrackingEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<TimeTrackingEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<TimeTrackingEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
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
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
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
        try {
            repository.deleteById(id);
            cashHandler.globalChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(TimeTrackingEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
