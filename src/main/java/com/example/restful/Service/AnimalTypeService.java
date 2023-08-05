package com.example.restful.Service;

import com.example.restful.Models.AnimalTypeModels;
import com.example.restful.Repository.AnimalTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnimalTypeService {

    protected final AnimalTypeRepository animalTypeRepository;

    public AnimalTypeService(AnimalTypeRepository animalTypeRepository){
        this.animalTypeRepository = animalTypeRepository;
    }

    public Optional<AnimalTypeModels> GetID(Long id){
        return animalTypeRepository.findById(id);
    }

    public AnimalTypeModels AddAnimalModel(String Type){

        AnimalTypeModels AnimalType = animalTypeRepository.findByType(Type);

        if(AnimalType != null){
            return null;
        }

        AnimalTypeModels response = new AnimalTypeModels();

        response.setType(Type);

        animalTypeRepository.save(response);

        return response;
    }

    public AnimalTypeModels UpdateAnimalType(Long TypeID ,String NewType){

        AnimalTypeModels animalType = animalTypeRepository.findByType(NewType);

        if(animalType != null){
            return  null;
        }

        Optional<AnimalTypeModels> animalID = animalTypeRepository.findById(TypeID);

        if(animalID.isEmpty()){
            return null;
        }

        AnimalTypeModels newAnimalType = animalID.get();

        newAnimalType.setType(NewType);

        animalTypeRepository.save(newAnimalType);

        return newAnimalType;
    }

    public boolean DeleteAnimalType(Long typeId){

        Optional<AnimalTypeModels> animalDb = animalTypeRepository.findById(typeId);

        if(animalDb.isEmpty()){
            return false;
        }

        animalTypeRepository.deleteById(typeId);

        return true;
    }
}
