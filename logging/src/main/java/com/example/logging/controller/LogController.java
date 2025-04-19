package com.example.logging.controller;

import com.example.logging.entity.LogEntry;
import com.example.logging.repository.LogEntryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogEntryRepository repository;

    public LogController(LogEntryRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity<String> createLog(@RequestBody LogEntry logEntry) {
        repository.save(logEntry);
        return ResponseEntity.ok("Log saved");
    }
}
