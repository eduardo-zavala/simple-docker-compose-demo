package com.example.logging.repository;

import com.example.logging.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
}
