package com.example.restful.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    private Date chippingDateTime;
    private int chipperId;
    private long chippingLocationId;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(name = "Animal_id"),
            inverseJoinColumns = @JoinColumn(name="Location_id")
    )
    private Set<LocationModels> visitedLocations;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mmX")
    private Date deathDateTime;
}
