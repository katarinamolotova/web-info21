package edu.school21.info21.services;

import edu.school21.info21.entities.TransferredPointsEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.TransferredPointsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferredPointsServices implements EduService<TransferredPointsEntity, Long> {
    private final TransferredPointsRepository repository;
    private List<TransferredPointsEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;
    private final EntityHandler<TransferredPointsEntity> entityHandler;

    @Autowired
    public TransferredPointsServices(
            final TransferredPointsRepository repository,
            final EntityHandler<TransferredPointsEntity> entityHandler
    ) {
        this.repository = repository;
        this.entityHandler = entityHandler;
    }

    @Override
    public TransferredPointsEntity created(TransferredPointsEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public TransferredPointsEntity update(TransferredPointsEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<TransferredPointsEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<TransferredPointsEntity>) repository.findAll();
            this.isChanged = false;
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
    public TransferredPointsEntity findById(Long id) {
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
        return Arrays.stream(TransferredPointsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
