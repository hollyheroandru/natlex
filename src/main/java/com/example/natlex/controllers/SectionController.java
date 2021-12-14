package com.example.natlex.controllers;

import com.example.natlex.dtos.requests.sections.SectionCreateRequest;
import com.example.natlex.dtos.requests.sections.SectionUpdateRequest;
import com.example.natlex.dtos.responses.geo.GeologicalClassInfoResponse;
import com.example.natlex.dtos.responses.sections.SectionCreateResponse;
import com.example.natlex.dtos.responses.sections.SectionReadResponse;
import com.example.natlex.dtos.responses.sections.SectionUpdateResponse;
import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/natlex/api/v1/sections")
@Validated
public class SectionController {

    @Autowired
    private SectionService sectionService;

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionCreateResponse> createSection(@Valid @RequestBody SectionCreateRequest request)
            throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectionService.createSection(request));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSectionById(@PathVariable(value = "id") @Positive Long id)
            throws ResourceNotFoundException {
        sectionService.deleteSectionById(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionReadResponse> getSectionById(@PathVariable(value = "id") @Positive Long id)
            throws ResourceNotFoundException {
        return ResponseEntity.ok(sectionService.getSectionById(id));
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SectionReadResponse>> getSectionsList() {
        return ResponseEntity.ok(sectionService.getSectionsList());
    }

    @PutMapping(value = "/{id}/change-all", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectionUpdateResponse> updateSectionById(@PathVariable(value = "id") @Positive Long id,
                                                                   @RequestBody SectionUpdateRequest request,
                                                                   @RequestParam(value = "isChanged") boolean isChanged)
            throws ResourceNotFoundException, BadRequestException {
        return ResponseEntity.ok(sectionService.updateSectionById(id, request, isChanged));
    }

    @GetMapping(value = "/by-code", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SectionReadResponse>> getSectionsListByGeoCode(@RequestParam("code") String code) {
        return ResponseEntity.ok(sectionService.getSectionsByGeoCode(code));
    }

    @GetMapping(value = "/{id}/geo-classes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GeologicalClassInfoResponse>> getAllGeoClassesBySectionId(@PathVariable(value = "id") @Positive Long id)
        throws ResourceNotFoundException {
        return ResponseEntity.ok(sectionService.getAllGeologicalClasses(id));
    }
}
