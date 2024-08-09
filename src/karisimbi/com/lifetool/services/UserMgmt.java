package karisimbi.com.lifetool.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import karisimbi.com.lifetool.models.Patient;
import karisimbi.com.lifetool.models.Admin;

public class UserMgmt {

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
            System.out.println("UUID: " + uuid);
            System.out.println("Role: " + role);


            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh registerUser " + firstName + " " + lastName + " " + email + " " + hPassword + " " + dob + " " + hivStatus + " " + diagnosisDate + " " + onARTStatus + " " + artStartDate + " " + countryCode + " " + uuid + " " + role);

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
}
