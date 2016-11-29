package net.lucianolattes.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import net.lucianolattes.exception.TweetException;
import net.lucianolattes.exception.UserNotFoundException;
import net.lucianolattes.model.ApiCallError;
import net.lucianolattes.model.Tweet;
import net.lucianolattes.service.TweetService;

@RestController
@RequestMapping(value = "/api")
public class TweetController {

  private static final Logger LOGGER = LoggerFactory.getLogger(TweetController.class);

  @Autowired
  private TweetService tweetService;

  @ApiOperation(value = "", notes = "Get tweets for the authenticated user")
  @RequestMapping(value = "/tweets", produces = { "application/xml", "application/json" }, method = RequestMethod.GET)
  public ResponseEntity<List<Tweet>> getOwnTweets(@RequestParam(value = "search", required = false) String search,
      Authentication authentication) {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.ok(tweetService.getTweets(authenticatedUser.getUsername(), search));
  }

  @ApiOperation(value = "", notes = "Publish/create new tweet")
  @RequestMapping(value = "/tweets/create", produces = { "application/xml",
      "application/json" }, method = RequestMethod.POST)
  public ResponseEntity<Tweet> addTweet(@RequestBody Tweet tweet, Authentication authentication)
      throws URISyntaxException, TweetException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    if (tweet.getContent() == null) {
      throw new TweetException("Tweet 'content' cannot be null");
    }

    if (tweet.getContent().length() > 140) {
      throw new TweetException("Tweet 'content' must not exceed a maximum of 140 characters");
    }

    return ResponseEntity.created(new URI("http://localhost:8080"))
        .body(tweetService.insertTweet(authenticatedUser.getUsername(), tweet));
  }

  @ApiOperation(value = "", notes = "Retweet an existing tweet")
  @RequestMapping(value = "/tweets/retweet/{id}", produces = { "application/xml",
      "application/json" }, method = RequestMethod.POST)
  public ResponseEntity<Tweet> retweet(@PathVariable Long id, Authentication authentication)
      throws URISyntaxException, TweetException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    return ResponseEntity.created(new URI("http://localhost:8080"))
        .body(tweetService.retweet(authenticatedUser.getUsername(), id));
  }

  @ApiOperation(value = "", notes = "Get tweets for the user whose ID is specified as path variable")
  @RequestMapping(value = "/tweets/{userId}", produces = { "application/xml",
      "application/json" }, method = RequestMethod.GET)
  public ResponseEntity<Object> getUserTweets(@PathVariable String userId,
      @RequestParam(value = "search", required = false) String search, Authentication authentication)
      throws UserNotFoundException {
    User authenticatedUser = (User) authentication.getPrincipal();

    if (authenticatedUser == null) {
      LOGGER.debug("There is no authenticated User");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    List<Tweet> tweetsByGivenUser = tweetService.getTweetsByGivenUser(userId, search);
    return ResponseEntity.ok(tweetsByGivenUser);
  }

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiCallError handleAppException(UserNotFoundException ex) {
    LOGGER.debug("Handling UserNotFoundException");
    return new ApiCallError(HttpStatus.BAD_REQUEST.toString(), ex.getLocalizedMessage(), ex.getClass().getSimpleName());
  }

  @ExceptionHandler(TweetException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public @ResponseBody ApiCallError handleAppException(TweetException ex) {
    LOGGER.debug("Handling TweetException");
    return new ApiCallError(HttpStatus.BAD_REQUEST.toString(), ex.getLocalizedMessage(), ex.getClass().getSimpleName());
  }
}
