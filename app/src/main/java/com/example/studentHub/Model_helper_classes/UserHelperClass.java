package com.example.studentHub.Model_helper_classes;

public class UserHelperClass {

    String regName, regUsername, regEmail, regPhone, regPassword;

    public UserHelperClass(String regName, String regUsername, String regEmail, String regPhone, String regPassword) {
        this.regName = regName;
        this.regUsername = regUsername;
        this.regEmail = regEmail;
        this.regPhone = regPhone;
        this.regPassword = regPassword;
    }

    public UserHelperClass(String regName, String regUsername, String regPhone) {
    }

    public String getRegName() {
        return regName;
    }

    public void setRegName(String regName) {
        this.regName = regName;
    }

    public String getRegUsername() {
        return regUsername;
    }

    public void setRegUsername(String regUsername) {
        this.regUsername = regUsername;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getRegPhone() {
        return regPhone;
    }

    public void setRegPhone(String regPhone) {
        this.regPhone = regPhone;
    }

    public String getRegPassword() {
        return regPassword;
    }

    public void setRegPassword(String regPassword) {
        this.regPassword = regPassword;
    }
}
