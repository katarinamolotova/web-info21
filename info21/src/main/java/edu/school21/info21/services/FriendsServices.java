package edu.school21.info21.services;

import edu.school21.info21.entities.FriendsEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.FriendsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendsServices implements EduService<FriendsEntity, Long> {
    private final FriendsRepository repository;
    private List<FriendsEntity> dataCash = new ArrayList<>();
    private boolean isChanged = true;

    @Autowired
    public FriendsServices(final FriendsRepository repository) {
        this.repository = repository;
    }

    @Override
    public FriendsEntity created(FriendsEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public FriendsEntity update(FriendsEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<FriendsEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<FriendsEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;
    }

    @Override
    public FriendsEntity findById(Long id) {
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
        return Arrays.stream(FriendsEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());

    }
}
