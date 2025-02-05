package com.innowise.managers;

import com.innowise.exceptions.FileManagerException;

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
    private static final String BRANCH_FILE_PREFIX = "db_";
    private static final String BRANCH_FILE_SUFFIX = ".json";

    private static final String DIRECTORY_NOT_FOUND = "Directory not found";
    private static final String FILE_NOT_FOUND = "File %s not found";
    private static final String FILE_PROCESSING_ERROR = "File processing error %s: %s";

    private final List<String> departments = new ArrayList<>();

    public void verifyDataDirectoryAndFiles() throws IOException {
        Path dataDir = Paths.get(DATA_DIRECTORY);

        if (!Files.exists(dataDir) || !Files.isDirectory(dataDir)) {
            throw new FileManagerException(DIRECTORY_NOT_FOUND);
        }

        checkFileExists(dataDir.resolve(DB_FILE));
        checkFileExists(dataDir.resolve(SETTINGS_FILE));
    }

    public void processBranchFiles() throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(DATA_DIRECTORY))) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith(BRANCH_FILE_PREFIX) &&
                            path.getFileName().toString().endsWith(BRANCH_FILE_SUFFIX))
                    .forEach(this::processBranchFile);
        }
    }

    private static void checkFileExists(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            throw new FileManagerException(String.format(FILE_NOT_FOUND, filePath.getFileName()));
        }
    }

    private void processBranchFile(Path path) {
        String fileName = path.getFileName().toString();
        String[] parts = fileName.split("_");
        String departmentName = parts[1].replace(BRANCH_FILE_SUFFIX, "");

        departments.add(departmentName);

        JsonDataTransferManager transferManager = new JsonDataTransferManager(path);
        try {
            transferManager.transferData();
        } catch (IOException e) {
            System.err.println(String.format(FILE_PROCESSING_ERROR, path, e.getMessage()));
        }
    }

    public List<String> getDepartments() {
        return departments;
    }
}