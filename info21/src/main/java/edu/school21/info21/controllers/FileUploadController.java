package edu.school21.info21.controllers;

import edu.school21.info21.services.IOFileService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class FileUploadController {
    private final IOFileService service;

    public FileUploadController(IOFileService service) {
        this.service = service;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    String fileUpload(
            @RequestParam("name") final String name,
            @RequestParam("file") final MultipartFile file
    ) {
        return service.fileUpload(name, file);
    }

    @RequestMapping(value = "/download/{table}/{file_name}", method = RequestMethod.GET)
    public void getFile(final HttpServletResponse response,
                          @PathVariable final String table,
                          @PathVariable final String file_name
    ) {
        service.getFile(response, table, file_name);
    }
}