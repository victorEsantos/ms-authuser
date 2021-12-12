package com.ead.authuser.user.service;

import com.ead.authuser.user.domain.model.User;
import com.ead.authuser.user.repository.UserRepository;
import com.ead.authuser.user.service.command.RegisterUserCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@DisplayName("Teste Serviço de usuario")
class UserAppServiceTest {
    @Mock
    UserRepository repository;

    @InjectMocks
    UserAppService service;

    @Test
    @DisplayName("Deve registrar um usuario e salvar no banco")
    void registerNewUserHanleTest() {
        RegisterUserCommand cmd = RegisterUserCommand.builder()
                .username("usr")
                .email("email@email.com")
                .password("1312321")
                .fullName("User Full Name")
                .phoneNumber("554788756456")
                .cpf("96828465016")
                .build();

        when(repository.save(any(User.class))).thenReturn(new User());

        var created = service.handle(cmd);

        assertThat(created.getUsername()).isSameAs(cmd.getUsername());
        assertThat(created.getEmail()).isSameAs(cmd.getEmail());
        assertThat(created.getPassword()).isSameAs(cmd.getPassword());
        assertThat(created.getFullName()).isSameAs(cmd.getFullName());
        assertThat(created.getPhoneNumber()).isSameAs(cmd.getPhoneNumber());
        assertThat(created.getCpf()).isSameAs(cmd.getCpf());
        assertNull(created.getImageUrl());

    }
}
