CREATE SEQUENCE IF NOT EXISTS book_library_reader_book_allotment_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 2147483647
    CACHE 1;
CREATE TABLE IF NOT EXISTS reader_book_allotment (
    id int8 NOT NULL DEFAULT nextval('book_library_reader_book_allotment_id_seq'),
    id_reader int8 REFERENCES book_reader (id),
    id_book int8 REFERENCES Book (id),
    allotted boolean,
    created_date date,
    updated_date date
)
