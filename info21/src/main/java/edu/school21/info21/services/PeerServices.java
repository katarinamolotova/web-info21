package edu.school21.info21.services;

import edu.school21.info21.entities.PeerEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.PeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PeerServices implements EduService<PeerEntity, String> {
    private final PeerRepository repository;

    @Override
    public PeerEntity created(PeerEntity entity) {
        return repository.save(entity);
    }

    @Override
    public PeerEntity update(PeerEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<PeerEntity> findAll() {
        return (List<PeerEntity>)repository.findAll();
    }

    @Override
    public PeerEntity findById(String id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(PeerEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
