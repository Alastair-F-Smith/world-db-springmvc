package org.example.worlddbspringmvc.service;

import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.entities.CountryLanguageEntity;
import org.example.worlddbspringmvc.model.entities.CountryLanguageEntityId;
import org.example.worlddbspringmvc.model.respositories.CountryLanguageEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CountryLanguageService {

    private final CountryLanguageEntityRepository countrylanguageEntityRepository;

    @Autowired
    public CountryLanguageService(CountryLanguageEntityRepository countrylanguageEntityRepository) {
        this.countrylanguageEntityRepository = countrylanguageEntityRepository;
    }

    public CountryLanguageEntity createCountryLanguage(CountryLanguageEntity countryLanguage) {
        return countrylanguageEntityRepository.save(countryLanguage);
    }

    public List<CountryLanguageEntity> getAllCountryLanguages() {
        return countrylanguageEntityRepository.findAll();
    }

    public Optional<CountryLanguageEntity> getCountryLanguageById(CountryLanguageEntityId id) {
        return countrylanguageEntityRepository.findById(id);
    }

    public CountryLanguageEntity updateCountryLanguage(CountryLanguageEntityId id, CountryLanguageEntity updatedCountryLanguage) {
        Optional<CountryLanguageEntity> countryLanguageOptional = countrylanguageEntityRepository.findById(id);
        if (countryLanguageOptional.isPresent()) {
            CountryLanguageEntity existingCountryLanguage = countryLanguageOptional.get();
            existingCountryLanguage.setCountryCode(updatedCountryLanguage.getCountryCode());
            existingCountryLanguage.setIsOfficial(updatedCountryLanguage.getIsOfficial());
            existingCountryLanguage.setPercentage(updatedCountryLanguage.getPercentage());

            return countrylanguageEntityRepository.save(existingCountryLanguage);
        } else {
            return null;
        }
    }

    public boolean deleteCountryLanguage(CountryLanguageEntityId id) {
        Optional<CountryLanguageEntity> countryLanguageOptional = countrylanguageEntityRepository.findById(id);
        if (countryLanguageOptional.isPresent()) {
            countrylanguageEntityRepository.delete(countryLanguageOptional.get());
            return true;
        } else {
            return false;
        }

    }

    public List<CountryLanguageEntity> getCountryLanguagesByCountry(String countryCode) {
        return getAllLanguageCountrySpeaks(countryCode);
    }


    private List<CountryLanguageEntity> getAllLanguageCountrySpeaks(String countryCode){
        List<CountryLanguageEntity> countries = new ArrayList<>();
        for(CountryLanguageEntity countrylanguageEntity : countrylanguageEntityRepository.findAll()){
            if(countrylanguageEntity.getCountryCode().getCode().equals(countryCode)){
                countries.add(countrylanguageEntity);
            }
        }
        return countries;
    }


}