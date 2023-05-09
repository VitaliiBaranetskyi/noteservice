package com.github.vitaliibaranetskyi.noteservice.controller;

import com.github.vitaliibaranetskyi.noteservice.dto.NoteDTO;
import com.github.vitaliibaranetskyi.noteservice.model.Role;
import com.github.vitaliibaranetskyi.noteservice.model.User;
import com.github.vitaliibaranetskyi.noteservice.service.NoteService;
import com.github.vitaliibaranetskyi.noteservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        List<NoteDTO> notes = noteService.getAllNotes();
        return ResponseEntity.ok(notes);
    }

    @PostMapping("/create")
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO noteDto) {
        NoteDTO savedNote = noteService.createNote(noteDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedNote);
    }

    @PutMapping("/note/{id}")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable String id, @RequestBody NoteDTO noteDto, Principal principal) {
        User currentUser = principal != null ?
                userService.findByUsername(principal.getName()).orElse(new User(new Role("ANONIM"))) :
                new User(new Role("ANONIM"));
        NoteDTO savedNote = noteService.updateNote(id, noteDto, currentUser);
        return ResponseEntity.ok(savedNote);
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable String id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/note/{id}/like")
    public ResponseEntity<NoteDTO> likeNote(@PathVariable String id) {
        NoteDTO updatedNote = noteService.likeNote(id);
        return ResponseEntity.ok(updatedNote);
    }

    @PutMapping("/note/{id}/unlike")
    public ResponseEntity<NoteDTO> unlikeNote(@PathVariable String id) {
        NoteDTO updatedNote = noteService.unlikeNote(id);
        return ResponseEntity.ok(updatedNote);
    }
}


