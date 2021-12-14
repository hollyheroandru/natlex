package com.example.natlex.dtos.requests.geo;

import javax.validation.constraints.NotBlank;

public class GeologicalClassRequest {
    @NotBlank(message = "Name of geological class cannot be null or empty")
    private String name;

    @NotBlank(message = "Code of geological class cannot be null or empty")
    private String code;

    public GeologicalClassRequest(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public GeologicalClassRequest() {
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
}