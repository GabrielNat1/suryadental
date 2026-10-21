package br.com.suryadental.application.controller;

import br.com.suryadental.application.dto.response.ImportResponse;
import br.com.suryadental.application.service.ImportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
public class ImportController {
    private final ImportService importService;

    public ImportController(ImportService importService) {
        this.importService = importService;
    }

    @PostMapping
    public ResponseEntity<ImportResponse> importFile(
            @RequestParam("file") MultipartFile file
    ) {
        return ResponseEntity.ok(importService.importFile(file));
    }
}
