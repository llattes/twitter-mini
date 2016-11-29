package net.lucianolattes.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

import net.lucianolattes.model.Tweet;

public class TweetRowMapper implements RowMapper<Tweet> {

  @Override
  public Tweet mapRow(ResultSet rs, int row) throws SQLException {
    Tweet tweet = new Tweet();

    tweet.setId(rs.getLong("id"));
    tweet.setAuthor(rs.getString("author"));
    tweet.setContent(rs.getString("content"));
    Timestamp timestamp = rs.getTimestamp("timestamp");
    tweet.setTimestamp(new java.util.Date(timestamp.getTime()));
    tweet.setIsRetweet(rs.getBoolean("isRetweet"));
    tweet.setOriginalId(rs.getLong("originalId"));
    tweet.setOriginalAuthor(rs.getString("originalAuthor"));

    return tweet;
  }
}
