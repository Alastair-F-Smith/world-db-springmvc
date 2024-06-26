package org.example.worlddbspringmvc.service;

import org.example.worlddbspringmvc.model.entities.CityEntity;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.exception.CityAlreadyExistsException;
import org.example.worlddbspringmvc.model.respositories.CityEntityRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private final CityEntityRepository cityEntityRepository;

    public CityService(CityEntityRepository cityEntityRepository) {
        this.cityEntityRepository = cityEntityRepository;
    }

    public CityEntity createCity(CityEntity city) {
        return cityEntityRepository.save(city);
    }

    public List<CityEntity> getAllCities() {
        return cityEntityRepository.findAll();
    }

    public Optional<CityEntity> getCityById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return cityEntityRepository.findById(id);
    }

    public CityEntity updateCity(Integer id, CityEntity updatedCity) {
        Optional<CityEntity> cityOptional = cityEntityRepository.findById(id);
        if (cityOptional.isPresent()) {
            CityEntity existingCity = cityOptional.get();
            existingCity.setName(updatedCity.getName());
            existingCity.setCountryCode(updatedCity.getCountryCode());
            existingCity.setDistrict(updatedCity.getDistrict());
            existingCity.setPopulation(updatedCity.getPopulation());

            return cityEntityRepository.save(existingCity);
        } else {
            return null;
        }
    }

    public boolean deleteCity(Integer id) {
        Optional<CityEntity> cityOptional = cityEntityRepository.findById(id);
        if (cityOptional.isPresent()) {
            cityEntityRepository.delete(cityOptional.get());
            return true;
        } else {
            return false;
        }
    }
    public void deleteCitiesByCountryCode(CountryEntity countryEntity) {
        List<CityEntity>  cityEntities = cityEntityRepository.getCityByCountryCode(countryEntity);
        for (CityEntity cityEntity : cityEntities) {
            deleteCity(cityEntity.getId());
        }
    }

    public List<CityEntity> findCitiesByName(String name){
        return cityEntityRepository.findByName(name);
    }

    public void checkCityDoesNotExist(CityEntity city) throws CityAlreadyExistsException {
        if (getCityById(city.getId()).isPresent()) {
            throw new CityAlreadyExistsException(city.getId());
        }
    }

    public List<CityEntity> getCitiesByCountry(CountryEntity country) {
        List<CityEntity>  cityEntities = cityEntityRepository.getCityByCountryCode(country);
        return cityEntities;
    }
}