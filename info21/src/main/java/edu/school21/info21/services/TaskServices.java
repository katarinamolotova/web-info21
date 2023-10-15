package edu.school21.info21.services;

import edu.school21.info21.entities.TaskEntity;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.CashHandler;
import edu.school21.info21.handlers.EntityHandler;
import edu.school21.info21.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("tasks")
public class TaskServices implements EduService<TaskEntity> {
    private final TaskRepository repository;
    private List<TaskEntity> dataCash = new ArrayList<>();
    private final CashHandler cashHandler;
    private final UUID uuid;
    private final EntityHandler<TaskEntity> entityHandler;

    @Autowired
    public TaskServices(
            final TaskRepository repository,
            final CashHandler cashHandler,
            final EntityHandler<TaskEntity> entityHandler
    ) {
        this.repository = repository;
        this.cashHandler = cashHandler;
        this.uuid = cashHandler.registry();
        this.entityHandler = entityHandler;
    }

    @Override
    public TaskEntity created(TaskEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public TaskEntity update(TaskEntity entity) {
        cashHandler.localChanges(uuid, true);
        return repository.save(entity);
    }

    @Override
    public List<TaskEntity> findAll() {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            this.dataCash = (List<TaskEntity>) repository.findAll();
            cashHandler.localChanges(uuid, false);
        }
        return dataCash;
    }

    @Override
    public List<List<String>> findAllAsString() {
        return entityHandler.mapEntitiesToListString(findAll(), TaskEntity.class);
    }

    @Override
    public String getTableName() {
        return TableNames.TASKS_TABLE.getName();
    }

    @Override
    public TaskEntity findById(String id) {
        if(cashHandler.changesById(uuid) || dataCash.isEmpty()) {
            return repository.findById(id)
                             .orElseThrow(NotFoundEntity::new);
        } else {
            return dataCash.stream()
                           .filter( i -> Objects.equals(i.getTitle(), id))
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
        return Arrays.stream(TaskEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }

    @Override
    public TaskEntity getEmptyEntity() {
        return new TaskEntity();
    }
}
