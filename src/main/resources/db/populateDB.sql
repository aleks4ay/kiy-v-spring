-- DELETE FROM status;
-- DELETE FROM description;
DELETE FROM orders;
DELETE FROM worker_roles;
DELETE FROM worker;
DELETE FROM client;
ALTER SEQUENCE global_seq_user RESTART WITH 100000;
ALTER SEQUENCE global_seq_order RESTART WITH 100000;

INSERT INTO worker (name, email, password) VALUES
  ('Solomko', 'solomko@ukr.net', '1111'),
  ('Varenya', 'natella_var@ukr.net', '1111'),
  ('Mosienko', 'mos@ukr.net', '1111');

INSERT INTO client (id_from_1c, id_parent, isfolder, descr1, descr2, phone) VALUES
  ('3V', '10', '2', 'Агромат-Сервис', '', '+380975748624'),
  ('30', '10', '2', 'Технохолод', '', '+380972489637'),
  ('RT', '10', '2', 'АВАЛАКС', '', '+380635478544'),
  ('2T', '10', '2', 'Холодтехсервис', '', '+380505774781');

INSERT INTO worker_roles (role, worker_id) VALUES
  ('ROLE_MANAGER', 100000),
  ('ROLE_MANAGER', 100001),
  ('ROLE_DESIGNER', 100002);

INSERT INTO orders (iddoc, docno, client_id, manager_id, designer_id, duration, t_create, t_factory) VALUES
  ('2SVT', 'KI-0002456', 100004, 100000, 100002, 14, '2020-03-23 10:00:00','2020-03-25 12:48:10'),
  ('2RVT', 'KI-0002476', 100003, 100001, 100002, 14, '2020-03-23 9:00:00','2020-03-25 11:48:10'),
  ('2SRT', 'KI-0002433', 100003, 100001, 100002, 14, '2020-03-23 11:00:00','2020-03-25 13:48:10'),
  ('3RVT', 'KI-0002516', 100005, 100001, 100002, 14, '2020-03-23 10:10:00','2020-03-25 13:38:10'),
  ('3SVT', 'KI-0001456', 100006, 100001, 100002, 14, '2020-03-23 10:08:00','2020-03-25 14:48:10'),
  ('YTVT', 'KI-0003456', 100005, 100001, 100002, 14, '2020-03-23 14:25:00','2020-03-25 10:08:10'),
  ('YYVT', 'KI-0008756', 100003, 100001, 100002, 14, '2020-03-23 7:40:00','2020-03-25 11:11:10'),
  ('6STW', 'KI-0003506', 100004, 100000, 100002, 14, '2020-03-23 10:00:00','2020-03-25 12:44:00');
