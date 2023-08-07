package com.example.restful.Controllers;

import com.example.restful.Models.AnimalsModels;
import com.example.restful.Service.AccountService;
import com.example.restful.Service.AnimalService;
import com.example.restful.Service.AnimalTypeService;
import com.example.restful.Service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    protected final LocationService locationService;
    protected final AccountService accountService;
    protected final AnimalService animalService;
    protected final AnimalTypeService typeService;

    public AnimalController(LocationService locationService, AccountService accountService, AnimalService animalService, AnimalTypeService typeService) {
        this.locationService = locationService;
        this.accountService = accountService;
        this.animalService = animalService;
        this.typeService = typeService;
    }

    @GetMapping("{animalId}")
    public ResponseEntity<Optional<AnimalsModels>> GetInfoAnimal(@PathVariable Long animalId){

        if(animalId == null || animalId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<AnimalsModels> animal = animalService.GetId(animalId);

        if(animal.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(animal);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AnimalsModels>> SearchAnimal(@ModelAttribute AnimalsModels animal,
                                                            @RequestParam(defaultValue = "0") int from,
                                                            @RequestParam(defaultValue = "10") int size){

        if(from < 0 || size <= 0 || animal.getChipperId() <= 0 || animal.getChippingLocationId() <= 0 ||
                (!animal.getLifeStatus().equals("ALIVE") && !animal.getLifeStatus().equals("DEAD")) ||
                (!animal.getGender().equals("MALE") && !animal.getGender().equals("FEMALE") && !animal.getGender().equals("OTHER"))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<AnimalsModels> animals = animalService.SearchAnimals(animal, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(animals);
    }

    @PostMapping
    public ResponseEntity<AnimalsModels> AddAnimal(@RequestBody AnimalsModels animal){

        if(animal.getAnimalTypes() == null ||
                animal.getAnimalTypes().size() <= 0 ||
                animal.getWeight() <= 0 ||
                animal.getLength() <= 0 ||
                animal.getHeight() <= 0 ||
                (!animal.getGender().equals("MALE") && !animal.getGender().equals("FEMALE") && !animal.getGender().equals("OTHER")) ||
                animal.getChipperId() <= 0 || animal.getChippingLocationId() <= 0){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(typeService.CheckTypeAnimal(animal) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!accountService.CheckChipperId(animal)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(locationService.CheckChippingLocationId(animal) == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(animalService.AddAnimal(animal));
    }
}
