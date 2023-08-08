package com.example.restful.Service;

import com.example.restful.Models.AnimalsModels;
import com.example.restful.Models.LocationModels;
import com.example.restful.Repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class LocationService {

    protected final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository){
        this.locationRepository = locationRepository;
    }

    public Optional<LocationModels> GetLocationId(long id){
        return locationRepository.findById(id);
    }

    public LocationModels AddLocation(double latitude, double longitude){

        LocationModels location = locationRepository.findByLatitudeAndLongitude(latitude, longitude);

        if(location != null){
            return null;
        }

        LocationModels response = new LocationModels();

        response.setLongitude(longitude);
        response.setLatitude(latitude);

        locationRepository.save(response);

        return response;
    }

    public boolean UpdateLocation(Long pointId, Double latitude, Double longitude){

        Optional<LocationModels> locationId = locationRepository.findById(pointId);

        LocationModels locationDb = locationRepository.findByLatitudeAndLongitude(latitude, longitude);

        if(locationDb != null || locationId.isEmpty()){
            return false;
        }

        LocationModels locationUpdate = locationId.get();

        locationUpdate.setLatitude(latitude);
        locationUpdate.setLongitude(longitude);

        locationRepository.save(locationUpdate);

        return true;
    }

    public boolean DeleteLocation(Long pointId){

        Optional<LocationModels> locationDb = locationRepository.findById(pointId);

        if(locationDb.isEmpty()){
            return false;
        }

        locationRepository.deleteById(pointId);

        return true;
    }

    public Set<LocationModels> CheckVisitedLocationId(AnimalsModels animal){
        Set<LocationModels> existingLocations = new HashSet<>();
        if(animal.getVisitedLocations() != null){
            for(LocationModels location : animal.getVisitedLocations()){
                LocationModels existingLocation = locationRepository.findByLatitudeAndLongitude(
                        location.getLatitude(),
                        location.getLatitude());

                if(existingLocation != null){
                    existingLocations.add(existingLocation);
                }else {
                    return null;
                }
            }
        }
        else {
            return null;
        }

        return existingLocations;
    }

}
