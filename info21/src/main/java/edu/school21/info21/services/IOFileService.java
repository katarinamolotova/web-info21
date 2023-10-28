package edu.school21.info21.services;

import com.google.common.base.Enums;
import edu.school21.info21.enums.Directory;
import edu.school21.info21.enums.ErrorMessages;
import edu.school21.info21.enums.TableNames;
import edu.school21.info21.repositories.IORepository;
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

    public String fileUpload(final String name,
                             final MultipartFile file
    ) {
        if (!file.isEmpty()) {
            try {
                clearImportDirectory();
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(getPathForDirectory(Directory.IMPORT) + name + ".csv"));
                stream.write(bytes);
                stream.close();
                repository.importFromTable(name);
                return ErrorMessages.INPUT_FILE_SUCCESS.getName();
            } catch (Exception e) {
                return ErrorMessages.INPUT_FILE_ERROR.getName() + e.getMessage();
            }
        } else {
            return ErrorMessages.INPUT_FILE_EMPTY.getName();
        }
    }

    private void clearImportDirectory() {
        File file = new File(getPathForDirectory(Directory.IMPORT));
        try {
            for (String i : Objects.requireNonNull(file.list())) {
                file = new File(getPathForDirectory(Directory.IMPORT) + i);
                file.delete();
            }
        } catch (NullPointerException e) {
            // DO NOTHING
        }
    }

    private String getPathForDirectory(final Directory directory) {
        return new File("").getAbsolutePath() + directory.getName();
    }

    public void getFile(final HttpServletResponse response,
                          final String table,
                          final String file_name
    ) {

        repository.exportFromTable(table);
        Path file = preparedExportFile(table);
        if (Files.exists(file)) {
            response.setHeader("Content-disposition", "attachment;filename=export.csv");
            response.setContentType("text/csv");

            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }

    private Path preparedExportFile (final String table) {
        TableNames enums = TableNames.fromString(table);
        if(Objects.nonNull(enums)) {
            return new File(getPathForDirectory(Directory.EXPORT) + enums.getName() + ".csv").toPath();
        } else {
            return new File(getPathForDirectory(Directory.EXPORT) + "export.csv").toPath();
        }
    }

}
