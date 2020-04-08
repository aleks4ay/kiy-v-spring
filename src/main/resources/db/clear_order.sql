DELETE FROM description;
DELETE FROM orders;

-- DROP SEQUENCE IF EXISTS users_id_seq;

-- CREATE SEQUENCE users_id_seq START WITH 1;
ALTER SEQUENCE hibernate_sequence RESTART WITH 1;