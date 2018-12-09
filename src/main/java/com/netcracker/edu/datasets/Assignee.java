package com.netcracker.edu.datasets;

public class Assignee {
        private String fullName;
        private String contactPhone;
        private String email;

    public String getContactPhone() {
        return contactPhone;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Assignee(String fullName, String contactPhone, String email) {
        this.fullName = fullName;
        this.contactPhone = contactPhone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "Assignee{" +
                "fullName='" + fullName + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
