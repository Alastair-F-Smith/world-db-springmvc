package org.example.worlddbspringmvc.model.exception;

public class CountryLanguageNotFoundException extends Exception{
    public CountryLanguageNotFoundException(String error){
        super(error);
    }
}
