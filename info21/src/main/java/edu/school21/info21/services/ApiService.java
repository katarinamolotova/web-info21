package edu.school21.info21.services;

import edu.school21.info21.exceptions.ApiWrongParameter;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.ServicesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;


@Service
@AllArgsConstructor
public class ApiService {
    private final ServicesHandler servicesHandler;

    public String findById(final String table, final String id) {
        try {
            return servicesHandler.getService(table).findById(id).toString();
        } catch (ApiWrongParameter e) {
            return "table not found";
        } catch (NumberFormatException e) {
            return "Id for this table must be Long format";
        } catch (NotFoundEntity e) {
            return "Entity not found";
        }
    }

    public Object findByIdObject(final String table, final String id) {
        try {
            return servicesHandler.getService(table).findById(id);
        } catch (ApiWrongParameter e) {
            return "table not found";
        } catch (NumberFormatException e) {
            return "Id for this table must be Long format";
        } catch (NotFoundEntity e) {
            return "Entity not found";
        }
    }

    public List findAllAsString(final String table) {
        return servicesHandler.getService(table).findAllAsString();
    }

    public String findAll(final String table) {
        try {
            return servicesHandler.getService(table).findAll().toString();
        } catch (ApiWrongParameter e) {
            return "table not found";
        }
    }

    public List findAllObjects(final String table) {
        try {
            return servicesHandler.getService(table).findAll();
        } catch (ApiWrongParameter e) {
            return Collections.emptyList();
        }
    }

    public Object created(final Object entity, final String table) {
        return servicesHandler.getService(table).created(entity);
    }

    public Object update(final Object entity, final String table) {
        servicesHandler.getService(table);
        return servicesHandler.getService(table).update(entity);
    }

    public void delete(final String id, final String table) {
        servicesHandler.getService(table).delete(id);
    }

    public List getHeaderForTable(final String table) {
        return servicesHandler.getService(table).getHeaderForTable();
    }

    public Object getEmptyEntity(final String table) {
        return servicesHandler.getService(table).getEmptyEntity();
    }
}
