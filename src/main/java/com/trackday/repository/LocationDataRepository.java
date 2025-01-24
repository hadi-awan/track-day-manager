package com.trackday.repository;

import com.trackday.model.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationDataRepository extends JpaRepository<LocationData, Long> {
    List<LocationData> findBySessionId(Long sessionId);
    List<LocationData> findBySessionIdOrderByTimestampDesc(Long sessionId);
}
