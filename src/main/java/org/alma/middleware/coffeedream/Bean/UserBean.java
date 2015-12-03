package org.alma.middleware.coffeedream.Bean;

import java.io.Serializable;

/**
 * Created by jeremy on 03/12/15.
 */
public class UserBean implements Serializable{

    private String phoneNumber;
    private String firstName;
    private String lastName;

    public UserBean(){

    }

    public UserBean(String phoneNumber, String firstName, String lastName){
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
