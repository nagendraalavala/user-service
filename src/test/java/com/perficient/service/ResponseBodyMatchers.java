package com.perficient.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Use these classes to verify the content of JSON objects returned by the server. Use of these matchers
 * for field validation error messages assumes an ExceptionHandler annotated with @ControllerAdvice
 * formatted the message body as described in this <a href="http://reflectoring.io/spring-boot-web-controller-test/#6-verifying-exception-handling">article</a>.
 *
 * @see ErrorResult
 * @see FieldValidationError
 */
@SuppressFBWarnings(
        value = "RV_RETURN_VALUE_IGNORED",
        justification = "Need to investigate why assert4j code does not pass spotbugs checks")
public class ResponseBodyMatchers {
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Use this method to verify a message body contains the expected JSON object.
     *
     * @param expectedObject the values the object should contain
     * @param targetClass    an instance of the class expected
     * @param <T>            the type of class expected
     * @return the matched result.
     */
    public <T> ResultMatcher containsObjectAsJson(
            final Object expectedObject,
            final Class<T> targetClass) {
        return mvcResult -> {
            final String json = mvcResult.getResponse().getContentAsString();
            final T actualObject = objectMapper.readValue(json, targetClass);
            assertThat(expectedObject).isEqualToComparingFieldByField(actualObject);
        };
    }

    /**
     * Use this method to verify the message body contains a list expected JSON objects. Note the
     * objects in the message body must be in the same order as the list of expected objects.
     *
     * @param expectedObject the values the list of objects should contain.
     * @param targetClass    an instance of the class expected organized by the list.
     * @param <T>            the type of class expected organized by the list.
     * @return the matched result.
     */
    public <T> ResultMatcher containsListAsJson(
            final List<T> expectedObject,
            final Class<T> targetClass) {
        return mvcResult -> {
            final String json = mvcResult.getResponse().getContentAsString();
            final List<T> actualObject = objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionLikeType(List.class, targetClass));
            assertThat(expectedObject.size()).isEqualTo(actualObject.size());
            for (int index = 0; index < expectedObject.size(); index++) {
                assertThat(expectedObject.get(index)).isEqualToComparingFieldByField(actualObject.get(index));
            }
        };
    }


    /**
     * Use this method to verify the message body contains the expected field validation error message.
     *
     * @param expectedFieldName the name of the field that is in error
     * @param expectedMessage   the type of error
     * @return the matched result
     */
    public ResultMatcher containsError(
            final String expectedFieldName,
            final String expectedMessage) {
        return mvcResult -> {
            final String json = mvcResult.getResponse().getContentAsString();
            final ErrorResult errorResult = objectMapper.readValue(json, ErrorResult.class);
            final List<FieldValidationError> fieldErrors = errorResult.getFieldErrors().stream()
                    .filter(fieldError -> fieldError.getField().equals(expectedFieldName))
                    .filter(fieldError -> fieldError.getMessage().equals(expectedMessage))
                    .collect(Collectors.toList());

            assertThat(fieldErrors)
                    .hasSize(1)
                    .withFailMessage("expecting exactly 1 error message with field name '%s' and message '%s'",
                            expectedFieldName,
                            expectedMessage);
        };
    }

    public static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }
}