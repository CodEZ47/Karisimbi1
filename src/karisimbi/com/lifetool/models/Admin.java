package karisimbi.com.lifetool.models;

import java.util.Scanner;
import java.io.Console;

import karisimbi.com.lifetool.services.UserMgmt;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        System.out.println(lastName + "'s Profile");
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
        boolean updated = false;
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        System.out.println("Update Your Profile");
        String firstName;
            do {
                System.out.println("Enter your first name:");
                firstName = scanner.nextLine();
                if (firstName.length() < 2 || !firstName.matches("[a-zA-Z]+")) {
                    System.out.println(RED + "First name must be at least 2 letters long and contain only letters." + RESET);
                }
            } while (firstName.length() < 2 || !firstName.matches("[a-zA-Z]+"));
            String lastName;
            do {
                System.out.println("Enter your last name:");
                lastName = scanner.nextLine();
                if (lastName.length() < 2 || !lastName.matches("[a-zA-Z]+")) {
                    System.out.println(RED + "First name must be at least 2 letters long and contain only letters." + RESET);
                }
            } while (lastName.length() < 2 || !lastName.matches("[a-zA-Z]+"));
            char[] passwordArray;
            char[] confirmPasswordArray;
            String password;
            String confirmPassword = "";
            do {
                System.out.println("Enter your password:");
                passwordArray = console.readPassword();
                password = new String(passwordArray);
                if (!UserMgmt.isValidPassword(password)) {
                    System.out.println(RED + "Password must be at least 8 characters long, include at least one uppercase letter, one lowercase letter, one number, and one special character." + RESET);
                    continue;
                }

                System.out.println("Confirm your password:");
                confirmPasswordArray = console.readPassword();
                confirmPassword = new String(confirmPasswordArray);
                if (!password.equals(confirmPassword)) {
                    System.out.println("Passwords do not match. Please try again.");
                }
            } while (!UserMgmt.isValidPassword(password) || !password.equals(confirmPassword));

            String hPassword = UserMgmt.hashPassword(password);
            Admin user = new Admin(firstName, lastName, email, hPassword, role);
            updated = UserMgmt.registerAdmin(user);

        if (updated) {
            System.out.println("Admin updated successfully!");
        } else {
            System.out.println("Admin update failed.");
        }
    }
    

    public void downloadAllUsersData(){
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh download_all_user_data");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("All users' data downloaded successfully.");
            } else {
                System.out.println("Error in downloading users' data. Exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downloadAnalytics() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh calculateStatistics");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Analytics downloaded successfully.");
            } else {
                System.out.println("Error in downloading analytics. Exit code: " + exitCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
}
