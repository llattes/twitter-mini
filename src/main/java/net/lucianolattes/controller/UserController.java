package net.lucianolattes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.lucianolattes.dao.UserDao;
import net.lucianolattes.model.UserProfile;

@RestController
@RequestMapping(value = "/api")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetController.class);
  
  @Autowired
  private UserDao userDao;
  
  @RequestMapping(value = "/followers", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public List<UserProfile> getFollowers(Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return userDao.getUsers();
  }
  
  @RequestMapping(value = "/followers/{userId}", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public List<UserProfile> getFollowers(@PathVariable String userId, Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return userDao.getUsers();
  }
  
  @RequestMapping(value = "/following", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public List<UserProfile> getFollowing(Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return userDao.getUsers();
  }
  
  @RequestMapping(value = "/following/{userId}", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public List<UserProfile> getFollowing(@PathVariable String userId, Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return userDao.getUsers();
  }
  
  @RequestMapping(value = "/follow", produces = { "application/xml", "application/json" }, method = RequestMethod.POST)
  public UserProfile follow(@RequestBody UserProfile user, Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return user;
  }
  
  @RequestMapping(value = "/unfollow", produces = { "application/xml", "application/json" }, method = RequestMethod.POST)
  public UserProfile unfollow(@RequestBody UserProfile user, Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser != null) {
      LOGGER.debug("Authenticated username: " + authenticatedUser.getUsername());
    }

    return user;
  }
}
