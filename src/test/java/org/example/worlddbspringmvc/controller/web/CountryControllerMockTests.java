package org.example.worlddbspringmvc.controller.web;

import org.example.worlddbspringmvc.controllers.rest.CountryController;
import org.example.worlddbspringmvc.model.entities.CountryEntity;
import org.example.worlddbspringmvc.model.respositories.CountryEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
public class CountryControllerMockTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CountryEntityRepository countryEntityRepository;

    @Test
    @WithMockUser(username = "admin")
    @Order(1)
    void testCountriesPage() throws Exception {
        int statusCode = mockMvc.perform(get("/countries"))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, statusCode);
    }

    @Test
    @WithMockUser(username = "admin")
    @Order(2)
    void testCountryByNamePage() throws Exception {
        int statusCode = mockMvc.perform(get("/country?name=sweden"))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, statusCode);
    }

    @Test
    @WithMockUser(username = "admin")
    @Order(3)
    void testCountryByCodePage() throws Exception {
        int statusCode = mockMvc.perform(get("/country/AFG"))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, statusCode);
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    @Order(4)
    void testEditCountryPage() throws Exception {
        int statusCode = mockMvc.perform(get("/country/edit/AFG"))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, statusCode);
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    @Order(5)
    void testAddCountryPage() throws Exception {
        int statusCode = mockMvc.perform(get("/country/add"))
                .andReturn()
                .getResponse()
                .getStatus();

        assertEquals(200, statusCode);
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    @Order(6)
    void testAddCountry() throws Exception {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCode("TEL");
        countryEntity.setName("Testland");
        countryEntity.setContinent("North America");
        countryEntity.setRegion("Caribbean");
        countryEntity.setSurfaceArea(BigDecimal.valueOf(12345.67));
        countryEntity.setIndepYear((short) 2024);
        countryEntity.setPopulation(1000000);
        countryEntity.setLifeExpectancy(BigDecimal.valueOf(75.5));
        countryEntity.setGnp(BigDecimal.valueOf(123456.78));
        countryEntity.setGNPOld(BigDecimal.valueOf(123456.11));
        countryEntity.setLocalName("Testlandia");
        countryEntity.setGovernmentForm("Republic");
        countryEntity.setHeadOfState("John Doe");
        countryEntity.setCapital(1);
        countryEntity.setCode2("TL");

        ResultActions resultActions;
        resultActions = mockMvc.perform(post("/country/add").with(csrf())
                        .param("code", countryEntity.getCode())
                        .param("name", countryEntity.getName())
                        .param("continent", countryEntity.getContinent())
                        .param("region", countryEntity.getRegion())
                        .param("surfaceArea", String.valueOf(countryEntity.getSurfaceArea()))
                        .param("indepYear", String.valueOf(countryEntity.getIndepYear()))
                        .param("population", String.valueOf(countryEntity.getPopulation()))
                        .param("lifeExpectancy", String.valueOf(countryEntity.getLifeExpectancy()))
                        .param("gnp", String.valueOf(countryEntity.getGnp()))
                        .param("oldGnp", String.valueOf(countryEntity.getGNPOld()))
                        .param("localName", countryEntity.getLocalName())
                        .param("governmentForm", countryEntity.getGovernmentForm())
                        .param("headOfState", countryEntity.getHeadOfState())
                        .param("capital", String.valueOf(countryEntity.getCapital()))
                        .param("code2", countryEntity.getCode2()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/countries"));


        // Check if the entity was added to the database
        Optional<CountryEntity> savedCountryOptional = countryEntityRepository.findById("TEL");
        assertTrue(savedCountryOptional.isPresent(), "Country with code 'TEL' should be saved");
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    @Order(7)
    void testEditCountry() throws Exception {
        mockMvc.perform(post("/country/edit/TEL").with(csrf())
                        .param("name", "TestingLand")
                        .param("population", String.valueOf(2000000)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/countries"));

        Optional<CountryEntity> updatedCountryOptional = countryEntityRepository.findById("TEL");
        assertTrue(updatedCountryOptional.isPresent(), "Country with code 'TEL' should exist");
        CountryEntity updatedCountry = updatedCountryOptional.get();
        assertEquals("TestingLand", updatedCountry.getName());
        assertEquals(2000000, updatedCountry.getPopulation());
    }

    @Test
    @WithMockUser(username = "admin", authorities={"ROLE_ADMIN"})
    @Order(8)
    void testDeleteCountry() throws Exception {
        mockMvc.perform(get("/country/delete/TEL"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/countries"));

        assertFalse(countryEntityRepository.existsById("TEL"), "Country with code 'TEL' should be deleted");
    }


}
