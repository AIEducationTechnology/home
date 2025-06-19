package org.example.repository;

import org.example.entity.PoetryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoetryRecordRepository extends JpaRepository<PoetryRecord, Long> {
}