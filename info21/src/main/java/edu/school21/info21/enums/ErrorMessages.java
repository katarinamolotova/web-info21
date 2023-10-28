package edu.school21.info21.enums;

public enum ErrorMessages {
    INPUT_FILE_EMPTY("Загрузка не удалась, так как файл пустой"),
    INPUT_FILE_ERROR("Загрузка не удалась, => "),
    INPUT_FILE_SUCCESS("Файл загружен успешно");;

    private final String name;

    ErrorMessages(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
