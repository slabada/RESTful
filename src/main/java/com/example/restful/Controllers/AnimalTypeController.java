package com.example.restful.Controllers;

import com.example.restful.Models.AccountsModels;
import com.example.restful.Models.AnimalTypeModels;
import com.example.restful.Service.AnimalTypeService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("animals/types")
public class AnimalTypeController {

    protected final AnimalTypeService animalTypeService;

    public AnimalTypeController(AnimalTypeService animalTypeService) {
        this.animalTypeService = animalTypeService;
    }

    @GetMapping("{typeId}")
    public ResponseEntity<Optional<AnimalTypeModels>> GetInfoAnimalType(@PathVariable Long typeId){

        if(typeId == null || typeId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<AnimalTypeModels> animalType = animalTypeService.GetID(typeId);

        if(animalType.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(animalType);
    }

    @PostMapping()
    public ResponseEntity<AnimalTypeModels> AddAnimalType(@RequestBody AnimalTypeModels animalType){

        if(animalType == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        AnimalTypeModels AnimalType = animalTypeService.AddAnimalModel(animalType.getType());

        if(AnimalType == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(AnimalType);
    }

    @PutMapping("{typeId}")
    public ResponseEntity<AnimalTypeModels> UpdateAnimalType(@PathVariable Long typeId,
                                                             @RequestBody AnimalTypeModels animalType){

        if(typeId == null || typeId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<AnimalTypeModels> AnimalId = animalTypeService.GetID(typeId);

        if(AnimalId.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AnimalTypeModels updatedAnimalType = animalTypeService.UpdateAnimalType(typeId, animalType.getType());

        if(updatedAnimalType == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(animalType);
    }

    @DeleteMapping("{typeId}")
    public ResponseEntity<HttpStatus> DeleteAnimalType(@PathVariable Long typeId){

        if(typeId == null || typeId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(!animalTypeService.DeleteAnimalType(typeId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
