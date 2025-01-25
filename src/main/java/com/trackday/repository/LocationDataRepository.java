package com.trackday.repository;

import com.trackday.model.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LocationDataRepository extends JpaRepository<LocationData, Long> {
    List<LocationData> findBySessionId(Long sessionId);
    List<LocationData> findBySessionIdOrderByTimestampDesc(Long sessionId);

    @Query("SELECT l FROM LocationData l WHERE l.session.id = :sessionId AND l.timestamp BETWEEN :startTime AND :endTime")
    List<LocationData> findBySessionIdAndTimestampBetween(
            @Param("sessionId") Long sessionId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
