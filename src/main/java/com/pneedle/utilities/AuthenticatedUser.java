package com.pneedle.utilities;

import com.pneedle.model.Employee;
import com.pneedle.model.User;
import com.pneedle.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {
    public static User userAuthenticated(UserService userService, Employee employee){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        if (user == null){
            user = new User();
            user.setName(employee.getFirstName());
            user.setLastName(employee.getLastName());
            user.setEmail(employee.getEmail());
            user.setRoles(employee.getRoles());
            user.setActive(employee.getActive());
            user.setId(employee.getId());
            user.setLicences(employee.getBoss().getLicences());
        }
        return user;
    }
}
