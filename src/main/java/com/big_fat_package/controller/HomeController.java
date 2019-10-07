package com.big_fat_package.controller;

import com.big_fat_package.service.EmployeeService;
import com.big_fat_package.service.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private final static String PAGES_FOLDER = "pages/";
    private EmployeeService employeeService;
    private UserService userService;

    public HomeController(EmployeeService employeeService, UserService userService){
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //
        Boolean userAuthentificated = false;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            String currentUserName = auth.getName();
            userAuthentificated = true;
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(PAGES_FOLDER + "welcome");
        modelAndView.addObject("userAuthentificated", userAuthentificated);
        return modelAndView;
    }
}
