package com.example.restful.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "Animals")
public class AnimalsModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @OneToMany
    public Set<AnimalTypeModels> animalTypes;
    public float weight;
    public float length;
    public float height;
    public String gender;
    public String lifeStatus;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    public Date chippingDateTime;
    public int chipperId;
    public long chippingLocationId;
    @OneToMany
    public Set<LocationModels> visitedLocations;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    public Date deathDateTime;
}
