package com.innowise.factory;

import com.google.gson.JsonObject;

public interface DataHandler {

    void handleData(JsonObject source, JsonObject target, String key);

}
