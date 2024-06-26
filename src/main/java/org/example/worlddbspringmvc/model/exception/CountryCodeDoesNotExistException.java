package org.example.worlddbspringmvc.model.exception;

public class CountryCodeDoesNotExistException extends Exception  {
    public CountryCodeDoesNotExistException(String countryCode) {
        super("Country code " + countryCode + " does not exist");
    }
}
