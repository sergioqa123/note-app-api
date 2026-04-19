package com.sergio.noteapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/notes")
public class NoteController {

    NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> findById(@PathVariable Long id) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()){
            return ResponseEntity.ok(note.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Void> createNote(@RequestBody Note newNoteRequest, UriComponentsBuilder ucb){
        Note savedNote = noteRepository.save(newNoteRequest);
        URI locationOfNewNote = ucb.path("/notes/{id}").buildAndExpand(newNoteRequest.id()).toUri();
        return ResponseEntity.created(locationOfNewNote).build();
    }
}
