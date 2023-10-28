package edu.school21.info21.handlers;

import edu.school21.info21.exceptions.NotFoundFunction;
import edu.school21.info21.services.EduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public record ServicesHandler(Map<String, EduService> services) {
    @Autowired
    public ServicesHandler {}

    public EduService getService(String serviceName) {
        return Optional.ofNullable(services.get(serviceName))
                       .orElseThrow(NotFoundFunction::new);
    }
}
