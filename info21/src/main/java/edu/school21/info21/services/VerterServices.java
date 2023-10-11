package edu.school21.info21.services;

import edu.school21.info21.entities.VerterEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.VerterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VerterServices implements EduService<VerterEntity, Long> {
    private final VerterRepository repository;
    private List<VerterEntity> dataCash;
    private boolean isChanged;

    @Override
    public VerterEntity created(VerterEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public VerterEntity update(VerterEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<VerterEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<VerterEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public VerterEntity findById(Long id) {
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
        return Arrays.stream(VerterEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
