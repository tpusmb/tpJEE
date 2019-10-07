package com.pneedle.repository;

import com.pneedle.model.RobotUsageEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("robotUsageEventRepository")
public interface RobotUsageEventRepository extends JpaRepository<RobotUsageEvent, Long> {
    RobotUsageEvent findByEventId(int eventId);
}
