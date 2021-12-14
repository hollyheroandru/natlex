package com.example.natlex.repositories;

import com.example.natlex.models.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {
    @Query(value = "SELECT * FROM sections s WHERE s.name = :name", nativeQuery = true)
    Optional<Section> findByName(@Param(value = "name") String name);

    @Query(value = "SELECT * FROM sections \n" +
            "JOIN geological_classes gc ON sections.id = gc.section_id \n" +
            "WHERE gc.code = :code", nativeQuery = true)
    List<Section> findByGeoCode(@Param(value = "code") String code);
}
