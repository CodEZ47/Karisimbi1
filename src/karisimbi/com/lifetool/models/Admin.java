package karisimbi.com.lifetool.models;

import java.util.Scanner;

public class Admin extends User {
    private String firstName;
    private String lastName;
    private String email;
    private String hPassword;
    private String role;

    public Admin(String firstName, String lastName, String email, String hPassword, String role) {
        super(firstName, lastName, email, hPassword, role);
        

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hPassword = hPassword;
        this.role = role;
    }

    @Override
    public void viewProfile() {
        System.out.println("Admin Profile");
        System.out.println("----------------------------------------------");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
        System.out.println("----------------------------------------------");
        System.out.println("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        return;
    }

    @Override
    public void updateProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateProfile'");
    }
    
}
