package karisimbi.com.lifetool.models;

import java.util.Scanner;
import karisimbi.com.lifetool.services.UserMgmt;

public class Patient extends User {

    private String dateOfBirth;
    private boolean hivPositive;
    private String diagnosisDate;
    private boolean onART;
    private String artStartDate;
    private String countryCode;
    private String uuidCode;

    public Patient(String firstName, String lastName, String email, String hPassword, String role, String dateOfBirth,
                   boolean hivPositive, String diagnosisDate, boolean onART, String artStartDate, String countryCode, String uuidCode) {
        super(firstName, lastName, email, hPassword, role);
        this.dateOfBirth = dateOfBirth;
        this.hivPositive = hivPositive;
        this.diagnosisDate = diagnosisDate;
        this.onART = onART;
        this.artStartDate = artStartDate;
        this.countryCode = countryCode;
        this.uuidCode = uuidCode;
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
        System.out.println("----------------------------------------------");
        System.out.println("Press Enter to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        return;

    }

    @Override
    public void updateProfile() {
        Scanner scanner = new Scanner(System.in);
        Boolean updated = false;
        System.out.println("Finalizing registration...");
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();
        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();
        System.out.println("Enter your date of birth:");
        String dob = scanner.nextLine();
        System.out.println("Are you HIV positive?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        String hivPositive = scanner.nextLine();
        boolean hivStatus = hivPositive.equals("1") ? true : false;

        if (!hivStatus){
            System.out.println("This tool is only for HIV positive patients. Please consult your doctor for further assistance.");
            return;
        }

        String diagnosisDate = "";
        String artStartDate = "";
        boolean onARTStatus = false;

        
        if (hivStatus) {
            System.out.println("Enter your diagnosis date:");
            diagnosisDate = scanner.nextLine(); 
            System.out.println("Are you on ART?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String onART = scanner.nextLine();
            onARTStatus = onART.equals("1") ? true : false;
            if (onARTStatus) {
                System.out.println("Enter your ART start date:");
                artStartDate = scanner.nextLine();
            }else{
                artStartDate = "";
            }
        }
        System.out.println("Enter your country code:");
        String countryCode = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();
        //Calculation Method Here
        String hPassword = UserMgmt.hashPassword(password);
        Patient user = new Patient(firstName, lastName, email, hPassword, role, dob, hivStatus, diagnosisDate, onARTStatus, artStartDate, countryCode, uuidCode);
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
}
