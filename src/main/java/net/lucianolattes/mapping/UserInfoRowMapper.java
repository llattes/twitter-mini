package net.lucianolattes.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.lucianolattes.model.UserInfo;

public class UserInfoRowMapper implements RowMapper<UserInfo> {

  @Override
  public UserInfo mapRow(ResultSet rs, int row) throws SQLException {
    UserInfo user = new UserInfo();
    
    user.setUsername(rs.getString("name"));
    user.setPassword(rs.getString("pass"));
    user.setRole(rs.getString("role"));
    
    return user;
  }
}
