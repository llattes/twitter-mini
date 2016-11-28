package net.lucianolattes.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.lucianolattes.mapping.UserInfoRowMapper;
import net.lucianolattes.model.UserProfile;
import net.lucianolattes.model.UserInfo;

@Component
@Transactional
public class UserDao {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public UserInfo getUserInfo(String username) {
    String sql = "SELECT u.username name, u.password pass, a.authority role FROM "
        + "users u INNER JOIN authorities a on u.username = a.username WHERE "
        + "u.enabled = 1 and u.username = :username";
    UserInfo userInfo = (UserInfo) namedParameterJdbcTemplate.queryForObject(sql, new MapSqlParameterSource("username", username), new UserInfoRowMapper());
    return userInfo;
  }
  
  public List<UserProfile> getUsers() {
    return new ArrayList<UserProfile>();
  }
}
