package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class CommentDto {
    private Long id;
    @NotNull
    private String text;
    private String authorName;
    private LocalDateTime created;
}
