package com.nure.kravchenko.student.reference.exception;

public class NureEmailException  extends RuntimeException {

    private Object value;

    /**
     * Instantiates a new nure email exception.
     *
     * @param message the message
     */
    public NureEmailException(String message) {
        super(message);
    }

    /**
     * Instantiates a new nure email exception.
     *
     * @param message the message
     * @param value the not found value
     */
    public NureEmailException(String message, Object value) {
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


