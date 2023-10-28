package edu.school21.info21.controllers;

import edu.school21.info21.services.IOFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {
    private final IOFileService service;

    public FileUploadController(final IOFileService service) {
        this.service = service;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public String fileUpload(
            @RequestParam("name") final String name,
            @RequestParam("file") final MultipartFile file
    ) {
        return service.fileUpload(name, file);
    }
}