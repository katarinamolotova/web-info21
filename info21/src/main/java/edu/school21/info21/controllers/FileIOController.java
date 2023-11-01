package edu.school21.info21.controllers;

import edu.school21.info21.enums.InfoMessages;
import edu.school21.info21.services.FileIOService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@Slf4j
@Controller
@AllArgsConstructor
public class FileIOController {
    private final FileIOService service;

    @PostMapping("/upload")
    @ResponseBody
    public RedirectView fileUpload(
            @RequestParam("table_name") final String table,
            @RequestParam("file_name") final MultipartFile file,
            @RequestParam("page") final String page
    ) {
        log.info("POST /upload?file_name={}&table_name={}", file.getName(), table);
        final InfoMessages message = service.fileUpload(table, file);
        if (message != InfoMessages.INPUT_FILE_SUCCESS) {
            log.warn("Import error: {}", message.getName());
            return new RedirectView(String.format("/%s/error/%s", page, message));
        }
        log.info("Import success");
        return new RedirectView(String.format("/%s", page));
    }


    @PostMapping("/download")
    @ResponseBody
    public void getFile(
            final HttpServletResponse response,
            @RequestParam("table_name") final String table,
            @RequestParam("file_name") final String fileName,
            @RequestParam("page") final String page
    ) {
        log.info("POST /download?file_name={}&table_name={}", fileName, table);
        final InfoMessages message = service.fileDownload(response, table, fileName);
        if (message != InfoMessages.OUTPUT_FILE_SUCCESS) {
            try {
                log.info("Export error: {}", message.getName());
                if (table.equals("custom")) {
                    response.sendRedirect(String.format("/%s/%s", page, message));
                } else {
                    response.sendRedirect(String.format("/%s/error/%s", page, message));
                }
            } catch (final IOException e) {
                log.error("Redirect error: {}", e.getMessage());
            }
        } else {
            log.info("Export success");
        }
    }
}