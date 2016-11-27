package net.lucianolattes.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.lucianolattes.mapping.UserInfoRowMapper;
import net.lucianolattes.model.UserInfo;

@Component
@Transactional
public class UserDao {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  public UserInfo getUserInfo(String username) {
    String sql = "SELECT u.username name, u.password pass, a.authority role FROM "
        + "users u INNER JOIN authorities a on u.username = a.username WHERE "
        + "u.enabled = 1 and u.username = ?";
    UserInfo userInfo = (UserInfo) jdbcTemplate.queryForObject(sql, new Object[] { username }, new UserInfoRowMapper());
    return userInfo;
  }
}
