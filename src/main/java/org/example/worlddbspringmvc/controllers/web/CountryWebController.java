package org.example.worlddbspringmvc.controllers.web;

import org.example.worlddbspringmvc.model.entities.CityEntity;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.entities.CountryLanguageEntity;
import org.example.worlddbspringmvc.model.respositories.CountryEntityRepository;
import org.example.worlddbspringmvc.model.respositories.CountryLanguageEntityRepository;
import org.example.worlddbspringmvc.service.CityService;
import org.example.worlddbspringmvc.service.CountryLanguageService;
import org.example.worlddbspringmvc.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Controller
public class CountryWebController {

    private final CountryEntityRepository countryEntityRepository;
    private final CountryService countryService;
    private final CityService cityService;
    private final CountryLanguageService countryLanguageService;
    private final CountryLanguageEntityRepository countryLanguageEntityRepository;

    @Autowired
    public CountryWebController(CountryEntityRepository countryEntityRepository, CountryService countryService, CityService cityService, CountryLanguageService countryLanguageService, CountryLanguageEntityRepository countryLanguageEntityRepository) {
        this.countryEntityRepository = countryEntityRepository;
        this.countryService = countryService;
        this.cityService = cityService;
        this.countryLanguageService = countryLanguageService;
        this.countryLanguageEntityRepository = countryLanguageEntityRepository;
    }



    //Country
    //
    ///countries
    ///country?name=France
    ///country/{id}
    ///country/add
    ///country/edit/{id}
    ///country/delete/{id}


    @GetMapping("/countries")
    public String getCountries(Model model){
        model.addAttribute("countries", countryService.getAllCountries());
        return "countries/countries";
    }


;    @GetMapping("/country")
    public String getCountryByName(@RequestParam String name, Model model){
        model.addAttribute("country", countryEntityRepository.findByName(name));
        return "countries/country";
    }

    @GetMapping("/country/{id}")
    public String getCountryById(@PathVariable String id, Model model){
        CountryEntity countryEntity = countryEntityRepository.findById(id).orElse(null);
        model.addAttribute("country", countryEntity);
        return "countries/country";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/country/edit/{id}")
    public String getEditCountryForm(@PathVariable String id, Model model){
        CountryEntity countryEntity = countryEntityRepository.findById(id).orElse(null);
        model.addAttribute("country", countryEntity);
        return "countries/edit-country";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/country/add")
    public String getAddCountry(Model model) {
        CountryEntity countryEntity = new CountryEntity();
        model.addAttribute("country", countryEntity);
        return "countries/add-country";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/country/add")
    public String addCountry(@ModelAttribute("country") CountryEntity countryEntity){
        countryEntityRepository.save(countryEntity);
        return "redirect:/countries";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/country/edit/{countryCode}")
    public String editCountry(@PathVariable String countryCode, @ModelAttribute("country") CountryEntity countryEntity, Model model){
        CountryEntity countryToEdit = countryService.getCountryByCode(countryCode).orElse(null);
        if(countryToEdit != null){
            countryToEdit.setName(countryEntity.getName());
            countryToEdit.setPopulation(countryEntity.getPopulation());
            countryService.updateCountry(countryCode, countryToEdit);
        }
        return "redirect:/countries";

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/country/delete/{countryCode}")
    public String deleteCountry(@PathVariable String countryCode) {
        Optional<CountryEntity> countryOptional = countryService.getCountryByCode(countryCode);
        if (countryOptional.isPresent()) {
            CountryEntity countryToDelete = countryOptional.get();

            List<CityEntity> cities = cityService.getCitiesByCountry(countryToDelete);
            for (CityEntity city : cities) {
                cityService.deleteCity(city.getId());
            }

            List<CountryLanguageEntity> countryLanguages = countryLanguageService.getCountryLanguagesByCountry(countryCode);
            for(CountryLanguageEntity countryLanguage : countryLanguages){
                countryLanguageService.deleteCountryLanguage(countryLanguage.getId());
            }
            countryService.deleteCountry(countryToDelete.getCode());
        }
        return "redirect:/countries";
    }
};
