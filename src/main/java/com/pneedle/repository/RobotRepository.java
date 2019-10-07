package com.pneedle.repository;

import com.pneedle.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("robotRepository")
public interface RobotRepository extends JpaRepository<Robot, Long> {
    Robot findByRobotSerialNumber(String robotSerialNumber);
}
