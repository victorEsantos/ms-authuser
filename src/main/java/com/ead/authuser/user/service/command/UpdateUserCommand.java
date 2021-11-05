package com.ead.authuser.user.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateUserCommand {
    private final UUID id;
    private final String fullName;
    private final String phoneNumber;
    private final String cpf;
}
