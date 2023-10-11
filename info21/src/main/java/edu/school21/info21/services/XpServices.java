package edu.school21.info21.services;

import edu.school21.info21.entities.XpEntity;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.repositories.XpRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class XpServices implements EduService<XpEntity, Long> {
    private final XpRepository repository;
    private List<XpEntity> dataCash;
    private boolean isChanged;

    @Override
    public XpEntity created(XpEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public XpEntity update(XpEntity entity) {
        this.isChanged = true;
        return repository.save(entity);
    }

    @Override
    public List<XpEntity> findAll() {
        if(isChanged || dataCash.isEmpty()) {
            this.dataCash = (List<XpEntity>) repository.findAll();
            this.isChanged = false;
        }
        return dataCash;    }

    @Override
    public XpEntity findById(Long id) {
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
        return Arrays.stream(XpEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
