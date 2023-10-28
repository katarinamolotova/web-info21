package edu.school21.info21.enums;

public enum Directory {
    IMPORT("/import/"),
    EXPORT("/export/");

    private final String name;

    Directory(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
