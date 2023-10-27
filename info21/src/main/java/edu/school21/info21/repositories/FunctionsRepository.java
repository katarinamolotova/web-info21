package edu.school21.info21.repositories;

import edu.school21.info21.annotations.Name;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class FunctionsRepository {
    private static final String TRANSFERRED_POINTS_FROM_PEERS = "SELECT * FROM transferred_points_form()";
    private static final String SUCCESSFULLY_COMPLETED_PROJECTS = "SELECT * FROM successfully_completed_projects()";
    private static final String PEERS_DID_NOT_COME_OUT = "SELECT * FROM peers_did_not_come_out(:date)";
    private static final String CHANGING_PEERS_POINTS_V1 = "SELECT * FROM changing_peer_points()";
    private static final String CHANGING_PEERS_POINTS_V2 = "SELECT * FROM changing_peer_points_from_func()";
    private static final String MOST_CHECKED_TASK = "SELECT * FROM most_checked_task()";
    private static final String PEERS_ARE_COMPLETING_BLOCK = "SELECT * FROM peers_and_completed_blocks(:block)";
    private static final String RECOMMENDED_CHECKING_PEER = "SELECT * FROM recommended_checking_peer_for_each_peer()";
    private static final String PERCENT_PEERS_DOING_BLOCKS = "SELECT * FROM percent_peers_doing_projects(:block1, :block2)";
    private static final String SUCCESSFUL_CHECKS_ON_BIRTHDAY = "SELECT * FROM successful_checks_on_birthday()";
    private static final String PEERS_AND_THREE_PROJECTS = "SELECT * FROM peers_and_projects(:task1, :task2, :task3)";
    private static final String AMOUNT_OF_PREVIOUS_TASKS = "SELECT * FROM amount_of_previous_task()";
    private static final String LUCKY_DAYS_FOR_CHECKING = "SELECT * FROM luck_days(:amount)";
    private static final String PEER_WITH_MOST_AMOUNT_OF_XP = "SELECT * FROM peer_with_the_most_xp()";
    private static final String PEERS_WHO_CAME_BEFORE = "SELECT * FROM determine_who_came_before_time(:time, :amount)";
    private static final String PEERS_WHO_CAME_OUT_MORE = "SELECT * FROM peers_who_came_out_more(:day, :amount)";
    private static final String PERCENT_OF_EARLY_ENTRIES = "SELECT * FROM percent_early_entries()";

    private EntityManager entityManager;
    //  получение имен столбцов?

    public List doNativeQueryByQuery(final Query query) {
        return query.getResultList();
    }

    @Name(valueRu = "00. Нативный запрос", valueEn = "00_native_query")
    @Description("Блок с возможностью самостоятельного ввода SQL-запроса для работы с данными в базе")
    public List doNativeQueryByString(final String query) {
        return entityManager.createNativeQuery(query)
                            .getResultList();
    }

    @Name(valueRu = "01. Переданные PRP", valueEn = "01_transferred_points_from")
    @Description("Функция, возвращающая таблицу TransferredPoints в более человеко читаемом виде")
    public List transferredPointsFromPeers() {
        return doNativeQueryByString(TRANSFERRED_POINTS_FROM_PEERS);
    }

    @Name(valueRu = "02. Успешно выполненные проекты", valueEn = "02_successfully_completed_projects")
    @Description("Функция, которая возвращает таблицу вида: ник, название проверенного задания и кол-ва полученного ХР. " +
                 "Только для успешно пройденных проектов.")
    public List successfullyCompletedProjects() {
        return doNativeQueryByString(SUCCESSFULLY_COMPLETED_PROJECTS);
    }

    @Name(valueRu = "03. Не выходившие пиры", valueEn = "03_peers_did_not_come_out")
    @Description("Функция, определяющая пиров, которые не выходили из кампуса в течение всего дня")
    public List peersAreNotLeavingSchoolOnDate(final LocalDate date) {
        final Query query = entityManager.createNativeQuery(PEERS_DID_NOT_COME_OUT)
                                         .setParameter("date", date);
        return doNativeQueryByQuery(query);
    }

    @Name(valueRu = "04. Изменения PRP v1", valueEn = "04_changing_peer_points")
    @Description("Функция, определяющая изменения в кол-ве PRP каждого пира по таблице TransferredPoints")
    public List changingPeersPointsV1() {
        return doNativeQueryByString(CHANGING_PEERS_POINTS_V1);
    }

    @Name(valueRu = "05. Изменения PRP v2", valueEn = "05_changing_peer_points_from_func")
    @Description("Функция, определяющая изменения в кол-ве PRP каждого пира по таблице из функции \"Переданные PRP\"")
    public List changingPeersPointsV2() {
        return doNativeQueryByString(CHANGING_PEERS_POINTS_V2);
    }

    // TODO empty result
    @Name(valueRu = "06. Часто проверяемое задание", valueEn = "06_most_checked_task")
    @Description("Функция, определяющая самое часто проверяемое задание за каждый день")
    public List mostCheckedTask() {
        return doNativeQueryByString(MOST_CHECKED_TASK);
    }

    @Name(valueRu = "07. Пиры, выполнившие блок", valueEn = "07_peers_and_completed_blocks")
    @Description("Функция, определяющая пиров, выполнивших весь блок задач и дату завершения последнего задания")
    public List peersAreCompletingBlocks(final String block) {
        final Query query = entityManager.createNativeQuery(PEERS_ARE_COMPLETING_BLOCK)
                                         .setParameter("block", block);
        return doNativeQueryByQuery(query);
    }

    @Name(valueRu = "08. Рекомендации по проверяющим", valueEn = "08_recommended_checking_peer_for_each_peer")
    @Description("Функция, определяющая к какому пиру стоит идти на проверку каждому проверяющему")
    public List recommendedCheckingPeerForEachPeer() {
        return doNativeQueryByString(RECOMMENDED_CHECKING_PEER);
    }

    @Name(valueRu = "09. Процент пиров по блокам заданий", valueEn = "09_percent_peers_doing_projects")
    @Description("Функция, определяющая процент пиров, которые только приступили к первому и второму блоку заданий, " +
                 "приступили к обоим блокам и не приступили ни к одному блоку")
    public List percentPeersDoingProjectBlocks(final String block1, final String block2) {
        final Query query = entityManager.createNativeQuery(PERCENT_PEERS_DOING_BLOCKS)
                                         .setParameter("block1", block1)
                                         .setParameter("block2", block2);
        return doNativeQueryByQuery(query);
    }

    // TODO division by zero
    @Name(valueRu = "10. Успешные проверки в день рождения", valueEn = "10_successful_checks_on_birthday")
    @Description("Функция, определяющая процент пиров, которые когда-либо успешно проходили проверку в свой день рождения")
    public List successfulChecksOnBirthday() {
        return doNativeQueryByString(SUCCESSFUL_CHECKS_ON_BIRTHDAY);
    }

    @Name(valueRu = "11. Пиры по заданиям", valueEn = "11_peers_and_projects")
    @Description("Функция, определяющая пиров, которые сдали задания 1 и 2, но не сдали задание 3")
    public List peersAndThreeProjects(final String task1, final String task2, final String task3) {
        final Query query = entityManager.createNativeQuery(PEERS_AND_THREE_PROJECTS)
                                         .setParameter("task1", task1)
                                         .setParameter("task2", task2)
                                         .setParameter("task3", task3);
        return doNativeQueryByQuery(query);
    }

    @Name(valueRu = "12. Предшествующие задания", valueEn = "12_amount_of_previous_task")
    @Description("Функция, рекурсивно определяющая для каждого задания кол-во предшествующих ему заданий")
    public List amountOfPreviousTasks() {
        return doNativeQueryByString(AMOUNT_OF_PREVIOUS_TASKS);
    }

    @Name(valueRu = "13. Удачные для проверок дни", valueEn = "13_luck_days")
    @Description("Функция, определяющая удачные для проверок дни. День считается удачным, если в нем есть хотя бы" +
                 "N идущих подряд успешных проверок.")
    public List luckyDaysForChecking(final Integer amount) {
        final Query query = entityManager.createNativeQuery(LUCKY_DAYS_FOR_CHECKING)
                                         .setParameter("amount", amount);
        return doNativeQueryByQuery(query);
    }

    @Name(valueRu = "14. Пир с наибольшим кол-вом опыта", valueEn = "14_peer_with_the_most_xp")
    @Description("Функция, определяющая пира с наибольшим кол-вом опыта")
    public List peerWithTheMostAmountOfXp() {
        return doNativeQueryByString(PEER_WITH_MOST_AMOUNT_OF_XP);
    }

    @Name(valueRu = "15. Пиры, приходившие раньше времени", valueEn = "15_determine_who_came_before_time")
    @Description("Функция, определяющая пиров, приходивших раньше заданного времени не менее N раз за все время")
    public List peersWhoCameBefore(final LocalTime time, final Integer amount) {
        final Query query = entityManager.createNativeQuery(PEERS_WHO_CAME_BEFORE)
                                         .setParameter("time", time)
                                         .setParameter("amount", amount);
        return doNativeQueryByQuery(query);
    }

    @Name(valueRu = "16. Пиры, выходившие из кампуса", valueEn = "16_peers_who_came_out_more")
    @Description("Функция, определяющая пиров, выходивших за последние N дней из кампуса не больше М раз")
    public List peersWhoCameOutMore(final Integer day, final Integer amount) {
        final Query query = entityManager.createNativeQuery(PEERS_WHO_CAME_OUT_MORE)
                                         .setParameter("day", day)
                                         .setParameter("amount", amount);
        return doNativeQueryByQuery(query);
    }

    @Name(valueRu = "17. Процент ранних входов", valueEn = "17_percent_early_entries")
    @Description("Функция, определяющая для каждого месяца процент ранних входов. Ранним считается вход до 12:00.")
    public List percentOfEarlyEntries() {
        return doNativeQueryByString(PERCENT_OF_EARLY_ENTRIES);
    }

}
