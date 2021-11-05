package com.ead.authuser.user.domain.model;

import com.ead.authuser.user.domain.UserDomainTestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName(value = "Deve criar com sucesso uma instancia de um usuário")
    void createUserSimpleBuildTest(){
//        Arrange
        User user = UserDomainTestFactory.instantiateAUser();

//        Assertion
        Assertions.assertEquals(UserDomainTestFactory.USERNAME, user.getUsername());
        Assertions.assertEquals(UserDomainTestFactory.EMAIL, user.getEmail());
        Assertions.assertEquals(UserDomainTestFactory.PASSWORD, user.getPassword());
        Assertions.assertEquals(UserDomainTestFactory.FULL_NAME, user.getFullName());
        Assertions.assertEquals(UserDomainTestFactory.PHONE_NUMBER, user.getPhoneNumber());
        Assertions.assertEquals(UserDomainTestFactory.CPF, user.getCpf());
        Assertions.assertEquals(UserDomainTestFactory.IMAGE_URL, user.getImageUrl());
        Assertions.assertEquals(UserDomainTestFactory.CREATION_DATE, user.getCreationDate());
        Assertions.assertEquals(UserDomainTestFactory.LAST_UPDATE_DATE, user.getLastUpdateDate());
    }

    @Test
    @DisplayName(value = "Deve atualizar com sucesso um usuário")
    void updateUserModelMethod(){
//        Arrange
        final String FULL_NAME = "FullName User";
        final String PHONE_NUMBER = "11356609577";
        final String CPF = "423423423";
        User user = UserDomainTestFactory.instantiateAUser();

//        Act
        user.update(FULL_NAME, PHONE_NUMBER, CPF);

//        Assertion
        Assertions.assertEquals(FULL_NAME, user.getFullName());
        Assertions.assertEquals(PHONE_NUMBER, user.getPhoneNumber());
        Assertions.assertEquals(CPF, user.getCpf());
    }
}
