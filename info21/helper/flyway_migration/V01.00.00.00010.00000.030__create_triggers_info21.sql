-- Написать триггер: после добавления записи со статутом "начало" в таблицу P2P,
-- изменить соответствующую запись в таблице TransferredPoints

CREATE OR REPLACE FUNCTION tr_new_transferred_points() RETURNS TRIGGER AS
$$
BEGIN
    IF (new.state = 'START')
    THEN
        update transferred_points
        set points_amount = points_amount + 1
        WHERE checking_peer = new.checking_peer
                AND checked_peer = (SELECT peer
                                    FROM checks AS c
                                             JOIN p2p p ON c.id = p.check_id
                                    WHERE new.check_id = c.id);
    END IF;
    RETURN NULL;
END 
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_new_transferred_points
    AFTER INSERT 
    ON p2p
    FOR EACH ROW
EXECUTE FUNCTION tr_new_transferred_points();

-- Написать триггер: перед добавлением записи в таблицу XP, проверить корректность добавляемой записи
-- Количество XP не превышает максимальное доступное для проверяемой задачи
-- Поле Check ссылается на успешную проверку
-- Если запись не прошла проверку, не добавлять её в таблицу.

CREATE OR REPLACE FUNCTION fnc_add_xp() RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.xp_amount > (SELECT max_xp
                        FROM tasks t
                                 INNER JOIN checks c ON t.title = c.task
                        WHERE c.id = NEW.check_id) THEN
        RETURN NULL;
    END IF;
    IF 0 = (SELECT count(*)
            FROM checks c
                     INNER JOIN p2p p ON c.id = p.check_id
                     LEFT JOIN verter v ON c.id = v.check_id
            WHERE c.id = NEW.check_id
                    AND p.state = 'SUCCESS'
                    AND (v.state = 'SUCCESS' OR v.state IS NULL)) THEN
        RETURN NULL;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER tr_before_xp
    AFTER INSERT
    ON xp
    FOR EACH ROW
EXECUTE FUNCTION fnc_add_xp();
