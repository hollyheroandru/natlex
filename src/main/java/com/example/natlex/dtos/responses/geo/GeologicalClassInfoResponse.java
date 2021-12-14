package com.example.natlex.dtos.responses.geo;

import com.example.natlex.models.GeologicalClass;

public class GeologicalClassInfoResponse {
    private Long id;
    private String name;
    private String code;

    public GeologicalClassInfoResponse(GeologicalClass geologicalClass) {
        this.id = geologicalClass.getId();
        this.name = geologicalClass.getName();
        this.code = geologicalClass.getCode();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
