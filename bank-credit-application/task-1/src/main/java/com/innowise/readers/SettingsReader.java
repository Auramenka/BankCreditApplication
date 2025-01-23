package com.innowise.readers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.innowise.constants.DataConstants;
import com.innowise.exceptions.JsonReadException;
import com.innowise.model.Settings;
import com.innowise.utils.LocalDateDeserializer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class SettingsReader {

    private static final String SETTINGS_FILE_PATH = "task-1/data/settings.json";
    private static final String JSON_READ_ERROR = "Ошибка при чтении JSON файла";

    public Settings readSettings() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(SETTINGS_FILE_PATH))) {
            JsonObject jsonObject = JsonParser.parseReader(bufferedReader).getAsJsonObject();
            JsonObject settingsObject = jsonObject.getAsJsonObject(DataConstants.SETTINGS);
            return gson.fromJson(settingsObject, Settings.class);
        } catch (IOException e) {
            throw new JsonReadException(JSON_READ_ERROR);
        }
    }

}
