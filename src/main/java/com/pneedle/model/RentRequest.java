package com.pneedle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rent_request")
public class RentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rent_request_id")
    private int rentRequestID;

    // Number of robot
    @Min(1)
    @Column(name = "robot_number")
    private int robot_number;

    // Number of computers
    @Min(1)
    @Column(name = "computer_number")
    private int computer_number;

    // Duration in months
    @Min(1)
    @Column(name = "duration")
    private int duration;

    // True if this request has been validated by an admin
    @Builder.Default
    @Column(name = "validated")
    private boolean validated = false;

    // True if this request has ended
    @Builder.Default
    @Column(name = "ended")
    private boolean ended = false;

    // email of the user which has created this request
    @Builder.Default
    @Column(name = "user_email")
    private String user_email = null;

    public int getRentRequestID() {
        return rentRequestID;
    }

    public void setRentRequestID(int rentRequestID) {
        this.rentRequestID = rentRequestID;
    }

    public int getRobot_number() {
        return robot_number;
    }

    public void setRobot_number(int robot_number) {
        this.robot_number = robot_number;
    }

    public int getComputer_number() {
        return computer_number;
    }

    public void setComputer_number(int computer_number) {
        this.computer_number = computer_number;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isEnded() {
        return ended;
    }

    public void setEnded(boolean ended) {
        this.ended = ended;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
}
