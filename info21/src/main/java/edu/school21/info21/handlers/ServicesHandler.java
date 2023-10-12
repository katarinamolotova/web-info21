package edu.school21.info21.handlers;

import edu.school21.info21.services.EduService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Objects;

@Component
@AllArgsConstructor
public class ServicesHandler {
    private final HashMap<String, EduService> services = new HashMap<>();

    public void registry(String serviceName, EduService service) {
        Object result = services.putIfAbsent(serviceName, service);
        if (!Objects.isNull(result))
            throw new IllegalArgumentException("Wrong service name");
    }

    public EduService getService (String serviceName) {
        return services.get(serviceName);
    }
}
