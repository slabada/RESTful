package com.example.restful.Repository;

import com.example.restful.Models.AnimalTypeModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalTypeRepository extends JpaRepository<AnimalTypeModels, Long> {

    AnimalTypeModels findByType(String Type);
}
