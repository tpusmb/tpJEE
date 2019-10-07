package com.pneedle.controller;

import com.pneedle.model.Employee;
import com.pneedle.model.User;
import com.pneedle.repository.EmployeeRepository;
import com.pneedle.service.EmployeeService;
import com.pneedle.service.UserService;
import com.pneedle.utilities.AuthenticatedUser;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = {"/dashboard/company"})
public class CompanyController {
    // Constants
    private final static String PAGES_FOLDER = "pages/";
    // Attributes
    @Autowired
    private UserService userService;
    private EmployeeService employeeService;

    // Constructors
    @Autowired
    public CompanyController(UserService userService, EmployeeService employeeService){
        this.userService = userService;
        this.employeeService = employeeService;
    }

    // Methods
    private Authentication getAuthenticatedUser(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping(value = {"/**"}, method = RequestMethod.GET)
    public ModelAndView companyManagement() {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        // Get all users
        List<User> users = userService.findAll();
        // Result view
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("auth", user);
        modelAndView.addObject("users", users);
        modelAndView.addObject("user", new User());
        modelAndView.setViewName(PAGES_FOLDER + "dashboard/manage_company");
        return modelAndView;
    }

    @RequestMapping(value = {"/employees/{boss_id}"}, method = RequestMethod.GET)
    public ModelAndView employeesManagement(@PathVariable int boss_id) {
        // Get user authenticated
        Authentication auth = getAuthenticatedUser();
        Employee employee = employeeService.findByEmail(auth.getName());
        User user = AuthenticatedUser.userAuthenticated(userService, employee);
        // Boss
        User boss = userService.findById(boss_id);
        // Get all employees
        List<Employee> employees = employeeService.findByBoss(boss);
        // Send view result
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("auth", user);
        modelAndView.addObject("boss", boss);
        modelAndView.addObject("linkAddEmployee", "/dashboard/company/employees/create/"+boss_id);
        modelAndView.addObject("employees", employees);
        modelAndView.addObject("employee", new Employee());
        modelAndView.setViewName(PAGES_FOLDER + "dashboard/manage_employee");
        return modelAndView;
    }

    @RequestMapping(value = {"/employees/create/{boss_id}"}, method = RequestMethod.POST)
    public String addNewEmployee(@Valid Employee employee, @PathVariable int boss_id, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (bindingResult.hasErrors()){
            return "redirect:/dashboard/employee/";
        }
        // Get boss
        User user = userService.findById(boss_id);
        // Attach boss
        employee.setBoss(user);
        employeeService.saveEmployee(employee);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Employee has been add successfully");
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @RequestMapping(value = {"/edit"}, method = RequestMethod.PUT)
    public String editCompany(User company, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            return "redirect:/dashboard/company/";
        }
        // Update company
        User target_company = userService.findById(company.getId());
        target_company.setEmail(company.getEmail());
        target_company.setName(company.getName());
        target_company.setLastName(company.getLastName());
        userService.editUser(target_company);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Company has been update successfully");
        return "redirect:/dashboard/company/";
    }

    @RequestMapping(value = {"/delete"}, method = RequestMethod.DELETE)
    public String deletecompany(Employee employee, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            return "redirect:/dashboard/company/";
        }
        // Delete company
        User target_company = userService.findById(employee.getId());
        userService.deleteUser(target_company);
        // Send view result
        redirectAttributes.addAttribute("successMessage", "Company has been delete successfully");
        return "redirect:/dashboard/company/";
    }
}