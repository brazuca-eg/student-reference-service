package com.nure.kravchenko.student.reference.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class CreateStudentPayload {
    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    @NotEmpty
    private String fatherhood;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFatherhood() {
        return fatherhood;
    }

    public void setFatherhood(String fatherhood) {
        this.fatherhood = fatherhood;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
