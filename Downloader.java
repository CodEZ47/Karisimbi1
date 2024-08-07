import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Downloader {

    // Method to call the bash script with a specific user ID
    public void downloadFiles(String userId) {
        String scriptPath = "downloadFile.sh"; // Path to your bash script

        try {
            // Create a process builder to run the bash script with the user ID as an argument
            ProcessBuilder processBuilder = new ProcessBuilder("bash", scriptPath, userId);
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
                System.out.println("Download query successfully completed.");
            } else {
                System.out.println("Error in downloading user info. Exit code: " + exitCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Main method to test the downloadFiles method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the user ID:");
        String userId = scanner.nextLine();
        Downloader fileDownloader = new Downloader();
        fileDownloader.downloadFiles(userId);
    }
}
