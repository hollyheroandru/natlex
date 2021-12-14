package com.example.natlex.services;

import com.example.natlex.dtos.requests.geo.GeoClassUpdateRequest;
import com.example.natlex.dtos.responses.geo.GeoClassUpdateResponse;
import com.example.natlex.dtos.responses.geo.GeologicalClassInfoResponse;
import com.example.natlex.exceptions.ResourceNotFoundException;

public interface GeoService {

    void deleteGeoClassById(Long id)
            throws ResourceNotFoundException;

    GeologicalClassInfoResponse getGeoClassById(Long id)
        throws ResourceNotFoundException;

    GeoClassUpdateResponse updateGeoClassById(Long id, GeoClassUpdateRequest request)
        throws ResourceNotFoundException;
}
