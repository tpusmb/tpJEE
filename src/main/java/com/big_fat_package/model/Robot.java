package com.big_fat_package.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "robot")
public class Robot {
    @Id
    private String robotSerialNumber;

    @NotEmpty(message = "*Please provide an robot name")
    private String robotName;

    // Purchase date YYYY-MM-DD
    @NotEmpty(message = "*Please provide an purchase date")
    private String purchaseDate;

    // number of hours of operation
    @Builder.Default
    private int numberOperationHoures = 0;

    // Current user used this robot
    @Builder.Default
    private String userEmailProprity = null;

    @Builder.Default
    private boolean canBeUsed = true;

    // All the user have used this robot
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "users_history", joinColumns = @JoinColumn(name = "robotSerialNumber"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> usersHistory = new ArrayList<>();

    // All the user have used this robot
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "robot_event_history", joinColumns = @JoinColumn(name = "robotSerialNumber"), inverseJoinColumns = @JoinColumn(name = "eventId"))
    private List<RobotUsageEvent> robotUsageEvents = new ArrayList<>();

    public void add_new_event(RobotUsageEvent event) {
        this.robotUsageEvents.add(event);
    }

    public List<RobotUsageEvent> getRobotUsageEvents() {
        return robotUsageEvents;
    }

    public void add_new_history(User user) {
        usersHistory.add(user);
    }

    public boolean isCanBeUsed() {
        return canBeUsed;
    }

    public void setCanBeUsed(boolean canBeUsed) {
        this.canBeUsed = canBeUsed;
    }

    public String getRobotSerialNumber() {
        return robotSerialNumber;
    }

    public void setRobotSerialNumber(String robotSerialNumber) {
        this.robotSerialNumber = robotSerialNumber;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getNumberOperationHoures() {
        return numberOperationHoures;
    }

    public void setNumberOperationHoures(int numberOperationHoures) {
        this.numberOperationHoures = numberOperationHoures;
    }

    public String getUserEmailProprity() {
        return userEmailProprity;
    }

    public void setUserEmailProprity(String userEmailProprity) {
        this.userEmailProprity = userEmailProprity;
    }

    public List<User> getUsersHistory() {
        return usersHistory;
    }

    public void setUsersHistory(List<User> usersHistory) {
        this.usersHistory = usersHistory;
    }

    public String getRobotName() {
        return robotName;
    }

    public void setRobotName(String robotName) {
        this.robotName = robotName;
    }
}
