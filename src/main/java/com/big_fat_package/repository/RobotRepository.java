package com.big_fat_package.repository;

import com.big_fat_package.model.Robot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("robotRepository")
public interface RobotRepository extends JpaRepository<Robot, Long> {
    Robot findByRobotSerialNumber(String robotSerialNumber);
}
