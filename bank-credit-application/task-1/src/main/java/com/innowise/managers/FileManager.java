package com.innowise.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class FileManager {

    private static final String DATA_DIRECTORY = "task-1/data";
    private static final String DB_FILE = "db.json";
    private static final String SETTINGS_FILE = "settings.json";

    private final List<String> departments = new ArrayList<>();

    public void verifyDataDirectoryAndFiles() throws IOException {
        Path dataDir = Paths.get(DATA_DIRECTORY);

        if (!Files.exists(dataDir) || !Files.isDirectory(dataDir)) {
            throw new IOException("Directory not found");
        }

        checkFileExists(dataDir.resolve(DB_FILE));
        checkFileExists(dataDir.resolve(SETTINGS_FILE));
    }

    public void processBranchFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_DIRECTORY))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("db_") &&
                            path.getFileName().toString().endsWith(".json"))
                    .forEach(this::processBranchFile);
        }
    }

    private static void checkFileExists(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new IOException("File " + filePath.getFileName() + " not found");
        }
    }

    private void processBranchFile(Path path) {
        String fileName = path.getFileName().toString();
        String departmentName = fileName.substring(3, fileName.length() - 5);

        departments.add(departmentName);

        JsonDataTransferManager transferManager = new JsonDataTransferManager(path);
        try {
            transferManager.transferData();
        } catch (IOException e) {
            System.err.println("File processing error " + path + ": " + e.getMessage());
        }
    }

    public List<String> getDepartments() {
        return departments;
    }
}