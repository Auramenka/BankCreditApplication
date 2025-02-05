package com.innowise.factory;

import com.innowise.constants.DataConstants;
import com.innowise.template.AbstractDataHandler;

public class CreditsHandler extends AbstractDataHandler {

    @Override
    protected String getDataKey() {
        return DataConstants.CREDITS;
    }
}
