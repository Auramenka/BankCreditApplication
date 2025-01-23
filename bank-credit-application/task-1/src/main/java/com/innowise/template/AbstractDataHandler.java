package com.innowise.template;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.innowise.factory.DataHandler;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;


public abstract class AbstractDataHandler implements DataHandler {

    @Override
    public void handleData(JsonObject source, JsonObject target, String key) {
        JsonArray events = source.getAsJsonArray(getDataKey());

        if (events != null) {
            JsonArray dbEvents = target.getAsJsonArray(getDataKey());
            if (dbEvents == null) {
                dbEvents = new JsonArray();
                target.add(getDataKey(), dbEvents);
            }

            List<JsonObject> eventList = StreamSupport.stream(events.spliterator(), false)
                    .map(e -> (JsonObject) e)
                    .toList();

            List<JsonObject> unmodifiabEventleList = Collections.unmodifiableList(eventList);
            unmodifiabEventleList.forEach(dbEvents::add);

            source.remove(getDataKey());
        }
    }

    protected abstract String getDataKey();
}
