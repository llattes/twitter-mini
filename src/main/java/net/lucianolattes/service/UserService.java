package net.lucianolattes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.lucianolattes.dao.UserDao;
import net.lucianolattes.exception.FollowException;
import net.lucianolattes.exception.UserNotFoundException;
import net.lucianolattes.model.UserProfile;

@Service
public class UserService {

  @Autowired
  private UserDao userDao;

  public List<UserProfile> getFollowing(String username) {
    List<UserProfile> following = userDao.getFollowing(username);
    return following;
  }

  public List<UserProfile> getFollowingByGivenUser(String username) throws UserNotFoundException {
    if (userDao.checkUser(username)) {
      return getFollowing(username);
    } else {
      throw new UserNotFoundException("The user '" + username + "' does not exist");
    }
  }

  public List<UserProfile> getFollowers(String username) {
    List<UserProfile> followers = userDao.getFollowers(username);
    return followers;
  }

  public List<UserProfile> getFollowersByGivenUser(String username) throws UserNotFoundException {
    if (userDao.checkUser(username)) {
      return getFollowers(username);
    } else {
      throw new UserNotFoundException("The user '" + username + "' does not exist");
    }
  }

  public List<UserProfile> follow(String follower, String followee) throws UserNotFoundException {
    if (userDao.checkUser(followee)) {
      userDao.insertFollow(follower, followee);
      return getFollowing(follower);
    } else {
      throw new UserNotFoundException("The user '" + followee + "' does not exist");
    }
  }

  public List<UserProfile> unfollow(String follower, String followee) throws UserNotFoundException, FollowException {
    if (userDao.checkUser(followee)) {
      if (userDao.checkFollow(follower, followee)) {
        userDao.deleteFollow(follower, followee);
        return getFollowing(follower);
      } else {
        throw new FollowException("The user '" + follower + "' does not follow '" + followee + "'");
      }
    } else {
      throw new UserNotFoundException("The user '" + followee + "' does not exist");
    }
  }
}
