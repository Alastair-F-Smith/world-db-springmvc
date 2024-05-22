package org.example.worlddbspringmvc.controllers.web;

import org.example.worlddbspringmvc.model.entities.CountryLanguageEntity;
import org.example.worlddbspringmvc.model.entities.CountryLanguageEntityId;
import org.example.worlddbspringmvc.service.CountryLanguageService;
import org.example.worlddbspringmvc.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.logging.Logger;

@Controller
public class CountryLanguageWebController {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private final CountryLanguageService countryLanguageService;
    private final CountryService countryService;

    @Autowired
    public CountryLanguageWebController(CountryLanguageService countryLanguageService, CountryService countryService) {
        this.countryLanguageService = countryLanguageService;
        this.countryService = countryService;
    }

    @GetMapping("/languages")
    public String getLanguages(Model model) {
        model.addAttribute("languages", countryLanguageService.getAllCountryLanguages());
        return "languages";
    }

    @GetMapping("/language/edit/{countryCode}/{language}")
    public String getEditLanguageForm(@PathVariable String countryCode, @PathVariable String language, Model model) {
        CountryLanguageEntityId id = new CountryLanguageEntityId();
        id.setLanguage(language);
        id.setCountryCode(countryCode);
        CountryLanguageEntity countryLanguage = countryLanguageService.getCountryLanguageById(id).orElse(null);
        model.addAttribute("language", countryLanguage);
        return "edit-language";
    }

    @PostMapping("/language/edit/{countryCode}/{language}")
    public String editLanguage(@PathVariable String countryCode, @PathVariable String language,
                               @RequestParam(name = "is-official") String isOfficial,
                               @RequestParam BigDecimal percentage) {
        CountryLanguageEntityId id = new CountryLanguageEntityId(countryCode, language);
        logger.info("Updating language: " + language + " with percentage = " + percentage);
        if (countryLanguageService.getCountryLanguageById(id).isPresent()) {
            CountryLanguageEntity languageEntity = new CountryLanguageEntity(id, isOfficial, percentage);
            languageEntity.setCountryCode(countryService.getCountryByCode(countryCode).orElseThrow());
            countryLanguageService.updateCountryLanguage(id, languageEntity);
        }
        return "redirect:/languages";
    }


    @GetMapping("/language/delete/{countryCode}/{language}")
    public String deleteLanguage(@PathVariable String countryCode, @PathVariable String language){
        CountryLanguageEntityId id = new CountryLanguageEntityId();
        id.setCountryCode(countryCode);
        id.setLanguage(language);
        countryLanguageService.deleteCountryLanguage(id);
        return "redirect:/languages";
    }

    @PostMapping("/language/add")
    public String addLanguage(@ModelAttribute CountryLanguageEntity languageEntity){
        countryLanguageService.createCountryLanguage(languageEntity);
        return "redirect:/languages";
    }

//    @GetMapping

}
