package karisimbi.com.lifetool.models;

import java.util.Scanner;
import java.io.Console;
import karisimbi.com.lifetool.services.UserMgmt;

public class Patient extends User {

    private String dateOfBirth;
    private boolean hivPositive;
    private String diagnosisDate;
    private boolean onART;
    private String artStartDate;
    private String countryCode;
    private String uuidCode;
    private String expectedLife;

    public Patient(String firstName, String lastName, String email, String hPassword, String role, String dateOfBirth,
                   boolean hivPositive, String diagnosisDate, boolean onART, String artStartDate, String countryCode, String uuidCode, String expectedLife) {
        super(firstName, lastName, email, hPassword, role);
        this.dateOfBirth = dateOfBirth;
        this.hivPositive = hivPositive;
        this.diagnosisDate = diagnosisDate;
        this.onART = onART;
        this.artStartDate = artStartDate;
        this.countryCode = countryCode;
        this.uuidCode = uuidCode;
        this.expectedLife = expectedLife;
    }

    @Override
    public void viewProfile() {
        System.out.println( lastName + "'s Profile");
        System.out.println("----------------------------------------------");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Email: " + email);
        System.out.println("Role: " + role);
        System.out.println("Date of Birth: " + dateOfBirth);
        System.out.println("HIV Positive: " + hivPositive);
        System.out.println("Diagnosis Date: " + diagnosisDate);
        System.out.println("On ART: " + onART);
        System.out.println("ART Start Date: " + artStartDate);
        System.out.println("Country Code: " + countryCode);
        System.out.println("Expected Lifespan: " + expectedLife);
        System.out.println("----------------------------------------------");
        System.out.println("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        return;

    }

    @Override
    public void updateProfile() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        Boolean updated = false;
        System.out.println("Updating your profile");
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
            System.out.println("Enter your date of birth (MM-DD-YYYY):");
            String dob = UserMgmt.getValidatedDate(scanner);
            System.out.println("Are you HIV positive?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String hivPositive = UserMgmt.checkOption(scanner);
            boolean hivStatus = hivPositive.equals("1") ? true : false;
            String diagnosisDate = "00-00-0000";
            String artStartDate = "00-00-0000";
            boolean onARTStatus = false;

            
            if (hivStatus) {
                System.out.println("Enter your diagnosis date:(MM-DD-YYYY)");
                diagnosisDate = UserMgmt.getValidatedDate(scanner);
                System.out.println("Are you on ART?");
                System.out.println("1. Yes");
                System.out.println("2. No");
                String onART = UserMgmt.checkOption(scanner);
                onARTStatus = onART.equals("1") ? true : false;
                if (onARTStatus) {
                    System.out.println("Enter your ART start date:(MM-DD-YYYY)");
                    artStartDate = UserMgmt.getValidatedDate(scanner);
                }else{
                    artStartDate = "";
                }
            }
            
            System.out.println("Enter your country:");
            String country = scanner.nextLine();

            String countryCode = UserMgmt.fetchCountryCode(scanner,country);

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

            
        
        String expectedLifeSpan = UserMgmt.calculateLifespan(dob, hivStatus, diagnosisDate, onARTStatus, artStartDate, countryCode);
        String hPassword = UserMgmt.hashPassword(password);
        Patient user = new Patient(firstName, lastName, email, hPassword, role, dob, hivStatus, diagnosisDate, onARTStatus, artStartDate, countryCode, uuidCode, expectedLifeSpan);
        updated = UserMgmt.registerUser(user);

        if (updated) {
            System.out.println("User updated successfully!");
        } else {
            System.out.println("User update failed.");
        }
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isHivPositive() {
        return hivPositive;
    }

    public void setHivPositive(boolean hivPositive) {
        this.hivPositive = hivPositive;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public boolean isOnART() {
        return onART;
    }

    public void setOnART(boolean onART) {
        this.onART = onART;
    }

    public String getArtStartDate() {
        return artStartDate;
    }

    public void setArtStartDate(String artStartDate) {
        this.artStartDate = artStartDate;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getUuidCode() {
        return uuidCode;
    }

    public void setUuidCode(String uuidCode) {
        this.uuidCode = uuidCode;
    }

    public String getExpectedLife() {
        return expectedLife;
    }

    public void setExpectedLife(String expectedLife) {
        this.expectedLife = expectedLife;
    }
}
