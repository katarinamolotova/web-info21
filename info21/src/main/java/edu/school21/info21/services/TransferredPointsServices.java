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
    private List<TransferredPointsEntity> dataCash;
    private boolean isChanged;

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
                     .collect(Collectors.toList());    }
}
