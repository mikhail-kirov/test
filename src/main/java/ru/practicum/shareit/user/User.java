package ru.practicum.shareit.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder(toBuilder = true)
public class User {
    private Long id;
    @NotNull
    private String name;
    @Email @NotNull
    private String email;
}
