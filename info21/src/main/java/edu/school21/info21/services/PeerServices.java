package edu.school21.info21.services;

import edu.school21.info21.entities.PeerEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.CashHandler;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("peers")
public class PeerServices implements EduService<PeerEntity> {
    private final PeerRepository repository;
    private List<PeerEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<PeerEntity> entityHandler;


    @Autowired
    public PeerServices(
            final PeerRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<PeerEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public PeerEntity created(Object entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save((PeerEntity)entity);
    }

    @Override
    public PeerEntity update(PeerEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<PeerEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<PeerEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
        }
        return dataCash;    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), PeerEntity.class );
    }

    @Override
    public String getTableName() {
        return TableNames.PEERS_TABLE.getName();
    }

    @Override
    public PeerEntity findById(String id) {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            return repository.findById(id)
                             .orElseThrow(NotFoundEntity::new);
        } else {
            return dataCash.stream()
                           .filter( i -> Objects.equals(i.getNickname(), id))
                           .findFirst()
                           .orElseThrow(NotFoundEntity::new);
        }
    }

    @Override
    public void delete(String id) {
        try {
            repository.deleteById(id);
            cashHandler.globalChanges();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(PeerEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public PeerEntity getEmptyEntity() {
        return new PeerEntity();
    }
}
