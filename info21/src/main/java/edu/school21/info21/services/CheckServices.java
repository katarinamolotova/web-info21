package edu.school21.info21.services;

import edu.school21.info21.repositories.entities.CheckEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.services.handlers.CashHandler;
import edu.school21.info21.services.handlers.EntityHandler;
import edu.school21.info21.repositories.CheckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("checks")
public class CheckServices implements EduService<CheckEntity> {
    private final CheckRepository repository;
    private List<CheckEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<CheckEntity> entityHandler;

    @Autowired
    public CheckServices(
            final CheckRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<CheckEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public CheckEntity created(final CheckEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<CheckEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<CheckEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
        }
        return dataCash;
    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), CheckEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.CHECK_TABLE.getName();
    }

    @Override
    public CheckEntity findById(final String id) {
        if (cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            return repository.findById(Long.parseLong(id))
                             .orElseThrow(NotFoundEntity::new);
        } else {
            return dataCash.stream()
                           .filter( i -> i.getId() == Long.parseLong(id))
                           .findFirst()
                           .orElseThrow(NotFoundEntity::new);
        }
    }

    @Override
    public void delete(final String id) {
        try {
            repository.deleteById(Long.parseLong(id));
            cashHandler.globalChanges();
        } catch (final Exception e) {
            throw new NotFoundEntity();
        }
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(CheckEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public CheckEntity getEmptyEntity() {
        return new CheckEntity();
    }

    @Override
    public boolean existsById(final String id) {
        return repository.existsById(Long.parseLong(id));
    }
}
