package edu.school21.info21.services;

import edu.school21.info21.exceptions.ApiWrongParameter;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.ServicesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ApiService {
    private final ServicesHandler servicesHandler;

    public String findById(final String base, final String id) {
        try {
            return servicesHandler.getService(base).findById(id).toString();
        } catch (ApiWrongParameter e) {
            return "Base not found";
        } catch (NumberFormatException e) {
            return "Id for this base must be Long format";
        } catch (NotFoundEntity e) {
            return "Entity not found";
        }
    }

    public List findAllAsString(final String base) {
        return servicesHandler.getService(base).findAllAsString();
    }

    public String findAll(final String base) {
        try {
            return servicesHandler.getService(base).findAll().toString();
        } catch (ApiWrongParameter e) {
            return "base not found";
        }
    }

    public Object created(final Object entity, final String base) {
        return servicesHandler.getService(base).created(entity);
    }

    public Object update(final Object entity, final String base) {
        servicesHandler.getService(base);
        return servicesHandler.getService(base).update(entity);
    }

    public void delete(final String id, final String base) {
        servicesHandler.getService(base).delete(id);
    }

    public List getHeaderForTable(final String base) {
        return servicesHandler.getService(base).getHeaderForTable();
    }

}
