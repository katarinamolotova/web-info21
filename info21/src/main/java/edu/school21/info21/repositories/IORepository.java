package edu.school21.info21.repositories;

import edu.school21.info21.enums.TableNames;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import org.hibernate.JDBCException;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class IORepository {
    private static final String IMPORT = "CALL import_db('";
    private static final String EXPORT = "CALL export_db('";
    private static final String CLOSE_STRING = "', ',')";

    private EntityManager entityManager;

    private void doNativeQueryByString(final String query) {
        try {
            entityManager.createNativeQuery(query).getResultList();
        } catch (JDBCException e) {
            // DO NOTHING
        }
    }

    public void importFromTable(final String table) {
        doNativeQueryByString(preparedQuery(IMPORT, table));
    }

    public void exportFromTable(TableNames table) {
        if(!table.getName().equals(TableNames.CUSTOM)) {
            doNativeQueryByString(preparedQuery(EXPORT, table.getName()));
        }
    }

    private String preparedQuery(final String procedure,
                                 final String table
    ) {
        return procedure + table + CLOSE_STRING;
    }
}
