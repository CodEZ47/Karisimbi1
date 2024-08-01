package main.com.lifetool;

import java.util.Scanner;
import main.com.lifetool.services.UserMgmt;
import main.com.lifetool.models.User;
import main.com.lifetool.models.Admin;
// import main.com.lifetool.models.Patient;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;

        while (!quit) {
            System.out.println("------------------------------------------------------------");
            System.out.println("Welcome to the Life Prognosis Management Tool!!");
            System.out.println("------------------------------------------------------------");
            System.out.println("1. Login");
            System.out.println("2. Finalize Registration");
            System.out.println("0. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Login
                    System.out.println("Logging in...");
                    String userRole = login(scanner);
                    if (userRole == "Admin" || userRole == "Patient") {
                        System.out.println("Login successful! :)");
                        manageAccount(scanner, userRole);

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

    private static String login(Scanner scanner) {
        System.out.println("Enter your email address:");
        // add validation here
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        // add validation here
        String password = scanner.nextLine();
        String userRole = UserMgmt.login(email, password);
        return userRole;
    }

    private static void verifyUUID(Scanner scanner) {
        System.out.println("Enter the UUID:");
        String uuid = scanner.nextLine();
        System.out.println("Enter your email address:");
        String email = scanner.nextLine();
        boolean verified = UserMgmt.verifyUUID(uuid, email);
        if (verified) {
            System.out.println("UUID verified successfully! :)");
            finalizeRegistration(scanner);
        } else {
            System.out.println("UUID verification failed. Please try again. :(");
        }
        
    }

    private static void finalizeRegistration(Scanner scanner) {
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
        if (hivPositive.equals("1")) {
            hivPositive = "yes";
        } else {
            hivPositive = "no";
        }

        if (hivPositive == "yes") {
            System.out.println("Enter your diagnosis date:");
            String diagnosisDate = scanner.nextLine();
            System.out.println("Are you on ART?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            String onART = scanner.nextLine();
            if (onART.equals("1")) {
                onART = "yes";
            } else {
                onART = "no";
            }
            if (onART == "yes") {
                System.out.println("Enter your ART start date:");
                String artStartDate = scanner.nextLine();
            }
        }
        System.out.println("Enter your country code:");
        String countryCode = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();


        
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

    private static void manageAccount(Scanner scanner, String userRole) {
        if(userRole == "Admin"){
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Download All User Data");
            System.out.println("4. Download Analytics");
            System.out.println("5. Onboard a New User");
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
                    // Download All User Data
                    break;
                case "4":
                    // Download Analytics
                    break;
                case "5":
                    onBoardUser(scanner);
                
                    break;
                case "0":
                    // Exit
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        else{
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
