package com.perficient.service;

import java.util.*;

import com.perficient.service.types.User;

/**
 * Use this class to hold constants and methods used by multiple test classes.
 *
 */
public final class TestUtils {

    public static final String DEFAULT_PASSWORD = "abc123";
    public static final String DEFAULT_USERNAME = "user@example.com";
    public static final String DEFAULT_FIRST_NAME = "first";
    public static final String DEFAULT_LAST_NAME = "last";

    // since this class has only static methods, make sure no one can create an instance
    private TestUtils() {
    }

    public static User buildDefaultUser() {
        final Date userCreated = new Date();

        final User user = new User();
        user.setUsername(DEFAULT_USERNAME);
        user.setPassword(DEFAULT_PASSWORD);
        user.setDateCreated(userCreated);
        user.setFirstName(DEFAULT_FIRST_NAME);
        user.setLastName(DEFAULT_LAST_NAME);
        
        return user;
    }

    public static <T> int iterableSize(final Iterable<T> iterable) {
        final List<T> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list.size();
    }

}
