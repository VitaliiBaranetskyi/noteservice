package com.github.vitaliibaranetskyi.noteservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteDTO {
    private String id;
    private String content;
    private int likes;
    private LocalDateTime createdAt;
}
