package org.example.worlddbspringmvc.model.exception;

public class CityAlreadyExistsException extends Exception{

    public CityAlreadyExistsException(int cityId) {
        super("City with ID " + cityId + " already exists.");
    }
}
