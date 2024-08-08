import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class AdminDownloads {

    // Method to call the bash script to download all users' data
    public void downloadAllUserData(String email) {
        String scriptPath = "download_all_user_data.sh"; // Path to your bash script

        try {
            // Use bash to execute the script
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, email);
            processBuilder.redirectErrorStream(true);

            // Start the process
            Process process = processBuilder.start();

            // Read the output of the bash script
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print the output to the console
            }

            // Wait for the process to complete and get the exit value
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("All users' data downloaded successfully.");
            } else {
                System.out.println("Error in downloading users' data. Exit code: " + exitCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Main method to test the admin download method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your admin's email:");
        String email = scanner.nextLine();
        AdminDownloads admin = new AdminDownloads();
        admin.downloadAllUserData(email);
    }
}