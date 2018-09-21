package com.gorb.search;

public enum ErrorMessage {

    NONE("None"), FILE_NOT_EXIST("File Does not Exist."),
    EXT_EMPTY("Empty file Extension."), MULTIPLE_DOTS("Cannot contain non-leading '.' Character."),
    SEARCH_LENGTH("Search Length must be greater than 2");

    private String value;

    ErrorMessage(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
