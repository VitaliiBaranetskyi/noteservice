package com.github.vitaliibaranetskyi.noteservice.service;

import com.github.vitaliibaranetskyi.noteservice.dto.NoteDTO;
import com.github.vitaliibaranetskyi.noteservice.exception.NoteNotFoundException;
import com.github.vitaliibaranetskyi.noteservice.mapper.NoteMapper;
import com.github.vitaliibaranetskyi.noteservice.model.Note;
import com.github.vitaliibaranetskyi.noteservice.model.User;
import com.github.vitaliibaranetskyi.noteservice.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteRepository.findAll();
        return notes.stream()
                .sorted(Comparator.comparing(Note::getCreatedAt).reversed())
                .map(noteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public NoteDTO createNote(NoteDTO noteDto) {
        Note note = noteMapper.toEntity(noteDto);
        note.setLikes(0);
        note.setCreatedAt(LocalDateTime.now());
        Note createdNote = noteRepository.save(note);

        return noteMapper.toDto(createdNote);
    }

    @Override
    public NoteDTO updateNote(String id, NoteDTO noteDto, User currentUser) {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (noteOptional.isEmpty()) {
            throw new NoteNotFoundException("Note with id " + id + " not found");
        }

        Note noteToUpdate = noteOptional.get();

        if (noteDto.getContent() != null) {
            noteToUpdate.setContent(noteDto.getContent());
        }
        if (currentUser != null && currentUser.getRole().getName().equals("ADMIN")) {
            if (noteDto.getLikes() != 0) {
                noteToUpdate.setLikes(noteDto.getLikes());
            }
            if (noteDto.getCreatedAt() != null) {
                noteToUpdate.setCreatedAt(noteDto.getCreatedAt());
            }
        } else{
            noteDto.setLikes(noteToUpdate.getLikes());
            noteDto.setCreatedAt(noteToUpdate.getCreatedAt());
        }

        Note updatedNote = noteRepository.save(noteToUpdate);

        return noteMapper.toDto(updatedNote);
    }

    @Override
    public void deleteNote(String id) throws NoteNotFoundException {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (noteOptional.isEmpty()) {
            throw new NoteNotFoundException("Note with id " + id + " not found");
        }

        noteRepository.deleteById(id);
    }

    @Override
    public NoteDTO likeNote(String id) throws NoteNotFoundException {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (noteOptional.isEmpty()) {
            throw new NoteNotFoundException("Note with id " + id + " not found");
        }

        Note noteToUpdate = noteOptional.get();

        noteToUpdate.setLikes(noteToUpdate.getLikes() + 1);

        Note updatedNote = noteRepository.save(noteToUpdate);

        return noteMapper.toDto(updatedNote);
    }

    @Override
    public NoteDTO unlikeNote(String id) throws NoteNotFoundException {
        Optional<Note> noteOptional = noteRepository.findById(id);

        if (noteOptional.isEmpty()) {
            throw new NoteNotFoundException("Note with id " + id + " not found");
        }

        Note noteToUpdate = noteOptional.get();

        if (noteToUpdate.getLikes() > 0) {
            noteToUpdate.setLikes(noteToUpdate.getLikes() - 1);
        }

        Note updatedNote = noteRepository.save(noteToUpdate);

        return noteMapper.toDto(updatedNote);
    }
}