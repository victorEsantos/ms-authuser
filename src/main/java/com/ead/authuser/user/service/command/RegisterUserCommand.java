package com.ead.authuser.user.service.command;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterUserCommand {
    private final String username;
    private final String email;
    private final String password;
    private final String fullName;
    private final String phoneNumber;
    private final String cpf;
}
