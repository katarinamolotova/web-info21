package edu.school21.info21.services;

import edu.school21.info21.enums.Directory;
import edu.school21.info21.enums.ErrorMessages;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

@Service
@AllArgsConstructor
public class IOFileService {

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

}
