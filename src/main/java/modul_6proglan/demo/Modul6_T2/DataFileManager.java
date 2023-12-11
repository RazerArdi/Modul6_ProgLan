package modul_6proglan.demo.Modul6_T2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataFileManager {

    private static final String DATA_FILE_PATH = "Data.txt";
    private static final String REQUESTS_FILE_PATH = "Data_PermintaanUser.txt";

    public List<String> readData() throws IOException {
        Path path = Paths.get(DATA_FILE_PATH);
        return Files.readAllLines(path);
    }

    public List<String> readRequests() throws IOException {
        Path path = Paths.get(REQUESTS_FILE_PATH);
        return Files.readAllLines(path);
    }

    public void writeData(List<String> data) throws IOException {
        Path path = Paths.get(DATA_FILE_PATH);
        Files.write(path, data);
    }

    public void writeRequests(List<String> requests) throws IOException {
        Path path = Paths.get(REQUESTS_FILE_PATH);
        Files.write(path, requests);
    }
}

