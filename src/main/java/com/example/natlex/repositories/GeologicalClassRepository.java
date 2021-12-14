package com.example.natlex.repositories;

import com.example.natlex.models.GeologicalClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GeologicalClassRepository extends JpaRepository<GeologicalClass, Long> {

    @Modifying
    @Query(value = "DELETE FROM geological_classes gc WHERE gc.section_id = :id", nativeQuery = true)
    void deleteBySectionId(@Param(value = "id") Long id);
}
