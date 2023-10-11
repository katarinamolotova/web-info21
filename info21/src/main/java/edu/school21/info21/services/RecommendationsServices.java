package edu.school21.info21.services;

import edu.school21.info21.entities.RecommendationsEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.RecommendationsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommendationsServices implements EduService<RecommendationsEntity, Long> {
    private final RecommendationsRepository repository;
    private List<RecommendationsEntity> dataCash;
    private boolean isChanged;

    @Override
    public RecommendationsEntity created(RecommendationsEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public RecommendationsEntity update(RecommendationsEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<RecommendationsEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<RecommendationsEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;    }

    @Override
    public RecommendationsEntity findById(Long id) {
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
        return Arrays.stream(RecommendationsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}