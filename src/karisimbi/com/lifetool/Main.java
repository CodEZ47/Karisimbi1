package karisimbi.com.lifetool;

import java.util.Arrays;
import java.util.Scanner;

import karisimbi.com.lifetool.models.Admin;
import karisimbi.com.lifetool.models.Patient;
import karisimbi.com.lifetool.models.User;
import karisimbi.com.lifetool.services.UserMgmt;

public class Main {

    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;



        while (!quit) {
            // clearScreen();
            System.out.println("------------------------------------------------------------");
            System.out.println("Welcome to the Life Prognosis Management Tool!!");
            System.out.println("------------------------------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Finalize Registration");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    String[] user = login(scanner);
                    String uuidCode = user[1];
                    if (user[2].equals("Admin")|| user[2].equals("Patient")) {
                        showLoadingSpinner("Logging in...");
                        System.out.println(user[1]);
                        manageAccount(scanner, user, uuidCode);

                    } else {
                        System.out.println("Login failed. Please try again. :(");
                    }

                    break;
                case 2:
                    // Register
                    System.out.println("Finalizing registration...");
                    verifyUUID(scanner);
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        System.out.println("------------------------------------------------------------");
        System.out.println("Thank you!");
        System.out.println("------------------------------------------------------------");

        scanner.close();
    }

    private static void clearScreen() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private static void showLoadingSpinner(String text) {
        System.out.println(text);
        String[] spinner = {"|", "/", "-", "\\"};
        for (int i = 0; i < 10; i++) {
            System.out.print("\rLoading " + spinner[i % spinner.length]);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.print("\rDone!        \n");
        System.out.println("------------------------------------");
    }

    private static String[] login(Scanner scanner) {
        System.out.println("Enter your email address:");
        // add validation here
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        // add validation here
        String password = scanner.nextLine();

        String[] user = UserMgmt.login(email, password);
        return user;
    }

    private static void verifyUUID(Scanner scanner) {
        System.out.println("Enter the UUID:");
        String uuid = scanner.nextLine();
        System.out.println("Enter your email address:");
        String email = scanner.nextLine();
        String role = UserMgmt.verifyUUID(uuid, email);
        showLoadingSpinner("Verifying UUID...");
        if (role.equals("Admin") || role.equals("Patient")) {
            System.out.println("UUID verified successfully! :)");
            System.out.println("------------------------------------");
            finalizeRegistration(scanner, email, uuid, role);
        } else {
            System.out.println("UUID verification failed. Please try again. :(");
        }
        
    }

    private static void finalizeRegistration(Scanner scanner, String email, String uuid, String role) {
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

        String hPassword = UserMgmt.hashPassword(password);

        Patient user = new Patient(firstName, lastName, email, hPassword, role, dob, hivStatus, diagnosisDate, onARTStatus, artStartDate, countryCode, uuid);
        boolean registered = UserMgmt.registerUser(user);

        if (registered) {
            System.out.println("Registration successful! :)");
        } else {
            System.out.println("Registration failed. Please try again. :(");
        }
    }

    private static void onBoardUser(Scanner scanner) {
        System.out.println("Enter the user's email address:");
        // add validation here
        String email = scanner.nextLine();
        System.out.println("Enter the user's role: (Admin/Patient)");
        String role = scanner.nextLine();
        boolean userOnboarded = UserMgmt.onBoardUser(email, role);
        if (userOnboarded) {
            System.out.println("User onboarded successfully! :)");
        } else {
            System.out.println("User onboarding failed. Please try again. :(");
        }
    }

    private static void manageAccount(Scanner scanner, String[] user, String uuidCode) {
        String userRole = user[2];
        boolean quit = false;

        if(userRole.equals("Admin")){
            while(!quit){
            Admin admin = new Admin(user[4], user[5], user[0], user[3], user[2]);            
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Download All User Data");
            System.out.println("4. Download Analytics");
            System.out.println("5. Onboard a New User");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    admin.viewProfile();
                    break;
                case "2":
                    // Update Profile
                    break;
                case "3":
                    // Download All User Data
                    break;
                case "4":
                    // Download Analytics
                    break;
                case "5":
                    onBoardUser(scanner);
                
                    break;
                case "0":
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        }
        else{
            Patient patient = new Patient(user[0], user[1], user[3], user[4], user[2], user[5], Boolean.parseBoolean(user[6]), user[7], Boolean.parseBoolean(user[8]), user[9], user[10], uuidCode);
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Download Files");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    // View Profile
                    break;
                case "2":
                    // Update Profile
                    break;
                case "3":
                    // Download Files
                    break;
                case "0":
                    // Exit
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        
    }
}

// public static String calculateLifeExpectancy()