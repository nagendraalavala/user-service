package com.perficient.service;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Use this class to describe an instance of a Spring MVC validation error within the body of a RESTFul
 * response message.
 *
 * @see http://reflectoring.io/spring-boot-web-controller-test/#6-verifying-exception-handling
 * @see ResponseBodyMatchers
 * @see ErrorResult
 */
public class FieldValidationError {

    private String field;

    private String message;

    public FieldValidationError(final @JsonProperty("field") String field, final @JsonProperty("message") String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public void setField(final String field) {
        this.field = field;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}