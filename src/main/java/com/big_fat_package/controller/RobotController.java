package com.big_fat_package.controller;

import com.big_fat_package.model.Employee;
import com.big_fat_package.model.Robot;
import com.big_fat_package.model.User;
import com.big_fat_package.service.EmployeeService;
import com.big_fat_package.service.RobotService;
import com.big_fat_package.service.UserService;
import com.big_fat_package.utilities.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RobotController {

    private final static String PAGES_FOLDER = "pages/dashboard/";
    private final UserService userService;
    private final RobotService robotService;
    private final EmployeeService employeeService;

    @Autowired
    public RobotController(UserService userService, RobotService robotService, EmployeeService employeeService) {
        this.userService = userService;
        this.robotService = robotService;
        this.employeeService = employeeService;
    }

    /**
     *
     * @param addNewRobot
     * @return
     */
    private ModelAndView get_robot_pages(boolean addNewRobot) {
        ModelAndView modelAndView = new ModelAndView();
        List<Robot> robots;

        robots = robotService.findAll();
        // If we have an error in the form we dont need to add a new licence
        if (addNewRobot)
            modelAndView.addObject("robot", new Robot());

        modelAndView.addObject("robots", robots);
        return modelAndView;
    }

    private Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = {"/dashboard/robots/**"}, method = RequestMethod.GET)
    public ModelAndView robots() {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        ModelAndView modelAndView = get_robot_pages(true);
        modelAndView.addObject("auth", user);
        modelAndView.setViewName(PAGES_FOLDER + "robot-admin");
        return modelAndView;
    }

    @RequestMapping(value = {"/dashboard/robots/create"}, method = RequestMethod.POST)
    public ModelAndView creat_robot(@Valid Robot robot, BindingResult bindingResult) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        Robot robotExist = robotService.findByRobotSerialNumber(robot.getRobotSerialNumber());
        if (robotExist != null) {
            bindingResult.rejectValue("robotSerialNumber", "error.robot",
                    "There is already a robot registered with the serial number");
        }
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = get_robot_pages(false);
            modelAndView.setViewName(PAGES_FOLDER + "robot-admin");
            modelAndView.addObject("auth", user);
            return modelAndView;
        } else {
            robotService.saveRobot(robot);
            ModelAndView modelAndView = get_robot_pages(true);
            modelAndView.addObject("successMessage", "The robot has been registered successfully");
            modelAndView.setViewName(PAGES_FOLDER + "robot-admin");
            modelAndView.addObject("auth", user);
            return modelAndView;
        }
    }

    @RequestMapping(value = "/dashboard/robots/edit", method = RequestMethod.POST)
    public ModelAndView editLicence(Robot robot, BindingResult bindingResult) {
        Robot target_robot = robotService.findByRobotSerialNumber(robot.getRobotSerialNumber());
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //

        if (target_robot == null) {
            bindingResult.rejectValue("robotSerialNumber", "error.robot", "The robot is not found");
        }
        // Get the new owner of the robot
        User new_user_with_this_robot = userService.findUserByEmail(robot.getUserEmailProprity());
        // If the mail is not existe and the email address is not empty (the case is the first user get the robot)
        if(new_user_with_this_robot == null && ! robot.getUserEmailProprity().equals(""))
            bindingResult.rejectValue("userEmailProprity", "error.robot", "This user email address not exist");

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = get_robot_pages(false);
            modelAndView.addObject("auth", user);
            modelAndView.setViewName(PAGES_FOLDER + "robot-admin");
            return modelAndView;
        }

        // If we just edit the robot name or can be used attribute
        if(target_robot.getUserEmailProprity() == null && new_user_with_this_robot == null){
            target_robot.setRobotName(robot.getRobotName());
            target_robot.setCanBeUsed(robot.isCanBeUsed());
            robotService.editRobot(target_robot);
        }
        // First user on this robot
        else if(target_robot.getUserEmailProprity() == null && new_user_with_this_robot != null){
            // Add the robot to the nex user
            target_robot.setUserEmailProprity(robot.getUserEmailProprity());
            new_user_with_this_robot.add_new_robot(target_robot);
            robotService.editRobot(target_robot, new_user_with_this_robot);

        // Different user
        }else if(! target_robot.getUserEmailProprity().equals(new_user_with_this_robot.getEmail())){

            User current_user_with_this_robot = userService.findUserByEmail(target_robot.getUserEmailProprity());

            // Remove the robot of the curent user
            target_robot.add_new_history(current_user_with_this_robot);
            current_user_with_this_robot.remove_robot(target_robot);

            target_robot.setRobotName(robot.getRobotName());
            target_robot.setCanBeUsed(robot.isCanBeUsed());

            // Add the robot to the nex user
            target_robot.setUserEmailProprity(robot.getUserEmailProprity());
            new_user_with_this_robot.add_new_robot(target_robot);

            robotService.editRobot(target_robot, current_user_with_this_robot, new_user_with_this_robot);
        // else we just edit the robot name or can be used attribute
        }else{

            target_robot.setRobotName(robot.getRobotName());
            target_robot.setCanBeUsed(robot.isCanBeUsed());
            robotService.editRobot(target_robot);
        }

        ModelAndView modelAndView = get_robot_pages(true);
        modelAndView.addObject("successMessage", "Robot has been edit successfully");
        modelAndView.addObject("auth", user);
        modelAndView.setViewName(PAGES_FOLDER + "robot-admin");
        return modelAndView;
    }
}