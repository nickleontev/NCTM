package com.netcracker.edu.datasets;

import java.io.Serializable;

public class Assignee implements Serializable {
        private String fullName;
        private String phone;
        private String email;

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Assignee(String fullName, String contactPhone, String email) {
        this.fullName = fullName;
        this.phone = contactPhone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Assignee{" +
                "fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
