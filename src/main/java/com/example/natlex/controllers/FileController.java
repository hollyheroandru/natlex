package com.example.natlex.controllers;

import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.exceptions.UnprocessableEntityException;
import com.example.natlex.services.SectionXlsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.io.File;

@RestController
@RequestMapping("/natlex/api/v1/files")
@Validated
public class FileController {

    @Autowired
    private SectionXlsFileService sectionXlsFileService;

    @PostMapping(value = "/import")
    public ResponseEntity<Long> importFile(@RequestParam("file") MultipartFile file)
            throws UnprocessableEntityException, BadRequestException, InterruptedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionXlsFileService.importFile(file));
    }

    @GetMapping(value = "/import/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getImportFileStatusByJobId(@PathVariable(value = "id") @Positive Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(sectionXlsFileService.getImportFileStatusByJobId(id));
    }

    @GetMapping(value = "/export")
    public ResponseEntity<Long> exportFile() throws UnprocessableEntityException {
        return ResponseEntity.ok(sectionXlsFileService.exportFile());
    }

    @GetMapping(value = "/export/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getExportFileStatusById(@PathVariable(value = "id") @Positive Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(sectionXlsFileService.getExportFileStatusById(id));
    }

    @GetMapping(value = "/export/{id}/file")
    public ResponseEntity<File> getFileByJobId(@PathVariable(value = "id") @Positive Long id)
            throws ResourceNotFoundException, UnprocessableEntityException {
        return ResponseEntity.ok(sectionXlsFileService.getFileByJobId(id));
    }
}
