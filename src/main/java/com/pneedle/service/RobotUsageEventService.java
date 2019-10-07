package com.pneedle.service;

import com.pneedle.model.Robot;
import com.pneedle.model.RobotUsageEvent;
import com.pneedle.model.User;
import com.pneedle.repository.RobotRepository;
import com.pneedle.repository.RobotUsageEventRepository;
import com.pneedle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("robotUsageEventService")
public class RobotUsageEventService {

    private UserRepository userRepository;
    private RobotRepository robotRepository;
    private RobotUsageEventRepository robotUsageEventRepository;

    @Autowired
    public RobotUsageEventService(@Qualifier("userRepository") UserRepository userRepository,
                                  @Qualifier("robotRepository") RobotRepository robotRepository,
                                  @Qualifier("robotUsageEventRepository")RobotUsageEventRepository robotUsageEvent) {
        this.userRepository = userRepository;
        this.robotRepository = robotRepository;
        this.robotUsageEventRepository = robotUsageEvent;
    }

    public RobotUsageEvent findByEventId(int eventId) {
        return robotUsageEventRepository.findByEventId(eventId);
    }

    public List<RobotUsageEvent> findAll() {
        return robotUsageEventRepository.findAll();
    }

    /**
     *
     * @param robotUsageEvent
     * @return
     */
    public RobotUsageEvent saveEvent(RobotUsageEvent robotUsageEvent) {
        return robotUsageEventRepository.save(robotUsageEvent);
    }

}