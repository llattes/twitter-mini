INSERT INTO tweet (id, author, content, timestamp)
VALUES
  (1, 'Lucho', 'Statistics', NOW()),
  (2, 'Lucho', 'Statistics 2', NOW()),
  (3, 'Lucho', 'Statistics 3', NOW()),
  (4, 'Lucho', 'Statistics 4', NOW());

INSERT INTO users (username, password, enabled)
VALUES
  ('Lucho', 'Lucho', 1),
  ('Pocho', 'Pocho', 1);

INSERT INTO authorities (username, authority)
VALUES
  ('Lucho', 'ROLE_ADMIN'),
  ('Pocho', 'ROLE_USER');

