package org.example.worlddbspringmvc.controllers.web;

import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.respositories.CountryEntityRepository;
import org.example.worlddbspringmvc.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class CountryWebController {


    private final CountryEntityRepository countryEntityRepository;
    private final CountryService countryService;
    @Autowired
    public CountryWebController(CountryEntityRepository countryEntityRepository, CountryService countryService) {
        this.countryEntityRepository = countryEntityRepository;
        this.countryService = countryService;
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


    @GetMapping("/country")
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

    @GetMapping("/country/edit/{id}")
    public String getEditCountryForm(@PathVariable String id, Model model){
        CountryEntity countryEntity = countryEntityRepository.findById(id).orElse(null);
        model.addAttribute("country", countryEntity);
        return "countries/edit-country";
    }

    @GetMapping("/country/add")
    public String getAddCountry(Model model) {
        CountryEntity countryEntity = new CountryEntity();
        model.addAttribute("country", countryEntity);
        return "countries/add-country";
    }

    @PostMapping("/country/add")
    public String addCountry(@ModelAttribute("country") CountryEntity countryEntity){
        countryEntityRepository.save(countryEntity);
        return "redirect:/countries";
    }


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

    @GetMapping("/country/delete/{countryCode}")
    public String deleteCountry(@PathVariable String countryCode){
        countryService.deleteCountry(countryCode);
        return "redirect:/countries";
    }
}
