package com.example.restful.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Set;
import java.util.stream.Stream;

@Entity
@Data
@Table(name = "AnimalType")
public class AnimalTypeModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @NotBlank
    private String type;
}
