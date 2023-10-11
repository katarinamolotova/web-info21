package edu.school21.info21.services;

import edu.school21.info21.entities.PeerEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.PeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PeerServices implements EduService<PeerEntity, String> {
    private final PeerRepository repository;
    private List<PeerEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;

    @Autowired
    public PeerServices(final PeerRepository repository) {
        this.repository = repository;
    }

    @Override
    public PeerEntity created(PeerEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public PeerEntity update(PeerEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<PeerEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<PeerEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;    }

    @Override
    public PeerEntity findById(String id) {
        if(isChanged || dataCash.isEmpty()) {
            return repository.findById(id)
                             .orElseThrow(NotFoundEntity::new);
        } else {
            return dataCash.stream()
                           .filter( i -> Objects.equals(i.getNickname(), id))
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
        return Arrays.stream(PeerEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
