package com.ead.authuser.user.service.command;

import lombok.Data;

import java.util.UUID;

@Data(staticConstructor = "of")
public class UpdateUserImageCommand {
    private final UUID id;
    private final String imageUrl;
}
