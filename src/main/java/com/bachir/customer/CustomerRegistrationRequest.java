package com.bachir.customer;

public record CustomerRegistrationRequest (
            String name,
            String email,
            Integer age
){

}