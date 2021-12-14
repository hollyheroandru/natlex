package com.example.natlex.dtos.responses.geo;

import com.example.natlex.models.GeologicalClass;

public class GeologicalClassResponse {
    private String name;
    private String code;

    public GeologicalClassResponse(GeologicalClass geologicalClass) {
        this.name = geologicalClass.getName();
        this.code = geologicalClass.getCode();
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
