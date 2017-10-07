package com.edu.acme.message;

import com.edu.acme.exception.InvalidMessageException;

public interface Validator {
    void validate(String message) throws InvalidMessageException;
}
