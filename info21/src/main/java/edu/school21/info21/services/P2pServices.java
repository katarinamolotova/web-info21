package edu.school21.info21.services;

import edu.school21.info21.entities.P2pEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.P2pRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class P2pServices implements EduService<P2pEntity, Long> {
    private final P2pRepository repository;

    @Override
    public P2pEntity created(P2pEntity entity) {
        return repository.save(entity);
    }

    @Override
    public P2pEntity update(P2pEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<P2pEntity> findAll() {
        return (List<P2pEntity>)repository.findAll();
    }

    @Override
    public P2pEntity findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(P2pEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());    }
}
