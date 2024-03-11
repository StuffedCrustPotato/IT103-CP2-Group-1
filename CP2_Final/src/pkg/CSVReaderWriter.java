package pkg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderWriter {

    private static final String AUTHORIZED_ACCOUNTS_FILE_PATH = "authorized_accounts.csv";
    private static final String EMPLOYEES_FILE_PATH = "employees.csv";

    public static List<String[]> readAuthorizedAccounts() {
        return readData(AUTHORIZED_ACCOUNTS_FILE_PATH);
    }

    public static void writeAuthorizedAccounts(List<String[]> data) {
        writeData(AUTHORIZED_ACCOUNTS_FILE_PATH, data);
    }

    public static List<String[]> readEmployees() {
        return readData(EMPLOYEES_FILE_PATH);
    }

    public static void writeEmployees(List<String[]> data) {
        writeData(EMPLOYEES_FILE_PATH, data);
    }

    public static List<String> readEvents(String filePath) {
        List<String> events = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                events.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return events;
    }

    public static void writeEvent(String event, String filePath) {
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(event).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void clearFile(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Clear the file by writing nothing
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readData(String filePath) {
        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] record = line.split(",");
                data.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    private static void writeData(String filePath, List<String[]> data) {
        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] record : data) {
                for (int i = 0; i < record.length; i++) {
                    writer.append(record[i]);
                    if (i < record.length - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
