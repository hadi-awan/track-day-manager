package com.trackday.repository;

import com.trackday.model.Lap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LapRepository extends JpaRepository<Lap, Long> {
    List<Lap> findBySessionId(Long sessionId);
}
