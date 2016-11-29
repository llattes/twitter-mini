package net.lucianolattes.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.lucianolattes.mapping.TweetRowMapper;
import net.lucianolattes.model.Tweet;

@Repository
@Transactional
public class TweetDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public List<Tweet> getUserTweets(String username, List<String> followingUsers, String search) {
    String sql = "SELECT * FROM tweets WHERE author = :username";
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("username", username);

    if (followingUsers.size() > 0) {
      sql = "SELECT * FROM tweets WHERE (author = :username OR author IN (:followingUsers))";
      mapSqlParameterSource.addValue("followingUsers", followingUsers);
    }

    if (search != null) {
      sql += " AND content LIKE :search";
      mapSqlParameterSource.addValue("search", "%" + search + "%");
    }

    sql += " ORDER BY timestamp ASC";

    List<Tweet> tweets = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new TweetRowMapper());
    return tweets;
  }

  public Tweet getTweetById(Long id) {
    String sql = "SELECT * FROM tweets WHERE id = :id";
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("id", id);
    List<Tweet> tweets = namedParameterJdbcTemplate.query(sql, mapSqlParameterSource, new TweetRowMapper());
    if (tweets.isEmpty()) {
      return null;
    }
    return tweets.get(0);
  }

  public Boolean retweetAllowed(String username, Long id) {
    String sql = "SELECT count(*) FROM tweets WHERE (author = :username AND originalId = :id) OR (author = :username AND id = :id)";
    MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource("username", username).addValue("id", id);
    int count = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, Integer.class);

    if (count > 0) {
      return false;
    }

    return true;
  }

  public void insertTweet(Tweet tweet) {
    String sql = "INSERT INTO tweets (author, content, timestamp, isRetweet, originalId, originalAuthor) VALUES (:author, :content, :timestamp, :isRetweet, :originalId, :originalAuthor)";
    namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(tweet));
  }
}
