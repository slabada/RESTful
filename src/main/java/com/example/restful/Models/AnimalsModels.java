package com.example.restful.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@Table(name = "Animals")
public class AnimalsModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            joinColumns = @JoinColumn(name = "Animal_id"),
            inverseJoinColumns = @JoinColumn(name="Type_id")
    )
    private Set<AnimalTypeModels> animalTypes;
    private float weight;
    private float length;
    private float height;
    private String gender;
    private String lifeStatus;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    private Date chippingDateTime;
    private int chipperId;
    private long chippingLocationId;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "Animal_id"),
            inverseJoinColumns = @JoinColumn(name="Location_id")
    )
    private Set<LocationModels> visitedLocations;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    private Date deathDateTime;
}
