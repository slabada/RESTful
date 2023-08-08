package com.example.restful.Service;

import com.example.restful.Models.AccountsModels;
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
    protected final AccountService accountService;

    public AnimalService(AnimalRepository animalRepository, AnimalTypeService animalTypeService, LocationService locationService, AccountService accountService) {
        this.animalRepository = animalRepository;
        this.animalTypeService = animalTypeService;
        this.locationService = locationService;
        this.accountService = accountService;
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
        Set<LocationModels> existingLocations = locationService.CheckVisitedLocationId(animal);

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

    public boolean UpdateLifeStatus(AnimalsModels animal){
        if(animal.getLifeStatus().equals("DEAD")){
            animal.setLifeStatus(animal.getLifeStatus());
            return true;
        }
        return false;
    }

    public AnimalsModels UpdateAnimal(Long animalId, AnimalsModels animal){

        Optional<AnimalsModels> animalDb = animalRepository.findById(animalId);

        if(animalDb.isEmpty()){
            return null;
        }

        AccountsModels chipperId = accountService.getAccountId(animal.getChipperId());

        if(chipperId == null){
            return null;
        }

        Optional<LocationModels> location = locationService.GetLocationId(animal.getChippingLocationId());

        if(location.isEmpty()){
            return null;
        }

        Set<AnimalTypeModels> existingAnimalTypes = animalTypeService.CheckTypeAnimal(animal);

        AnimalsModels sourceAnimal = animalDb.get();

        sourceAnimal.setAnimalTypes(existingAnimalTypes);
        sourceAnimal.setWeight(animal.getWeight());
        sourceAnimal.setLength(animal.getLength());
        sourceAnimal.setHeight(animal.getHeight());
        sourceAnimal.setGender(animal.getGender());
        sourceAnimal.setLifeStatus(animal.getLifeStatus());
        sourceAnimal.setChippingDateTime(sourceAnimal.getChippingDateTime());
        sourceAnimal.setChipperId(animal.getChipperId());
        sourceAnimal.setChippingLocationId(animal.getChippingLocationId());
        sourceAnimal.setVisitedLocations(animal.getVisitedLocations());
        sourceAnimal.setDeathDateTime(animal.getDeathDateTime());

        animalRepository.save(sourceAnimal);

        return sourceAnimal;
    }

    public boolean DeleteAnimal(Long animalId){
        Optional<AnimalsModels> animalDb = animalRepository.findById(animalId);
        if(animalDb.isEmpty()){
            return false;
        }

        animalRepository.deleteById(animalId);

        return true;
    }
}
