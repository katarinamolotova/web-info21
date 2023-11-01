package edu.school21.info21.repositories;

import edu.school21.info21.enums.TableNames;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class IORepository {
    private static final String IMPORT = "SELECT import_db(:tableName, ',')";
    private static final String EXPORT = "SELECT export_db(:tableName, ',')";

    private EntityManager entityManager;

    private void doNativeQueryByString(final String query,
                                       final String tableName) {
        entityManager.createNativeQuery(query)
                     .setParameter("tableName", tableName)
                     .getSingleResult();
    }

    public void importFromTable(final String table) {
        doNativeQueryByString(IMPORT, table);
    }

    public void exportFromTable(final TableNames table) {
        if(!table.getName().equals(TableNames.CUSTOM.getName())) {
            doNativeQueryByString(EXPORT, table.getName());
        }
    }
}
