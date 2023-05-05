package com.github.vitaliibaranetskyi.noteservice.repository;

import com.github.vitaliibaranetskyi.noteservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findAllByOrderByCreatedAtDesc(); //get a list of notes sorted from new to old
}
