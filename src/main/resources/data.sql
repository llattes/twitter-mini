INSERT INTO tweet (id, author, content, timestamp)
VALUES
  (1, 'Lucho', 'Statistics', NOW()),
  (2, 'Lucho', 'Statistics 2', NOW()),
  (3, 'Lucho', 'Statistics 3', NOW()),
  (4, 'Lucho', 'Statistics 4', NOW());

INSERT INTO users (username, password, enabled)
VALUES
  ('Lucho', 'fabe47f675df40f18f5e14025687cdfd5e8891b09e2664f9ce663ef418a13676', 1),
  ('Pocho', 'e4aad07909e196a608c8d415a69bd9e9b494942d7095c1df1bd8bc2194c0e175', 1);

INSERT INTO authorities (username, authority)
VALUES
  ('Lucho', 'ROLE_ADMIN'),
  ('Pocho', 'ROLE_USER');
