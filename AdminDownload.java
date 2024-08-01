import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AdminDownload {
    private static final String USER_STORE = "user-store.txt";

    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter admin email: ");
            String adminEmail = reader.readLine();
            System.out.print("Enter admin password: ");
            String adminPassword = new String(System.console().readPassword());
            String hashedPassword = hashPassword(adminPassword);

            if (verifyAdmin(adminEmail, hashedPassword)) {
                downloadCSV();
                System.out.println("CSV files downloaded.");
            } else {
                System.out.println("Invalid admin credentials.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hashPassword(String password) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("bash", "-c", "echo -n \"" + password + "\" | openssl dgst -sha256 | awk '{print $2}'");
        Process process = processBuilder.start();
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        return reader.readLine().trim();
    }

    private static boolean verifyAdmin(String email, String hashedPassword) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(USER_STORE));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(email) && line.contains(hashedPassword) && line.contains("ADMIN")) {
                return true;
            }
        }
        return false;
    }

    private static void downloadCSV() throws IOException {
        Files.createFile(Paths.get("all_usersFile.csv"));
        Files.createFile(Paths.get("analyticsFile.csv"));
    }
}