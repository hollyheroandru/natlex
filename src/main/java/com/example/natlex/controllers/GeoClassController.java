package com.example.natlex.controllers;

import com.example.natlex.dtos.requests.geo.GeoClassUpdateRequest;
import com.example.natlex.dtos.responses.geo.GeoClassUpdateResponse;
import com.example.natlex.dtos.responses.geo.GeologicalClassInfoResponse;
import com.example.natlex.exceptions.ResourceNotFoundException;
import com.example.natlex.services.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(value = "natlex/api/v1/geo")
@Validated
public class GeoClassController {
    @Autowired
    private GeoService geoService;

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGeoClassById(@PathVariable(value = "id") Long id)
            throws ResourceNotFoundException {
        geoService.deleteGeoClassById(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeologicalClassInfoResponse> getGeoClassById(@PathVariable(value = "id") @Positive Long id)
        throws ResourceNotFoundException {
        return ResponseEntity.ok(geoService.getGeoClassById(id));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeoClassUpdateResponse> updateGeoClassById(@PathVariable(value = "id") @Positive Long id,
                                                                     @Valid @RequestBody GeoClassUpdateRequest request)
        throws ResourceNotFoundException {
        return ResponseEntity.ok(geoService.updateGeoClassById(id, request));
    }

}
