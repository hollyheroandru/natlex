package com.example.natlex.repositories;

import com.example.natlex.models.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {

    @Modifying
    @Query(value = "DELETE FROM geological_classes gc WHERE gc.section_id = :id", nativeQuery = true)
    void deleteBySectionId(@Param(value = "id") Long id);

    @Query(value = "SELECT * FROM geological_classes gc WHERE " +
            "gc.name = :name AND gc.code = :code", nativeQuery = true)
    Optional<GeologicalClass> findGeoClassByAllParams(@Param(value = "name") String name,
                                                      @Param(value = "code") String code);
}
