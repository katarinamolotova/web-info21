package edu.school21.info21.services;

import edu.school21.info21.enums.Directory;
import edu.school21.info21.enums.ErrorMessages;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.repositories.IORepository;
import edu.school21.info21.services.handlers.CashHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@AllArgsConstructor
public class IOFileService {

    private final IORepository repository;
    private final CashHandler handler;

    public String fileUpload(final String name,
                             final MultipartFile file
    ) {
        if (!file.isEmpty()) {
            try {
                clearDirectory(Directory.IMPORT.getName());
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(getPathForDirectory(Directory.IMPORT) + name + ".csv"));
                stream.write(bytes);
                stream.close();
                repository.importFromTable(name);
                handler.globalChanges();
                return ErrorMessages.INPUT_FILE_SUCCESS.getName();
            } catch (Exception e) {
                return prepareImportErrorMessage(e);
            }
        } else {
            return ErrorMessages.INPUT_FILE_ERROR_EMPTY.getName();
        }
    }

    private String prepareImportErrorMessage(final Exception e) {
        if(e.getMessage()
            .contains("duplicate key value violates")) {

            return ErrorMessages.INPUT_FILE_ERROR_DUPLICATE.getName();
        } else if (e.getMessage()
                    .equalsIgnoreCase("ERROR: invalid input syntax")) {

            return ErrorMessages.INPUT_FILE_ERROR_INVALID_DATA.getName();
        } else {
            return ErrorMessages.INPUT_FILE_ERROR_SOMETHING_WRONG.getName();
        }
    }

    private void clearDirectory(final String directory_name) {
        Directory enums = Directory.fromString(directory_name);
        if(Objects.nonNull(enums)) {
            File file = new File(getPathForDirectory(enums));
            try {
                for (String i : Objects.requireNonNull(file.list())) {
                    file = new File(getPathForDirectory(enums) + i);
                    file.delete();
                }
            } catch (NullPointerException e) {
                // DO NOTHING
            }
        }
    }

    private String getPathForDirectory(final Directory directory) {
        return new File("").getAbsolutePath() + directory.getName();
    }

    public String fileDownload(final HttpServletResponse response,
                             final String table,
                             final String fileName
    ) {
        clearDirectory(Directory.EXPORT.getName());
        TableNames enums = TableNames.fromString(table);
        if(Objects.nonNull(enums)) {
            repository.exportFromTable(enums);
            Path file = preparedExportFile(enums);
            if (Files.exists(file)) {
                response.setHeader("Content-disposition", attachmentParameters(fileName));
                response.setContentType("text/csv");

                try {
                    Files.copy(file, response.getOutputStream());
                    response.getOutputStream().flush();
                } catch (IOException e) {
                    throw new RuntimeException(ErrorMessages.OUTPUT_FILE_ERROR.getName());
                }
            }
        }
        return ErrorMessages.OUTPUT_FILE_SUCCESS.getName();
    }

    private String attachmentParameters (final String fileName ) {
        return "attachment;filename=" + fileName + ".csv";
    }

    private Path preparedExportFile (final TableNames table) {
        return new File(getPathForDirectory(Directory.EXPORT)
                        + table.getName()
                        + ".csv").toPath();
    }
}
