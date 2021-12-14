package com.example.natlex.dtos.responses.sections;

import com.example.natlex.dtos.responses.geo.GeologicalClassResponse;
import com.example.natlex.models.Section;

import java.util.List;

public class SectionCreateResponse {
    private String name;
    private List<GeologicalClassResponse> geologicalClasses;

    public SectionCreateResponse(Section section, List<GeologicalClassResponse> geologicalClasses) {
        this.name = section.getName();
        this.geologicalClasses = geologicalClasses;
    }

    public String getName() {
        return name;
    }

    public List<GeologicalClassResponse> getGeologicalClasses() {
        return geologicalClasses;
    }
}
