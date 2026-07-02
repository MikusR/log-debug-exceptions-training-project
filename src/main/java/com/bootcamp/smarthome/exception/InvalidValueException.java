package com.bootcamp.smarthome.exception;

import java.util.Objects;

public class InvalidValueException extends HomeAutomationException {

    public InvalidValueException(String field, Object value, String constraint) {
        super(field + " " + Objects.toString(value) + " " + constraint);
    }
}
