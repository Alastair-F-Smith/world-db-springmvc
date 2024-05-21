package org.example.worlddbspringmvc.model.exception;

public class CountryIsNullException extends Exception{

    public CountryIsNullException() {
        super("Country provided is null!");
    }
}
