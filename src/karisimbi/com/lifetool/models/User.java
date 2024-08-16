package karisimbi.com.lifetool.models;

public abstract class User {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String hPassword;
    protected String role;
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";


    public User (String firstName, String lastName, String email, String hPassword, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hPassword = hPassword;
        this.role = role;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHPassword() {
        return hPassword;
    }

    public void setHPassword(String hPassword) {
        this.hPassword = hPassword;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }


    public abstract void viewProfile();
    public abstract void updateProfile();
}