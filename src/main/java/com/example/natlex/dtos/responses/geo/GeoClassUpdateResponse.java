package com.example.natlex.dtos.responses.geo;

import com.example.natlex.models.GeologicalClass;

public class GeoClassUpdateResponse {
    private Long id;
    private String name;
    private String code;
    private Long sectionId;

    public GeoClassUpdateResponse(GeologicalClass geologicalClass) {
        this.id = geologicalClass.getId();
        this.name = geologicalClass.getName();
        this.code = geologicalClass.getCode();
        this.sectionId = geologicalClass.getSection().getId();
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

    public Long getSectionId() {
        return sectionId;
    }
}
