package com.example.natlex.services;

import com.example.natlex.dtos.requests.sections.SectionCreateRequest;
import com.example.natlex.dtos.requests.sections.SectionUpdateRequest;
import com.example.natlex.dtos.responses.geo.GeologicalClassInfoResponse;
import com.example.natlex.dtos.responses.sections.SectionCreateResponse;
import com.example.natlex.dtos.responses.sections.SectionReadResponse;
import com.example.natlex.dtos.responses.sections.SectionUpdateResponse;
import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.exceptions.ResourceNotFoundException;

import java.util.List;

public interface SectionService {
    SectionCreateResponse createSection(SectionCreateRequest request)
            throws BadRequestException;

    void deleteSectionById(Long id)
            throws ResourceNotFoundException;

    SectionReadResponse getSectionById(Long id)
            throws ResourceNotFoundException;

    List<SectionReadResponse> getSectionsList();

    SectionUpdateResponse updateSectionById(Long id, SectionUpdateRequest request, boolean isChanged)
            throws ResourceNotFoundException, BadRequestException;

    List<SectionReadResponse> getSectionsByGeoCode(String code);

    List<GeologicalClassInfoResponse> getAllGeologicalClasses(Long id)
            throws ResourceNotFoundException;
}
