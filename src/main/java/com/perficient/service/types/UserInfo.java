package com.perficient.service.types;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo
{
    private String username;

    private String password;

    private Date dateCreated;

    private String firstName;

    private String lastName;

    private String countryCode;

    private String zipCode;

    private String countryCurrency;

    private String quote;
}
