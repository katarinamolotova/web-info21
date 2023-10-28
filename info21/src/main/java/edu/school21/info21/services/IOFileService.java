package edu.school21.info21.services;

import edu.school21.info21.enums.Directory;
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
                String absolutePath = getPathForDirectory(Directory.IMPORT) + name;
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(absolutePath + ".csv"));
                stream.write(bytes);
                stream.close();
                return "Вы удачно загрузили " + name;
            } catch (Exception e) {
                return "Вам не удалось загрузить " + name + " => " + e.getMessage();
            }
        } else {
            return "Вам не удалось загрузить " + name + " потому что файл пустой.";
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
