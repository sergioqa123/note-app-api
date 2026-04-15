package com.sergio.noteapp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notes")
public class NoteController {

    @GetMapping("/{id}")
    private ResponseEntity<Note> findById(@PathVariable Long id) {
        if (id.equals(20L)){
            Note note = new Note(20L, "Note 20", "Content 20", "sergio");
            return ResponseEntity.ok(note);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
