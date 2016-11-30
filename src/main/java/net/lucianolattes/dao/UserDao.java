package net.lucianolattes.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import net.lucianolattes.mapping.UserInfoRowMapper;
import net.lucianolattes.mapping.UserProfileRowMapper;
import net.lucianolattes.model.UserInfo;
import net.lucianolattes.model.UserProfile;

/**
 * Data Access Object that uses the <tt>NamedParameterJdbcTemplate</tt> bean for
 * making JDBC operations against the users, follows and authorities tables.
 *
 * @author lucianolattes
 */
@Repository
@Transactional
public class UserDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public UserInfo getUserInfo(String username) {
    String sql = "SELECT u.username username, u.password password, u.enabled enabled, a.authority role "
        + "FROM users u INNER JOIN authorities a on u.username = a.username WHERE "
        + "u.enabled = 1 and u.username = :username";
    UserInfo userInfo = (UserInfo) namedParameterJdbcTemplate.queryForObject(sql,
        new MapSqlParameterSource("username", username), new UserInfoRowMapper());
    return userInfo;
  }

  public Boolean checkUser(String username) {
    String sql = "SELECT count(*) FROM users WHERE username = :username";
    int count = namedParameterJdbcTemplate.queryForObject(sql,
        new MapSqlParameterSource("username", username), Integer.class);

    if (count != 1) {
      return false;
    }

    return true;
  }

  public Boolean checkFollow(String follower, String followee) {
    String sql = "SELECT count(*) FROM follows WHERE follower = :follower AND followee = :followee";
    int count = namedParameterJdbcTemplate.queryForObject(sql,
        new MapSqlParameterSource("follower", follower).addValue("followee", followee), Integer.class);

    if (count != 1) {
      return false;
    }

    return true;
  }

  public List<UserProfile> getFollowing(String username) {
    String sql = "SELECT * FROM users u INNER JOIN follows f ON u.username = f.followee WHERE f.follower = :username";
    List<UserProfile> userProfiles = namedParameterJdbcTemplate.query(sql,
        new MapSqlParameterSource("username", username), new UserProfileRowMapper());
    return userProfiles;
  }

  public List<UserProfile> getFollowers(String username) {
    String sql = "SELECT * FROM users u INNER JOIN follows f ON u.username = f.follower WHERE f.followee = :username";
    List<UserProfile> userProfiles = namedParameterJdbcTemplate.query(sql,
        new MapSqlParameterSource("username", username), new UserProfileRowMapper());
    return userProfiles;
  }

  public void insertFollow(String follower, String followee) {
    String sql = "INSERT INTO follows (followee, follower) VALUES (:followee, :follower)";
    namedParameterJdbcTemplate.update(sql,
        new MapSqlParameterSource("followee", followee).addValue("follower", follower));
  }

  public void deleteFollow(String follower, String followee) {
    String sql = "DELETE FROM follows WHERE follower = :follower AND followee = :followee";
    namedParameterJdbcTemplate.update(sql,
        new MapSqlParameterSource("followee", followee).addValue("follower", follower));
  }
}
