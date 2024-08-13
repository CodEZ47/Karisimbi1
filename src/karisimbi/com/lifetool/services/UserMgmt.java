package karisimbi.com.lifetool.services;

import java.io.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.regex.*;

import karisimbi.com.lifetool.models.Patient;
import karisimbi.com.lifetool.models.Admin;

public class UserMgmt {
    public static final String RESET = "\033[0m";
    public static final String RED = "\033[0;31m";
    public static final String GREEN = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE = "\033[0;34m";

    public static String[] fetchUserByUUID(String uuid) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh fetchUserByUUID " + uuid);
            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String input = reader.readLine(); 
            System.out.println("input: " + input);
            String[] user = input.split(",");
            process.waitFor();

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            String [] error = {};
            return error;
        }
    }
    public static boolean onBoardUser(String email, String role){
        // System.out.println(email + "this user has been onboarded");
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh onBoardUser " + email + " " + role);
            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return true;
            } else {
                System.err.println("Process exited with code: " + exitCode);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String[] login(String email, String password) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "./scripts/user-manager.sh", "login", email, password);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String input = reader.readLine();
            String[] user = input.split(",");
            process.waitFor();

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            String [] error = {};
            return error;
        }

    }

    public static String verifyUUID(String uuid, String email) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh verifyUUID " + uuid + " " + email);
            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String role = reader.readLine();
            process.waitFor();

            return role;
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public static String hashPassword(String password) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh hashPassword " + password);
            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String hashedPassword = reader.readLine();
            process.waitFor();

            return hashedPassword;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                Pattern.compile("[A-Z]").matcher(password).find() &&
                Pattern.compile("[a-z]").matcher(password).find() &&
                Pattern.compile("[0-9]").matcher(password).find() &&
                Pattern.compile("[^a-zA-Z0-9]").matcher(password).find();
    }

    public static String getValidatedDate(Scanner scanner) {
        String date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate today = LocalDate.now();

        while (true) {
            date = scanner.nextLine();
            if (isValidDate(date)) {
                try {
                    LocalDate enteredDate = LocalDate.parse(date, formatter);
                    if (enteredDate.isBefore(today.plusDays(1))) {
                        break;
                    } else {
                        System.out.println("Date cannot be in the future. Please enter a valid date:");
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format. Please enter the date in MM-DD-YYYY format:");
                }
            } else {
                System.out.println("Invalid date format. Please enter the date in MM-DD-YYYY format:");
            }
        }
        return date;
    }

    private static boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        try {
            LocalDate parsedDate = LocalDate.parse(date, formatter);
            String reformattedDate = parsedDate.format(formatter);
            return date.equals(reformattedDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static String checkOption(Scanner scanner) {
        String option;
        while (true) {
            option = scanner.nextLine();
            if (option.equals("1") || option.equals("2")) {
                break;
            } else {
                System.out.println("Invalid option. Please enter 1 or 2:");
            }
        }
        return option;
    }

    public static boolean registerUser(Patient user) {
        try {

            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();
            String hPassword = user.getHPassword();
            String dob = user.getDateOfBirth();
            String hivStatus = String.valueOf(user.isHivPositive());
            String diagnosisDate = user.getDiagnosisDate();
            String onARTStatus = String.valueOf(user.isOnART());
            String artStartDate = user.getArtStartDate();
            String countryCode = user.getCountryCode();
            String uuid = user.getUuidCode();
            String role = user.getRole();
            String expectedLifeSpan = user.getExpectedLife();

            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Email: " + email);
            System.out.println("Hashed Password: " + hPassword);
            System.out.println("Date of Birth: " + dob);
            System.out.println("HIV Status: " + hivStatus);
            System.out.println("Diagnosis Date: " + diagnosisDate);
            System.out.println("On ART: " + onARTStatus);
            System.out.println("ART Start Date: " + artStartDate);
            System.out.println("Country Code: " + countryCode);
            System.out.println("Expected Lifespan: " + expectedLifeSpan);
            System.out.println("UUID: " + uuid);
            System.out.println("Role: " + role);


            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh registerUser " + firstName + " " + lastName + " " + email + " " + hPassword + " " + dob + " " + hivStatus + " " + diagnosisDate + " " + onARTStatus + " " + artStartDate + " " + countryCode + " " + uuid + " " + role + " " + expectedLifeSpan);

            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }


            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return true;
            } else {
                System.err.println("Process exited with code: " + exitCode);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerAdmin(Admin user){
        try {
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String email = user.getEmail();
            String hPassword = user.getHPassword();
            String role = user.getRole();

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh registerAdmin " + email + " " + role + " " + hPassword + " " + firstName + " " + lastName);
            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return true;
            } else {
                System.err.println("Process exited with code: " + exitCode);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static String fetchCountryCode(Scanner scanner, String country) {
        String countryCode = "";
        do {
            try {
                ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh getCountryCode " + country);
                Process process = pb.start();
                pb.redirectErrorStream(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                countryCode = reader.readLine();
                process.waitFor();
        
                if (countryCode.equals("NOT_FOUND")) {
                    System.out.println(RED + "Country not found. Please enter a valid country." + RESET);
                    country = scanner.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
                countryCode = "NOT_FOUND";
            }
        } while (countryCode.equals("NOT_FOUND"));
        
        System.out.println("Country code for " + country + " is " + countryCode);
        return countryCode;
    }


    public static String calculateLifespan(String dob, boolean hivStatus, String diagnosisDate, boolean artStatus, String artStartDate, String countryCode) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh getLifeExpectancy " + countryCode);
            Process process = pb.start();
            pb.redirectErrorStream(true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String lifeExpectancyStr = reader.readLine();
            process.waitFor();

            if (lifeExpectancyStr.equals("NOT_FOUND")) {
                return "0";
            }

            double lifeExpectancy = Double.parseDouble(lifeExpectancyStr);
            System.out.println(lifeExpectancy);
            int currentAge = calculateAge(dob, LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            int ageAtDiagnosis = calculateAge(dob, diagnosisDate);
            int ageAtStartART = calculateAge(dob, artStartDate);
            double remainingYears = (lifeExpectancy - currentAge)* 0.9;
            double survivalCoef = 0.9;
            int delay = ageAtStartART - ageAtDiagnosis;
            for (int i = 0; i < delay; i++) {
                remainingYears *= survivalCoef;
                System.out.println("Hahahaha---"+i);
            }
            String lifespan = String.valueOf((int)Math.ceil(remainingYears));
            return lifespan;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    private static int calculateAge(String dob, String eventDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        LocalDate eventLocalDate = LocalDate.parse(eventDate, formatter);
        Period period = Period.between(dateOfBirth, eventLocalDate);
        System.out.println(period.getYears());
        return period.getYears();
    }
}
