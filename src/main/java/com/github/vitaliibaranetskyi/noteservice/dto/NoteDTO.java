package com.github.vitaliibaranetskyi.noteservice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private String id;
    @NotBlank
    private String content;
    private int likes;
    private LocalDateTime createdAt;
}
