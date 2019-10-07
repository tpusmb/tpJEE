package com.pneedle.TemplateDataTest;

import com.pneedle.model.Licence;
import com.pneedle.model.Role;
import com.pneedle.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class TemplateDataTest {

    public Licence licence;

    public Role admin_role;
    public Role company_role;

    public User admin;
    public User company;

    public TemplateDataTest() {

        // Creat admin role
        admin_role = new Role();
        admin_role.setRole("ADMIN");

        // Creat company role
        company_role = new Role();
        company_role.setRole("ENTREPRIS");

        // Creat admin user
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        admin = new User();
        admin.setName("Admin");
        admin.setLastName("Admin last name");
        admin.setEmail("admin@test.com");
        admin.setPassword(bCryptPasswordEncoder.encode("123"));
        admin.setActive(1);

        // Creat admin user
        company = new User();
        company.setName("Company");
        company.setLastName("Company last name");
        company.setEmail("company@test.com");
        company.setPassword(bCryptPasswordEncoder.encode("123"));
        company.setActive(1);

        // Creat simple Licence link to admin
        licence = new Licence();
        licence.generateLicenceKey();
        licence.setExpiry_date("2019-11-29");
        licence.setUser_email(admin.getEmail());
        admin.add_new_licence(licence);
    }
}
