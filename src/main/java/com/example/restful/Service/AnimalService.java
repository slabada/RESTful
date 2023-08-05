package com.example.restful.Service;

import com.example.restful.Models.AnimalsModels;
import com.example.restful.Repository.AnimalRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalService {

    protected final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
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
                animal.getDeathDateTime()
        );
    }
}
