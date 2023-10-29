package edu.school21.info21.repositories;

import jakarta.persistence.EntityManager;
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

    public void exportFromTable(String table) {
        doNativeQueryByString(preparedQuery(EXPORT, table));
    }

    private String preparedQuery(final String procedure,
                                 final String table
    ) {
        return procedure + table + CLOSE_STRING;
    }
}
