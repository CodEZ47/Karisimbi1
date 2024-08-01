package main.com.lifetool.models;

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String hPassword;


    public User (String firstName, String lastName, String email, String hPassword){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hPassword = hPassword;
    }


    public abstract void viewProfile();
    public abstract void updateProfile();
}