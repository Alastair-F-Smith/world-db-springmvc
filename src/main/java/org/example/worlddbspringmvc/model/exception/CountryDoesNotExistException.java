package org.example.worlddbspringmvc.model.exception;

public class CountryDoesNotExistException extends Exception{

    public CountryDoesNotExistException(String countryCode) {
        super("Cannot find country with code: " + countryCode);
    }
}
