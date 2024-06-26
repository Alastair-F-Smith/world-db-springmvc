package org.example.worlddbspringmvc.controllers.web;

import org.example.worlddbspringmvc.model.entities.CityEntity;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.exception.CityDoesNotExistException;
import org.example.worlddbspringmvc.model.exception.CountryNotFoundException;
import org.example.worlddbspringmvc.service.CityService;
import org.example.worlddbspringmvc.service.CountryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class CityWebController {

    private final CityService cityService;
    private final CountryService countryService;

    public CityWebController(CityService cityService, CountryService countryService) {
        this.cityService = cityService;
        this.countryService = countryService;
    }

    @RequestMapping("/cities")
    public String getCities(String cityName, Model model){
        if(cityName == null){
            model.addAttribute("cities", cityService.getAllCities());
        }else{
            model.addAttribute("cities", findByCityNameContains(cityName));
        }
        return "cities";
    }

    @GetMapping("/city/{id}")
    public String getCityById(@PathVariable int id, Model model) throws CityDoesNotExistException {
        if(cityService.getCityById(id).isPresent()){
            model.addAttribute("city", cityService.getCityById(id));
            return "cities";
        }
        throw new CityDoesNotExistException("City with id: " + id + " does not exists");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/city/delete/{id}")
    public String deleteCityById(@PathVariable int id) throws CityDoesNotExistException {
        if(cityService.deleteCity(id)){
            return "redirect:/cities";
        }else{
            throw new CityDoesNotExistException("City with id: " + id + " does not exists");
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/city/add")
    public String addCity(Model model) {
        CityEntity city = new CityEntity();
        model.addAttribute("city", city);
        model.addAttribute("countries", sortCountries(countryService.getAllCountries()));
        return "addCity";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/city/save")
    public String saveCity(@RequestParam String name,
                           @RequestParam String countryCode,
                           @RequestParam String district,
                           @RequestParam int population) throws CountryNotFoundException {
        CountryEntity country = countryService.getCountryByCode(countryCode).orElseThrow(()->new CountryNotFoundException("Country not found"));
        CityEntity city = new CityEntity();
        city.setName(name);
        city.setCountryCode(country);
        city.setDistrict(district);
        city.setPopulation(population);
        cityService.createCity(city);
        return "redirect:/cities";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/city/edit/{id}")
    public String editCity(@PathVariable int id, Model model) throws CityDoesNotExistException {
        CityEntity city = cityService.getCityById(id).orElseThrow(()->new CityDoesNotExistException(""));
        model.addAttribute("city", city);
        model.addAttribute("countries", sortCountries(countryService.getAllCountries()));
        return "editCity";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/city/edit/{id}")
    public String updateCity(@RequestParam("name") String name,
                             @RequestParam("countryCode") String countryCode,
                             @RequestParam("district") String district,
                             @RequestParam("population") int population,
                             @PathVariable int id) throws CountryNotFoundException {
        CountryEntity country = countryService.getCountryByCode(countryCode).orElseThrow(() -> new CountryNotFoundException(countryCode));

        CityEntity newCity = new CityEntity();
        newCity.setName(name);
        newCity.setCountryCode(country);
        newCity.setDistrict(district);
        newCity.setPopulation(population);
        cityService.updateCity(id, newCity);
        return "redirect:/cities";
    }

    private List<CityEntity> findByCityNameContains(String name){
        List<CityEntity> cities = new ArrayList<>();
        for(CityEntity city: cityService.getAllCities()){
            if(city.getName().toLowerCase().contains(name.toLowerCase())){
                cities.add(city);
            }
            if(city.getCountryCode().getCode().equalsIgnoreCase(name)){
                cities.add(city);
            }
        }
        return cities;
    }

    private List<CountryEntity> sortCountries(List<CountryEntity> countries){
        List<CountryEntity> sortedCountries = countryService.getAllCountries();
        sortedCountries.sort(Comparator.comparing(CountryEntity::getName));
        return sortedCountries;
    }
}
