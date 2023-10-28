package edu.school21.info21.services;

import edu.school21.info21.repositories.entities.TransferredPointsEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.services.handlers.CashHandler;
import edu.school21.info21.services.handlers.EntityHandler;
import edu.school21.info21.repositories.TransferredPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("transferred_points")
public class TransferredPointsServices implements EduService<TransferredPointsEntity> {
    private final TransferredPointsRepository repository;
    private List<TransferredPointsEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<TransferredPointsEntity> entityHandler;

    @Autowired
    public TransferredPointsServices(
            final TransferredPointsRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<TransferredPointsEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public TransferredPointsEntity created(final TransferredPointsEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<TransferredPointsEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<TransferredPointsEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
        }
        return dataCash;
    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), TransferredPointsEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.TRANSFERRED_POINTS_TABLE.getName();
    }

    @Override
    public TransferredPointsEntity findById(final String id) {
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
        return Arrays.stream(TransferredPointsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public TransferredPointsEntity getEmptyEntity() {
        return new TransferredPointsEntity();
    }

    @Override
    public boolean existsById(final String id) {
        return repository.existsById(Long.parseLong(id));
    }
}
