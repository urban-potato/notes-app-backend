package com.example.backend.controller;

import com.example.backend.model.Note;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.backend.repository.NoteRepository;

import java.time.Instant;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/notes")
public class NoteRestController {
    private final NoteRepository noteRepository;

    public NoteRestController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Note>> getAll() {
        List<Note> notes = noteRepository.findAll();

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Note note = optionalNote.get();
        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        if (note.getTitle() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        note.setTimestamp(Instant.now());
        Note createdNote = noteRepository.save(note);
        return new ResponseEntity<>(createdNote, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable("id") Long id) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        noteRepository.delete(optionalNote.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Note> patchUpdateNote(
            @PathVariable("id") Long id,
            @RequestBody Note updatedNote
    ) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Note note = optionalNote.get();

        if (updatedNote.getTitle() != null) {
            note.setTitle(updatedNote.getTitle());
        }
        if (updatedNote.getDescription() != null) {
            note.setDescription(updatedNote.getDescription());
        }

        note.setTimestamp(Instant.now());

        noteRepository.save(note);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateEntireNote(
            @PathVariable("id") Long id,
            @RequestBody Note updatedNote
    ) {
        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (updatedNote.getTitle() == null || updatedNote.getDescription() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Note note = optionalNote.get();

        note.setTitle(updatedNote.getTitle());
        note.setDescription(updatedNote.getDescription());

        note.setTimestamp(Instant.now());

        noteRepository.save(note);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.HEAD)
    public ResponseEntity<Void> headNotes() {
        List<Note> notes = noteRepository.findAll();
        int totalNotesNumber = notes.size();
        HttpHeaders headers = new HttpHeaders();
        headers.add("totalCount", String.valueOf(totalNotesNumber));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.HEAD)
    public ResponseEntity<Void> headNoteById(@PathVariable("id") Long id) {
        HttpHeaders headers = new HttpHeaders();

        Optional<Note> optionalNote = noteRepository.findById(id);

        if (optionalNote.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Note note = optionalNote.get();
        headers.add("id", String.valueOf(note.getId()));

        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<Void> optionsNotes() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Allow", "GET, POST, PUT, PATCH, DELETE, HEAD, OPTIONS");
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }
}
