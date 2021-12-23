CREATE SEQUENCE IF NOT EXISTS book_library_book_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  MAXVALUE 2147483647
  CACHE 1;
CREATE TABLE IF NOT EXISTS Book (
    id int8 NOT NULL DEFAULT nextval('book_library_book_id_seq'),
    title VARCHAR(255),
    author VARCHAR(255),
    isbnCode VARCHAR(255),
    created_date date,
    updated_date date
    PRIMARY KEY(id));
);

CREATE SEQUENCE IF NOT EXISTS book_library_reader_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;

CREATE TABLE IF NOT EXISTS book_reader(
  id BIGINT NOT NULL,
  name VARCHAR(255),
  address VARCHAR(255),
  age INTEGER,
  created_date date,
  updated_date date
  CONSTRAINT pk_bookreader PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS book_library_book_inventory_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
CREATE TABLE IF NOT EXISTS book_inventory (
    id int8 NOT NULL DEFAULT nextval('book_library_book_inventory_id_seq'),
    available int8,
    allotted int8,
    id_book int8,
    created_date date,
    updated_date date
)

ALTER TABLE IF EXISTS book_inventory
ADD CONSTRAINT book_fk
FOREIGN KEY (id_book) REFERENCES book;