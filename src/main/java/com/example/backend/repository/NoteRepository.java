package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.backend.model.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
