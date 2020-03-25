DROP TABLE IF EXISTS status;
DROP TABLE IF EXISTS description;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS worker_roles;
DROP TABLE IF EXISTS worker;
DROP TABLE IF EXISTS client;
DROP SEQUENCE IF EXISTS global_seq_user;
DROP SEQUENCE IF EXISTS global_seq_order;

CREATE SEQUENCE global_seq_user START WITH 1;
CREATE SEQUENCE global_seq_order START WITH 100000;

CREATE TABLE worker
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq_user'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR,
  password         VARCHAR,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL
);

CREATE TABLE client
(
  id              VARCHAR PRIMARY KEY DEFAULT nextval('global_seq_user'),
  id_from_1c      VARCHAR UNIQUE NOT NULL ,
  id_parent       VARCHAR,
  isfolder        INTEGER,
  descr1          VARCHAR,
  descr2          VARCHAR,
  phone           VARCHAR,
  inn             VARCHAR,
  num_certificate VARCHAR,
  addr_fis        VARCHAR,
  addr_urid       VARCHAR
);


CREATE UNIQUE INDEX client_unique_idx ON client (id_from_1c);

CREATE TABLE worker_roles
(
  worker_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT worker_roles_idx UNIQUE (worker_id, role),
  FOREIGN KEY (worker_id) REFERENCES worker (id) ON DELETE CASCADE
);




CREATE TABLE orders
(
  id    INTEGER PRIMARY KEY DEFAULT nextval('global_seq_order'),
  iddoc varchar NOT NULL,
  docno VARCHAR NOT NULL,
  client_id VARCHAR NOT NULL,
  manager_id VARCHAR NOT NULL,
  designer_id INTEGER,
  duration integer,
  t_create TIMESTAMP,
  t_factory TIMESTAMP,
  t_end TIMESTAMP,
--   price DEC(14,3),
--   marker INTEGER
  CONSTRAINT order_idx UNIQUE (iddoc, docno),
  FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE
);

/*


CREATE TABLE meals (
  id          INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  user_id     INTEGER   NOT NULL,
  date_time   TIMESTAMP NOT NULL,
  description TEXT      NOT NULL,
  calories    INT       NOT NULL,
  FOREIGN KEY (user_id) REFERENCES order (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX meals_unique_user_datetime_idx
  ON meals (user_id, date_time);












CREATE TABLE descriptions
(
  key varchar(13) NOT NULL PRIMARY KEY,
  */
/*  id SERIAL NOT NULL PRIMARY KEY,*//*

  kod INTEGER NOT NULL ,
  big_number integer NOT NULL ,
  iddoc varchar(9),
  pos integer,
  id_tmc varchar(9),
  descr_first VARCHAR(100),
  descr_second VARCHAR(200),
  quantity integer,
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER,
  embodiment varchar(9),
  marker INTEGER DEFAULT 1
);


CREATE TABLE statuses
(
  key varchar(13) NOT NULL PRIMARY KEY,
  */
/*id SERIAL NOT NULL PRIMARY KEY,*//*

  kod integer NOT NULL,
  iddoc VARCHAR(9),
  time_0 bigint,
  time_1 bigint,
  time_2 bigint,
  time_3 bigint,
  time_4 bigint,
  time_5 bigint,
  time_6 bigint,
  time_7 bigint,
  time_8 bigint,
  time_9 bigint,
  time_10 bigint,
  time_11 bigint,
  time_12 bigint,
  time_13 bigint,
  time_14 bigint,
  time_15 bigint,
  time_16 bigint,
  type_index INTEGER,
  status_index INTEGER,
  designer_name VARCHAR(30),
  is_technologichka INTEGER,
  is_parsing INTEGER DEFAULT 0
);


CREATE TABLE tmc
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  id_parent VARCHAR(9),
  code VARCHAR(5),
  descr VARCHAR(50),
  is_folder integer,
  descr_all VARCHAR(100),
  type varchar(9),
  size_a INTEGER,
  size_b INTEGER,
  size_c INTEGER
);

CREATE TABLE set_technologichka
(
  id VARCHAR(9) NOT NULL PRIMARY KEY,
  parentid VARCHAR(9),
  descr varchar(100)
);
*/
