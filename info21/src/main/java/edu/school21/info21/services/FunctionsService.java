package edu.school21.info21.services;

import edu.school21.info21.repositories.FunctionsRepository;
import jdk.jfr.Description;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class FunctionsService {
    private final Map<String, String> methodsNameToDescription = new TreeMap<>();
    private FunctionsRepository repository;

    //  список функций?
    public Map<String, String> getMethodsNameAndDescription() {
        if (!methodsNameToDescription.isEmpty()) {
            return methodsNameToDescription;
        }

        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        for (final Method m : methods) {
            if (m.isAnnotationPresent(Name.class) && m.isAnnotationPresent(Description.class)) {
                final Name name = m.getAnnotation(Name.class);
                final Description description = m.getAnnotation(Description.class);
                methodsNameToDescription.put(name.value(), description.value());
            }
        }
        return methodsNameToDescription;
    }
}
