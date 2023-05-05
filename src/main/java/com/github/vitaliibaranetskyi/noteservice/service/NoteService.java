package com.github.vitaliibaranetskyi.noteservice.service;

import com.github.vitaliibaranetskyi.noteservice.dto.NoteDTO;
import com.github.vitaliibaranetskyi.noteservice.model.User;

import java.util.List;

public interface NoteService {
    List<NoteDTO> getAllNotes();
    NoteDTO createNote(NoteDTO noteDto);
    NoteDTO updateNote(String id, NoteDTO noteDto, User currentUse);
    void deleteNote(String id);
    NoteDTO likeNote(String id);
    NoteDTO unlikeNote(String id);


}
