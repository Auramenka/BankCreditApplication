package com.innowise;

import com.innowise.exceptions.FileManagerException;
import com.innowise.managers.FileManager;
import com.innowise.model.CreditResults;
import com.innowise.model.Settings;
import com.innowise.readers.JsonReader;
import com.innowise.readers.SettingsReader;
import com.innowise.service.CreditResultsService;
import com.innowise.utils.CreditTableFormatter;

import java.io.IOException;
import java.util.List;

public class ApplicationRunner {

    private static final String JSON_READ_ERROR = "Ошибка при обработке файла";

    public void run() {
        try {
            FileManager fileManager = new FileManager();
            initializeFileManager(fileManager);

            JsonReader jsonReader = new JsonReader();
            Settings settings = initializeSettings(fileManager, jsonReader);

            List<CreditResults> creditResults = processCreditResults(settings);

            printCreditResults(creditResults);
        } catch (IOException e) {
            throw new FileManagerException(JSON_READ_ERROR);
        }
    }

    private void initializeFileManager(FileManager fileManager) throws IOException {
        fileManager.verifyDataDirectoryAndFiles();
        fileManager.processBranchFiles();
    }

    private Settings initializeSettings(FileManager fileManager, JsonReader jsonReader) throws IOException {
        jsonReader.readJson();
        SettingsReader settingsReader = new SettingsReader();
        Settings settings = settingsReader.readSettings();
        settings.setUseDepartments(fileManager.getDepartments());
        return settings;
    }

    private List<CreditResults> processCreditResults(Settings settings) {
        CreditResultsService creditResultsService = new CreditResultsService();
        List<CreditResults> creditResults = creditResultsService.getCreditResults(settings);
        creditResultsService.sortCreditResults(creditResults, settings);
        return creditResults;
    }

    private void printCreditResults(List<CreditResults> creditResults) {
        CreditTableFormatter creditTableFormatter = new CreditTableFormatter();
        creditTableFormatter.printTable(creditResults);
    }

}
