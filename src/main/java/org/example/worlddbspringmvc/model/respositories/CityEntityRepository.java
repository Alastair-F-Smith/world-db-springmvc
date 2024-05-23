package org.example.worlddbspringmvc.model.respositories;

//import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.NotNull;
import org.example.worlddbspringmvc.model.entities.CityEntity;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//@Hidden
public interface CityEntityRepository extends JpaRepository<CityEntity, Integer> {

    List<CityEntity> findByName(String name);
    List<CityEntity> getCityByCountryCode(CountryEntity countryCode);
}