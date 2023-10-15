package edu.school21.info21.handlers;

import edu.school21.info21.exceptions.ApiWrongParameter;
import edu.school21.info21.services.EduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public record ServicesHandler(Map<String, EduService> services) {
    @Autowired
    public ServicesHandler {}

    public void registry(String serviceName, EduService service) {
        Object result = services.putIfAbsent(serviceName, service);
        if (!Objects.isNull(result)) {
            throw new IllegalArgumentException("Wrong service name");
        }
    }

    public EduService getService(String serviceName) {
        return Optional.ofNullable(services.get(serviceName)).orElseThrow(ApiWrongParameter::new);
    }
}
