package edu.school21.info21.services;

import edu.school21.info21.annotations.Name;
import edu.school21.info21.repositories.FunctionsRepository;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Service
@AllArgsConstructor
public class FunctionsService {
    private final Map<String, String> methodsNameToDescription = new TreeMap<>();
    private final Map<String, String> methodsEnNameToRuName = new TreeMap<>();
    private FunctionsRepository repository;

    public List executeFunctionWithoutParameters(final String name) {
        final Method method = getMethodByName(name);
        if (method != null) {
            try {
                return (List) method.invoke(repository);
            } catch (final Exception e) {
                //  do nothing || log
            }
        }
        return null;
    }

    private Method getMethodByName(final String name) {
        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        return Arrays.stream(methods)
              .filter(method -> method.isAnnotationPresent(Name.class))
              .filter(method -> Objects.equals(method.getAnnotation(Name.class).valueEn(), name))
              .findAny()
              .orElse(null);
    }

    public String getDescriptionForMethod(final String name) {
        if (methodsNameToDescription.isEmpty()) {
            getMethodsNameAndDescription();
        }
        return methodsNameToDescription.get(name);
    }

    public Map<String, String> getMethodsEnNameToRuName() {
        if (!methodsEnNameToRuName.isEmpty()) {
            return methodsEnNameToRuName;
        }

        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        for (final Method m : methods) {
            if (m.isAnnotationPresent(Name.class)) {
                final Name name = m.getAnnotation(Name.class);
                methodsEnNameToRuName.put(name.valueEn(), name.valueRu());
            }
        }
        return methodsEnNameToRuName;
    }

    private void getMethodsNameAndDescription() {
        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        for (final Method m : methods) {
            if (m.isAnnotationPresent(Name.class) && m.isAnnotationPresent(Description.class)) {
                final Name name = m.getAnnotation(Name.class);
                final Description description = m.getAnnotation(Description.class);
                methodsNameToDescription.put(name.valueEn(), description.value());
            }
        }
    }
}
