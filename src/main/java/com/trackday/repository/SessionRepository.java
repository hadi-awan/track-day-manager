package com.trackday.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.trackday.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    List<Session> findByUserId(Long userId);

    Optional<Session> findByUserIdAndStatus(Long userId, String status);
}
