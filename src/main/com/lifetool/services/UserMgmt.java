package main.com.lifetool.services;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UserMgmt {
    public static boolean onBoardUser(String email){
        System.out.println(email + "this user has been onboarded");
        // try {
        //     ProcessBuilder pb = new ProcessBuilder("bash", "-c", "./scripts/user-manager.sh register " + email);
        //     Process process = pb.start();
        //     process.waitFor();
        //     BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         System.out.println(line);
        //     }
        // }catch(Exception e){
        //     e.printStackTrace();
        // }
        return true;
    }

    public static String login(String email, String password) {
        // TODO Auto-generated method stub
        return "Admin";
        // throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    public static boolean verifyUUID(String uuid, String email) {
        // TODO Auto-generated method stub
        return true;
        // throw new UnsupportedOperationException("Unimplemented method 'verifyUUID'");
    }
}
