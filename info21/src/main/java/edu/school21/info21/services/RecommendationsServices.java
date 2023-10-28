package edu.school21.info21.services;

import edu.school21.info21.entities.RecommendationsEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.CashHandler;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("recommendations")
public class RecommendationsServices implements EduService<RecommendationsEntity> {
    private final RecommendationsRepository repository;
    private List<RecommendationsEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<RecommendationsEntity> entityHandler;

    @Autowired
    public RecommendationsServices(
            final RecommendationsRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<RecommendationsEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public RecommendationsEntity created(final RecommendationsEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<RecommendationsEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<RecommendationsEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
        }
        return dataCash;    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), RecommendationsEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.RECOMMENDATIONS_TABLE.getName();
    }

    @Override
    public RecommendationsEntity findById(final String id) {
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
        return Arrays.stream(RecommendationsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public RecommendationsEntity getEmptyEntity() {
        return new RecommendationsEntity();
    }

    @Override
    public boolean existsById(final String id) {
        return repository.existsById(Long.parseLong(id));
    }
}
