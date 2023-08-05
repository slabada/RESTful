package com.example.restful.Repository;

import com.example.restful.Models.LocationModels;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationModels, Long> {

    LocationModels findByLatitudeAndLongitude(double latitude, double longitude);
}
