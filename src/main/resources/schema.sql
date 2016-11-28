CREATE TABLE tweet (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  author VARCHAR(64) NOT NULL,
  content VARCHAR(256) NOT NULL,
  timestamp DATETIME NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB;

CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(64) NOT NULL,
  enabled TINYINT(1) NOT NULL,
  PRIMARY KEY (username)
) ENGINE = InnoDB;

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  UNIQUE KEY ix_auth_username (username, authority),
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
) ENGINE = InnoDB;
