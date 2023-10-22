package edu.school21.info21.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jdk.jfr.Description;
import jdk.jfr.Name;
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

    @Name("00. Нативный запрос")
    @Description("Блок с возможностью самостоятельного ввода SQL-запроса для работы с данными в базе")
    public List doNativeQueryByString(final String query) {
        return entityManager.createNativeQuery(query)
                            .getResultList();
    }

    public List doNativeQueryByQuery(final Query query) {
        return query.getResultList();
    }

    @Name("01. Переданные PRP")
    @Description("Функция, возвращающая таблицу TransferredPoints в более человеко читаемом виде")
    public List transferredPointsFromPeers() {
        return doNativeQueryByString(TRANSFERRED_POINTS_FROM_PEERS);
    }

    @Name("02. Успешно выполненные проекты")
    @Description("Функция, которая возвращает таблицу вида: ник, название проверенного задания и кол-ва полученного ХР." +
                 "Только для успешно пройденных проектов")
    public List successfullyCompletedProjects() {
        return doNativeQueryByString(SUCCESSFULLY_COMPLETED_PROJECTS);
    }

    @Name("03. Не выходившие пиры")
    @Description("Функция, определяющая пиров, которые не выходили из кампуса в течение всего дня")
    public List peersAreNotLeavingSchoolOnDate(final LocalDate date) {
        final Query query = entityManager.createNativeQuery(PEERS_DID_NOT_COME_OUT)
                                         .setParameter("date", date);
        return doNativeQueryByQuery(query);
    }

    @Name("04. Изменения PRP v1")
    @Description("Функция, определяющая изменения в кол-ве PRP каждого пира по таблице TransferredPoints")
    public List changingPeersPointsV1() {
        return doNativeQueryByString(CHANGING_PEERS_POINTS_V1);
    }

    @Name("05. Изменения PRP v2")
    @Description("Функция, определяющая изменения в кол-ве PRP каждого пира по таблице из функции \"Переданные PRP\"")
    public List changingPeersPointsV2() {
        return doNativeQueryByString(CHANGING_PEERS_POINTS_V2);
    }

    @Name("06. Часто проверяемое задание")
    @Description("Функция, определяющая самое часто проверяемое задание за каждый день")
    public List mostCheckedTask() {
        return doNativeQueryByString(MOST_CHECKED_TASK);
    }

    @Name("07. Пиры, выполнившие блок")
    @Description("Функция, определяющая пиров, выполнивших весь блок задач и дату завершения последнего задания")
    public List peersAreCompletingBlocks(final String block) {
        final Query query = entityManager.createNativeQuery(PEERS_ARE_COMPLETING_BLOCK)
                                         .setParameter("block", block);
        return doNativeQueryByQuery(query);
    }

    @Name("08. Рекомендации по проверяющим")
    @Description("Функция, определяющая к какому пиру стоит идти на проверку каждому проверяющему")
    public List recommendedCheckingPeerForEachPeer() {
        return doNativeQueryByString(RECOMMENDED_CHECKING_PEER);
    }

    @Name("09. Процент пиров по блокам заданий")
    @Description("Функция, определяющая процент пиров, которые только приступили к первому и второму блоку заданий, " +
                 "приступили к обоим блокам и не приступили ни к одному блоку")
    public List percentPeersDoingProjectBlocks(final String block1, final String block2) {
        final Query query = entityManager.createNativeQuery(PERCENT_PEERS_DOING_BLOCKS)
                                         .setParameter("block1", block1)
                                         .setParameter("block2", block2);
        return doNativeQueryByQuery(query);
    }

    @Name("10. Успешные проверки в день рождения")
    @Description("Функция, определяющая процент пиров, которые когда-либо успешно проходили проверку в свой день рождения")
    public List successfulChecksOnBirthday() {
        return doNativeQueryByString(SUCCESSFUL_CHECKS_ON_BIRTHDAY);
    }

    @Name("11. Пиры по заданиям")
    @Description("Функция, определяющая пиров, которые сдали задания 1 и 2, но не сдали задание 3")
    public List peersAndThreeProjects(final String task1, final String task2, final String task3) {
        final Query query = entityManager.createNativeQuery(PEERS_AND_THREE_PROJECTS)
                                         .setParameter("task1", task1)
                                         .setParameter("task2", task2)
                                         .setParameter("task3", task3);
        return doNativeQueryByQuery(query);
    }

    @Name("12. Предшествующие задания")
    @Description("Функция, рекурсивно определяющая для каждого задания кол-во предшествующих ему заданий")
    public List amountOfPreviousTasks() {
        return doNativeQueryByString(AMOUNT_OF_PREVIOUS_TASKS);
    }

    @Name("13. Удачные для проверок дни")
    @Description("Функция, определяющая удачные для проверок дни. День считается удачным, если в нем есть хотя бы" +
                 "N идущих подряд успешных проверок.")
    public List luckyDaysForChecking(final String amount) {
        final Query query = entityManager.createNativeQuery(LUCKY_DAYS_FOR_CHECKING)
                                         .setParameter("amount", amount);
        return doNativeQueryByQuery(query);
    }

    @Name("14. Пир с наибольшим кол-вом опыта")
    @Description("Функция, определяющая пира с наибольшим кол-вом опыта")
    public List peerWithTheMostAmountOfXp(final String amount) {
        return doNativeQueryByString(PEER_WITH_MOST_AMOUNT_OF_XP);
    }

    @Name("15. Пиры, приходившие раньше времени")
    @Description("Функция, определяющая пиров, приходивших раньше заданного времени не менее N раз за все время")
    public List peersWhoCameBefore(final LocalTime time, final String amount) {
        final Query query = entityManager.createNativeQuery(PEERS_WHO_CAME_BEFORE)
                                         .setParameter("time", time)
                                         .setParameter("amount", amount);
        return doNativeQueryByQuery(query);
    }

    @Name("16. Пиры, выходившие из кампуса")
    @Description("Функция, определяющая пиров, выходивших за последние N дней из кампуса не больше М раз")
    public List peersWhoCameOutMore(final String day, final String amount) {
        final Query query = entityManager.createNativeQuery(PEERS_WHO_CAME_OUT_MORE)
                                         .setParameter("day", day)
                                         .setParameter("amount", amount);
        return doNativeQueryByQuery(query);
    }

    @Name("17. Процент ранних входов")
    @Description("Функция, определяющая для каждого месяца процент ранних входов. Ранним считается вход до 12:00.")
    public List percentOfEarlyEntries() {
        return doNativeQueryByString(PERCENT_OF_EARLY_ENTRIES);
    }

}
