CREATE OR REPLACE PROCEDURE import_db_test(sep VARCHAR = ',') AS
$$
DECLARE
str TEXT;
BEGIN
        str := 'copy checks FROM ''/var/lib/postgresql/import/test.csv'' delimiter ''' || sep || ''' csv header';
EXECUTE (str);
        str :=  'SELECT setval(''checks_id_seq'', max(id)) FROM checks';
EXECUTE (str);
END;
$$ LANGUAGE plpgsql;

CALL import_db_test(',');