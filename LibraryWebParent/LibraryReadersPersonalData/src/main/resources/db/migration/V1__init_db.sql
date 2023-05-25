-- -------------------------------------------------- --
-- Схема базы данных на языке SQL диалекта PostgreSQL --
-- -------------------------------------------------- --

CREATE TABLE "readers" (
    "id"        BIGSERIAL       PRIMARY KEY,-- Идентификатор записи
    "reader_id" BIGINT          NOT NULL,   -- Уникальный идентификатор читателя

    "family"    VARCHAR(64)     NOT NULL,   -- Фамилия  //УДАЛИТЬ
    "name"      VARCHAR(64)     NOT NULL,   -- Имя
    "surname"   VARCHAR(64)     NULL,       -- Фамилия

    "birthday"  DATE            NULL,       -- Дата рождения

    "phone1"    VARCHAR(20)     NULL,       -- Телефон (домашний)
    "phone2"    VARCHAR(20)     NULL,       -- Телефон (рабочий)
    "email"     VARCHAR(50)     NULL,       -- Адрес электронной почты

    "address"   VARCHAR(256)    NULL,       -- Адрес места жительства
    "passport"  VARCHAR(30)     NULL        -- Паспорт/личный номер

    CONSTRAINT "reader_id_unique" UNIQUE ("reader_id"),
    CONSTRAINT "main_info_unique" UNIQUE ("name", "surname", "birthday")
);
