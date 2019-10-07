package com.pneedle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "email")
    @Email(message = "*Please provide a valid Email")
    @NotEmpty(message = "*Please provide an email")
    private String email;

    @Column(name = "password")
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Please provide your name")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "*Please provide your last name")
    private String lastName;

    @Column(name = "active")
    private int active;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_licence", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "licenceKey"))
    private Set<Licence> licences = new HashSet<Licence>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_employee", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))
    private Set<Employee> employees = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_robots", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "robotSerialNumber"))
    private Set<Robot> robots = new HashSet<Robot>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_rent_request", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rentRequestID"))
    private Set<RentRequest> rentRequests = new HashSet<RentRequest>();

    public boolean is_admin(){
        for(Role role : this.roles) {
            if (role.getRole().equals("ADMIN"))
                return true;
        }
        return false;
    }

    public void add_new_robot(Robot robot) {
        robots.add(robot);
    }

    public void remove_robot(Robot robot){

        this.robots.removeIf(set_robot -> set_robot.getUserEmailProprity().equals(robot.getUserEmailProprity()));
    }

    public void add_new_licence(Licence licence) {
        licences.add(licence);
    }

    public Set<Robot> getRobots() {
        return robots;
    }

    public void setRobots(Set<Robot> robots) {
        this.robots = robots;
    }

    public Set<Licence> getLicences() {
        return licences;
    }

    public void setLicences(Set<Licence> licences) {
        this.licences = licences;
    }

    public void add_new_rent_request(RentRequest rentRequest){
        rentRequests.add(rentRequest);
    }

    public Set<RentRequest> getRentRequests() {
        return rentRequests;
    }

    public void setRentRequests(Set<RentRequest> rentRequests) {
        this.rentRequests = rentRequests;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void add_employee(Employee employee) {
        employees.add(employee);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public String fullName() {
        return name + " " + lastName;
    }
}
