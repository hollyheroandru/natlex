package com.example.natlex.services;

import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.exceptions.UnprocessableEntityException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface SectionXlsFileService {

    Long importFile(MultipartFile file)
            throws UnprocessableEntityException, BadRequestException, InterruptedException;

    String getImportFileStatusByJobId(Long id) throws ResourceNotFoundException;

    Long exportFile() throws UnprocessableEntityException;

    String getExportFileStatusById(Long id) throws ResourceNotFoundException;

    File getFileByJobId(Long id) throws UnprocessableEntityException, ResourceNotFoundException;
}
