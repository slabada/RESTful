package com.example.restful.Controllers;

import com.example.restful.Models.LocationModels;
import com.example.restful.Service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("locations")
public class LocationsController {

    protected final LocationService locationService;

    public LocationsController(LocationService locationService){
        this.locationService = locationService;
    }

    @GetMapping("{pointId}")
    public ResponseEntity<Optional<LocationModels>> GetInfoPoint(@PathVariable Long pointId){

        if(pointId == null || pointId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<LocationModels> location = locationService.GetLocationId(pointId);

        if(location.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(location);
    }

    @PostMapping()
    public ResponseEntity<LocationModels> AddPoint(@RequestParam Double latitude,
                                                   @RequestParam Double longitude){

        if((latitude > 90 || latitude < -90) && (longitude > 180 || longitude < -180)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        LocationModels location = locationService.AddLocation(latitude, longitude);

        if(location == null){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(location);
    }

    @PutMapping("{pointId}")
    public ResponseEntity<Optional<LocationModels>> UpdateLocation(@PathVariable Long pointId,
                                                                   @RequestParam Double latitude,
                                                                   @RequestParam Double longitude){

        if((latitude > 90 || latitude < -90) && (longitude > 180 || longitude < -180)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Optional<LocationModels> location = locationService.GetLocationId(pointId);

        if(location.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        if(!locationService.UpdateLocation(pointId,latitude,longitude)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(location);
    }

    @DeleteMapping("{pointId}")
    public ResponseEntity<HttpStatus> DeleteLocation(@PathVariable Long pointId){

        if(pointId == null || pointId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(!locationService.DeleteLocation(pointId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
