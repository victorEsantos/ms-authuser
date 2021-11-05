package com.ead.authuser.user.service.command;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UpdateUserPasswordCommand {
    private final UUID id;
    private final String oldPassword;
    private final String password;
}
