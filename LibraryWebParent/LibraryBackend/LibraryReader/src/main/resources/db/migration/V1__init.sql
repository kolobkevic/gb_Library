DROP TABLE IF EXISTS settings;

-- library.mail_settings definition
DROP TABLE IF EXISTS mail_settings;
CREATE TABLE mail_settings (
                                 key_name       varchar(32)         NOT NULL,
                                 value          varchar(124)        NOT NULL,
                                 PRIMARY KEY (key_name)
);

-- library.roles definition
DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
                         id               int           NOT NULL AUTO_INCREMENT,
                         description      varchar(150)  NOT NULL,
                         name             varchar(30)   NOT NULL,
                         role_type        varchar(10)   NOT NULL,
                         created_at       datetime(6)   DEFAULT NULL,
                         updated_at       datetime(6)   DEFAULT NULL,
                         PRIMARY KEY (id),
                         UNIQUE KEY `UK_roles_name` (name)
);

-- library.users definition
DROP TABLE IF EXISTS users;
CREATE TABLE users (
                         id                 int             NOT NULL AUTO_INCREMENT,
                         created_at         datetime(6)     DEFAULT NULL,
                         email              varchar(128)    NOT NULL,
                         enabled            bit(1)          NOT NULL,
                         first_name         varchar(45)     NOT NULL,
                         last_name          varchar(60)     NOT NULL,
                         password           varchar(64)     DEFAULT NULL,
                         registration_type  varchar(10)     NOT NULL,
                         updated_at         datetime(6)     DEFAULT NULL,
                         verification_code  varchar(64)     DEFAULT NULL,
                         PRIMARY KEY (id),
                         UNIQUE KEY `UK_users_email` (email)
);

-- library.users_roles definition
DROP TABLE IF EXISTS users_roles;
CREATE TABLE users_roles (
                               user_id      int         NOT NULL,
                               role_id      int         NOT NULL,
                               PRIMARY KEY (user_id, role_id),
                               KEY `K_users_roles_role` (role_id),
                               CONSTRAINT `FK_users_roles_user_id` FOREIGN KEY (user_id) REFERENCES users (id),
                               CONSTRAINT `FK_users_roles_role_id` FOREIGN KEY (role_id) REFERENCES roles (id)
);

-- library.storages definition
DROP TABLE IF EXISTS storages;
CREATE TABLE storages (
                            id              int             NOT NULL AUTO_INCREMENT,
                            available       bit(1)          NOT NULL,
                            created_at      datetime(6)     DEFAULT NULL,
                            sector          varchar(10)     NOT NULL,
                            updated_at      datetime(6)     DEFAULT NULL,
                            zone            varchar(40)     NOT NULL,
                            PRIMARY KEY (id)
);

-- library.genres definition
DROP TABLE IF EXISTS genres;
CREATE TABLE genres (
                          id                int                 NOT NULL AUTO_INCREMENT,
                          created_at        datetime(6)         DEFAULT NULL,
                          description       varchar(128)        DEFAULT NULL,
                          name              varchar(40)         NOT NULL,
                          updated_at        datetime(6)         DEFAULT NULL,
                          PRIMARY KEY (id),
                          UNIQUE KEY `UK_genres_name` (name)
);

-- library.authors definition
DROP TABLE IF EXISTS authors;
CREATE TABLE authors (
                           id               int             NOT NULL AUTO_INCREMENT,
                           created_at       datetime(6)     DEFAULT NULL,
                           first_name       varchar(45)     NOT NULL,
                           last_name        varchar(60)     NOT NULL,
                           updated_at       datetime(6)     DEFAULT NULL,
                           PRIMARY KEY (id)
);

-- library.world_books definition
DROP TABLE IF EXISTS world_books;
CREATE TABLE world_books (
                               id               int             NOT NULL AUTO_INCREMENT,
                               age_rating       varchar(4)      NOT NULL,
                               created_at       datetime(6)     DEFAULT NULL,
                               description      varchar(250)    DEFAULT NULL,
                               title            varchar(128)    NOT NULL,
                               updated_at       datetime(6)     DEFAULT NULL,
                               author_id        int             NOT NULL,
                               genre_id         int             NOT NULL,
                               PRIMARY KEY (id),
                               KEY `K_world_books_author` (author_id),
                               KEY `K_world_books_genre` (genre_id),
                               CONSTRAINT `FK_world_books_author_id` FOREIGN KEY (author_id) REFERENCES authors (id),
                               CONSTRAINT `FK_world_books_genres_id` FOREIGN KEY (genre_id) REFERENCES genres (id)
);

-- library.library_books definition
DROP TABLE IF EXISTS library_books;
CREATE TABLE library_books (
                                 id                 int             NOT NULL AUTO_INCREMENT,
                                 available          bit(1)          NOT NULL,
                                 created_at         datetime(6)     DEFAULT NULL,
                                 inventory_number   varchar(20)     NOT NULL,
                                 isbn               varchar(13)     NOT NULL,
                                 publisher          varchar(40)     NOT NULL,
                                 updated_at         datetime(6)     DEFAULT NULL,
                                 world_book_id      int             NOT NULL,
                                 storage_id         int             NOT NULL,
                                 PRIMARY KEY (id),
                                 UNIQUE KEY `UK_inventory_number` (inventory_number),
                                 KEY `K_library_books_world_book` (world_book_id),
                                 KEY `K_library_books_storage` (storage_id),
                                 CONSTRAINT `FK_library_book_world_books_id` FOREIGN KEY (world_book_id) REFERENCES world_books (id),
                                 CONSTRAINT `FK_library_book_storages_id` FOREIGN KEY (storage_id) REFERENCES storages (id)
);

-- library.books_on_hands definition
DROP TABLE IF EXISTS books_on_hands;
CREATE TABLE books_on_hands (
                                  id                int             NOT NULL AUTO_INCREMENT,
                                  created_at        datetime(6)     DEFAULT NULL,
                                  returned          bit(1)          NOT NULL,
                                  taken_at          date            NOT NULL,
                                  updated_at        datetime(6)     DEFAULT NULL,
                                  book_id           int             NOT NULL,
                                  user_id           int             NOT NULL,
                                  PRIMARY KEY (id),
                                  KEY `K_books_on_hands_book` (book_id),
                                  KEY `K_books_on_hands_user` (user_id),
                                  CONSTRAINT `FK_books_on_hands_library_books` FOREIGN KEY (book_id) REFERENCES library_books (id),
                                  CONSTRAINT `FK_books_on_hands_users` FOREIGN KEY (user_id) REFERENCES users (id)
);

-- library.books_wishlist definition
DROP TABLE IF EXISTS books_wishlist;
CREATE TABLE books_wishlist (
                                  id            int             NOT NULL AUTO_INCREMENT,
                                  book_id       int             NOT NULL,
                                  reader_id     int             NOT NULL,
                                  created_at    datetime(6)     DEFAULT NULL,
                                  updated_at    datetime(6)     DEFAULT NULL,
                                  PRIMARY KEY (id),
                                  KEY `K_books_wishlist_book` (book_id),
                                  KEY `K_books_wishlist_reader` (reader_id),
                                  CONSTRAINT `FK_books_wishlist_users_id` FOREIGN KEY (reader_id) REFERENCES users (id),
                                  CONSTRAINT `FK_books_wishlist_world_books_id` FOREIGN KEY (book_id) REFERENCES world_books (id)
);

-- library.reserved_books definition
DROP TABLE IF EXISTS reserved_books;
CREATE TABLE reserved_books (
                                  id                int             NOT NULL AUTO_INCREMENT,
                                  created_at        datetime(6)     DEFAULT NULL,
                                  updated_at        datetime(6)     DEFAULT NULL,
                                  library_book_id   int             DEFAULT NULL,
                                  user_id           int             NOT NULL,
                                  world_book_id     int             NOT NULL,
                                  PRIMARY KEY (id),
                                  KEY `K_reserved_books_library_book_id` (library_book_id),
                                  KEY `K_reserved_books_user_id` (user_id),
                                  KEY `K_reserved_books_world_book_id` (world_book_id),
                                  CONSTRAINT `FK_reserved_books_library_books_id` FOREIGN KEY (library_book_id) REFERENCES library_books (id),
                                  CONSTRAINT `FK_reserved_books_world_books_id` FOREIGN KEY (world_book_id) REFERENCES world_books (id),
                                  CONSTRAINT `FK_reserved_books_users_id` FOREIGN KEY (user_id) REFERENCES users (id)
);