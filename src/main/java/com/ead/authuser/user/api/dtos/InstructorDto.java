package com.ead.authuser.user.api.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class InstructorDto {

    @NotNull
    private UUID userId;
}
