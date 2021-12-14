package com.example.natlex.dtos.requests.sections;

import com.example.natlex.dtos.requests.geo.GeologicalClassRequest;

import java.util.List;

public class SectionUpdateRequest {
    private String name;
    private List<GeologicalClassRequest> geologicalClasses;

    public SectionUpdateRequest(String name, List<GeologicalClassRequest> geologicalClasses) {
        this.name = name;
        this.geologicalClasses = geologicalClasses;
    }

    public SectionUpdateRequest() {
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
