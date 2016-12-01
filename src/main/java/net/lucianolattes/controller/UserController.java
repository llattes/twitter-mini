package net.lucianolattes.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.lucianolattes.exception.FollowException;
import net.lucianolattes.exception.UserNotFoundException;
import net.lucianolattes.model.ApiCallError;
import net.lucianolattes.model.UserInfo;
import net.lucianolattes.model.UserProfile;
import net.lucianolattes.service.UserService;

/**
 * {@link RestController RestController} for all user-related API calls.
 *
 * @author lucianolattes
 */
@RestController
@RequestMapping(value = "/api")
public class UserController {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private UserService userService;

  @ApiOperation(value = "", notes = "Returns a collection of users following the authenticated user")
  @RequestMapping(value = "/followers", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public ResponseEntity<List<UserProfile>> getOwnFollowers(Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.ok(userService.getFollowers(authenticatedUser.getUsername()));
  }

  @ApiOperation(value = "", notes = "Returns a collection of users following the user whose ID is specified as path variable")
  @RequestMapping(value = "/followers/{userId}", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public ResponseEntity<Object> getUserFollowers(@PathVariable String userId, Authentication authentication)
      throws UserNotFoundException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    List<UserProfile> followersByGivenUser = userService.getFollowersByGivenUser(userId);
    return ResponseEntity.ok(followersByGivenUser);
  }

  @ApiOperation(value = "", notes = "Returns a collection of users followed by the authenticated user")
  @RequestMapping(value = "/following", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public ResponseEntity<List<UserProfile>> getOwnFollowing(Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.ok(userService.getFollowing(authenticatedUser.getUsername()));
  }

  @ApiOperation(value = "", notes = "Returns a collection of users followed by the user whose ID is specified as path variable")
  @RequestMapping(value = "/following/{userId}", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public ResponseEntity<Object> getUserFollowing(@PathVariable String userId, Authentication authentication)
      throws UserNotFoundException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    List<UserProfile> followingByGivenUser = userService.getFollowingByGivenUser(userId);
    return ResponseEntity.ok(followingByGivenUser);
  }

  @ApiOperation(value = "", notes = "Allows the authenticated user to follow the user specified in the request body")
  @RequestMapping(value = "/follow", produces = { "application/xml", "application/json" }, method = RequestMethod.POST)
  public ResponseEntity<List<UserProfile>> follow(@RequestBody UserInfo user, Authentication authentication)
      throws FollowException, UserNotFoundException, URISyntaxException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    if (authenticatedUser.getUsername().equals(user.getUsername())) {
      throw new FollowException("A User (" + authenticatedUser.getUsername() + ") cannot follow himself");
    }

    List<UserProfile> followers = userService.follow(authenticatedUser.getUsername(), user.getUsername());
    return ResponseEntity.created(new URI("http://localhost:8080")).body(followers);
  }

  @ApiOperation(value = "", notes = "Allows the authenticated user to unfollow the user specified in the request body")
  @RequestMapping(value = "/unfollow", produces = { "application/xml",
      "application/json" }, method = RequestMethod.POST)
  public ResponseEntity<List<UserProfile>> unfollow(@RequestBody UserInfo user, Authentication authentication)
      throws FollowException, UserNotFoundException, URISyntaxException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    if (authenticatedUser.getUsername().equals(user.getUsername())) {
      throw new FollowException("A User (" + authenticatedUser.getUsername() + ") cannot unfollow himself");
    }

    List<UserProfile> followers = userService.unfollow(authenticatedUser.getUsername(), user.getUsername());
    return ResponseEntity.created(new URI("http://localhost:8080")).body(followers);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiCallError handleAppException(UserNotFoundException ex) {
    LOGGER.debug("Handling UserNotFoundException");
    return new ApiCallError(HttpStatus.BAD_REQUEST.toString(), ex.getLocalizedMessage(), ex.getClass().getSimpleName());
  }

  @ExceptionHandler(FollowException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiCallError handleAppException(FollowException ex) {
    LOGGER.debug("Handling FollowException");
    return new ApiCallError(HttpStatus.BAD_REQUEST.toString(), ex.getLocalizedMessage(), ex.getClass().getSimpleName());
  }

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiCallError handleAppException(DuplicateKeyException ex) {
    LOGGER.debug("Handling DuplicateKeyException");
    return new ApiCallError(HttpStatus.BAD_REQUEST.toString(), ex.getLocalizedMessage(), ex.getClass().getSimpleName());
  }
}
