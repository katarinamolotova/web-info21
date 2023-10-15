package edu.school21.info21.services;

import edu.school21.info21.exceptions.ApiWrongParameter;
import edu.school21.info21.exceptions.NotFoundEntity;
import edu.school21.info21.handlers.JsonHandler;
import edu.school21.info21.handlers.ServicesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ApiService {
    private final JsonHandler handler;
    private final ServicesHandler servicesHandler;

    public String findAll(final String base) {
        try {
            return servicesHandler.getService(base).findAll().toString();
        } catch (ApiWrongParameter e) {
            return "base not found";
        }
    }

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

    public String doOperation(final String base,
                              final String operation,
                              final String id
    ){
        return "varible 2";
    }

}
