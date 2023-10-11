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

    @Override
    public XpEntity created(XpEntity entity) {
        return repository.save(entity);
    }

    @Override
    public XpEntity update(XpEntity entity) {
        return repository.save(entity);
    }

    @Override
    public List<XpEntity> findAll() {
        return (List<XpEntity>)repository.findAll();
    }

    @Override
    public XpEntity findById(Long id) {
        return repository.findById(id).orElseThrow(NotFoundEntity::new);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<String> getHeaderForTable() {
        return Arrays.stream(XpEntity.class.getDeclaredFields())
                     .map(Field::getName)
                     .collect(Collectors.toList());
    }
}
