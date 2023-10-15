package edu.school21.info21.enums;

public enum CheckState {
    START("start"),
    SUCCESS("success"),
    FAILURE("failure");

    private final String name;

    CheckState(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
