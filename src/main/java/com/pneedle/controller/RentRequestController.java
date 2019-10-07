package com.pneedle.controller;

import com.pneedle.model.Employee;
import com.pneedle.model.Licence;
import com.pneedle.model.RentRequest;
import com.pneedle.model.User;
import com.pneedle.service.EmployeeService;
import com.pneedle.service.RentRequestService;
import com.pneedle.service.UserService;
import com.pneedle.utilities.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RentRequestController {

    private final static String PAGES_FOLDER = "pages/dashboard/";
    private final UserService userService;
    private final RentRequestService rentRequestService;
    private final EmployeeService employeeService;

    private ModelAndView get_roles_pages(User user, boolean addNewRentRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("auth", user);
        List<User> users;
        // If the user have the admin role we get all the usere
        if (user.is_admin()) {
            users = userService.findAll();
        } else {
            users = new ArrayList<>();
            users.add(user);
        }
        if (addNewRentRequest)
            modelAndView.addObject("rentRequest", new RentRequest());
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    private Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Autowired
    public RentRequestController(UserService userService, RentRequestService rentRequestService, EmployeeService employeeService) {
        this.userService = userService;
        this.rentRequestService = rentRequestService;
        this.employeeService = employeeService;
    }

    @RequestMapping(value = {"/dashboard/rent-request/**"}, method = RequestMethod.GET)
    public ModelAndView rentRequest() {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        ModelAndView modelAndView = get_roles_pages(user, true);
        modelAndView.setViewName(PAGES_FOLDER + "rent-request");
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard/rent-request/send-request", method = RequestMethod.POST)
    public ModelAndView createNewRentRequest(@Valid RentRequest rentRequest, BindingResult bindingResult) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        ModelAndView modelAndView = get_roles_pages(user, false);

        if (bindingResult.hasErrors()) {
            modelAndView = get_roles_pages(user, false);
            modelAndView.setViewName(PAGES_FOLDER + "rent-request");
            return modelAndView;
        }

        rentRequestService.saveRentRequest(rentRequest, user);
        modelAndView.addObject("successMessage", "Your rent request has been successfully added.");
        modelAndView.setViewName(PAGES_FOLDER + "rent-request");
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard/rent-request/edit", method = RequestMethod.POST)
    public ModelAndView editRentRequest(RentRequest rentRequest, BindingResult bindingResult) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        RentRequest target_rentRequest = rentRequestService.findByRentRequestID(rentRequest.getRentRequestID());
        ModelAndView modelAndView = get_roles_pages(user, false);
        if (target_rentRequest == null){
            bindingResult.rejectValue("rentRequestID", "error.rentRequest", "The rent request was not found");
        }
        else if(!user.is_admin() && !user.getEmail().equals(target_rentRequest.getUser_email())){
            bindingResult.rejectValue("rentRequestID", "error.rentRequest", "The rent request email is not the same as your email");
        }

        if (bindingResult.hasErrors()) {
            modelAndView = get_roles_pages(user, false);
            modelAndView.setViewName(PAGES_FOLDER + "rent-request");
            return modelAndView;
        }

        target_rentRequest.setRobot_number(rentRequest.getRobot_number());
        target_rentRequest.setComputer_number(rentRequest.getComputer_number());
        target_rentRequest.setDuration(rentRequest.getDuration());

        rentRequestService.editRentRequest(target_rentRequest);
        modelAndView.addObject("successMessage", "Rent request has been edited successfully");
        modelAndView.setViewName(PAGES_FOLDER + "rent-request");
        return modelAndView;
    }


    @RequestMapping(value = {"/dashboard/rent-request/validate"}, method = RequestMethod.POST)
    public ModelAndView validateRentRequest(RentRequest rentRequest, BindingResult bindingResult) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        RentRequest target_rentRequest = rentRequestService.findByRentRequestID(rentRequest.getRentRequestID());
        ModelAndView modelAndView = get_roles_pages(user, false);

        if (target_rentRequest == null){
            bindingResult.rejectValue("rentRequestID", "error.rentRequest", "The rent request was not found");
        }
        if (bindingResult.hasErrors()) {
            modelAndView = get_roles_pages(user, false);
            modelAndView.setViewName(PAGES_FOLDER + "rent-request");
            return modelAndView;
        }

        target_rentRequest.setValidated(true);
        rentRequestService.editRentRequest(target_rentRequest);
        modelAndView.addObject("successMessage", "The rent request has been successfully validated.");
        modelAndView.setViewName(PAGES_FOLDER + "rent-request");
        return modelAndView;
    }
}