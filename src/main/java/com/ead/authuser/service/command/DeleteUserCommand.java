package com.ead.authuser.service.command;

import lombok.Data;

import java.util.UUID;

@Data(staticConstructor = "of")
public class DeleteUserCommand {
    private final UUID id;
}
