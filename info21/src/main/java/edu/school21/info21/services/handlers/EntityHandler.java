package edu.school21.info21.services.handlers;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Обработчик сущностей
 *
 * @param <T> класс сущности
 */
@Component
public class EntityHandler<T> {

    /**
     * Метод, который конвертирует все значения полей сущность в строку. Фактически переводит
     * объект сущности в список строк.
     * <p>
     * Необходим для стандартизированной передачи сущностей в шаблон HTML для отрисовки таблиц.
     *
     * @param entities список сущностей
     * @param clazz класс сущности
     * @return список, в котором лежит список со значениями полей сущности в виде строк
     */
    public List<List<String>> mapEntitiesToListString(final List<T> entities, final Class<T> clazz) {
        final Field[] fields = clazz.getDeclaredFields();
        return entities
                .stream()
                .map(entity -> getFieldsValueAsString(fields, entity))
                .collect(Collectors.toList());
    }

    /**
     * Получение значения полей сущности
     *
     * @param fields список полей сущности
     * @param entity сущность
     * @return список сущностей в виде строк
     */
    private List<String> getFieldsValueAsString(final Field[] fields, final T entity) {
        return Arrays
                .stream(fields)
                .map(field -> {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    try {
                        return String.valueOf(field.get(entity));
                    } catch (final IllegalAccessException e) {
                        return "";
                    }
                }).collect(Collectors.toList());
    }
}
