package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class FileManager {

    private static final String LOG_FILE = "data/logs.txt";
    private static final String BACKUP_FILE = "data/backup.txt";
    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

     
    static {
        new File("data").mkdirs();
    }

    
    public static List<String> loadConsumerLines() {
        List<String> lines = new ArrayList<>();
        File file = new File(BACKUP_FILE);
        if (!file.exists()) {
            return lines;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Warning: could not read " + BACKUP_FILE + " (" + e.getMessage() + ")");
        }
        return lines;
    }

    
    public static void logAction(String action) {
        Thread logThread = new Thread(() -> writeLine(LOG_FILE, action), "log-writer");
        logThread.start();
    }

    private static void writeLine(String path, String message) {
        String timestamped = "[" + LocalDateTime.now().format(TIMESTAMP) + "] " + message;
        try (FileWriter fw = new FileWriter(path, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(timestamped);
        } catch (IOException e) {
            System.err.println("Warning: could not write to " + path + " (" + e.getMessage() + ")");
        }
    }

    
    public static void backupConsumers(String consumerDump) {
        Thread backupThread = new Thread(() -> {
            try (FileWriter fw = new FileWriter(BACKUP_FILE, false);
                 PrintWriter pw = new PrintWriter(fw)) {
                pw.print(consumerDump);
            } catch (IOException e) {
                System.err.println("Warning: backup failed (" + e.getMessage() + ")");
            }
        }, "backup-writer");
        backupThread.start();
    }

    
    public static void backupConsumersSync(String consumerDump) {
        try (FileWriter fw = new FileWriter(BACKUP_FILE, false);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.print(consumerDump);
        } catch (IOException e) {
            System.err.println("Warning: backup failed (" + e.getMessage() + ")");
        }
    }
}