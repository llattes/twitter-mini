package net.lucianolattes.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import net.lucianolattes.model.UserInfo;
import net.lucianolattes.model.UserProfile;

/**
 * Maps a DB row to a <tt>UserProfile</tt> object.
 *
 * @author lucianolattes
 */
public class UserProfileRowMapper implements RowMapper<UserProfile> {

  @Override
  public UserProfile mapRow(ResultSet rs, int row) throws SQLException {
    UserProfile user = new UserProfile();
    UserInfo info = new UserInfo();

    info.setUsername(rs.getString("username"));
    info.setEnabled(rs.getBoolean("enabled"));

    user.setDisplayName(rs.getString("displayname"));
    user.setBio(rs.getString("bio"));
    user.setUserInfo(info);

    return user;
  }
}
