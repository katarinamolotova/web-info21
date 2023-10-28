package edu.school21.info21.services.context;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Контекст для отправки POST запросов на выполнение функций
 */
@Data
public class FunctionContext {

    private List<String> stringParameters = new ArrayList<>(3);

    private List<Integer> intParameters;

    private LocalTime time;

    private LocalDate date;

    public FunctionContext() {
        intParameters = new ArrayList<>(List.of(1, 1));
    }
}
