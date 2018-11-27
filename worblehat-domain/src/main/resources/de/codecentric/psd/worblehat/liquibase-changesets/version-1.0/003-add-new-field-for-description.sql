-- liquibase formatted sql

-- changeset action:insert_book_description

ALTER TABLE book ADD description TEXT;