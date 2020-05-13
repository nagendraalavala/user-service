package com.perficient.service.exception;

/**
 * Use this exception when detecting that an entity already exists. The audience for the message include
 * the consumers of RESTFul services and those reading log files.
 */
public class DuplicateEntityException extends Exception {
    private static final long serialVersionUID = -42L;
    public DuplicateEntityException(final String message) {
        super(message);
    }
}
