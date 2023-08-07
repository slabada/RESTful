package com.example.restful.Repository;

import com.example.restful.Models.AnimalsModels;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface AnimalRepository extends JpaRepository<AnimalsModels, Long> {
    @Query("SELECT animal FROM AnimalsModels animal " +
            "WHERE (:weight IS NULL OR animal.weight = :weight) " +
            "OR (:length IS NULL OR animal.length = :length) " +
            "OR (:height IS NULL OR animal.height = :height) " +
            "OR (:gender IS NULL OR animal.gender = :gender) " +
            "OR (:lifeStatus IS NULL OR animal.lifeStatus = :lifeStatus) " +
            "OR (cast(:chippingDateTime as date) is null or animal.chippingDateTime = :chippingDateTime) " +
            "OR (:chipperId IS NULL OR animal.chipperId = :chipperId) " +
            "OR (:chippingLocationId IS NULL OR animal.chippingLocationId = :chippingLocationId) " +
            "OR (cast(:deathDateTime as date) IS NULL OR animal.deathDateTime = :deathDateTime)")
    List<AnimalsModels> findByAllAnimals(
            @Param("weight") Float weight,
            @Param("length") Float length,
            @Param("height") Float height,
            @Param("gender") String gender,
            @Param("lifeStatus") String lifeStatus,
            @Param("chippingDateTime") Date chippingDateTime,
            @Param("chipperId") Integer chipperId,
            @Param("chippingLocationId") Long chippingLocationId,
            @Param("deathDateTime") Date deathDateTime,
            PageRequest pageRequest
    );
}
