package com.example.natlex.services.impl;

import com.example.natlex.dtos.requests.sections.SectionCreateRequest;
import com.example.natlex.dtos.requests.sections.SectionUpdateRequest;
import com.example.natlex.dtos.responses.geo.GeologicalClassInfoResponse;
import com.example.natlex.dtos.responses.geo.GeologicalClassResponse;
import com.example.natlex.dtos.responses.sections.SectionCreateResponse;
import com.example.natlex.dtos.responses.sections.SectionReadResponse;
import com.example.natlex.dtos.responses.sections.SectionUpdateResponse;
import com.example.natlex.exceptions.BadRequestException;
import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.models.GeologicalClass;
import com.example.natlex.models.Section;
import com.example.natlex.repositories.GeologicalClassRepository;
import com.example.natlex.repositories.SectionRepository;
import com.example.natlex.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private GeologicalClassRepository geologicalClassRepository;

    @Override
    public SectionCreateResponse createSection(SectionCreateRequest request) throws BadRequestException {
        Section section = sectionRepository.findByName(request.getName()).orElse(new Section());

        if(section.getName() != null) {
            throw new BadRequestException("Section name already taken");
        }

        section.setName(request.getName());

        section.setGeologicalClasses(request.getGeologicalClasses().stream()
                .map(gc -> new GeologicalClass(gc.getName(), gc.getCode(), section))
                .collect(Collectors.toList()));


        sectionRepository.save(section);

        return new SectionCreateResponse(section, getGeologicalClassesResponse(section.getGeologicalClasses()));
    }

    @Override
    public void deleteSectionById(Long id) throws ResourceNotFoundException {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));
        sectionRepository.delete(section);
    }

    @Override
    public SectionReadResponse getSectionById(Long id) throws ResourceNotFoundException {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        return new SectionReadResponse(section, getGeologicalClassesResponse(section.getGeologicalClasses()));
    }

    @Override
    public List<SectionReadResponse> getSectionsList() {
        return getSectionsResponse(sectionRepository.findAll());
    }

    @Transactional
    @Override
    public SectionUpdateResponse updateSectionById(Long id, SectionUpdateRequest request, boolean isChanged)
            throws ResourceNotFoundException, BadRequestException {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        if(request.getName() != null) {
            if(sectionRepository.findByName(request.getName()).isEmpty()) {
                section.setName(request.getName());
            } else throw new BadRequestException("Section name already taken");
        }

        if(isChanged && request.getGeologicalClasses() != null && !request.getGeologicalClasses().isEmpty()) {
            geologicalClassRepository.deleteBySectionId(section.getId());
            section.getGeologicalClasses().clear();
        }

        if(request.getGeologicalClasses() != null && !request.getGeologicalClasses().isEmpty()) {
            section.setGeologicalClasses(request.getGeologicalClasses().stream()
                    .map(gc -> new GeologicalClass(gc.getName(), gc.getCode(), section))
                    .collect(Collectors.toList()));
        }


        sectionRepository.save(section);

        return new SectionUpdateResponse(section, getGeologicalClassesResponse(section.getGeologicalClasses()));
    }

    @Override
    public List<SectionReadResponse> getSectionsByGeoCode(String code) {
        return getSectionsResponse(sectionRepository.findByGeoCode(code));
    }

    @Override
    public List<GeologicalClassInfoResponse> getAllGeologicalClasses(Long id) throws ResourceNotFoundException {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

        return section.getGeologicalClasses()
                .stream()
                .sorted(Comparator.comparing(GeologicalClass::getId))
                .map(GeologicalClassInfoResponse::new)
                .collect(Collectors.toList());
    }

    private List<GeologicalClassResponse> getGeologicalClassesResponse(List<GeologicalClass> geologicalClasses) {
        return geologicalClasses
                .stream()
                .sorted(Comparator.comparing(GeologicalClass::getId))
                .map(GeologicalClassResponse::new)
                .collect(Collectors.toList());
    }

    private List<SectionReadResponse> getSectionsResponse(List<Section> sections) {
        return sections
                .stream()
                .sorted(Comparator.comparing(Section::getId))
                .map(section -> new SectionReadResponse(section,
                        getGeologicalClassesResponse(section.getGeologicalClasses())))
                .collect(Collectors.toList());
    }
}
