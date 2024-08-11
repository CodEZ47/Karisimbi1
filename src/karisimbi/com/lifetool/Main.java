package karisimbi.com.lifetool;
import java.util.Scanner;
import java.io.Console;

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
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();
        boolean quit = false;



        while (!quit) {
            clearScreen();
            System.out.println(BLUE + "------------------------------------------------------------");
            System.out.println("Welcome to the Life Prognosis Management Tool!!");
            System.out.println("------------------------------------------------------------" + RESET);
            System.out.println(YELLOW + "1." + RESET + " Login");
            System.out.println(YELLOW + "2."+ RESET +  " Finalize Registration");
            System.out.println(YELLOW + "0." + RESET +  " Exit");
            System.out.println(" ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    String[] user = login(scanner, console);
                    if (user.length <= 1) {
                        System.out.println(RED + "Wrong email or password. Please try again." + RESET);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    String uuidCode = user[1];
                    if (user[2].equals("Admin")|| user[2].equals("Patient")) {
                        showLoadingSpinner("Logging in...");
                        manageAccount(scanner, user, uuidCode);

                    } else {
                        System.out.println("Login failed. Please try again. :(");
                    }

                    break;
                case 2:
                    // Register
                    System.out.println("Finalizing registration...");
                    verifyUUID(scanner, console);
                    break;
                case 0:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }

        System.out.println(BLUE + "------------------------------------------------------------");
        System.out.println("Thank you!");
        System.out.println("------------------------------------------------------------" + RESET);

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

    private static String[] login(Scanner scanner, Console console) {
        System.out.println("Enter your email address:");
        String email = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = new String(console.readPassword());

        String[] user = UserMgmt.login(email, password);
        return user;
    }

    private static void verifyUUID(Scanner scanner, Console console) {
        System.out.println("Enter the UUID:");
        String uuid = scanner.nextLine();
        System.out.println("Enter your email address:");
        String email = scanner.nextLine();
        String role = UserMgmt.verifyUUID(uuid, email);
        showLoadingSpinner("Verifying UUID...");
        if (role.equals("Admin") || role.equals("Patient")) {
            System.out.println("UUID verified successfully! :)");
            System.out.println("------------------------------------");
            finalizeRegistration(scanner, console, email, uuid, role);
        } else {
            System.out.println("UUID verification failed. Please try again. :(");
        }
        
    }

    private static void finalizeRegistration(Scanner scanner, Console console, String email, String uuid, String role) {
        boolean registered = false;

        if (console == null) {
            System.out.println("No console available. Cannot hide input.");
            return;
        }

        if (role.equals("Admin")){
            System.out.println("Finalizing registration...");
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
            registered = UserMgmt.registerAdmin(user);

        }else if(role.equals("Patient")){
            System.out.println("Finalizing registration...");
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
            
            System.out.println("Enter your country code:");
            String countryCode = scanner.nextLine();

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

            //Calculation Method Here
            String hPassword = UserMgmt.hashPassword(password);
            Patient user = new Patient(firstName, lastName, email, hPassword, role, dob, hivStatus, diagnosisDate, onARTStatus, artStartDate, countryCode, uuid);
            registered = UserMgmt.registerUser(user);
        }
        

        
        

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
        clearScreen();
        if(userRole.equals("Admin")){
            while(!quit){
            Admin admin = new Admin(user[4], user[5], user[0], user[3], user[2]);   
            System.out.println("------------------------------------------------------------");
            System.out.println("Welcome " + user[4] + " " + user[5] + "!");
            System.out.println("------------------------------------------------------------");         
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Download All User Data");
            System.out.println("4. Download Analytics");
            System.out.println("5. Onboard a New User");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    clearScreen();
                    admin.viewProfile();
                    clearScreen();
                    break;
                case "2":
                    admin.updateProfile();
                    break;
                case "3":
                    admin.downloadAllUsersData();
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
            Patient patient = new Patient(user[4], user[5], user[0], user[3], user[2], user[6], Boolean.parseBoolean(user[7]), user[8], Boolean.parseBoolean(user[9]), user[10], user[11], uuidCode);
            while(!quit){
            System.out.println("------------------------------------------------------------");
            System.out.println("Welcome " + user[4] + " " + user[5] + "!");
            System.out.println("------------------------------------------------------------");
            System.out.println("1. View Profile");
            System.out.println("2. Update Profile");
            System.out.println("3. Download Files");
            System.out.println("0. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    patient.viewProfile();
                    break;
                case "2":
                    patient.updateProfile();
                    break;
                case "3":
                    // Download Files
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

        
    }
}
