package com.developerkurt.model;

public enum Category {
    ELECTRONIC,
    FASHION,
    HOME_DECOR;

    @Override
    public String toString() {
        switch (this) {
            case FASHION:
                return "Fashion";
            case ELECTRONIC:
                return "Electronic";
            case HOME_DECOR:
                return "Home Decor";
            default:
                throw new IllegalStateException();
        }
    }
}
