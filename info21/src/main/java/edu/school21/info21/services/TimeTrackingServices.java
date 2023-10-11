package edu.school21.info21.services;

import edu.school21.info21.entities.TimeTrackingEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.TimeTrackingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TimeTrackingServices implements EduService<TimeTrackingEntity, Long> {
    private final TimeTrackingRepository repository;

    @Override
    public TimeTrackingEntity created(TimeTrackingEntity entity) {
        return repository.save(entity);
    }

    @Override
    public TimeTrackingEntity update(TimeTrackingEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<TimeTrackingEntity> findAll() {
        return (List<TimeTrackingEntity>)repository.findAll();
    }

    @Override
    public TimeTrackingEntity findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(TimeTrackingEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());    }
}
