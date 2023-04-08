package com.nure.kravchenko.student.reference.exception;

public class InvalidProvidedDataException  extends RuntimeException {

    private Object value;

    /**
     * Instantiates a new invalid provided data exception.
     *
     * @param message the message
     */
    public InvalidProvidedDataException(String message) {
        super(message);
    }

    /**
     * Instantiates a new invalid provided data exception.
     *
     * @param message the message
     * @param value the not found value
     */
    public InvalidProvidedDataException(String message, Object value) {
        super(message);
        this.value = value;
    }

    /**
     * Gets the value of the exception.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

}
