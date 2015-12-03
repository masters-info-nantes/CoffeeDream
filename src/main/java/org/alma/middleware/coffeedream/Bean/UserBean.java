package org.alma.middleware.coffeedream.Bean;

/**
 * Created by jeremy on 03/12/15.
 */
public class UserBean {

    private int phoneNumber;

    private String firstName;
    private String lastName;

    public UserBean(){

    }

    public UserBean(int phoneNumber, String firstName, String lastName){
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
