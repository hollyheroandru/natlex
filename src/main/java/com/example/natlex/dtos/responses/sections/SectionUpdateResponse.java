package com.example.natlex.dtos.responses.sections;

import com.example.natlex.dtos.responses.geo.GeologicalClassResponse;
import com.example.natlex.models.Section;

import java.util.List;

public class SectionUpdateResponse {
    private Long id;
    private String name;
    private List<GeologicalClassResponse> geologicalClasses;

    public SectionUpdateResponse(Section section, List<GeologicalClassResponse> geologicalClasses) {
        this.id = section.getId();
        this.name = section.getName();
        this.geologicalClasses = geologicalClasses;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<GeologicalClassResponse> getGeologicalClasses() {
        return geologicalClasses;
    }
}
