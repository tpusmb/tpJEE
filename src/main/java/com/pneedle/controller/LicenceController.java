package com.pneedle.controller;

import com.pneedle.model.Employee;
import com.pneedle.model.Licence;
import com.pneedle.model.User;
import com.pneedle.service.EmployeeService;
import com.pneedle.service.LicenceService;
import com.pneedle.service.RoleService;
import com.pneedle.service.UserService;
import com.pneedle.utilities.AuthenticatedUser;
import com.pneedle.utilities.RoleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class LicenceController {

    private final static String PAGES_FOLDER = "pages/dashboard/";
    private final UserService userService;
    private final LicenceService licenceService;
    private final EmployeeService employeeService;
    private final RoleService roleService;

    @Autowired
    public LicenceController(UserService userService, LicenceService licenceService, EmployeeService employeeService, RoleService roleService) {
        this.userService = userService;
        this.licenceService = licenceService;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    /**
     * Creat model and view in function of the user role
     *
     * @param user          connected user
     * @param addNewLicence If we have an error in the form we dont need to add a new licence
     * @return The model and view
     */
    private ModelAndView get_roles_pages(User user, boolean addNewLicence) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("auth", user);
        List<User> users;
        // If the user have the admin role we get all the usere
        if (user.is_admin()) {
            users = userService.findAll();
            // If we have an error in the form we dont need to add a new licence
            if (addNewLicence)
                modelAndView.addObject("licence", new Licence());
        } else {
            users = new ArrayList<>();
            users.add(user);
        }
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    private Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = {"/dashboard/licence/**"}, method = RequestMethod.GET)
    public ModelAndView licence() {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        ModelAndView modelAndView = get_roles_pages(user, true);
        modelAndView.setViewName(PAGES_FOLDER + "licence");
        return modelAndView;
    }

    // TODO utiliser le maime lien /dashboard/licence
    @RequestMapping(value = "/dashboard/licence/create", method = RequestMethod.POST)
    public ModelAndView createNewLicence(@Valid Licence licence, BindingResult bindingResult) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        User target_user = userService.findUserByEmail(licence.getUser_email());

        if (target_user == null)
            bindingResult.rejectValue("user_email", "error.licence", "This email does not match any account");

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = get_roles_pages(user, false);
            modelAndView.setViewName(PAGES_FOLDER + "licence");
            return modelAndView;
        }

        ModelAndView modelAndView = get_roles_pages(user, true);
        licenceService.saveLicence(licence, target_user);
        modelAndView.addObject("successMessage", "Licence has been add successfully");
        modelAndView.setViewName(PAGES_FOLDER + "licence");
        return modelAndView;
    }

    @RequestMapping(value = "/dashboard/licence/edit", method = RequestMethod.POST)
    public ModelAndView editLicence(Licence licence, BindingResult bindingResult) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        //
        Licence target_licence = licenceService.findByLicenceKey(licence.getLicenceKey());

        if (target_licence == null)
            bindingResult.rejectValue("licenceKey", "error.licence", "The licence key not found");

        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = get_roles_pages(user, false);
            modelAndView.setViewName(PAGES_FOLDER + "licence");
            return modelAndView;
        }

        ModelAndView modelAndView = get_roles_pages(user, true);
        target_licence.setBlock(licence.isBlock());
        target_licence.setExpiry_date(licence.getExpiry_date());
        licenceService.editLicence(target_licence);
        modelAndView.addObject("successMessage", "Licence has been edit successfully");
        modelAndView.setViewName(PAGES_FOLDER + "licence");
        return modelAndView;
    }
}