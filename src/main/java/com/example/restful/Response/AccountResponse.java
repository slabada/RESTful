package com.example.restful.Response;

import lombok.Data;

@Data
public class AccountResponse {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public AccountResponse(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
