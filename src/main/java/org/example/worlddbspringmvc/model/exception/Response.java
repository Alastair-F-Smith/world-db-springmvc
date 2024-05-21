package org.example.worlddbspringmvc.model.exception;

public record Response (String message, int statusCode, String url) {
}
