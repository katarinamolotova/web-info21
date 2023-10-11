package edu.school21.info21.services;

import edu.school21.info21.entities.TaskEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TaskServices implements EduService<TaskEntity, String> {
    private final TaskRepository repository;
    private List<TaskEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;

    @Autowired
    public TaskServices(final TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public TaskEntity created(TaskEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public TaskEntity update(TaskEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<TaskEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<TaskEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public TaskEntity findById(String id) {
        if(isChanged || dataCash.isEmpty()) {
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
        repository.deleteById(id);
        this.isChanged = true;
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(TaskEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
