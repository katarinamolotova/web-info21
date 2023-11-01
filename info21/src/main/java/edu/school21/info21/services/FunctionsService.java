package edu.school21.info21.services;

import edu.school21.info21.annotations.FunctionInfo;
import edu.school21.info21.exceptions.NotFoundFunction;
import edu.school21.info21.repositories.FunctionsRepository;
import edu.school21.info21.services.context.FunctionContext;
import edu.school21.info21.services.handlers.CashHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Slf4j
@Service
public class FunctionsService {
    private final Map<String, String> methodsNameToDescription = new TreeMap<>();
    private final Map<String, String> methodsEnNameToRuName = new TreeMap<>();
    private List<Object[]> lastResult = new ArrayList<>();
    private final CashHandler cashHandler;
    private final FunctionsRepository repository;

    @Autowired
    public FunctionsService(final CashHandler cashHandler, final FunctionsRepository repository) {
        this.cashHandler = cashHandler;
        this.repository = repository;
    }

    public List executeFunction(final String funcName, final FunctionContext context) {
        lastResult = switch (funcName) {
            case "00_native_query" -> doNativeQuery(context);
            case "03_peers_did_not_come_out" -> peersAreNotLeavingSchoolOnDate(context);
            case "07_peers_and_completed_blocks" -> peersAreCompletingBlocks(context);
            case "09_percent_peers_doing_projects" -> percentPeersDoingProjectBlocks(context);
            case "11_peers_and_projects" -> peersAndThreeProjects(context);
            case "13_luck_days" -> luckyDaysForChecking(context);
            case "15_determine_who_came_before_time" -> peersWhoCameBefore(context);
            case "16_peers_who_came_out_more" -> peersWhoCameOutMore(context);
            default -> executeFunctionWithoutParameters(funcName);
        };
        cashHandler.globalChanges();
        return lastResult;
    }

    private List doNativeQuery(final FunctionContext context) {
        return repository.doNativeQueryByString(context.getStringParameters().get(0));
    }

    private List peersAreNotLeavingSchoolOnDate(final FunctionContext context) {
        return repository.peersAreNotLeavingSchoolOnDate(context.getDate());
    }

    private List peersAreCompletingBlocks(final FunctionContext context) {
        return repository.peersAreCompletingBlocks(context.getStringParameters().get(0));
    }

    private List percentPeersDoingProjectBlocks(final FunctionContext context) {
        return repository.percentPeersDoingProjectBlocks(
                context.getStringParameters().get(0),
                context.getStringParameters().get(1)
        );
    }

    private List peersAndThreeProjects(final FunctionContext context) {
        return repository.peersAndThreeProjects(
                context.getStringParameters().get(0),
                context.getStringParameters().get(1),
                context.getStringParameters().get(2)
        );
    }

    private List luckyDaysForChecking(final FunctionContext context) {
        return repository.luckyDaysForChecking(context.getIntParameters().get(0));
    }

    private List peersWhoCameBefore(final FunctionContext context) {
        return repository.peersWhoCameBefore(context.getTime(), context.getIntParameters().get(0));
    }

    private List peersWhoCameOutMore(final FunctionContext context) {
        return repository.peersWhoCameOutMore(
                context.getIntParameters().get(0),
                context.getIntParameters().get(1)
        );
    }

    private List executeFunctionWithoutParameters(final String funcName) {
        final Method method = getMethodByName(funcName);
        if (method != null) {
            try {
                return (List) method.invoke(repository);
            } catch (final Exception e) {
                log.warn("Attempt to call a non-existent function {}", funcName);
            }
        }
        return null;
    }

    private Method getMethodByName(final String name) {
        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        return Arrays.stream(methods)
              .filter(method -> method.isAnnotationPresent(FunctionInfo.class))
              .filter(method -> Objects.equals(method.getAnnotation(FunctionInfo.class).nameEn(), name))
              .findAny()
              .orElse(null);
    }

    public String[] getColumnsNameByFuncName(final String funcName) {
        try {
            return getMethodByName(funcName).getAnnotation(FunctionInfo.class).columnsName();
        } catch (final Exception e) {
            throw new NotFoundFunction();
        }
    }

    public String getDescriptionForMethod(final String funcName) {
        if (methodsNameToDescription.isEmpty()) {
            getMethodsNameAndDescription();
        }
        return methodsNameToDescription.get(funcName);
    }

    public Map<String, String> getMethodsEnNameToRuName() {
        if (!methodsEnNameToRuName.isEmpty()) {
            return methodsEnNameToRuName;
        }

        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        for (final Method m : methods) {
            if (m.isAnnotationPresent(FunctionInfo.class)) {
                final FunctionInfo functionInfo = m.getAnnotation(FunctionInfo.class);
                methodsEnNameToRuName.put(functionInfo.nameEn(), functionInfo.nameRu());
            }
        }
        return methodsEnNameToRuName;
    }

    private void getMethodsNameAndDescription() {
        final Method[] methods =  FunctionsRepository.class.getDeclaredMethods();
        for (final Method m : methods) {
            if (m.isAnnotationPresent(FunctionInfo.class)) {
                final FunctionInfo functionInfo = m.getAnnotation(FunctionInfo.class);
                methodsNameToDescription.put(functionInfo.nameEn(), functionInfo.description());
            }
        }
    }

    protected List getLastResult() {
        return this.lastResult;
    }
}
