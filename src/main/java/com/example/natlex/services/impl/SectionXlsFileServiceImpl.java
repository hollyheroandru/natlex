package com.example.natlex.services.impl;

import com.example.natlex.enums.Result;
import com.example.natlex.exceptions.BadRequestException;

import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.exceptions.UnprocessableEntityException;
import com.example.natlex.models.GeologicalClass;
import com.example.natlex.models.Section;
import com.example.natlex.models.SectionXlsFile;
import com.example.natlex.repositories.GeologicalClassRepository;
import com.example.natlex.repositories.SectionRepository;
import com.example.natlex.repositories.SectionXlsFileRepository;
import com.example.natlex.services.SectionXlsFileService;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;


@Service
public class SectionXlsFileServiceImpl implements SectionXlsFileService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private GeologicalClassRepository geologicalClassRepository;

    @Autowired
    private SectionXlsFileRepository sectionXlsFileRepository;

    @Override
    public Long importFile(MultipartFile file)
            throws UnprocessableEntityException, BadRequestException, InterruptedException {
        SectionXlsFile xlsFile = new SectionXlsFile();
        String filename = FilenameUtils.getBaseName(file.getOriginalFilename()) + UUID.randomUUID() + "." +
                FilenameUtils.getExtension(file.getOriginalFilename());
        xlsFile.setFilename(filename);
        xlsFile.setResult(Result.IN_PROGRESS.toString());
        xlsFile.setOriginalName(file.getOriginalFilename());
        sectionXlsFileRepository.save(xlsFile);

        parseFile(file, xlsFile, filename);

        return xlsFile.getId();
    }

    @Override
    public String getImportFileStatusByJobId(Long id) throws ResourceNotFoundException {
        SectionXlsFile xlsFile = sectionXlsFileRepository
                .findById(id).orElseThrow(() -> new ResourceNotFoundException("Xls file with job id " + id + " not found"));
        return xlsFile.getResult();
    }

    @Override
    public Long exportFile() throws UnprocessableEntityException {
        SectionXlsFile xlsFile = new SectionXlsFile();
        String filename = "export" + UUID.randomUUID() + "." + "xlsx";
        xlsFile.setFilename(filename);
        xlsFile.setOriginalName(filename);
        xlsFile.setResult(Result.IN_PROGRESS.toString());
        sectionXlsFileRepository.save(xlsFile);
        parseFromDBToFile(xlsFile, filename);
        return xlsFile.getId();
    }

    @Override
    public String getExportFileStatusById(Long id) throws ResourceNotFoundException {
        SectionXlsFile sectionXlsFile = sectionXlsFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Process with job id " + id + " not found"));
        return sectionXlsFile.getResult();
    }

    @Override
    public File getFileByJobId(Long id) throws UnprocessableEntityException, ResourceNotFoundException {
        SectionXlsFile sectionXlsFile = sectionXlsFileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Process with job id " + id + " not found"));
        File file;

        if(sectionXlsFile.getResult().equals(Result.DONE.toString())) {
            file = new File("src/main/resources/templates/" + sectionXlsFile.getFilename());
        } else throw new UnprocessableEntityException(" File is not ready yet");

        return file;
    }

    @Async("taskExecutor")
    public void parseFromDBToFile(SectionXlsFile xlsFile, String filename) throws UnprocessableEntityException {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet();
            int rowNumber = 1;
            int maxCellsNumber = 0;

            for (Section section : sectionRepository.findAll()) {
                Row row = sheet.createRow(rowNumber++);
                int cellNumber = 0;
                Cell cell = row.createCell(cellNumber++);
                cell.setCellValue(section.getName());
                for (GeologicalClass gc : section.getGeologicalClasses()) {
                    cell = row.createCell(cellNumber++);
                    cell.setCellValue(gc.getName());
                    cell = row.createCell(cellNumber++);
                    cell.setCellValue(gc.getCode());
                }
                if(cellNumber > maxCellsNumber) {
                    maxCellsNumber = cellNumber;
                }
            }

            Row row = sheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Section name");

            for(int i = 1; i < maxCellsNumber; i++) {
                if(i % 2 == 1) {
                    cell = row.createCell(i);
                    cell.setCellValue("Class " + (i + 1) / 2 + " name");
                } else {
                    cell = row.createCell(i);
                    cell.setCellValue("Class " + i / 2 + " code");
                }
            }

            FileOutputStream fos = new FileOutputStream("src/main/resources/templates/" + filename);
            workbook.write(fos);
            xlsFile.setResult(Result.DONE.toString());
            sectionXlsFileRepository.save(xlsFile);
        } catch (Exception e) {
            e.printStackTrace();
            xlsFile.setResult(Result.ERROR.toString());
            sectionXlsFileRepository.save(xlsFile);
            throw new UnprocessableEntityException("Database parsing error");
        }
    }

    @Async("taskExecutor")
    public void parseFile(MultipartFile file, SectionXlsFile xlsFile, String filename)
            throws InterruptedException, BadRequestException, UnprocessableEntityException {

        if(!Objects.equals(FilenameUtils.getExtension(file.getOriginalFilename()), "xlsx")) {
            xlsFile.setResult(Result.ERROR.toString());
            sectionXlsFileRepository.save(xlsFile);
            throw new BadRequestException("File must be xlsx");
        }

        if(file.isEmpty()) {
            xlsFile.setResult(Result.ERROR.toString());
            sectionXlsFileRepository.save(xlsFile);
            throw new BadRequestException("File is empty");
        }

        File originalFile = new File("src/main/resources/templates/" + filename);

        try(OutputStream os = new FileOutputStream(originalFile)) {
            os.write(file.getBytes());
        } catch (Exception e) {
            xlsFile.setResult(Result.ERROR.toString());
            sectionXlsFileRepository.save(xlsFile);
            throw new UnprocessableEntityException("File convert error");
        }

        try {
            FileInputStream fis = new FileInputStream(originalFile);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Section section;
                List<GeologicalClass> geologicalClasses = new ArrayList<>();

                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                String sectionName = cellIterator.next().getStringCellValue();

                //Only originals sections
                if(!sectionName.isBlank()) {
                    section = sectionRepository.findByName(sectionName).orElse(new Section());
                } else break;

                if(section.getName() == null) {
                    section.setName(sectionName);
                    section.setGeologicalClasses(geologicalClasses);
                }

                while (cellIterator.hasNext()) {
                    String geoClassName = cellIterator.next().getStringCellValue();
                    String geoClassCode = cellIterator.next().getStringCellValue();

                    if(!geoClassCode.isBlank() && !geoClassCode.isBlank()) {
                        GeologicalClass geologicalClass = new GeologicalClass(geoClassName,
                                geoClassCode, section);

                        if(!section.getGeologicalClasses().isEmpty()) {
                            //Only originals geological classes
                            if (geologicalClassRepository
                                    .findGeoClassByAllParams(geoClassName, geoClassCode).isEmpty()) {
                                section.getGeologicalClasses().add(geologicalClass);
                            }
                        } else {
                            section.getGeologicalClasses().add(geologicalClass);
                        }

                    } else break;
                }

                if(section.getName() != null) {
                    sectionRepository.save(section);
                }
            }
            xlsFile.setResult(Result.DONE.toString());
            sectionXlsFileRepository.save(xlsFile);
        } catch (Exception e) {
            xlsFile.setResult(Result.ERROR.toString());
            sectionXlsFileRepository.save(xlsFile);
            throw new UnprocessableEntityException("File parsing error");
        }
    }
}
