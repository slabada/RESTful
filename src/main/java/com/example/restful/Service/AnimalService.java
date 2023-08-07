package com.example.restful.Service;

import com.example.restful.Models.AnimalTypeModels;
import com.example.restful.Models.AnimalsModels;
import com.example.restful.Models.LocationModels;
import com.example.restful.Repository.AnimalRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;

@Service
public class AnimalService {

    protected final AnimalRepository animalRepository;
    protected final AnimalTypeService animalTypeService;

    protected final LocationService locationService;

    public AnimalService(AnimalRepository animalRepository, AnimalTypeService animalTypeService, LocationService locationService) {
        this.animalRepository = animalRepository;
        this.animalTypeService = animalTypeService;
        this.locationService = locationService;
    }

    public Optional<AnimalsModels> GetId(Long id){
        return animalRepository.findById(id);
    }

    public List<AnimalsModels> SearchAnimals(@ModelAttribute AnimalsModels animal,
                                             int from, int size){

        Sort sort = Sort.by(Sort.Direction.ASC, "id");
        PageRequest pageRequest = PageRequest.of(from, size, sort);

        return animalRepository.findByAllAnimals(
                animal.getWeight(),
                animal.getLength(),
                animal.getHeight(),
                animal.getGender(),
                animal.getLifeStatus(),
                animal.getChippingDateTime(),
                animal.getChipperId(),
                animal.getChippingLocationId(),
                animal.getDeathDateTime(),
                pageRequest
        );
    }

    public AnimalsModels AddAnimal(AnimalsModels animal) {
        AnimalsModels response = new AnimalsModels();

        Set<AnimalTypeModels> existingAnimalTypes = animalTypeService.CheckTypeAnimal(animal);
        Set<LocationModels> existingLocations = locationService.CheckChippingLocationId(animal);

        response.setId(animal.getId());
        response.setAnimalTypes(existingAnimalTypes);
        response.setWeight(animal.getWeight());
        response.setLength(animal.getLength());
        response.setHeight(animal.getHeight());
        response.setGender(animal.getGender());
        response.setLifeStatus("ALIVE");
        response.setChippingDateTime(new Date());
        response.setChipperId(animal.getChipperId());
        response.setChippingLocationId(animal.getChippingLocationId());
        response.setVisitedLocations(existingLocations);
        response.setDeathDateTime(animal.getDeathDateTime());

        animalRepository.save(response);

        return response;
    }

}
