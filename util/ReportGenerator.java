package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {

    public static void generateReport(Path logsDir, Path reportFile) {
        long totalDeposits = 0;
        long totalWithdrawals = 0;
        long totalBytes = 0;

        try {
            if (!Files.exists(logsDir)) {
                Files.createDirectories(logsDir);
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(logsDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                        totalBytes += attrs.size();
                        try (BufferedReader reader = Files.newBufferedReader(entry)) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] parts = line.split(" ");
                                if (parts.length >= 3) {
                                    String type = parts[0];
                                    long amount = Long.parseLong(parts[2]);
                                    if (type.equalsIgnoreCase("DEPOSIT")) {
                                        totalDeposits += amount;
                                    } else if (type.equalsIgnoreCase("WITHDRAW")) {
                                        totalWithdrawals += amount;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (reportFile.getParent() != null && !Files.exists(reportFile.getParent())) {
                Files.createDirectories(reportFile.getParent());
            }

            try (BufferedWriter writer = Files.newBufferedWriter(reportFile)) {
                writer.write("Summary Report\n");
                writer.write("Total Log Size: " + totalBytes + " bytes\n");
                writer.write("Total Deposits: " + totalDeposits + "\n");
                writer.write("Total Withdrawals: " + totalWithdrawals + "\n");
                writer.write("Net Balance: " + (totalDeposits - totalWithdrawals) + "\n");
            }
        } catch (IOException e) {
            System.out.println("Report generation failed: " + e.getMessage());
        }
    }
}
