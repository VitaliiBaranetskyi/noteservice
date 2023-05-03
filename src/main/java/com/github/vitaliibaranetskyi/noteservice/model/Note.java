package com.github.vitaliibaranetskyi.noteservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Note {
    @Id
    private String id;
    private String content;
    private int likes;
    private String createdBy;

}
