package edu.school21.info21.services;

import edu.school21.info21.entities.P2pEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.P2pRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class P2pServices implements EduService<P2pEntity, Long> {
    private final P2pRepository repository;
    private List<P2pEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;

    @Autowired
    public P2pServices(P2pRepository repository) {
        this.repository = repository;
    }

    @Override
    public P2pEntity created(P2pEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public P2pEntity update(P2pEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<P2pEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<P2pEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public P2pEntity findById(Long id) {
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
        return Arrays.stream(P2pEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());    }
}
