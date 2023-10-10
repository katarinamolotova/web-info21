-- Постгресу нужен абсолютный путь, либо нужно чудным образом перенести все необходимые файлы
-- и скрипт в дерикторию, где работает бд

CREATE OR REPLACE PROCEDURE import_db(name VARCHAR, sep VARCHAR = ',') AS
$$
DECLARE
    str TEXT;
BEGIN
    str := 'copy ' || name || ' FROM ''/var/lib/postgresql/import/' || name || '.csv'' delimiter ''' || sep ||
        ''' csv header';
    EXECUTE (str);
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE PROCEDURE export_db(name VARCHAR, sep VARCHAR = ',') AS
$$
DECLARE
    str TEXT;
BEGIN
    str := 'copy ' || name || ' to ''/var/lib/postgresql/export/' || name || '.csv'' with csv delimiter ''' || sep ||
        ''' header';
    EXECUTE (str);
END;
$$ LANGUAGE plpgsql;

-- Параметры: ник проверяемого, ник проверяющего, название задания, статус P2P проверки, время.
-- Если задан статус "начало", добавить запись в таблицу Checks (в качестве даты использовать сегодняшнюю).
-- Добавить запись в таблицу P2P.
-- Если задан статус "начало", в качестве проверки указать только что добавленную запись, иначе указать проверку с незавершенным P2P этапом.

CREATE OR REPLACE PROCEDURE add_to_p2p(checked_peer VARCHAR, new_checking_peer VARCHAR,
                                       task_name VARCHAR, status check_status, start_time TIME) AS
$$
BEGIN
    IF (status = 'start') THEN
        INSERT INTO checks
        VALUES ((SELECT max(id) + 1 FROM checks),
                checked_peer, task_name, now());
        INSERT INTO p2p
        VALUES ((SELECT max(id) + 1 FROM p2p),
                (SELECT max(id) FROM checks),
                new_checking_peer, status, start_time);
    ELSE
        INSERT INTO p2p
        VALUES ((SELECT max(id) + 1 FROM p2p),
                (SELECT check_id FROM p2p GROUP BY check_id HAVING count(check_id) = 1),
                new_checking_peer, status, start_time);
    END IF;
END;
$$ LANGUAGE plpgsql;

--  Добавить запись в таблицу Verter (в качестве проверки указать проверку соответствующего задания с самым
--  поздним (по времени) успешным P2P этапом)

CREATE OR REPLACE PROCEDURE add_to_verter(checked_peer VARCHAR, task_name VARCHAR,
                                          status check_status, END_time time) AS
$$
BEGIN
    INSERT INTO verter
    VALUES ((SELECT max(id) + 1 FROM verter),
            (SELECT p2p.check_id
             FROM p2p
                      JOIN checks c ON c.id = p2p.check_id
             WHERE p2p.state = 'success'
                     AND task = task_name
                     AND peer = checked_peer
                     AND check_time >= ANY (SELECT check_time FROM p2p WHERE state = 'success' AND task = task_name)),
            status, END_time);
END
$$ LANGUAGE plpgsql;


