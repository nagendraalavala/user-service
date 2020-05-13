package com.perficient.service;

import java.util.ArrayList;
import java.util.List;

/**
 * Use this class when deserializing JSON error messages describing field validation errors from the
 * server.
 *
 * @see http://reflectoring.io/spring-boot-web-controller-test/#6-verifying-exception-handling
 * @see FieldValidationError
 * @see ResponseBodyMatchers
 */
public class ErrorResult {
    private final List<FieldValidationError> fieldErrors = new ArrayList<>();

    public ErrorResult() {
        // This constructor is intentionally empty. Nothing special is needed here.
    }

    public ErrorResult(final String field, final String message) {
        this.fieldErrors.add(new FieldValidationError(field, message));
    }

    public List<FieldValidationError> getFieldErrors() {
        return fieldErrors;
    }
}
