package edu.school21.info21.services;

import edu.school21.info21.entities.VerterEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.services.handlers.CashHandler;
import edu.school21.info21.services.handlers.EntityHandler;
import edu.school21.info21.repositories.VerterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("verter")
public class VerterServices implements EduService<VerterEntity> {
    private final VerterRepository repository;
    private List<VerterEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<VerterEntity> entityHandler;

    @Autowired
    public VerterServices(
            final VerterRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<VerterEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public VerterEntity created(final VerterEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<VerterEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<VerterEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
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
    public VerterEntity findById(final String id) {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
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
        return Arrays.stream(VerterEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public VerterEntity getEmptyEntity() {
        return new VerterEntity();
    }

    @Override
    public boolean existsById(final String id) {
        return repository.existsById(Long.parseLong(id));
    }
}
