package com.example.natlex.dtos.requests.sections;

import com.example.natlex.dtos.requests.geo.GeologicalClassRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class SectionCreateRequest {
    @NotBlank(message = "Name of section cannot be null or empty")
    private String name;

    @Valid
    private List<GeologicalClassRequest> geologicalClasses;


    public SectionCreateRequest(String name, List<GeologicalClassRequest> geologicalClasses) {
        this.name = name;
        this.geologicalClasses = geologicalClasses;
    }

    public SectionCreateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeologicalClassRequest> getGeologicalClasses() {
        return geologicalClasses;
    }

    public void setGeologicalClasses(List<GeologicalClassRequest> geologicalClasses) {
        this.geologicalClasses = geologicalClasses;
    }
}
