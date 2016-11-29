INSERT INTO users (username, password, enabled, displayName, bio)
VALUES
  ('luciano',      '094dbbde77990245afe521519e98cf5a48fba5cb5cec9c11422ef3d21bea088f', 1, 'Luciano Lattes', 'Software Engineer'),
  ('thousandeyes', '8550a304358021e7cbf5b35f36157e7bba8f17572f224413c6f6a15912fb4e16', 1, 'ThousandEyes', 'ThousandEyes is a Network Intelligence platform.'),
  ('twitter',      '7352f353c460e74c7ae226952d04f8aa307b12329c5512ec8cb6f1a0f8f9b2cb', 1, 'Twitter', NULL),
  ('johndoe',      'c2713b62c903791bdefc5a6a99df04d4330de491bbc7a0ca6a5007337e4a6028', 1, 'John Doe', NULL),
  ('iamdevloper',  '9ae58c10a561d2b3ffe746d48e9f4b40c0eb9e5169d0356258913f58ce016a22', 1, 'I am devloper', NULL);

INSERT INTO authorities (username, authority)
VALUES
  ('luciano',      'ROLE_ADMIN'),
  ('thousandeyes', 'ROLE_ADMIN'),
  ('twitter',      'ROLE_USER'),
  ('johndoe',      'ROLE_USER'),
  ('iamdevloper',  'ROLE_USER');

INSERT INTO follows (followee, follower)
VALUES
  ('thousandeyes', 'luciano'),
  ('iamdevloper',  'luciano'),
  ('twitter',      'thousandeyes'),
  ('twitter',      'johndoe');
  
INSERT INTO tweets (author, content, timestamp, isRetweet, originalAuthor)
VALUES
  ('luciano',      'This is my first tweet!', NOW(), 0, NULL),
  ('thousandeyes', 'Diagnose Your Network in Real Time', NOW(), 0, NULL),
  ('iamdevloper',  'No-one realised it, but Apple’s biggest revenue stream is now adapters.', NOW(), 0, NULL),
  ('twitter',      'Put a turkey on it with featured Thanksgiving #Stickers.', NOW(), 0, NULL),
  ('luciano',      'Put a turkey on it with featured Thanksgiving #Stickers.', NOW(), 1, 'twitter'),
  ('thousandeyes', 'Our network perimeter has expanded to cloud partners & clients, road warriors & WFH employees.', NOW(), 0, NULL),
  ('johndoe',      'Listening to Dream Theater in a good mood!', NOW(), 0, NULL);
