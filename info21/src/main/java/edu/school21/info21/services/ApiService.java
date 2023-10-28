package edu.school21.info21.services;

import edu.school21.info21.entities.EntityInfo;
import edu.school21.info21.handlers.ServicesHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ApiService {
    private final ServicesHandler servicesHandler;

    public Object findByIdObject(final String table, final String id) {
        log.info("Get entity from {} by id = {}", table, id);
        return servicesHandler.getService(table).findById(id);
    }

    public List findAllAsString(final String table) {
        log.info("Get all data from {}", table);
        return servicesHandler.getService(table).findAllAsString();
    }

    public List findAllObjects(final String table) {
        log.info("Get all data from {}", table);
        return servicesHandler.getService(table).findAll();
    }

    public EntityInfo created(final EntityInfo entity, final String table) {
        log.info("Create entity in {}", table);
        return (EntityInfo) servicesHandler.getService(table).created(entity);
    }

    public void delete(final String id, final String table) {
        log.info("Delete entity with id = {} from {}", id, table);
        servicesHandler.getService(table).delete(id);
    }

    public boolean existsById(final String table, final String id) {
        log.info("Checking existence of entity from {} with id = {}", table, id);
        return servicesHandler.getService(table).existsById(id);
    }

    public List getHeaderForTable(final String table) {
        return servicesHandler.getService(table).getHeaderForTable();
    }

    public Object getEmptyEntity(final String table) {
        return servicesHandler.getService(table).getEmptyEntity();
    }
}
