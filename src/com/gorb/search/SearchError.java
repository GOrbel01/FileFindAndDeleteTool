package com.gorb.search;

import java.util.ArrayList;
import java.util.List;

public class SearchError {
    private List<ErrorMessage> errorMessages;

    public SearchError() {
        errorMessages = new ArrayList<>();
    }

    public void addError(ErrorMessage errorMessage) {
        errorMessages.add(errorMessage);
    }

    public boolean hasError() {
        boolean result = false;
        for (ErrorMessage msg : errorMessages) {
            if (msg != ErrorMessage.NONE) {
                result = true;
            }
        }

        return result;
    }

    public ErrorMessage getFirstError() {
        for (ErrorMessage msg : errorMessages) {
            if (msg != ErrorMessage.NONE) {
                return msg;
            }
        }
        return ErrorMessage.NONE;
    }
}
