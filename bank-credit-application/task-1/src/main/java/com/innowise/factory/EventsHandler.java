package com.innowise.factory;

import com.innowise.constants.DataConstants;
import com.innowise.template.AbstractDataHandler;

public class EventsHandler extends AbstractDataHandler {

    @Override
    protected String getDataKey() {
        return DataConstants.EVENTS;
    }
}
