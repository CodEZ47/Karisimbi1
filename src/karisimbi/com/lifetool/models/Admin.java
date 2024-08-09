package karisimbi.com.lifetool.models;

import java.util.Scanner;
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
}
