package edu.school21.info21.services;

import edu.school21.info21.entities.TransferredPointsEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.TransferredPointsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransferredPointsServices implements EduService<TransferredPointsEntity, Long> {
    private final TransferredPointsRepository repository;

    @Override
    public TransferredPointsEntity created(TransferredPointsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public TransferredPointsEntity update(TransferredPointsEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<TransferredPointsEntity> findAll() {
        return (List<TransferredPointsEntity>)repository.findAll();
    }

    @Override
    public TransferredPointsEntity findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(TransferredPointsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());    }
}
