package com.example.natlex.dtos.requests.geo;

public class GeoClassUpdateRequest {
    private String name;
    private String code;
    private Long sectionId;

    public GeoClassUpdateRequest(String name, String code, Long sectionId) {
        this.name = name;
        this.code = code;
        this.sectionId = sectionId;
    }

    public GeoClassUpdateRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}
