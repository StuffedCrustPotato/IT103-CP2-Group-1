package pkg;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderWriter {

    private static final String AUTHORIZED_ACCOUNTS_FILE_PATH = "C:\\Users\\james\\eclipse-workspace\\CSV\\authorized_accounts.csv";
    private static final String EMPLOYEES_FILE_PATH = "C:\\Users\\james\\eclipse-workspace\\CSV\\employees.csv";

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
