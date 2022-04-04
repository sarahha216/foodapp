package vn.edu.huflit.hmt_19dh110405.Model;

import java.io.Serializable;

public class User implements Serializable {
    String firstname, lastname, email, address, lattitude, longitude, mobile;

    public User(){

    }

    public User(String firstname, String lastname, String email, String address, String lattitude, String longitude, String mobile) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.address = address;
        this.lattitude = lattitude;
        this.longitude = longitude;
        this.mobile = mobile;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstName) {
        this.firstname = firstName;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastName) {
        this.lastname = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstname + '\'' +
                ", lastName='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", lattitude='" + lattitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}

