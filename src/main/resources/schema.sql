CREATE TABLE users (
  username VARCHAR(50) NOT NULL,
  password VARCHAR(64) NOT NULL,
  enabled TINYINT(1) NOT NULL,
  displayName VARCHAR(128) NOT NULL,
  bio VARCHAR(140),
  PRIMARY KEY (username)
) ENGINE = InnoDB;

CREATE TABLE authorities (
  username VARCHAR(50) NOT NULL,
  authority VARCHAR(50) NOT NULL,
  UNIQUE KEY uk_user_authority (username, authority),
  CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username)
) ENGINE = InnoDB;

CREATE TABLE follows (
  follower VARCHAR(50) NOT NULL,
  followee VARCHAR(50) NOT NULL,
  UNIQUE KEY uk_follower_followee (follower, followee),
  CONSTRAINT fk_follows_follower_users FOREIGN KEY (follower) REFERENCES users (username),
  CONSTRAINT fk_follows_followee_users FOREIGN KEY (followee) REFERENCES users (username)
) ENGINE = InnoDB;

CREATE TABLE tweets (
  id BIGINT NOT NULL AUTO_INCREMENT,
  author VARCHAR(50) NOT NULL,
  content VARCHAR(256) NOT NULL,
  timestamp DATETIME NOT NULL,
  isRetweet TINYINT(1) NOT NULL,
  originalAuthor VARCHAR(50),
  PRIMARY KEY (id),
  CONSTRAINT fk_tweets_author_users FOREIGN KEY (author) REFERENCES users (username),
  CONSTRAINT fk_tweets_originalauthor_users FOREIGN KEY (originalAuthor) REFERENCES users (username)
) ENGINE = InnoDB;
