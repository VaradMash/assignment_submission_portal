package com.example.swadhyaya;

public class User
{
    public String username;
    public String email;
    public String institution;
    public String class_name;

    public User()
    {
        /*
         * Default Constructor.
         */
    }

    public User(String username, String email, String institution, String class_name) {
        this.username = username;
        this.email = email;
        this.institution = institution;
        this.class_name = class_name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getInstitution() {
        return institution;
    }

    public String getClass_name() {
        return class_name;
    }
}
