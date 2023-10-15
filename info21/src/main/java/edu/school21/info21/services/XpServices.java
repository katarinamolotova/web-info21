package edu.school21.info21.services;

import edu.school21.info21.entities.XpEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.CashHandler;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.XpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("xp")
public class XpServices implements EduService<XpEntity> {
    private final XpRepository repository;
    private List<XpEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<XpEntity> entityHandler;

    @Autowired
    public XpServices(
            final XpRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<XpEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public XpEntity created(XpEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public XpEntity update(XpEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<XpEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<XpEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
        }
        return dataCash;    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), XpEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.XP_TABLE.getName();
    }

    @Override
    public XpEntity findById(String id) {
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
    public void delete(String id) {
        try {
            repository.deleteById(Long.parseLong(id));
            cashHandler.globalChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(XpEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public XpEntity getEmptyEntity() {
        return new XpEntity();
    }
}
