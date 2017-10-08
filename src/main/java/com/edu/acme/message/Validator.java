package com.edu.acme.message;

public interface Validator {
    boolean validate(String message);
    String getErrorDescription(String message);
}
