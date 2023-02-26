package com.nure.kravchenko.student.reference.exception;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Data
@Setter
@Getter
@Builder
public class ErrorValidationResponse implements Serializable {

    private String errorCode;
    private Map errorDescription;

    public ErrorValidationResponse() {
    }

    public ErrorValidationResponse(String errorCode, Map errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorValidationResponse that = (ErrorValidationResponse) o;
        return Objects.equals(errorCode, that.errorCode) && Objects.equals(errorDescription, that.errorDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorDescription);
    }
}
