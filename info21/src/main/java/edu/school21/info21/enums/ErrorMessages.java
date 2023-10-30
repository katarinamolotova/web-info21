package edu.school21.info21.enums;

public enum ErrorMessages {
    INPUT_FILE_ERROR_EMPTY("Загрузка не удалась, пустой файл"),
    INPUT_FILE_ERROR_DUPLICATE("Загрузка не удалась, дубликаты первичного ключа"),
    INPUT_FILE_ERROR_INVALID_DATA("Загрузка не удалась, неподходящие данные для таблицы"),
    INPUT_FILE_ERROR_SOMETHING_WRONG("Загрузка не удалась, неизвестная ошибка"),
    INPUT_FILE_SUCCESS("Файл загружен успешно"),
    OUTPUT_FILE_SUCCESS("Экспорт выполнен"),
    OUTPUT_FILE_ERROR("Ошибка экспорта");

    private final String name;

    ErrorMessages(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
