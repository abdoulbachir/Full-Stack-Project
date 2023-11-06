package com.bachir.customer;

public record CustomerUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
