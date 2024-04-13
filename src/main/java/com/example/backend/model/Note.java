package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id = -1;
    @Column(name = "title")
    private String title = null;
    @Column(name = "description")
    private String description = null;
    @Column(name = "timestamp")
    private Instant timestamp = null;

    public Note(String title, String description, Instant timestamp) {
        this.title = title;
        this.description = description;
        this.timestamp = timestamp;
    }

    public Note(String title, Instant timestamp) {
        this.title = title;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
