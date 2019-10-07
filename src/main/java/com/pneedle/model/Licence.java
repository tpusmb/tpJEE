package com.pneedle.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Random;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "licence")
public class Licence {

    private final static int NUMBER_DIGIT = 10;
    private final static int NUMBER_DIGIT_GROUP = 4;

    @Id
    private String licenceKey;
    // Expiration date string format YYYY-MM-DD
    @NotEmpty(message = "*Please provide an expiration date")
    private String expiry_date;
    // Hash licenceKey + machin id + random token
    @Builder.Default
    private String hash = null;
    // Machin id hashed
    @Builder.Default
    private String hashed_machine_ID = null;
    // Random token
    @Builder.Default
    private String token = null;
    // True id this licenceKey is used
    @Builder.Default
    private boolean used = false;
    // True if we need to block this licence
    @Builder.Default
    private boolean block = false;
    // email of the user have the licence key
    @NotEmpty(message = "*Please provide an email address of the user destination")
    private String user_email;

    /**
     * Generate and set the atribut licenceKey with the new licence key
     */
    public void generateLicenceKey() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt_group = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < NUMBER_DIGIT_GROUP; i++) {
            StringBuilder salt = new StringBuilder();
            while (salt.length() < NUMBER_DIGIT) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            salt_group.append(salt);
            if (i < NUMBER_DIGIT_GROUP - 1)
                salt_group.append("-");
        }
        this.licenceKey = salt_group.toString();
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getLicenceKey() {
        return licenceKey;
    }

    public void setLicenceKey(String licenceKey) {
        this.licenceKey = licenceKey;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getHashed_machine_ID() {
        return hashed_machine_ID;
    }

    public void setHashed_machine_ID(String hashed_machine_ID) {
        this.hashed_machine_ID = hashed_machine_ID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }
}
