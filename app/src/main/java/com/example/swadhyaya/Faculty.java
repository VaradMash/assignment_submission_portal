package com.example.swadhyaya;

public class Faculty
{
    public String username;
    public String email;
    public String institution;
    public String class_name;
    public String subject;

    public Faculty()
    {
        /*
         * Default Constructor.
         */
    }

    public Faculty(String username, String email, String institution, String class_name, String subject)
    {
        this.username = username;
        this.email = email;
        this.institution = institution;
        this.class_name = class_name;
        this.subject = subject;
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

    public String getSubject() {
        return subject;
    }
}
