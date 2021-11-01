package com.ead.authuser.domain;

import com.ead.authuser.domain.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public class UserDomainTestFactory {
    public static final String FULL_NAME = "User Full Name";
    public static final String USERNAME = "UserName";
    public static final String EMAIL = "user@email.com";
    public static final String PASSWORD = "pass@321";
    public static final String PHONE_NUMBER = "5547993847565";
//    generated at https://www.4devs.com.br/gerador_de_cpf only for dev test
    public static final String CPF = "73577817046";
    public static final String IMAGE_URL = "https://sites.google.com/site/minhasemoco/_/rsrc/1342875510991/config/Google%20-%20Google%20Chrome.jpg";
    public static final LocalDateTime CREATION_DATE = LocalDateTime.now(ZoneId.of("UTC"));
    public static final LocalDateTime LAST_UPDATE_DATE = LocalDateTime.now(ZoneId.of("UTC"));

    public static User instantiateAUser(){
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(USERNAME)
                .fullName(FULL_NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .phoneNumber(PHONE_NUMBER)
                .cpf(CPF)
                .imageUrl(IMAGE_URL)
                .creationDate(CREATION_DATE)
                .lastUpdateDate(LAST_UPDATE_DATE)
                .build();

        return user;
    }
}
