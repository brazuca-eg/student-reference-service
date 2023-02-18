package com.nure.kravchenko.student.reference.exception;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Data
@Setter
@Getter
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = 6837112666549119686L;

    private String errorCode;
    private String errorDescription;

    public ErrorResponse errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public ErrorResponse errorDescription(String errorDescriptionIn) {
        this.errorDescription = errorDescriptionIn;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ErrorResponse that = (ErrorResponse) o;
        return Objects.equals(errorCode, that.errorCode) &&
            Objects.equals(errorDescription, that.errorDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorDescription);
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
            "errorCode='" + errorCode + '\'' +
            ", errorDescription='" + errorDescription + '\'' +
            '}';
    }
}
