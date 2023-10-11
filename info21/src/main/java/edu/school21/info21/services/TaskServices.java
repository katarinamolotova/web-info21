package edu.school21.info21.services;

import edu.school21.info21.entities.TaskEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TaskServices implements EduService<TaskEntity, String> {
    private final TaskRepository repository;

    @Override
    public TaskEntity created(TaskEntity entity) {
        return repository.save(entity);
    }

    @Override
    public TaskEntity update(TaskEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<TaskEntity> findAll() {
        return (List<TaskEntity>)repository.findAll();
    }

    @Override
    public TaskEntity findById(String id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(TaskEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
