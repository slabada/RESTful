package com.example.restful.Controllers;

import com.example.restful.Models.AnimalsModels;
import com.example.restful.Service.AnimalService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    protected final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
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

        if(from < 0 || size <= 0 || animal.chipperId <= 0 || animal.chippingLocationId <= 0 ||
                (!animal.lifeStatus.equals("ALIVE") && !animal.lifeStatus.equals("DEAD")) ||
                (!animal.gender.equals("MALE") && !animal.gender.equals("FEMALE") && !animal.gender.equals("OTHER"))){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        List<AnimalsModels> animals = animalService.SearchAnimals(animal, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(animals);
    }

    @PostMapping
    public ResponseEntity<AnimalsModels> AddAnimal(@ModelAttribute AnimalsModels animal){

        // Все хуйня, доделать надо!

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
