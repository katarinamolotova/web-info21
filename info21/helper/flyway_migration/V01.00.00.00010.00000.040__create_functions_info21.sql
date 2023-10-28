-- Написать функцию, которая возвращает таблицу изменений в количестве пир поинтов каждого пира
-- Ник пира 1, ник пира 2, количество переданных пир поинтов.
-- Количество отрицательное, если пир 2 получил от пира 1 больше поинтов.

CREATE OR REPLACE FUNCTION transferred_points_form()
    RETURNS TABLE (
        peer1         VARCHAR,
        peer2         VARCHAR,
        points_amount BIGINT
    )
AS
$$
SELECT t1_checking_peer, t1_checked_peer, sum(t1_sum)
FROM (SELECT checking_peer      AS t1_checking_peer,
          checked_peer       AS t1_checked_peer,
          sum(points_amount) AS t1_sum
      FROM transferred_points AS t1
      GROUP BY checking_peer, checked_peer
      UNION ALL
      SELECT checked_peer           AS t1_checked_peer,
          checking_peer          AS t1_checking_peer,
          0 - sum(points_amount) AS t1_sum
      FROM transferred_points AS t2
      GROUP BY checked_peer, checking_peer) AS t1
GROUP BY t1_checking_peer, t1_checked_peer;
$$ LANGUAGE sql;

-- 2) Написать функцию, которая возвращает таблицу вида: ник пользователя, название проверенного задания, кол-во полученного XP
-- В таблицу включать только задания, успешно прошедшие проверку (определять по таблице Checks).
-- Одна задача может быть успешно выполнена несколько раз. В таком случае в таблицу включать все успешные проверки.

CREATE OR REPLACE FUNCTION successfully_completed_projects()
    RETURNS TABLE (
        peer VARCHAR,
        task VARCHAR,
        xp   BIGINT
    )
AS
$$
SELECT checks.peer, checks.task, xp_amount
FROM checks JOIN xp x ON checks.id = x.check_id
ORDER BY checks.peer;
$$ LANGUAGE sql;

-- 3) Написать функцию, определяющую пиров, которые не выходили из кампуса в течение всего дня
-- Параметры функции: день, например 2022-12-12.

CREATE OR REPLACE FUNCTION peers_did_not_come_out(date)
    RETURNS TABLE (
        peers VARCHAR
    )
AS
$$
SELECT peer
FROM time_tracking
WHERE state = 1 AND visit_date = $1
EXCEPT
SELECT peer FROM time_tracking
WHERE state = 2 AND visit_date = $1;
$$ LANGUAGE sql;

-- 4) Посчитать изменение в количестве пир поинтов каждого пира по таблице TransferredPoints
-- Результат вывести отсортированным по изменению числа поинтов.
-- Формат вывода: ник пира, изменение в количество пир поинтов

CREATE OR REPLACE FUNCTION changing_peer_points()
    RETURNS TABLE (
        peers  VARCHAR,
        points BIGINT
    )
AS
$$
SELECT peer, sum(sum) AS points
FROM (SELECT checking_peer AS peer, sum(points_amount) AS sum
      FROM transferred_points
      GROUP BY checking_peer
      UNION ALL
      SELECT checked_peer AS peer, 0 - sum(points_amount) AS sum
      FROM transferred_points
      GROUP BY checked_peer) AS t
GROUP BY t.peer
ORDER BY 2 DESC ;
$$ LANGUAGE sql;

-- 5) Посчитать изменение в количестве пир поинтов каждого пира по таблице, возвращаемой первой функцией из Part 3
-- Результат вывести отсортированным по изменению числа поинтов.
-- Формат вывода: ник пира, изменение в количество пир поинтов

CREATE OR REPLACE FUNCTION changing_peer_points_from_func()
    RETURNS TABLE (
        peers  VARCHAR,
        points BIGINT
    )
AS
$$
SELECT peer, sum(sum) AS points
FROM (SELECT peer1 AS peer, peer2, points_amount AS sum
      FROM transferred_points_form()
      UNION
      SELECT peer2 AS peer, peer1 AS peer2, 0 - points_amount AS sum
      FROM transferred_points_form()) AS t
GROUP BY t.peer
ORDER BY 2 DESC;
$$ LANGUAGE sql;

-- 6) Определить самое часто проверяемое задание за каждый день
-- При одинаковом количестве проверок каких-то заданий в определенный день, вывести их все.
-- Формат вывода: день, название задания

CREATE OR REPLACE FUNCTION most_checked_task()
    RETURNS TABLE (
        day  DATE,
        task VARCHAR
    )
AS
$$
WITH temp AS (
    SELECT check_date, count(check_date) AS amount, t.task
    FROM (SELECT check_date::date, check_id, checks.task
          FROM p2p JOIN checks ON check_id = checks.id
          WHERE state in ('SUCCESS', 'FAILURE')) AS t
    GROUP BY check_date, check_id, t.task)
SELECT DISTINCT check_date AS day, checked.task
FROM temp AS checked
WHERE amount = (SELECT MAX(amount) FROM temp WHERE check_date = checked.check_date)
ORDER BY 1;
$$ LANGUAGE sql;

-- 7) Найти всех пиров, выполнивших весь заданный блок задач и дату завершения последнего задания
-- Параметры процедуры: название блока, например "CPP".
-- Результат вывести отсортированным по дате завершения.
-- Формат вывода: ник пира, дата завершения блока (т.е. последнего выполненного задания из этого блока)

CREATE OR REPLACE FUNCTION peers_and_completed_blocks(block VARCHAR)
    RETURNS TABLE (
        peer VARCHAR,
        day  date
    )
AS
$$
WITH tasks_in_block AS (
    SELECT title
    FROM tasks
    WHERE title SIMILAR TO block || '[0-9]%'),
    completed_tasks AS (
        SELECT peer, task, max(check_date) AS completed_date
        FROM checks
        WHERE task IN (SELECT title FROM tasks_in_block)
        GROUP BY peer, task)
SELECT peer, max(completed_date) AS last_completion_date
FROM completed_tasks
GROUP BY peer
HAVING count(DISTINCT task) = (SELECT count(*) FROM tasks_in_block)
ORDER BY last_completion_date;
$$ LANGUAGE sql;

-- 8) Определить, к какому пиру стоит идти на проверку каждому обучающемуся
-- Определять нужно исходя из рекомендаций друзей пира, т.е. нужно найти пира, проверяться у которого рекомендует наибольшее число друзей.
-- Формат вывода: ник пира, ник найденного проверяющего

CREATE OR REPLACE FUNCTION recommended_checking_peer_for_each_peer()
    RETURNS TABLE (
        peer             VARCHAR,
        recommended_peer VARCHAR
    )
AS
$$
SELECT DISTINCT ON (p.nickname) p.nickname AS peer,
    coalesce(r.recommENDed_peer, 'no friends or recommendations') AS recommENDed_peer
FROM peers AS p
         LEFT JOIN friENDs f ON p.nickname = f.peer1
         LEFT JOIN recommENDations r ON f.peer2 = r.peer AND p.nickname <> r.recommENDed_peer
GROUP BY p.nickname, r.recommENDed_peer
ORDER BY p.nickname, count(recommENDed_peer) DESC
$$ LANGUAGE sql;

-- 9) Определить процент пиров, которые:
-- Приступили только к блоку 1
-- Приступили только к блоку 2
-- Приступили к обоим
-- Не приступили ни к одному
-- Пир считается приступившим к блоку, если он проходил хоть одну проверку любого задания из этого блока (по таблице Checks)
--
-- Параметры процедуры: название блока 1, например SQL, название блока 2, например A.
-- Формат вывода: процент приступивших только к первому блоку, процент приступивших только ко второму блоку,
-- процент приступивших к обоим, процент не приступивших ни к одному

CREATE OR REPLACE FUNCTION percent_peers_doing_projects(block1 VARCHAR, block2 VARCHAR)
    RETURNS TABLE (
        started_block_1         INT,
        started_block_2         INT,
        started_both_block      INT,
        did_not_start_any_block INT
    )
AS
$$
WITH first_block AS (SELECT DISTINCT peer
                     FROM checks
                     WHERE task SIMILAR TO block1 || '[0-9]%'),
    second_block AS (SELECT DISTINCT peer
                     FROM checks
                     WHERE task SIMILAR TO block2 || '[0-9]%'),
    both_blocks AS (SELECT DISTINCT peer
                    FROM first_block
                    intersect
                    SELECT DISTINCT peer
                    FROM second_block),
    no_blocks AS (SELECT nickname AS peer
                  FROM peers
                  except
                  (SELECT DISTINCT peer
                   FROM first_block
                   UNION
                   SELECT DISTINCT peer
                   FROM second_block))

SELECT (SELECT count(peer) FROM first_block) * 100 / count(nickname)  AS started_block_1,
    (SELECT count(peer) FROM second_block) * 100 / count(nickname) AS started_block_2,
    (SELECT count(peer) FROM both_blocks) * 100 / count(nickname)  AS started_both_block,
    (SELECT count(peer) FROM no_blocks) * 100 / count(nickname)    AS did_not_start_any_block
FROM peers;
$$ LANGUAGE sql;

-- 10) Определить процент пиров, которые когда-либо успешно проходили проверку в свой день рождения
-- Также определите процент пиров, которые хоть раз проваливали проверку в свой день рождения.
-- Формат вывода: процент пиров, успешно прошедших проверку в день рождения, процент пиров, проваливших проверку в день рождения

CREATE OR REPLACE FUNCTION successful_checks_on_birthday()
    RETURNS TABLE (
        successful_checks   INT,
        unsuccessful_checks INT
    )
AS
$$
WITH counts AS (SELECT count(peer)
                       filter (WHERE p2p.state = 'SUCCESS' AND verter.state <> 'FAILURE') AS success,
                            count(peer)
                            filter (WHERE (p2p.state = 'SUCCESS' AND verter.state = 'FAILURE') or p2p.state =
                                'FAILURE') AS fail
                FROM checks
                         JOIN p2p ON checks.id = p2p.check_id
                         JOIN verter ON checks.id = verter.check_id
                         JOIN peers ON peers.nickname = checks.peer
                WHERE extract(month FROM checks.check_date) = extract(month FROM peers.birthday)
                        AND extract(DAY FROM checks.check_date) = extract(day FROM peers.birthday))
SELECT (success::NUMERIC / (success + fail)::NUMERIC * 100)::INT AS successful_checks,
    (fail::NUMERIC / (success + fail)::NUMERIC * 100)::INT AS unsuccessful_checks
FROM counts;
$$ LANGUAGE sql;

-- 11) Определить всех пиров, которые сдали заданные задания 1 и 2, но не сдали задание 3
-- Параметры процедуры: названия заданий 1, 2 и 3.
-- Формат вывода: список пиров

CREATE OR REPLACE FUNCTION peers_and_projects(project1 VARCHAR, project2 VARCHAR, project3 VARCHAR)
    RETURNS TABLE (
        peer VARCHAR
    )
AS
$$
SELECT DISTINCT peer
FROM checks AS foo
WHERE exists(SELECT peer
             FROM checks JOIN p2p ON checks.id = p2p.check_id
             WHERE task = project1 AND state = 'SUCCESS' AND peer = foo.peer)
             AND exists(SELECT peer FROM checks JOIN p2p ON checks.id = p2p.check_id
             WHERE task = project2 AND state = 'SUCCESS' AND peer = foo.peer)
             AND (NOT exists(SELECT peer  FROM checks JOIN p2p ON checks.id = p2p.check_id
             WHERE task = project3 AND state = 'SUCCESS' AND peer = foo.peer)
             );
$$ LANGUAGE sql;

-- 12) Используя рекурсивное обобщенное табличное выражение, для каждой задачи вывести кол-во предшествующих ей задач
-- То есть сколько задач нужно выполнить, исходя из условий входа, чтобы получить доступ к текущей.
-- Формат вывода: название задачи, количество предшествующих

CREATE OR REPLACE FUNCTION amount_of_previous_task()
    RETURNS TABLE (
        task       VARCHAR,
        prev_count BIGINT
    )
AS
$$
WITH recursive recursion
    AS (SELECT 'C2_SimpleBashUtils'::VARCHAR AS task, 0::BIGINT AS prev_count
        UNION
        SELECT title, recursion.prev_count + 1 FROM recursion, tasks
        WHERE parent_task = recursion.task AND prev_count < (SELECT count(*) FROM tasks))
SELECT *
FROM recursion;
$$ LANGUAGE sql;

-- 13) Найти "удачные" для проверок дни. День считается "удачным", если в нем есть хотя бы N идущих подряд успешных проверки
-- Параметры процедуры: количество идущих подряд успешных проверок N.
-- Временем проверки считать время начала P2P этапа.
-- Под идущими подряд успешными проверками подразумеваются успешные проверки, между которыми нет неуспешных.
-- При этом кол-во опыта за каждую из этих проверок должно быть не меньше 80% от максимального.
-- Формат вывода: список дней

CREATE OR REPLACE FUNCTION luck_days(n int)
    RETURNS TABLE (
        day DATE
    )
AS
$$
WITH temp_table AS (SELECT t.check_date,
                                row_number() OVER (PARTITION BY t.check_date, flag ORDER BY t.check_date) -
                            CASE WHEN flag != 0 THEN 1 ELSE 0 END AS counter
                    FROM (SELECT c.check_date,
                                      count(CASE WHEN p1.state <> 'SUCCESS' OR v.state <> 'SUCCESS' THEN 1 END )
                                      OVER (PARTITION by c.check_date ORDER BY p1.check_time) AS flag
                          FROM checks AS c
                                   LEFT JOIN p2p p1 ON c.id = p1.check_id AND p1.state <> 'START'
                                   LEFT JOIN p2p p2 ON c.id = p2.check_id AND p2.check_time < p1.check_time AND
                              p2.state = 'START'
                                   LEFT JOIN verter v ON c.id = v.check_id AND v.state <> 'START'
                          ORDER BY check_date, p2.check_time) AS t)
SELECT check_date
FROM temp_table
GROUP BY check_date
HAVING max(counter) >= n;

$$ LANGUAGE sql;

-- 14) Определить пира с наибольшим количеством XP
-- Формат вывода: ник пира, количество XP

CREATE OR REPLACE FUNCTION peer_with_the_most_xp()
    RETURNS TABLE (
        peer VARCHAR,
        xp   BIGINT
    )
AS
$$
SELECT t.nickname AS peer, SUM(t.max_xp_amount) AS xp
FROM (SELECT nickname, MAX(check_date), MAX(xp_amount) AS max_xp_amount
      FROM peers
               LEFT JOIN checks c ON peers.nickname = c.peer
               LEFT JOIN xp x ON c.id = x.check_id
      WHERE xp_amount IS NOT NULL
      GROUP BY nickname, task) AS t
GROUP BY nickname
ORDER BY xp DESC
LIMIT 1;
$$ LANGUAGE sql;


-- 15) Определить пиров, приходивших раньше заданного времени не менее N раз за всё время
-- Параметры процедуры: время, количество раз N.
-- Формат вывода: список пиров

CREATE OR REPLACE FUNCTION determine_who_came_before_time(time_entry TIME, n INT)
    RETURNS TABLE (
        peer VARCHAR
    )
AS
$$
WITH most_early_visit AS (SELECT peer, visit_date, min(visit_time) AS time
                          FROM time_tracking
                          WHERE state = 1 AND visit_time < time_entry
                          GROUP BY peer, visit_date)
SELECT peer
FROM most_early_visit AS t1
WHERE time < time_entry
GROUP BY peer
HAVING count(visit_date) >= n
$$ LANGUAGE sql;

-- 16) Определить пиров, выходивших за последние N дней из кампуса больше M раз
-- Параметры процедуры: количество дней N, количество раз M.
-- Формат вывода: список пиров

CREATE OR REPLACE FUNCTION peers_who_came_out_more(n int, m int)
    RETURNS TABLE (
        peer VARCHAR
    )
AS
$$
SELECT DISTINCT peer
FROM (SELECT DISTINCT peer, count(state) AS in_out_count
      FROM time_tracking
      WHERE time_tracking.visit_date > current_date - n
              AND state = 2
      GROUP BY peer) AS tt
WHERE tt.in_out_count > m
$$ LANGUAGE sql;

-- 17) Определить для каждого месяца процент ранних входов
-- Для каждого месяца посчитать, сколько раз люди, родившиеся в этот месяц, приходили в кампус за всё время (будем называть это общим числом входов).
-- Для каждого месяца посчитать, сколько раз люди, родившиеся в этот месяц, приходили в кампус раньше 12:00 за всё время (будем называть это числом ранних входов).
-- Для каждого месяца посчитать процент ранних входов в кампус относительно общего числа входов.
-- Формат вывода: месяц, процент ранних входов

CREATE OR REPLACE FUNCTION percent_early_entries()
    RETURNS TABLE (
        month VARCHAR,
        early_entries INT
    )
AS
$$
WITH months AS (
    SELECT DATE '2000-01-01' + INTERVAL '1' MONTH * s.a AS date
    FROM generate_series(0, 11) AS s(a)),
    person_in AS (
        SELECT peer, visit_date, visit_time
        FROM time_tracking
        WHERE state = 1)
SELECT to_char(m.date, 'Month') AS month,
    (CASE
        WHEN count(peer) != 0 THEN
            ((count(peer) FILTER ( WHERE visit_time < '12:00:00') / count(peer)::FLOAT) * 100)::INT
        ELSE
            0
        END) AS early_entries
FROM months AS m
         LEFT JOIN peers ON to_char(m.date, 'Month') = to_char(birthday, 'Month')
         LEFT JOIN person_in AS pi ON peers.nickname = pi.peer
GROUP BY m.date
ORDER BY m.date;
$$ LANGUAGE sql;