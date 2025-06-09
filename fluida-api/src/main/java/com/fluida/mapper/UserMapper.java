package com.fluida.mapper;

import com.fluida.model.Customer;
import com.fluida.dto.CustomerResponse;

public class UserMapper {

    public static CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail()
        );
    }
}