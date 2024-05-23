package org.example.worlddbspringmvc.controller.web;

import org.example.worlddbspringmvc.controllers.web.CityWebController;
import org.example.worlddbspringmvc.model.entities.CityEntity;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.service.CityService;
import org.example.worlddbspringmvc.service.CountryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@AutoConfigureMockMvc
public class CityControllerWebTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CityService cityService;

    @MockBean
    CountryService countryService;

    @Test
    @DisplayName("Tests cities page")
    void testsCitiesPage() throws Exception {
        mockMvc.perform(get("/cities"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test welcome page")
    void testWelcomePage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Test editCity when city does not exist")
    void testEditCityWhenCityDoesNotExist() throws Exception {

        mockMvc.perform(get("/city/edit/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Test updateCity with valid data")
    void testUpdateCityWithValidData() throws Exception {
        CountryEntity country = new CountryEntity();
        country.setCode("AFG");

        when(countryService.getCountryByCode("AFG")).thenReturn(Optional.of(country));

        mockMvc.perform(MockMvcRequestBuilders.post("/city/edit/11")
                        .param("name", "New jimmy")
                        .param("countryCode", "AFG")
                        .param("district", "Some District")
                        .param("population", "100000")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cities"));
    }


    @Test
    @DisplayName("Given valid citi")
    void givenValidCitiesNames() throws Exception {
        mockMvc.perform(get("/city/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("city"))
                .andExpect(model().attributeExists("countries"))
                .andExpect(view().name("addCity"));
    }


    @Test
    @DisplayName("Test saveCity method with valid data")
    void testSaveCityValidData() throws Exception {
        String countryCode = "AFG";
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode(countryCode);
        when(countryService.getCountryByCode(countryCode)).thenReturn(Optional.of(countryEntity));
        System.out.println(countryEntity);
        mockMvc.perform(post("/city/save")
                        .param("name", "Herattest")
                        .param("countryCode", "AFG")
                        .param("district", "Herat")
                        .param("population", "100000"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/cities"));
        }




    @Test
    @DisplayName("Given invalid cities id, return bad request")
    void givenInvalidCitiesIdReturnBadRequest() throws Exception {
        when(cityService.getCityById(anyInt()))
                .thenReturn(Optional.empty());
        mockMvc.perform(get("/city/999999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given a delete request for a city which exists, return ok status")
    void givenADeleteRequestForACityWhichExistsReturnOkStatus() throws Exception {
        CityEntity returnedCity = new CityEntity();
        returnedCity.setId(1);
        when(cityService.getCityById(anyInt()))
                .thenReturn(Optional.of(returnedCity));

        mockMvc.perform(get("/city/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given a delete request for a city which don't exists, return bad request status")
    void givenADeleteRequestForACityWhichDonTExistsReturnBadRequestStatus() throws Exception {
        when(cityService.getCityById(anyInt()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/city/delete/1"))
                .andExpect(status().is4xxClientError());
    }
}
