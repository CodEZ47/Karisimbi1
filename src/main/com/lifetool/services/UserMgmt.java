package main.com.lifetool.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserMgmt {
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

    public static String login(String email, String password) {
        try {
            ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh", "login_user", email, password);
            pb.redirectErrorStream(true);
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String role = reader.readLine(); 

            process.waitFor();

            return "Admin";

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static boolean verifyUUID(String uuid, String email) {
        // TODO Auto-generated method stub
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'verifyUUID'");
    }
}
