package com.pneedle.controller;

import com.pneedle.model.Employee;
import com.pneedle.model.Role;
import com.pneedle.model.User;
import com.pneedle.repository.RoleRepository;
import com.pneedle.service.EmployeeService;
import com.pneedle.service.RoleService;
import com.pneedle.service.UserService;
import com.pneedle.utilities.AuthenticatedUser;
import com.pneedle.utilities.RoleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/dashboard/employee")
public class EmployeeController {
    // Constants
    private final static String PAGES_FOLDER = "pages/";
    // Attributes
    @Autowired
    private UserService userService;
    private EmployeeService employeeService;
    private RoleService roleService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    // Constructors
    @Autowired
    public EmployeeController(UserService userService, EmployeeService employeeService, RoleService roleService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    // Methods
    private Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = {"/**"}, method = RequestMethod.GET)
    public ModelAndView employeesManagement() {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        // Get all employees
        List<Employee> employees = employeeService.findByBoss(user);
        // Send view result
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("auth", user);
        modelAndView.addObject("linkAddEmployee", "/dashboard/employee/create");
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("employee", new Employee());
        modelAndView.setViewName(PAGES_FOLDER + "dashboard/manage_employee");
        return modelAndView;
    }

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    public String addNewEmployee(@Valid Employee employee, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            return "redirect:/dashboard/employee/";
        }
        // Check if email exist
        if (employeeService.findByEmail(employee.getEmail())!= null){
            redirectAttributes.addAttribute("errorMessage", "There is already an employee registered with the provided email");
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee auth_employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService,auth_employee );
        // Attach boss
        employee.setBoss(user);
        // Encrypt password
        employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        // Role
        Set<Role> employee_role = new HashSet<>();
        employee_role.add(roleService.findByRole(RoleData.EMPLOYEE));
        employee.setRoles(employee_role);
        employeeService.saveEmployee(employee);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Employee has been add successfully");
        return "redirect:/dashboard/employee/";
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.PUT)
    public String editEmployee(Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            return "redirect:/dashboard/employee/";
        }
        // Update employee
        Employee target_employee = employeeService.findByEmail(employee.getEmail());
        target_employee.setEmail(employee.getEmail());
        target_employee.setFirstName(employee.getFirstName());
        target_employee.setLastName(employee.getLastName());
        employeeService.editEmployee(target_employee);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Employee has been update successfully");
        //return "redirect:/dashboard/employee/";
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
    public String deleteEmployee(Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            return "redirect:/dashboard/employee/";
        }
        // Delete employee
        Employee target_employee = employeeService.findByEmail(employee.getEmail());
        employeeService.deleteEmployee(target_employee);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Employee has been delete successfully");
        //return "redirect:/dashboard/employee/";
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
