package org.example.worlddbspringmvc.model.exception;

public class CityDoesNotExistException extends Exception {

    public CityDoesNotExistException(String name){
        super("Could not find city: " + name);
    }
}
