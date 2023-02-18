package com.nure.kravchenko.student.reference.exception;


public class NotFoundException extends RuntimeException {

    private static final long ser = -301000626141059112L;

    private Object value;

    /**
     * Instantiates a new not found exception.
     *
     * @param message the message
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new not found exception.
     *
     * @param message the message
     * @param value the not found value
     */
    public NotFoundException(String message, Object value) {
        super(message);
        this.value = value;
    }

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Object getValue() {
        return value;
    }

}
