package com.example.natlex.services.impl;

import com.example.natlex.dtos.requests.geo.GeoClassUpdateRequest;
import com.example.natlex.dtos.responses.geo.GeoClassUpdateResponse;
import com.example.natlex.dtos.responses.geo.GeologicalClassInfoResponse;
import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.models.GeologicalClass;
import com.example.natlex.models.Section;
import com.example.natlex.repositories.GeologicalClassRepository;
import com.example.natlex.repositories.SectionRepository;
import com.example.natlex.services.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeoServiceImpl implements GeoService {
    @Autowired
    private GeologicalClassRepository geologicalClassRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Override
    public void deleteGeoClassById(Long id) throws ResourceNotFoundException {
        GeologicalClass geologicalClass = geologicalClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Geological class not found"));

        geologicalClassRepository.delete(geologicalClass);
    }

    @Override
    public GeologicalClassInfoResponse getGeoClassById(Long id) throws ResourceNotFoundException {
        GeologicalClass geologicalClass = geologicalClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Geological class not found"));

        return new GeologicalClassInfoResponse(geologicalClass);
    }

    @Transactional
    @Override
    public GeoClassUpdateResponse updateGeoClassById(Long id, GeoClassUpdateRequest request)
            throws ResourceNotFoundException {
        GeologicalClass geologicalClass = geologicalClassRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Geological class not found"));

        geologicalClass.setName(request.getName() != null ? request.getName() : geologicalClass.getName());
        geologicalClass.setCode(request.getCode() != null ? request.getCode() : geologicalClass.getCode());

        if(request.getSectionId() != null) {
            Section section = sectionRepository.findById(request.getSectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Section not found"));

            geologicalClass.setSection(section);
        }

        geologicalClassRepository.save(geologicalClass);

        return new GeoClassUpdateResponse(geologicalClass);
    }
}
