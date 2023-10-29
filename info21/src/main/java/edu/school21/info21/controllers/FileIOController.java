package edu.school21.info21.controllers;

import edu.school21.info21.services.IOFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileIOController {
    private final IOFileService service;

    public FileIOController(final IOFileService service) {
        this.service = service;
    }

    @PostMapping(value = "/upload")
    @ResponseBody
    public String fileUpload(
            @RequestParam("name") final String name,
            @RequestParam("file") final MultipartFile file
    ) {
        return service.fileUpload(name, file);
    }

    @PostMapping(value = "/download")
    public @ResponseBody
    void getFile(final HttpServletResponse response,
                 @RequestParam("table_name") final String table,
                 @RequestParam("file_name") final String fileName
    ) {
        service.fileDownload(response, table, fileName);
    }
}