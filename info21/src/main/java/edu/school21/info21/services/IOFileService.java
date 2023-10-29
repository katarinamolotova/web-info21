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
import java.io.DataInput;
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
                clearDirectory(Directory.IMPORT.getName());
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
        // TODO  предусмотреть редирект на изначальную страницу
    }

    private void clearDirectory(final String directory_name) {
        Directory enums = Directory.fromString(directory_name);

        assert enums != null;
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

    private String getPathForDirectory(final Directory directory) {
        return new File("").getAbsolutePath() + directory.getName();
    }

    public void getFile(final HttpServletResponse response,
                          final String table,
                          final String fileName
    ) {
        clearDirectory(Directory.EXPORT.getName());
        repository.exportFromTable(table);
        Path file = preparedExportFile(table);
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
        // TODO  предусмотреть редирект на изначальную страницу
    }

    private String attachmentParameters (final String fileName ) {
        return "attachment;filename=" + fileName + ".csv";
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
