CREATE TABLE IF NOT EXISTS readers
(
    id          BIGINT AUTO_INCREMENT   NOT NULL,
    reader_id   BIGINT                  NULL,

    name        VARCHAR(64)             NOT NULL,
    surname     VARCHAR(64)             NULL,

    birthday    DATE                    NULL,

    phone1      VARCHAR(20)             NULL,
    phone2      VARCHAR(20)             NULL,
    email       VARCHAR(20)             NULL,

    address     VARCHAR(256)            NULL,
    passport    VARCHAR(30)             NULL,

    CONSTRAINT pk_readers PRIMARY KEY (id),
    CONSTRAINT reader_id_unique UNIQUE (reader_id),
    CONSTRAINT main_info_unique UNIQUE (name, surname, birthday)
);