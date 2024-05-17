package org.example.dungeonsanddebugerss.controller;

import org.example.dungeonsanddebugerss.controllers.CityController;
import org.example.dungeonsanddebugerss.model.entities.CityEntity;
import org.example.dungeonsanddebugerss.model.exception.KeyNotFoundException;
import org.example.dungeonsanddebugerss.service.CityService;
import org.example.dungeonsanddebugerss.service.CountryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CityController.class)
public class CityControllerMockTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CityService cityService;

    @MockBean
    CountryService countryService;


    @Test
    @DisplayName("Given a delete request for a city id that is in the database, return a 200 status code")
    void givenADeleteRequestForACityIdThatIsInTheDatabaseReturnA200StatusCode() throws Exception {
        int cityId = 100;
        CityEntity returnedCity = new CityEntity();
        returnedCity.setId(cityId);
        when(cityService.getCityById(anyInt()))
                .thenReturn(Optional.of(returnedCity));

        mockMvc.perform(delete("/api/city/" + cityId).param("key", "test"))
               .andExpect(status().isOk());

//        webTestClient.delete()
//                     .uri("http://localhost:8080/api/city/" + cityId + "?key=test")
//                     .exchange()
//                .expectStatus()
//                .is2xxSuccessful();
    }

    @Test
    @DisplayName("Given a delete request for a city id that is in the database, the response body contains a city with that id")
    void givenADeleteRequestForACityIdThatIsInTheDatabaseTheResponseBodyContainsACityWithThatId() {
        int cityId = 100;
        CityEntity returnedCity = new CityEntity();
        returnedCity.setId(cityId);

        when(cityService.getCityById(anyInt()))
                .thenReturn(Optional.of(returnedCity));

//        mockMvc.perform(delete("/api/city/" + cityId).param("key", "test"))
//                .andExpect(jsonPath("$.id", is()));

//        webTestClient.delete()
//                     .uri("http://localhost:8080/api/city/" + cityId + "?key=test")
//                     .exchange()
//                     .expectBody(CityEntity.class)
//                .value(city -> Assertions.assertEquals(cityId, city.getId()));
    }

    @Test
    @DisplayName("Given a delete request without an API key, a 401 response code should be returned")
    void givenADeleteRequestWithoutAnApiKeyA403ResponseCodeShouldBeReturned() {
        int cityId = 100;
        CityEntity returnedCity = new CityEntity();
        returnedCity.setId(cityId);
        when(cityService.getCityById(anyInt()))
                .thenReturn(Optional.of(returnedCity));
//        webTestClient.delete()
//                     .uri("http://localhost:8080/api/city/" + cityId)
//                     .exchange()
//                .expectStatus()
//                .isUnauthorized();
    }

    @Test
    @DisplayName("Given a delete request for a city id that does not exist, returns a 400 response code")
    void givenADeleteRequestForACityIdThatDoesNotExistReturnsA400ResponseCode() throws Exception {
        int cityId = 9999;

        mockMvc.perform(delete("/api/city/" + cityId))
                .andExpect(status().isUnauthorized());
    }
}
