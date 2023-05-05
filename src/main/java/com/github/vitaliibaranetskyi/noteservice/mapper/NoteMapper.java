package com.github.vitaliibaranetskyi.noteservice.mapper;

import com.github.vitaliibaranetskyi.noteservice.dto.NoteDTO;
import com.github.vitaliibaranetskyi.noteservice.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteDTO toDto(Note note);

    Note toEntity(NoteDTO noteDTO);
}
