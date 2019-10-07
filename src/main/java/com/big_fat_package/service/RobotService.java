package com.big_fat_package.service;

import com.big_fat_package.model.Robot;
import com.big_fat_package.model.User;
import com.big_fat_package.repository.RobotRepository;
import com.big_fat_package.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("robotService")
public class RobotService {

    private UserRepository userRepository;
    private RobotRepository robotRepository;

    @Autowired
    public RobotService(@Qualifier("userRepository") UserRepository userRepository,
                        @Qualifier("robotRepository") RobotRepository robotRepository) {
        this.userRepository = userRepository;
        this.robotRepository = robotRepository;
    }

    public Robot findByRobotSerialNumber(String robotSerialNumber) {
        return robotRepository.findByRobotSerialNumber(robotSerialNumber);
    }

    public List<Robot> findAll() {
        return robotRepository.findAll();
    }

    /**
     * @param robot robot to add
     * @return the new licence add
     */
    public Robot saveRobot(Robot robot) {
        return robotRepository.save(robot);
    }

    /**
     * Edit the licence in the data base.
     *
     * @param robot robot to edit
     * @return the new robot add
     */
    public Robot editRobot(Robot robot, User current_user, User new_user) {
        userRepository.save(current_user);
        userRepository.save(new_user);
        return robotRepository.save(robot);
    }

    public Robot editRobot(Robot robot, User new_user) {
        userRepository.save(new_user);
        return robotRepository.save(robot);
    }

    public Robot editRobot(Robot robot) {
        return robotRepository.save(robot);
    }

}